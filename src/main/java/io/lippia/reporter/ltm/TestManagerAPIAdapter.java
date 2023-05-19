package io.lippia.reporter.ltm;

import cucumber.api.PickleStepTestStep;
import cucumber.api.Result;
import cucumber.api.event.*;

import gherkin.ast.Feature;
import gherkin.ast.Step;
import gherkin.pickles.PickleTag;

import io.lippia.reporter.ltm.models.run.response.RunDTO;
import io.lippia.reporter.ltm.models.StepDTO;
import io.lippia.reporter.ltm.models.TestDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("[Technical Debt] -> {TestManagerAPIAdapter::createFeature}")
public abstract class TestManagerAPIAdapter implements ConcurrentEventListener {
    private final TestSourcesModel testSources = new TestSourcesModel();
    private final ThreadLocal<String> currentFeatureFile = new ThreadLocal<>();
    private static final ThreadLocal<List<StepDTO>> steps = new ThreadLocal<>();

    private static final RunDTO runResponseDTO;

    static {
        runResponseDTO = TestManagerAPIClient.createRun();
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestSourceRead.class, this::readTestSource);
        eventPublisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
        eventPublisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        eventPublisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }

    protected void readTestSource(TestSourceRead event) {
        testSources.addTestSourceReadEvent(event.uri, event);
    }

    private synchronized void handleTestStepFinished(TestStepFinished event) {
        addFinishedStep(event);
    }

    private synchronized void handleTestCaseStarted(TestCaseStarted event) {
        this.handleStartOfFeature(event);
    }

    private synchronized void handleTestCaseFinished(TestCaseFinished event) {
        this.handleEndOfFeature(event);
        //createTestCase(event);
    }

    private void cleanSteps() {
        if (steps.get() != null) {
            steps.remove();
        }
    }

    private synchronized void handleStartOfFeature(TestCaseStarted testCase) {
        String uri = currentFeatureFile.get();
        if(uri == null || !uri.equals(testCase.getTestCase().getUri())) {
            currentFeatureFile.set(testCase.getTestCase().getUri());
        }
    }

    private synchronized void handleEndOfFeature(TestCaseFinished testCase) {
        createFeature(testCase);
        cleanSteps();
    }

    protected void createFeature(TestCaseFinished testCase) {
        Feature feature = testSources.getFeature(testCase.getTestCase().getUri());
        if (feature != null) {
            TestDTO test = createTestDTO(testCase, feature.getName());
            TestManagerAPIClient.createTest(test);
        }
    }

    private TestDTO createTestDTO(TestCaseFinished testCase, String featureName) {
        String title        = testCase.getTestCase().getName();
        String runId        = runResponseDTO.getId();
        String status       = getStatusAsString(testCase);
        String type         = "SCENARIO";
        List<String> tags   = testCase.getTestCase().getTags().stream().map(PickleTag::getName).collect(Collectors.toList());

        return new TestDTO(title, runId, status, featureName, type, tags, steps.get());
    }

    private String getStatusAsString(TestCaseEvent test) {
        Result.Type status = Result.Type.PENDING;
        if (test instanceof TestStepFinished) {
            status = ((TestStepFinished) test).result.getStatus();
        } else if (test instanceof TestCaseFinished) {
            status = ((TestCaseFinished) test).result.getStatus();
        }

        return status.toString().toUpperCase().substring(0, status.toString().length() - 2);
    }

    protected synchronized void addFinishedStep(TestStepFinished event) {
        if (steps.get() == null) {
            steps.set(new LinkedList<>());
        }

        if (event.testStep instanceof PickleStepTestStep) {
            String base64Image = null;
            String stackTrace = null;
            String status = getStatusAsString(event);
            if (status.equalsIgnoreCase("FAIL")) {
                base64Image = getBase64Image();
                stackTrace = truncate(event.result.getErrorMessage(), 200);
            }

            steps.get().add(new StepDTO(getStepText(event), stackTrace, base64Image, status));
        }

    }

    protected synchronized String getStepText(TestStepFinished event) {
        String stepText = null;
        PickleStepTestStep pickle = ((PickleStepTestStep) event.testStep);
        AstNode astNode = testSources.getAstNode(currentFeatureFile.get(), pickle.getStepLine());
        if (astNode != null) {
            Step step = (Step) astNode.node;
            stepText = step.getKeyword() + pickle.getStepText();
        }

        return stepText;
    }

    public static synchronized String truncate(String str, int length) {
        if (str.length() <= length) {
            return str.substring(0, length);
        }

        return str;
    }

    public abstract String getBase64Image();

}