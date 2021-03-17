package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.general.BagType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckBag;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.InwardTruckBagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InwardTruckBagServiceImpl implements InwardTruckBagService {

    @Autowired
    private InwardTruckBagRepository inwardTruckBagRepository;

    /**
     * Get inward truck bag
     *
     * @param id
     * @return
     */
    @Override
    public Optional<InwardTruckBag> get(Integer id) {
        return inwardTruckBagRepository.findById(id);
    }

    /**
     * Edit inward truck bag
     *
     * @param inwardTruckBag
     * @return
     */
    @Override
    public InwardTruckBag edit(InwardTruckBag inwardTruckBag) {
        return inwardTruckBagRepository.save(inwardTruckBag);
    }
    
    /**
     * bulk Edit inward truck bag
     *
     * @param inwardTruckBag
     * @return
     */
    @Override
    public List<InwardTruckBag> editBulk(List<InwardTruckBag> inwardTruckBag) {
      return inwardTruckBagRepository.saveAll(inwardTruckBag);
    }

    /**
     * Delete inward truck bag
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        inwardTruckBagRepository.deleteById(id);
    }

    /**
     * Get all inward truck bag basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<InwardTruckBag> getAll(Integer pageNumber, Integer pageSize) {
        return inwardTruckBagRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all inward truck bag
     *
     * @return
     */
    @Override
    public List<InwardTruckBag> getAll() {
        return inwardTruckBagRepository.findAll();
    }

    /**
     * Get inward truck bag count
     *
     * @return
     */
    public Long count() {
        return inwardTruckBagRepository.count();
    }

    /**
     * Get inward truck bag by inward truck
     *
     * @param inwardTruck
     * @return
     */
    public List<InwardTruckBag> getByInwardTruck(InwardTruck inwardTruck) {
        return inwardTruckBagRepository.findByInwardTruck(inwardTruck);
    }

    /**
     * Get inward truck bag by warehouse stack
     *
     * @param warehouseStack
     * @return
     */
    public List<InwardTruckBag> getByWarehouseStack(WarehouseStack warehouseStack) {
        return inwardTruckBagRepository.findByWarehouseStack(warehouseStack);
    }

    /**
     * Get inward truck bag available for do by inward truck and warehouse stack and bag type
     *
     * @param inwardTruck
     * @param warehouseStack
     * @param bagType
     * @return
     */
    public List<InwardTruckBag> getAvailableForDoByInwardTruckAndWarehouseStackAndBagType(
            InwardTruck inwardTruck,
            WarehouseStack warehouseStack,
            BagType bagType
    ) {
        return inwardTruckBagRepository
                .findByInwardTruckAndWarehouseStackAndBagTypeAndLienAndDoModelAndOutward(
                        inwardTruck,
                        warehouseStack,
                        bagType,
                        false,
                        false,
                        false
                );
    }
}
