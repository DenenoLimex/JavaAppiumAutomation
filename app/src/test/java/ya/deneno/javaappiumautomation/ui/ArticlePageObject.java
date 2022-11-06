package ya.deneno.javaappiumautomation.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumDriver;

public class ArticlePageObject extends MainPageObject {
    public static final String
            TITLE = "org.wikipedia:id/view_page_title_text",
            FOOTER_ELEMENT = "//*[@text='View page in browser']";

    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() {
        return waitForElementPresent(
                By.id(TITLE),
                "Cannot find Article title on page",
                15
        );
    }

    public String getArticleTitle() {
        return waitForTitleElement().getAttribute("text");
    }

    public void swipeToFooter() {
        swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Can't find the end of the article",
                20
        );
    }
}
