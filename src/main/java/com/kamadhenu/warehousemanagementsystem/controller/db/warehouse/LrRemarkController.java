package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Lr;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.LrRemark;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.LrRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.LrService;
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
 * <h1>LrRemark controller</h1>
 * <p>
 * This lr remark controller class provides the CRUD actions for lr remarks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/lr-remark")
public class LrRemarkController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LrRemarkController.class);

    @Autowired
    private LrRemarkService lrRemarkService;

    @Autowired
    private LrService lrService;

    @Autowired
    private HelperService helperService;

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("LrRemark delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            lrRemarkService.delete(id);
            LOGGER.info("LrRemark delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/lr-remark/index");
        return modelAndView;
    }

    /**
     * Get remarks by lr action
     *
     * @param id the id of lr
     * @return json
     */
    @RequestMapping(value = "/getByLr/{id}", method = RequestMethod.GET)
    public ResponseEntity getByLr(@PathVariable Integer id) {
        Optional<Lr> lr = lrService.get(id);
        List<LrRemark> lrRemarkList = new ArrayList<>();
        if (lr.isPresent()) {
            Lr model = lr.get();
            lrRemarkList = lrRemarkService.getByLr(model);
        }
        return new ResponseEntity(lrRemarkList, HttpStatus.OK);
    }
}
