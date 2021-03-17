package com.kamadhenu.warehousemanagementsystem.controller.db.document;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentType;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentPurposeService;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentTypeService;
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
import java.util.List;
import java.util.Optional;

/**
 * <h1>DocumentType controller</h1>
 * <p>
 * This document type controller class provides the CRUD actions for document types
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/document-type")
public class DocumentTypeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentTypeController.class);

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private DocumentPurposeService documentPurposeService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("DocumentType index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<DocumentType> documentTypeList = documentTypeService.getAll();
        modelAndView.addObject("models", documentTypeList);
        modelAndView.setViewName("admin/document/document-type/index");
        LOGGER.info("DocumentType index action returned {} models", documentTypeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("DocumentType edit action called");
        ModelAndView modelAndView = new ModelAndView();
        DocumentType model = new DocumentType();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("DocumentType edit action called on existing model with id {}", identifier);
            Optional<DocumentType> document = documentTypeService.get(identifier);
            if (document.isPresent()) {
                model = document.get();
            }
        }

        List<DocumentPurpose> documentPurposeList = documentPurposeService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("documentPurposes", documentPurposeList);
        modelAndView.setViewName("admin/document/document-type/edit");
        LOGGER.info("DocumentType edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") DocumentType documentType,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("DocumentType save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/document/document-type/edit");
        if (!result.hasErrors()) {
            try {
                documentTypeService.edit(documentType);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("DocumentType save action successful");
                modelAndView.setViewName("redirect:/admin/document-type/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false);
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("DocumentType save action had errors {}", result.getAllErrors());
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
        LOGGER.info("DocumentType delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            documentTypeService.delete(id);
            LOGGER.info("DocumentType delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/document-type/index");
        return modelAndView;
    }

}
