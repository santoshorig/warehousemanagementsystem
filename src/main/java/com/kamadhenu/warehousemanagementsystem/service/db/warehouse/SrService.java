package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.SrStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;

import java.util.List;
import java.util.Optional;

/**
 * Sr interface
 */
public interface SrService {

    /**
     * Get sr
     *
     * @param id
     * @return
     */
    Optional<Sr> get(Integer id);

    /**
     * Edit sr
     *
     * @param sr
     * @return
     */
    Sr edit(Sr sr);

    /**
     * Delete sr
     *
     * @param id
     */
    void delete(Integer id);
    
    /**
     * Check if SR can be edited
     *
     * @param sr
     * @return
     */
    Boolean canEdit(Sr sr);

    /**
     * Get all sr basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Sr> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all sr
     *
     * @return
     */
    List<Sr> getAll();

    /**
     * Get sr count
     *
     * @return
     */
    Long count();

    /**
     * Get srs by status and business type
     *
     * @return
     */
    List<Sr> getByStatusAndBusinessType();

    /**
     * Get srs by provided status and business type
     *
     * @param statusList
     * @return
     */
    List<Sr> getByStatusAndBusinessType(List<String> statusList);

    /**
     * Get sr by contract
     *
     * @param contract
     * @return
     */
    List<Sr> getByContract(Contract contract);

    /**
     * Get approved sr by contract
     *
     * @param contract
     * @return
     */
    List<Sr> getApprovedByContract(Contract contract);

    /**
     * Get sr count by status
     *
     * @return
     */
    List<SrStatusCount> getCountSrByStatus();
}
