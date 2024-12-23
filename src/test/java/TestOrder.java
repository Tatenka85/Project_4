import org.openqa.selenium.WebDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import pages.OrderPage;

@RunWith(Parameterized.class)
public class TestOrder {

    private final String name;
    private final String surname;
    private final String address;
    private final String station;
    private final String phone;
    private final String date;
    private final String time;
    private final String buttonPosition;

    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

    // Конструктор для параметризации
    public TestOrder(String name, String surname, String address, String station, String phone, String date, String time, String buttonPosition) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.buttonPosition = buttonPosition;
    }

    @Before
    public void before() {
        driver = WebDriverFactory.createForEnvironment();
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);
        mainPage.openPage();
        MainPage.closeCookiePopup(driver); // Закрытие попапа с куками
    }

    @Parameterized.Parameters
    public static Object[][] userData() {
        return new Object[][]{
                {"Иван", "Иванов", "Москва", "Черкизовская", "89998887766", "01.01.2025", "трое суток", "top"},
                {"Алексей", "Петров", "Санкт-Петербург", "Сокольники", "89995554433", "02.02.2025", "сутки", "bottom"}
        };
    }

    @Test
    public void testOrderButtonClick() {
        // Клик по нужной кнопке (верхняя или нижняя)
        mainPage.clickOrderButton(buttonPosition);

        // Заполнение формы с пользовательскими данными
        orderPage.fillFormFields(name, surname, address, station, phone);
        orderPage.submitOrderForm();
        orderPage.fillDateAndTime(date, time);
        orderPage.orderButtonOrderForm();

        // Подтверждение заказа
        orderPage.confirmOrderForm();
    }

    @After
    public void after() {
        if (driver != null) {
            driver.quit();
        }
    }
}
