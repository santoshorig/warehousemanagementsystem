package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.DoForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.DoRemarkStatusForm;
import com.kamadhenu.warehousemanagementsystem.model.db.client.*;
import com.kamadhenu.warehousemanagementsystem.model.db.general.BusinessType;
import com.kamadhenu.warehousemanagementsystem.model.db.location.RegionLocation;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.*;
import com.kamadhenu.warehousemanagementsystem.model.domain.common.Remark;
import com.kamadhenu.warehousemanagementsystem.model.domain.document.Document;
import com.kamadhenu.warehousemanagementsystem.service.db.client.*;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.location.RegionLocationService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.DoRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.DoService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseDocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseRemarkService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <h1>Do controller</h1>
 * <p>
 * This do controller class provides the CRUD actions for dos
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/do")
public class DoController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoController.class);

    @Autowired
    private DoService doService;

    @Autowired
    private com.kamadhenu.warehousemanagementsystem.service.domain.warehouse.DoService doDomainService;

    @Autowired
    private DoRemarkService doRemarkService;

    @Autowired
    private ContractService contractService;

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
    private DocumentService documentService;

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
        LOGGER.info("Do index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Do> doList = new ArrayList<>();
        List<Integer> locationList = helperService.getUserLocationsId();
        List<Do> existingDoList = doService.getByStatusAndBusinessType();
        if (!locationList.isEmpty() && !helperService.isBusinessHeadUser()) {
            for (Do doModel : existingDoList) {
                Integer regionLocationId = doModel.getContract().getWarehouse().getRegLoc();
                Optional<RegionLocation> regionLocation = regionLocationService.get(regionLocationId);
                if (regionLocation.isPresent()) {
                    if (locationList.contains(regionLocation.get().getLocation().getId())) {
                        doList.add(doModel);
                    }
                }
            }
        } else {
            doList = existingDoList;
        }
        modelAndView.addObject("models", doList);
        modelAndView.addObject("friendlyStatusMap", constantService.getFRIENDLY_SR_STATUS());
        modelAndView.setViewName("admin/warehouse/do/index");
        LOGGER.info("Do index action returned {} models", doList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Do edit action called");
        ModelAndView modelAndView = new ModelAndView();
        DoForm model = new DoForm();
        Do doModel = new Do();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("Do edit action called on existing model with id {}", identifier);
            Optional<Do> existingDoModel = doService.get(identifier);
            if (existingDoModel.isPresent()) {
                doModel = existingDoModel.get();
            }
            model.setDoModel(doModel);
        } else {
            model.setDoModel(doModel);
            if (helperService.isGeneralUser()) {
                model.getDoModel().setStatus(constantService.getDO_STATUS().get("underprocess"));
                model.getDoModel().setDoNumber(helperService.getEpoch().toString());
                model.getDoModel().setOutward(false);
            } else {
                modelAndView.setViewName("redirect:/error/403");
                return modelAndView;
            }
        }

        List<BusinessType> businessTypeList = helperService.getUserBusinessType();
        List<Contract> contractList = contractService.getApprovedContract();
        modelAndView.addObject("model", model);
        modelAndView.addObject("contracts", contractList);
        modelAndView.addObject("businessTypes", businessTypeList);
        modelAndView.setViewName("admin/warehouse/do/edit");
        LOGGER.info("Do edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") DoForm doForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Do save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/do/edit");
        if (!result.hasErrors()) {
            try {
                Do doModel = doForm.getDoModel();
                Do existingDo = new Do();
                if (doModel.getId() != null) {
                    Optional<Do> existingDOModel = doService.get(doModel.getId());
                    if (existingDOModel.isPresent()) {
                        existingDo = existingDOModel.get();
                    }
                }
                // save letter document
                if (doForm.getDoLetterUpload().isEmpty()) {
                    if (existingDo.getId() != null && existingDo.getDoLetterDocument() != null) {
                        doModel.setDoLetterDocument(existingDo.getDoLetterDocument());
                    }
                } else {
                    MultipartFile file = doForm.getDoLetterUpload();
                    com.kamadhenu.warehousemanagementsystem.model.db.document.Document document =
                            documentService.store(file);
                    doModel.setDoLetterDocument(document);
                }
                // save email document
                if (doForm.getDoEmailUpload().isEmpty()) {
                    if (existingDo.getId() != null && existingDo.getDoEmailDocument() != null) {
                        doModel.setDoEmailDocument(existingDo.getDoEmailDocument());
                    }
                } else {
                    MultipartFile file = doForm.getDoEmailUpload();
                    com.kamadhenu.warehousemanagementsystem.model.db.document.Document document =
                            documentService.store(file);
                    doModel.setDoEmailDocument(document);
                }
                // save kyc document
                if (doForm.getDoKycUpload().isEmpty()) {
                    if (existingDo.getId() != null && existingDo.getDoKycDocument() != null) {
                        doModel.setDoKycDocument(existingDo.getDoKycDocument());
                    }
                } else {
                    MultipartFile file = doForm.getDoKycUpload();
                    com.kamadhenu.warehousemanagementsystem.model.db.document.Document document =
                            documentService.store(file);
                    doModel.setDoKycDocument(document);
                }

                doService.edit(doForm.getDoModel());
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Do save action successful");
                modelAndView.setViewName("redirect:/admin/do-inward-truck-bag/edit/" + doForm.getDoModel()
                        .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("Do save action had errors {}", result.getAllErrors());
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
        LOGGER.info("Do delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            doService.delete(id);
            LOGGER.info("Do delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/do/index");
        return modelAndView;
    }

    /**
     * Review approval action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/review-approval/{do}"})
    public ModelAndView reviewApproval(@PathVariable("do") Integer doId) {
        LOGGER.info("Do review approval action called");
        ModelAndView modelAndView = new ModelAndView();
        com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Do doDomainModel =
                new com.kamadhenu.warehousemanagementsystem.model.domain.warehouse.Do();
        DoRemarkStatusForm doRemarkStatusForm = new DoRemarkStatusForm();
        Optional<Do> doModel = doService.get(doId);
        if (doModel.isPresent()) {
            Do existingDoModel = doModel.get();
            doRemarkStatusForm.setDoModel(existingDoModel);
            doDomainModel = doDomainService.get(existingDoModel);
        }

        List<String> statusList = constantService.getREVIEWER_APPROVAL_DO_STATUS();
        modelAndView.addObject("model", doRemarkStatusForm);
        modelAndView.addObject("statuses", statusList);
        modelAndView.addObject("doDomain", doDomainModel);
        modelAndView.setViewName("admin/warehouse/do/review-approval");
        LOGGER.info("Do review approval action returned {} model", doRemarkStatusForm);
        return modelAndView;
    }

    /**
     * Review approval save action
     *
     * @return the model and view
     */
    @PostMapping("/review-approval-save")
    public ModelAndView reviewApprovalsave(
            @Valid @ModelAttribute("model") DoRemarkStatusForm doRemarkStatusForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("Do review approval save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/do/index");
        if (!result.hasErrors()) {
            try {
                Do doModel = doRemarkStatusForm.getDoModel();
                doModel.setStatus(doRemarkStatusForm.getStatus());
                doService.edit(doModel);
                DoRemark doRemark = new DoRemark();
                doRemark.setRemark(doRemarkStatusForm.getRemark());
                doRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                doRemark.setRemarkDate(new Date());
                doRemark.setDoModel(doModel);
                doRemark.setUser(helperService.getLoggedInDbUser());
                doRemarkService.edit(doRemark);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("Do review approval save action successful");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error("Do review approval save action had errors {}", result.getAllErrors());
        }
        return modelAndView;
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
}
