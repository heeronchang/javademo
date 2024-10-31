package org.hc.html2pdf.html2pdf;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author 叽哒嘎叽
 * @since 2024/10/30
 */
public class GenPdf {
    private static final Logger log = LoggerFactory.getLogger(GenPdf.class);
    public static void genPdf(Document document, String baseUri, String dstPath) {
        try (OutputStream os = new FileOutputStream(dstPath)){
            PdfRendererBuilder builder = new PdfRendererBuilder();
//            builder.withUri(dstPath);
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(document), baseUri);
            builder.run();
        } catch (IOException e) {
            log.error("生成pdf失败", e);
            e.printStackTrace();
        }
    }
}
