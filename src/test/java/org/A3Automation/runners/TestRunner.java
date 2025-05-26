package org.A3Automation.runners; // Sesuaikan dengan package Anda

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class) // Menggunakan JUnit 4 runner untuk Cucumber
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "org.A3Automation.stepdefinitions", // **PENTING: Sesuaikan dengan package step definitions Anda**
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-report.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        monochrome = true
        // tags = "@SmokeTest" // Opsional
)
public class TestRunner {
}