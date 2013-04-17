package com.hnk.aws.web.security.google;


import org.springframework.security.core.AuthenticationException;

/**
 * @author hnguyen
 */
public class BadTokenException extends AuthenticationException {
    public BadTokenException(String message) {
        super(message);
    }
}
