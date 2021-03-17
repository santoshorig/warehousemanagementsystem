package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseDocumentForm;
import com.kamadhenu.warehousemanagementsystem.form.warehouse.WarehouseDocumentsForm;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Warehouse;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.WarehouseDocument;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentPurposeService;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentTypeService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseDocumentService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.WarehouseService;
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
 * <h1>WarehouseDocument controller</h1>
 * <p>
 * This warehouse document controller class provides the CRUD actions for warehouse documents
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/warehouse-document")
public class WarehouseDocumentController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseDocumentController.class);

    @Autowired
    private WarehouseDocumentService warehouseDocumentService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private DocumentPurposeService documentPurposeService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private HelperService helperService;

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit/warehouse/{id}"})
    public ModelAndView edit(@PathVariable("id") Integer id) {
        LOGGER.info("WarehouseDocument edit action called");
        ModelAndView modelAndView = new ModelAndView();
        WarehouseDocumentsForm model = new WarehouseDocumentsForm();
        model.setWarehouseDocumentForm(new ArrayList<>());
        Optional<Warehouse> warehouse = warehouseService.get(id);
        if (warehouse.isPresent()) {
            Warehouse warehouseModel = warehouse.get();
            List<WarehouseDocument> warehouseDocumentList = warehouseDocumentService.getByWarehouse(warehouseModel);
            for (WarehouseDocument WarehouseDocument : warehouseDocumentList) {
                WarehouseDocumentForm warehouseDocumentForm = new WarehouseDocumentForm();
                warehouseDocumentForm.setWarehouseDocument(WarehouseDocument);
                model.getWarehouseDocumentForm().add(warehouseDocumentForm);
            }
            Optional<DocumentPurpose> documentPurpose =
                    documentPurposeService.getByName(constantService.getDOCUMENT_PURPOSE().get("warehouse"));
            if (documentPurpose.isPresent()) {
                List<DocumentType> documentTypeList = documentTypeService.getByDocumentPurpose(documentPurpose.get());
                for (WarehouseDocument warehouseDocument : warehouseDocumentList) {
                    documentTypeList.remove(warehouseDocument.getDocumentType());
                }
                for (DocumentType documentType : documentTypeList) {
                    WarehouseDocument warehouseDocument = new WarehouseDocument();
                    warehouseDocument.setDocumentType(documentType);
                    warehouseDocument.setWarehouse(warehouseModel);
                    WarehouseDocumentForm WarehouseDocumentForm = new WarehouseDocumentForm();
                    WarehouseDocumentForm.setWarehouseDocument(warehouseDocument);
                    model.getWarehouseDocumentForm().add(WarehouseDocumentForm);
                }
            }

            model.setWarehouse(warehouseModel);
            modelAndView.addObject("warehouse", warehouseModel);
        }

        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/warehouse/warehouse-document/edit");
        LOGGER.info("WarehouseDocument edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") WarehouseDocumentsForm warehouseDocumentsForm,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("WarehouseDocument save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/warehouse/warehouse-document/edit");
        if (!result.hasErrors()) {
            try {
                for (WarehouseDocumentForm warehouseDocumentForm : warehouseDocumentsForm.getWarehouseDocumentForm()) {
                    if (!warehouseDocumentForm.getUpload().isEmpty()) {
                        MultipartFile file = warehouseDocumentForm.getUpload();
                        Document document = documentService.store(file);
                        ///TODO need to delete old documents as well
                        warehouseDocumentForm.getWarehouseDocument().setDocument(document);
                        warehouseDocumentService.edit(warehouseDocumentForm.getWarehouseDocument());
                    }
                }
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("WarehouseDocument save action successful");
                modelAndView
                        .setViewName("redirect:/admin/warehouse-inspection/edit/warehouse/" + warehouseDocumentsForm
                                .getWarehouse()
                                .getId());
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView
                        .setViewName("redirect:/admin/warehouse-document/edit/warehouse/" + warehouseDocumentsForm
                                .getWarehouse()
                                .getId());
            }
        } else {
            LOGGER.error("WarehouseDocument save action had errors {}", result.getAllErrors());
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
        LOGGER.info("WarehouseDocument delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        Integer warehouseId = null;
        try {
            Optional<WarehouseDocument> warehouseDocument = warehouseDocumentService.get(id);
            if (warehouseDocument.isPresent()) {
                warehouseId = warehouseDocument.get().getWarehouse().getId();
                warehouseDocumentService.delete(id);
                LOGGER.info("WarehouseDocument delete action successful");
                addFlashMessage(redirectAttributes, true);
            }
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/warehouse-document/index/warehouse/" + warehouseId);
        return modelAndView;
    }

}
