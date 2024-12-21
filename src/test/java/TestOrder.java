import Pages.TestOrderPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


@RunWith(Parameterized.class)
public class TestOrder {


    private final String name;
    private final String surname;
    private final String address;
    private final String station;
    private final String phone;
    private final String date;
    private final String time;
    private final By orderButtonLocator;


    private WebDriver driver;
    private TestOrderPage testOrderPage;


    // Конструктор для параметризации
    public TestOrder(String name, String surname, String address, String station, String phone, String date, String time, By orderButtonLocator) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.date = date;
        this.time = time;
        this.orderButtonLocator = orderButtonLocator;
    }


    @Before
    public void before() {
        String browserName = System.getenv(TestOrderPage.BROWSER_NAME_ENV_VARIABLE);
        driver = WebDriverFactory.createForName(browserName != null ? browserName : TestOrderPage.DEFAULT_BROWSER_NAME);


        testOrderPage = new TestOrderPage(name, surname, address, station, phone, date, time);
    }


    @Parameterized.Parameters
    public static Object[] UserData() {
        return new Object[][]{
                {"Иван", "Иванов", "Москва", "Черкизовская", "89998887766", "01.01.2025", "трое суток", TestOrderPage.ORDER_BUTTON_TOP},
                {"Алексей", "Петров", "Санкт-Петербург", "Сокольники", "89995554433", "02.02.2025", "сутки", TestOrderPage.ORDER_BUTTON_BOTTOM}
        };
    }


    @Test
    public void testUserData() {
        // Открытие страницы и закрытие модалки с cookies
        testOrderPage.openPage(driver);
        testOrderPage.closeCookiePopup(driver);


        // Проверка доступности кнопки "Заказать" и клик по ней
        driver.findElement(orderButtonLocator).click();


        // Заполнение формы с пользовательскими данными
        testOrderPage.fillOrderForm(driver);


        // Подтверждение заказа
        testOrderPage.confirmOrderForm(driver);
    }


    @After
    public void after() {
        driver.quit();
    }
}
