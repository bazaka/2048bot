package test;

import org.junit.Test;
import page.GamePage;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Bazaka on 10.07.2015.
 */
public class GameTest extends BaseTest {

    String url = "http://gabrielecirulli.github.io/2048/"; //game link

    @Test
    public void goGameTest(){
        log.info("Starting game test");
        GamePage page = new GamePage(driver, wait);
        page.open(url); //open page
        log.info("Open page");
        assertTrue(page.isOpened(url)); //check correct opening
        log.info("Page is opened");
        page.waitForPageLoad(); //wait for page load
        log.info("Page is loaded");
        List keys = page.keyList(); //write control keys in list 'keys'
        while (page.checkGameOver()==0) { //we push random keys and print game field into logger until 'game over' message appears
            page.pushRandomKey(keys); //push keys
            log.info("Random arrow is pushed");
            String gameField = page.showCurrentGameField(); //write result with game field to String
            log.info(gameField); //show result via logger
        }
        page.waitGameOverMessage(); //wait 'game over' message
        assertEquals("Incorrect message about end of game", page.getGameOverText(), "Game over!");
        log.info("Game over");
        log.info("Current score: " + page.showCurrentScore());
        log.info("Test successfully passed");
    }
}
