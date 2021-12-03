/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Report;

import Helper.PDFHelper;
import Model.Delivery.Delivery;
import Model.Feedback.Feedback;
import Model.Order.Order;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author CCK
 */
public class Report {
//https://www.baeldung.com/java-pdf-creation

    String absolutePath = new File("").getAbsolutePath()+"\\";
    PDFHelper help = new PDFHelper();
    Paragraph hr = new Paragraph(" ");
    Font headerFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
    LocalDate now = LocalDate.now();

    public Report() {
    }

    public String getThisPath() {
        return absolutePath;
    }
    

    public String generateOrderReport() {
        Document document = new Document();
        String fileName = "report\\Order_" + now + ".pdf";
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            document.add(new Chunk("Order Report", headerFont));
            document.add(hr);

            List<String> fromFile = new Order().getReader().getFromFile();
            return createPdfTable(document, fileName, writer, fromFile);
        } catch (IOException | DocumentException e) {
            System.out.println(e.getMessage());
            return "Error";
        }
    }

    public String generateFeedbackReport() {
        Document document = new Document();
        String fileName = "report\\Feedback_" + now + ".pdf";
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            document.add(new Chunk("Feedback Report", headerFont));
            document.add(hr);

            List<String> fromFile = new Feedback().getReader().getFromFile();
            return createPdfTable(document, fileName, writer, fromFile);
        } catch (IOException | DocumentException e) {
            System.out.println(e.getMessage());
            return "Error";
        }
    }

    public String generateDeliveryReport() {
        Document document = new Document();
        String fileName = "report\\Delivery_" + now + ".pdf";
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            document.add(new Chunk("Delivery Report", headerFont));
            document.add(hr);

            List<String> fromFile = new Delivery().getReader().getFromFile();
            return createPdfTable(document, fileName, writer, fromFile);
        } catch (IOException | DocumentException e) {
            System.out.println(e.getMessage());
            return "Error";
        }
    }

    private String createPdfTable(Document document, String fileName, PdfWriter writer, List<String> fromFile) throws DocumentException {
        PdfPTable table = new PdfPTable(fromFile.get(0).split(",").length);

        help.addTableHeader(table, fromFile);
        help.addRows(table, fromFile);
        document.add(table);

        document.close();
        writer.close();
        return fileName;
    }
}
