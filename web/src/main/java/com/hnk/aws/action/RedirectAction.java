//
// @Copyright 2013
//
package com.hnk.aws.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Properties;

/**
 * @author hnguyen
 */
@Controller
@RequestMapping("/redirect")
public class RedirectAction {
    @Autowired
    Properties thirdPartyGoogle;

    @RequestMapping("googleLogin")
    public String redirectGoogleLogin() {
        // forming redirect URL.
        StringBuilder sb = new StringBuilder();
        sb.append("https://accounts.google.com/o/oauth2/auth?");
        sb.append("response_type=" + thirdPartyGoogle.get("responseType"));
        sb.append("&client_id=" + thirdPartyGoogle.get("clientId"));
        sb.append("&redirect_uri=" + thirdPartyGoogle.get("redirectUrl"));
        sb.append("&scope=" + thirdPartyGoogle.get("scope"));
        return "redirect:" + sb.toString();
    }
}
