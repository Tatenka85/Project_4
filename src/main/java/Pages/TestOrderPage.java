package Pages;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;


public class TestOrderPage {


    // Константы для локаторов кнопок "Заказать"
    public static final By ORDER_BUTTON_TOP = By.xpath("//*[@class='Button_Button__ra12g']"); // Кнопка Заказать вверху страницы
    public static final By ORDER_BUTTON_BOTTOM = By.xpath("//*[@class='Button_Button__ra12g Button_Middle__1CSJM']"); // Кнопка Заказать внизу страницы


    // Локаторы элементов на странице
    public static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/order";
    public static final String BROWSER_NAME_ENV_VARIABLE = "BROWSER_NAME";
    public static final String DEFAULT_BROWSER_NAME = "CHROME";
    public final By cookiePopup = By.xpath("//*[@class='App_CookieConsent__1yUIN']"); // Модалка с куки
    public final By cookiePopupClose = By.xpath("//*[@class='App_CookieButton__3cvqF']"); // Кнопка закрытия модалки куки
    public final By nameInput = By.xpath("//input[@placeholder='* Имя']"); // Поле для ввода имени
    public final By surnameInput = By.xpath("//input[@placeholder='* Фамилия']"); // Поле для ввода фамилии
    public final By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']"); // Поле для ввода адреса
    public final By stationInput = By.xpath("//input[@placeholder='* Станция метро']"); // Поле выбора станции метро
    public final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']"); // Поле ввода телефона
    public final By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']"); // Поле выбора даты
    public final By timeInput = By.xpath("//div[contains(@class,'Dropdown-placeholder')]"); // Поле выбора срока аренды
    public final By submitButton = By.xpath("//button[text()='Далее']"); // Кнопка Далее
    public final By orderButtonOrder = By.xpath("//*[@class='Button_Button__ra12g Button_Middle__1CSJM']"); // Кнопка Заказать в заказе
    public final By confirmPopup = By.xpath("//div[@class='Order_Modal__YZ-d3']"); // Модалка подтверждения заказа
    public final By yesButton = By.xpath("//button[text()='Да']"); // Кнопка Да на модалке
    public final By successPopup = By.xpath("//div[@class='Order_Modal__YZ-d3']"); // Модалка успешного создания заказа
    public final By statusButton = By.xpath("//button[text()='Посмотреть статус']"); // Кнопка Просмотра статуса на модалке успешного создания заказа


    private final String name;
    private final String surname;
    private final String address;
    private final String station;
    private final String phone;
    private final String date;
    private final String time;


    // Конструктор для параметризации
    public TestOrderPage(String name, String surname, String address, String station, String phone, String date, String time) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.date = date;
        this.time = time;
    }


    // Открытие страницы
    public void openPage(WebDriver driver) {
        driver.get(PAGE_URL);
    }


    // Закрытие модалки с куками
    public void closeCookiePopup(WebDriver driver) {
        try {
            WebDriverWait waitForCookiePopup = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement cookieConsentPopup = waitForCookiePopup.until(ExpectedConditions.visibilityOfElementLocated(cookiePopup));
            if (cookieConsentPopup.isDisplayed()) {
                WebElement closeButton = cookieConsentPopup.findElement(cookiePopupClose);
                closeButton.click(); // Клик на кнопку закрытия модалки с куками
            }
        } catch (Exception e) {
            System.out.println("Cookie popup close failed: " + e.getMessage());
        }
    }


    // Заполнение данных в форме заказа
    private void fillFormFields(WebDriver driver) {
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(surnameInput).sendKeys(surname);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(stationInput).sendKeys(station); // Пример станции метро
        driver.findElement(stationInput).sendKeys(Keys.DOWN, Keys.ENTER); // Клик и выбор станции
        driver.findElement(phoneInput).sendKeys(phone);
    }


    // Заполнение формы даты и времени
    private void fillDateAndTime(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dateInputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dateInput));
        dateInputElement.click();
        dateInputElement.sendKeys(date);
        dateInputElement.sendKeys(Keys.ENTER);


        WebElement timeInputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(timeInput));
        timeInputElement.click();
        WebElement timeOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='Dropdown-option'][text()='" + time + "']")));
        timeOption.click();
    }


    // Оформление заказа
    public void fillOrderForm(WebDriver driver) {
        // Ожидание, пока кнопка "Заказать" вверху страницы будет доступна
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(ORDER_BUTTON_TOP));
        orderButton.click(); // Клик по кнопке «Заказать»


        // Проверяем, что форма заказа открыта
        WebElement nameInputElement = driver.findElement(nameInput);
        assert nameInputElement.isDisplayed();


        // Заполнение формы заказа
        fillFormFields(driver);


        // Нажатие кнопки Далее для перехода
        driver.findElement(submitButton).click();


        // Заполнение даты и времени
        fillDateAndTime(driver);


        // Нажатие кнопки Заказать
        driver.findElement(orderButtonOrder).click();
    }


    // Клик по кнопке "Да" в модалке подтверждения
    public void confirmOrderForm(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        // Подтверждаем заказ
        WebElement confirmPopupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPopup));
        assert confirmPopupElement.isDisplayed();
        driver.findElement(yesButton).click();


        // Ожидаем успешного подтверждения
        WebElement successPopupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
        assert successPopupElement.isDisplayed();


        // Клик по кнопке "Посмотреть статус"
        driver.findElement(statusButton).click();
    }
}
