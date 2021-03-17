package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.SrRemarkForm;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrInwardTruckQualityCheck;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrRemark;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.SrInwardTruckWeightedQualityCheck;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.SrInwardTruckQualityCheckService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.SrRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.SrService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
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
import java.util.*;

/**
 * <h1>SrInwardTruckQualityCheck controller</h1>
 * <p>
 * This sr inward truck quality check controller class provides the CRUD actions for sr inward truck quality checks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/sr-inward-truck-quality-check")
public class SrInwardTruckQualityCheckController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SrInwardTruckQualityCheckController.class);

    @Autowired
    private SrInwardTruckQualityCheckService srInwardTruckQualityCheckService;

    @Autowired
    private SrService srService;

    @Autowired
    private SrRemarkService srRemarkService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.SrService srDomainService;

    @Autowired
    private HelperService helperService;

    @Autowired
    private ConstantService constantService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/sr/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("SrInwardTruckQualityCheck index action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/sr-inward-truck-quality-check/edit/sr/" + id + "/0");
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/sr/{srId}/{revalidate}"})
    public ModelAndView edit(
            @PathVariable("srId") Integer srId, @PathVariable("revalidate") Optional<Boolean> revalidate
    ) {
        LOGGER.info("SrInwardTruckQualityCheck edit action called");
        ModelAndView modelAndView = new ModelAndView();
        SrRemarkForm model = new SrRemarkForm();
        Boolean revalidateSr = false;
        if (revalidate.isPresent()) {
            revalidateSr = revalidate.get();
        }
        Optional<Sr> sr = srService.get(srId);
        if (sr.isPresent()) {
            Sr srModel = sr.get();
            com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Sr srDomainModel =
                    srDomainService.get(srModel);
            List<SrInwardTruckQualityCheck> srInwardTruckQualityCheckList =
                    srInwardTruckQualityCheckService.getBySr(srModel);
            Map<QualityParameter, Double> weightedQualityCheckMap = srDomainModel.getWeightedQualityChecks();
            List<SrInwardTruckWeightedQualityCheck> srInwardTruckWeightedQualityCheckList = new ArrayList<>();
            for (QualityParameter qualityParameter : weightedQualityCheckMap.keySet()) {
                SrInwardTruckWeightedQualityCheck srInwardTruckWeightedQualityCheck =
                        new SrInwardTruckWeightedQualityCheck();
                srInwardTruckWeightedQualityCheck.setQualityParameter(qualityParameter);
                srInwardTruckWeightedQualityCheck.setValue(weightedQualityCheckMap.get(qualityParameter));
                srInwardTruckWeightedQualityCheck.setSrQcTestResult(weightedQualityCheckMap.get(qualityParameter));
                srInwardTruckWeightedQualityCheckList.add(srInwardTruckWeightedQualityCheck);
            }
            model.setSrInwardTruckWeightedQualityCheckList(srInwardTruckWeightedQualityCheckList);
            model.setSrInwardTruckQualityCheckList(srInwardTruckQualityCheckList);
            model.setSr(srModel);
            model.setRevalidate(revalidateSr);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/sr-inward-truck-quality-check/edit");
        LOGGER.info("SrInwardTruckQualityCheck edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") SrRemarkForm srRemarkForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("SrInwardTruckQualityCheck save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/sr-inward-truck-quality-check/edit");
        if (!result.hasErrors()) {
            try {
                for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srRemarkForm
                        .getSrInwardTruckQualityCheckList()) {
                    for (SrInwardTruckWeightedQualityCheck srInwardTruckWeightedQualityCheck : srRemarkForm
                            .getSrInwardTruckWeightedQualityCheckList()) {
                        if (srInwardTruckQualityCheck.getQualityParameter()
                                .equals(srInwardTruckWeightedQualityCheck.getQualityParameter())) {
                            srInwardTruckQualityCheck.setTestResult(srInwardTruckWeightedQualityCheck.getValue());
                            srInwardTruckQualityCheck.setSrQcTestResult(srInwardTruckWeightedQualityCheck.getSrQcTestResult());
                            srInwardTruckQualityCheckService.edit(srInwardTruckQualityCheck);
                        }
                    }
                }
                Sr sr = srRemarkForm.getSr();
                if (srRemarkForm.getReview()) {
                    sr.setStatus(constantService.getSR_STATUS().get("pendingforbusinessreview"));
                }
                srService.edit(sr);
                SrRemark srRemark = new SrRemark();
                srRemark.setRemark(srRemarkForm.getRemark());
                srRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                srRemark.setRemarkDate(new Date());
                srRemark.setSr(sr);
                srRemark.setUser(helperService.getLoggedInDbUser());
                srRemarkService.edit(srRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("SrInwardTruckQualityCheck save action successful");
                modelAndView
                        .setViewName("redirect:/admin/sr/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("SrInwardTruckQualityCheck save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

}
