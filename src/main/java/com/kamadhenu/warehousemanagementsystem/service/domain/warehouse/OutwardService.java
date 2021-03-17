package com.kamadhenu.warehousemanagementsystem.service.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Outward;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Outward domain service
 */
@Service("outwardService")
public class OutwardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutwardService.class);

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.db.warehouse.OutwardService outwardService;

    @Autowired
    private OutwardTruckService outwardTruckService;

    @Autowired
    private OutwardTruckBagService outwardTruckBagService;

    @Autowired
    private OutwardTruckQualityCheckService outwardTruckQualityCheckService;

    @Autowired
    private OutwardMadeUpBagService outwardMadeUpBagService;

    @Autowired
    private OutwardRemarkService outwardRemarkService;

    /**
     * Get domain outward based on database outward
     *
     * @param outwardModel
     * @return
     */
    public Outward get(com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Outward outwardModel) {
        Outward outward = new Outward();
        outward.setOutward(outwardModel);

        List<com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.OutwardTruck> outwardTruckList =
                new ArrayList<>();
        List<OutwardTruck> existingOutwardTruckList = outwardTruckService.getByOutward(outwardModel);
        for (OutwardTruck outwardTruck : existingOutwardTruckList) {
            List<OutwardTruckBag> outwardTruckBagList = outwardTruckBagService.getByOutwardTruck(outwardTruck);
            List<OutwardTruckQualityCheck> outwardTruckQualityCheckList =
                    outwardTruckQualityCheckService.getByOutwardTruck(outwardTruck
                    );
            com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.OutwardTruck outwardTruckModel =
                    new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.OutwardTruck();
            outwardTruckModel.setOutwardTruck(outwardTruck);
            outwardTruckModel.setOutwardTruckBagList(outwardTruckBagList);
            outwardTruckModel.setOutwardTruckQualityCheckList(outwardTruckQualityCheckList);
            outwardTruckList.add(outwardTruckModel);
        }
        outward.setOutwardTruckList(outwardTruckList);

        List<OutwardMadeUpBag> outwardMadeUpBagList = outwardMadeUpBagService.getByOutward(outwardModel);
        outward.setOutwardMadeUpBagList(outwardMadeUpBagList);

        List<OutwardRemark> outwardRemarkList = outwardRemarkService.getByOutward(outwardModel);
        outward.setOutwardRemarkList(outwardRemarkList);
        return outward;
    }
}
