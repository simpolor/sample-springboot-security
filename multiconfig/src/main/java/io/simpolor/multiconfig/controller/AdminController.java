package io.simpolor.multiconfig.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public ModelAndView admin(ModelAndView mav) {

        mav.setViewName("admin/admin");
        return mav;
    }

    @GetMapping("/admin/login")
    public ModelAndView login(ModelAndView mav) {

        mav.setViewName("admin/login");
        return mav;
    }

}

