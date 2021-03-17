package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseCadForm;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseCad;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseCadService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>WarehouseCad controller</h1>
 * <p>
 * This warehouse cad controller class provides the CRUD actions for warehouse cads
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-cad")
public class WarehouseCadController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseCadController.class);

    @Autowired
    private WarehouseCadService warehouseCadService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/warehouse/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("WarehouseCad index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WarehouseCad> warehouseCadList = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            warehouseCadList = warehouseCadService.getByWarehouse(warehouseModel);
            modelAndView.addObject("warehouse", warehouseModel);
        }
        if (warehouseCadList.size() > 0) {
            modelAndView.addObject("models", warehouseCadList);
            modelAndView.setViewName("admin/warehouse/warehouse-cad/index");
        } else {
            modelAndView.setViewName("redirect:/admin/warehouse-cad/edit-all/warehouse/" + id);
        }
        LOGGER.info("WarehouseCad index action returned {} models", warehouseCadList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/warehouse/{warehouseId}", "/edit/warehouse/{warehouseId}/{id}"})
    public ModelAndView edit(
            @PathVariable("warehouseId") Integer warehouseId,
            @PathVariable("id") Optional<Integer> id
    ) {
        LOGGER.info("WarehouseCad edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseCad model = new WarehouseCad();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("WarehouseCad edit action called on existing model with id {}", identifier);
                Optional<WarehouseCad> warehouseCad = warehouseCadService.get(identifier);
                if (warehouseCad.isPresent()) {
                    model = warehouseCad.get();
                }
            } else {
                model.setUsed(false);
            }
            model.setWarehouse(warehouseModel);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/warehouse-cad/edit");
        LOGGER.info("WarehouseCad edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseCad warehouseCad,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseCad save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-cad/edit");
        if (!result.hasErrors()) {
            try {
                warehouseCadService.edit(warehouseCad);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseCad save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-cad/index/warehouse/" + warehouseCad
                        .getWarehouse()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("WarehouseCad save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Edit all action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit-all/warehouse/{warehouseId}"})
    public ModelAndView editAll(
            @PathVariable("warehouseId") Integer warehouseId
    ) {
        LOGGER.info("WarehouseCad edit all action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseCadForm model = new WarehouseCadForm();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            model.setWarehouse(warehouseModel);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/warehouse-cad/edit-all");
        LOGGER.info("WarehouseCad edit all action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save all action
     *
     * @return the model and view
     */
    @PostMapping("/save-all")
    public ModelAndView saveAll(
            @Valid @ModelAttribute("model") WarehouseCadForm warehouseCadForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseCad save all action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-cad/edit-all");
        if (!result.hasErrors()) {
            try {
                Warehouse warehouse = warehouseCadForm.getWarehouse();
                Integer bookNumber = warehouseCadForm.getBookNumber();
                Integer startSerialNumber = warehouseCadForm.getStartSerialNumber();
                Integer endSerialNumber = warehouseCadForm.getEndSerialNumber();
                if (bookNumber > 0 && startSerialNumber > 0 && endSerialNumber > 0 && endSerialNumber > startSerialNumber) {
                    for (int i = startSerialNumber; i <= endSerialNumber; i++) {
                        WarehouseCad warehouseCad = new WarehouseCad();
                        warehouseCad.setWarehouse(warehouse);
                        warehouseCad.setBookNumber(bookNumber);
                        warehouseCad.setSerialNumber(i);
                        warehouseCad.setUsed(false);
                        try {
                            warehouseCadService.edit(warehouseCad);
                        } catch (DataIntegrityViolationException e) {
                            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                            LOGGER.error(e.getLocalizedMessage());
                            LOGGER.debug(helperService.stackTraceToString(e));
                        }
                    }
                }

                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseCad save all action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-cad/index/warehouse/" + warehouse.getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("WarehouseCad save all action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("WarehouseCad delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer warehouseId = null;
        try {
            Optional<WarehouseCad> warehouseCad = warehouseCadService.get(id);
            if (warehouseCad.isPresent()) {
                WarehouseCad model = warehouseCad.get();
                warehouseId = model.getWarehouse().getId();
                warehouseCadService.delete(id);
                LOGGER.info("WarehouseCad delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-cad/index/warehouse/" + warehouseId);
        return modelAndView;
    }

}
