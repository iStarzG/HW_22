package tests;

import com.codeborne.selenide.Configuration;
import config.DriverConfig;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBaseAPI {
    @BeforeAll
    static void setup() {
        System.setProperty("driver", System.getProperty("driver", "remote"));
        Configuration.pageLoadStrategy = "eager";
        Configuration.baseUrl = "https://demoqa.com";
        RestAssured.baseURI = "https://demoqa.com";
       // Configuration.holdBrowserOpen = true;
        DriverConfig driverConfig = ConfigFactory.create(DriverConfig.class);
        Configuration.browser = driverConfig.browserName();
        Configuration.browserVersion = driverConfig.browserVersion();
        Configuration.browserSize = driverConfig.browserSize();
        if (driverConfig.isRemote()) {
            Configuration.remote = driverConfig.browserRemoteUrl();

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }
    }

    @AfterEach
    void tearDown() {
          closeWebDriver();
    }
}

