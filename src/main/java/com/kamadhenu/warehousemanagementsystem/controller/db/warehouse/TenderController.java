package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.service.db.general.BusinessTypeService;
import com.kamadhenu.warehousemanagementsystem.service.db.general.CommodityService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
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
import java.util.List;
import java.util.Optional;

/**
 * <h1>Tender controller</h1>
 * <p>
 * This tender controller class provides the CRUD actions for tenders
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/tender")
public class TenderController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenderController.class);

    @Autowired
    private TenderService tenderService;

    @Autowired
    private TenderClientService tenderClientService;

    @Autowired
    private BusinessTypeService businessTypeService;

    @Autowired
    private HeadOfficeService headOfficeService;

    @Autowired
    private BasisOfTakeoverService basisOfTakeoverService;

    @Autowired
    private MaterialOwnerService materialOwnerService;

    @Autowired
    private CommodityService commodityService;

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
        LOGGER.info("Tender index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Tender> tenderList = tenderService.getAll();
        modelAndView.addObject("models", tenderList);
        modelAndView.setViewName("admin/warehouse/tender/index");
        LOGGER.info("Tender index action returned {} models", tenderList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Tender edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Tender model = new Tender();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Tender edit action called on existing model with id {}", identifier);
            Optional<Tender> tender = tenderService.get(identifier);
            if (tender.isPresent()) {
                model = tender.get();
            }
        }

        List<BusinessType> businessTypeList = businessTypeService.getAll();
        List<HeadOffice> headOfficeList = headOfficeService.getAll();
        List<BasisOfTakeover> basisOfTakeoverList = basisOfTakeoverService.getAll();
        List<TenderClient> tenderClientList = tenderClientService.getAll();
        List<MaterialOwner> materialOwnerList = materialOwnerService.getAll();
        List<Commodity> commodityList = commodityService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.addObject("headOffices", headOfficeList);
        modelAndView.addObject("basisOfTakeover", basisOfTakeoverList);
        modelAndView.addObject("tenderClients", tenderClientList);
        modelAndView.addObject("storageChargeBasis", constantService.getSTORAGE_CHARGE_BASIS());
        modelAndView.addObject("lockInUnits", constantService.getLOCK_IN_UNITS());
        modelAndView.addObject("billingPeakStocks", constantService.getBILLING_PEAK_STOCK());
        modelAndView.addObject("materialOwners", materialOwnerList);
        modelAndView.addObject("commodities", commodityList);
        modelAndView.setViewName("admin/warehouse/tender/edit");
        LOGGER.info("Tender edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Tender tender,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Tender save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/tender/edit");
        if (!result.hasErrors()) {
            try {
                tenderService.edit(tender);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Tender save action successful");
                modelAndView
                        .setViewName("redirect:/admin/tender-add-on-service/index/tender/" + tender
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Tender save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Tender delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            tenderService.delete(id);
            LOGGER.info("Tender delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/tender/index");
        return modelAndView;
    }

}
