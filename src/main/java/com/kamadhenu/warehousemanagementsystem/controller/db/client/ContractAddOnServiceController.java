package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractAddOnService;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.AddOnService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractAddOnServiceService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.AddOnServiceService;
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
 * <h1>ContractAddOnService controller</h1>
 * <p>
 * This contract add on service controller class provides the CRUD actions for contract add on services
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/contract-add-on-service")
public class ContractAddOnServiceController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractAddOnServiceController.class);

    @Autowired
    private ContractAddOnServiceService contractAddOnServiceService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private AddOnServiceService addOnServiceService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/index/contract/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("ContractAddOnService index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<ContractAddOnService> contractAddOnServiceList = new ArrayList<>();
        Optional<Contract> contract = contractService.get(id);
        if (contract.isPresent()) {
            Contract contractModel = contract.get();
            contractAddOnServiceList = contractAddOnServiceService.getByContract(contractModel);
            modelAndView.addObject("contract", contractModel);
        }
        if (contractAddOnServiceList.size() > 0) {
            modelAndView.addObject("models", contractAddOnServiceList);
            modelAndView.setViewName("admin/client/contract-add-on-service/index");
        } else {
            modelAndView.setViewName("redirect:/admin/contract-add-on-service/edit/contract/" + id);
        }
        LOGGER.info("ContractAddOnService index action returned {} models", contractAddOnServiceList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/contract/{contractId}", "/edit/contract/{contractId}/{id}"})
    public ModelAndView edit(@PathVariable("contractId") Integer contractId, @PathVariable("id") Optional<Integer> id) {
        LOGGER.info("ContractAddOnService edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ContractAddOnService model = new ContractAddOnService();
        Optional<Contract> contract = contractService.get(contractId);
        if (contract.isPresent()) {
            Contract contractModel = contract.get();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                Optional<ContractAddOnService> contractAddOnService = contractAddOnServiceService.get(identifier);
                if (contractAddOnService.isPresent()) {
                    model = contractAddOnService.get();
                }
            }
            model.setContract(contractModel);
        }

        List<AddOnService> addOnServiceList = addOnServiceService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("addOnServices", addOnServiceList);
        modelAndView.setViewName("admin/client/contract-add-on-service/edit");
        LOGGER.info("ContractAddOnService edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ContractAddOnService contractAddOnService,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ContractAddOnService save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/client/contract-add-on-service/edit");
        if (!result.hasErrors()) {
            try {
                contractAddOnServiceService.edit(contractAddOnService);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ContractAddOnService save action successful");
                modelAndView
                        .setViewName("redirect:/admin/contract-add-on-service/index/contract/" + contractAddOnService
                                .getContract()
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.setViewName("redirect:/admin/contract-add-on-service/edit/contract/" + contractAddOnService
                        .getContract()
                        .getId());
            }
        } else {
            LOGGER.error("ContractAddOnService save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ContractAddOnService delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer contractId = null;
        try {
            Optional<ContractAddOnService> contractAddOnService = contractAddOnServiceService.get(id);
            if (contractAddOnService.isPresent()) {
                ContractAddOnService model = contractAddOnService.get();
                contractId = model.getContract().getId();
                contractAddOnServiceService.delete(id);
                LOGGER.info("ContractAddOnService delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/contract-add-on-service/index/contract/" + contractId);
        return modelAndView;
    }

}
