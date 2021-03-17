package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.client.ContractForm;
import com.kamadhenu.warehousemanagementsystem.form.client.ContractRemarkForm;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractRemark;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.general.Commodity;
import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.BasisOfTakeover;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseCad;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractService;
import com.kamadhenu.warehousemanagementsystem.service.db.general.CommodityService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionLocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.BasisOfTakeoverService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseCadService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <h1>Contract controller</h1>
 * <p>
 * This contract controller class provides the CRUD actions for contracts
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/contract")
public class ContractController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private ContractService contractService;

    @Autowired
    private BasisOfTakeoverService basisOfTakeoverService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseCadService warehouseCadService;

    @Autowired
    private ContractRemarkService contractRemarkService;

    @Autowired
    private RegionLocationService regionLocationService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.client.ContractService contractDomainService;

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
        LOGGER.info("Contract index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Contract> contractList = contractService.getByStatusAndBusinessType();
        modelAndView.addObject("models", contractList);
        modelAndView.addObject("friendlyStatusMap", constantService.getFRIENDLY_CONTRACT_STATUS());
        modelAndView.setViewName("admin/client/contract/index");
        LOGGER.info("Contract index action returned {} models", contractList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Contract edit action called");
        ModelAndView modelAndView = new ModelAndView();
        Contract model = new Contract();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Contract edit action called on existing model with id {}", identifier);
            Optional<Contract> contract = contractService.get(identifier);
            if (contract.isPresent()) {
                model = contract.get();
            }
        } else {
            if (helperService.isGeneralUser()) {
                model.setStatus(constantService.getCONTRACT_STATUS().get("underprocess"));
                model.setLockedInward(false);
                model.setLockedSr(false);
                model.setLockedDo(false);
                model.setLockedOutward(false);
            } else {
                modelAndView.setViewName("redirect:/error/403");
                return modelAndView;
            }
        }

        List<Warehouse> warehouseList = new ArrayList<>();
        List<Warehouse> existingWarehouseList = warehouseService.getActiveByBusinessType();
        List<Integer> locationList = helperService.getUserLocationsId();
        if (!locationList.isEmpty()) {
            for (Warehouse warehouse : existingWarehouseList) {
                Optional<RegionLocation> regionLocation = regionLocationService.get(warehouse.getRegLoc());
                if (regionLocation.isPresent()) {
                    RegionLocation regionLocationModel = regionLocation.get();
                    if (locationList.contains(regionLocationModel.getLocation().getId())) {
                        warehouseList.add(warehouse);
                    }
                }
            }
        } else {
            warehouseList = existingWarehouseList;
        }

        List<BusinessType> businessTypeList = helperService.getUserBusinessType();
        List<BasisOfTakeover> basisOfTakeoverList = basisOfTakeoverService.getAll();
        List<Commodity> commodityList = commodityService.getAll();
        List<Client> clientList = clientService.getAll();

        modelAndView.addObject("model", model);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.addObject("basisOfTakeover", basisOfTakeoverList);
        modelAndView.addObject("storageChargeBasis", constantService.getSTORAGE_CHARGE_BASIS());
        modelAndView.addObject("lockInUnits", constantService.getLOCK_IN_UNITS());
        modelAndView.addObject("billingPeakStocks", constantService.getBILLING_PEAK_STOCK());
        modelAndView.addObject("commodities", commodityList);
        modelAndView.addObject("clients", clientList);
        modelAndView.addObject("warehouses", warehouseList);
        modelAndView.addObject("billingTypes", constantService.getBILLING_TYPE());
        modelAndView.setViewName("admin/client/contract/edit");
        LOGGER.info("Contract edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") Contract contract,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Contract save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/client/contract/edit");
        if (!result.hasErrors()) {
            try {
                contractService.edit(contract);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Contract save action successful");
                modelAndView
                        .setViewName("redirect:/admin/contract-add-on-service/index/contract/" + contract
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Contract save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Contract delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            contractService.delete(id);
            LOGGER.info("Contract delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/contract/index");
        return modelAndView;
    }

    /**
     * Ops approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/ops-approval/{id}"})
    public ModelAndView opsApproval(@PathVariable("id") Integer id) {
        LOGGER.info("Contract ops approval action called");
        ModelAndView modelAndView = new ModelAndView();
        ContractRemarkForm contractRemarkForm = new ContractRemarkForm();
        com.kamadhenu.warehousemanagementsystem.model.domain.client.Contract model =
                new com.kamadhenu.warehousemanagementsystem.model.domain.client.Contract();
        Optional<Contract> contract = contractService.get(id);
        if (contract.isPresent()) {
            Contract contractModel = contract.get();
            if (helperService.getContractStatusByRole().stream()
                    .anyMatch(contractModel.getStatus()::equalsIgnoreCase)) {
                model = contractDomainService.get(contractModel);
            }
            contractRemarkForm.setContract(contractModel);
        }

        List<String> statusList = constantService.getOPS_APPROVER_APPROVAL_CONTRACT_STATUS();
        modelAndView.addObject("model", model);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("contractRemarkForm", contractRemarkForm);
        modelAndView.setViewName("admin/client/contract/ops-approval");
        LOGGER.info("Contract ops approval action returned {} model", model);
        return modelAndView;
    }

    /**
     * Ops approval save action
     *
     * @return the model and view
     */
    @PostMapping("/ops-approval-save")
    public ModelAndView opsApprovalSave(
            @Valid @ModelAttribute("model") ContractRemarkForm contractRemarkForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Contract ops approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/contract/index");
        if (!result.hasErrors()) {
            try {
                Contract contract = contractRemarkForm.getContract();
                contract.setStatus(contractRemarkForm.getStatus());
                contractService.edit(contract);
                ContractRemark contractRemark = new ContractRemark();
                contractRemark.setRemark(contractRemarkForm.getRemark());
                contractRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                contractRemark.setRemarkDate(new Date());
                contractRemark.setContract(contract);
                contractRemark.setUser(helperService.getLoggedInDbUser());
                contractRemarkService.edit(contractRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Contract ops approval save action successful");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Contract ops approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Business approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/business-approval/{id}"})
    public ModelAndView businessApproval(@PathVariable("id") Integer id) {
        LOGGER.info("Contract business approval action called");
        ModelAndView modelAndView = new ModelAndView();
        ContractRemarkForm contractRemarkForm = new ContractRemarkForm();
        com.kamadhenu.warehousemanagementsystem.model.domain.client.Contract model =
                new com.kamadhenu.warehousemanagementsystem.model.domain.client.Contract();
        Optional<Contract> contract = contractService.get(id);
        if (contract.isPresent()) {
            Contract contractModel = contract.get();
            if (helperService.getContractStatusByRole().stream()
                    .anyMatch(contractModel.getStatus()::equalsIgnoreCase)) {
                model = contractDomainService.get(contractModel);
            }
            contractRemarkForm.setContract(contractModel);
        }

        List<String> statusList = constantService.getBUSINESS_APPROVAL_CONTRACT_STATUS();
        modelAndView.addObject("model", model);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("contractRemarkForm", contractRemarkForm);
        modelAndView.setViewName("admin/client/contract/business-approval");
        LOGGER.info("Contract business approval action returned {} model", model);
        return modelAndView;
    }

    /**
     * Business approval save action
     *
     * @return the model and view
     */
    @PostMapping("/business-approval-save")
    public ModelAndView businessApprovalSave(
            @Valid @ModelAttribute("model") ContractRemarkForm contractRemarkForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Contract business approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/contract/index");
        if (!result.hasErrors()) {
            try {
                Contract contract = contractRemarkForm.getContract();
                contract.setStatus(contractRemarkForm.getStatus());
                contractService.edit(contract);
                ContractRemark contractRemark = new ContractRemark();
                contractRemark.setRemark(contractRemarkForm.getRemark());
                contractRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                contractRemark.setRemarkDate(new Date());
                contractRemark.setContract(contract);
                contractRemark.setUser(helperService.getLoggedInDbUser());
                contractRemarkService.edit(contractRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Contract business approval save action successful");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Contract business approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Lock action
     *
     * @return the model and view
     */
    @GetMapping("/lock")
    public ModelAndView lock() {
        LOGGER.info("Contract lock action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Contract> contractList = contractService.getAll();
        modelAndView.addObject("models", contractList);
        modelAndView.addObject("friendlyStatusMap", constantService.getFRIENDLY_CONTRACT_STATUS());
        modelAndView.setViewName("admin/client/contract/lock");
        LOGGER.info("Contract lock action returned {} models", contractList.size());
        return modelAndView;
    }

    /**
     * Lock Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/lock-edit"})
    public ModelAndView lockedit() {
        LOGGER.info("Contract lock edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ContractForm model = new ContractForm();
        List<Contract> contractList = contractService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("contracts", contractList);
        modelAndView.setViewName("admin/client/contract/lock-edit");
        LOGGER.info("Contract lock edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Ops approval save action
     *
     * @return the model and view
     */
    @PostMapping("/lock-save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ContractForm contractForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Contract lock edit save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/contract/lock");
        if (!result.hasErrors()) {
            try {
                for (Contract contract : contractForm.getContractList()) {
                    contract.setLockedInward(contractForm.isLockedInward());
                    contract.setLockedSr(contractForm.isLockedSr());
                    contract.setLockedDo(contractForm.isLockedDo());
                    contract.setLockedOutward(contractForm.isLockedOutward());
                    contractService.edit(contract);
                }
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Contract lock save action successful");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Contract lock save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Get contract by id action
     *
     * @param contract the id of contract
     * @return json
     */
    @RequestMapping(value = "/getContract/{contract}", method = RequestMethod.GET)
    public ResponseEntity getContract(@PathVariable Integer contract) {
        Optional<Contract> contractModel = contractService.get(contract);
        Contract existingContract = new Contract();
        if (contractModel.isPresent()) {
            existingContract = contractModel.get();
        }
        return new ResponseEntity(existingContract, HttpStatus.OK);
    }

    /**
     * Get cad by id action
     *
     * @param contract the id of contract
     * @return json
     */
    @RequestMapping(value = "/getCadByContract/{contract}", method = RequestMethod.GET)
    public ResponseEntity getCadByContract(@PathVariable Integer contract) {
        Optional<Contract> contractModel = contractService.get(contract);
        List<WarehouseCad> warehouseCadList = new ArrayList<>();
        if (contractModel.isPresent()) {
            warehouseCadList = warehouseCadService.getByWarehouseAndUsed(contractModel.get().getWarehouse(), false);
        }
        return new ResponseEntity(warehouseCadList, HttpStatus.OK);
    }

}
