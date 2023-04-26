import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class SubscriptionPackagesStepDefinitions {
    private WebDriver driver;

    @Given("I am on the subscription page")
    public void iAmOnTheSubscriptionPage() {
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://subscribe.stctv.com/");
        // this 2 lines solve the problem of the last update of chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("https://classic.crmpro.com/index.html");
    }

    @When("I select {string} country")
    public void iSelectCountry(String country) {
        WebElement countryDropdown = driver.findElement(By.id("country"));
        countryDropdown.sendKeys(country);
    }

    @Then("I should see the subscription packages with valid type, price and currency")
    public void iShouldSeeTheSubscriptionPackagesWithValidTypePriceAndCurrency() {
        WebElement subscriptionPackagesTable = driver.findElement(By.id("packages"));
        WebElement tbody = subscriptionPackagesTable.findElement(By.tagName("tbody"));
        WebElement tr = tbody.findElement(By.tagName("tr"));
        Map<String, String> expectedPackages = getExpectedPackages(tr.getText());

        for (Map.Entry<String, String> expectedPackage : expectedPackages.entrySet()) {
            String packageType = expectedPackage.getKey();
            String expectedPriceAndCurrency = expectedPackage.getValue();
            WebElement td = tr.findElement(By.xpath("//td[contains(text(), '" + packageType + "')]"));
            String actualPriceAndCurrency = td.getText().replace(packageType, "").trim();

            Assert.assertEquals(expectedPriceAndCurrency, actualPriceAndCurrency);
        }

        driver.quit();
    }

    private Map<String, String> getExpectedPackages(String packagesText) {
        Map<String, String> expectedPackages = new HashMap<>();
        String[] packages = packagesText.split("\n");

        for (String packageStr : packages) {
            String[] packageDetails = packageStr.split(" ");
            String packageType = packageDetails[0];
            String priceAndCurrency = packageDetails[1];
            expectedPackages.put(packageType, priceAndCurrency);
        }

        return expectedPackages;
    }
}
