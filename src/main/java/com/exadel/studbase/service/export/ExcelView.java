package com.exadel.studbase.service.export;

import com.exadel.studbase.domain.impl.User;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ExcelView extends AbstractExcelView {
    public String fillCell(Object value) {
        if (value != null) {
            return value.toString();
        }
        return "";
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response)
            throws Exception {
        response.setContentType("application/vnd.ms-excel");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        Date now = new Date();
        String fileName = "Student report - " + simpleDateFormat.format(now) + ".xls";
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);

        List<User> listOfUsers = (List<User>) model.get("users");

        HSSFSheet sheet = workbook.createSheet();
        HSSFRow header = sheet.createRow(0);
        sheet.setDefaultColumnWidth(20);

        // create style for header cells
        CellStyle styleHeader = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        styleHeader.setFillForegroundColor(HSSFColor.SEA_GREEN.index);
        styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleHeader.setFont(font);

        header.createCell(0).setCellValue("Name");
        header.createCell(1).setCellValue("Birth date");
        header.createCell(2).setCellValue("State");
        header.createCell(3).setCellValue("Email");
        header.createCell(4).setCellValue("Skype");
        header.createCell(5).setCellValue("Phone number");
        header.createCell(6).setCellValue("Educational institution");
        header.createCell(7).setCellValue("Faculty");
        header.createCell(8).setCellValue("Speciality");
        header.createCell(9).setCellValue("Course");
        header.createCell(10).setCellValue("Group");
        header.createCell(11).setCellValue("Graduation date");
        header.createCell(12).setCellValue("Average marks");
        header.createCell(13).setCellValue("Working hours");
        header.createCell(14).setCellValue("Hire date");
        header.createCell(15).setCellValue("Billable from");
        header.createCell(16).setCellValue("Working hours you want");
        header.createCell(17).setCellValue("Working from");
        header.createCell(18).setCellValue("Training before working");
        header.createCell(19).setCellValue("Training in Exadel");
        header.createCell(20).setCellValue("Current project");
        header.createCell(21).setCellValue("Role in the project");
        header.createCell(22).setCellValue("Technologies used");
        for (int cellNumber = 0; cellNumber < header.getLastCellNum(); cellNumber++) {
            header.getCell(cellNumber).setCellStyle(styleHeader);
        }

        int rowCount = 1;
        for (User user : listOfUsers) {
            HSSFRow row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(fillCell(user.getName()));
            row.createCell(1).setCellValue(fillCell(user.getBirthdate()));
            row.createCell(2).setCellValue(fillCell(user.getStudentInfo().getState()));
            row.createCell(3).setCellValue(fillCell(user.getEmail()));
            row.createCell(4).setCellValue(fillCell(user.getSkype()));
            row.createCell(5).setCellValue(fillCell(user.getTelephone()));
            row.createCell(6).setCellValue(fillCell(user.getStudentInfo().getUniversity()));
            row.createCell(7).setCellValue(fillCell(user.getStudentInfo().getFaculty()));
            row.createCell(8).setCellValue(fillCell(user.getStudentInfo().getSpeciality()));
            row.createCell(9).setCellValue(fillCell(user.getStudentInfo().getCourse()));
            row.createCell(10).setCellValue(fillCell(user.getStudentInfo().getGroup()));
            row.createCell(11).setCellValue(fillCell(user.getStudentInfo().getGraduationDate()));
            row.createCell(12).setCellValue(fillCell(user.getStudentInfo().getTermMarks()));
            row.createCell(13).setCellValue(fillCell(user.getStudentInfo().getWorkingHours()));
            row.createCell(14).setCellValue(fillCell(user.getStudentInfo().getHireDate()));
            if (user.getStudentInfo().getBillable() != null) {
                row.createCell(15).setCellValue(user.getStudentInfo().getBillable().toString());
            } else {
                row.createCell(15).setCellValue("false");
            }
            row.createCell(16).setCellValue(fillCell(user.getStudentInfo().getWishesHoursNumber()));
            row.createCell(17).setCellValue(fillCell(user.getStudentInfo().getCourseWhenStartWorking()));
            row.createCell(18).setCellValue(fillCell(user.getStudentInfo().getTrainingBeforeStartWorking()));
            row.createCell(19).setCellValue(fillCell(user.getStudentInfo().getTrainingsInExadel()));
            row.createCell(20).setCellValue(fillCell(user.getStudentInfo().getCurrentProject()));
            row.createCell(21).setCellValue(fillCell(user.getStudentInfo().getRoleCurrentProject()));
            row.createCell(22).setCellValue(fillCell(user.getStudentInfo().getTechsCurrentProject()));
        }

        for (int cellNumber = 0; cellNumber < header.getLastCellNum(); cellNumber++) {
            sheet.autoSizeColumn(cellNumber);
        }
        sheet.setColumnWidth(22, 15000);
    }
}