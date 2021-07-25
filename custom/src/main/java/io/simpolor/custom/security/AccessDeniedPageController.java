package io.simpolor.custom.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 접근 권한이 없는 경우 해당 처리하기 위한 컨트롤러
 */
@Controller
public class AccessDeniedPageController {

    private final String ERROR_403_PATH = "/error/403";

    @GetMapping(value = "/access-denied-page")
    public ModelAndView handleError(ModelAndView mav, HttpServletRequest request) {

        mav.setViewName(ERROR_403_PATH);
        return mav;
    }
}
