package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.InwardTruckQualityCheckForm;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodityAcceptanceLimit;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruckQualityCheck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractCommodityAcceptanceLimitService;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardTruckQualityCheckService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.InwardTruckService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>InwardTruckQualityCheck controller</h1>
 * <p>
 * This inward truck quality check controller class provides the CRUD actions for inward truck quality checks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/inward-truck-quality-check")
public class InwardTruckQualityCheckController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(InwardTruckQualityCheckController.class);

    @Autowired
    private InwardTruckQualityCheckService inwardTruckQualityCheckService;

    @Autowired
    private InwardTruckService inwardTruckService;

    @Autowired
    private ContractCommodityAcceptanceLimitService contractCommodityAcceptanceLimitService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/inwardTruck/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("InwardTruckQualityCheck index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<InwardTruckQualityCheck> inwardTruckQualityCheckList = new ArrayList<>();
        Optional<InwardTruck> inwardTruck = inwardTruckService.get(id);
        if (inwardTruck.isPresent()) {
            InwardTruck inwardTruckModel = inwardTruck.get();
            inwardTruckQualityCheckList = inwardTruckQualityCheckService.getByInwardTruck(inwardTruckModel);
            modelAndView.addObject("inwardTruck", inwardTruckModel);
        }
        if (inwardTruckQualityCheckList.size() > 0) {
            modelAndView.addObject("models", inwardTruckQualityCheckList);
            modelAndView.setViewName("admin/warehouse/inward-truck-quality-check/index");
        } else {
            modelAndView.setViewName("redirect:/admin/inward-truck-quality-check/edit/inwardTruck/" + id);
        }
        LOGGER.info("InwardTruckQualityCheck index action returned {} models", inwardTruckQualityCheckList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/inwardTruck/{inwardTruckId}"})
    public ModelAndView edit(
            @PathVariable("inwardTruckId") Integer inwardTruckId
    ) {
        LOGGER.info("InwardTruckQualityCheck edit action called");
        ModelAndView modelAndView = new ModelAndView();
        InwardTruckQualityCheckForm model = new InwardTruckQualityCheckForm();
        List<InwardTruckQualityCheck> inwardTruckQualityCheckList = new ArrayList<>();
        Optional<InwardTruck> inwardTruck = inwardTruckService.get(inwardTruckId);
        if (inwardTruck.isPresent()) {
            InwardTruck inwardTruckModel = inwardTruck.get();
            List<ContractCommodityAcceptanceLimit> contractCommodityAcceptanceLimits =
                    contractCommodityAcceptanceLimitService.getByContract(inwardTruckModel.getInward().getContract());
            List<InwardTruckQualityCheck> existingInwardTruckQualityCheckList =
                    inwardTruckQualityCheckService.getByInwardTruck(inwardTruckModel);
            for (ContractCommodityAcceptanceLimit contractCommodityAcceptanceLimit : contractCommodityAcceptanceLimits) {
                QualityParameter qualityParameter = contractCommodityAcceptanceLimit.getQualityParameter();
                InwardTruckQualityCheck inwardTruckQualityCheck = new InwardTruckQualityCheck();
                inwardTruckQualityCheck.setQualityParameter(qualityParameter);
                inwardTruckQualityCheck.setMinLimit(contractCommodityAcceptanceLimit.getMinLimit());
                inwardTruckQualityCheck.setMaxLimit(contractCommodityAcceptanceLimit.getMaxLimit());
                inwardTruckQualityCheck
                        .setTestResultValidation(contractCommodityAcceptanceLimit.getTestResultValidation());
                inwardTruckQualityCheck.setInwardTruck(inwardTruckModel);
                for (InwardTruckQualityCheck existingInwardTruckQualityCheck : existingInwardTruckQualityCheckList) {
                    if (existingInwardTruckQualityCheck.getQualityParameter().getId() == qualityParameter.getId()) {
                        inwardTruckQualityCheck.setTestResult(existingInwardTruckQualityCheck.getTestResult());
                        inwardTruckQualityCheck.setId(existingInwardTruckQualityCheck.getId());
                    }
                }
                inwardTruckQualityCheckList.add(inwardTruckQualityCheck);
            }
            model.setInwardTruckQualityCheckList(inwardTruckQualityCheckList);
            model.setInwardTruck(inwardTruckModel);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/inward-truck-quality-check/edit");
        LOGGER.info("InwardTruckQualityCheck edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") InwardTruckQualityCheckForm inwardTruckQualityCheckForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("InwardTruckQualityCheck save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/inward-truck-quality-check/edit");
        if (!result.hasErrors()) {
            try {
                if (inwardTruckQualityCheckForm.getQualityCheckDocument().isEmpty()) {
                    if (inwardTruckQualityCheckForm.getInwardTruck().getId() != null) {
                        Optional<InwardTruck> existingInwardTruck =
                                inwardTruckService.get(inwardTruckQualityCheckForm.getInwardTruck().getId());
                        if (existingInwardTruck.isPresent()) {
                            inwardTruckQualityCheckForm.getInwardTruck()
                                    .setQualityCheckDocument(existingInwardTruck.get().getQualityCheckDocument());
                        }
                    }
                } else {
                    MultipartFile file = inwardTruckQualityCheckForm.getQualityCheckDocument();
                    Document document = documentService.store(file);
                    inwardTruckQualityCheckForm.getInwardTruck().setQualityCheckDocument(document);
                }

                for (InwardTruckQualityCheck inwardTruckQualityCheck : inwardTruckQualityCheckForm
                        .getInwardTruckQualityCheckList()) {
                    inwardTruckQualityCheck.setInwardTruck(inwardTruckQualityCheckForm.getInwardTruck());
                    if (inwardTruckQualityCheck.getTestResultValidation()) {
                        if (inwardTruckQualityCheck.getTestResult() > inwardTruckQualityCheck
                                .getMinLimit() && inwardTruckQualityCheck.getTestResult() < inwardTruckQualityCheck
                                .getMaxLimit()) {
                            inwardTruckQualityCheckService.edit(inwardTruckQualityCheck);
                        }
                    } else {
                        inwardTruckQualityCheckService.edit(inwardTruckQualityCheck);
                    }
                }
                inwardTruckService.edit(inwardTruckQualityCheckForm.getInwardTruck());
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("InwardTruckQualityCheck save action successful");
                modelAndView
                        .setViewName("redirect:/admin/inward-truck-quality-check/index/inwardTruck/" + inwardTruckQualityCheckForm
                                .getInwardTruck()
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("InwardTruckQualityCheck save action had errors {}", result.getAllErrors());
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
        LOGGER.info("InwardTruckQualityCheck delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer inwardTruckId = null;
        try {
            Optional<InwardTruckQualityCheck> inwardTruckQualityCheck = inwardTruckQualityCheckService.get(id);
            if (inwardTruckQualityCheck.isPresent()) {
                InwardTruckQualityCheck model = inwardTruckQualityCheck.get();
                inwardTruckId = model.getInwardTruck().getId();
                inwardTruckQualityCheckService.delete(id);
                LOGGER.info("InwardTruckQualityCheck delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/inward-truck-quality-check/index/inwardTruck/" + inwardTruckId);
        return modelAndView;
    }

}
