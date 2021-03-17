package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.LrStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;

import java.util.List;
import java.util.Optional;

/**
 * Lr interface
 */
public interface LrService {

    /**
     * Get lr
     *
     * @param id
     * @return
     */
    Optional<Lr> get(Integer id);

    /**
     * Edit lr
     *
     * @param lr
     * @return
     */
    Lr edit(Lr lr);

    /**
     * Delete lr
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all lr basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<Lr> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all lr
     *
     * @return
     */
    List<Lr> getAll();

    /**
     * Get lr count
     *
     * @return
     */
    Long count();

    /**
     * Get lrs by status and business type
     *
     * @return
     */
    List<Lr> getByStatusAndBusinessType();

    /**
     * Get lrs by provided status and business type
     *
     * @param statusList
     * @return
     */
    List<Lr> getByStatusAndBusinessType(List<String> statusList);

    /**
     * Get lr by sr
     *
     * @param sr
     * @return
     */
    List<Lr> getBySr(Sr sr);

    /**
     * Get approved lr by sr
     *
     * @param sr
     * @return
     */
    List<Lr> getApprovedBySr(Sr sr);

    /**
     * Get lr count by status
     *
     * @return
     */
    List<LrStatusCount> getCountLrByStatus();
}