package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    // Локаторы элементов на странице
    private static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";//URL страницы Яндекс.Самокат
    private final By nameInput = By.xpath("//input[@placeholder='* Имя']"); // Поле для ввода имени
    private final By surnameInput = By.xpath("//input[@placeholder='* Фамилия']"); // Поле для ввода фамилии
    private final By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']"); // Поле для ввода адреса
    private final By stationInput = By.xpath("//input[@placeholder='* Станция метро']"); // Поле выбора станции метро
    private final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']"); // Поле ввода телефона
    private final By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']"); // Поле выбора даты
    private final By timeInput = By.xpath("//div[contains(@class,'Dropdown-placeholder')]"); // Поле выбора срока аренды
    private final By submitButton = By.xpath("//button[text()='Далее']"); // Кнопка Далее
    private final By orderButtonOrder = By.xpath("//*[@class='Button_Button__ra12g Button_Middle__1CSJM']"); // Кнопка Заказать в заказе
    private final By confirmPopup = By.xpath("//div[@class='Order_Modal__YZ-d3']"); // Модалка подтверждения заказа
    private final By yesButton = By.xpath("//button[text()='Да']"); // Кнопка Да на модалке
    private final By successPopup = By.xpath("//div[@class='Order_Modal__YZ-d3']"); // Модалка успешного создания заказа
    private final By statusButton = By.xpath("//button[text()='Посмотреть статус']"); // Кнопка Просмотра статуса на модалке успешного создания заказа

    private final WebDriver driver;
    private final MainPage mainPage;

    // Конструктор, который использует WebDriverFactory для создания WebDriver
    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.mainPage = new MainPage(driver);
    }

    // Метод для открытия страницы
    public void openPage() {
        driver.get(PAGE_URL);
    }

    // Метод для закрытия модалки с куками
    public void closeCookiePopup(WebDriver driver) {
        MainPage.closeCookiePopup(driver);
    }

    public void clickOrderButton(String position) {
        if ("top".equals(position)) {
            mainPage.clickOrderButtonTop();// Нажимаем на верхнюю кнопку "Заказать"
        } else if ("bottom".equals(position)) {
            mainPage.clickOrderButtonBottom();// Нажимаем на нижнюю кнопку "Заказать"
        } else {
            throw new IllegalArgumentException("Неверное значение для кнопки: " + position);
        }
    }

    // Заполнение данных в форме заказа
    public void fillFormFields(String name, String surname, String address, String station, String phone) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).sendKeys(name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(surnameInput)).sendKeys(surname);
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressInput)).sendKeys(address);
        WebElement stationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(stationInput));
        stationElement.sendKeys(station);
        stationElement.sendKeys(Keys.DOWN, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput)).sendKeys(phone);
    }

    // Заполнение формы заказа
    public void submitOrderForm() {
        driver.findElement(submitButton).click(); // Нажимаем кнопку "Далее"
    }

    // Заполнение формы даты и времени
    public void fillDateAndTime(String date, String time) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Заполнение даты
        WebElement dateInputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dateInput));
        dateInputElement.click();
        dateInputElement.sendKeys(date);
        dateInputElement.sendKeys(Keys.ENTER);

        //Заполнение времени
        WebElement timeInputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(timeInput));
        timeInputElement.click();
        WebElement timeOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='Dropdown-option'][text()='" + time + "']")));
        timeOption.click();
    }

    public void orderButtonOrderForm() {
        driver.findElement(orderButtonOrder).click(); // Нажимаем кнопку "Заказать"
    }

    // Клик по кнопке "Да" в модалке подтверждения
    public void confirmOrderForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Подтверждаем заказ
        WebElement confirmPopupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPopup));
        /*assert confirmPopupElement.isDisplayed();*/
        Assert.assertTrue("Success popup should be visible", confirmPopupElement.isDisplayed());
        driver.findElement(yesButton).click();

        // Ожидаем успешного подтверждения
        WebElement successPopupElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
        /*assert successPopupElement.isDisplayed();*/
        Assert.assertTrue("Success popup should be visible", successPopupElement.isDisplayed());

        // Клик по кнопке "Посмотреть статус"
        driver.findElement(statusButton).click();
    }
}
