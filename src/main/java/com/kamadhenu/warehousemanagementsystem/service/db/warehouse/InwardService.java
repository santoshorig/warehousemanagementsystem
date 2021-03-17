package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.InwardStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Inward;

import java.util.List;
import java.util.Optional;

/**
 * Inward interface
 */
public interface InwardService {

    /**
     * Get inward
     *
     * @param id
     * @return
     */
    Optional<Inward> get(Integer id);

    /**
     * Edit inward
     *
     * @param inward
     * @return
     */
    Inward edit(Inward inward);

    /**
     * Delete inward
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all inward basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Inward> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all inward
     *
     * @return
     */
    List<Inward> getAll();

    /**
     * Get inward count
     *
     * @return
     */
    Long count();

    /**
     * Get inwards by status and business type
     *
     * @return
     */
    List<Inward> getByStatusAndBusinessType();

    /**
     * Get inwards by provided status and business type
     *
     * @param statusList
     * @return
     */
    List<Inward> getByStatusAndBusinessType(List<String> statusList);

    /**
     * Needs lot number
     *
     * @param inward
     * @return
     */
    Boolean needsLotNumber(Inward inward);

    /**
     * Get approved inwards by contract
     *
     * @param contract
     * @return
     */
    List<Inward> getApprovedByContract(Contract contract);

    /**
     * Get open inwards
     *
     * @return
     */
    List<Inward> getOpen();

    /**
     * Get inward count by status
     *
     * @return
     */
    List<InwardStatusCount> getCountInwardByStatus();

    /**
     * Get inward by cdd number
     *
     * @param cddNumber
     * @return
     */
    List<Inward> getByCddNumber(String cddNumber);
}
