# Changelog 
All notable changes to this project will be documented in this file.

## 1.1.1-SNAPSHOT (18/8/2020)
- ReportServerApiAdapter addition to interact with Report-server-api project.

## 1.1.0.10 (14/7/2020)
- parallel executions log repeated examples  
- change groupid from com.crowdar.report to io.lippia.report

## 1.1.0.9 (19/6/2020)
- change in ExtentCucumberGenericAdapter log stacktrace: now create the stacktrace with codeBlock of MarkupHelper.  

## 1.1.0.8 (17/6/2020)
- fix regex patter in ExtentCucumberZaleniumKlovAdapter - video not be shown when scenario have "-" y name.
- replacemente of ExtentCucumberKlovAdapter by ExtentCucumberGenericAdapter, this is the correct abstraction.  
- improvement in ExtentCucumberGenericAdapter supporting extra info.
	Examples: 
    	ExtentCucumberGenericAdapter.addExtraInfo("request", "a text");
		ExtentCucumberGenericAdapter.addExtraInfo("response", "another text");
- rename method in ExtentCucumberGenericAdapter, from ScreenShotCapture() to getScreenshotBase64()
- modify strategy to add screenshot 
	Example:   public String getScreenshotBase64() { return ((TakesScreenshot) DriverManager.getDriverInstance()).getScreenshotAs(OutputType.BASE64);	}
		
## 1.1.0.7 (11/6/2020)
- adding ExtentCucumberGenericAdapter. Soports Base64 Screenshot for generic use.

## 1.1.0.6 (10/6/2020)
- adding stressTest for klov implementation
- add jenkinsFile
- add crowdar nexus repo
- add resources to maven build
- Update ExtentVersion to 4.2.0-beta.2.1
- author by system env (key: extent.author)
- device by system env (key: extent.device)
- revert comment of hook remove 



