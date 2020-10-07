## extentreports-cucumber4-adapter

[![Maven Central](https://img.shields.io/maven-central/v/com.aventstack/extentreports-cucumber4-adapter.svg?maxAge=300)](http://search.maven.org/#search|ga|1|g:"com.aventstack")

### Docs.

See [here](http://extentreports.com/docs/versions/4/java/cucumber4.html) for complete docs.

### Usage

To begin using the adapter, add the com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter plugin to the runner.

```java
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class RunCukesTest {
	// ..
}
```

### Attaching Screenshots

Screenshots can be saved to a specified in `extent.properties` using:

```
screenshot.dir=test-output/
```

Starting `1.0.11`, the below setting can be used to specify the relative path from the saved HTML file to link screenshots:

```
screenshot.rel.path=../
```

To learn more about this, refer to [#20](https://github.com/extent-framework/extentreports-cucumber4-adapter/issues/20#issuecomment-601591963).

### License

MIT licensed




detallar las properties que deben ir como systemProperties, o Env variables.  
	
	private static final String REPORT_SERVER_API_HOST_KEY = "LIPPIA_RS_API_HOST";
	private static final String REPORT_SERVER_API_PORT_KEY = "LIPPIA_RS_API_PORT";
	private static final String REPORT_SERVER_API_URI = "LIPPIA_RS_API_URI";
	
	
	 <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.22.1</version>
        <configuration>
        		<systemPropertyVariables>
        		
		    			<LIPPIA_RS_API_URI>http://localhost:8080</LIPPIA_RS_API_URI>
		    			
		    				O SINO SE PUEDE ESPECIFICAR HOST(con protocolo) Y PORT 
		    				
						<LIPPIA_RS_API_HOST>http://localhost</LIPPIA_RS_API_HOST>
						<LIPPIA_RS_API_PORT>8080</LIPPIA_RS_API_PORT>
	    			
	    				<LIPPIA_RS_REPORT_NAME> [PROJECT_NAME] </LIPPIA_RS_REPORT_NAME>
	    				<LIPPIA_RS_PROJECT_NAME> [REPORT_NAME] </LIPPIA_RS_PROJECT_NAME>
	    			
					</systemPropertyVariables>

            <suiteXmlFiles>
                <suiteXmlFile>${runner}</suiteXmlFile>
            </suiteXmlFiles>
            <testFailureIgnore>true</testFailureIgnore>
        </configuration>
    </plugin>    
    

    
    
    
    
    
    
    
    
    