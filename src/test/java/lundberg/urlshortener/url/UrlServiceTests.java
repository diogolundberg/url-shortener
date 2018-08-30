package lundberg.urlshortener.url;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlServiceTests {
    @Mock
    private UrlIdGenerator urlIdGenerator;

    @Mock
    private UrlRepository urlRepository;

    private MockHttpServletRequest request;

    private UrlService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        request.setServerName("name");
        request.setScheme("scheme");
        request.setServerPort(123);
        this.service = new UrlService(urlIdGenerator, urlRepository, request);
    }

    private Url url = Url.builder()
            .id("generated_id")
            .longUrl("url")
            .localUrl("scheme://name:123")
            .build();

    @Test
    public void shortenPersistUrlWithdGeneratedId() {
        given(urlIdGenerator.generateId()).willReturn("generated_id");
        service.shorten("url");

        verify(urlRepository, times(1)).save(url);
    }

    @Test
    public void shortenHideShortUrlPortWhenDefault() {
        given(urlIdGenerator.generateId()).willReturn("generated_id");
        request.setServerPort(80);
        url.setLocalUrl("scheme://name");
        service.shorten("url");

        verify(urlRepository, times(1)).save(url);
    }

    @Test
    public void shortenHideShortUrlPortWhenSSLDefault() {
        given(urlIdGenerator.generateId()).willReturn("generated_id");
        request.setServerPort(443);
        url.setLocalUrl("scheme://name");
        service.shorten("url");

        verify(urlRepository, times(1)).save(url);
    }

    @Test
    public void shortenReturnPersistedUrl() {
        given(urlRepository.save(any())).willReturn(url);

        assertThat(service.shorten("url")).isEqualTo(url);
    }

    @Test
    public void enlargeReturnUrlWithProtocol() {
        url.setLongUrl("no_protocol");
        when(urlRepository.findById("id")).thenReturn(Optional.of(url));

        assertThat(service.enlarge("id")).isEqualTo("https://no_protocol");
    }

    @Test
    public void enlargeReturnUrlWhenHasAlreadyHttpProtocol() {
        url.setLongUrl("http://only_http");
        when(urlRepository.findById("id")).thenReturn(Optional.of(url));

        assertThat(service.enlarge("id")).isEqualTo("http://only_http");
    }
}
