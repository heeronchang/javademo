package org.hc.html2pdf;

import org.hc.html2pdf.html2pdf.GenPdf;
import org.hc.html2pdf.html2pdf.ParseHtml;
import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Html2pdfApplication {

    public static void main(String[] args) {
        SpringApplication.run(Html2pdfApplication.class, args);
    }

}
