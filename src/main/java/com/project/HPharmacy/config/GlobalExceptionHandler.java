package com.project.HPharmacy.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(Throwable throwable) {
        logger.error("An unexpected error occurred", throwable);

        ModelAndView modelAndView = new ModelAndView("error");
        String errorMessage = (throwable != null && throwable.getMessage() != null)
                ? throwable.getMessage()
                : "An unknown error occurred.";
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }@ExceptionHandler(NoResourceFoundException.class)

    public String handleNoResourceFound(NoResourceFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}


//public class GlobalExceptionHandler {
//    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(Throwable.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ModelAndView exception(final Throwable throwable) {
//        logger.error("Exception during execution of SpringSecurity application", throwable);
//
//        ModelAndView modelAndView = new ModelAndView("/error");
//        String errorMessage = (throwable != null ? throwable.toString() : "Unknown error");
//        modelAndView.addObject("errorMessage", errorMessage);
//        return modelAndView;
//    }
//}