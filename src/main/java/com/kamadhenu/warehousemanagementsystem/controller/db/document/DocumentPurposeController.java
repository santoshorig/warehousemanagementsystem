package com.kamadhenu.warehousemanagementsystem.controller.db.document;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.document.DocumentPurpose;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentPurposeService;
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
 * <h1>DocumentPurpose controller</h1>
 * <p>
 * This document purpose controller class provides the CRUD actions for document purposes
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/document-purpose")
public class DocumentPurposeController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentPurposeController.class);

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
        LOGGER.info("DocumentPurpose index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<DocumentPurpose> documentPurposeList = documentPurposeService.getAll();
        modelAndView.addObject("models", documentPurposeList);
        modelAndView.setViewName("admin/document/document-purpose/index");
        LOGGER.info("DocumentPurpose index action returned {} models", documentPurposeList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("DocumentPurpose edit action called");
        ModelAndView modelAndView = new ModelAndView();
        DocumentPurpose model = new DocumentPurpose();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("DocumentPurpose edit action called on existing model with id {}", identifier);
            Optional<DocumentPurpose> document = documentPurposeService.get(identifier);
            if (document.isPresent()) {
                model = document.get();
            }
        }
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/document/document-purpose/edit");
        LOGGER.info("DocumentPurpose edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") DocumentPurpose documentPurpose,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("DocumentPurpose save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/document/document-purpose/edit");
        if (!result.hasErrors()) {
            try {
                documentPurposeService.edit(documentPurpose);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("DocumentPurpose save action successful");
                modelAndView.setViewName("redirect:/admin/document-purpose/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("DocumentPurpose save action had errors {}", result.getAllErrors());
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
        LOGGER.info("DocumentPurpose delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            documentPurposeService.delete(id);
            LOGGER.info("DocumentPurpose delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/document-purpose/index");
        return modelAndView;
    }

}
