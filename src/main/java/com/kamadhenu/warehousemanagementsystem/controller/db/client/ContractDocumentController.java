package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.client.ContractDocumentForm;
import com.kamadhenu.warehousemanagementsystem.form.client.ContractDocumentsForm;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Contract;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractDocument;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ContractRemark;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractDocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ContractService;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentPurposeService;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentTypeService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.ConstantService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * <h1>ContractDocument controller</h1>
 * <p>
 * This contract document controller class provides the CRUD actions for contract documents
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/contract-document")
public class ContractDocumentController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractDocumentController.class);

    @Autowired
    private ContractDocumentService contractDocumentService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private DocumentPurposeService documentPurposeService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractRemarkService contractRemarkService;

    @Autowired
    private HelperService helperService;

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/contract/{id}"})
    public ModelAndView edit(@PathVariable("id") Integer id) {
        LOGGER.info("ContractDocument edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ContractDocumentsForm model = new ContractDocumentsForm();
        model.setContractDocumentForm(new ArrayList<>());
        Optional<Contract> contract = contractService.get(id);
        if (contract.isPresent()) {
            Contract contractModel = contract.get();
            List<ContractDocument> contractDocumentList = contractDocumentService.getByContract(contractModel);
            for (ContractDocument contractDocument : contractDocumentList) {
                ContractDocumentForm contractDocumentForm = new ContractDocumentForm();
                contractDocumentForm.setContractDocument(contractDocument);
                model.getContractDocumentForm().add(contractDocumentForm);
            }
            Optional<DocumentPurpose> documentPurpose =
                    documentPurposeService.getByName(constantService.getDOCUMENT_PURPOSE().get("contract"));
            if (documentPurpose.isPresent()) {
                List<DocumentType> documentTypeList = documentTypeService.getByDocumentPurpose(documentPurpose.get());
                for (ContractDocument contractDocument : contractDocumentList) {
                    documentTypeList.remove(contractDocument.getDocumentType());
                }
                for (DocumentType documentType : documentTypeList) {
                    ContractDocument contractDocument = new ContractDocument();
                    contractDocument.setDocumentType(documentType);
                    contractDocument.setContract(contractModel);
                    ContractDocumentForm contractDocumentForm = new ContractDocumentForm();
                    contractDocumentForm.setContractDocument(contractDocument);
                    model.getContractDocumentForm().add(contractDocumentForm);
                }
            }

            model.setContract(contractModel);
            modelAndView.addObject("contract", contractModel);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/client/contract-document/edit");
        LOGGER.info("ContractDocument edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ContractDocumentsForm contractDocumentsForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ContractDocument save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/client/contract-document/edit");
        if (!result.hasErrors()) {
            try {
                for (ContractDocumentForm contractDocumentForm : contractDocumentsForm.getContractDocumentForm()) {
                    if (!contractDocumentForm.getUpload().isEmpty()) {
                        MultipartFile file = contractDocumentForm.getUpload();
                        Document document = documentService.store(file);
                        ///TODO need to delete old documents as well
                        contractDocumentForm.getContractDocument().setDocument(document);
                        contractDocumentService.edit(contractDocumentForm.getContractDocument());
                    }
                }
                Contract contract = contractDocumentsForm.getContract();
                ContractRemark contractRemark = new ContractRemark();
                contractRemark.setRemark(contractDocumentsForm.getRemark());
                contractRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                contractRemark.setRemarkDate(new Date());
                contractRemark.setContract(contract);
                contractRemark.setUser(helperService.getLoggedInDbUser());
                contractRemarkService.edit(contractRemark);
                if (contractDocumentsForm.getReview()) {
                    if (contract.getStatus()
                            .equalsIgnoreCase(constantService.getCONTRACT_STATUS().get("pendingforopsapproval"))) {
                        contract.setStatus(constantService.getCONTRACT_STATUS().get("pendingforbusinessapproval"));
                    } else {
                        contract.setStatus(constantService.getCONTRACT_STATUS().get("pendingforopsapproval"));
                    }
                } else {
                    if (contract.getStatus()
                            .equalsIgnoreCase(constantService.getCONTRACT_STATUS().get("pendingforopsapproval"))) {
                        contract.setStatus(constantService.getCONTRACT_STATUS().get("rework"));
                    }
                }
                contractService.edit(contract);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ContractDocument save action successful");
                modelAndView.setViewName("redirect:/admin/contract/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView
                        .setViewName("redirect:/admin/contract-document/edit/contract/" + contractDocumentsForm
                                .getContract()
                                .getId());
            }
        } else {
            LOGGER.error("ContractDocument save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ContractDocument delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer contractId = null;
        try {
            Optional<ContractDocument> contractDocument = contractDocumentService.get(id);
            if (contractDocument.isPresent()) {
                contractId = contractDocument.get().getContract().getId();
                contractDocumentService.delete(id);
                LOGGER.info("ContractDocument delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/contract-document/index/contract/" + contractId);
        return modelAndView;
    }

}
