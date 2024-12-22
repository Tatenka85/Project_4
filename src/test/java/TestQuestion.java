import pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestQuestion {

    private final int questionIndex;
    private final int answerIndex;
    private final int expectedAnswerIndex;
    private WebDriver driver;
    private MainPage testQuestionPage;

    // Конструктор для параметризации
    public TestQuestion(int questionIndex, int answerIndex, int expectedAnswerIndex) {
        this.questionIndex = questionIndex;
        this.answerIndex = answerIndex;
        this.expectedAnswerIndex = expectedAnswerIndex;
    }

    @Before
    public void before() {
        driver = WebDriverFactory.createForEnvironment();
        testQuestionPage = new MainPage(driver);
        testQuestionPage.openPage();
        MainPage.closeCookiePopup(driver); // Закрытие попапа с куками
    }

    @Parameterized.Parameters
    public static Object[][] questionImportant() {
        return new Object[][]{
                {0, 0, 0},
                {1, 1, 1},
                {2, 2, 2},
                {3, 3, 3},
                {4, 4, 4},
                {5, 5, 5},
                {6, 6, 6},
                {7, 7, 7}
        };
    }

    @Test
    public void testQuestionImportant() {
        testQuestionPage.scrollToQuestion(questionIndex);
        testQuestionPage.clickQuestion(questionIndex);
        String actualAnswer = testQuestionPage.getAnswerText(answerIndex);
        String expectedAnswer = testQuestionPage.getExpectedAnswerText(expectedAnswerIndex);
        assertEquals("Индекс ответа не совпадает с ожидаемым!", expectedAnswer, actualAnswer);
    }

    @After
    public void after() {
        driver.quit();
    }
}

