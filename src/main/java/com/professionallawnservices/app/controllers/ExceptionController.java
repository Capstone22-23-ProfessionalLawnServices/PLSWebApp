package com.professionallawnservices.app.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(
            Exception ex,
            Model model
    )
    {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }
}