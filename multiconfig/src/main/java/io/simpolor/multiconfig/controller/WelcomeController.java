package io.simpolor.multiconfig.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

    @RequestMapping({"/", "/index", "/welcome"})
    public ModelAndView welcome(ModelAndView mav) {

        mav.setViewName("/welcome");
        return mav;
    }
}

