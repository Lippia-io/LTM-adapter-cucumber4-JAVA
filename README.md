# Test Manager Adapter
[![Crowdar Official Page](https://img.shields.io/badge/crowdar-official%20page-brightgreen)](https://crowdar.com.ar/)
[![Lippia Official Page](https://img.shields.io/badge/lippia-official%20page-brightgreen)](https://www.lippia.io/)

#### description pending

### Getting Started
```bash
$ git clone https://gitlab.crowdaronline.com/lippia/products/test-manager/adapters/cucumber4-adapter.git && cd "$(basename "$_" .git)"
$ mvn clean install -DskipTests
```

### Usage
```xml
<properties>
    ...
    <test-manager-plugin>--plugin classpath:CucumberReporter:</test-manager-plugin>
    ...
</properties>

<dependencies>
    ...
    <dependency>
        <groupId>io.lippia.report</groupId>
        <artifactId>ltm-cucumber4-adapter</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
    ...
</dependencies>

<plugins>
    ...
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>${maven-surefire-plugin.version}</version>
        <configuration>
            <suiteXmlFiles>
                <suiteXmlFile>${testng-runner}</suiteXmlFile>
            </suiteXmlFiles>
            <systemPropertyVariables>
                <TEST_MANAGER_USERNAME></TEST_MANAGER_USERNAME>
                <TEST_MANAGER_PASSWORD></TEST_MANAGER_PASSWORD>
                <TEST_MANAGER_API_HOST>https://runs.dev.lippia.io</TEST_MANAGER_API_HOST>
                <TEST_MANAGER_API_PORT></TEST_MANAGER_API_PORT>
                <TEST_MANAGER_RUN_NAME>Local - build</TEST_MANAGER_RUN_NAME>
                <TEST_MANAGER_PROJECT_CODE>GESHTJ</TEST_MANAGER_PROJECT_CODE>
            </systemPropertyVariables>
        </configuration>
    </plugin>
    ...
</plugins>
```