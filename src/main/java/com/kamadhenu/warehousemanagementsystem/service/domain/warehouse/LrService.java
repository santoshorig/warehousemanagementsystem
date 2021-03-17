package com.kamadhenu.warehousemanagementsystem.service.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.LrInwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.LrRemark;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.LrInwardTruckBagService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.LrRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Lr domain service
 */
@Service("lrService")
public class LrService {

    @Autowired
    private LrInwardTruckBagService lrInwardTruckBagService;

    @Autowired
    private LrRemarkService lrRemarkService;

    /**
     * Get domain lr based on database lr
     *
     * @param lrModel
     * @return
     */
    public Lr get(com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr lrModel) {
        Lr lr = new Lr();
        lr.setLr(lrModel);

        List<LrInwardTruckBag> lrInwardTruckBagList = lrInwardTruckBagService.getByLr(lrModel);
        lr.setLrInwardTruckBagList(lrInwardTruckBagList);

        List<LrRemark> lrRemarkList = lrRemarkService.getByLr(lrModel);
        lr.setLrRemarkList(lrRemarkList);

        return lr;
    }
}
