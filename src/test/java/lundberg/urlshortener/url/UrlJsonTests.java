package lundberg.urlshortener.url;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@JsonTest
public class UrlJsonTests {

    @Autowired
    private JacksonTester<Url> json;

    @Test
    public void serializeJson() throws Exception {
        Url url = Url.builder()
                .longUrl("www.longurl.com")
                .shortUrl("short")
                .build();
        assertThat(json.write(url)).isEqualTo("url.json");
        assertThat(json.write(url)).isEqualToJson("url.json");
        assertThat(json.write(url)).hasJsonPathStringValue("$.longUrl");
        assertThat(json.write(url)).extractingJsonPathStringValue("$.longUrl").isEqualTo("www.longurl.com");
        assertThat(json.write(url)).hasJsonPathStringValue("$.shortUrl");
        assertThat(json.write(url)).extractingJsonPathStringValue("$.shortUrl").isEqualTo("short");
    }
}