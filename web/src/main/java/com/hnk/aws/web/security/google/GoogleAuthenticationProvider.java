package com.hnk.aws.web.security.google;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hnk.aws.web.security.google.model.GoogleTokenResponse;
import com.hnk.aws.web.security.google.model.GoogleUserInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author hnguyen
 */
public class GoogleAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(GoogleAuthenticationProvider.class.getCanonicalName());
    private static final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final String GOOGLE_ACCESS_TOKEN_VERIFICATION_URL = "https://accounts.google.com//o/oauth2/token";


    private String googleUserInfoUrl = GOOGLE_USER_INFO_URL;
    private String googleAccessTokenVerificationUrl = GOOGLE_ACCESS_TOKEN_VERIFICATION_URL;

    @Autowired
    Properties thirdPartyGoogle;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // get code token.
        GoogleAuthenticationToken token = (GoogleAuthenticationToken) authentication;
        String code = (String) token.getCredentials();
        if (StringUtils.isEmpty(code)) {
            throw new BadCodeException("Verification code is empty.");
        }

        String accessToken = getAccessToken(code);
        GoogleUserInfo userInfo = getAccountInfo(accessToken);

        return token;
    }

    /**
     * Send a request to get access token when the user granted permission.
     *
     * @param code the authentication code to get the access token.
     * @return the access token retrieved from Google.
     */
    private String getAccessToken(String code) {
        try {
            URL url = new URL(googleAccessTokenVerificationUrl);

            // build query string
            StringBuilder sb = new StringBuilder();
            sb.append("code=" + code);
            sb.append("&client_id=" + thirdPartyGoogle.get("clientId"));
            sb.append("&client_secret=" + thirdPartyGoogle.get("secret"));
            sb.append("&redirect_uri=" + thirdPartyGoogle.get("redirectUrl"));
            sb.append("&grant_type=" + thirdPartyGoogle.get("grantType"));


            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(HttpMethod.POST.name());

            if (StringUtils.isNotBlank(sb.toString())) {
                DataOutputStream wr = new DataOutputStream(
                        connection.getOutputStream());
                wr.writeBytes(sb.toString());
                wr.flush();
                wr.close();
            }

            InputStream is = connection.getInputStream();

            String response = new String(IOUtils.toByteArray(is), "UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            GoogleTokenResponse resObj = mapper.readValue(response, GoogleTokenResponse.class);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Parsed token: " + resObj);
            }
            return resObj.getAccessToken();
        } catch (Exception ex) {
            throw new AuthenticationFailureException("Could not verify access token.");
        }
    }

    /**
     * Get google user information.
     *
     * @param token the access token.
     * @return a {@code GoogleUserInfo} instance.
     */
    private GoogleUserInfo getAccountInfo(String token) {
        String params = "access_token=" + token;
        try {
            URL url = new URL(googleUserInfoUrl + "?" + params);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod(HttpMethod.GET.name());

            InputStream is = connection.getInputStream();

            String response = new String(IOUtils.toByteArray(is), "UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            GoogleUserInfo userInfo = mapper.readValue(response, GoogleUserInfo.class);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Response: " + response);
            }

            return userInfo;
        } catch (Exception ex) {
            throw new AuthenticationFailureException("Could not verify access token.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (GoogleAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public void setGoogleUserInfoUrl(String googleUserInfoUrl) {
        this.googleUserInfoUrl = googleUserInfoUrl;
    }

    public void setGoogleAccessTokenVerificationUrl(String googleAccessTokenVerificationUrl) {
        this.googleAccessTokenVerificationUrl = googleAccessTokenVerificationUrl;
    }
}
