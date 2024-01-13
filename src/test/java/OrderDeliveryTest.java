import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class OrderDeliveryTest {

    private String createDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldConfirmCardDelivery() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Екатеринбург");
        String currentDate = createDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").sendKeys(currentDate);
        $("[data-test-id=name] input").setValue("Василий Васильев Васирович");
        $("[data-test-id=phone] input").setValue("+0123456789");
        $("[data-test-id=agreement]").click();
        $(".button__text").click();
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Успешно! Встреча успешно забронирована на " + currentDate));


    }

}
