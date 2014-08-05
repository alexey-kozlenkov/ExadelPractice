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
        header.createCell(1).setCellValue("Email");
        header.createCell(2).setCellValue("Role");
        header.createCell(3).setCellValue("State");
        header.createCell(4).setCellValue("Hire date");
        header.createCell(5).setCellValue("University");
        header.createCell(6).setCellValue("Faculty");
        header.createCell(7).setCellValue("Course");
        header.createCell(8).setCellValue("Group");
        header.createCell(9).setCellValue("Graduation date");
        header.createCell(10).setCellValue("Working hours");
        header.createCell(11).setCellValue("Billable");
        header.createCell(12).setCellValue("Role current project");
        header.createCell(13).setCellValue("Technology current poject");
        header.createCell(14).setCellValue("English level");
        for (int cellNumber = 0; cellNumber < header.getLastCellNum(); cellNumber++) {
            header.getCell(cellNumber).setCellStyle(styleHeader);
        }

        int rowCount = 1;
        for (User user : listOfUsers) {
            HSSFRow row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(user.getName());
            row.createCell(1).setCellValue(user.getEmail());
            row.createCell(2).setCellValue(user.getRole());
            row.createCell(3).setCellValue(user.getStudentInfo().getState());
            if (user.getStudentInfo().getHireDate() != null) {
                row.createCell(4).setCellValue(user.getStudentInfo().getHireDate().toString());
            }
            row.createCell(5).setCellValue(user.getStudentInfo().getUniversity());
            row.createCell(6).setCellValue(user.getStudentInfo().getFaculty());
            row.createCell(7).setCellValue(user.getStudentInfo().getCourse());
            row.createCell(8).setCellValue(user.getStudentInfo().getGroup());
            if (user.getStudentInfo().getGraduationDate() != null) {
                row.createCell(9).setCellValue(user.getStudentInfo().getGraduationDate());
            }
            row.createCell(10).setCellValue(user.getStudentInfo().getWorkingHours());
            if (user.getStudentInfo().getBillable() != null) {
                row.createCell(11).setCellValue(user.getStudentInfo().getBillable().toString());
            } else {
                row.createCell(11).setCellValue("false");
            }
            row.createCell(12).setCellValue(user.getStudentInfo().getRoleCurrentProject());
            row.createCell(13).setCellValue(user.getStudentInfo().getTechsCurrentProject());
            row.createCell(14).setCellValue(user.getStudentInfo().getEnglishLevel());
        }

        for (int cellNumber = 0; cellNumber < header.getLastCellNum(); cellNumber++) {
            sheet.autoSizeColumn(cellNumber);
        }
        sheet.setColumnWidth(13, 15000);
    }
}