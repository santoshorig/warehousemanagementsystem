package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosure;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseClosureRemark;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseClosureRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseClosureService;
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
 * <h1>WarehouseClosureRemark controller</h1>
 * <p>
 * This warehouse closure remark controller class provides the CRUD actions for warehouse closure remarks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-closure-remark")
public class WarehouseClosureRemarkController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseClosureRemarkController.class);

    @Autowired
    private WarehouseClosureRemarkService warehouseClosureRemarkService;

    @Autowired
    private WarehouseClosureService warehouseClosureService;

    @Autowired
    private HelperService helperService;

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("WarehouseClosureRemark delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            warehouseClosureRemarkService.delete(id);
            LOGGER.info("WarehouseClosureRemark delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-closure-remark/index");
        return modelAndView;
    }

    /**
     * Get remarks by warehouse closure action
     *
     * @param id the id of warehouse closure
     * @return json
     */
    @RequestMapping(value = "/getByWarehouseClosure/{id}", method = RequestMethod.GET)
    public ResponseEntity getByWarehouse(@PathVariable Integer id) {
        Optional<WarehouseClosure> warehouseClosure = warehouseClosureService.get(id);
        List<WarehouseClosureRemark> warehouseClosureRemarkList = new ArrayList<>();
        if (warehouseClosure.isPresent()) {
            WarehouseClosure model = warehouseClosure.get();
            warehouseClosureRemarkList = warehouseClosureRemarkService.getByWarehouseClosure(model);
        }
        return new ResponseEntity(warehouseClosureRemarkList, HttpStatus.OK);
    }
}
