package io.lippia.reporter.ltm;

import io.lippia.reporter.ltm.models.*;
import io.lippia.reporter.ltm.models.run.response.RunDTO;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_X_WWW_FORM_URLENCODED;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

final class TestManagerAPIClient {
    private static final String TEST_MANAGER_API_HOST_KEY = System.getProperty("TEST_MANAGER_API_HOST");
    private static final String TEST_MANAGER_API_PORT_KEY = System.getProperty("TEST_MANAGER_API_PORT");
    private static final String TEST_MANAGER_RUN_NAME = System.getProperty("TEST_MANAGER_RUN_NAME");
    private static final String TEST_MANAGER_PROJECT_CODE = System.getProperty("TEST_MANAGER_PROJECT_CODE");

    private static RestTemplate restTemplate;

    private static String apiUrl;

    private static void initializeRestTemplate(){
        apiUrl = getAPIUrl();

        if(apiUrl.startsWith("https://")) {
            TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;

            SSLContext sslContext;
            try {
                sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
                SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
                CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
                HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
                requestFactory.setHttpClient(httpClient);
                restTemplate = new RestTemplate(requestFactory);
            } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
                e.printStackTrace();
            }
        } else {
            restTemplate = new RestTemplate();
        }
    }

    private static RestTemplate getRestInstance() {
        if(restTemplate==null)
            initializeRestTemplate();
        return restTemplate;
    }

    private static HttpHeaders getApiHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAuthToken());
        return headers;
    }

    private static String getAuthToken() {
        String url = "https://authdev.lippia.io/realms/mvp.lippia.io/protocol/openid-connect/token";
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED);
        HttpEntity<String> request = new HttpEntity<>("client_id=admin-cli&grant_type=password&username=admin&password=admin", headers);
        AuthResDTO authRes = getRestInstance().postForObject(url, request, AuthResDTO.class);
        return authRes.getAccessToken();
    }

    private static String getAPIUrl() {
        if(apiUrl != null) {
            return apiUrl;
        }

        String uri = TEST_MANAGER_API_HOST_KEY;

        if(TEST_MANAGER_API_PORT_KEY != null) {
            uri +=  ":" + TEST_MANAGER_API_PORT_KEY;
        }

        return uri;
    }

    public static RunDTO createRun() {
        if (TEST_MANAGER_RUN_NAME == null) {
            throw new IllegalArgumentException("TEST_MANAGER_RUN_NAME cannot be null");
        }

        if (TEST_MANAGER_PROJECT_CODE == null) {
            throw new IllegalArgumentException("TEST_MANAGER_RUN_NAME cannot be null");
        }

        io.lippia.reporter.ltm.models.run.request.RunDTO run = new io.lippia.reporter.ltm.models.run.request.RunDTO(TEST_MANAGER_RUN_NAME, TEST_MANAGER_PROJECT_CODE);

        String url = getAPIUrl() + "/runs/runs";
        HttpEntity<io.lippia.reporter.ltm.models.run.request.RunDTO> request = new HttpEntity<>(run, getApiHeaders());
        return getRestInstance().postForObject(url, request, RunDTO.class);
    }

    public static void createTest(TestDTO test) {
        String url = getAPIUrl() + "/runs/tests";
        HttpEntity<TestDTO> request = new HttpEntity<>(test, getApiHeaders());
        getRestInstance().postForObject(url, request, TestResDTO.class);
    }
}