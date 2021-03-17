package com.kamadhenu.warehousemanagementsystem.service.db.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.State;

import java.util.List;
import java.util.Optional;

/**
 * State interface
 */
public interface StateService {

    /**
     * Get state
     *
     * @param id
     * @return
     */
    Optional<State> get(Integer id);

    /**
     * Edit state
     *
     * @param state
     * @return
     */
    State edit(State state);

    /**
     * Delete state
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * Get all state basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<State> getAll(Integer pageNumber, Integer pageSize);

    /**
     * Get all state
     *
     * @return
     */
    List<State> getAll();

    /**
     * Get state count
     *
     * @return
     */
    Long count();
}
