package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.insurance.Insurance;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Tender;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodity;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.TenderCommodityInsurance;
import com.kamadhenu.warehousemanagementsystem.service.db.insurance.InsuranceService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.TenderCommodityInsuranceService;
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
 * <h1>TenderCommodityInsurance controller</h1>
 * <p>
 * This tender add on service controller class provides the CRUD actions for tender add on services
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/tender-commodity-insurance")
public class TenderCommodityInsuranceController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenderCommodityInsuranceController.class);

    @Autowired
    private TenderCommodityInsuranceService tenderCommodityInsuranceService;

    @Autowired
    private TenderCommodityService tenderCommodityService;

    @Autowired
    private TenderService tenderService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/tender/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("TenderCommodityInsurance index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<TenderCommodityInsurance> tenderCommodityInsuranceList = new ArrayList<>();
        Optional<Tender> tender = tenderService.get(id);
        if (tender.isPresent()) {
            Tender tenderModel = tender.get();
            tenderCommodityInsuranceList = tenderCommodityInsuranceService.getByTender(tenderModel);
            modelAndView.addObject("tender", tenderModel);
            List<TenderCommodity> tenderCommodityList = tenderCommodityService.getByTender(tenderModel);
            Map<Integer, TenderCommodity> tenderCommodityMap = new HashMap<>();
            for (TenderCommodity tenderCommodity : tenderCommodityList) {
                tenderCommodityMap.put(tenderCommodity.getId(), tenderCommodity);
            }
            modelAndView.addObject("tenderCommodities", tenderCommodityMap);
            if (tenderCommodityInsuranceList.size() > 0) {
                modelAndView.addObject("models", tenderCommodityInsuranceList);
                modelAndView.setViewName("admin/warehouse/tender-commodity-insurance/index");
            } else {
                modelAndView.setViewName("redirect:/admin/tender-commodity-insurance/edit/tender/" + id);
                LOGGER.info(
                        "TenderCommodityInsurance index action returned {} models",
                        tenderCommodityInsuranceList.size()
                );
            }
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
        LOGGER.info("TenderCommodityInsurance edit action called");
        ModelAndView modelAndView = new ModelAndView();
        TenderCommodityInsurance model = new TenderCommodityInsurance();
        Optional<Tender> tender = tenderService.get(tenderId);
        List<TenderCommodity> tenderCommodityList = new ArrayList<>();
        if (tender.isPresent()) {
            Tender tenderModel = tender.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                Optional<TenderCommodityInsurance> tenderCommodityInsurance =
                        tenderCommodityInsuranceService.get(identifier);
                if (tenderCommodityInsurance.isPresent()) {
                    model = tenderCommodityInsurance.get();
                }
            }
            tenderCommodityList = tenderCommodityService.getByTender(tenderModel);
        }

        List<Insurance> insuranceList = insuranceService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("tenderCommodities", tenderCommodityList);
        modelAndView.addObject("insurances", insuranceList);
        modelAndView.setViewName("admin/warehouse/tender-commodity-insurance/edit");
        LOGGER.info("TenderCommodityInsurance edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") TenderCommodityInsurance tenderCommodityInsurance,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("TenderCommodityInsurance save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/tender-commodity-insurance/edit");
        if (!result.hasErrors()) {
            Optional<TenderCommodity> tenderCommodity =
                    tenderCommodityService.get(tenderCommodityInsurance.getTenderCommodity());
            TenderCommodity model = tenderCommodity.get();
            try {
                ///TODO fix this as due to bad relationship
                tenderCommodityInsuranceService.edit(tenderCommodityInsurance);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("TenderCommodityInsurance save action successful");
                modelAndView
                        .setViewName("redirect:/admin/tender-commodity-insurance/index/tender/" + model.getTender()
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView
                        .setViewName("redirect:/admin/tender-commodity-insurance/edit/tender/" + model.getTender()
                                .getId());
            }
        } else {
            LOGGER.error("TenderCommodityInsurance save action had errors {}", result.getAllErrors());
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
        LOGGER.info("TenderCommodityInsurance delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer tenderId = null;
        try {
            Optional<TenderCommodityInsurance> tenderCommodityInsurance = tenderCommodityInsuranceService.get(id);
            if (tenderCommodityInsurance.isPresent()) {
                TenderCommodityInsurance model = tenderCommodityInsurance.get();
                Optional<TenderCommodity> tenderCommodity = tenderCommodityService.get(model.getTenderCommodity());
                tenderId = tenderCommodity.get().getTender().getId();
                tenderCommodityInsuranceService.delete(id);
                LOGGER.info("TenderCommodityInsurance delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/tender-commodity-insurance/index/tender/" + tenderId);
        return modelAndView;
    }

}
