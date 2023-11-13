package com.oauth.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.util.LinkedMultiValueMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Service
public class OAuth2Service {

    @Value("${spring.security.oauth2.client.registration.azure.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.azure.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.azure.scope}")
    private String scope;

    @Value("${spring.security.oauth2.client.registration.azure.client.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.registration.azure.authorization-grant-type}")
    private String grant_type;

    private String protectedURL = "http://localhost:8081/protected";

    private final RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper; // Jackson's ObjectMapper

    public OAuth2Service(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("scope", scope);
        body.add("client_secret", clientSecret);
        body.add("grant_type", grant_type);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUri, request, String.class);

        try {
            Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), Map.class);
            return (String) responseMap.get("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String makeAuthenticatedRequest() {
        String accessToken = getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                protectedURL, HttpMethod.GET, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to make authenticated request";
        }
    }

    public String makeNonauthenticatedRequest() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    protectedURL, String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to make non-authenticated request";
        }
    }
}
