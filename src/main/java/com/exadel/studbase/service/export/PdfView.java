package com.exadel.studbase.service.export;

import com.exadel.studbase.domain.impl.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class PdfView extends AbstractPdfView {
    private static int cellLeftPadding = 20;


    private PdfPCell createCell(Object o, Font f) {
        PdfPCell cell = new PdfPCell();
        if (o != null) {
            cell.addElement(new Chunk(o.toString(), f));
        }
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPaddingRight(cellLeftPadding);
        return cell;
    }

    @Override
    protected void buildPdfDocument(Map model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        User user = (User) model.get("user");

        Paragraph header = new Paragraph(new Chunk(
                user.getName(),
                FontFactory.getFont(FontFactory.HELVETICA, 24)));
        document.add(header);

        document.add(new Paragraph(new Chunk("Manual information", FontFactory.getFont(FontFactory.TIMES_ITALIC, 16))));

        PdfPTable table = new PdfPTable(2);
        int width[] = {120, 200};

        Font headerFont = new Font(FontFactory.getFont(FontFactory.TIMES_ITALIC, 14));
        Font contentFont = new Font(FontFactory.getFont(FontFactory.HELVETICA, 14));


        table.setWidths(width);
        table.addCell(createCell("Name", headerFont));
        table.addCell(createCell(user.getName(), contentFont));
        table.addCell(createCell("Email", headerFont));
        table.addCell(createCell(user.getEmail(), contentFont));
        document.add(table);

        document.add(new Paragraph(new Chunk("Education", FontFactory.getFont(FontFactory.TIMES_ITALIC, 16))));

        table = new PdfPTable(2);
        table.setWidths(width);
        table.addCell(createCell("University", headerFont));
        table.addCell(createCell(user.getStudentInfo().getUniversity(), contentFont));
        table.addCell(createCell("Faculty", headerFont));
        table.addCell(createCell(user.getStudentInfo().getFaculty(), contentFont));
        table.addCell(createCell("Speciality", headerFont));
        table.addCell(createCell(user.getStudentInfo().getSpeciality(), contentFont));
        table.addCell(createCell("Cource", headerFont));
        table.addCell(createCell(user.getStudentInfo().getCourse(), contentFont));
        table.addCell(createCell("Group", headerFont));
        table.addCell(createCell(user.getStudentInfo().getGroup(), contentFont));
        table.addCell(createCell("Graduation date", headerFont));
        table.addCell(createCell(user.getStudentInfo().getGraduationDate(), contentFont));
        document.add(table);

        document.add(new Paragraph(new Chunk("Exadel", FontFactory.getFont(FontFactory.TIMES_ITALIC, 16))));

        table = new PdfPTable(2);
        table.setWidths(width);
        table.addCell(createCell("Working hours", headerFont));
        table.addCell(createCell(user.getStudentInfo().getWorkingHours(), contentFont));
        table.addCell(createCell("Hire date", headerFont));
        table.addCell(createCell(user.getStudentInfo().getHireDate(), contentFont));
        table.addCell(createCell("Billable", headerFont));
        PdfPCell cell = new PdfPCell();
        if (user.getStudentInfo().getBillable() != null) {
            cell.addElement(new Chunk(user.getStudentInfo().getBillable().toString(), contentFont));
        } else {
            cell.addElement(new Chunk("false", contentFont));
        }
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPaddingRight(cellLeftPadding);
        table.addCell(cell);
        table.addCell(createCell("Role in the current project", headerFont));
        table.addCell(createCell(user.getStudentInfo().getRoleCurrentProject(), contentFont));
        table.addCell(createCell("Technologies used in the current project", headerFont));
        table.addCell(createCell(user.getStudentInfo().getTechsCurrentProject(), contentFont));
        document.add(table);
    }
}