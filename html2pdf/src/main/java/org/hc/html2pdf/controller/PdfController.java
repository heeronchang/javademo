package org.hc.html2pdf.controller;

import org.hc.html2pdf.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author 叽哒嘎叽
 * @since 2024/10/31
 */
@Controller
@RequestMapping("/pdf")
public class PdfController {
    private final PdfService pdfService;

    @Autowired
    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/ping")
    @ResponseBody
    public ResponseEntity<?> ping() {
        return ResponseEntity.ok("pong");
    }

    /**
     * 此种方式集成的echarts需要在浏览器中打开之后，再根据script标签动态生成echart表
     * @param model
     * @return
     */
    @GetMapping("/test/template")
    public String testTemplate(Model model) {
        model.addAttribute("name", "heeron");
        List<String> list = Arrays.asList("衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子");
        model.addAttribute("xData", list);
        List<Object> seriesData = Collections.singletonList(new HashMap<String, Object>() {
            {
                put("name", "销量");
                put("type", "bar");
                put("data", Arrays.asList(5, 20, 36, 10, 10, 20));
            }
        });
        model.addAttribute("seriesData", seriesData);
//        return "test-template";
        return "phantomjs-template";
    }

    @GetMapping("/gen")
    @ResponseBody
    public ResponseEntity<String> genPdf(HttpServletRequest request, HttpServletResponse response) {
        return pdfService.genPdf(request, response);
    }

    @PostMapping("/download")
    @ResponseBody
    public ResponseEntity<String> download() {
        return null;
    }
}
