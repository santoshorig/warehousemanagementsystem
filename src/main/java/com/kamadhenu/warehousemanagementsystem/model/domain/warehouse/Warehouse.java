package com.kamadhenu.warehousemanagementsystem.model.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.location.Location;
import com.kamadhenu.warehousemanagementsystem.model.db.location.Region;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Warehouse Domain Model
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Warehouse {

    @NotNull
    private com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse warehouse;

    @NotNull
    private String fullAddress;

    @NotNull
    private WarehouseDetail warehouseDetail;

    @NotNull
    private List<WarehouseInspection> warehouseInspectionList;

    @NotNull
    private List<WarehouseDocument> warehouseDocumentList;

    @NotNull
    private List<WarehouseManpower> warehouseManpowerList;

    @NotNull
    private List<WarehouseOwner> warehouseOwnerList;

    @NotNull
    private List<WarehouseRemark> warehouseRemarkList;

    @NotNull
    private List<Commodity> commodityList;

    @NotNull
    private List<WarehouseRiskScore> warehouseRiskScoreList;

    @NotNull
    private String finalRiskGrade;

    @NotNull
    private Region region;

    @NotNull
    private Location location;

    @NotNull
    private List<WarehouseWeighbridge> warehouseWeighbridgeList;

    /**
     * Get party status
     *
     * @return
     */
    public String getPartyStatus() {
        String partyStatus = "";
        if (warehouseOwnerList.size() > 0) {
            partyStatus = warehouseOwnerList.get(0).getStatusOfParty().getName();
        }
        return partyStatus;
    }
}
