package com.kamadhenu.warehousemanagementsystem.controller.db.document;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.document.Document;
import com.kamadhenu.warehousemanagementsystem.service.db.document.DocumentService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * <h1>Document controller</h1>
 * <p>
 * This document controller class provides the CRUD actions for documents
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/document")
public class DocumentController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentService documentService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("Document index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<Document> documentList = documentService.getAll();
        modelAndView.addObject("models", documentList);
        modelAndView.setViewName("admin/document/document/index");
        LOGGER.info("Document index action returned {} models", documentList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("Document edit action called but this doesn't have edit view");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/document/index");
        return modelAndView;
    }

    /**
     * Download document
     *
     * @param id
     * @return
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Integer id) throws FileNotFoundException {
        LOGGER.info("Document download action called");
        Optional<Document> document = documentService.get(id);
        if (document.isPresent()) {
            Document model = document.get();
            LOGGER.info("Document download action returned {} model", model);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(model.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + model.getFileName() + "\"")
                    .body(new ByteArrayResource(model.getFileContent()));
        } else {
            throw new FileNotFoundException("File not found with id " + id);
        }
    }

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("Document delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            documentService.delete(id);
            LOGGER.info("Document delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/document/index");
        return modelAndView;
    }

}
