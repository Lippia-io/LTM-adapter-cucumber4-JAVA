package io.lippia.reporter.ltm;

import cucumber.api.PickleStepTestStep;
import cucumber.api.TestCase;
import cucumber.api.event.*;

import io.lippia.reporter.ltm.models.RunResDTO;
import io.lippia.reporter.ltm.models.StepDTO;
import io.lippia.reporter.ltm.models.TestDTO;

public abstract class TestManagerAPIAdapter implements ConcurrentEventListener {
    private static final RunResDTO runResDto;

    static {
        runResDto = TestManagerAPIClient.createRun();
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestCaseFinished.class, event -> TestManagerAPIClient.createTest(parseTest(event)));
    }

    protected synchronized TestDTO parseTest(TestCaseFinished event) {
        if (runResDto.getId() == null) {
            throw new IllegalArgumentException("Run ID cannot be null");
        }

        return null;
    }

    protected synchronized StepDTO parseStep(TestStepFinished event) {
        return new StepDTO(getStepText(event), null, getBase64Image(), getStepStatus(event));
    }

    protected synchronized String getStepText(TestStepFinished event) {
        return ((PickleStepTestStep) event.testStep).getStepText();
    }

    protected synchronized String getStepStatus(TestStepFinished event) {
        return event.result.getStatus().firstLetterCapitalizedName();
    }

    public abstract String getBase64Image();

}