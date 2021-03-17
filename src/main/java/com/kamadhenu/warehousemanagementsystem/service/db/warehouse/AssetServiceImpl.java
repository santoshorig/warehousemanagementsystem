package com.kamadhenu.warehousemanagementsystem.service.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Asset;
import com.kamadhenu.warehousemanagementsystem.repository.warehouse.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    /**
     * Get asset
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Asset> get(Integer id) {
        return assetRepository.findById(id);
    }

    /**
     * Edit asset
     *
     * @param asset
     * @return
     */
    @Override
    public Asset edit(Asset asset) {
        return assetRepository.save(asset);
    }

    /**
     * Delete asset
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        assetRepository.deleteById(id);
    }

    /**
     * Get all asset basis page number and size
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public List<Asset> getAll(Integer pageNumber, Integer pageSize) {
        return assetRepository.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
    }

    /**
     * Get all asset
     *
     * @return
     */
    @Override
    public List<Asset> getAll() {
        return assetRepository.findAll();
    }

    /**
     * Get asset count
     *
     * @return
     */
    public Long count() {
        return assetRepository.count();
    }
}
