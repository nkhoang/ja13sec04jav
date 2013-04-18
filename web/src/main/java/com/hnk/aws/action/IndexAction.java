package com.hnk.aws.action;

import com.hnk.aws.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Hashtable;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexAction {
    @RequestMapping("index")
    public ModelAndView welcome() {
        // load user authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal != null && principal instanceof User) {
                currentUser = (User) principal;
            }
        }
        Map<String, Object> model = new Hashtable<String, Object>();
        return new ModelAndView("index", model);
    }
}
