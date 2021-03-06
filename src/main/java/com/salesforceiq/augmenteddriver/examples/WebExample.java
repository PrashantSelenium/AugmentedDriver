package com.salesforceiq.augmenteddriver.examples;

import com.salesforceiq.augmenteddriver.testcases.AugmentedWebTestCase;
import com.salesforceiq.augmenteddriver.util.Suites;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.allure.annotations.Title;

@Suites("SMOKE")
public class WebExample extends AugmentedWebTestCase {

    public static int count = 0;
    @Test
    @Title("Successful Test")
    public void testSucceed() {
        driver().get("https://www.wikipedia.org/");

        driver()
                .augmented()
                .findElementClickable(By.id("searchInput"))
                .sendKeys("WebDriver");
        driver()
                .augmented()
                .findElementsVisible(By.className("suggestion-link"))
                .get(0)
                .click();
        driver()
                .augmented()
                .findElementVisible(By.id("firstHeading"));
    }

    @Test
    @Title("Failed Test")
    public void testFail() {
        count++;
        driver().get("https://www.wikipedia.org/");

        stepOne();
        if (count != 4433) {
            Assert.fail("THEREASON");
        }
    }

    @Step("STEP ONE")
    public void stepOne() {

    }

}
