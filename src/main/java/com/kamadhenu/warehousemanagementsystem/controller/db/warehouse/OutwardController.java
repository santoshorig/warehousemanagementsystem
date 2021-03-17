package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.OutwardForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.OutwardRemarkForm;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.DoService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.OutwardRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.OutwardService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

/**
 * <h1>Outward controller</h1>
 * <p>
 * This outward controller class provides the CRUD actions for outwards
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/outward")
public class OutwardController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutwardController.class);

    @Autowired
    private OutwardService outwardService;

    @Autowired
    private DoService doService;

    @Autowired
    private OutwardRemarkService outwardRemarkService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.OutwardService outwardDomainService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Outward index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Outward> outwardList = outwardService.getByStatusAndBusinessType();
        modelAndView.addObject("models", outwardList);
        modelAndView.addObject("friendlyStatusMap", constantService.getFRIENDLY_OUTWARD_STATUS());
        modelAndView.setViewName("admin/warehouse/outward/index");
        LOGGER.info("Outward index action returned {} models", outwardList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Outward edit action called");
        ModelAndView modelAndView = new ModelAndView();
        OutwardForm outwardForm = new OutwardForm();
        Outward model = new Outward();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Outward edit action called on existing model with id {}", identifier);
            Optional<Outward> outward = outwardService.get(identifier);
            if (outward.isPresent()) {
                model = outward.get();
            }
        } else {
            if (helperService.isOutwardGeneralUser()) {
                model.setCdfDate(new Date());
                model.setStatus(constantService.getOUTWARD_STATUS().get("underprocess"));
            } else {
                modelAndView.setViewName("redirect:/error/403");
                return modelAndView;
            }
        }

        outwardForm.setOutward(model);

        List<Do> doList = doService.getForOutward();
        List<BusinessType> businessTypeList = helperService.getUserBusinessType();
        modelAndView.addObject("model", outwardForm);
        modelAndView.addObject("dos", doList);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.setViewName("admin/warehouse/outward/edit");
        LOGGER.info("Outward edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") OutwardForm outwardForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Outward save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/outward/edit");
        if (!result.hasErrors()) {
            try {
                Outward outward = outwardForm.getOutward();
                Outward existingOutward = new Outward();
                if (outward.getId() != null) {
                    Optional<Outward> outwardModel = outwardService.get(outward.getId());
                    if (outwardModel.isPresent()) {
                        existingOutward = outwardModel.get();
                    }
                }

                if (outwardForm.getCdfDocument().isEmpty()) {
                    if (existingOutward.getId() != null && existingOutward.getCdfDocument() != null) {
                        outward.setCdfDocument(existingOutward.getCdfDocument());
                    }
                } else {
                    MultipartFile file = outwardForm.getCdfDocument();
                    Document document = documentService.store(file);
                    outward.setCdfDocument(document);
                }

                outwardService.edit(outward);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Outward save action successful");
                modelAndView.setViewName("redirect:/admin/outward-truck/index/outward/" + outward
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false);
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Outward save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Outward delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            outwardService.delete(id);
            LOGGER.info("Outward delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/outward/index");
        return modelAndView;
    }

    /**
     * Approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/approve/{id}"})
    public ModelAndView approve(@PathVariable("id") Integer id) {
        LOGGER.info("Outward approve action called with id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Optional<Outward> outward = outwardService.get(id);
        if (outward.isPresent()) {
            try {
                Outward outwardModel = outward.get();
                if (helperService.isOutwardGeneralUser()) {
                    outwardModel.setStatus(constantService.getOUTWARD_STATUS().get("pendingforreview"));
                    outwardService.edit(outwardModel);
                    modelAndView.setViewName("redirect:/admin/outward/index");
                } else {
                    modelAndView.setViewName("redirect:/error/403");
                }
            } catch (Exception e) {
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
                return modelAndView;
            }
        } else {
            modelAndView.setViewName("redirect:/error/403");
        }
        return modelAndView;
    }

    /**
     * Reviewer approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/review-approval/{id}"})
    public ModelAndView reviewApproval(@PathVariable("id") Integer id) {
        LOGGER.info("Outward review approval action called");
        ModelAndView modelAndView = new ModelAndView();
        OutwardRemarkForm outwardRemarkForm = new OutwardRemarkForm();
        com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Outward model =
                new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Outward();
        List<WarehouseStack> warehouseStackList = new ArrayList<>();
        Optional<Outward> outward = outwardService.get(id);
        if (outward.isPresent()) {
            try {
                Outward outwardModel = outward.get();
                model = outwardDomainService.get(outwardModel);
                outwardRemarkForm.setOutward(outwardModel);

                // Now get full stacks to mark non full
                Map<Integer, WarehouseStack> warehouseStackMap = new HashMap<>();
                for (com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.OutwardTruck outwardTruck : model
                        .getOutwardTruckList()) {
                    for (OutwardTruckBag outwardTruckBag : outwardTruck.getOutwardTruckBagList()) {
                        WarehouseStack warehouseStack =
                                outwardTruckBag.getDoInwardTruckBag().getInwardTruckBag().getWarehouseStack();
                        if (!warehouseStackMap.containsKey(warehouseStack.getId())) {
                            warehouseStackMap.put(warehouseStack.getId(), warehouseStack);
                        }
                    }
                }
                for (Map.Entry<Integer, WarehouseStack> warehouseStackEntry : warehouseStackMap.entrySet()) {
                    WarehouseStack warehouseStack = warehouseStackEntry.getValue();
                    if (warehouseStack.getFull() == true) {
                        warehouseStackList.add(warehouseStackEntry.getValue());
                    }
                }
            } catch (Exception e) {
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
                return modelAndView;
            }
        }

        List<String> statusList = constantService.getREVIEWER_APPROVAL_OUTWARD_STATUS();
        modelAndView.addObject("model", model);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("warehouseStacks", warehouseStackList);
        modelAndView.addObject("outwardRemarkForm", outwardRemarkForm);
        modelAndView.setViewName("admin/warehouse/outward/review-approval");
        LOGGER.info("Outward review approval action returned {} model", model);
        return modelAndView;
    }

    /**
     * Review approval save action
     *
     * @return the model and view
     */
    @PostMapping("/review-approval-save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") OutwardRemarkForm outwardRemarkForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Outward review approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/outward/index");
        if (!result.hasErrors()) {
            try {
                Outward outward = outwardRemarkForm.getOutward();
                outward.setStatus(outwardRemarkForm.getStatus());
                outwardService.edit(outward);
                OutwardRemark outwardRemark = new OutwardRemark();
                outwardRemark.setRemark(outwardRemarkForm.getRemark());
                outwardRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                outwardRemark.setRemarkDate(new Date());
                outwardRemark.setOutward(outward);
                outwardRemark.setUser(helperService.getLoggedInDbUser());
                outwardRemarkService.edit(outwardRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Warehouse business approval save action successful");
                modelAndView.setViewName("redirect:/admin/outward/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false);
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Outward review approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }
}
