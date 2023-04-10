package com.bin.common.core.utils;

import com.bin.user.pojo.VO.ExcelOutcomeVO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class ExcelUtils {
    public static ByteArrayInputStream usersToExcel(List<ExcelOutcomeVO> outcomes) throws IOException {
        String[] COLUMNs = {"ID", "Name", "Email", "Phone"};
        try(
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ){
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Users");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            // CellStyle for Age
            CellStyle ageCellStyle = workbook.createCellStyle();
            ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));

            int rowIdx = 0;
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue("用户名");
            row.createCell(1).setCellValue("支出描述");
            row.createCell(2).setCellValue("支出金额");
            row.createCell(3).setCellValue("支出类型");
            row.createCell(4).setCellValue("交易日期");
            for (ExcelOutcomeVO outcome : outcomes) {
                row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(outcome.getUserName());
                row.createCell(1).setCellValue(outcome.getOutcomeName());
                row.createCell(2).setCellValue(outcome.getOutcome());
                row.createCell(3).setCellValue(outcome.getTypeName());
                row.createCell(4).setCellValue(outcome.getDate());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
