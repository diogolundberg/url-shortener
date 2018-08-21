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
public class UrlParamsJsonTests {

    @Autowired
    private JacksonTester<UrlParams> json;

    @Test
    public void deserializeJson() throws Exception {
        String content = "{\"url\":\"www.serialized.com\"}";
        assertThat(json.parse(content)).isEqualTo(new UrlParams("www.serialized.com"));
        assertThat(json.parseObject(content).getUrl()).isEqualTo("www.serialized.com");
    }

}
