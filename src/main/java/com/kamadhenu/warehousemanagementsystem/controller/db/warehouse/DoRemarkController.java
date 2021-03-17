package com.kamadhenu.warehousemanagementsystem.controller.db.warehouse;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.Do;
import com.kamadhenu.warehousemanagementsystem.model.db.warehouse.DoRemark;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.DoRemarkService;
import com.kamadhenu.warehousemanagementsystem.service.db.warehouse.DoService;
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
 * <h1>DoRemark controller</h1>
 * <p>
 * This do remark controller class provides the CRUD actions for do remarks
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/do-remark")
public class DoRemarkController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoRemarkController.class);

    @Autowired
    private DoRemarkService doRemarkService;

    @Autowired
    private DoService doService;

    @Autowired
    private HelperService helperService;

    /**
     * Delete action
     *
     * @return the model and view
     */
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        LOGGER.info("DoRemark delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            doRemarkService.delete(id);
            LOGGER.info("DoRemark delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/do-remark/index");
        return modelAndView;
    }

    /**
     * Get remarks by do action
     *
     * @param id the id of do
     * @return json
     */
    @RequestMapping(value = "/getByDo/{id}", method = RequestMethod.GET)
    public ResponseEntity getByWarehouse(@PathVariable Integer id) {
        Optional<Do> doModel = doService.get(id);
        List<DoRemark> doRemarkList = new ArrayList<>();
        if (doModel.isPresent()) {
            Do model = doModel.get();
            doRemarkList = doRemarkService.getByDo(model);
        }
        return new ResponseEntity(doRemarkList, HttpStatus.OK);
    }
}
