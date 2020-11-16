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

    PDFHelper help = new PDFHelper();
    Paragraph hr = new Paragraph(" ");
    Font headerFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
    LocalDate now = LocalDate.now();

    public void getOrderReport() {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("report/Order_" + now + ".pdf"));
            document.open();

            document.add(new Chunk("Order Repoert", headerFont));
            document.add(hr);

            List<String> fromFile = new Order().getReader().getFromFile();
            PdfPTable table = new PdfPTable(fromFile.get(0).split(",").length);

            help.addTableHeader(table, fromFile);
            help.addRows(table, fromFile);
            document.add(table);

            document.close();
            writer.close();
            System.out.println("Document Written Successfully");
        } catch (IOException | DocumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getFeedbackReport() {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("report/Feedback_" + now + ".pdf"));
            document.open();

            document.add(new Chunk("Feedback Repoert", headerFont));
            document.add(hr);

            List<String> fromFile = new Feedback().getReader().getFromFile();
            PdfPTable table = new PdfPTable(fromFile.get(0).split(",").length);

            help.addTableHeader(table, fromFile);
            help.addRows(table, fromFile);
            document.add(table);

            document.close();
            writer.close();
        } catch (IOException | DocumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getDeliveryReport() {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("report/Delivery_" + now + ".pdf"));
            document.open();

            document.add(new Chunk("Delivery Repoert", headerFont));
            document.add(hr);

            List<String> fromFile = new Delivery().getReader().getFromFile();
            PdfPTable table = new PdfPTable(fromFile.get(0).split(",").length);

            help.addTableHeader(table, fromFile);
            help.addRows(table, fromFile);
            document.add(table);

            document.close();
            writer.close();
        } catch (IOException | DocumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
