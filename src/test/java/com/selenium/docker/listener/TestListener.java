package com.selenium.docker.listener;

import com.selenium.docker.util.Constants;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {

        TakesScreenshot driver = (TakesScreenshot) result.getTestContext().getAttribute(Constants.DRIVER);
        String screenshot = driver.getScreenshotAs(OutputType.BASE64);
        //for attaching screenshot image in html use below tag
        //embedding image to testng report
        String htmlImageFormat =  "<img width=700px src='data:image/png;base64,%s' />";
        String htmlImage = String.format(htmlImageFormat, screenshot);
        Reporter.log(htmlImage);

    }
}
