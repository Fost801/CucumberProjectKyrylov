package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class CommonMethods extends PageInitializer{
        public static WebDriver driver;
    public static void openBrowserAndLaunchApplication() throws IOException {

        switch (ConfigReader.read("browser")){
            case "Chrome":
                driver=new ChromeDriver();
                break;
            case "Firefox":
                driver=new FirefoxDriver();
                break;
            case "Edge":
                driver=new EdgeDriver();
                break;
            default:
                throw new RuntimeException("Invalid Browser name");
        }
        //implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driver.get(ConfigReader.read("url"));
        initializePageObjects();

    }
    public static void closeBrowser(){
        if (driver!=null){
            driver.quit();
        }

    }
    public static void selectDropDown(WebElement dropDown,int index){
        Select select=new Select(dropDown);
        select.selectByIndex(index);
    }
    public static void selectDropDown(WebElement dropDown,String visibleText){
        Select select=new Select(dropDown);
        select.selectByVisibleText(visibleText);
    }
    public static void selectDropDown(String value, WebElement dropDown){
        Select select=new Select(dropDown);
        select.selectByValue(value);
    }
    public static void sendText(String text, WebElement element){
        element.clear();
        element.sendKeys(text);
    }
    public static WebDriverWait getWait(){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        return wait;
    }

    public static void waitForElementToBeClickable(WebElement element){
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }
    public static void click(WebElement element){
        waitForElementToBeClickable(element);
        element.click();
    }
    public static byte[]takeScreenshot(String fileName){

    TakesScreenshot ts = (TakesScreenshot) driver;
    byte[] picBytes=ts.getScreenshotAs(OutputType.BYTES);
    File sourceFile =ts.getScreenshotAs(OutputType.FILE);
    try {
        FileUtils.copyFile(sourceFile,new File(Constants.SCREENSHOT_FILEPATH+fileName+" "+getTimeStamp("yyyy-MM-dd-HH-mm-ss")+".png"));
    } catch (IOException e) {
        e.printStackTrace();
    }
    return picBytes;
}
    public static String getTimeStamp(String pattern){

        Date date = new Date();
        //yyyy-MM-dd-hh-mm-ss
        //dd-MM-yyyy-mm-hh-ss
        //to get the date in my acceptable format, i need to format it
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        return sdf.format(date);

    }
    public static JavascriptExecutor getJSExecutor(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;
    }
    public static void jsClick (WebElement element){
        getJSExecutor().executeScript("arguments[0].click();",element);

    }
}
