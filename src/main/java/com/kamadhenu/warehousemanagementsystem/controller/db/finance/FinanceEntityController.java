package com.kamadhenu.warehousemanagementsystem.controller.db.finance;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.finance.FinanceEntity;
import com.kamadhenu.warehousemanagementsystem.service.db.finance.FinanceEntityService;
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
 * <h1>FinanceEntity controller</h1>
 * <p>
 * This finance entity controller class provides the CRUD actions for finance entities
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/finance-entity")
public class FinanceEntityController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinanceEntityController.class);

    @Autowired
    private FinanceEntityService financeEntityService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("FinanceEntity index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<FinanceEntity> financeEntityList = financeEntityService.getAll();
        modelAndView.addObject("models", financeEntityList);
        modelAndView.setViewName("admin/finance/finance-entity/index");
        LOGGER.info("FinanceEntity index action returned {} models", financeEntityList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("FinanceEntity edit action called");
        ModelAndView modelAndView = new ModelAndView();
        FinanceEntity model = new FinanceEntity();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("FinanceEntity edit action called on existing model with id {}", identifier);
            Optional<FinanceEntity> financeEntity = financeEntityService.get(identifier);
            if (financeEntity.isPresent()) {
                model = financeEntity.get();
            }
        }
        modelAndView.addObject("model", model);
        modelAndView.setViewName("admin/finance/finance-entity/edit");
        LOGGER.info("Bank edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") FinanceEntity financeEntity,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("FinanceEntity save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/finance/finance-entity/edit");
        if (!result.hasErrors()) {
            try {
                financeEntityService.edit(financeEntity);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("FinanceEntity save action successful");
                modelAndView.setViewName("redirect:/admin/finance-entity/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false);
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("FinanceEntity save action had errors {}", result.getAllErrors());
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
        LOGGER.info("FinanceEntity delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            financeEntityService.delete(id);
            LOGGER.info("FinanceEntity delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false);
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/finance-entity/index");
        return modelAndView;
    }

}
