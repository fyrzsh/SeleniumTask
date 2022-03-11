package com.hackerrank.selenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.hackerrank.selenium.server.JettyServer;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;

public class WebAuthenticatorTest {
    static JettyServer server = null;
    static WebDriver driver = null;
    static String pagUrl = null;

    @BeforeClass
    public static void setup() {
        driver = new HtmlUnitDriver(BrowserVersion.CHROME, true) {
            @Override
            protected WebClient newWebClient(BrowserVersion version) {
                WebClient webClient = super.newWebClient(version);
                webClient.getOptions().setThrowExceptionOnScriptError(false);

                java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
                java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

                return webClient;
            }
        };
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        server = new JettyServer();
        server.start();

        pagUrl = "http://localhost:8080/admin";

    }

    @AfterClass
    public static void tearDown() {
        driver.close();
        server.stop();
    }

    @Test
    public void testAuthentication() throws IOException {
        String actualText = WebAuthenticator.authenticateAndGetText(driver, pagUrl);

        String expectedText = FileUtils.readFileToString(new File("random.txt"));

        assertEquals(expectedText, actualText);
    }
}
