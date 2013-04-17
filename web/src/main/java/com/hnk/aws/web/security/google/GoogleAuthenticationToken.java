package com.hnk.aws.web.security.google;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @author hnguyen
 */
public class GoogleAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private String accessToken;

    public GoogleAuthenticationToken(String token) {
        super(null);
        this.accessToken = token;
    }

    @Override
    public Object getCredentials() {
        return this.accessToken;
    }

    /**
     * Always returns an empty string.
     */
    public Object getPrincipal() {
        return "";
    }
}
