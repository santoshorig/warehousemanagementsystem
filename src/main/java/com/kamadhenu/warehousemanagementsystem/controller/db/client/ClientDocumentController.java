package com.kamadhenu.warehousemanagementsystem.controller.db.client;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.client.ClientDocumentForm;
import com.kamadhenu.warehousemanagementsystem.form.client.ClientDocumentsForm;
import com.kamadhenu.warehousemanagementsystem.model.db.client.Client;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientDocument;
import com.kamadhenu.warehousemanagementsystem.model.db.client.ClientRemark;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientDocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.client.ClientService;
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
 * <h1>ClientDocument controller</h1>
 * <p>
 * This client document controller class provides the CRUD actions for client documents
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/client-document")
public class ClientDocumentController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDocumentController.class);

    @Autowired
    private ClientDocumentService clientDocumentService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private DocumentPurposeService documentPurposeService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRemarkService clientRemarkService;

    @Autowired
    private HelperService helperService;

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/client/{id}"})
    public ModelAndView edit(@PathVariable("id") Integer id) {
        LOGGER.info("ClientDocument edit action called");
        ModelAndView modelAndView = new ModelAndView();
        ClientDocumentsForm model = new ClientDocumentsForm();
        model.setClientDocumentForm(new ArrayList<>());
        Optional<Client> client = clientService.get(id);
        if (client.isPresent()) {
            Client clientModel = client.get();
            List<ClientDocument> clientDocumentList = clientDocumentService.getByClient(clientModel);
            for (ClientDocument clientDocument : clientDocumentList) {
                ClientDocumentForm clientDocumentForm = new ClientDocumentForm();
                clientDocumentForm.setClientDocument(clientDocument);
                model.getClientDocumentForm().add(clientDocumentForm);
            }
            Optional<DocumentPurpose> documentPurpose =
                    documentPurposeService.getByName(constantService.getDOCUMENT_PURPOSE().get("client"));
            if (documentPurpose.isPresent()) {
                List<DocumentType> documentTypeList = documentTypeService.getByDocumentPurpose(documentPurpose.get());
                for (ClientDocument clientDocument : clientDocumentList) {
                    documentTypeList.remove(clientDocument.getDocumentType());
                }
                for (DocumentType documentType : documentTypeList) {
                    ClientDocument clientDocument = new ClientDocument();
                    clientDocument.setDocumentType(documentType);
                    clientDocument.setClient(clientModel);
                    ClientDocumentForm clientDocumentForm = new ClientDocumentForm();
                    clientDocumentForm.setClientDocument(clientDocument);
                    model.getClientDocumentForm().add(clientDocumentForm);
                }
            }

            String clientType = constantService.getDOCUMENT_PURPOSE().get("clienttype") + "-" + clientModel
                    .getClientType().getName().toLowerCase();
            Optional<DocumentPurpose> documentPurposeForClientType =
                    documentPurposeService.getByName(clientType);
            if (documentPurposeForClientType.isPresent()) {
                List<DocumentType> documentTypeList =
                        documentTypeService.getByDocumentPurpose(documentPurposeForClientType.get());
                for (ClientDocument clientDocument : clientDocumentList) {
                    documentTypeList.remove(clientDocument.getDocumentType());
                }
                for (DocumentType documentType : documentTypeList) {
                    ClientDocument clientDocument = new ClientDocument();
                    clientDocument.setDocumentType(documentType);
                    clientDocument.setClient(clientModel);
                    ClientDocumentForm clientDocumentForm = new ClientDocumentForm();
                    clientDocumentForm.setClientDocument(clientDocument);
                    model.getClientDocumentForm().add(clientDocumentForm);
                }
            }

            String clientConstitution =
                    constantService.getDOCUMENT_PURPOSE().get("clientconstitution") + "-" + clientModel
                            .getClientConstitution().getName().toLowerCase();
            Optional<DocumentPurpose> documentPurposeForClientConstitution =
                    documentPurposeService.getByName(clientConstitution);
            if (documentPurposeForClientConstitution.isPresent()) {
                List<DocumentType> documentTypeList =
                        documentTypeService.getByDocumentPurpose(documentPurposeForClientConstitution.get());
                for (ClientDocument clientDocument : clientDocumentList) {
                    documentTypeList.remove(clientDocument.getDocumentType());
                }
                for (DocumentType documentType : documentTypeList) {
                    ClientDocument clientDocument = new ClientDocument();
                    clientDocument.setDocumentType(documentType);
                    clientDocument.setClient(clientModel);
                    ClientDocumentForm clientDocumentForm = new ClientDocumentForm();
                    clientDocumentForm.setClientDocument(clientDocument);
                    model.getClientDocumentForm().add(clientDocumentForm);
                }
            }

            model.setClient(clientModel);
            modelAndView.addObject("client", clientModel);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/client/client-document/edit");
        LOGGER.info("ClientDocument edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") ClientDocumentsForm clientDocumentsForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("ClientDocument save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/client/client-document/edit");
        if (!result.hasErrors()) {
            try {
                for (ClientDocumentForm clientDocumentForm : clientDocumentsForm.getClientDocumentForm()) {
                    if (!clientDocumentForm.getUpload().isEmpty()) {
                        MultipartFile file = clientDocumentForm.getUpload();
                        Document document = documentService.store(file);
                        ///TODO need to delete old documents as well
                        clientDocumentForm.getClientDocument().setDocument(document);
                        clientDocumentService.edit(clientDocumentForm.getClientDocument());
                    }
                }
                Client client = clientDocumentsForm.getClient();
                ClientRemark clientRemark = new ClientRemark();
                clientRemark.setRemark(clientDocumentsForm.getRemark());
                clientRemark.setStatus(constantService.getREMARK_STATUS().get("unread"));
                clientRemark.setRemarkDate(new Date());
                clientRemark.setClient(client);
                clientRemark.setUser(helperService.getLoggedInDbUser());
                clientRemarkService.edit(clientRemark);
                if (clientDocumentsForm.getReview()) {
                    if (client.getStatus()
                            .equalsIgnoreCase(constantService.getCLIENT_STATUS().get("pendingforbusinessreview"))) {
                        client.setStatus(constantService.getCLIENT_STATUS().get("pendingforriskassessment"));
                    } else {
                        client.setStatus(constantService.getCLIENT_STATUS().get("pendingforbusinessreview"));
                    }
                } else {
                    client.setStatus(constantService.getCLIENT_STATUS().get("rework"));
                }
                clientService.edit(client);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("ClientDocument save action successful");
                modelAndView.setViewName("redirect:/admin/client/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView
                        .setViewName("redirect:/admin/client-document/edit/client/" + clientDocumentsForm.getClient()
                                .getId());
            }
        } else {
            LOGGER.error("ClientDocument save action had errors {}", result.getAllErrors());
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
        LOGGER.info("ClientDocument delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer clientId = null;
        try {
            Optional<ClientDocument> clientDocument = clientDocumentService.get(id);
            if (clientDocument.isPresent()) {
                clientId = clientDocument.get().getClient().getId();
                clientDocumentService.delete(id);
                LOGGER.info("ClientDocument delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/client-document/index/client/" + clientId);
        return modelAndView;
    }

}
