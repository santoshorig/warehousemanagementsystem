package com.kamadhenu.warehousemanagementsystem.service.domain.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoInwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoRemark;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Do;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.DoInwardTruckBagService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.DoRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Do domain service
 */
@Service("doService")
public class DoService {

    @Autowired
    private DoInwardTruckBagService doInwardTruckBagService;

    @Autowired
    private DoRemarkService doRemarkService;

    /**
     * Get domain do based on database do
     *
     * @param doModel
     * @return
     */
    public Do get(com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do doModel) {
        Do doDomainModel = new Do();
        doDomainModel.setDoModel(doModel);

        List<DoInwardTruckBag> doInwardTruckBagList = doInwardTruckBagService.getByDo(doModel);
        doDomainModel.setDoInwardTruckBagList(doInwardTruckBagList);

        List<DoRemark> doRemarkList = doRemarkService.getByDo(doModel);
        doDomainModel.setDoRemarkList(doRemarkList);

        return doDomainModel;
    }
}
