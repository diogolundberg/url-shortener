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
        Url expected = new Url("url", "generated_id");
        Url result = service.shorten("url");
        given(urlIdGenerator.generateId()).willReturn("generated_id");

        assertThat(result.getShortUrl()).isEqualTo(expected.getShortUrl());
        assertThat(result.getLongUrl()).isEqualTo(expected.getLongUrl());
    }
}
