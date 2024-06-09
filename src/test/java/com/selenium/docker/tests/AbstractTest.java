package com.selenium.docker.tests;

import com.google.common.util.concurrent.Uninterruptibles;
import com.selenium.docker.listener.TestListener;
import com.selenium.docker.pages.vendorportal.DashboardPage;
import com.selenium.docker.pages.vendorportal.LoginPage;
import com.selenium.docker.util.Config;
import com.selenium.docker.util.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Listeners({TestListener.class})
public abstract class AbstractTest {

    protected WebDriver driver;
    private static final Logger log = LoggerFactory.getLogger(AbstractTest.class);

    //if we want to run tests on say different browsers we'll not use system property instead
    //use parameters as below and will add param String browser in setDriver and getRemoteDriver

    @BeforeSuite
    public void setupConfig() {
        Config.initialize();
    }

    @BeforeTest
    //@Parameters({"browser"})
    public void setDriver(ITestContext ctx/*String browser*/) throws MalformedURLException {
        this.driver = Boolean.parseBoolean(Config.get(Constants.GRID_ENABLED)) ? getRemoteDriver() : getLocalDriver();
        ctx.setAttribute(Constants.DRIVER, this.driver);

       /* if(Boolean.getBoolean("selenium.grid.enabled")) {
            this.driver = getRemoteDriver(*//*browser*//*);
        }
        else {
            this.driver = getLocalDriver();
        }*/
        //commenting below line since we're updating to use grid
        //WebDriverManager.chromedriver().clearDriverCache().driverVersion("125").setup();
        //this.driver = new ChromeDriver();
    }

    //using grid
    //now we'll check selenium.grid.enabled property , based on that we'll launch chrome
    //if property is true we'll call getRemoteDriver() else getLocalDriver()

    private WebDriver getRemoteDriver(/*String browser*/) throws MalformedURLException {
        //we have to send our test req to selenium grid
        //http://localhost:4444/wd/hub
        //Commenting for refactoring
        //Capabilities capabilities;
        Capabilities capabilities = new ChromeOptions();
        if(Constants.FIREFOX.equalsIgnoreCase(Config.get(Constants.BROWSER))) {
            capabilities = new FirefoxOptions();
        }
        String urlFormat = Config.get(Constants.GRID_URL_FORMAT);
        String hubHost = Config.get(Constants.GRID_HUB_HOST);
        String url = String.format(urlFormat,hubHost);

        //here we're reading property prowser from pom.xml
        //if(System.getProperty("browser").equalsIgnoreCase("chrome")) {
            //use below line if we want to run diff tests on diff browsers
        //if(browser.equalsIgnoreCase("chrome")) {
            //capabilities = new ChromeOptions();
       // }
        //else {
            //capabilities = new FirefoxOptions();
       // }
        log.info("grid url------: {}", url);
        return new RemoteWebDriver(new URL(url),capabilities);
    }

    private WebDriver getLocalDriver() {
        WebDriverManager.chromedriver().clearDriverCache().driverVersion("125").setup();
        return new ChromeDriver();
    }

    @AfterTest
    public void quitDriver() {
        this.driver.quit();
    }

    //If we want to see dockerized test inside container we'll use following
    //since tests are superfast we're delaying
  /*  @AfterMethod
    public void sleep() {
        Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(5));
    }*/
}
