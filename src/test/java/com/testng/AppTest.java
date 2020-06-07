package com.testng;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.testng.Capture.capture;


public class AppTest {

    private ChromeOptions options;
    private WebDriver webdriver;
    private ExtentReports extent;
    private ExtentTest logger;
    private String path;


    /**
     * Setting the chrome driver.exe path
     *
     * @param filepath chromedriver.exe path
     */
    @BeforeClass
    @Parameters({"chromeExeFilePath"})
    public void setChromeExeFilePath(@Optional("C://chromedriver_win32//chromedriver.exe") String filepath) {
        options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        System.setProperty("webdriver.chrome.driver", filepath);

        Reporter.log("Set Browser is Passed");
        path=System.getProperty("user.dir")+"/test-output/";

    }

    /**
     * open chrome browser and navigate to Noon
     */
    @Test
    public void openBrowser() throws IOException {
        extent = new ExtentReports (System.getProperty("user.dir") +"/test-output/AutomationReport.html", true);

        extent
                .addSystemInfo("Application Name", "Noon")
                .addSystemInfo("Environment", "chrome Browser");
        //loading the external xml file (i.e., extent-config.xml) which was placed under the base directory
        //You could find the xml file below. Create xml file in your project and copy past the code mentioned below
        extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));

        webdriver = new ChromeDriver(options);
        webdriver.manage().window().maximize();
        webdriver.get("https://www.noon.com/saudi-ar/");

        // to take screenshot
        logger = extent.startTest("Open Chrome Browser");
        //ScreenShotUtil.takeScreenShot(webdriver);
        logger.log(LogStatus.PASS,logger.addScreenCapture(capture(webdriver)),"Starting Opened successfully");
    }

    /**
     * Make sure to update your  username and password in testng.xml file
     * This method is used to login with username and password
     *
     * @param user  username
     * @param pass  password
     */
    @Parameters({"username", "password"})
    @Test(priority = 1)
    public void login(@Optional("Abdallahmh19@gmail.com") String user, @Optional("ah666777") String pass) throws IOException {

        logger = extent.startTest("Start Login");
        WebElement account = webdriver.findElement(By.id("dd_header_signInOrUp"));
        account.click();

        WebElement signin = webdriver.findElement(By.id("btn_header_signin"));
        signin.click();

        WebElement username = webdriver.findElement(By.id("tf_signin_email"));
        username.clear();
        username.sendKeys(user);
        logger.log(LogStatus.INFO,"Entering Username");

        WebElement password = webdriver.findElement(By.id("tf_signin_password"));
        password.clear();
        password.sendKeys(pass);
        logger.log(LogStatus.INFO,"Entering Password");


        // to take screenshot
        logger.log(LogStatus.PASS,logger.addScreenCapture(capture(webdriver)),"Login Passed successfully");
        password.sendKeys(Keys.ENTER);

        WebElement signinBtn = webdriver.findElement(By.id("btn_signin_signin"));
        signinBtn.click();


        //ScreenShotUtil.takeScreenShot(webdriver);

    }



    /**
     * Logout
     */
    @Test(priority = 2)
    public void logout() throws IOException, InterruptedException {
        webdriver.switchTo().defaultContent();
        Thread.sleep(2000);
        WebElement dropdown = webdriver.findElement(By.xpath("//span[text()='حسابي']"));
        dropdown.click();
        webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement signOut = webdriver.findElement(By.xpath("//button[@class='jsx-726901317 logOut'][text()='تسجيل الخروج']"));

        // to take screenshot
        logger = extent.startTest("Start Logout");
        ScreenShotUtil.takeScreenShot(webdriver);
        logger.log(LogStatus.PASS,"Login Passed successfully");



        signOut.click();

    }

    /**
     * Closing browser
     */
    @Test(priority = 3)
    public void closeBrowser() {
        webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webdriver.close();

    }

    @AfterTest
    protected void afterSuite() {
        extent.flush();
    }


}
