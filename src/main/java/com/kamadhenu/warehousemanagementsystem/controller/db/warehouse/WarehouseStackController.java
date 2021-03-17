package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseStackForm;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseStack;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseStackService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * <h1>WarehouseStack controller</h1>
 * <p>
 * This warehouse stack controller class provides the CRUD actions for warehouse stacks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-stack")
public class WarehouseStackController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseStackController.class);

    @Autowired
    private WarehouseStackService warehouseStackService;

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
        LOGGER.info("WarehouseStack index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<WarehouseStack> warehouseStackList = new ArrayList<>();
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            warehouseStackList = warehouseStackService.getByWarehouse(warehouseModel);
            modelAndView.addObject("warehouse", warehouseModel);
        }
        if (warehouseStackList.size() == 0) {
            modelAndView.setViewName("redirect:/admin/warehouse-stack/edit-all/warehouse/" + id);
        } else {
            modelAndView.addObject("models", warehouseStackList);
            modelAndView.setViewName("admin/warehouse/warehouse-stack/index");
        }
        LOGGER.info("WarehouseStack index action returned {} models", warehouseStackList.size());
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
        LOGGER.info("WarehouseStack edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseStack model = new WarehouseStack();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                LOGGER.info("WarehouseStack edit action called on existing model with id {}", identifier);
                Optional<WarehouseStack> warehouseStack = warehouseStackService.get(identifier);
                if (warehouseStack.isPresent()) {
                    model = warehouseStack.get();
                }
            }
            model.setWarehouse(warehouseModel);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/warehouse-stack/edit");
        LOGGER.info("WarehouseStack edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseStack warehouseStack,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseStack save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-stack/edit");
        if (!result.hasErrors()) {
            try {
                warehouseStackService.edit(warehouseStack);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseStack save action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-stack/index/warehouse/" + warehouseStack
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
            LOGGER.error("WarehouseStack save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseStack edit all action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseStackForm model = new WarehouseStackForm();
        Optional<Warehouse> warehouse = warehouseService.get(warehouseId);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            model.setWarehouse(warehouseModel);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/warehouse-stack/edit-all");
        LOGGER.info("WarehouseStack edit all action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save all action
     *
     * @return the model and view
     */
    @PostMapping("/save-all")
    public ModelAndView saveAll(
            @Valid @ModelAttribute("model") WarehouseStackForm warehouseStackForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseStack save all action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-stack/edit-all");
        if (!result.hasErrors()) {
            try {
                Warehouse warehouse = warehouseStackForm.getWarehouse();
                String shed = warehouseStackForm.getShed();
                String[] sheds = shed.split(",");
                String chamber = warehouseStackForm.getChamber();
                String[] chambers = chamber.split(",");
                String stack = warehouseStackForm.getStack();
                String[] stacks = stack.split(",");

                for (String s : sheds) {
                    for (String c : chambers) {
                        for (String st : stacks) {
                            WarehouseStack warehouseStack = new WarehouseStack();
                            warehouseStack.setShed(s);
                            warehouseStack.setChamber(c);
                            warehouseStack.setStack(st);
                            warehouseStack.setLength(warehouseStackForm.getLength());
                            warehouseStack.setBreadth(warehouseStackForm.getBreadth());
                            warehouseStack.setHeight(warehouseStackForm.getHeight());
                            warehouseStack.setFull(false);
                            warehouseStack.setWarehouse(warehouse);
                            warehouseStackService.edit(warehouseStack);
                        }
                    }
                }

                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseStack save all action successful");
                modelAndView.setViewName("redirect:/admin/warehouse-stack/index/warehouse/" + warehouse.getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("WarehouseStack save all action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseStack delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer warehouseId = null;
        try {
            Optional<WarehouseStack> warehouseStack = warehouseStackService.get(id);
            if (warehouseStack.isPresent()) {
                WarehouseStack model = warehouseStack.get();
                warehouseId = model.getWarehouse().getId();
                warehouseStackService.delete(id);
                LOGGER.info("WarehouseStack delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-stack/index/warehouse/" + warehouseId);
        return modelAndView;
    }

}
