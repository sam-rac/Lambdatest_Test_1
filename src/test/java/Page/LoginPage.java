package Page;

import io.github.bonigarcia.wdm.managers.ChromeDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final By usrNameTextBox = By.id("username");
    private final By pswdTextBox = By.id("password");
    private final By loginBtn = By.xpath("//button[contains(text(),'Login')]");
    private final By toast = By.xpath("//div[@class='automation__toast toast jam']");

    @BeforeClass
    public void before() {
        ChromeDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);

        driver.get("https://www.lambdatest.com/automation-demos/");

        wait = new WebDriverWait(driver, Duration.ofSeconds(300));

        driver.manage().window().maximize();
    }

    @Test
    public void LoginTest() throws AWTException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usrNameTextBox)).sendKeys("lambda");

        wait.until(ExpectedConditions.visibilityOfElementLocated(pswdTextBox)).sendKeys("lambda123");

        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();

        String toastTxt = wait.until(ExpectedConditions.visibilityOfElementLocated(toast)).getText();
        System.out.println(toastTxt);
        assertThat(toastTxt, containsString("Thank You Successully Login!!"));
        if (toastTxt.contains("Thank You Successully Login!!")) {
            System.out.println("Login is Succesful");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#developer-name"))).sendKeys("raichur.samir@gmail.com");
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#populate"))).click();
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alrt = driver.switchTo().alert();
            System.out.println(driver.switchTo().alert().getText());
            alrt.accept();
        }
        driver.switchTo().defaultContent();

        WebElement eCommerce_website_radioBtn = driver.findElement(By.cssSelector("input[id='3months']"));//(By.id("3months"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", eCommerce_website_radioBtn);
        //wait.until(ExpectedConditions.elementToBeClickable(By.id("3months"))).click();

        /*WebElement eCommerce_purchase_chkBox = driver.findElement(By.xpath("//input[@id='customer-service']']"));//(By.id("3months"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click",eCommerce_purchase_chkBox);
*/
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='customer-service']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='preferred-payment']"))).click();

        Select payment_mode_dropDown = new Select(driver.findElement(By.xpath("//select[@id='preferred-payment']")));
        payment_mode_dropDown.selectByIndex(1);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("tried-ecom"))).click();

        WebElement experience_slider = driver.findElement(By.xpath("//div[@role='slider']//div"));

        assertTrue(experience_slider.isDisplayed());

        WebElement sliderHiddenElement = driver.findElement(By.cssSelector("#__next > div.wrapper > section.my-50.automationbar.smtablet\\:px-20 > div > div > div.form-bottom > div.sliderBar > div > div > div:nth-child(1) > div > div:nth-child(12)"));

        Dimension sliderSize = experience_slider.getSize();
        int sliderWidth = sliderSize.getWidth();
        System.out.println("Slider Width =" + sliderWidth);
        int xCoord = experience_slider.getLocation().getX();
        System.out.println("Slider position =" + xCoord);

/*        Actions builder = new Actions(driver);
        builder.moveToElement(experience_slider)
                .click()
                .dragAndDropBy(experience_slider,(xCoord+sliderWidth)-100, 0)
                .build()
                .perform();*/

        Actions builder = new Actions(driver);
        int iCount = 0;

        for (iCount = 0; iCount < 4; iCount++) {
            builder.moveToElement(experience_slider).click(experience_slider).sendKeys(Keys.ARROW_UP).perform();
        }
/*
        int sliderWidth_new = sliderSize.getWidth();
        System.out.println("New Slider Width ="+sliderWidth_new);
        int xCoord_new = experience_slider.getLocation().getX();
        System.out.println("New Slider position ="+xCoord_new);*/

        WebElement hiddenSlider = driver.findElement(By.xpath("//div[@role='slider']"));

        Integer actualValue = Integer.parseInt(hiddenSlider.getAttribute("aria-valuenow"));
        actualValue = actualValue / 10;
        assertThat(9, equalTo(actualValue));

        //((JavascriptExecutor)driver).executeScript("window.open('https://www.lambdatest.com/selenium-automation/','_blank');");
        ((JavascriptExecutor) driver).executeScript("window.open('https://www.lambdatest.com/selenium-automation/','_blank');");
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs2.get(1));

        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

        // WebElement imgElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@title='Jenkins']")));
        // String src = imgElement.getAttribute("src");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        By Image = By.xpath("//img[@title='Jenkins']");

        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        //  WebElement ImageElemnt =driver.findElement(Image);
 /*       String src = ImageElemnt.getAttribute("src");
        System.out.println(src);*/
        wait.ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.invisibilityOfElementLocated(Image));

    }

}
