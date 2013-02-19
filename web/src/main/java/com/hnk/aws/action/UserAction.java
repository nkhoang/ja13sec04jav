package com.hnk.aws.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
@RequestMapping("user")
public class UserAction {
    @RequestMapping("/register")
    public ModelAndView registerUser() {
        return new ModelAndView("user/register");
    }

}
