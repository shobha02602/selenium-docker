package com.selenium.docker.tests.flightreservation;

import com.selenium.docker.pages.flightreservation.*;
import com.selenium.docker.tests.AbstractTest;
import com.selenium.docker.tests.flightreservation.model.FlightReservationTestData;
import com.selenium.docker.tests.vendorportal.model.VendorPortalTestData;
import com.selenium.docker.util.Config;
import com.selenium.docker.util.Constants;
import com.selenium.docker.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class FlightReservationTest extends AbstractTest {

     private FlightReservationTestData testData;


    @BeforeTest
    @Parameters("testDataPath")
    public void setParameters(String testDataPath) {
        this.testData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);
    }

    @Test
    public void userRegistration() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        //registrationPage.goTo("https://d1uh9e7cu07ukd.cloudfront.net/selenium-docker/reservation-app/index.html");
        registrationPage.goTo(Config.get(Constants.FLIGHT_RESERVATION_URL));
        Assert.assertTrue(registrationPage.isAt());
        driver.manage().window().maximize();

        registrationPage.enterUserDetails(testData.firstName(), testData.lastName());
        registrationPage.enterUserCredentials(testData.email(), testData.password());
        registrationPage.enterAddress(testData.street(), testData.city(), testData.zip());
        registrationPage.register();
    }

    @Test (dependsOnMethods = "userRegistration")
    public void registrationConfirmationTest() {
        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);
        Assert.assertTrue(registrationConfirmationPage.isAt());
        Assert.assertEquals(registrationConfirmationPage.getFirstName(),testData.firstName());

        registrationConfirmationPage.goToFlightSerach();
    }

    @Test (dependsOnMethods = "registrationConfirmationTest")
    public void flightSearchTest() throws InterruptedException {
        FlightSearchPage flightSearchPage = new FlightSearchPage(driver);
        Assert.assertTrue(flightSearchPage.isAt());

        flightSearchPage.selectPassengers(testData.passengersCount());
        Thread.sleep(2000);
        flightSearchPage.searchFlights();
    }

    @Test (dependsOnMethods = "flightSearchTest")
    public void flightSelectionTest() {
        FlightSelectionPage flightSelectionPage = new FlightSelectionPage(driver);
        Assert.assertTrue(flightSelectionPage.isAt());

        flightSelectionPage.selectFlights();
        flightSelectionPage.confirmFlights();
    }

    @Test (dependsOnMethods = "flightSelectionTest")
    public void flightConfirmationTest() {
        FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);
        Assert.assertTrue(flightConfirmationPage.isAt());

        Assert.assertEquals(flightConfirmationPage.getPrice(),testData.expectedPrice());
    }


}
