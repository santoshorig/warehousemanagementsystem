package com.kamadhenu.warehousemanagementsystem.service.db.location;

import com.kamadhenu.warehousemanagementsystem.model.db.location.State;
import com.kamadhenu.warehousemanagementsystem.repository.location.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository stateRepository;

    /**
     * Get state
     *
     * @param id
     * @return
     */
    @Override
    public Optional<State> get(Integer id) {
        return stateRepository.findById(id);
    }

    /**
     * Edit state
     *
     * @param state
     * @return
     */
    @Override
    public State edit(State state) {
        return stateRepository.save(state);
    }

    /**
     * Delete state
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        stateRepository.deleteById(id);
    }

    /**
     * Get all state basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<State> getAll(Integer pageNumber, Integer pageSize) {
        return stateRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all state
     *
     * @return
     */
    @Override
    public List<State> getAll() {
        return stateRepository.findAll();
    }

    /**
     * Get state count
     *
     * @return
     */
    public Long count() {
        return stateRepository.count();
    }
}
