package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseRemark;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>WarehouseRemark controller</h1>
 * <p>
 * This warehouse remark controller class provides the CRUD actions for warehouse remarks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-remark")
public class WarehouseRemarkController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseRemarkController.class);

    @Autowired
    private WarehouseRemarkService warehouseRemarkService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private HelperService helperService;

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("WarehouseRemark delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            warehouseRemarkService.delete(id);
            LOGGER.info("WarehouseRemark delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-remark/index");
        return modelAndView;
    }

    /**
     * Get remarks by warehouse action
     *
     * @param id the id of warehouse
     * @return json
     */
    @RequestMapping(value = "/getByWarehouse/{id}", method = RequestMethod.GET)
    public ResponseEntity getByWarehouse(@PathVariable Integer id) {
        Optional<Warehouse> warehouse = warehouseService.get(id);
        List<WarehouseRemark> warehouseRemarkList = new ArrayList<>();
        if (warehouse.isPresent()) {
            Warehouse model = warehouse.get();
            warehouseRemarkList = warehouseRemarkService.getByWarehouse(model);
        }
        return new ResponseEntity(warehouseRemarkList, HttpStatus.OK);
    }
}
