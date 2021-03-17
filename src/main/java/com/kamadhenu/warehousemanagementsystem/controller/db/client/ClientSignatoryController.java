package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.client.ClientSignatoryForm;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientModeOfOperation;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientSignatory;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientModeOfOperationService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientSignatoryService;
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
import java.util.List;
import java.util.Optional;

/**
 * <h1>ClientSignatory controller</h1>
 * <p>
 * This client signatory controller class provides the CRUD actions for client signatories
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/client-signatory")
public class ClientSignatoryController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSignatoryController.class);

    @Autowired
    private ClientSignatoryService clientSignatoryService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientModeOfOperationService clientModeOfOperationService;

    @Autowired
    private DocumentPurposeService documentPurposeService;

    @Autowired
    private DocumentTypeService documentTypeService;

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
    @RequestMapping(method = RequestMethod.GET, value = {"/index/client/{id}"})
    public ModelAndView index(@PathVariable("id") Integer id) {
        LOGGER.info("ClientSignatory index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<ClientSignatory> clientSignatoryList = new ArrayList<ClientSignatory>();
        Optional<Client> client = clientService.get(id);
        if (client.isPresent()) {
            Client clientModel = client.get();
            clientSignatoryList = clientSignatoryService.getByClient(clientModel);
            modelAndView.addObject("client", clientModel);
        }
        if (clientSignatoryList.size() > 0) {
            modelAndView.addObject("models", clientSignatoryList);
            modelAndView.setViewName("admin/client/client-signatory/index");
        } else {
            modelAndView.setViewName("redirect:/admin/client-signatory/edit/client/" + id);
        }
        LOGGER.info("ClientSignatory index action returned {} models", clientSignatoryList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/client/{clientId}", "/edit/client/{clientId}/{id}"})
    public ModelAndView edit(@PathVariable("clientId") Integer clientId, @PathVariable("id") Optional<Integer> id) {
        LOGGER.info("ClientSignatory edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ClientSignatoryForm model = new ClientSignatoryForm();
        List<ClientSignatory> clientSignatoryList = new ArrayList<>();
        Optional<Client> client = clientService.get(clientId);
        if (client.isPresent()) {
            Client clientModel = client.get();
            ClientSignatory clientSignatoryModel = new ClientSignatory();
            if (id.isPresent()) {
                Integer identifier = id.map(Integer::intValue).get();
                Optional<ClientSignatory> clientSignatory = clientSignatoryService.get(identifier);
                if (clientSignatory.isPresent()) {
                    clientSignatoryModel = clientSignatory.get();
                }
            }
            clientSignatoryModel.setClient(clientModel);
            model.setClientSignatory(clientSignatoryModel);
            List<ClientSignatory> existingClientSignatoryList = clientSignatoryService.getByClient(clientModel);
            if (clientSignatoryModel.getId() != null) {
                if (existingClientSignatoryList.size() > 1) {
                    for (ClientSignatory clientSignatory : existingClientSignatoryList) {
                        if (clientSignatory.getId() != clientSignatoryModel.getId()) {
                            clientSignatoryList.add(clientSignatory);
                        }
                    }
                }
            } else {
                clientSignatoryList = existingClientSignatoryList;
            }
        }

        List<DocumentType> documentTypeList = documentTypeService.getAll();
        Optional<DocumentPurpose> documentPurpose =
                documentPurposeService.getByName(constantService.getDOCUMENT_PURPOSE().get("signatory"));
        if (documentPurpose.isPresent()) {
            documentTypeList = documentTypeService.getByDocumentPurpose(documentPurpose.get());
        }

        List<ClientModeOfOperation> clientModeOfOperationList = clientModeOfOperationService.getAll();
        List<String> titleList = constantService.getTITLES();
        modelAndView.addObject("model", model);
        modelAndView.addObject("clientModeOfOperations", clientModeOfOperationList);
        modelAndView.addObject("clientSignatories", clientSignatoryList);
        modelAndView.addObject("documentTypes", documentTypeList);
        modelAndView.addObject("titles", titleList);
        modelAndView.setViewName("admin/client/client-signatory/edit");
        LOGGER.info("ClientSignatory edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ClientSignatoryForm clientSignatoryForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ClientSignatory save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/client/client-signatory/edit");
        if (!result.hasErrors()) {
            try {
                if (clientSignatoryForm.getUpload().isEmpty()) {
                    if (clientSignatoryForm.getClientSignatory() != null && clientSignatoryForm.getClientSignatory()
                            .getId() != null) {
                        Optional<ClientSignatory> clientSignatory =
                                clientSignatoryService.get(clientSignatoryForm.getClientSignatory().getId());
                        if (clientSignatory.isPresent()) {
                            ClientSignatory clientSignatoryModel = clientSignatory.get();
                            clientSignatoryForm.getClientSignatory()
                                    .setSignatureDocument(clientSignatoryModel.getSignatureDocument());
                        }
                    }
                } else {
                    MultipartFile file = clientSignatoryForm.getUpload();
                    Document document = documentService.store(file);
                    clientSignatoryForm.getClientSignatory().setSignatureDocument(document);
                }
                clientSignatoryService.edit(clientSignatoryForm.getClientSignatory());
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ClientSignatory save action successful");
                modelAndView.setViewName("redirect:/admin/client-signatory/index/client/" + clientSignatoryForm
                        .getClientSignatory().getClient().getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.setViewName("redirect:/admin/client-document/edit/client/" + clientSignatoryForm
                        .getClientSignatory().getClient().getId());
            }
        } else {
            LOGGER.error("ClientSignatory save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ClientSignatory delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer clientId = null;
        try {
            Optional<ClientSignatory> clientSignatory = clientSignatoryService.get(id);
            if (clientSignatory.isPresent()) {
                ClientSignatory model = clientSignatory.get();
                clientId = model.getClient().getId();
                documentService.delete(model.getSignatureDocument().getId());
                clientSignatoryService.delete(id);
                LOGGER.info("ClientSignatory delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/client-signatory/index/client/" + clientId);
        return modelAndView;
    }
}
