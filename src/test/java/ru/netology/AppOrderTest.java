package ru.netology;

import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class AppOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void shouldCheckFormPositiveTest() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+71234567890");
        driver.findElement(By.cssSelector("[class='checkbox__box']")).click();
        driver.findElement(By.tagName("button")).click();
        String resultMessage = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().strip();
        String expectedMessage = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        Assertions.assertEquals(expectedMessage, resultMessage, "Фактическое сообщение не соответствует ожидаемому!");
    }

    @Test
    public void shouldCheckFormWithWrongName() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Ivan");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+71234567890");
        driver.findElement(By.cssSelector("[class='checkbox__box']")).click();
        driver.findElement(By.tagName("button")).click();
        String resultMessage = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        String expectedMessage = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expectedMessage, resultMessage, "Фактическое сообщение не соответствует ожидаемому!");
    }

    @Test
    public void shouldCheckFormWithWrongTelephoneNumber() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+712");
        driver.findElement(By.cssSelector("[class='checkbox__box']")).click();
        driver.findElement(By.tagName("button")).click();
        String resultMessage = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        String expectedMessage = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expectedMessage, resultMessage, "Фактическое сообщение не соответствует ожидаемому!");
    }

    @Test
    public void shouldCheckFormWithoutCheckBox() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+79111171617");
        driver.findElement(By.tagName("button")).click();
        String resultMessage = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText();
        String expectedMessage = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        Assertions.assertEquals(expectedMessage, resultMessage, "Фактическое сообщение не соответствует ожидаемому!");
    }

    @Test
    public void shouldCheckFormWithoutTelephoneNumber() {
        driver.findElement(By.cssSelector("[type='text']")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[class='checkbox__box']")).click();
        driver.findElement(By.tagName("button")).click();
        String resultMessage = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, resultMessage, "Фактическое сообщение не соответствует ожидаемому!");
    }

    @Test
    public void shouldCheckFormWithoutName() {
        driver.findElement(By.cssSelector("[type='tel']")).sendKeys("+7111111111");
        driver.findElement(By.cssSelector("[class='checkbox__box']")).click();
        driver.findElement(By.tagName("button")).click();
        String resultMessage = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        String expectedMessage = "Поле обязательно для заполнения";
        Assertions.assertEquals(expectedMessage, resultMessage, "Фактическое сообщение не соответствует ожидаемому!");
    }


}
