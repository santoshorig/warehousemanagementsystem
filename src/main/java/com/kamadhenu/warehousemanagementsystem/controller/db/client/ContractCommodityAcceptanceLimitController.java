package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodity;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodityAcceptanceLimit;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.QualityParameter;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractCommodityAcceptanceLimitService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractCommodityService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.QualityParameterService;
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
 * <h1>ContractCommodityAcceptanceLimit controller</h1>
 * <p>
 * This contract commodity acceptance limit controller class provides the CRUD actions for contract commodity acceptance limits
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/contract-commodity-acceptance-limit")
public class ContractCommodityAcceptanceLimitController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractCommodityAcceptanceLimitController.class);

    @Autowired
    private ContractCommodityAcceptanceLimitService contractCommodityAcceptanceLimitService;

    @Autowired
    private ContractCommodityService contractCommodityService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private QualityParameterService qualityParameterService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/contract/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("ContractCommodityAcceptanceLimit index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<ContractCommodityAcceptanceLimit> contractCommodityAcceptanceLimitList = new ArrayList<>();
        Optional<Contract> contract = contractService.get(id);
        if (contract.isPresent()) {
            Contract contractModel = contract.get();
            contractCommodityAcceptanceLimitList = contractCommodityAcceptanceLimitService.getByContract(contractModel);
            modelAndView.addObject("contract", contractModel);
            List<ContractCommodity> contractCommodityList = contractCommodityService.getByContract(contractModel);
            Map<Integer, ContractCommodity> contractCommodityMap = new HashMap<>();
            for (ContractCommodity contractCommodity : contractCommodityList) {
                contractCommodityMap.put(contractCommodity.getId(), contractCommodity);
            }
            modelAndView.addObject("contractCommodities", contractCommodityMap);
            if (contractCommodityAcceptanceLimitList.size() > 0) {
                modelAndView.addObject("models", contractCommodityAcceptanceLimitList);
                modelAndView.setViewName("admin/client/contract-commodity-acceptance-limit/index");
            } else {
                modelAndView.setViewName("redirect:/admin/contract-commodity-acceptance-limit/edit/contract/" + id);
            }
            LOGGER.info(
                    "ContractCommodityAcceptanceLimit index action returned {} models",
                    contractCommodityAcceptanceLimitList.size()
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
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/contract/{contractId}", "/edit/contract/{contractId}/{id}"})
    public ModelAndView edit(@PathVariable("contractId") Integer contractId, @PathVariable("id") Optional<Integer> id) {
        LOGGER.info("ContractCommodityAcceptanceLimit edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ContractCommodityAcceptanceLimit model = new ContractCommodityAcceptanceLimit();
        Optional<Contract> contract = contractService.get(contractId);
        List<ContractCommodity> contractCommodityList = new ArrayList<>();
        if (contract.isPresent()) {
            Contract contractModel = contract.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                Optional<ContractCommodityAcceptanceLimit> contractCommodityAcceptanceLimit =
                        contractCommodityAcceptanceLimitService.get(identifier);
                if (contractCommodityAcceptanceLimit.isPresent()) {
                    model = contractCommodityAcceptanceLimit.get();
                }
            }
            contractCommodityList = contractCommodityService.getByContract(contractModel);
        }

        List<QualityParameter> qualityParameterList = qualityParameterService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("contractCommodities", contractCommodityList);
        modelAndView.addObject("qualityParameters", qualityParameterList);
        modelAndView.setViewName("admin/client/contract-commodity-acceptance-limit/edit");
        LOGGER.info("ContractCommodityAcceptanceLimit edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ContractCommodityAcceptanceLimit contractCommodityAcceptanceLimit,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ContractCommodityAcceptanceLimit save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/client/contract-commodity-acceptance-limit/edit");
        if (!result.hasErrors()) {
            Optional<ContractCommodity> contractCommodity =
                    contractCommodityService.get(contractCommodityAcceptanceLimit.getContractCommodity());
            ContractCommodity model = contractCommodity.get();
            try {
                contractCommodityAcceptanceLimitService.edit(contractCommodityAcceptanceLimit);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ContractCommodityAcceptanceLimit save action successful");
                modelAndView
                        .setViewName("redirect:/admin/contract-commodity-acceptance-limit/index/contract/" + model
                                .getContract()
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView
                        .setViewName("redirect:/admin/contract-commodity-acceptance-limit/edit/contract/" + model
                                .getContract()
                                .getId());
            }
        } else {
            LOGGER.error("ContractCommodityAcceptanceLimit save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ContractCommodityAcceptanceLimit delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer contractId = null;
        try {
            Optional<ContractCommodityAcceptanceLimit> contractCommodityAcceptanceLimit =
                    contractCommodityAcceptanceLimitService.get(id);
            if (contractCommodityAcceptanceLimit.isPresent()) {
                ContractCommodityAcceptanceLimit model = contractCommodityAcceptanceLimit.get();
                Optional<ContractCommodity> contractCommodity =
                        contractCommodityService.get(model.getContractCommodity());
                contractId = contractCommodity.get().getContract().getId();
                contractCommodityAcceptanceLimitService.delete(id);
                LOGGER.info("ContractCommodityAcceptanceLimit delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/contract-commodity-acceptance-limit/index/contract/" + contractId);
        return modelAndView;
    }

}
