package com.kamadhenu.warehousemanagementsystem.controller;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.exceptions.TemplateProcessingException;

/**
 * This controller handled the exceptions.
 * 
 * @author Arpit Gupta
 *
 */
@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(value = { SQLException.class })
  public void handleSQLException(SQLException ex) {
    System.out.println("print  handleConflict");
  }

  @ExceptionHandler(value = { DataIntegrityViolationException.class })
  public void handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("error", new IllegalArgumentException("Invalid input parameters: ", ex));
    modelAndView.setViewName("error/500");
  }

  @ExceptionHandler(value = { PersistenceException.class })
  public void handlePersistenceException(PersistenceException ex) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("error", new PersistenceException("UI Exception: ", ex));
    modelAndView.setViewName("error/500");
  }
  
  @ExceptionHandler(value = { TemplateProcessingException.class })
  public void handleTemplateProcessingException(TemplateProcessingException ex) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("error", new PersistenceException("UI Exception: ", ex));
    modelAndView.setViewName("error/500");
  }
  
  @ExceptionHandler(value = { Exception.class })
  public void handleConflict(Exception ex) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("error", new Exception("Internal Server Exception: ", ex));
    modelAndView.setViewName("error/500");
  }
}
