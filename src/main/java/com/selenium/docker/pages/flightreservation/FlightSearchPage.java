package com.selenium.docker.pages.flightreservation;

import com.selenium.docker.pages.AbstractPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightSearchPage extends AbstractPage {

    @FindBy(id = "passengers")
    private WebElement passengerSelect;

    @FindBy(id = "search-flights")
    private WebElement searchFlightsButton;

    //initializing elements
    public FlightSearchPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        this.wait.until((ExpectedConditions.visibilityOf(passengerSelect)));
        return this.passengerSelect.isDisplayed();
    }

    public void selectPassengers(String numOfPassengers) {
        Select passengers = new Select(this.passengerSelect);
        passengers.selectByValue(numOfPassengers);
    }

    public void searchFlights() {
       WebElement element = this.wait.until(ExpectedConditions.elementToBeClickable(searchFlightsButton));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
        //this.searchFlightsButton.click();
    }
}
