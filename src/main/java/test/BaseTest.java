package test;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Bazaka on 10.07.2015.
 */
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    //Object initialization for log
    static Logger log = Logger.getLogger(BaseTest.class.getName());

    @Before
    public void preCondition(){
        // loading log4j.xml file
        DOMConfigurator.configure("log4j.xml");
        driver = new FirefoxDriver();
        log.info("Open Firefox");
        //initialize wait
        wait = new WebDriverWait(driver, 15);
        driver.manage().window().maximize();
    }
    @After
    public void postCondition(){
        if(driver!=null) {
            log.info("Close Firefox");
            driver.quit();
        }
    }
}
