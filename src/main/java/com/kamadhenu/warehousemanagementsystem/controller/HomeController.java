package com.kamadhenu.warehousemanagementsystem.controller;

import com.kamadhenu.warehousemanagementsystem.model.domain.graph.Home;
import com.kamadhenu.warehousemanagementsystem.model.domain.location.Location;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
import com.kamadhenu.warehousemanagementsystem.service.db.hr.EmployeeService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.LocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
import com.kamadhenu.warehousemanagementsystem.service.domain.graph.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <h1>Home controller</h1>
 * <p>
 * This home controller class provides the entry point to a logged in user
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin")
public class HomeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    ClientService clientService;

    WarehouseService warehouseService;

    EmployeeService employeeService;

    LocationService locationService;

    com.kamadhenu.warehousemanagementsystem.service.domain.location.LocationService domainLocationService;

    HomeService homeService;

    @Autowired
    public HomeController(
            ClientService clientService,
            WarehouseService warehouseService,
            EmployeeService employeeService,
            LocationService locationService,
            com.kamadhenu.warehousemanagementsystem.service.domain.location.LocationService domainLocationService,
            HomeService homeService
    ) {
        this.clientService = clientService;
        this.warehouseService = warehouseService;
        this.employeeService = employeeService;
        this.locationService = locationService;
        this.domainLocationService = domainLocationService;
        this.homeService = homeService;
    }

    /**
     * Home action
     *
     * @return the model and view
     */
    @GetMapping("/home")
    public ModelAndView home() {
        LOGGER.info("Home action reached");
        ModelAndView modelAndView = new ModelAndView();
        Long clientCount = clientService.count();
        Long warehouseCount = warehouseService.count();
        Long employeeCount = employeeService.count();
        Long locationCount = locationService.count();
        List<Location> locationList = domainLocationService.getMapLocations();
        Home graphData = homeService.getGraphData();
        LOGGER.debug(
                "Counts for home action are - client count {} and warehouse count {} and employee count {} and location count {} and " +
                        "map location count {}",
                clientCount,
                warehouseCount,
                employeeCount,
                locationCount,
                locationList.size()
        );
        modelAndView.addObject("clientsCount", clientCount);
        modelAndView.addObject("warehousesCount", warehouseCount);
        modelAndView.addObject("employeesCount", employeeCount);
        modelAndView.addObject("locationsCount", locationCount);
        modelAndView.addObject("locationList", locationList);
        modelAndView.addObject("graphData", graphData);
        modelAndView.setViewName("admin/home/index");
        return modelAndView;
    }

}
