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
public class BadTokenException extends AuthenticationException {
    public BadTokenException(String message) {
        super(message);
    }
}
