package com.kamadhenu.warehousemanagementsystem.service.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.UtilizationFactor;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseCommodity;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDetail;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Commodity;
import com.kamadhenu.warehousemanagementsystem.service.db.general.UtilizationFactorService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseCommodityService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Commodity domain service
 */
@Service("commodityService")
public class CommodityService {

    @Autowired
    private WarehouseCommodityService warehouseCommodityService;

    @Autowired
    private UtilizationFactorService utilizationFactorService;

    @Autowired
    private WarehouseDetailService warehouseDetailService;

    /**
     * Get commodity details
     *
     * @param warehouse
     * @return
     */
    public List<Commodity> getCommodityDetails(Warehouse warehouse) {
        List<Commodity> commodityList = new ArrayList<>();
        List<WarehouseDetail> warehouseDetailList = warehouseDetailService.getByWarehouse(warehouse);
        if (warehouseDetailList.size() > 0) {
            WarehouseDetail warehouseDetail = warehouseDetailList.get(0);
            List<WarehouseCommodity> warehouseCommodityList =
                    warehouseCommodityService.getByWarehouse(warehouse);
            for (WarehouseCommodity warehouseCommodity : warehouseCommodityList) {
                Commodity commodity = new Commodity();
                commodity.setWarehouseCommodity(warehouseCommodity);
                commodity.setWarehouse(warehouse);
                commodity.setWarehouseDetail(warehouseDetail);
                commodity.setCommodity(warehouseCommodity.getCommodity());
                List<UtilizationFactor> utilizationFactorList = utilizationFactorService
                        .getByCommodityAndBusinessType(warehouseCommodity.getCommodity(), warehouse.getBusinessType());
                if (utilizationFactorList.size() > 0) {
                    commodity.setUtilizationFactor(utilizationFactorList.get(0));
                }
                commodityList.add(commodity);
            }
        }

        return commodityList;
    }
}
