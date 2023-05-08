package io.lippia.reporter.ltm;

import io.lippia.reporter.ltm.controller.APIController;
import io.lippia.reporter.ltm.models.RunResDTO;
import io.lippia.reporter.ltm.models.TestDTO;
import io.lippia.reporter.ltm.models.TestResDTO;

import java.io.IOException;

final class TestManagerAPIClient {
    private static final String TEST_MANAGER_API_HOST_KEY = System.getProperty("TEST_MANAGER_API_HOST");
    private static final String TEST_MANAGER_API_PORT_KEY = System.getProperty("TEST_MANAGER_API_PORT");
    private static final String TEST_MANAGER_RUN_NAME = System.getProperty("TEST_MANAGER_RUN_NAME");
    private static final String TEST_MANAGER_PROJECT_CODE = System.getProperty("TEST_MANAGER_PROJECT_CODE");

    public static RunResDTO createRun() {
        if (TEST_MANAGER_API_HOST_KEY == null) {
            throw new IllegalArgumentException("TEST_MANAGER_API_HOST_KEY cannot be null");
        }

        if (TEST_MANAGER_API_PORT_KEY == null) {
            throw new IllegalArgumentException("TEST_MANAGER_API_PORT_KEY cannot be null");
        }

        if (TEST_MANAGER_RUN_NAME == null) {
            throw new IllegalArgumentException("TEST_MANAGER_RUN_NAME cannot be null");
        }

        if (TEST_MANAGER_PROJECT_CODE == null) {
            throw new IllegalArgumentException("TEST_MANAGER_RUN_NAME cannot be null");
        }

        String res = APIController.sendRequest("run.json", null);

        try {
            return MapperUtils.getDefaultMapper().readValue(res, RunResDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("deserialization error, root trouble: " + e.getMessage());
        }
    }

    public static TestResDTO createTest(TestDTO testDTO) {
        String res = APIController.sendRequest("test.json", testDTO);

        try {
            return MapperUtils.getDefaultMapper().readValue(res, TestResDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("deserialization error, root trouble: " + e.getMessage());
        }
    }

}