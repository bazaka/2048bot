package page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Bazaka on 10.07.2015.
 */
public class GamePage extends BasePage{
    final int fieldSize = 4; //size of game field (it can be changed in future)

    //control keys
    public final Keys keyUp = Keys.ARROW_UP;
    public final Keys keyDown = Keys.ARROW_DOWN;
    public final Keys keyLeft = Keys.LEFT;
    public final Keys keyRight = Keys.RIGHT;

    //locators
    public static final By gameField = By.className("game-container"); //game field
    public static final By gameOverMessage = By.xpath("//div[contains(@class, 'game-over')]/p");
    public static final By score = By.className("score-container");
    //String will be used like a locators for all cells
    public static final String cell = "//div[contains(@class, 'tile-position-%d-%d')]";
    public static final String mergedCell = "//div[contains(@class, 'tile-position-%d-%d') and contains(@class, 'tile-merged')]"; //mergedCell contains sum of two united cells

    public GamePage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
    }

    //wait until game field has been loaded
    public void waitForPageLoad(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(gameField));
    }

    //create list of control key actions
    public List keyList(){
        List<Keys> controlKeys = new ArrayList<Keys>();
        //add keys to list
        controlKeys.add(keyUp); //add keys to list
        controlKeys.add(keyDown);
        controlKeys.add(keyLeft);
        controlKeys.add(keyRight);
        return controlKeys;
    }

    //get random value in list of keys
    public void pushRandomKey(List<Keys> keys){
        Random rand = new Random();
        int index = rand.nextInt(keys.size());
        //String cellsValue = showCurrentGameField(); //write current game field
        driver.findElement(gameField).sendKeys(keys.get(index));  //press selected key

        // It's unnecessary to use Explicitly and Implicitly waiting here because random pushing of buttons not always can cause changes on the game field
        // so, here is used Thread.sleep()
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //waitUntilGameFieldIsUpdated(cellsValue); //use our own wait
    }

    /*
    //write own Expected Condition of waiting for updating game field after pushing key
    public void waitUntilGameFieldIsUpdated(final String cells){
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver webDriver) {
                String newCells = showCurrentGameField();
                if(cells.equals(newCells))
                    return false;
                return true;
            }
        });
    }
    */

    //show current game score
    public String showCurrentScore(){
        String currentScore = driver.findElement(score).getText();
        //Because text in score locator may contain the last received score (such like '+5') we should to cut this extra part
        if(currentScore.contains("+")){
            return currentScore.split("\n")[0]; // '\n' is separator of needed and needless parts of score mapping
        }else
             return currentScore;
    }

    //get String with current game field
    public String showCurrentGameField(){
        //We need to show game field via logger. We use StringBuilder to return String with game field to logger.
        StringBuilder result = new StringBuilder();
        for(int i=1; i<=fieldSize; i++){
            result.append("\n");
            result.append("| ");
            for(int j=1; j<=fieldSize; j++){
                By cellLocator = By.xpath(String.format(cell, j, i)); // locator of cell
                By mergedCellLocator = By.xpath(String.format(mergedCell, j, i)); //locator of cell which have class.contains("merged")
                if(driver.findElements(mergedCellLocator).isEmpty()){ //if cell doesn't have 'merged' class
                    if(driver.findElements(cellLocator).isEmpty()){//if cell hasn't numeric value
                        result.append("0");
                    }else //if cell not empty print his value
                        result.append(driver.findElement(cellLocator).getText());
                }else //else print 'mergedCell' text
                    result.append(driver.findElement(mergedCellLocator).getText());
                result.append(" | ");
            }
        }
        return result.toString();
    }

    //check that 'game over message' class is present in DOM
    public int checkGameOver(){
        return(driver.findElements(gameOverMessage).size());
    }

    //wait for visibility of 'game over message'
    public void waitGameOverMessage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(gameOverMessage));
    }

    //get text about end of game
    public String getGameOverText(){
        return(driver.findElement(gameOverMessage).getText());
    }
}
