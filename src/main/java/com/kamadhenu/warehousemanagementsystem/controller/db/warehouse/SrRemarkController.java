package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Sr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.SrRemark;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.SrRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.SrService;
import com.kamadhenu.warehousemanagementsystem.service.domain.common.HelperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1>SrRemark controller</h1>
 * <p>
 * This sr remark controller class provides the CRUD actions for sr remarks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/sr-remark")
public class SrRemarkController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SrRemarkController.class);

    @Autowired
    private SrRemarkService srRemarkService;

    @Autowired
    private SrService srService;

    @Autowired
    private HelperService helperService;

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("SrRemark delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            srRemarkService.delete(id);
            LOGGER.info("SrRemark delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/sr-remark/index");
        return modelAndView;
    }

    /**
     * Get remarks by sr action
     *
     * @param id the id of sr
     * @return json
     */
    @RequestMapping(value = "/getBySr/{id}", method = RequestMethod.GET)
    public ResponseEntity getBySr(@PathVariable Integer id) {
        Optional<Sr> sr = srService.get(id);
        List<SrRemark> srRemarkList = new ArrayList<>();
        if (sr.isPresent()) {
            Sr model = sr.get();
            srRemarkList = srRemarkService.getBySr(model);
        }
        return new ResponseEntity(srRemarkList, HttpStatus.OK);
    }
}
