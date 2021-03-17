package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.insurance.Insurance;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrRemark;
import com.kamadhenu.warehousemanagementsystem.model.domain.common.Remark;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Sr Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Sr {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr sr;

    @NotNull
    private List<SrInwardTruckQualityCheck> srInwardTruckQualityCheckList;

    @NotNull
    private List<SrRemark> srRemarkList;

    @NotNull
    private List<Insurance> insuranceList;

    @NotNull
    private List<com.kamadhenu.warehousemanagementsystem.model.domain.document.Document> clientDocumentList;

    @NotNull
    private List<com.kamadhenu.warehousemanagementsystem.model.domain.document.Document> warehouseDocumentList;

    @NotNull
    private List<com.kamadhenu.warehousemanagementsystem.model.domain.document.Document> contractDocumentList;

    @NotNull
    private List<com.kamadhenu.warehousemanagementsystem.model.domain.document.Document> inwardDocumentList;

    @NotNull
    private List<Remark> clientRemarkList;

    @NotNull
    private List<Remark> warehouseRemarkList;

    @NotNull
    private List<Remark> contractRemarkList;

    private List<String> ownInsuranceList;

    /**
     * Get dumping date
     *
     * @return
     */
    public List<Date> getDumpingDate() {
        List<Date> dateList = new ArrayList<>();
        for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {
            if (srInwardTruckQualityCheck.getDumpingDate() != null) {
                if (!dateList.contains(srInwardTruckQualityCheck.getDumpingDate())) {
                    dateList.add(srInwardTruckQualityCheck.getDumpingDate());
                }
            }
        }
        return dateList;
    }

    /**
     * Get total bags/bales/drums
     *
     * @return
     */
    public Integer getTotalBagsBalesDrums() {
        Integer totalBagsBalesDrums = 0;
        for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {
            totalBagsBalesDrums += srInwardTruckQualityCheck.getTotalBagsBalesDrums();
        }
        return totalBagsBalesDrums;
    }

    /**
     * Get total net weight
     *
     * @return
     */
    public Double getTotalNetWeight() {
        Double totalNetWeight = 0.0;
        List<InwardTruck> inwardTruckList = new ArrayList<>();
        for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {
            if (!inwardTruckList.contains(srInwardTruckQualityCheck.getSrInwardTruckQualityCheck().getInwardTruck())) {
                totalNetWeight += srInwardTruckQualityCheck.getTotalNetWeight();
                // So as to add each truck only once and not multiple times if multiple qc parameters are defined for the same truck
                inwardTruckList.add(srInwardTruckQualityCheck.getSrInwardTruckQualityCheck().getInwardTruck());
            }

        }
        return totalNetWeight;
    }

    /**
     * Get friendly total
     *
     * @return
     */
    public String getFriendlyTotal() {
        String total = "";
        for (Commodity commodity : sr.getContract().getCommodity()) {
            total += getTotalBagsBalesDrums() + " " + commodity.getStorageIn();
        }
        return total;
    }

    /**
     * Get friendly weight
     *
     * @return
     */
    public String getFriendlyWeight() {
        String total = "";
        for (Commodity commodity : sr.getContract().getCommodity()) {
            total += getTotalNetWeight() + " " + commodity.getUnitOfMeasure();
        }
        return total;
    }

    /**
     * Get cads
     *
     * @return
     */
    public String getCads() {
        List<String> cadList = new ArrayList<>();
        for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {
            if (!cadList.contains(srInwardTruckQualityCheck.getCad())) {
                cadList.add(srInwardTruckQualityCheck.getCad());
            }
        }
        return String.join(", ", cadList);
    }

    /**
     * Get weighbridges
     *
     * @return
     */
    public String getWeighbridges() {
        List<String> weighbridgeList = new ArrayList<>();
        for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {
            if (srInwardTruckQualityCheck.getWeighbridge() != null) {
                if (!weighbridgeList.contains(srInwardTruckQualityCheck.getWeighbridge())) {
                    weighbridgeList.add(srInwardTruckQualityCheck.getWeighbridge());
                }
            }
        }
        return String.join(", ", weighbridgeList);
    }

    /**
     * Get valuation
     *
     * @return
     */
    public String getValuation() {
        return String.format("%.2f", (sr.getSpotPrice() * getTotalNetWeight()));
    }

    /**
     * Get quality check documents
     *
     * @return
     */
    public Map<String, Document> getQualityCheckDocuments() {
        Map<String, Document> documentMap = new HashMap<>();
        List<Document> documentList = new ArrayList<>();
        for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {
            if (srInwardTruckQualityCheck.getQualityCheckDocument() != null) {
                documentMap.put(
                        srInwardTruckQualityCheck.getVehicleNumber(),
                        srInwardTruckQualityCheck.getQualityCheckDocument()
                );
            }
        }
        return documentMap;
    }

    /**
     * Get weighted quality checks
     *
     * @return
     */
    public Map<QualityParameter, Double> getWeightedQualityChecks() {
      Map<QualityParameter, Double> weightedQualityInwardWise = new HashMap<>();
      for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {
        QualityParameter qualityParameter =
            srInwardTruckQualityCheck.getSrInwardTruckQualityCheck().getQualityParameter();
        if (weightedQualityInwardWise.containsKey(qualityParameter)) {
          Double weight = weightedQualityInwardWise.get(qualityParameter);
          weight = weight + srInwardTruckQualityCheck.getTotalNetWeight() * srInwardTruckQualityCheck
              .getSrInwardTruckQualityCheck().getTestResult();
          weightedQualityInwardWise.put(qualityParameter, weight);
        } else {
          Double truckWeightQuality = srInwardTruckQualityCheck.getTotalNetWeight() * srInwardTruckQualityCheck
              .getSrInwardTruckQualityCheck().getTestResult();
          weightedQualityInwardWise
          .put(srInwardTruckQualityCheck.getSrInwardTruckQualityCheck().getQualityParameter(), truckWeightQuality);
        }
      }
      
      Double totalNetWeight = getTotalNetWeight();
      Map<QualityParameter, Double> weightedQualityCheckMap = new HashMap<>();
      if (totalNetWeight > 0) {
        weightedQualityInwardWise.entrySet().forEach(object -> {
          Double weightedQualityCheck = object.getValue() / totalNetWeight;
          BigDecimal bd = new BigDecimal(weightedQualityCheck).setScale(3, RoundingMode.HALF_UP);
          weightedQualityCheck = bd.doubleValue();
          weightedQualityCheckMap.put(object.getKey(), weightedQualityCheck);
        });
      }
      return weightedQualityCheckMap;
    }
}
