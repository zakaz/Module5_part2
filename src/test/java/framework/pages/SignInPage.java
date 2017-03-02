package framework.pages;

import framework.YaMailAbstract;
import framework.data.User;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Zakir_Mustafin on 2/3/2017.
 */
public class SignInPage extends YaMailAbstract {

    @FindBy(xpath = "//input[@name='login']")
    private WebElement loginInput;

    @FindBy(xpath = "//input[@name='passwd']")
    private WebElement pwdInput;

    @FindBy (xpath = "//button[@type='submit']")
    private WebElement signInBtn;

    @FindBy(xpath = "//button[@type='submit']/span[text()='Войти']")
    WebElement checkThatThisElementPresented;

//    private WebDriver driver;

    public SignInPage(WebDriver driver, Logger logger) {
        super (driver, logger);
    }

    public InboxMailPage loginToYaMail(User user) {

        logger.info("Typing user login: " + user.getLogin());
        highlightElement(getDriver(), loginInput);
        loginInput.clear();
        loginInput.sendKeys(user.getLogin());

        logger.info("Typing user password: " + user.getPassword());
        highlightElement(getDriver(), pwdInput);
        pwdInput.clear();
        pwdInput.sendKeys(user.getPassword());

        highlightElement(getDriver(), signInBtn);
        signInBtn.click();
        logger.info("Login is in progress...");

        return new InboxMailPage(getDriver(), logger);
    }

    public boolean checkThatExitTrue(){
        return checkThatThisElementPresented.isDisplayed();
    }

}
