package framework.pages;

import framework.YaMailAbstract;
import framework.data.LetterParams;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Zakir_Mustafin on 2/4/2017.
 */
public class SentFolderPage extends YaMailAbstract implements JustForInformation {

    private By subjectElement;
    private WebDriverWait wait;


    @FindBy(xpath = "//a[contains(@href, '#sent')]")
    WebElement sentButton;

    @FindBy(css = ".mail-User-Picture.js-user-picture")
    WebElement clickOnUserImage;

    @FindBy(linkText = "Выход")
    WebElement clickOnExitButton;

    public SentFolderPage(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    public SentFolderPage() {
        super(driver, logger);
    }

    public boolean goToSentFolderCheckTheLetterExsist(LetterParams letterParams){
        wait = new WebDriverWait(getDriver(), 10);
        subjectElement = By.xpath("//span[contains(@title, '" +letterParams.getSUBJECTFORLETTER()+ "')]");
        sentButton.click();
        logger.info("Opened Sent Folder");
        wait.until(ExpectedConditions.titleIs("Отправленные — Яндекс.Почта"));
        return getDriver().findElement(subjectElement).isDisplayed();
    }

    public void logOut(){
        wait = new WebDriverWait(getDriver(), 10);
        clickOnUserImage.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("b-user-dropdown-content")));
        logger.info("Drop-down menu appered");
        clickOnExitButton.click();
        logger.info("Exit button clicked");
    }

    public void giveInfoOnWhichPageWeAre() {
        System.out.println("We are in SENT FOLDER page!");
    }
}
