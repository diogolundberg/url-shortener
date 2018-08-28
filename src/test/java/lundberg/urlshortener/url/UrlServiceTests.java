package lundberg.urlshortener.url;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlServiceTests {

    @MockBean
    private UrlIdGenerator urlIdGenerator;

    @Autowired
    private UrlService service;

    @Test
    public void shortenShouldReturnGeneratedId() {
        Url expected = Url.builder()
                .longUrl("url")
                .shortUrl("generated_id")
                .build();
        given(urlIdGenerator.generateId()).willReturn("generated_id");

        assertThat(service.shorten("url")).isEqualTo(expected);
    }
}
