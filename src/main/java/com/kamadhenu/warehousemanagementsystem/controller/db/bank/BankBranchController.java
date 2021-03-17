package com.kamadhenu.warehousemanagementsystem.controller.db.bank;

import com.kamadhenu.warehousemanagementsystem.controller.AbstractController;
import com.kamadhenu.warehousemanagementsystem.model.db.bank.Bank;
import com.kamadhenu.warehousemanagementsystem.model.db.bank.BankBranch;
import com.kamadhenu.warehousemanagementsystem.service.db.bank.BankBranchService;
import com.kamadhenu.warehousemanagementsystem.service.db.bank.BankService;
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
 * <h1>BankBranch controller</h1>
 * <p>
 * This bank branch controller class provides the CRUD actions for bank branches
 *
 * @author Kamadhenu
 */
@Controller
@RequestMapping("/admin/bank-branch")
public class BankBranchController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankBranchController.class);

    @Autowired
    private BankBranchService bankBranchService;

    @Autowired
    private BankService bankService;

    @Autowired
    private HelperService helperService;

    /**
     * Index action
     *
     * @return the model and view
     */
    @GetMapping("/index")
    public ModelAndView index() {
        LOGGER.info("BankBranch index action called");
        ModelAndView modelAndView = new ModelAndView();
        List<BankBranch> bankBranchList = bankBranchService.getAll();
        modelAndView.addObject("models", bankBranchList);
        modelAndView.setViewName("admin/bank/bank-branch/index");
        LOGGER.info("BankBranch index action returned {} models", bankBranchList.size());
        return modelAndView;
    }

    /**
     * Edit action
     *
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/edit", "/edit/{id}"})
    public ModelAndView edit(@PathVariable("id") Optional<Integer> id) {
        LOGGER.info("BankBranch edit action called");
        ModelAndView modelAndView = new ModelAndView();
        BankBranch model = new BankBranch();
        if (id.isPresent()) {
            Integer identifier = id.map(Integer::intValue).get();
            LOGGER.info("BankBranch edit action called on existing model with id {}", identifier);
            Optional<BankBranch> bankBranch = bankBranchService.get(identifier);
            if (bankBranch.isPresent()) {
                model = bankBranch.get();
            }
        }

        List<Bank> bankList = bankService.getAll();
        modelAndView.addObject("model", model);
        modelAndView.addObject("banks", bankList);
        modelAndView.setViewName("admin/bank/bank-branch/edit");
        LOGGER.info("BankBranch edit action returned {} model", model);
        return modelAndView;
    }

    /**
     * Save action
     *
     * @return the model and view
     */
    @PostMapping("/save")
    public ModelAndView save(
            @Valid @ModelAttribute("model") BankBranch bank,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        LOGGER.info("BankBranch save action called");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/admin/bank/bank-branch/edit");
        if (!result.hasErrors()) {
            try {
                bankBranchService.edit(bank);
                addFlashMessage(redirectAttributes, true);
                LOGGER.info("BankBranch save action successful");
                modelAndView.setViewName("redirect:/admin/bank-branch/index");
            } catch (Exception e) {
                addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
                LOGGER.error(e.getLocalizedMessage());
                LOGGER.debug(helperService.stackTraceToString(e));
                modelAndView.addObject("error", e);
                modelAndView.setViewName("error/500");
            }
        } else {
            LOGGER.error("BankBranch save action had errors {}", result.getAllErrors());
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
        LOGGER.info("BankBranch delete action called on id {}", id);
        ModelAndView modelAndView = new ModelAndView();
        try {
            bankBranchService.delete(id);
            LOGGER.info("BankBranch delete action successful");
            addFlashMessage(redirectAttributes, true);
        } catch (Exception e) {
            addFlashMessage(redirectAttributes, false, e.getLocalizedMessage());
            LOGGER.error(e.getLocalizedMessage());
            LOGGER.debug(helperService.stackTraceToString(e));
        }

        modelAndView.setViewName("redirect:/admin/bank-branch/index");
        return modelAndView;
    }

}
