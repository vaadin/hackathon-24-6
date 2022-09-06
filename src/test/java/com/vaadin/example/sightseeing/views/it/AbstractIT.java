package com.vaadin.example.sightseeing.views.it;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchTestCase;

public class AbstractIT extends TestBenchTestCase {

    @Before
    public void setup() throws Exception {
        // Create a new browser instance
        setDriver(new ChromeDriver());
        // Open the application
        getDriver().get("http://localhost:8080/");
    }

    public void loginAsAdmin() {
        $(TextFieldElement.class).first().sendKeys("admin");
        $(PasswordFieldElement.class).first().sendKeys("admin");
        $(ButtonElement.class).first().click();
    }

    public void loginAsUser() {
        $(TextFieldElement.class).first().sendKeys("user");
        $(PasswordFieldElement.class).first().sendKeys("user");
        $(ButtonElement.class).first().click();
    }

    @After
    public void tearDown() throws Exception {
        // close the browser instance when all tests are done
        getDriver().quit();
    }

}
