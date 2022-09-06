package com.vaadin.example.sightseeing.views.it;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.textfield.testbench.PasswordFieldElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;

public class LoginViewIT extends AbstractIT {

    @Test
    public void testLogin() {
        assertEquals("There should be no map element on login view", 0,
                findElements(By.tagName("vaadin-map")).size());

        // Find the first button (<vaadin-button>) on the page
        ButtonElement button = $(ButtonElement.class).first();

        assertEquals("Log in", button.getText());

        // Enter login credentials
        $(TextFieldElement.class).first().sendKeys("admin");
        $(PasswordFieldElement.class).first().sendKeys("admin");

        button.click();

        waitUntil(ExpectedConditions
                .presenceOfElementLocated(By.tagName("vaadin-map")));
    }

}
