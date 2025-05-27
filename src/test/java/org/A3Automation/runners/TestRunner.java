package org.A3Automation.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",      // Path ke direktori .feature files
        glue = "org.A3Automation.stepdefinitions",   // Path ke paket tempat Step Definitions dan Hooks berada
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-report.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        monochrome = true
        // tags = "@namatag" // Opsional: Untuk menjalankan skenario dengan tag tertentu
)
public class TestRunner {
}