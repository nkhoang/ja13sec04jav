package com.hnk.aws.web.security.google;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author hnguyen
 */
public class GoogleAuthenticationProvider implements AuthenticationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(GoogleAuthenticationProvider.class.getCanonicalName());
    private static final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";

    private String googleUserInfoUrl = GOOGLE_USER_INFO_URL;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // get access token
        String token = (String) authentication.getCredentials();
        if (StringUtils.isEmpty(token)) {
            throw new BadTokenException("Access token cannot empty.");
        }

        getAccountInfo(token);

        return null;
    }

    private void getAccountInfo(String token) {
        String params = "access_token=" + token;
        try {
            URL url = new URL(googleUserInfoUrl + "?" + params);

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            // connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            // connection.setRequestProperty("Accept", "*/*");
            /// connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            connection.setRequestMethod("GET");

            InputStream is = connection.getInputStream();

            String response = new String(IOUtils.toByteArray(is), "UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
            JsonParser jp = factory.createJsonParser(response);
            JsonNode responseObj = mapper.readTree(jp);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Response: " + response);
            }
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
}
