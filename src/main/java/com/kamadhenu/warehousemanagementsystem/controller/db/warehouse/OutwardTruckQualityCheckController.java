package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.OutwardTruckQualityCheckForm;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodityAcceptanceLimit;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.OutwardTruckQualityCheck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractCommodityAcceptanceLimitService;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.OutwardTruckQualityCheckService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.OutwardTruckService;
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
 * <h1>OutwardTruckQualityCheck controller</h1>
 * <p>
 * This outward truck quality check controller class provides the CRUD actions for outward truck quality checks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/outward-truck-quality-check")
public class OutwardTruckQualityCheckController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutwardTruckQualityCheckController.class);

    @Autowired
    private OutwardTruckQualityCheckService outwardTruckQualityCheckService;

    @Autowired
    private OutwardTruckService outwardTruckService;

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
    @RequestMapping(method = RequestMethod.GET, value = {"/index/outwardTruck/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("OutwardTruckQualityCheck index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<OutwardTruckQualityCheck> outwardTruckQualityCheckList = new ArrayList<>();
        Optional<OutwardTruck> outwardTruck = outwardTruckService.get(id);
        if (outwardTruck.isPresent()) {
            OutwardTruck outwardTruckModel = outwardTruck.get();
            outwardTruckQualityCheckList = outwardTruckQualityCheckService.getByOutwardTruck(outwardTruckModel);
            modelAndView.addObject("outwardTruck", outwardTruckModel);
        }
        if (outwardTruckQualityCheckList.size() > 0) {
            modelAndView.addObject("models", outwardTruckQualityCheckList);
            modelAndView.setViewName("admin/warehouse/outward-truck-quality-check/index");
        } else {
            modelAndView.setViewName("redirect:/admin/outward-truck-quality-check/edit/outwardTruck/" + id);
        }
        LOGGER.info("OutwardTruckQualityCheck index action returned {} models", outwardTruckQualityCheckList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/outwardTruck/{outwardTruckId}"})
    public ModelAndView edit(
            @PathVariable("outwardTruckId") Integer outwardTruckId
    ) {
        LOGGER.info("OutwardTruckQualityCheck edit action called");
        ModelAndView modelAndView = new ModelAndView();
        OutwardTruckQualityCheckForm model = new OutwardTruckQualityCheckForm();
        List<OutwardTruckQualityCheck> outwardTruckQualityCheckList = new ArrayList<>();
        Optional<OutwardTruck> outwardTruck = outwardTruckService.get(outwardTruckId);
        if (outwardTruck.isPresent()) {
            OutwardTruck outwardTruckModel = outwardTruck.get();
            List<ContractCommodityAcceptanceLimit> contractCommodityAcceptanceLimits =
                    contractCommodityAcceptanceLimitService
                            .getByContract(outwardTruckModel.getOutward().getDoModel().getContract());
            List<OutwardTruckQualityCheck> existingOutwardTruckQualityCheckList =
                    outwardTruckQualityCheckService.getByOutwardTruck(outwardTruckModel);
            for (ContractCommodityAcceptanceLimit contractCommodityAcceptanceLimit : contractCommodityAcceptanceLimits) {
                QualityParameter qualityParameter = contractCommodityAcceptanceLimit.getQualityParameter();
                OutwardTruckQualityCheck outwardTruckQualityCheck = new OutwardTruckQualityCheck();
                outwardTruckQualityCheck.setQualityParameter(qualityParameter);
                outwardTruckQualityCheck.setOutwardTruck(outwardTruckModel);
                for (OutwardTruckQualityCheck existingOutwardTruckQualityCheck : existingOutwardTruckQualityCheckList) {
                    if (existingOutwardTruckQualityCheck.getQualityParameter().getId() == qualityParameter.getId()) {
                        outwardTruckQualityCheck.setTestResult(existingOutwardTruckQualityCheck.getTestResult());
                        outwardTruckQualityCheck.setId(existingOutwardTruckQualityCheck.getId());
                    }
                }
                outwardTruckQualityCheckList.add(outwardTruckQualityCheck);
            }
            model.setOutwardTruckQualityCheckList(outwardTruckQualityCheckList);
            model.setOutwardTruck(outwardTruckModel);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/outward-truck-quality-check/edit");
        LOGGER.info("OutwardTruckQualityCheck edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") OutwardTruckQualityCheckForm outwardTruckQualityCheckForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("OutwardTruckQualityCheck save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/outward-truck-quality-check/edit");
        if (!result.hasErrors()) {
            try {
                if (outwardTruckQualityCheckForm.getQualityCheckDocument().isEmpty()) {
                    if (outwardTruckQualityCheckForm.getOutwardTruck().getId() != null) {
                        Optional<OutwardTruck> existingOutwardTruck =
                                outwardTruckService.get(outwardTruckQualityCheckForm.getOutwardTruck().getId());
                        if (existingOutwardTruck.isPresent()) {
                            outwardTruckQualityCheckForm.getOutwardTruck()
                                    .setQualityCheckDocument(existingOutwardTruck.get().getQualityCheckDocument());
                        }
                    }
                } else {
                    MultipartFile file = outwardTruckQualityCheckForm.getQualityCheckDocument();
                    Document document = documentService.store(file);
                    outwardTruckQualityCheckForm.getOutwardTruck().setQualityCheckDocument(document);
                }

                for (OutwardTruckQualityCheck outwardTruckQualityCheck : outwardTruckQualityCheckForm
                        .getOutwardTruckQualityCheckList()) {
                    outwardTruckQualityCheck.setOutwardTruck(outwardTruckQualityCheckForm.getOutwardTruck());
                    outwardTruckQualityCheckService.edit(outwardTruckQualityCheck);
                }
                outwardTruckService.edit(outwardTruckQualityCheckForm.getOutwardTruck());
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("OutwardTruckQualityCheck save action successful");
                modelAndView
                        .setViewName("redirect:/admin/outward-truck-quality-check/index/outwardTruck/" + outwardTruckQualityCheckForm
                                .getOutwardTruck()
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("OutwardTruckQualityCheck save action had errors {}", result.getAllErrors());
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
        LOGGER.info("OutwardTruckQualityCheck delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer outwardTruckId = null;
        try {
            Optional<OutwardTruckQualityCheck> outwardTruckQualityCheck = outwardTruckQualityCheckService.get(id);
            if (outwardTruckQualityCheck.isPresent()) {
                OutwardTruckQualityCheck model = outwardTruckQualityCheck.get();
                outwardTruckId = model.getOutwardTruck().getId();
                outwardTruckQualityCheckService.delete(id);
                LOGGER.info("OutwardTruckQualityCheck delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/outward-truck-quality-check/index/outwardTruck/" + outwardTruckId);
        return modelAndView;
    }

}
