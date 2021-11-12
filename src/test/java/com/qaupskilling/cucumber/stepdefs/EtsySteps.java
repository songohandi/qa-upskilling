package com.qaupskilling.cucumber.stepdefs;

import com.qaupskilling.cucumber.pageObjects.EtsyPagePO;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
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

import java.util.concurrent.TimeUnit;

public class EtsySteps {
    public static final String GDPR_PRIVACY_SETTINGS = "gdpr-privacy-settings";
    public static final String ETSY_TITLE = "Etsy Poland - Shop for handmade, vintage, custom, and unique gifts for everyone";
    public static final String ETSY_URL = "https://www.etsy.com/";
    public static final String ACCEPT_BTN = "[class='wt-btn wt-btn--filled wt-mb-xs-0']";
    public static final String SIGNIN_BUTTON = "//button[@class='wt-btn wt-btn--small wt-btn--transparent wt-mr-xs-1 inline-overlay-trigger signin-header-action select-signin']";
    public static final String POPULAR_LIST = "//ul[contains(@class,'wt-list-unstyled')]";
    public static final String SUBSCRIBE_AREA = "//form[@class='subscribe-form not-signed-in']";
    //public static final String SELECTIONS = "//h2[@class='hp-text-title-xs-02 hp-text-title-md-03 wt-display-inline-block']";
    public static final String SELECTIONS = "//*[@id='content']/div/div[6]/div/div/div/div/div[1]/div[1]/a/*";
    private WebDriver driver;
    private EtsyPagePO landingPage;

    @Before
    public void scenarioSetUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mhanderek\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        landingPage = new EtsyPagePO(driver);
    }


    @After
    public void scenarioTearDown() {
        driver.quit();
    }

    @Given("{string} on the shop landing page")
    public void johnOnTheShopLandingPage(String string) {
        landingPage.goTo(ETSY_URL);

    }

    @When("(she|he) accepts privacy policy with default settings")
    public void heAcceptsPrivacyPolicyWithDefaultSettings() {
        landingPage.acceptPrivacyPolicy();
    }

    @Then("{string} can proceed with shopping")
    public void johnCanProceedWithShopping(String string) {
        landingPage.waitForPolicyWindowToDissapear();
    }


    /*
    This one is not using POM yet, but I used it instead of four different methods to check elements visibility
     */
    @Then("{string} is visible")
    public void elementIsVisible(String string) {
        String locator = getLocator(string);

        WebElement element = new WebDriverWait(driver, 5).until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))
        );
        Assertions.assertTrue(element.isDisplayed());
    }

    private String getLocator(String locator) {
        String result = "";
        switch (locator) {
            case "Popular list":
                result = POPULAR_LIST;
                break;
            case "Signing button":
                result = SIGNIN_BUTTON;
                break;
            case "Selections area":
                result = SELECTIONS;
                break;
            case "Subscribe area":
                result = SUBSCRIBE_AREA;
        }
        return result;
    }
}
