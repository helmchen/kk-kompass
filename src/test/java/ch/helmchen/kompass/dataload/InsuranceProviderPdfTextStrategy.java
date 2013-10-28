/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.helmchen.kompass.dataload;

import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author helmut
 */
public class InsuranceProviderPdfTextStrategy implements TextExtractionStrategy {

    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private StringBuilder text;
    private float lastXPos;
    private String pageHeaderEnd;
    private String pageFooterStart;
    private boolean addContent;

    {
        text = new StringBuilder();
        lastXPos = 0.0f;
        addContent = false;
    }

    public InsuranceProviderPdfTextStrategy(String pageHeaderEnd, String pageFooterStart) {
        this.pageHeaderEnd = pageHeaderEnd;
        this.pageFooterStart = pageFooterStart;

        if (this.pageHeaderEnd == null || this.pageHeaderEnd.isEmpty()) {
            addContent = true;
        }
    }

    @Override
    public String getResultantText() {
        return text.toString();
    }

    @Override
    public void beginTextBlock() {
        //  text.append("<<<");
    }

    @Override
    public void renderText(TextRenderInfo tri) {
        if (tri.getText().contains(pageFooterStart)) {
            addContent = false;
        }
        if (addContent) {
            final float currentXPos = tri.getBaseline().getStartPoint().get(0);
            if (currentXPos < lastXPos && currentXPos < 80) {
                text.append("\n");
            }
            //text.append(new String(tri.getText().getBytes(UTF8_CHARSET)));
            try {
                byte[] content = tri.getFont().convertToBytes(tri.getText());
                String converted = new String(content, "UTF-8");
                text.append(converted);
            } catch (UnsupportedEncodingException ex) {
                text.append(tri.getText());
            }
            //  text.append (" (x-POS: " + currentXPos + ")"); 
            lastXPos = currentXPos;
        }
        if (pageHeaderEnd != null && (!pageHeaderEnd.isEmpty())
                && tri.getText().contains(pageHeaderEnd)) {
            addContent = true;
        }

    }

    @Override
    public void endTextBlock() {
        text.append("\t");
    }

    @Override
    public void renderImage(ImageRenderInfo iri) {
    }
}
