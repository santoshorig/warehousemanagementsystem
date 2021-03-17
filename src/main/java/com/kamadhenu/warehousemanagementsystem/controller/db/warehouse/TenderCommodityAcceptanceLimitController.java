package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodity;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodityAcceptanceLimit;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.QualityParameterService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.TenderCommodityAcceptanceLimitService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.TenderCommodityService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.TenderService;
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
 * <h1>TenderCommodityAcceptanceLimit controller</h1>
 * <p>
 * This tender commodity acceptance limit controller class provides the CRUD actions for tender commodity acceptance limits
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/tender-commodity-acceptance-limit")
public class TenderCommodityAcceptanceLimitController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenderCommodityAcceptanceLimitController.class);

    @Autowired
    private TenderCommodityAcceptanceLimitService tenderCommodityAcceptanceLimitService;

    @Autowired
    private TenderCommodityService tenderCommodityService;

    @Autowired
    private TenderService tenderService;

    @Autowired
    private QualityParameterService qualityParameterService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/tender/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("TenderCommodityAcceptanceLimit index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<TenderCommodityAcceptanceLimit> tenderCommodityAcceptanceLimitList = new ArrayList<>();
        Optional<Tender> tender = tenderService.get(id);
        if (tender.isPresent()) {
            Tender tenderModel = tender.get();
            tenderCommodityAcceptanceLimitList = tenderCommodityAcceptanceLimitService.getByTender(tenderModel);
            modelAndView.addObject("tender", tenderModel);
            List<TenderCommodity> tenderCommodityList = tenderCommodityService.getByTender(tenderModel);
            Map<Integer, TenderCommodity> tenderCommodityMap = new HashMap<>();
            for (TenderCommodity tenderCommodity : tenderCommodityList) {
                tenderCommodityMap.put(tenderCommodity.getId(), tenderCommodity);
            }
            modelAndView.addObject("tenderCommodities", tenderCommodityMap);
            if (tenderCommodityAcceptanceLimitList.size() > 0) {
                modelAndView.addObject("models", tenderCommodityAcceptanceLimitList);
                modelAndView.setViewName("admin/warehouse/tender-commodity-acceptance-limit/index");
            } else {
                modelAndView.setViewName("redirect:/admin/tender-commodity-acceptance-limit/edit/tender/" + id);
            }
            LOGGER.info(
                    "TenderCommodityAcceptanceLimit index action returned {} models",
                    tenderCommodityAcceptanceLimitList.size()
            );
        } else {
            ///TODO Fix this as cant make it work due to bad relationships
            modelAndView.setViewName("redirect:/admin/home");
        }

        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/tender/{tenderId}", "/edit/tender/{tenderId}/{id}"})
    public ModelAndView edit(@PathVariable("tenderId") Integer tenderId, @PathVariable("id") Optional<Integer> id) {
        LOGGER.info("TenderCommodityAcceptanceLimit edit action called");
        ModelAndView modelAndView = new ModelAndView();
        TenderCommodityAcceptanceLimit model = new TenderCommodityAcceptanceLimit();
        Optional<Tender> tender = tenderService.get(tenderId);
        List<TenderCommodity> tenderCommodityList = new ArrayList<>();
        if (tender.isPresent()) {
            Tender tenderModel = tender.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                Optional<TenderCommodityAcceptanceLimit> tenderCommodityAcceptanceLimit =
                        tenderCommodityAcceptanceLimitService.get(identifier);
                if (tenderCommodityAcceptanceLimit.isPresent()) {
                    model = tenderCommodityAcceptanceLimit.get();
                }
            }
            tenderCommodityList = tenderCommodityService.getByTender(tenderModel);
        }

        List<QualityParameter> qualityParameterList = qualityParameterService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("tenderCommodities", tenderCommodityList);
        modelAndView.addObject("qualityParameters", qualityParameterList);
        modelAndView.setViewName("admin/warehouse/tender-commodity-acceptance-limit/edit");
        LOGGER.info("TenderCommodityAcceptanceLimit edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") TenderCommodityAcceptanceLimit tenderCommodityAcceptanceLimit,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("TenderCommodityAcceptanceLimit save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/tender-commodity-acceptance-limit/edit");
        if (!result.hasErrors()) {
            Optional<TenderCommodity> tenderCommodity =
                    tenderCommodityService.get(tenderCommodityAcceptanceLimit.getTenderCommodity());
            TenderCommodity model = tenderCommodity.get();
            try {
                tenderCommodityAcceptanceLimitService.edit(tenderCommodityAcceptanceLimit);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("TenderCommodityAcceptanceLimit save action successful");
                modelAndView
                        .setViewName("redirect:/admin/tender-commodity-acceptance-limit/index/tender/" + model
                                .getTender()
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView
                        .setViewName("redirect:/admin/tender-commodity-acceptance-limit/edit/tender/" + model
                                .getTender()
                                .getId());
            }
        } else {
            LOGGER.error("TenderCommodityAcceptanceLimit save action had errors {}", result.getAllErrors());
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
        LOGGER.info("TenderCommodityAcceptanceLimit delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer tenderId = null;
        try {
            Optional<TenderCommodityAcceptanceLimit> tenderCommodityAcceptanceLimit =
                    tenderCommodityAcceptanceLimitService.get(id);
            if (tenderCommodityAcceptanceLimit.isPresent()) {
                TenderCommodityAcceptanceLimit model = tenderCommodityAcceptanceLimit.get();
                Optional<TenderCommodity> tenderCommodity = tenderCommodityService.get(model.getTenderCommodity());
                tenderId = tenderCommodity.get().getTender().getId();
                tenderCommodityAcceptanceLimitService.delete(id);
                LOGGER.info("TenderCommodityAcceptanceLimit delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/tender-commodity-acceptance-limit/index/tender/" + tenderId);
        return modelAndView;
    }

}
