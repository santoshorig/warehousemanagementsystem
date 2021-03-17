package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodity;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractCommodityInsurance;
import com.kamadhenu.warehousemanagementsystem.model.db.insurance.Insurance;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractCommodityInsuranceService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractCommodityService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractService;
import com.kamadhenu.warehousemanagementsystem.service.db.insurance.InsuranceService;
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
 * <h1>ContractCommodityInsurance controller</h1>
 * <p>
 * This contract add on service controller class provides the CRUD actions for contract add on services
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/contract-commodity-insurance")
public class ContractCommodityInsuranceController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractCommodityInsuranceController.class);

    @Autowired
    private ContractCommodityInsuranceService contractCommodityInsuranceService;

    @Autowired
    private ContractCommodityService contractCommodityService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private InsuranceService insuranceService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/contract/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("ContractCommodityInsurance index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<ContractCommodityInsurance> contractCommodityInsuranceList = new ArrayList<>();
        Optional<Contract> contract = contractService.get(id);
        if (contract.isPresent()) {
            Contract contractModel = contract.get();
            contractCommodityInsuranceList = contractCommodityInsuranceService.getByContract(contractModel);
            modelAndView.addObject("contract", contractModel);
            List<ContractCommodity> contractCommodityList = contractCommodityService.getByContract(contractModel);
            Map<Integer, ContractCommodity> contractCommodityMap = new HashMap<>();
            for (ContractCommodity contractCommodity : contractCommodityList) {
                contractCommodityMap.put(contractCommodity.getId(), contractCommodity);
            }
            modelAndView.addObject("contractCommodities", contractCommodityMap);
            if (contractCommodityInsuranceList.size() > 0) {
                modelAndView.addObject("models", contractCommodityInsuranceList);
                modelAndView.setViewName("admin/client/contract-commodity-insurance/index");
            } else {
                modelAndView.setViewName("redirect:/admin/contract-commodity-insurance/edit/contract/" + id);
                LOGGER.info(
                        "ContractCommodityInsurance index action returned {} models",
                        contractCommodityInsuranceList.size()
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
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/contract/{contractId}", "/edit/contract/{contractId}/{id}"})
    public ModelAndView edit(@PathVariable("contractId") Integer contractId, @PathVariable("id") Optional<Integer> id) {
        LOGGER.info("ContractCommodityInsurance edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ContractCommodityInsurance model = new ContractCommodityInsurance();
        Optional<Contract> contract = contractService.get(contractId);
        List<ContractCommodity> contractCommodityList = new ArrayList<>();
        if (contract.isPresent()) {
            Contract contractModel = contract.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                Optional<ContractCommodityInsurance> contractCommodityInsurance =
                        contractCommodityInsuranceService.get(identifier);
                if (contractCommodityInsurance.isPresent()) {
                    model = contractCommodityInsurance.get();
                }
            }
            contractCommodityList = contractCommodityService.getByContract(contractModel);
        }

        Map<String, String> insuranceOwnerMap = constantService.getINSURANCE_OWNER();
        List<Insurance> insuranceList = insuranceService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("contractCommodities", contractCommodityList);
        modelAndView.addObject("insurances", insuranceList);
        modelAndView.addObject("insuranceOwner", insuranceOwnerMap);
        modelAndView.setViewName("admin/client/contract-commodity-insurance/edit");
        LOGGER.info("ContractCommodityInsurance edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ContractCommodityInsurance contractCommodityInsurance,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ContractCommodityInsurance save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/client/contract-commodity-insurance/edit");
        if (!result.hasErrors()) {
            Optional<ContractCommodity> contractCommodity =
                    contractCommodityService.get(contractCommodityInsurance.getContractCommodity());
            ContractCommodity model = contractCommodity.get();
            try {
                ///TODO fix this as due to bad relationship
                contractCommodityInsuranceService.edit(contractCommodityInsurance);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ContractCommodityInsurance save action successful");
                modelAndView
                        .setViewName("redirect:/admin/contract-commodity-insurance/index/contract/" + model
                                .getContract()
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView
                        .setViewName("redirect:/admin/contract-commodity-insurance/edit/contract/" + model.getContract()
                                .getId());
            }
        } else {
            LOGGER.error("ContractCommodityInsurance save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ContractCommodityInsurance delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer contractId = null;
        try {
            Optional<ContractCommodityInsurance> contractCommodityInsurance = contractCommodityInsuranceService.get(id);
            if (contractCommodityInsurance.isPresent()) {
                ContractCommodityInsurance model = contractCommodityInsurance.get();
                Optional<ContractCommodity> contractCommodity =
                        contractCommodityService.get(model.getContractCommodity());
                contractId = contractCommodity.get().getContract().getId();
                contractCommodityInsuranceService.delete(id);
                LOGGER.info("ContractCommodityInsurance delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/contract-commodity-insurance/index/contract/" + contractId);
        return modelAndView;
    }

}
