//
// @Copyright 2013
//
package com.hnk.aws.web.security.google;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementation of {@code AuthenticationProcessingFilter} to authenticate google login request.
 * <p/>
 * The default process url is {@code /oauth2}. This will be the {@code redirect_url} configuration parameter
 * included in the google authentication request. Either the user granted the permission to the application
 * or deny to grant the permission, the {@code redirect_url} will be called and it will be processed by the filter.
 * <p/>
 * For more information:
 * <a href="https://developers.google.com/accounts/docs/OAuth2WebServer">https://developers.google.com/accounts/docs/OAuth2WebServer</a>
 * <p/>
 * Error response will be:
 * <p/>
 * https://oauth2-login-demo.appspot.com/code?error=access_denied&state=/profile
 * <p/>
 * Success response will be:
 * <p/>
 * https://oauth2-login-demo.appspot.com/code?state=/profile&code=4/P7q7W91a-oMsCeLvIaQm6bTrgtp7
 *
 * @author hnguyen
 */
public class GoogleAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private static final String SPRING_SECURITY_GOOGLE_CODE_KEY = "code";
    private static final String SPRING_SECURITY_GOOGLE_STATE_KEY = "state";

    private String codeParameter = SPRING_SECURITY_GOOGLE_CODE_KEY;
    private String stateParameter = SPRING_SECURITY_GOOGLE_STATE_KEY;

    public GoogleAuthenticationProcessingFilter() {
        super("/oauth2");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // save the 'code' value to verify.
        String codeString = request.getParameter(codeParameter);
        GoogleAuthenticationToken token = new GoogleAuthenticationToken(codeString);

        return this.getAuthenticationManager().authenticate(token);
    }

    public void setCodeParameter(String codeParameter) {
        this.codeParameter = codeParameter;
    }

    public void setStateParameter(String stateParameter) {
        this.stateParameter = stateParameter;
    }
}