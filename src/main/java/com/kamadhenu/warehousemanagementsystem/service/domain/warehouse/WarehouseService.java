package com.kamadhenu.warehousemanagementsystem.service.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InspectionType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDetail;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseInspection;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseWeighbridge;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.WarehouseRiskScore;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionLocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.excel.warehouse.WarehouseExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Warehouse domain service
 */
@Service("warehouseService")
public class WarehouseService {

    @Autowired
    private WarehouseDetailService warehouseDetailService;

    @Autowired
    private WarehouseInspectionService warehouseInspectionService;

    @Autowired
    private WarehouseDocumentService warehouseDocumentService;

    @Autowired
    private WarehouseManpowerService warehouseManpowerService;

    @Autowired
    private WarehouseOwnerService warehouseOwnerService;

    @Autowired
    private WarehouseRemarkService warehouseRemarkService;

    @Autowired
    private WarehouseWeighbridgeService warehouseWeighbridgeService;

    @Autowired
    private RegionLocationService regionLocationService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private WarehouseExcelService warehouseExcelService;

    @Autowired
    private ConstantService constantService;

    /**
     * Get domain warehouse based on database warehouse
     *
     * @param warehouseModel
     * @return
     */
    public Warehouse get(com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse warehouseModel) {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouse(warehouseModel);
        List<WarehouseInspection> warehouseInspectionList = warehouseInspectionService.getByWarehouse(warehouseModel);
        warehouse.setWarehouseInspectionList(warehouseInspectionList);
        warehouse.setWarehouseDocumentList(warehouseDocumentService.getByWarehouse(warehouseModel));
        warehouse.setWarehouseManpowerList(warehouseManpowerService.getByWarehouse(warehouseModel));
        warehouse.setWarehouseOwnerList(warehouseOwnerService.getByWarehouse(warehouseModel));
        warehouse.setWarehouseRemarkList(warehouseRemarkService.getByWarehouse(warehouseModel));
        warehouse.setCommodityList(commodityService.getCommodityDetails(warehouseModel));

        List<WarehouseDetail> warehouseDetailList = warehouseDetailService.getByWarehouse(warehouseModel);
        if (warehouseDetailList.size() > 0) {
            warehouse.setWarehouseDetail(warehouseDetailList.get(0));
        }

        String address = warehouseModel.getFullAddress();
        Optional<RegionLocation> regionLocation = regionLocationService.get(warehouseModel.getRegLoc());
        if (regionLocation.isPresent()) {
            RegionLocation regionLocationModel = regionLocation.get();
            address = address + ", " + regionLocationModel.getFullAddress();
            warehouse.setFullAddress(address);
            warehouse.setRegion(regionLocationModel.getRegion());
            warehouse.setLocation(regionLocationModel.getLocation());
        }

        Map<InspectionType, Double> riskScores = new HashMap<>();
        for (WarehouseInspection warehouseInspection : warehouseInspectionList) {
            InspectionType inspectionType =
                    warehouseInspection.getInspectionOption().getInspection().getInspectionType();
            if (!riskScores.containsKey(inspectionType)) {
                riskScores.put(
                        inspectionType,
                        new Double(0)
                );
            }
            for (InspectionType inspectionTypeKey : riskScores.keySet()) {
                if (inspectionTypeKey.equals(inspectionType)) {
                    Double currentRiskScore = riskScores.get(inspectionTypeKey);
                    Double newRiskScore = currentRiskScore + warehouseInspection.getWeightedRiskScore();
                    riskScores.replace(inspectionTypeKey, newRiskScore);
                }
            }
        }

        List<WarehouseRiskScore> warehouseRiskScoreList = new ArrayList<>();
        for (InspectionType inspectionTypeKey : riskScores.keySet()) {
            WarehouseRiskScore warehouseRiskScore = new WarehouseRiskScore();
            warehouseRiskScore.setInspectionType(inspectionTypeKey);
            warehouseRiskScore.setWeightedRiskScore(riskScores.get(inspectionTypeKey));
            warehouseRiskScoreList.add(warehouseRiskScore);
        }
        warehouse.setWarehouseRiskScoreList(warehouseRiskScoreList);

        Double totalRiskScore = new Double(0);
        for (WarehouseRiskScore warehouseRiskScore : warehouseRiskScoreList) {
            totalRiskScore += warehouseRiskScore.getWeightedTotalScore();
        }
        String grade = constantService.getDEFAULT_RISK_SCORE_GRADE();
        for (String gradeKey : constantService.getRISK_SCORE_GRADE().keySet()) {
            if (constantService.getRISK_SCORE_GRADE().get(gradeKey).contains(totalRiskScore)) {
                grade = gradeKey;
                break;
            }
        }
        warehouse.setFinalRiskGrade(grade);

        List<WarehouseWeighbridge> warehouseWeighbridgeList =
                warehouseWeighbridgeService.getByWarehouse(warehouseModel);
        warehouse.setWarehouseWeighbridgeList(warehouseWeighbridgeList);

        return warehouse;
    }

    /**
     * Get term sheet
     *
     * @param warehouse
     * @return
     */
    public Document getTermSheet(com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse warehouse) {
        Warehouse warehouseModel = get(warehouse);
        return warehouseExcelService.getTermSheet(warehouseModel);
    }
}
