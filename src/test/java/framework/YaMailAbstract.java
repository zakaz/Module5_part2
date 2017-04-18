package framework;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

/**
 * Created by Zakir_Mustafin on 2/3/2017.
 */
public abstract class YaMailAbstract {
    protected static WebDriver driver;
    protected static Logger logger;
    //    protected WebElement webElement;
//    protected WebDriverWait wait;
//    protected String subjectOfLetter = String.valueOf(System.currentTimeMillis());
    protected String addressOfLetter = "samsamitch1@yandex.ru";
    protected String textOfLetter = "Мама мыла раму!!!";

    public YaMailAbstract(WebDriver driver, Logger logger) {
        this.driver = driver;
        this.logger = logger;
        PageFactory.initElements(this.driver, this);
    }



    public static WebDriver getDriver() {
        if (driver == null) {
            setDriver();
//            setDriverCapabilities();
        }
        return driver;
    }

    private static void setDriver() {
//         System.setProperty("webdriver.ie.driver", "C:\\Data\\For_grid\\IEDriverServer.exe");
//        driver = new InternetExplorerDriver();
//
//		 System.setProperty("webdriver.gecko.driver", "C:\\Data\\For_grid\\geckodriver.exe");
//        driver = new FirefoxDriver();

        System.setProperty("webdriver.chrome.driver", "C:\\Data\\For_grid\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    private static void setDriverCapabilities() {


//         System.setProperty("webdriver.ie.driver", "C:\\Data\\For_grid\\IEDriverServer.exe");
//         DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
//
		 System.setProperty("webdriver.gecko.driver", "/media/share/geckodriver");
		 DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setVersion("0.14.0");

//        System.setProperty("webdriver.chrome.driver", "C:\\Data\\For_grid\\chromedriver.exe");
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

//        capabilities.setPlatform(Platform.WINDOWS);
        capabilities.setPlatform(Platform.LINUX);
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void highlightElement(WebDriver driver, WebElement element)
    {
        String bg = element.getCssValue("backgroundColor");
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].style.backgroundColor = '" + "yellow" + "'", element);
// take screenshot here
        js.executeScript("arguments[0].style.backgroundColor = '" + bg + "'", element);
    }


    public boolean isElementPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }
}
