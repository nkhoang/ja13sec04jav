package com.hnk.aws.web.security.google;

import org.springframework.security.core.AuthenticationException;

/**
 * @author hnguyen
 */
public class AuthenticationFailureException extends AuthenticationException {

    public AuthenticationFailureException(String message) {
        super(message);
    }
}
