package com.testng;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author vishal
 */
class ScreenShotUtil {

    /**
     * For taking screenshot this method is used by AppTest class
     *
     * @param webdriver webdriver instance is passed
     */
    static void takeScreenShot(WebDriver webdriver) throws IOException {

        File screenshotFile;
        String screenShotName = "test_";

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yy-hh-mm-ss");
        String currentDate = dateFormat.format(date);
        screenshotFile = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
        String imageName = screenShotName + currentDate;
        String location = System.getProperty("user.dir") + "\\test-output\\screenshot\\" + imageName + ".png";
        FileUtils.copyFile(screenshotFile, new File(location));
    }

}
