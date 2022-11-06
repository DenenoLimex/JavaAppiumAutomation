package ya.deneno.javaappiumautomation.tests;

import org.junit.Test;

import ya.deneno.javaappiumautomation.CoreTestCase;

public class ArticleTests extends CoreTestCase {
    @Test
    public void testCompareArticleTitle() {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        String articleTitle = articlePageObject.getArticleTitle();
        assertEquals(
                "We see unexpected title!",
                articleTitle,
                "Java (programming language)"
        );
    }

    @Test
    public void testSwipeArticle() {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Appium");
        searchPageObject.clickByArticleWithSubstring("Appium");
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }
}
