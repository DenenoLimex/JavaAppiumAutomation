package ya.deneno.javaappiumautomation.tests;

import org.junit.Test;

import ya.deneno.javaappiumautomation.CoreTestCase;

public class MyListsTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleForMyList() {
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();
        String nameOfFolder = "Learning programming";
        articlePageObject.addArticleToMyList(nameOfFolder);
        articlePageObject.closeArticle();
        navigationUiObject.clickMyList();
        myListPageObject.openFolderByName(nameOfFolder);
        myListPageObject.swipeByArticleToDelete(articleTitle);
    }


    @Test
    public void testSaveTwoArticleForMyListAndDeleteOne() {
        String searchFirst = "Java (programming language)";
        String searchSecond = "Kotlin (programming language)";
        String nameOfFolder = "Learning programming";
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchFirst);
        searchPageObject.clickByArticleWithSubstring(searchFirst);
        articlePageObject.addArticleToMyList(nameOfFolder);
        articlePageObject.closeArticle();
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchSecond);
        searchPageObject.clickByArticleWithSubstring(searchSecond);
        articlePageObject.addArticleToMyListAfterOnBoarding(nameOfFolder);
        articlePageObject.closeArticle();
        navigationUiObject.clickMyList();
        myListPageObject.openFolderByName(nameOfFolder);
        myListPageObject.swipeByArticleToDelete(searchFirst);
        myListPageObject.waitForArticleToDisappearByTitle(searchFirst);
        myListPageObject.waitForArticleToAppearByTitle(searchSecond);
    }
}
