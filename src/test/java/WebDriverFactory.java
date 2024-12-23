import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {
    // Константы для работы с браузером
    private static final String BROWSER_NAME_ENV_VARIABLE = "BROWSER_NAME"; // Переменная окружения для выбора браузера
    private static final String BROWSER_NAME_SYS_PROPERTY = "browser.name"; // Системное свойство для выбора браузера
    private static final String DEFAULT_BROWSER_NAME = "CHROME"; // Браузер по умолчанию

    // Метод для создания WebDriver в зависимости от имени браузера
    public static WebDriver createForName(String browserName) {
        switch (browserName.toUpperCase()) {
            case "FIREFOX":
                return new FirefoxDriver();
            case "CHROME":
                return new ChromeDriver();
            default:
                throw new RuntimeException("Нераспознанный браузер: " + browserName);
        }
    }

    // Метод для получения WebDriver с учетом переменной окружения или системного свойства
    public static WebDriver createForEnvironment() {
        // Получаем имя браузера из переменной окружения или из системного свойства
        String browserName = System.getenv(BROWSER_NAME_ENV_VARIABLE); // Переменная окружения
        if (browserName == null) {
            browserName = System.getProperty(BROWSER_NAME_SYS_PROPERTY); // Системное свойство
        }

        // Если не указано ни переменное окружение, ни системное свойство, используем браузер по умолчанию
        return createForName(browserName != null ? browserName : DEFAULT_BROWSER_NAME);
    }
}
