//
// @Copyright 2013
//
package com.hnk.aws.web.security.google;


import org.springframework.security.core.AuthenticationException;

/**
 * Implementation of {@code AuthenticationException} for Google Authentication.
 *
 * @author hnguyen
 */
public class BadCodeException extends AuthenticationException {
    public BadCodeException(String message) {
        super(message);
    }
}
