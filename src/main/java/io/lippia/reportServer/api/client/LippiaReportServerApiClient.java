package io.lippia.reportServer.api.client;

import java.util.HashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import io.lippia.reportServer.api.model.InitializeDTO;
import io.lippia.reportServer.api.model.InitializeResponseDTO;
import io.lippia.reportServer.api.model.LogDTO;
import io.lippia.reportServer.api.model.TestDTO;

public class LippiaReportServerApiClient {

	private static final String DEFAULT_REPORT_SERVER_API_URL = "http://localhost:8080";

	private static final String REPORT_SERVER_API_HOST_KEY = "LIPPIA_RS_API_HOST";
	private static final String REPORT_SERVER_API_PORT_KEY = "LIPPIA_RS_API_PORT";

	private static final String REPORT_SERVER_API_URI = "LIPPIA_RS_API_URI";

	private static RestTemplate restTemplate = new RestTemplate();

	public static InitializeResponseDTO initialize() {
		String url = getAPIUrl() + "/initialize";
		InitializeDTO init = new InitializeDTO(System.getProperty("LIPPIA_RS_PROJECT_NAME"), System.getProperty("LIPPIA_RS_REPORT_NAME"));

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<InitializeDTO> request = new HttpEntity<InitializeDTO>(init, headers);

		return restTemplate.postForObject(url, request, InitializeResponseDTO.class);
	}

	public static void finish(TestDTO test) {
		String url = getAPIUrl() + "/test/finish";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<TestDTO> request = new HttpEntity<TestDTO>(test, headers);

		restTemplate.postForObject(url, request, String.class, headers);
	}

	public static TestDTO create(TestDTO test) {
		String url = getAPIUrl() + "/test/new";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<TestDTO> request = new HttpEntity<TestDTO>(test, headers);

		return restTemplate.postForObject(url, request, TestDTO.class, headers);
	}

	public static void log(LogDTO log) {
		String url = getAPIUrl() + "/test/log";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		HttpEntity<LogDTO> request = new HttpEntity<LogDTO>(log, headers);
		
		restTemplate.postForObject(url, request, String.class, headers);
	}

	public static void finishReport(InitializeResponseDTO report) {
		String url = getAPIUrl() + "/finalize";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		TestDTO test = new TestDTO();
		test.setExcecutionIdentifier(report.getExcecutionIdentifier());
		HttpEntity<TestDTO> request = new HttpEntity<TestDTO>(test, headers);
		
		restTemplate.postForObject(url, request, String.class, headers);
	}

	private static String getAPIUrl() {
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
