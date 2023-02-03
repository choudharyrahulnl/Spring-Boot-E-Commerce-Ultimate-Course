package com.api.ecommerce.services.impl;

import com.api.ecommerce.dtos.UserDto;
import com.api.ecommerce.services.ExcelExporterService;
import com.api.ecommerce.services.UserService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExcelExporterServiceImpl extends AbstractExporter implements ExcelExporterService {

    private UserService userService;

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelExporterServiceImpl(UserService userService) {
        this.userService = userService;
        this.workbook = new XSSFWorkbook();
    }

    @Override
    public void exportToExcel(HttpServletResponse response) {
        super.setResponseHeader(response, "xlsx", "application/octet-stream");

        writeHeaderLine();
        List<UserDto> all = userService.findAll();
        writeDataLines(all);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            log.error("ExportToExcel Error: " + e.getMessage());
        }

    }

    private void writeHeaderLine() {

        sheet = workbook.createSheet("Users");

        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        createCell(row, 0, "Id", cellStyle);
        createCell(row, 1, "Email", cellStyle);
        createCell(row, 2, "First Name", cellStyle);
        createCell(row, 3, "Last Name", cellStyle);
        createCell(row, 4, "Roles", cellStyle);
        createCell(row, 5, "Enabled", cellStyle);
    }

    private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle cellStyle) {
        XSSFCell cell = row.createCell(columnIndex);

        sheet.autoSizeColumn(columnIndex);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }

    }

    private void writeDataLines(List<UserDto> users) {
        int rowIndex = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        cellStyle.setFont(font);

        for (UserDto userDto : users) {
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;

            createCell(row, columnIndex++, userDto.getId().toString(), cellStyle);
            createCell(row, columnIndex++, userDto.getEmail(), cellStyle);
            createCell(row, columnIndex++, userDto.getFirstName(), cellStyle);
            createCell(row, columnIndex++, userDto.getLastName(), cellStyle);
            createCell(row, columnIndex++, userDto.getRoles().toString(), cellStyle);
            createCell(row, columnIndex++, userDto.getEnabled(), cellStyle);
        }
    }
}
