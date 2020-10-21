# Changelog 
All notable changes to this project will be documented in this file.

## 1.5 (21/10/2020)
- Solve null description on table examples.

## 1.4 (07/10/2020)
- Solve Bug - error 403 whe script send test finish.

## 1.3 (07/10/2020)
- Full dockerization.  

## 1.2 (19/6/2020)
- change in ExtentCucumberGenericAdapter log stacktrace: now create the stacktrace with codeBlock of MarkupHelper.  
- fix regex patter in ExtentCucumberZaleniumKlovAdapter - video not be shown when scenario have "-" y name.
- replacemente of ExtentCucumberKlovAdapter by ExtentCucumberGenericAdapter, this is the correct abstraction.  
- improvement in ExtentCucumberGenericAdapter supporting extra info.
	Examples: 
    	ExtentCucumberGenericAdapter.addExtraInfo("request", "a text");
		ExtentCucumberGenericAdapter.addExtraInfo("response", "another text");
- rename method in ExtentCucumberGenericAdapter, from ScreenShotCapture() to getScreenshotBase64()
- modify strategy to add screenshot 
	Example:   public String getScreenshotBase64() { return ((TakesScreenshot) DriverManager.getDriverInstance()).getScreenshotAs(OutputType.BASE64);	}
		
## 1.1 (11/6/2020)
- adding ExtentCucumberGenericAdapter. Soports Base64 Screenshot for generic use.

## 1.0 (10/6/2020)
- adding stressTest for klov implementation
- add jenkinsFile
- add crowdar nexus repo
- add resources to maven build
- Update ExtentVersion to 4.2.0-beta.2.1
- author by system env (key: extent.author)
- device by system env (key: extent.device)
- revert comment of hook remove 



