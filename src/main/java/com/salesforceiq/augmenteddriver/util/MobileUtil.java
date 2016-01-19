package com.salesforceiq.augmenteddriver.util;

import com.google.common.base.Preconditions;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

/**
 * Common utilities for IOS and Android.
 */
public class MobileUtil {

    private static final int VERTICAL_OFFSET = 10;
    private static final int BIG_NUMBER = 9999999;
    private static final int DEFAULT_DURATION = 1000;
    private static final int DEFAULT_TAP_DURATION = 500;

    /**
     * Taps on an element based on the coordinates (Sometimes the element is there but not clickable).
     *
     * @param driver The AppiumDriver used to tap.
     * @param augmentedFunctions all the augmented functions.
     * @param by the by that identifies the element
     * @param waitTimeInSeconds how much time to wait until the element becomes visible
     * @return the element that was tapped.
     */
    public static WebElement tap(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions, By by, int waitTimeInSeconds) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(by);

        WebElement element = augmentedFunctions.findElementPresentAfter(by, waitTimeInSeconds);
        tap(driver, element, DEFAULT_TAP_DURATION);
        return  element;
    }

    /**
     * Taps on a location determined by the coordinated of the element plus the offsets.
     *
     * @param driver The AppiumDriver used to tap.
     * @param augmentedFunctions all the augmented functions.
     * @param by the by that identifies the element
     * @param offsetX horizontal offset (can be negative) where the tap will occur from the element
     * @param offsetY vertical offset (can be negative) where the tap will occur from the element.
     * @param waitTimeInSeconds how much time to wait until the element becomes visible
     * @return the element that was used to determine the coordinates.
     */
    public static WebElement tap(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions,
                                 By by, int offsetX, int offsetY, int waitTimeInSeconds) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(by);

        WebElement element = augmentedFunctions.findElementPresentAfter(by, waitTimeInSeconds);
        driver.tap(1, element.getLocation().getX() + offsetX, element.getLocation().getY() + offsetY, DEFAULT_TAP_DURATION);
        return element;
    }

    /**
     * Taps on an element. The tap lasts pressInMilliSeconds. Sometimes the element is present but
     * not clickable (do not know why).
     *
     * @param driver The AppiumDriver used to tap.
     * @param element the Element that is going to be tapped.
     * @param pressInMilliSeconds how much time in milliseconds.
     */
    public static void tap(AppiumDriver driver, WebElement element, int pressInMilliSeconds) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(element);

        driver.tap(1, element.getLocation().getX(), element.getLocation().getY(), pressInMilliSeconds);
    }

    /**
     * Swipes up on an element, until the other element becomes visible.
     *
     * @param driver The AppiumDriver used to swipe up.
     * @param augmentedFunctions all the augmented functions.
     * @param swipeElement form which element the swipe up should start
     * @param elementVisible which element should become visible
     * @return the element visible.
     */
    public static WebElement swipeUpWaitVisible(AppiumDriver driver,
                                                AugmentedFunctions<?> augmentedFunctions,
                                                By swipeElement,
                                                By elementVisible) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(swipeElement);
        Preconditions.checkNotNull(elementVisible);

        return swipeVerticalWaitVisible(driver, augmentedFunctions, swipeElement,
                elementVisible, - BIG_NUMBER, 5, DEFAULT_DURATION);
    }


    /**
     * Swipes down on an element, until the other element becomes visible.
     *
     * @param driver The AppiumDriver used to swipe down.
     * @param augmentedFunctions all the augmented functions.
     * @param swipeElement form which element the swipe up should start
     * @param elementVisible which element should become visible
     * @return the element visible.
     */
    public static WebElement swipeDownWaitVisible(AppiumDriver driver,
                                                AugmentedFunctions<?> augmentedFunctions,
                                                By swipeElement,
                                                By elementVisible) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(swipeElement);
        Preconditions.checkNotNull(elementVisible);

        return swipeVerticalWaitVisible(driver, augmentedFunctions, swipeElement,
                elementVisible, BIG_NUMBER, 5, DEFAULT_DURATION);
    }

    /**
     * Swipes up on an element, pressing a default amount of time before swiping.
     *
     * @param driver The AppiumDriver used to swipe Up.
     * @param augmentedFunctions all the augmented functions.
     * @param swipeBy from which element the swipe up should start.
     */
    public static void swipeUp(AppiumDriver driver,
                               AugmentedFunctions<?> augmentedFunctions,
                               By swipeBy) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(swipeBy);

        swipeVertical(driver, augmentedFunctions, swipeBy, -BIG_NUMBER, DEFAULT_DURATION);
    }

    /**
     * Swipes down on an element, pressing a default amount of time before swiping.
     *
     * @param driver The AppiumDriver used to swipe Up.
     * @param augmentedFunctions all the augmented functions.
     * @param swipeBy from which element the swipe down should start.
     */
    public static void swipeDown(AppiumDriver driver,
                               AugmentedFunctions<?> augmentedFunctions,
                               By swipeBy) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(swipeBy);

        swipeVertical(driver, augmentedFunctions, swipeBy, BIG_NUMBER, DEFAULT_DURATION);
    }

    /**
     * Swipes vertical (depends on the offset if negative or positive) on an element, pressing
     * for a specific duration before swiping.
     *
     * @param driver The AppiumDriver used to swipe Up.
     * @param augmentedFunctions all the augmented functions.
     * @param swipeBy from which element the swipe should start.
     * @param offset The offset where to end the swipe.
     * @param durationInMilliSeconds time to press before swiping.
     */
    public static void swipeVertical(AppiumDriver driver,
                                     AugmentedFunctions<?> augmentedFunctions,
                                     By swipeBy,
                                     int offset,
                                     int durationInMilliSeconds) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(swipeBy);

        WebElement elementPresent = augmentedFunctions.findElementPresent(swipeBy);
        int x = elementPresent.getLocation().getX() + elementPresent.getSize().getWidth() / 2;
        int y = elementPresent.getLocation().getY() + elementPresent.getSize().getHeight() /  2;
        int swipe = getVerticalOffset(driver, y, offset);
        driver.swipe(x, y, x, swipe, durationInMilliSeconds);
    }

    /**
     * Swipes vertical on an element until another becomes visible. How much to swipe depends on the
     * offset, and how much time to press before swiping depends on durationInMilliSeconds.
     *
     * Finally quantity is the amount of times it will swipe before giving up and failing since the
     * element did not show up.
     *
     * @param driver The AppiumDriver used to swipe.
     * @param augmentedFunctions all the augmented functions.
     * @param swipeElement from which element the swipe should start.
     * @param elementVisible which element has to become visibile.
     * @param offset The offset where to end the swipe.
     * @param durationInMilliSeconds time to press before swiping.
     * @param quantity how many time to try swiping.
     * @return the element that became visible.
     */
    public static WebElement swipeVerticalWaitVisible(AppiumDriver driver,
                                    AugmentedFunctions<?> augmentedFunctions,
                                    By swipeElement,
                                    By elementVisible,
                                    int offset,
                                    int quantity,
                                    int durationInMilliSeconds) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(swipeElement);
        Preconditions.checkNotNull(elementVisible);

        WebElement elementPresent = augmentedFunctions.findElementPresent(swipeElement);
        int x = elementPresent.getLocation().getX() + elementPresent.getSize().getWidth() / 2;
        int y = elementPresent.getLocation().getY() + elementPresent.getSize().getHeight() /  2;

        int swipe = getVerticalOffset(driver, y, offset);

        for(int iteration = 0; iteration < quantity; iteration++) {
            driver.swipe(x, y, x, swipe, durationInMilliSeconds);
            if (augmentedFunctions.isElementVisibleAfter(elementVisible, 3)) {
                return augmentedFunctions.findElementVisible(elementVisible);
            }
        }
        throw new AssertionError(String.format("Swiped %s with an offest of %s times but element %s not found",
                        quantity, offset, elementVisible));
    }

    /**
     * Swipes right from the left of the screen to the right of the screen on the vertical alignment of an
     * element.
     *
     * @param driver The AppiumDriver used to swipe.
     * @param augmentedFunctions all the augmented functions.
     * @param element the element to find the vertical coordinates
     * @param pressTimeInMilliSeconds Time to press the element before swiping.
     */
    public static void swipeFullRightAfter(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions, WebElement element,
                                           int pressTimeInMilliSeconds) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(element);

        Dimension size = driver.manage().window().getSize();
        int to = size.getWidth() * 85 / 100;
        int from = size.getWidth() * 15 / 100;
        int y = element.getLocation().getY() + element.getSize().getHeight() / 2;
        driver.swipe(from, y, to, y, pressTimeInMilliSeconds);
    }

    /**
     * Swipes right from the left of the screen to the right of the screen on the vertical alignment of an
     * element.
     *
     * @param driver The AppiumDriver used to swipe.
     * @param augmentedFunctions all the augmented functions.
     * @param by the element to find the vertical coordinates
     * @param waitTimeInSeconds how much time to wait until the element becomes present.
     * @param pressInMilliSeconds Time to press the element before swiping.
     */
    public static void swipeFullRightAfter(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions, By by,
                                           int pressInMilliSeconds, int waitTimeInSeconds) {
        Preconditions.checkNotNull(by);
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);

        WebElement element = augmentedFunctions.findElementPresentAfter(by, waitTimeInSeconds);
        swipeFullRightAfter(driver, augmentedFunctions, element, pressInMilliSeconds);
    }

    /**
     * Swipes left from the right of the screen to the left of the screen on the vertical alignment of an
     * element.
     *
     * @param driver The AppiumDriver used to swipe.
     * @param augmentedFunctions all the augmented functions.
     * @param element the element to find the vertical coordinates
     * @param pressTimeInMilliSeconds Time to press the element before swiping.
     */
    public static void swipeFullLeftAfter(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions, WebElement element,
                                           int pressTimeInMilliSeconds) {
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);
        Preconditions.checkNotNull(element);

        Dimension size = driver.manage().window().getSize();
        int from = size.getWidth() * 85 / 100;
        int to = size.getWidth() * 15 / 100;
        int y = element.getLocation().getY() + element.getSize().getHeight() / 2;
        driver.swipe(from, y, to, y, pressTimeInMilliSeconds);
    }

    /**
     * Swipes left from the right of the screen to the left of the screen on the vertical alignment of an
     * element.
     *
     * @param driver The AppiumDriver used to swipe.
     * @param augmentedFunctions all the augmented functions.
     * @param by the element to find the vertical coordinates
     * @param waitTimeInSeconds how much time to wait until the element becomes present.
     * @param waitTimeInSeconds how much time to wait until the element becomes present.
     * @param pressInMilliSeconds Time to press the element before swiping.
     */
    public static void swipeFullLeftAfter(AppiumDriver driver, AugmentedFunctions<?> augmentedFunctions, By by,
                                          int pressInMilliSeconds, int waitTimeInSeconds) {
        Preconditions.checkNotNull(by);
        Preconditions.checkNotNull(driver);
        Preconditions.checkNotNull(augmentedFunctions);

        WebElement element = augmentedFunctions.findElementPresentAfter(by, waitTimeInSeconds);
        swipeFullLeftAfter(driver, augmentedFunctions, element, pressInMilliSeconds);
    }

    /**
     * Basically its caps how close you can get swiping to the vertical borders of the device
     *
     * @param driver The AppiumDriver used to swipe.
     * @param y the actual vertical position.
     * @param offset how much you want to swipe from the vertical position.
     * @return the end coordinate of the swiping action.
     */
    private static int getVerticalOffset(AppiumDriver driver, int y, int offset) {
        Preconditions.checkNotNull(driver);

        if (y + offset < VERTICAL_OFFSET) {
            return VERTICAL_OFFSET;
        } else if (y + offset > driver.manage().window().getSize().getHeight() - VERTICAL_OFFSET) {
            return driver.manage().window().getSize().getHeight() - VERTICAL_OFFSET;
        } else {
            return y + offset;
        }
    }
}
