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
        // forming google login
        /*'https://accounts.google.com/o/oauth2/auth?' +
                'response_type=${thirdPartyGoogle.responseType}&' +
                'client_id=${thirdPartyGoogle.clientId}&' +
                'redirect_uri=${thirdPartyGoogle.redirectUrl}&' +
                'scope=${thirdPartyGoogle.scope}';*/
        return "redirect:" + "http://google.com";
    }
}
