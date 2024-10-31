package org.hc.html2pdf.html2pdf;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;

/**
 * @author 叽哒嘎叽
 * @since 2024/10/30
 */
public class ParseHtml {
    private static final Logger log = LoggerFactory.getLogger(ParseHtml.class);
    public static Document parser(@NonNull File inputHtml) throws IOException {
        try {
            Document document = Jsoup.parse(inputHtml, "UTF-8");
            document.outputSettings().syntax(Document.OutputSettings.Syntax.html);
            return document;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
//        log.info("test logback");
        File inputHtml = null;
        try {
            inputHtml = ResourceUtils.getFile("classpath:templates/test.html");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Document document = null;
        try {
            document = ParseHtml.parser(inputHtml);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String baseUri = FileSystems.getDefault().getPath("./html2pdf/src/main/resources/templates").toUri().toString();
        GenPdf.genPdf(document, baseUri, "./html2pdf/temp/test.pdf");
    }
}
