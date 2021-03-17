package com.kamadhenu.warehousemanagementsystem.service.excel.client;

import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientPartner;
import com.kamadhenu.warehousemanagementsystem.model.domain.client.Client;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Client excel service
 */
@Service("clientExcelService")
public class ClientExcelService {

    private static final String FONT = "Arial";

    private static final short MAIN_HEADER_FONT_SIZE = 18;

    private static final short HEADER_FONT_SIZE = 12;

    private static final short DATA_FONT_SIZE = 8;

    private static final short MAIN_HEADER_FONT_COLOR = 0xc;

    private static final short HEADER_FONT_COLOR = 0xc;

    private static final short DATA_FONT_COLOR = 0xc;

    private static final String RISK_MAIN_HEADER = "Company Data";

    private static final List<String> CLIENT_HEADERS = new ArrayList<>(
            Arrays.asList(
                    "Client Constitution",
                    "Client Name",
                    "Date Of Incorporation",
                    "Client PAN Number",
                    "Client Type",
                    "Registered Address"
            )
    );

    private static final List<String> PARTNER_HEADERS = new ArrayList<>(
            Arrays.asList(
                    "Title",
                    "Name",
                    "Date Of Birth",
                    "PAN Number",
                    "Aadhar Number",
                    "Residential Address",
                    "Fathers Name",
                    "Mobile Number"
            )
    );

    @Autowired
    private HelperService helperService;

    /**
     * Create style
     *
     * @param workbook
     * @param fontName
     * @param fontSize
     * @param fontColor
     * @param bold
     * @param wrap
     * @return
     */
    private XSSFCellStyle createStyle(
            Workbook workbook,
            String fontName,
            short fontSize,
            short fontColor,
            Boolean bold,
            Boolean wrap
    ) {
        XSSFFont xssfFont = ((XSSFWorkbook) workbook).createFont();
        xssfFont.setFontName(fontName);
        xssfFont.setFontHeightInPoints(fontSize);
        xssfFont.setColor(fontColor);
        xssfFont.setBold(bold);
        XSSFCellStyle xssfCellStyle = (XSSFCellStyle) workbook.createCellStyle();
        xssfCellStyle.setFont(xssfFont);
        xssfCellStyle.setWrapText(wrap);
        return xssfCellStyle;
    }

    /**
     * Create cell
     *
     * @param cellId
     * @param row
     * @param xssfCellStyle
     * @param value
     * @return
     */
    private void createCell(Integer cellId, Row row, XSSFCellStyle xssfCellStyle, String value) {
        Cell cell = row.createCell(cellId);
        cell.setCellStyle(xssfCellStyle);
        cell.setCellValue(value);
    }

    /**
     * Get risk report from client
     *
     * @param client
     * @return
     */
    public Workbook riskReport(Client client) throws IOException {
        // Create workbook
        Workbook workbook = new XSSFWorkbook();

        // Create sheet
        Integer sheetId = 0;
        Sheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetId, client.getClient().getCode());

        // Main Header style
        XSSFCellStyle mainHeaderCellStyle =
                createStyle(workbook, FONT, MAIN_HEADER_FONT_SIZE, MAIN_HEADER_FONT_COLOR, true, false);
        // Main Header row
        Integer rowId = 2;
        Row row = sheet.createRow(rowId);
        // Main Header cells
        Integer cellId = 1;
        createCell(cellId, row, mainHeaderCellStyle, RISK_MAIN_HEADER);

        // Header style
        XSSFCellStyle headerCellStyle = createStyle(workbook, FONT, HEADER_FONT_SIZE, HEADER_FONT_COLOR, true, false);
        //Header cells
        rowId++;
        row = sheet.createRow(rowId);
        cellId = 1;
        for (String header : CLIENT_HEADERS) {
            createCell(cellId, row, headerCellStyle, header);
            cellId++;
        }

        // Data style
        XSSFCellStyle dataCellStyle = createStyle(workbook, FONT, DATA_FONT_SIZE, DATA_FONT_COLOR, true, false);
        rowId++;
        row = sheet.createRow(rowId);
        cellId = 1;

        createCell(cellId, row, dataCellStyle, client.getClient().getClientConstitution().getName());
        cellId++;

        createCell(cellId, row, dataCellStyle, client.getClient().getName());
        cellId++;

        String dateOfIncorporation = "";
        if (client.getClient().getDateOfIncorporation() != null) {
            dateOfIncorporation = helperService.dateToString(client.getClient().getDateOfIncorporation(), "dd-MM-yyyy");
        }

        createCell(
                cellId,
                row,
                dataCellStyle,
                dateOfIncorporation
        );
        cellId++;

        createCell(cellId, row, dataCellStyle, client.getClient().getPanNumber());
        cellId++;

        createCell(cellId, row, dataCellStyle, client.getClient().getClientType().getName());
        cellId++;

        createCell(cellId, row, dataCellStyle, client.getBusinessAddress());
        cellId++;

        // Client Header cells
        rowId = rowId + 5;
        row = sheet.createRow(rowId);
        cellId = 1;
        for (String header : PARTNER_HEADERS) {
            createCell(cellId, row, headerCellStyle, header);
            cellId++;
        }

        rowId++;
        for (ClientPartner clientPartner : client.getClientPartner()) {
            row = sheet.createRow(rowId);
            cellId = 1;

            createCell(cellId, row, dataCellStyle, clientPartner.getTitle());
            cellId++;

            createCell(cellId, row, dataCellStyle, clientPartner.getName());
            cellId++;

            createCell(cellId, row, dataCellStyle, clientPartner.getDOB());
            cellId++;

            createCell(cellId, row, dataCellStyle, clientPartner.getPanNumber());
            cellId++;

            createCell(cellId, row, dataCellStyle, clientPartner.getAadharNumber());
            cellId++;

            createCell(cellId, row, dataCellStyle, clientPartner.getFullAddress());
            cellId++;

            createCell(cellId, row, dataCellStyle, clientPartner.getFathersName());
            cellId++;

            createCell(cellId, row, dataCellStyle, clientPartner.getMobileNumber());
            cellId++;

            rowId++;
        }

        return workbook;
    }
}
