package com.hnk.aws.web.security.google;

import com.hnk.aws.web.security.google.model.GoogleUserInfo;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @author hnguyen
 */
public class GoogleAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private String accessToken;
    private Object principle;

    public GoogleAuthenticationToken(String token, Object principle) {
        super(null);
        this.accessToken = token;
        this.principle = principle;
    }

    public GoogleAuthenticationToken(String token) {
        super(null);
        this.accessToken = token;
    }

    @Override
    public Object getCredentials() {
        return this.accessToken;
    }

    @Override
    public Object getPrincipal() {
        return principle;
    }

    public void setPrinciple(Object principle) {
        this.principle = principle;
    }
}
