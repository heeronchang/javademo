package org.hc.html2pdf.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 叽哒嘎叽
 * @since 2024/10/31
 */
@Service
public class PdfService {
    private static final Logger log = LoggerFactory.getLogger(PdfService.class);

    public ResponseEntity<String> genPdf(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/pdf");

        String pdf = "test-pdf.pdf";
        response.setHeader("Content-Disposition", "filename=" + new String(pdf.getBytes(), StandardCharsets.ISO_8859_1));

        OutputStream os = null;
        try {
            os = response.getOutputStream();
        } catch (IOException e) {
            log.error("生成pdf失败", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("HttpServletResponse get outputStream error.");
        }

        Map<String, Object> variables = getVariables(request, response);
        String templateString = getTemplateString(request, response, variables);

        PdfRendererBuilder builder = new PdfRendererBuilder();
        try {
            builder.useFont(ResourceUtils.getFile("classpath:static/fonts/simsun.ttf"), "simsun");
        } catch (FileNotFoundException e) {
            log.error("get font resource err.", e);
        }
        String baseUri = Objects.requireNonNull(getClass().getResource("/static")).toString();
        log.warn("baseUri:{}", baseUri);
        builder.withHtmlContent(templateString, baseUri);
        builder.toStream(os);
        try {
            builder.run();
        } catch (IOException e) {
            log.error("gen pdf error.", e);
        }
        return ResponseEntity.ok("生成PDF成功");
    }

    private static Map<String, Object> getVariables(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", "heeron");
        String figure1Base64Url = genBase64Image();
        if (StringUtils.hasLength(figure1Base64Url)) {
            variables.put("figure1Base64Url", figure1Base64Url);
        }

        return variables;
    }

    /**
     * 根据业务数据生成报表，可使用CompleteFuture 并发处理多个图表（phantomjs是否支持？如果不支持是否可以部署多个phantomjs，以池的形式管理起来以满足并发需求）
     *
     * @return
     */
    private static String genBase64Image() {
        // 1. 定义图表配置（options），将其转换为 JSON 字符串
        String optionsJSON = "{ \"title\": {\"text\": \"示例图表\"}, \"xAxis\": {\"data\": [\"Mon\", \"Tue\", \"Wed\", \"Thu\", \"Fri\", \"Sat\", \"Sun\"]}, \"yAxis\": {}, \"series\": [{\"type\": \"line\", \"data\": [820, 932, 901, 934, 1290, 1330, 1320]}]}";

        // 2. 定义输出图表图片的路径
        String outputPath = "html2pdf/src/main/resources/temp/charts/figure1.png";

        // TODO：考虑使用临时文件夹作为缓存，避免每次都调用phantomjs生成
        // 3. 调用 PhantomJS 渲染脚本
        // 3. 获取PhantomJS脚本的绝对路径
        URL resourceUrl = PdfService.class.getClassLoader().getResource("static/js/phantomjs/renderChart.js");
        if (resourceUrl == null) {
            log.error("无法找到资源文件: static/js/phantomjs/renderChart.js");
            return null;
        }
        String scriptPath = resourceUrl.getFile();
        ProcessBuilder processBuilder = new ProcessBuilder(
                "/usr/local/phantomjs-2.1.1/bin/phantomjs", scriptPath, optionsJSON, outputPath);
        Process process = null;
        try {
            process = processBuilder.start();
            process.waitFor(30, TimeUnit.SECONDS);
        } catch (IOException e) {
            log.error("start process error.", e);
        } catch (InterruptedException e) {
            log.error("waitFor error.", e);
        }

        // 4. 将生成的图表图片转换为 Base64
        byte[] imageBytes = null;
        try {
            imageBytes = java.nio.file.Files.readAllBytes(new File(outputPath).toPath());
        } catch (IOException e) {
            log.error("readAllBytes err.", e);
            return null;
        }
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
//        log.info("base64Image:{}", base64Image);
        return base64Image;
    }

    private static String getTemplateString(HttpServletRequest request, HttpServletResponse response, Map<String, Object> variables) {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//        resolver.setPrefix("./html2pdf/src/main/resources/templates/");
        resolver.setPrefix("templates/");
        resolver.setSuffix(".html");

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        Context context = getContext(request, response, variables);
        String templateString = templateEngine.process("phantomjs-template", context);
//        String templateString = templateEngine.process("test-header", context);
//        log.info("templateString:{}", templateString);
        return templateString;
    }

    private static Context getContext(HttpServletRequest request, HttpServletResponse response, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return context;
    }
}
