package framework.pages;

import framework.YaMailAbstract;
import framework.data.LetterParams;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Zakir_Mustafin on 2/3/2017.
 */
public class CreateNewMailPage extends YaMailAbstract {

    private By subjectElement;
    private WebDriverWait wait;

    @FindBy (xpath = "//span[text()='Отправить']")
    WebElement sendButton;

    @FindBy (css = ".js-compose-field.mail-Bubbles.mail-Bubbles-Sms_hidden")
    WebElement addressForSending;

    @FindBy(xpath = "//input[@name='subj']")
    WebElement subjectForLetter;

    @FindBy(xpath = "//div[@role='textbox']")
    WebElement textForLetter;

    @FindBy(xpath = "//*[@title=\"Закрыть\"]")
    WebElement closeButton;

    @FindBy(xpath = "//button[@data-action='save']")
    WebElement saveButtonExsist;

    @FindBy(xpath = "//span[text()='Черновики']")
    WebElement clickDraftButton;

    public CreateNewMailPage(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    public CreateNewMailPage enterDetailsOfTheLetter(LetterParams letterParams){
        JavascriptExecutor executor = ((JavascriptExecutor) getDriver());

        addressForSending.sendKeys(letterParams.getADDRESS());
        logger.info("address '"+ letterParams.getADDRESS() +"' added");
        subjectForLetter.sendKeys(letterParams.getSUBJECTFORLETTER());
        logger.info("subject '"+ letterParams.getSUBJECTFORLETTER() +"' added");
        textForLetter.sendKeys(letterParams.getTEXTFORLETTER());
        logger.info("text '"+ letterParams.getTEXTFORLETTER() +"' for letter added");
//        closeButton.click();
        executor.executeScript("arguments[0].click();", closeButton);

        return new CreateNewMailPage(getDriver(), logger);
    }

    public DraftFolderPage savingLetterToDraft(){
        saveButtonExsist.click();
        logger.info("Clicked on 'Сохранить' button");
        return new DraftFolderPage(getDriver(), logger);
    }

    public SentFolderPage sendMail(LetterParams letterParams){
        wait = new WebDriverWait(getDriver(), 10);
        subjectElement = By.xpath("//span[contains(@title, '" +letterParams.getSUBJECTFORLETTER()+ "')]");
        getDriver().findElement(subjectElement).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Отправить']")));
        sendButton.click();
        logger.info("sent letter with subject '"+ letterParams.getSUBJECTFORLETTER() +"'");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mail-Done-Title.js-title-info")));
        clickDraftButton.click();
        wait.until(ExpectedConditions.titleIs("Черновики — Яндекс.Почта"));
        logger.info("returned to Draft Folder");
        return new SentFolderPage(getDriver(), logger);
    }

    public boolean checkThatSentLetterDisapearedFromDraft(){
        try {
            return getDriver().findElement(subjectElement).isDisplayed();
        } catch (NullPointerException e){
            logger.info("Didn't find such subject in draft folder " + e);
            return false;
        }
    }

    public boolean saveButtonExsist(){
        logger.info("Checking that word 'Сохранить' is displayed in Pop-up window");
        return saveButtonExsist.isDisplayed();
    }

    public boolean sendButtonExsist(){
        logger.info("Checking if word 'Отправить' is displayed");
        return sendButton.isDisplayed();
    }
}
