package github;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    static void beforeUrl() {
        Configuration.browserSize = "1280x1024";

    }
}