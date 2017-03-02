package framework.pages;

import framework.YaMailAbstract;
import framework.data.LetterParams;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Zakir_Mustafin on 2/4/2017.
 */
public class DraftFolderPage extends YaMailAbstract {

    private By addressElement;
    private By subjectElement;
    private By textElement;

    @FindBy(xpath = "//span[text()='Черновики']")
    WebElement draftButton;

    public DraftFolderPage(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    public CreateNewMailPage clickOnDraftButton(){
        draftButton.click();
        logger.info("Clicked on 'Черновики' button");
//        wait.until(ExpectedConditions.titleIs("Черновики — Яндекс.Почта"));
        return new CreateNewMailPage(getDriver(), logger);
    }

    public boolean checkLetterExsistInDraft(LetterParams letterParams){
        addressElement = By.xpath("//span[@title='" +letterParams.getADDRESS()+ "']");
        subjectElement = By.xpath("//span[text()='" + letterParams.getSUBJECTFORLETTER() + "']");
        textElement = By.xpath("//span[text()='" +letterParams.getTEXTFORLETTER()+ "']");

        if (getDriver().findElement(addressElement).isDisplayed() && getDriver().findElement(subjectElement).isDisplayed() &&
                getDriver().findElement(textElement).isDisplayed()){
            logger.info("Letter to '"+ letterParams.getADDRESS() +"', with subject '"+ letterParams.getSUBJECTFORLETTER() +
                    "' and with text '"+ letterParams.getTEXTFORLETTER() +"' is displayed in Draft Folder");
            return true;
        } else return false;
    }
}
