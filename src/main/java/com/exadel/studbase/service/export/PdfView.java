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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class PdfView extends AbstractPdfView {
    private static int cellRightPadding = 20;

    private PdfPCell createCell(Object o, Font f) {
        PdfPCell cell = new PdfPCell();
        if (o != null) {
            cell.addElement(new Chunk(o.toString(), f));
        }
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPaddingRight(cellRightPadding);
        return cell;
    }

    @Override
    protected void buildPdfDocument(Map model, Document document,
                                    PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        User user = (User) model.get("user");

        String fileNameBeginning = user.getName() + " report - ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        String fileName = fileNameBeginning + simpleDateFormat.format(new Date()) + ".pdf";
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);

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
        table.addCell(createCell("Birth date", headerFont));
        table.addCell(createCell(user.getBirthdate(), contentFont));
        table.addCell(createCell("State", headerFont));
        table.addCell(createCell(user.getStudentInfo().getState(), contentFont));
        table.addCell(createCell("Email", headerFont));
        table.addCell(createCell(user.getEmail(), contentFont));
        table.addCell(createCell("Skype", headerFont));
        table.addCell(createCell(user.getSkype(), contentFont));
        table.addCell(createCell("Phone number", headerFont));
        table.addCell(createCell(user.getTelephone(), contentFont));
        document.add(table);

        document.add(new Paragraph(new Chunk("Education", FontFactory.getFont(FontFactory.TIMES_ITALIC, 16))));

        table = new PdfPTable(2);
        table.setWidths(width);
        table.addCell(createCell("Educational institution", headerFont));
        table.addCell(createCell(user.getStudentInfo().getUniversity(), contentFont));
        table.addCell(createCell("Faculty", headerFont));
        table.addCell(createCell(user.getStudentInfo().getFaculty(), contentFont));
        table.addCell(createCell("Speciality", headerFont));
        table.addCell(createCell(user.getStudentInfo().getSpeciality(), contentFont));
        table.addCell(createCell("Course", headerFont));
        table.addCell(createCell(user.getStudentInfo().getCourse(), contentFont));
        table.addCell(createCell("Group", headerFont));
        table.addCell(createCell(user.getStudentInfo().getGroup(), contentFont));
        table.addCell(createCell("Graduation date", headerFont));
        table.addCell(createCell(user.getStudentInfo().getGraduationDate(), contentFont));
        table.addCell(createCell("Average marks", headerFont));
        if (user.getStudentInfo().getTermMarks() != null) {
            table.addCell(createCell(user.getStudentInfo().getTermMarks().replaceAll(";", ";  "), contentFont));
        }
        document.add(table);

        if(user.getStudentInfo().getState().equals("working")) {
            document.add(new Paragraph(new Chunk("Exadel", FontFactory.getFont(FontFactory.TIMES_ITALIC, 16))));

            table = new PdfPTable(2);
            table.setWidths(width);
            table.addCell(createCell("Working hours", headerFont));
            table.addCell(createCell(user.getStudentInfo().getWorkingHours(), contentFont));
            table.addCell(createCell("Hire date", headerFont));
            table.addCell(createCell(user.getStudentInfo().getHireDate(), contentFont));
            table.addCell(createCell("Billable from", headerFont));
            PdfPCell cell = new PdfPCell();
            if (user.getStudentInfo().getBillable() != null) {
                cell.addElement(new Chunk(user.getStudentInfo().getBillable().toString(), contentFont));
            } else {
                cell.addElement(new Chunk("false", contentFont));
            }
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPaddingRight(cellRightPadding);
            table.addCell(cell);
            table.addCell(createCell("Working hours you want", headerFont));
            table.addCell(createCell(user.getStudentInfo().getWishesHoursNumber(), contentFont));
            table.addCell(createCell("Working from", headerFont));
            table.addCell(createCell(user.getStudentInfo().getCourseWhenStartWorking(), contentFont));
            table.addCell(createCell("Training before working", headerFont));
            table.addCell(createCell(user.getStudentInfo().getTrainingBeforeStartWorking(), contentFont));
            table.addCell(createCell("Training in Exadel", headerFont));
            table.addCell(createCell(user.getStudentInfo().getTrainingsInExadel(), contentFont));
            table.addCell(createCell("Current project", headerFont));
            table.addCell(createCell(user.getStudentInfo().getCurrentProject(), contentFont));
            table.addCell(createCell("Role in the project", headerFont));
            table.addCell(createCell(user.getStudentInfo().getRoleCurrentProject(), contentFont));
            table.addCell(createCell("Technologies used", headerFont));
            table.addCell(createCell(user.getStudentInfo().getTechsCurrentProject(), contentFont));
            document.add(table);
        }
    }
}