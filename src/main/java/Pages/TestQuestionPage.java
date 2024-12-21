package Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;


public class TestQuestionPage {


    // Локаторы элементов
    private static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";//URL страницы Яндекс.Самокат
    private final By cookiePopup = By.xpath("//*[@class='App_CookieConsent__1yUIN']");//модалка куки
    private final By cookiePopupClose = By.xpath("//*[@class='App_CookieButton__3cvqF']");//кнопка закрытия модалки куки


    public static final String BROWSER_NAME_ENV_VARIABLE = "BROWSER_NAME";//лоактор для кроссбраузерности
    public static final String DEFAULT_BROWSER_NAME = "CHROME";//браузер для вызова теста
    public static final String QUESTION_1 = "accordion__heading-0";//первый вопрос
    public static final String QUESTION_2 = "accordion__heading-1";//второй вопрос
    public static final String QUESTION_3 = "accordion__heading-2";//третий вопрос
    public static final String QUESTION_4 = "accordion__heading-3";//четвертый вопрос
    public static final String QUESTION_5 = "accordion__heading-4";//пятый вопрос
    public static final String QUESTION_6 = "accordion__heading-5";//шестой вопрос
    public static final String QUESTION_7 = "accordion__heading-6";//седьмой вопрос
    public static final String QUESTION_8 = "accordion__heading-7";//восьмой вопрос
    public static final String ANSWER_1 = "accordion__panel-0";//ответ на первый вопрос на странице браузера
    public static final String ANSWER_2 = "accordion__panel-1";//ответ на второй вопрос на странице браузера
    public static final String ANSWER_3 = "accordion__panel-2";//ответ на третий вопрос на странице браузера
    public static final String ANSWER_4 = "accordion__panel-3";//ответ на четвертый вопрос на странице браузера
    public static final String ANSWER_5 = "accordion__panel-4";//ответ на пятый вопрос на странице браузера
    public static final String ANSWER_6 = "accordion__panel-5";//ответ на шестой вопрос на странице браузера
    public static final String ANSWER_7 = "accordion__panel-6";//ответ на сельмой вопрос на странице браузера
    public static final String ANSWER_8 = "accordion__panel-7";//ответ на восьмой вопрос на странице браузера
    public static final String TEXT_ANSWER_1 = "Сутки — 400 рублей. Оплата курьеру — наличными или картой.";//эталонный ответ на первый вопрос на странице браузера
    public static final String TEXT_ANSWER_2 = "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.";//эталонный ответ на второй вопрос на странице браузера
    public static final String TEXT_ANSWER_3 = "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.";//эталонный ответ на третий вопрос на странице браузера
    public static final String TEXT_ANSWER_4 = "Только начиная с завтрашнего дня. Но скоро станем расторопнее.";//эталонный ответ на четвертый вопрос на странице браузера
    public static final String TEXT_ANSWER_5 = "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.";//эталонный ответ на пятый вопрос на странице браузера
    public static final String TEXT_ANSWER_6 = "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.";//эталонный ответ на шестой вопрос на странице браузера
    public static final String TEXT_ANSWER_7 = "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.";//эталонный ответ на седьмой вопрос на странице браузера
    public static final String TEXT_ANSWER_8 = "Да, обязательно. Всем самокатов! И Москве, и Московской области.";//эталонный ответ на восьмой вопрос на странице браузера
    private final By questionLocator;//локатор полей с вопросами
    private final By answerLocator;//локатор полей с ответами в браузере


    public TestQuestionPage(String questionId, String answerId) {//конструктор для параметризации
        this.questionLocator = By.id(questionId);
        this.answerLocator = By.id(answerId);
    }


    public void openPage(WebDriver driver) {//открытие страницы
        driver.get(PAGE_URL);
    }


    public void closeCookiePopup(WebDriver driver) {//метод закрытия модалки с куками
        try {
            WebDriverWait waitForCookiePopup = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement cookieConsentPopup = waitForCookiePopup.until(ExpectedConditions.visibilityOfElementLocated(cookiePopup));
            if (cookieConsentPopup.isDisplayed()) {
                WebElement closeButton = cookieConsentPopup.findElement(cookiePopupClose);
                closeButton.click();//клик на кнопку закрытия модалки с куками
            }
        } catch (Exception ignored) {
        }
    }


    public void scrollToQuestion(WebDriver driver) {//метод для скролла до вопроса
        WebElement question = driver.findElement(questionLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);
    }


    public void clickQuestion(WebDriver driver) {//метод клика по полю с вопросом
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement clickableQuestion = wait.until(ExpectedConditions.elementToBeClickable(questionLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableQuestion);
    }


    public String getAnswerText(WebDriver driver) {//метод получения текста ответа со страницы браузера
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement answer = wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return answer.getText();
    }
}
