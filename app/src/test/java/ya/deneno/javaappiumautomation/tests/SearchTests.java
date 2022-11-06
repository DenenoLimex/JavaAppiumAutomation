package ya.deneno.javaappiumautomation.tests;

import org.junit.Test;

import ya.deneno.javaappiumautomation.CoreTestCase;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch() {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        searchPageObject.initSearchInput();
        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        searchPageObject.initSearchInput();
        String search = "Linkin Park Discography";
        searchPageObject.typeSearchLine(search);
        searchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "Count of element is 0",
                searchPageObject.getAmountOfFoundArticles() > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        searchPageObject.initSearchInput();
        String search = "zxvzxvzxcvzxcvzxcv";
        searchPageObject.typeSearchLine(search);
        searchPageObject.waitForEmptyResultLabel();
        searchPageObject.assertThereIsNoResultOfSearch();
    }
}
