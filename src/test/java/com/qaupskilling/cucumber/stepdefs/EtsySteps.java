package com.qaupskilling.cucumber.stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EtsySteps {
    public static final String GDPR_PRIVACY_SETTINGS = "gdpr-privacy-settings";
    public static final String ETSY_TITLE = "Etsy Poland - Shop for handmade, vintage, custom, and unique gifts for everyone";
    public static final String ETSY_URL = "https://www.etsy.com/";
    public static final String ACCEPT_BTN = "[class='wt-btn wt-btn--filled wt-mb-xs-0']";
    private WebDriver driver;

    @Given("{string} on the shop landing page")
    public void john_on_the_shop_landing_page(String string) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mhanderek\\chromedriver.exe");
        driver = new ChromeDriver();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ETSY_URL);
    }

    @When("(she|he) accepts privacy policy with default settings")
    public void he_accepts_privacy_policy_with_default_settings() {
        WebElement acceptButton = driver.findElement(By.cssSelector(ACCEPT_BTN));
        acceptButton.click();
    }

    @Then("{string} can proceed with shopping")
    public void john_can_proceed_with_shopping(String string) {
        WebElement policyWindow = driver.findElement(By.id(GDPR_PRIVACY_SETTINGS));
        boolean pass = new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf(policyWindow));

        Assertions.assertTrue(driver.getTitle().equals(ETSY_TITLE));
        Assertions.assertTrue(driver.getCurrentUrl().equals(ETSY_URL));
        //Assertions.assertFalse(policyWindow.isDisplayed());
        Assertions.assertTrue(pass);
        driver.quit();
    }


}
