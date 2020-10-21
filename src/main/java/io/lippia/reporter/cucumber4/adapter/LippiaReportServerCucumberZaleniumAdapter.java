package io.lippia.reporter.cucumber4.adapter;

import cucumber.api.HookTestStep;
import cucumber.api.HookType;
import cucumber.api.event.TestStepFinished;

public abstract class LippiaReportServerCucumberZaleniumAdapter extends ReportServerApiAdapter{

	public LippiaReportServerCucumberZaleniumAdapter(String arg) {
	}
	
	@Override
	synchronized void handleTestStepFinished(TestStepFinished event) {
		super.handleTestStepFinished(event);

		
		if (event.testStep instanceof HookTestStep) {

			HookTestStep hook = (HookTestStep) event.testStep;
			if (hook.getHookType().equals(HookType.After)) {

//        		Log scenario resume
					
				String stepName = "Summary";
				

				String zaleniumDashboard = System.getProperty("zalenium.hub").replace("wd/hub", "dashboard");
				String build = System.getProperty("build.identifier").replaceAll("[^a-zA-Z0-9/]", "_");
				String fileName = event.getTestCase().getName().replaceAll("[^a-zA-Z0-9/\\-]", "_");

				addExtraInfo(stepName, buildZaleniumResumeHtml(zaleniumDashboard, build, fileName));
			}
		}
	}

	private String buildZaleniumResumeHtml(String zaleniumDashboard, String build, String fileName) {
		String zaleniumResumeHtml = "<video width=\"320\" height=\"240\" controls><source src=\"{zaleniumDashboard}/{build}/{fileName}.mp4\" type=\"video/mp4\"></video> <br> "
				+ "<a target=\"_blank\" href=\"{zaleniumDashboard}/{build}/logs/{fileName}/chrome_driver.log\" class=\"ng-scope\"> <span class=\"badge primary\">Chrome driver logs</span> </a> &nbsp "
				+ "<a target=\"_blank\" href=\"{zaleniumDashboard}/{build}/logs/{fileName}/selenium-multinode-stderr.log\" class=\"ng-scope\"> <span class=\"badge primary\">Selenium Logs</span> </a>";

		zaleniumResumeHtml = zaleniumResumeHtml
				.replace("{zaleniumDashboard}", zaleniumDashboard)
				.replace("{build}", build)
				.replace("{fileName}", fileName);
		return zaleniumResumeHtml;
	}


}
