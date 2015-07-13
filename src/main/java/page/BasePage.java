package page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Bazaka on 10.07.2015.
 */

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver, WebDriverWait wait){
        this.driver=driver;
        this.wait=wait;
    }
    public void open(String url){
        driver.get(url);
    }
    public boolean isOpened(String url){
        return driver.getCurrentUrl().equals(url);
    }
}
