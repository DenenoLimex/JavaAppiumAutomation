package ya.deneno.javaappiumautomation.tests;

import org.junit.Test;

import ya.deneno.javaappiumautomation.CoreTestCase;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch() {
        String search = "Java";
        String title = "Java (programming language)";
        String description = "Object-oriented programming language";
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);
        searchPageObject.waitForElementByTitleAndDescription(title, description);
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

    @Test
    public void testCheckCountOfElementsBySearch() {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "Count of element < 2",
                searchPageObject.getAmountOfFoundArticles() > 1
        );
        searchPageObject.clickCancelSearch();
        searchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testCheckTextElementsBySearch() {
        String value = "Java";
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(value);
        searchPageObject.assertTextElementsBySearch(value);
    }

    @Test
    public void testCheckTitleAndDesriptionOfElementsBySearch() {
        String search = "Room";
        String firstTitle = "Room";
        String firstDescription = "Distinguishable space within a building or other structure";
        String secondTitle = "Room (2015 film)";
        String secondDescription = "2015 film by Lenny Abrahamson";
        String thirdTitle = "Roomba";
        String thirdDescription = "Series of autonomous robotic vacuum cleaners sold by iRobot";
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(search);
        searchPageObject.waitForElementByTitleAndDescription(firstTitle, firstDescription);
        searchPageObject.waitForElementByTitleAndDescription(secondTitle, secondDescription);
        searchPageObject.waitForElementByTitleAndDescription(thirdTitle, thirdDescription);
    }
}
