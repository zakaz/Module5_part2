package framework.pages;

import framework.YaMailAbstract;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Zakir_Mustafin on 2/3/2017.
 */
public class InboxMailPage extends YaMailAbstract {

    @FindBy (xpath = "//span[text()='Входящие']")
    WebElement wordInboxInMailbox;

    @FindBy (linkText = "Написать")
    WebElement buttonCreateNewLetter;

//    WebElement draggable = driver.findElement(By.xpath("//div[@count='2']/div/a/div/span/span[position()=2]"));
    @FindBy (xpath = "//div[5]/div[1]/div[@count='2']/div/a/div/span/span[position()=2]/span[text()='Сам Самыч']")
    WebElement draggable;

    @FindBy (xpath = "//span[text()='просто_папка']")
    WebElement droppable;

    @FindBy (xpath = "//div[@data-params='source=toolbar'][19]")
    WebElement elementForContexClick;

    private static InboxMailPage instance = new InboxMailPage(driver, logger);

    private InboxMailPage(WebDriver driver, Logger logger){
        super(driver, logger);
    }

    public static InboxMailPage getInstance(){
        return instance;
    }

    public CreateNewMailPage createNewMail() throws InterruptedException {
        Thread.sleep(3000);
//        Actions actions = new Actions(driver);
//        Action dragAndDropSomeLetter = actions.dragAndDrop(draggable, droppable).build();
//        dragAndDropSomeLetter.perform();

        Actions builder = new Actions(getDriver());
        Action dragAndDrop = builder.moveToElement(draggable)
                .clickAndHold(draggable)
                .moveToElement(droppable)
                .release(droppable)
                .build();
        dragAndDrop.perform();

        Thread.sleep(3000);
        builder.contextClick(elementForContexClick).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
        Thread.sleep(5000);

        highlightElement(getDriver(), buttonCreateNewLetter);
        buttonCreateNewLetter.click();
        logger.info("Clicked element Create new Letter '"+ buttonCreateNewLetter +"'");

       return new CreateNewMailPage(getDriver(), logger);
    }


    public boolean loginIsCorrect(){
        logger.info("Checking that '"+ wordInboxInMailbox +"' is Displayed");
        return wordInboxInMailbox.isDisplayed();
    }


}
