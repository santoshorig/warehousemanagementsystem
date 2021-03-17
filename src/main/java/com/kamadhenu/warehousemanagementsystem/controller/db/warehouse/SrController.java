package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.SrForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.SrRemarkStatusForm;
import com.kamadhenu.warehousemanagementsystem.model.db.bank.BankBranch;
import com.kamadhenu.warehousemanagementsystem.model.db.client.*;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.model.domain.common.Remark;
import com.kamadhenu.warehousemanagementsystem.model.domain.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.InwardSimple;
import com.kamadhenu.warehousemanagementsystem.service.db.bank.BankBranchService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.*;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionLocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.*;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <h1>Sr controller</h1>
 * <p>
 * This sr controller class provides the CRUD actions for srs
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/sr")
public class SrController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SrController.class);

    @Autowired
    private SrService srService;

    @Autowired
    private SrInwardTruckQualityCheckService srInwardTruckQualityCheckService;

    @Autowired
    private SrRemarkService srRemarkService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.SrService srDomainService;

    @Autowired
    private InwardTruckService inwardTruckService;

    @Autowired
    private InwardTruckBagService inwardTruckBagService;

    @Autowired
    private InwardTruckQualityCheckService inwardTruckQualityCheckService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private BankBranchService bankBranchService;

    @Autowired
    private InwardService inwardService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.InwardService inwardDomainService;

    @Autowired
    private RegionLocationService regionLocationService;

    @Autowired
    private ClientDocumentService clientDocumentService;

    @Autowired
    private WarehouseDocumentService warehouseDocumentService;

    @Autowired
    private ContractDocumentService contractDocumentService;

    @Autowired
    private ClientRemarkService clientRemarkService;

    @Autowired
    private WarehouseRemarkService warehouseRemarkService;

    @Autowired
    private ContractRemarkService contractRemarkService;

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
        LOGGER.info("Sr index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Sr> srList = new ArrayList<>();
        List<Integer> locationList = helperService.getUserLocationsId();
        List<Sr> existingSrList = srService.getByStatusAndBusinessType();
        if (!locationList.isEmpty() && !helperService.isBusinessHeadUser()) {
            for (Sr sr : existingSrList) {
                Integer regionLocationId = sr.getContract().getWarehouse().getRegLoc();
                Optional<RegionLocation> regionLocation = regionLocationService.get(regionLocationId);
                if (regionLocation.isPresent()) {
                    if (locationList.contains(regionLocation.get().getLocation().getId())) {
                        srList.add(sr);
                    }
                }
            }
        } else {
            srList = existingSrList;
        }
        modelAndView.addObject("models", srList);
        modelAndView.addObject("friendlyStatusMap", constantService.getFRIENDLY_SR_STATUS());
        modelAndView.setViewName("admin/warehouse/sr/index");
        LOGGER.info("Sr index action returned {} models", srList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Sr edit action called");
        ModelAndView modelAndView = new ModelAndView();
        SrForm model = new SrForm();
        Sr sr = new Sr();
        List<Inward> inwardList = new ArrayList<>();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Sr edit action called on existing model with id {}", identifier);
            Optional<Sr> srModel = srService.get(identifier);
            Map<Integer, Inward> inwardHashMap = new HashMap<>();
            if (srModel.isPresent()) {
                sr = srModel.get();
                List<SrInwardTruckQualityCheck> srInwardTruckQualityCheckList =
                        srInwardTruckQualityCheckService.getBySr(sr);
                for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {
                    if (!inwardHashMap.containsKey(srInwardTruckQualityCheck.getInwardTruck().getInward().getId())) {
                        inwardHashMap.put(
                                srInwardTruckQualityCheck.getInwardTruck().getInward().getId(),
                                srInwardTruckQualityCheck.getInwardTruck().getInward()
                        );
                    }
                }
            }
            inwardList = new ArrayList<>(inwardHashMap.values());
            model.setSr(sr);
            model.setInwardList(inwardList);
        } else {
            model.setSr(sr);
            if (helperService.isGeneralUser()) {
                model.getSr().setStatus(constantService.getSR_STATUS().get("underprocess"));
                model.getSr().setSrNumber(helperService.getEpoch().toString());
            } else {
                modelAndView.setViewName("redirect:/error/403");
                return modelAndView;
            }
        }

        List<BankBranch> bankBranchList = bankBranchService.getAll();
        List<BusinessType> businessTypeList = helperService.getUserBusinessType();
        List<Contract> contractList = contractService.getApprovedContract();
        Boolean canEdit = srService.canEdit(model.getSr());
        modelAndView.addObject("model", model);
        modelAndView.addObject("contracts", contractList);
        modelAndView.addObject("inwards", inwardList);
        modelAndView.addObject("bankBranches", bankBranchList);
        modelAndView.addObject("businessTypes", businessTypeList);
        if (canEdit != null) {
          modelAndView.addObject("canEdit", canEdit);
        } else {
          modelAndView.addObject("canEdit", false);
        }
        modelAndView.setViewName("admin/warehouse/sr/edit");
        LOGGER.info("Sr edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") SrForm srForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Sr save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/sr/edit");
        modelAndView.addObject("canEdit", false);
        if (!result.hasErrors()) {
            try {
                srService.edit(srForm.getSr());
                for (Inward inward : srForm.getInwardList()) {
                    List<InwardTruck> inwardTruckList = inwardTruckService.getByInward(inward);
                    for (InwardTruck inwardTruck : inwardTruckList) {
                        List<SrInwardTruckQualityCheck> existingSrInwardTruckQualityCheckList =
                                srInwardTruckQualityCheckService.getByInwardTruck(inwardTruck);
                        Map<Integer, SrInwardTruckQualityCheck> srInwardTruckQualityCheckMap = new HashMap<>();
                        for (SrInwardTruckQualityCheck existingSrInwardTruckQualityCheck : existingSrInwardTruckQualityCheckList) {
                            if (!srInwardTruckQualityCheckMap.containsKey(existingSrInwardTruckQualityCheck.getId())) {
                                srInwardTruckQualityCheckMap.put(
                                        existingSrInwardTruckQualityCheck.getId(),
                                        existingSrInwardTruckQualityCheck
                                );
                            }
                        }
                        List<InwardTruckQualityCheck> inwardTruckQualityCheckList =
                                inwardTruckQualityCheckService.getByInwardTruck(inwardTruck);
                        for (InwardTruckQualityCheck inwardTruckQualityCheck : inwardTruckQualityCheckList) {
                            SrInwardTruckQualityCheck srInwardTruckQualityCheck = new SrInwardTruckQualityCheck();
                            // Check if already entered for same truck and same quality parameter and if so update that
                            Boolean needsTestResultUpdate = Boolean.TRUE;
                            for (SrInwardTruckQualityCheck existingSrInwardTruckQualityCheck : existingSrInwardTruckQualityCheckList) {
                                if (existingSrInwardTruckQualityCheck.getQualityParameter()
                                        .getId() == inwardTruckQualityCheck.getQualityParameter().getId()) {
                                    srInwardTruckQualityCheck = existingSrInwardTruckQualityCheck;
                                    // keep a track of ones updated
                                    srInwardTruckQualityCheckMap.remove(existingSrInwardTruckQualityCheck.getId());
                                    needsTestResultUpdate = Boolean.FALSE;
                                }
                            }
                            if (needsTestResultUpdate) {
                                srInwardTruckQualityCheck.setMinLimit(inwardTruckQualityCheck.getMinLimit());
                                srInwardTruckQualityCheck.setMaxLimit(inwardTruckQualityCheck.getMaxLimit());
                                srInwardTruckQualityCheck.setTestResult(inwardTruckQualityCheck.getTestResult());
                            }
                            srInwardTruckQualityCheck.setValidationDate(new Date());
                            srInwardTruckQualityCheck
                                    .setQualityParameter(inwardTruckQualityCheck.getQualityParameter());
                            srInwardTruckQualityCheck.setInwardTruck(inwardTruck);
                            srInwardTruckQualityCheck.setSr(srForm.getSr());
                            srInwardTruckQualityCheckService.edit(srInwardTruckQualityCheck);
                        }
                        // delete the ones that are not updated this time around
                        for (Map.Entry<Integer, SrInwardTruckQualityCheck> entry : srInwardTruckQualityCheckMap
                                .entrySet()) {
                            srInwardTruckQualityCheckService.delete(entry.getKey());
                        }
                    }
                }
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Sr save action successful");
                modelAndView.setViewName("redirect:/admin/sr-inward-truck-quality-check/index/sr/" + srForm.getSr()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Sr save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Sr delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            srService.delete(id);
            LOGGER.info("Sr delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/sr/index");
        return modelAndView;
    }

    /**
     * Review approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/review-approval/{id}"})
    public ModelAndView reviewApproval(@PathVariable("id") Integer id) {
        LOGGER.info("Sr review approval action called");
        ModelAndView modelAndView = new ModelAndView();
        Double spotPrice = 0.0;
        boolean canEdit = false;
        com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Sr srDomainModel =
                new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Sr();
        SrRemarkStatusForm srRemarkStatusForm = new SrRemarkStatusForm();
        List<SrInwardTruckQualityCheck> srInwardTruckQualityCheckList = new ArrayList<>();
        Optional<Sr> sr = srService.get(id);
        if (sr.isPresent()) {
            Sr srModel = sr.get();
            srRemarkStatusForm.setSr(srModel);
            srInwardTruckQualityCheckList = srInwardTruckQualityCheckService.getBySr(srModel);
            srDomainModel = srDomainService.get(srModel);
            srRemarkStatusForm.setSpotPrice(srModel.getSpotPrice());
            Contract contract = srModel.getContract();
            spotPrice = contractService.getSpotPriceByContract(contract, srModel.getSrDate());
            canEdit = srService.canEdit(srModel);
        }

        List<String> statusList = constantService.getREVIEWER_APPROVAL_SR_STATUS();
        modelAndView.addObject("model", srRemarkStatusForm);
        modelAndView.addObject("srInwardTruckQualityCheckList", srInwardTruckQualityCheckList);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("spotPrice", spotPrice);
        modelAndView.addObject("srDomain", srDomainModel);
        modelAndView.addObject("canEdit", canEdit);
        modelAndView.setViewName("admin/warehouse/sr/review-approval");
        LOGGER.info("Sr review approval action returned {} model", srRemarkStatusForm);
        return modelAndView;
    }

    /**
     * Review approval save action
     *
     * @return the model and view
     */
    @PostMapping("/review-approval-save")
    public ModelAndView reviewApprovalsave(
            @Valid @ModelAttribute("model") SrRemarkStatusForm srRemarkStatusForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Sr review approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/sr/index");
        if (!result.hasErrors()) {
            try {
                Sr sr = srRemarkStatusForm.getSr();
                sr.setSpotPrice(srRemarkStatusForm.getSpotPrice());
                sr.setStatus(srRemarkStatusForm.getStatus());
                srService.edit(sr);
                SrRemark srRemark = new SrRemark();
                srRemark.setRemark(srRemarkStatusForm.getRemark());
                srRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                srRemark.setRemarkDate(new Date());
                srRemark.setSr(sr);
                srRemark.setUser(helperService.getLoggedInDbUser());
                srRemarkService.edit(srRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Sr review approval save action successful");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Sr review approval save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Sr business approval action called");
        ModelAndView modelAndView = new ModelAndView();
        Double spotPrice = 0.0;
        boolean canEdit = false;
        com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Sr srDomainModel =
                new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Sr();
        SrRemarkStatusForm srRemarkStatusForm = new SrRemarkStatusForm();
        List<SrInwardTruckQualityCheck> srInwardTruckQualityCheckList = new ArrayList<>();
        Optional<Sr> sr = srService.get(id);
        if (sr.isPresent()) {
            Sr srModel = sr.get();
            srRemarkStatusForm.setSr(srModel);
            srInwardTruckQualityCheckList = srInwardTruckQualityCheckService.getBySr(srModel);
            srDomainModel = srDomainService.get(srModel);
            srRemarkStatusForm.setSpotPrice(srModel.getSpotPrice());
            Contract contract = srModel.getContract();
            spotPrice = contractService.getSpotPriceByContract(contract, srModel.getSrDate());
            srRemarkStatusForm.setSpotPrice(srModel.getSpotPrice());
            canEdit = srService.canEdit(srModel);
        }

        List<String> statusList = constantService.getBUSINESS_APPROVAL_SR_STATUS();
        modelAndView.addObject("model", srRemarkStatusForm);
        modelAndView.addObject("srInwardTruckQualityCheckList", srInwardTruckQualityCheckList);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("spotPrice", spotPrice);
        modelAndView.addObject("canEdit", canEdit);
        modelAndView.addObject("srDomain", srDomainModel);
        modelAndView.setViewName("admin/warehouse/sr/business-approval");
        LOGGER.info("Sr business approval action returned {} model", srRemarkStatusForm);
        return modelAndView;
    }

    /**
     * Business approval save action
     *
     * @return the model and view
     */
    @PostMapping("/business-approval-save")
    public ModelAndView businessApprovalSave(
            @Valid @ModelAttribute("model") SrRemarkStatusForm srRemarkStatusForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Sr business approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/sr/index");
        if (!result.hasErrors()) {
            try {
                Sr sr = srRemarkStatusForm.getSr();
                sr.setSpotPrice(srRemarkStatusForm.getSpotPrice());
                sr.setStatus(srRemarkStatusForm.getStatus());
                srService.edit(sr);
                // Set inward truck bags to be liened
                List<SrInwardTruckQualityCheck> srInwardTruckQualityCheckList =
                        srInwardTruckQualityCheckService.getBySr(sr);
                for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {
                    InwardTruck inwardTruck = srInwardTruckQualityCheck.getInwardTruck();
                    List<InwardTruckBag> inwardTruckBagList = inwardTruckBagService.getByInwardTruck(inwardTruck);
                    for (InwardTruckBag inwardTruckBag : inwardTruckBagList) {
                        inwardTruckBag.setLien(true);
                        inwardTruckBagService.edit(inwardTruckBag);
                    }
                }
                SrRemark srRemark = new SrRemark();
                srRemark.setRemark(srRemarkStatusForm.getRemark());
                srRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                srRemark.setRemarkDate(new Date());
                srRemark.setSr(sr);
                srRemark.setUser(helperService.getLoggedInDbUser());
                srRemarkService.edit(srRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Sr business approval save action successful");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Sr business approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
    }

    /**
     * Get cad by contract action
     *
     * @param contract the id of contract
     * @return json
     */
    @RequestMapping(value = "/getCadByContract/{contract}", method = RequestMethod.GET)
    public ResponseEntity getCadByContract(@PathVariable Integer contract) {
        Optional<Contract> contractModel = contractService.get(contract);
        Map<Integer, InwardSimple> inwardHashMap =
                new HashMap<>();
        if (contractModel.isPresent()) {
            List<Inward> existingInwardList = inwardService.getApprovedByContract(contractModel.get());
            List<Sr> existingSrList = srService.getApprovedByContract(contractModel.get());
            List<Integer> existingInwardForSrList = new ArrayList<>();
            for (Sr sr : existingSrList) {
                List<SrInwardTruckQualityCheck> srInwardTruckQualityCheckList =
                        srInwardTruckQualityCheckService.getBySr(sr);
                for (SrInwardTruckQualityCheck srInwardTruckQualityCheck : srInwardTruckQualityCheckList) {
                    if (!existingInwardForSrList
                            .contains(srInwardTruckQualityCheck.getInwardTruck().getInward().getId())) {
                        existingInwardForSrList.add(srInwardTruckQualityCheck.getInwardTruck().getInward().getId());
                    }
                }
            }
            for (Inward inward : existingInwardList) {
                if (!inwardHashMap.containsKey(inward.getId()) && !existingInwardForSrList.contains(inward.getId())) {
                    com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Inward domainInwardModel =
                            inwardDomainService.get(inward);
                    InwardSimple inwardSimple = new InwardSimple();
                    inwardSimple.setInward(inward);
                    inwardSimple.setTotalBags(domainInwardModel.getTotalBags());
                    inwardSimple.setTotalNetWeight(domainInwardModel.getTotalNetWeightMt());
                    inwardHashMap.put(inward.getId(), inwardSimple);
                }
            }
        }
        List<InwardSimple> inwardList = new ArrayList<>(inwardHashMap.values());
        return new ResponseEntity(inwardList, HttpStatus.OK);
    }
    
    /**
     * Check contract is expired
     *
     * @param contract
     * @return
     */
    @RequestMapping(value = "/checkContractExpiry/{contract}", method = RequestMethod.GET)
    public ResponseEntity checkContractExpiry(@PathVariable Integer contract) {
        boolean isExpiry = false;
        Optional<Contract> contractModel = contractService.get(contract);
        if (contractModel.isPresent()) {
          Contract c = contractModel.get();
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
          LocalDateTime now = LocalDateTime.now();  
          if (c.getEffectiveTo().toString().compareTo(dtf.format(now)) < 0) {
            isExpiry = true;
          }
        }
        return new ResponseEntity(isExpiry, HttpStatus.OK);
    }

    /**
     * Get spot price by contract action
     *
     * @param contract the id of contract
     * @return json
     * @throws ParseException 
     */
    @RequestMapping(value = "/getSpotPriceByContract/{contract}/{srDate}", method = RequestMethod.GET)
    public ResponseEntity getSpotPriceByContract(@PathVariable Integer contract, @PathVariable String srDate) throws ParseException {
        Double spotPrice = 0.0;
        Optional<Contract> contractModel = contractService.get(contract);
        if (contractModel.isPresent()) {
            Contract existingContractModel = contractModel.get(); 
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(srDate); 
            spotPrice = contractService.getSpotPriceByContract(existingContractModel, date);
        }
        return new ResponseEntity(spotPrice, HttpStatus.OK);
    }

    /**
     * Get document by contract action
     *
     * @param contract the id of contract
     * @return json
     */
    @RequestMapping(value = "/getDocumentByContract/{contract}", method = RequestMethod.GET)
    public ResponseEntity getDocumentByContract(@PathVariable Integer contract) {
        List<Document> documentList = new ArrayList<>();
        Optional<Contract> contractModel = contractService.get(contract);
        if (contractModel.isPresent()) {
            Contract existingContractModel = contractModel.get();
            Warehouse warehouse = existingContractModel.getWarehouse();
            Client client = existingContractModel.getClient();

            // get client documents
            List<ClientDocument> clientDocumentList = clientDocumentService.getByClient(client);
            for (ClientDocument clientDocument : clientDocumentList) {
                Document document = new Document();
                document.setDocumentFor("client");
                document.setDocumentType(clientDocument.getDocumentType().getName());
                document.setDocumentId(clientDocument.getDocument().getId());
                documentList.add(document);
            }

            // get warehouse documents
            List<WarehouseDocument> warehouseDocumentList = warehouseDocumentService.getByWarehouse(warehouse);
            for (WarehouseDocument warehouseDocument : warehouseDocumentList) {
                Document document = new Document();
                document.setDocumentFor("warehouse");
                document.setDocumentType(warehouseDocument.getDocumentType().getName());
                document.setDocumentId(warehouseDocument.getDocument().getId());
                documentList.add(document);
            }

            // get contract documents
            List<ContractDocument> contractDocumentList = contractDocumentService.getByContract(existingContractModel);
            for (ContractDocument contractDocument : contractDocumentList) {
                Document document = new Document();
                document.setDocumentFor("contract");
                document.setDocumentType(contractDocument.getDocumentType().getName());
                document.setDocumentId(contractDocument.getDocument().getId());
                documentList.add(document);
            }
        }
        return new ResponseEntity(documentList, HttpStatus.OK);
    }

    /**
     * Get remark by contract action
     *
     * @param contract the id of contract
     * @return json
     */
    @RequestMapping(value = "/getRemarkByContract/{contract}", method = RequestMethod.GET)
    public ResponseEntity getRemarkByContract(@PathVariable Integer contract) {
        List<Remark> remarkList = new ArrayList<>();
        Optional<Contract> contractModel = contractService.get(contract);
        if (contractModel.isPresent()) {
            Contract existingContractModel = contractModel.get();
            Warehouse warehouse = existingContractModel.getWarehouse();
            Client client = existingContractModel.getClient();

            // get client remarks
            List<ClientRemark> clientRemarkList = clientRemarkService.getByClient(client);
            for (ClientRemark clientRemark : clientRemarkList) {
                Remark remark = new Remark();
                remark.setRemarkFor("client");
                remark.setRemark(clientRemark.getRemark());
                remark.setRemarkDate(clientRemark.getRemarkDate());
                remark.setRemarkUser(clientRemark.getUser());
                remarkList.add(remark);
            }

            // get warehouse remarks
            List<WarehouseRemark> warehouseRemarkList = warehouseRemarkService.getByWarehouse(warehouse);
            for (WarehouseRemark warehouseRemark : warehouseRemarkList) {
                Remark remark = new Remark();
                remark.setRemarkFor("warehouse");
                remark.setRemark(warehouseRemark.getRemark());
                remark.setRemarkDate(warehouseRemark.getRemarkDate());
                remark.setRemarkUser(warehouseRemark.getUser());
                remarkList.add(remark);
            }

            // get contract documents
            List<ContractRemark> contractRemarkList = contractRemarkService.getByContract(existingContractModel);
            for (ContractRemark contractRemark : contractRemarkList) {
                Remark remark = new Remark();
                remark.setRemarkFor("contract");
                remark.setRemark(contractRemark.getRemark());
                remark.setRemarkDate(contractRemark.getRemarkDate());
                remark.setRemarkUser(contractRemark.getUser());
                remarkList.add(remark);
            }
        }
        return new ResponseEntity(remarkList, HttpStatus.OK);
    }

    ///TODO Should be in Inward Controller with access

    /**
     * Get document by inward action
     *
     * @param inward the id of inward
     * @return json
     */
    @RequestMapping(value = "/getInwardDocument/{inward}", method = RequestMethod.GET)
    public ResponseEntity getInwardDocument(@PathVariable Integer inward) {
        List<com.kamadhenu.warehousemanagementsystem.model.domain.document.Document> documentList = new ArrayList<>();
        Optional<Inward> inwardModel = inwardService.get(inward);
        if (inwardModel.isPresent()) {
            Inward existingInwardModel = inwardModel.get();

            // inward document
            com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                    cadDocument = new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
            cadDocument.setDocumentFor("inward");
            cadDocument.setDocumentType("CAD");
            cadDocument.setDocumentId(existingInwardModel.getCadDocument().getId());
            documentList.add(cadDocument);

            com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                    cddDocument = new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
            cddDocument.setDocumentFor("inward");
            cddDocument.setDocumentType("CDD");
            cddDocument.setDocumentId(existingInwardModel.getCddDocument().getId());
            documentList.add(cddDocument);

            // truck documents
            com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Inward domainInwardModel =
                    inwardDomainService.get(existingInwardModel);
            for (com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.InwardTruck domainInwardTruck : domainInwardModel
                    .getInwardTruckList()) {
                com.kamadhenu.warehousemanagementsystem.model.db.warehouse.InwardTruck inwardTruck =
                        domainInwardTruck.getInwardTruck();
                if (inwardTruck.getGatePassDocument() != null) {
                    com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                            truckDocument =
                            new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
                    truckDocument.setDocumentFor("inwardtruck");
                    truckDocument.setDocumentType(inwardTruck.getVehicleNumber() + " - " + "Gate Pass");
                    truckDocument.setDocumentId(inwardTruck.getGatePassDocument().getId());
                    documentList.add(truckDocument);
                }
                if (inwardTruck.getWeighmentDocument() != null) {
                    com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                            truckDocument =
                            new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
                    truckDocument.setDocumentFor("inwardtruck");
                    truckDocument.setDocumentType(inwardTruck.getVehicleNumber() + " - " + "Weighment Document");
                    truckDocument.setDocumentId(inwardTruck.getWeighmentDocument().getId());
                    documentList.add(truckDocument);
                }
                if (inwardTruck.getMandiReceiptDocument() != null) {
                    com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                            truckDocument =
                            new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
                    truckDocument.setDocumentFor("inwardtruck");
                    truckDocument.setDocumentType(inwardTruck.getVehicleNumber() + " - " + "Mandi Receipt Document");
                    truckDocument.setDocumentId(inwardTruck.getMandiReceiptDocument().getId());
                    documentList.add(truckDocument);
                }
                if (inwardTruck.getQualityCheckDocument() != null) {
                    com.kamadhenu.warehousemanagementsystem.model.domain.document.Document
                            truckDocument =
                            new com.kamadhenu.warehousemanagementsystem.model.domain.document.Document();
                    truckDocument.setDocumentFor("inwardtruck");
                    truckDocument.setDocumentType(inwardTruck.getVehicleNumber() + " - " + "Quality Check Document");
                    truckDocument.setDocumentId(inwardTruck.getQualityCheckDocument().getId());
                    documentList.add(truckDocument);
                }
            }
        }
        return new ResponseEntity(documentList, HttpStatus.OK);
    }
}
