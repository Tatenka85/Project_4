import Pages.TestQuestionPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;


import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class TestQuestion {


    private final String questionId;
    private final String answerId;
    private final String expectedAnswer;


    private WebDriver driver;
    private TestQuestionPage testQuestionPage;


    // Конструктор для параметризации
    public TestQuestion(String questionId, String answerId, String expectedAnswer) {
        this.questionId = questionId;
        this.answerId = answerId;
        this.expectedAnswer = expectedAnswer;
    }


    @Before
    public void before() {
        String browserName = System.getenv(TestQuestionPage.BROWSER_NAME_ENV_VARIABLE);
        driver = WebDriverFactory.createForName(browserName != null ? browserName : TestQuestionPage.DEFAULT_BROWSER_NAME);


        testQuestionPage = new TestQuestionPage(questionId, answerId);
    }


    @Parameterized.Parameters
    public static Object[] QuestionImportant() {//набор данных для параметризированного теста
        return new Object[][]{
                {TestQuestionPage.QUESTION_1, TestQuestionPage.ANSWER_1, TestQuestionPage.TEXT_ANSWER_1},
                {TestQuestionPage.QUESTION_2, TestQuestionPage.ANSWER_2, TestQuestionPage.TEXT_ANSWER_2},
                {TestQuestionPage.QUESTION_3, TestQuestionPage.ANSWER_3, TestQuestionPage.TEXT_ANSWER_3},
                {TestQuestionPage.QUESTION_4, TestQuestionPage.ANSWER_4, TestQuestionPage.TEXT_ANSWER_4},
                {TestQuestionPage.QUESTION_5, TestQuestionPage.ANSWER_5, TestQuestionPage.TEXT_ANSWER_5},
                {TestQuestionPage.QUESTION_6, TestQuestionPage.ANSWER_6, TestQuestionPage.TEXT_ANSWER_6},
                {TestQuestionPage.QUESTION_7, TestQuestionPage.ANSWER_7, TestQuestionPage.TEXT_ANSWER_7},
                {TestQuestionPage.QUESTION_8, TestQuestionPage.ANSWER_8, TestQuestionPage.TEXT_ANSWER_8}
        };
    }


    @Test
    public void testQuestionImportant() {
        // Закрытие всплывающего окна с cookies
        testQuestionPage.closeCookiePopup(driver);


        // Открытие страницы и выполнение действий
        testQuestionPage.openPage(driver);
        testQuestionPage.scrollToQuestion(driver);
        testQuestionPage.clickQuestion(driver);


        // Получаем текст ответа и сравниваем с ожидаемым
        String actualAnswer = testQuestionPage.getAnswerText(driver);
        assertEquals("Текст ответа не совпадает с ожидаемым!", expectedAnswer, actualAnswer);
    }


    @After
    public void after() {
        driver.quit();
    }
}
