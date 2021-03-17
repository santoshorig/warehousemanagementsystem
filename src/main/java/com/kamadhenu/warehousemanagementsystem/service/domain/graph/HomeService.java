package com.kamadhenu.warehousemanagementsystem.service.domain.graph;

import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ClientStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.client.ContractStatusCount;
import com.kamadhenu.warehousemanagementsystem.model.db.projection.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.model.domain.graph.Home;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Home domain service
 */
@Service("homeService")
public class HomeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeService.class);

    private final WarehouseService warehouseService;

    private final ClientService clientService;

    private final ContractService contractService;

    private final InwardService inwardService;

    private final SrService srService;

    private final LrService lrService;

    private final InwardTruckService inwardTruckService;

    private final ConstantService constantService;

    @Autowired
    public HomeService(
            WarehouseService warehouseService,
            ClientService clientService,
            ContractService contractService,
            InwardService inwardService,
            SrService srService,
            LrService lrService,
            InwardTruckService inwardTruckService,
            ConstantService constantService
    ) {
        this.warehouseService = warehouseService;
        this.clientService = clientService;
        this.contractService = contractService;
        this.inwardService = inwardService;
        this.srService = srService;
        this.lrService = lrService;
        this.inwardTruckService = inwardTruckService;
        this.constantService = constantService;
    }

    /**
     * Get graph data for home
     *
     * @return
     */
    public Home getGraphData() {
        LOGGER.info("Getting graph data for home");

        Home home = new Home();

        // Get client map
        List<ClientStatusCount> clientStatusCountList = clientService.getCountClientByStatus();
        Map<String, Integer> clientCountMap = clientStatusCountList.stream()
                .collect(Collectors.toMap(ClientStatusCount::getStatus, ClientStatusCount::getCount));
        home.setClientStatusMap(clientCountMap);

        // Get warehouse map
        List<WarehouseStatusCount> warehouseStatusCountList = warehouseService.getCountWarehouseByStatus();
        Map<String, Integer> warehouseCountMap = warehouseStatusCountList.stream()
                .collect(Collectors.toMap(WarehouseStatusCount::getStatus, WarehouseStatusCount::getCount));
        home.setWarehouseStatusMap(warehouseCountMap);

        // Get contract map
        List<ContractStatusCount> contractStatusCountList = contractService.getCountContractByStatus();
        Map<String, Integer> contractCountMap = contractStatusCountList.stream()
                .collect(Collectors.toMap(ContractStatusCount::getStatus, ContractStatusCount::getCount));
        home.setContractStatusMap(contractCountMap);

        // Get inward map
        List<InwardStatusCount> inwardStatusCountList = inwardService.getCountInwardByStatus();
        Map<String, Integer> inwardCountMap = inwardStatusCountList.stream()
                .collect(Collectors.toMap(InwardStatusCount::getStatus, InwardStatusCount::getCount));
        home.setInwardStatusMap(inwardCountMap);

        // Get sr map
        List<SrStatusCount> srStatusCountList = srService.getCountSrByStatus();
        Map<String, Integer> srCountMap = srStatusCountList.stream()
                .collect(Collectors.toMap(SrStatusCount::getStatus, SrStatusCount::getCount));
        home.setSrStatusMap(srCountMap);

        // Get lr map
        List<LrStatusCount> lrStatusCountList = lrService.getCountLrByStatus();
        Map<String, Integer> lrCountMap = lrStatusCountList.stream()
                .collect(Collectors.toMap(LrStatusCount::getStatus, LrStatusCount::getCount));
        home.setLrStatusMap(lrCountMap);

        // Get dates for inward and outward graph
        Date toDate = new Date();
        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(constantService.getInwardOutwardDaysBeforeForGraph()));
        Date fromDate = Date.from(before);

        // Formatter for output
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Get inward and outward truck map
        List<ReportingDateWeightCount> reportingDateWeightCountList =
                inwardTruckService
                        .getSumWeightByDateAndStatus(constantService.getAPPROVED_INWARD_STATUS(), fromDate, toDate);
        Map<String, Double> inwardQuantityMap = new HashMap<>();
        for (ReportingDateWeightCount reportingDateWeightCount : reportingDateWeightCountList) {
            inwardQuantityMap
                    .put(
                            simpleDateFormat.format(reportingDateWeightCount.getReportingDate()),
                            reportingDateWeightCount.getWeight()
                    );
        }
        home.setInwardQuantityMap(inwardQuantityMap);

        ///TODO Change once ouwtward is ready
        Map<String, Double> outwardQuantityMap = new HashMap<>();
        for (ReportingDateWeightCount reportingDateWeightCount : reportingDateWeightCountList) {
            outwardQuantityMap
                    .put(
                            simpleDateFormat.format(reportingDateWeightCount.getReportingDate()),
                            reportingDateWeightCount.getWeight() + 500
                    );
        }
        home.setOutwardQuantityMap(outwardQuantityMap);

        // Now setup combined map
        Map<String, List<Double>> inwardOutwardQuantityMap = new HashMap<>();
        // For inward
        for (Map.Entry<String, Double> entry : inwardQuantityMap.entrySet()) {
            if (!inwardOutwardQuantityMap.containsKey(entry.getKey())) {
                inwardOutwardQuantityMap.put(entry.getKey(), new ArrayList<>());
            }
            // Once for inward
            inwardOutwardQuantityMap.get(entry.getKey()).add(entry.getValue());
            // Assume 0 for outward for now
            inwardOutwardQuantityMap.get(entry.getKey()).add(0.0);
        }
        // For outward
        for (Map.Entry<String, Double> entry : outwardQuantityMap.entrySet()) {
            // If it doesn't exist then assume 0 for inward
            if (!inwardOutwardQuantityMap.containsKey(entry.getKey())) {
                inwardOutwardQuantityMap.put(entry.getKey(), new ArrayList<>());
                // Assume 0 for inward for now
                inwardOutwardQuantityMap.get(entry.getKey()).add(0.0);
                // Once for outward
                inwardOutwardQuantityMap.get(entry.getKey()).add(entry.getValue());
            } else {
                // replace for outward
                inwardOutwardQuantityMap.get(entry.getKey()).set(1, entry.getValue());
            }
        }
        home.setInwardOutwardQuantityMap(inwardOutwardQuantityMap);

        return home;
    }
}
