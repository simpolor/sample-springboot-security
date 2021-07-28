package io.simpolor.resource.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class WelcomeController {

    @RequestMapping({"/", "/index", "/welcome"})
    public String welcome(ModelAndView mav) {

        return "welcome";
    }
}

