package io.lippia.reporter.api.client;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import io.lippia.reportserver.api.model.InitializeDTO;
import io.lippia.reportserver.api.model.InitializeResponseDTO;
import io.lippia.reportserver.api.model.LogDTO;
import io.lippia.reportserver.api.model.TestDTO;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class LippiaReportServerApiClient {

	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE = "Content-Type";

	private static final String DEFAULT_REPORT_SERVER_API_URL = "http://localhost:8080";

	private static final String REPORT_SERVER_API_HOST_KEY = "LIPPIA_RS_API_HOST";
	private static final String REPORT_SERVER_API_PORT_KEY = "LIPPIA_RS_API_PORT";

	private static final String REPORT_SERVER_API_URI = "LIPPIA_RS_API_URI";
	private static final String REPORT_SERVER_RUN_IDENTIFIER = "rs.run.id";

	private static RestTemplate restTemplate;
	
	private static String apiUrl;

	private LippiaReportServerApiClient() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		apiUrl = getAPIUrl();
		
		if(apiUrl.startsWith("https://")) {	
			TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
		        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
		            return true;
		        }
		    };
		    
		    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
		    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
		    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
		    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    restTemplate = new RestTemplate(requestFactory);
		} else {
			restTemplate = new RestTemplate();
		}
	}

	public static InitializeResponseDTO initialize() {
		String identifier = System.getProperty(REPORT_SERVER_RUN_IDENTIFIER);
		if(identifier != null) {
			return new InitializeResponseDTO(identifier);
		}

		String url = getAPIUrl() + "/initialize";
		InitializeDTO init = new InitializeDTO(System.getProperty("LIPPIA_RS_PROJECT_NAME"), System.getProperty("LIPPIA_RS_REPORT_NAME"));

		HttpEntity<InitializeDTO> request = new HttpEntity<>(init, getApiHeaders());

		return getRestInstance().postForObject(url, request, InitializeResponseDTO.class);
	}

	private static RestTemplate getRestInstance() {
		return restTemplate;
	}

	public static void finish(TestDTO test) {
		String url = getAPIUrl() + "/test/finish";

		HttpEntity<TestDTO> request = new HttpEntity<>(test, getApiHeaders());

		getRestInstance().postForObject(url, request, String.class);
	}

	public static TestDTO create(TestDTO test) {
		String url = getAPIUrl() + "/test/new";

		HttpEntity<TestDTO> request = new HttpEntity<>(test, getApiHeaders());

		return getRestInstance().postForObject(url, request, TestDTO.class);
	}

	public static void log(LogDTO log) {
		String url = getAPIUrl() + "/test/log";

		HttpEntity<LogDTO> request = new HttpEntity<>(log, getApiHeaders());

		getRestInstance().postForObject(url, request, String.class);
	}

	public static void finishReport(InitializeResponseDTO report) {
		String identifier = System.getProperty(REPORT_SERVER_RUN_IDENTIFIER);
		if(identifier != null) {
			return;
		}

		String url = getAPIUrl() + "/finalize";

		TestDTO test = new TestDTO();
		test.setExecutionIdentifier(report.getExecutionIdentifier());
		HttpEntity<TestDTO> request = new HttpEntity<>(test, getApiHeaders());

		getRestInstance().postForObject(url, request, String.class);
	}

	private static HttpHeaders getApiHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(CONTENT_TYPE, APPLICATION_JSON);
		return headers;
	}

	private static String getAPIUrl() {
	
		if(apiUrl != null) {
			return apiUrl;
		}
		
		String uri;

		String reportServerApiHost = getProperty(null, REPORT_SERVER_API_HOST_KEY);
		String reportServerApiPort = getProperty(null, REPORT_SERVER_API_PORT_KEY);
		if (reportServerApiHost != null) {
			uri = reportServerApiHost;

			if(reportServerApiPort != null) {
				uri +=  ":" + reportServerApiPort;
			}

		} else {
			uri = getProperty(DEFAULT_REPORT_SERVER_API_URL, REPORT_SERVER_API_URI);
		}

		return uri+"/api/v2";
	}	


	private static String getProperty(String defaultValue, String key) {
		String propertyValue = System.getProperty(key);

		if(propertyValue == null) {
			propertyValue = System.getenv(key);
			if(propertyValue == null) {
				propertyValue = defaultValue;
			}	
		}
		return propertyValue;
	}


}
