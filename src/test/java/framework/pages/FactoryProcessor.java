package framework.pages;

import org.openqa.selenium.WebDriver;

/**
 * Created by Zakir_Mustafin on 19.04.2017.
 */
public class FactoryProcessor {
    public JustForInformation getPage(String pageTipe){
        if(pageTipe == null){
            return null;
        }
        if(pageTipe.equalsIgnoreCase("MAIN")){
            return new SignInPage();

        } else if(pageTipe.equalsIgnoreCase("SENT")){
            return new SentFolderPage();
        }

        return null;
    }
}
