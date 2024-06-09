package com.selenium.docker.pages.flightreservation;

import com.selenium.docker.pages.AbstractPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FlightSelectionPage extends AbstractPage {

    @FindBy(name = "departure-flight")
    private List<WebElement> departureFlightOptions;

    @FindBy(name = "arrival-flight")
    private List<WebElement> arrivalFlightOptions;

    @FindBy(id = "confirm-flights")
    private WebElement confirmFlightsButton;

    public FlightSelectionPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        this.wait.until(ExpectedConditions.visibilityOf(this.confirmFlightsButton));
        return this.confirmFlightsButton.isDisplayed();
    }

    public void selectFlights() {
        int random = ThreadLocalRandom.current().nextInt(0, departureFlightOptions.size());
        WebElement element = this.wait.until(ExpectedConditions.elementToBeClickable(departureFlightOptions.get(random)));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
        //this.departureFlightOptions.get(random).click();
        //this.arrivalFlightOptions.get(random).click();

        WebElement element1 = this.wait.until(ExpectedConditions.elementToBeClickable(arrivalFlightOptions.get(random)));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element1);
    }

    public void confirmFlights() {
        WebElement element = this.wait.until(ExpectedConditions.elementToBeClickable(confirmFlightsButton));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);


        //this.confirmFlightsButton.click();
    }
}
