/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author CCK
 */
public class PDFHelper {

    private String fileName;

    public PDFHelper() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void addTableHeader(PdfPTable table, List<String> heading) {
        String[] colHeader = heading.get(0).split(",");

        Stream.of(colHeader)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    public void addRows(PdfPTable table, List<String> heading) {
        for (int i = 1; i < heading.size(); i++) {
            String[] split = heading.get(i).split(",");
            for (int j = 0; j < split.length; j++) {
                table.addCell(split[j]);
            }
        }
    }

}
