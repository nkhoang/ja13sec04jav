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
 *
 * @author hnguyen
 */
public class GoogleAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private static final String SPRING_SECURITY_GOOGLE_TOKEN_KEY = "token";

    private String tokenParameter = SPRING_SECURITY_GOOGLE_TOKEN_KEY;

    public GoogleAuthenticationProcessingFilter() {
        super("/oauth2");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String tokenString = request.getParameter(tokenParameter);
        GoogleAuthenticationToken token = new GoogleAuthenticationToken(tokenString);

        return this.getAuthenticationManager().authenticate(token);
    }

    public void setTokenParameter(String tokenParameter) {
        this.tokenParameter = tokenParameter;
    }
}