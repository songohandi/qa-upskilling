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
    public static final String ETSY_URL = "https://www.etsy.com/";
    private WebDriver driver;
    private EtsyPagePO landingPage;

    @Before("@ui")
    public void scenarioSetUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mhanderek\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        landingPage = new EtsyPagePO(driver);
    }


    @After("@ui")
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

    @Then("{string} is visible")
    public void elementIsVisible(String string) {
        landingPage.elementIsVisible(string);
    }
}
