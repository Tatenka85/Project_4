package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;

    // URL страницы Яндекс.Самокат
    private static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";

    // Локаторы модалки с куками
    private static final By cookiePopup = By.xpath("//*[@class='App_CookieConsent__1yUIN']");
    static final By cookiePopupClose = By.xpath("//*[@class='App_CookieButton__3cvqF']");

    // Локаторы кнопок "Заказать"
    private static final By ORDER_BUTTON_TOP = By.xpath("//*[@class='Button_Button__ra12g']");
    private static final By ORDER_BUTTON_BOTTOM = By.xpath("//*[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // Локаторы вопросов и ответов
    private static final String[] QUESTIONS = {
            "accordion__heading-0", "accordion__heading-1", "accordion__heading-2", "accordion__heading-3",
            "accordion__heading-4", "accordion__heading-5", "accordion__heading-6", "accordion__heading-7"
    };

    private static final String[] ANSWERS = {
            "accordion__panel-0", "accordion__panel-1", "accordion__panel-2", "accordion__panel-3",
            "accordion__panel-4", "accordion__panel-5", "accordion__panel-6", "accordion__panel-7"
    };

    private static final String[] EXPECTED_ANSWERS = {
            "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.",
            "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.",
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
            "Да, обязательно. Всем самокатов! И Москве, и Московской области."
    };

    // Конструктор, который использует WebDriverFactory для создания WebDriver
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для открытия страницы
    public void openPage() {
        driver.get(PAGE_URL);
    }

    // Метод для закрытия модалки с куками
    public static void closeCookiePopup(WebDriver driver) {
        try {
            WebDriverWait waitForCookiePopup = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement cookieConsentPopup = waitForCookiePopup.until(ExpectedConditions.visibilityOfElementLocated(cookiePopup));
            if (cookieConsentPopup.isDisplayed()) {
                WebElement closeButton = cookieConsentPopup.findElement(cookiePopupClose);
                closeButton.click();
            }
        } catch (Exception e) {
            System.out.println("Не удалось закрыть попап с куками: " + e.getMessage());
        }
    }

    // Метод для клика на кнопку "Заказать" (вверху страницы)
    public void clickOrderButtonTop() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(ORDER_BUTTON_TOP));
        orderButton.click();
    }

    // Метод для клика на кнопку "Заказать" (внизу страницы)
    public void clickOrderButtonBottom() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(ORDER_BUTTON_BOTTOM));
        orderButton.click();
    }

    public void clickOrderButton(String position) {
        if ("top".equals(position)) {
            clickOrderButtonTop();// Нажимаем на верхнюю кнопку "Заказать"
        } else if ("bottom".equals(position)) {
            clickOrderButtonBottom();// Нажимаем на нижнюю кнопку "Заказать"
        } else {
            throw new IllegalArgumentException("Неверное значение для кнопки: " + position);
        }
    }


    // Метод для получения локатора вопроса по индексу
    public By getQuestionLocator(int index) {
        if (index >= 0 && index < QUESTIONS.length) {
            return By.id(QUESTIONS[index]);
        }
        throw new IllegalArgumentException("Invalid question index: " + index);
    }

    // Метод для получения локатора ответа по индексу
    public By getAnswerLocator(int index) {
        if (index >= 0 && index < ANSWERS.length) {
            return By.id(ANSWERS[index]);
        }
        throw new IllegalArgumentException("Invalid answer index: " + index);
    }

    // Метод для скроллинга до вопроса
    public void scrollToQuestion(int index) {
        WebElement question = driver.findElement(getQuestionLocator(index));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);
    }

    // Метод для клика по вопросу
    public void clickQuestion(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement clickableQuestion = wait.until(ExpectedConditions.elementToBeClickable(getQuestionLocator(index)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickableQuestion);
    }

    // Метод для получения текста ответа
    public String getAnswerText(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement answer = wait.until(ExpectedConditions.visibilityOfElementLocated(getAnswerLocator(index)));
        return answer.getText();
    }

    public String getExpectedAnswerText(int expectedAnswerIndex) {
        return EXPECTED_ANSWERS[expectedAnswerIndex]; // Возвращаем текст из массива
    }
}
