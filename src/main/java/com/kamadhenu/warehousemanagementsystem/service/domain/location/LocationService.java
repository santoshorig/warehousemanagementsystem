package com.kamadhenu.warehousemanagementsystem.service.domain.location;

import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ClientLocationCount;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.WarehouseLocationCount;
import com.kamadhenu.warehousemanagementsystem.model.domain.location.Location;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionLocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Location domain service
 */
@Service("locationService")
public class LocationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

    private final com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService locationService;

    private final WarehouseService warehouseService;

    private final ClientService clientService;

    private final RegionLocationService regionLocationService;

    private final ConstantService constantService;

    @Autowired
    public LocationService(
            com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService locationService,
            WarehouseService warehouseService,
            ClientService clientService,
            RegionLocationService regionLocationService,
            ConstantService constantService
    ) {
        this.locationService = locationService;
        this.warehouseService = warehouseService;
        this.clientService = clientService;
        this.regionLocationService = regionLocationService;
        this.constantService = constantService;
    }

    /**
     * Get locations with mapped data
     *
     * @return
     */
    public List<Location> getMapLocations() {
        LOGGER.info("Getting data for map");
        // Get warehouse count map
        List<WarehouseLocationCount> warehouseLocationCountList = warehouseService.getCountWarehouseByLocation();
        Map<Integer, Integer> warehouseCountMap = warehouseLocationCountList.stream()
                .collect(Collectors.toMap(WarehouseLocationCount::getLocation, WarehouseLocationCount::getCount));
        // Get client count map
        List<ClientLocationCount> clientLocationCountList =
                clientService.getCountClientByLocation(constantService.getCLIENT_ADDRESS_TYPES().get("office"));
        Map<Integer, Integer> clientCountMap = clientLocationCountList.stream()
                .collect(Collectors.toMap(ClientLocationCount::getLocation, ClientLocationCount::getCount));

        // Get location list
        List<com.kamadhenu.warehousemanagementsystem.model.db.location.Location> locationModelList =
                locationService.getAll();
        List<Location> locationList = new ArrayList<>();
        for (com.kamadhenu.warehousemanagementsystem.model.db.location.Location location : locationModelList) {
            Location domainLocation = new Location();
            domainLocation.setLocation(location);
            if (warehouseCountMap.containsKey(location.getId())) {
                domainLocation.setWarehouseCount(warehouseCountMap.get(location.getId()));
            } else {
                domainLocation.setWarehouseCount(0);
            }
            if (clientCountMap.containsKey(location.getId())) {
                domainLocation.setClientCount(clientCountMap.get(location.getId()));
            } else {
                domainLocation.setClientCount(0);
            }
            if (domainLocation.hasData()) {
                locationList.add(domainLocation);
            }
        }
        return locationList;
    }
}
