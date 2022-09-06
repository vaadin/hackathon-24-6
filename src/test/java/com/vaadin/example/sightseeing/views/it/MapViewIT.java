package com.vaadin.example.sightseeing.views.it;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.vaadin.example.sightseeing.data.generator.DataGenerator;
import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.dialog.testbench.DialogElement;
import com.vaadin.flow.component.grid.testbench.GridElement;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.map.testbench.MapElement;
import com.vaadin.testbench.TestBenchElement;

public class MapViewIT extends AbstractIT {
    private Coordinate center = DataGenerator.CENTER;

    @Test
    public void loginAsUserAndTestDialog() {
        loginAsUser();
        waitUntil(ExpectedConditions
                .presenceOfElementLocated(By.tagName("vaadin-map")));

        assertFalse("There shouldn't be any buttons on the user map view",
                $(ButtonElement.class).exists());

        // dialog is already present before the first click
        DialogElement dialog = $(DialogElement.class).first();
        assertFalse("Dialog shouldn't be open yet", dialog.isOpen());

        $(MapElement.class).first().clickAtCoordinates(center.getX(),
                center.getY());

        try {
            waitUntil(webDriver -> dialog.isOpen());
        } catch (TimeoutException e) {
            fail("Dialog wasn't opened");
        }
        TestBenchElement content = $("vaadin-dialog-overlay").first();
        assertTrue("Unexpected dialog contents: " + content.getText(),
                content.getText().contains("Nearby:"));

        // click outside the dialog
        new Actions(getDriver()).moveToElement(content)
                .moveByOffset(10 + content.getSize().getWidth() / 2,
                        10 + content.getSize().getHeight() / 2)
                .click().perform();
        try {
            waitUntil(webDriver -> !dialog.isOpen());
        } catch (TimeoutException e) {
            fail("Dialog didn't close");
        }
    }

    @Test
    public void loginAsAdminAndNavigateAway() {
        loginAsAdmin();
        waitUntil(ExpectedConditions
                .presenceOfElementLocated(By.tagName("vaadin-map")));

        assertTrue("There should be buttons on the admin map view",
                $(ButtonElement.class).exists());

        ButtonElement button = $(ButtonElement.class).id("placesButton");
        assertEquals("Places", button.getText());
        button.click();
        try {
            waitUntil(webDriver -> $(GridElement.class).exists());
        } catch (TimeoutException e) {
            fail("Failed to navigate away from map view");
        }

        assertFalse("There shouldn't be a map on the places view",
                $(MapElement.class).exists());
    }

}
