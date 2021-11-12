package com.qaupskilling.cucumber.pageObjects;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EtsyPagePO {
    private final WebDriver driver;
    public static final String ETSY_URL = "https://www.etsy.com/";
    public static final String GDPR_PRIVACY_SETTINGS = "gdpr-privacy-settings";

    @FindBy(xpath = "//button[@class='wt-btn wt-btn--filled wt-mb-xs-0']")
    public WebElement acceptBtn;

    @FindBy(xpath = "//button[@class='wt-btn wt-btn--small wt-btn--transparent wt-mr-xs-1 inline-overlay-trigger signin-header-action select-signin']")
    public WebElement signInButton;

    @FindBy( xpath = "//ul[contains(@class,'wt-list-unstyled')]")
    public WebElement popularList;

    @FindBy(xpath = "//form[@class='subscribe-form not-signed-in']")
    public WebElement subscribeArea;

    @FindBy(xpath = "//*[@id='content']/div/div[6]/div/div/div/div/div[1]/div[1]/a/h2")
    public WebElement selections;

    public EtsyPagePO(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void goTo(String etsyUrl) {
        driver.get(etsyUrl);
    }

    public void acceptPrivacyPolicy() {
        this.acceptBtn.click();
    }

    public void waitForPolicyWindowToDissapear() {
        WebElement policyWindow = driver.findElement(By.id(GDPR_PRIVACY_SETTINGS));
        boolean pass = new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf(policyWindow));

        Assertions.assertTrue(pass);
    }

    public void elementIsVisible(String string) {
        WebElement element = getElementFromGherkinStringParameter(string);
        Assertions.assertTrue(elementIsVisible(element));

    }

    public boolean elementIsVisible(WebElement element) {
        element = new WebDriverWait(driver, 5).until(
                ExpectedConditions.visibilityOf(element)
        );
        return element.isDisplayed();
    }

    private WebElement getElementFromGherkinStringParameter(String locator) {
        WebElement result = null;

        switch (locator) {
            case "Popular list":
                result = popularList;
                break;
            case "Signing button":
                result = signInButton;
                break;
            case "Selections area":
                result = selections;
                break;
            case "Subscribe area":
                result = subscribeArea;
        }
        return result;
    }
}
