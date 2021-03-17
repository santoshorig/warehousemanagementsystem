package com.kamadhenu.warehousemanagementsystem.service.excel.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseWeighbridge;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Warehouse Excel service
 */
@Service("warehouseExcelService")
public class WarehouseExcelService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseExcelService.class);

    @Autowired
    private HelperService helperService;

    @Autowired
    private ConstantService constantService;

    public Document getTermSheet(Warehouse warehouse) {
        Document document = new Document();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // First create a duplicate of the file
            String sourceFile = "docs/termsheet.xlsx";
            Resource sourceResource = new ClassPathResource(sourceFile);
            InputStream sourceInputStream = sourceResource.getInputStream();
            String destinationFile = "/tmp/termsheet-" + warehouse.getWarehouse().getId() + ".xlsx";
            Files.copy(
                    sourceInputStream,
                    new File(destinationFile).toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Create map for storing data
            Map<String, String> dataMap = new HashMap<>();

            // Load file
            InputStream inputStream = Files.newInputStream(Paths.get(destinationFile));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet("TERM");

            // Get data
            String takeoverType = warehouse.getWarehouse().getTakeoverType().getName();
            dataMap.put("D5", takeoverType);

            String state = warehouse.getLocation().getDistrict().getState().getName();
            dataMap.put("D7", state);

            String region = warehouse.getRegion().getName();
            dataMap.put("F7", region);

            String location = warehouse.getLocation().getName();
            dataMap.put("H7", location);

            String today = sdf.format(new Date());
            dataMap.put("J7", today);

            String firstSentToRiskAssessmentDate = "";
            if (warehouse.getWarehouse().getFirstSentToRiskAssessment() != null) {
                firstSentToRiskAssessmentDate = sdf.format(warehouse.getWarehouse().getFirstSentToRiskAssessment());
            }
            dataMap.put("E10", firstSentToRiskAssessmentDate);

            String reworkDate = "";
            if (warehouse.getWarehouse().getLastRework() != null) {
                reworkDate = sdf.format(warehouse.getWarehouse().getLastRework());
            }
            dataMap.put("F10", reworkDate);

            String firstSentToRiskApproverDate = "";
            if (warehouse.getWarehouse().getFirstSentToRiskApprover() != null) {
                firstSentToRiskApproverDate = sdf.format(warehouse.getWarehouse().getFirstSentToRiskApprover());
            }
            dataMap.put("G10", firstSentToRiskApproverDate);

            String firstSentToManagementApproverDate = "";
            if (warehouse.getWarehouse().getFirstSentToManagement() != null) {
                firstSentToManagementApproverDate = sdf.format(warehouse.getWarehouse().getFirstSentToManagement());
            }
            dataMap.put("H10", firstSentToManagementApproverDate);

            String lastSentToManagementApproverDate = "";
            if (warehouse.getWarehouse().getLastSentToManagement() != null) {
                lastSentToManagementApproverDate = sdf.format(warehouse.getWarehouse().getLastSentToManagement());
            }
            dataMap.put("I10", lastSentToManagementApproverDate);

            String warehouseName = warehouse.getWarehouse().getName();
            dataMap.put("D12", warehouseName);

            String agreementType = warehouse.getWarehouseDetail().getAgreementType();
            dataMap.put("E15", StringUtils.capitalize(agreementType));

            String licenseStatus = warehouse.getWarehouseDetail().getLicense();
            dataMap.put("E16", StringUtils.capitalize(licenseStatus));

            String partyStatus = warehouse.getPartyStatus();
            dataMap.put("E17", partyStatus);

            String locationType = "";
            if (constantService.getWAREHOUSE_LOCATION().containsKey(warehouse.getWarehouseDetail().getLocationType())) {
                locationType =
                        constantService.getWAREHOUSE_LOCATION().get(warehouse.getWarehouseDetail().getLocationType());
            }
            dataMap.put("E18", locationType);

            String structureInsuranceExpiry = "";
            if (warehouse.getWarehouseDetail().getStructureInsuranceExpiry() != null) {
                Date structureInsuranceExpiryDate = warehouse.getWarehouseDetail().getStructureInsuranceExpiry();
                structureInsuranceExpiry = sdf.format(structureInsuranceExpiryDate);
            }
            dataMap.put("E19", structureInsuranceExpiry);

            String weighbridge = "";
            List<WarehouseWeighbridge> warehouseWeighbridgeList = warehouse.getWarehouseWeighbridgeList();
            for (WarehouseWeighbridge warehouseWeighbridge : warehouseWeighbridgeList) {
                if (warehouseWeighbridge.getType()
                        .equalsIgnoreCase(constantService.getWEIGHBRIDGE_TYPE().get("Main"))) {
                    String weighbridgeName = warehouseWeighbridge.getWeighbridge().getName();
                    Double distance = warehouseWeighbridge.getDistanceFromWarehouse();
                    String warehouseType = warehouseWeighbridge.getWeighbridge().getWeighbridgeType().getName();
                    weighbridge = weighbridgeName + " " + distance + " mt. " + warehouseType;
                }
            }
            dataMap.put("E27", weighbridge);

            Double area = warehouse.getWarehouseDetail().getArea();
            dataMap.put("F29", area.toString());

            String utilizationFactors = "";
            String capacityUtilizations = "";
            List<Commodity> commodityList = warehouse.getCommodityList();
            Integer commodityStartRow = 39;
            for (Integer i = 0; i < commodityList.size(); i++) {
                Commodity commodity = commodityList.get(i);
                dataMap.put("D" + commodityStartRow.toString(), commodity.getCommodity().getName());
                dataMap.put("F" + commodityStartRow.toString(), commodity.getCapacityUtilization().toString());
                dataMap.put("G" + commodityStartRow.toString(), commodity.getSpotPrice().toString());
                dataMap.put("H" + commodityStartRow.toString(), commodity.getExpectedAUM().toString());
                dataMap.put("I" + commodityStartRow.toString(), commodity.getQualityCheckDetails());
                String commodityName = commodity.getCommodity().getName();
                Double commodityUtilizationFactor = commodity.getUtilizationFactor().getFactor();
                String capacityUtilization = commodity.getRiskCapacity();
                utilizationFactors += commodityName + " / " + commodityUtilizationFactor;
                capacityUtilizations += commodityName + " / " + capacityUtilization;
            }
            dataMap.put("F30", utilizationFactors);
            dataMap.put("F31", capacityUtilizations);

            // Update  in template
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                CellReference cellReference = new CellReference(entry.getKey());
                Row row = sheet.getRow(cellReference.getRow());
                Cell cell = row.getCell(cellReference.getCol());
                cell.setCellValue(entry.getValue());
            }
            inputStream.close();

            FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
            workbook.write(fileOutputStream);
            workbook.close();
            fileOutputStream.close();

            File contentFile = new File(destinationFile);
            byte[] fileContent = Files.readAllBytes(contentFile.toPath());
            document.setId(warehouse.getWarehouse().getId());
            document.setFileName(destinationFile);
            document.setFileType("application/octet-stream");
            document.setFileContent(fileContent);
            document.setFileSize(Math.toIntExact(contentFile.length()));
        } catch (IOException | EncryptedDocumentException
                | InvalidFormatException | NullPointerException e) {
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }
        return document;
    }
}
