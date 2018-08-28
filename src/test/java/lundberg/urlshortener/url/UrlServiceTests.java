package lundberg.urlshortener.url;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlServiceTests {

    @MockBean
    private UrlIdGenerator urlIdGenerator;

    @MockBean
    private UrlRepository urlRepository;

    @Autowired
    private UrlService service;

    private Url url = Url.builder()
            .id("generated_id")
            .longUrl("url")
            .shortUrl("generated_id")
            .build();

    @Test
    public void shortenPersistUrlWithdGeneratedId() {
        given(urlIdGenerator.generateId()).willReturn("generated_id");

        service.shorten("url");
        verify(urlRepository, times(1)).save(url);
    }

    @Test
    public void shortenReturnPersistedUrl() {
        given(urlRepository.save(any())).willReturn(url);

        assertThat(service.shorten("url")).isEqualTo(url);
    }
}
