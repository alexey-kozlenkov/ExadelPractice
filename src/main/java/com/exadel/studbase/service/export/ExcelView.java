package com.exadel.studbase.service.export;


import com.exadel.studbase.domain.impl.User;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class ExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment; filename=Report.xls");

        List<User> listOfUsers = (List<User>) model.get("users");

        // create a new Excel sheet
        HSSFSheet sheet = workbook.createSheet("Report");


        /*sheet.setDefaultColumnWidth(30);

        // create style for header cells
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Arial");
        style.setFillForegroundColor(HSSFColor.BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.WHITE.index);
        style.setFont(font);*/

        // create header row
        HSSFRow header = sheet.createRow(0);

        header.createCell(0).setCellValue("Name");

        header.createCell(1).setCellValue("Role");

        // create data rows
        int rowCount = 1;

        for (User user : listOfUsers) {
            HSSFRow row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(user.getName());
            row.createCell(1).setCellValue(user.getRole());
        }
    }
}