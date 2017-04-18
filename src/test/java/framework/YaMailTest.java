package framework;

import static framework.YaMailAbstract.getDriver;
import framework.data.LetterParams;
import framework.data.User;
import framework.pages.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


/**
 * Created by Zakir_Mustafin on 2/3/2017.
 */
public class YaMailTest {
    private static final String START_URL = "https://mail.yandex.ru/";
    private WebDriver driver;
    private Logger logger;

    @BeforeClass(description = "Start browser")
    public void startBrowser() {
        driver = YaMailAbstract.getDriver();
//        driver = new ChromeDriver();
        driver.get(START_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.manage().window().maximize();
    }
    @Test(description = "Check valid Title")
    public void checkTitle(){
        String expectedTitle = "Яндекс.Почта — бесплатная электронная почта";
        String actualTitle =  driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @Test(dependsOnMethods = "checkTitle", description = "Login to Mail.Yandex account")
    public void loginToYaMail() {
        logger = Logger.getLogger("Test-1. Login to Mail.Yandex account");

        // Login via user-defined method
        new SignInPage(driver, logger).loginToYaMail(new User());

        // Verify the login procedure was correct
        InboxMailPage object = InboxMailPage.getInstance();
        Assert.assertTrue(object.loginIsCorrect(), "Looks you are NOT logged in correctly!");
    }

    @Test(dependsOnMethods = "loginToYaMail", description = "Create new Letter")
    public void createNewLetter() throws InterruptedException {
        // Login via user-defined method
        logger = Logger.getLogger("Test-2. Create new Letter");
        InboxMailPage object = InboxMailPage.getInstance();
        object.createNewMail();

        // Verify the login procedure was correct
        Assert.assertTrue(new CreateNewMailPage(driver, logger).sendButtonExsist(), "Looks you are on the wrong Page!");
        logger.info("Window for creating new Mail opened correctly.");
    }

    @Test(dependsOnMethods = "createNewLetter", description = "Type address, subject and main Text of the letter")
    public void typeAddressSubjTextOfLetter() {
        logger = Logger.getLogger("Test-3. Type address, subject and main Text of the letter");
        new CreateNewMailPage(driver, logger).enterDetailsOfTheLetter(new LetterParams());

        // Verify the login procedure was correct
        Assert.assertTrue(new CreateNewMailPage(driver, logger).saveButtonExsist(), "Pop-up window didn't appear with Save button");
        logger.info("Save button displayed correctly.");
    }

    @Test(dependsOnMethods = "typeAddressSubjTextOfLetter", description = "click on save button, check letter in draft")
    public void clickSaveButtonCheckLetterInDraft(){
        logger = Logger.getLogger("Test-4. click on save button, check letter in draft");
        new CreateNewMailPage(driver, logger).savingLetterToDraft().clickOnDraftButton();
        Assert.assertTrue(new DraftFolderPage(driver, logger).checkLetterExsistInDraft(new LetterParams()), "It seems some problems, letter didn't save in draft folder!");
        logger.info("Letter saved in draft folder!");
    }

    @Test(dependsOnMethods = "clickSaveButtonCheckLetterInDraft", description = "send letter and check draft again")
    public void sendLetterCheckDraftAgain(){
        logger = Logger.getLogger("Test-5. send letter and check draft again");
        new CreateNewMailPage(driver, logger).sendMail(new LetterParams());
        Assert.assertFalse(new CreateNewMailPage(driver, logger).checkThatSentLetterDisapearedFromDraft(), " It Seems some problems, and letter didn't send");
        logger.info("Letter disapeared from draft folder!");
    }

    @Test(dependsOnMethods = "sendLetterCheckDraftAgain", description = "check that letter exsist in sent folder")
    public void checkLetterInSentFolder(){
        logger = Logger.getLogger("Test-6. check that letter exsist in sent folder");
        Assert.assertTrue(new SentFolderPage(driver, logger).goToSentFolderCheckTheLetterExsist(new LetterParams()), "No Letter in Sent Folder");
        logger.info("Letter exsist in sent folder!");
    }

    @Test(dependsOnMethods = "checkLetterInSentFolder", description = "Exit and check that exit true")
    public void exitAndCheckThatExitDone(){
        logger = Logger.getLogger("Test-7. Exit and check that exit true");
        new SentFolderPage(driver, logger).logOut();
        Assert.assertTrue(new SignInPage(driver, logger).checkThatExitTrue(), "Can't check that exit was made correctly");
        logger.info("LogOut done successfully!");
    }

    @AfterClass(description = "Stop Browser")
    public void stopBrowser() {
        driver.quit();
    }

}
