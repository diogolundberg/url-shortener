package lundberg.urlshortener.url;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UrlController.class)
public class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UrlService urlService;

    @Test
    public void shorten() throws Exception {
        UrlParams params = new UrlParams("http://www.longurl.com");
        Url response = Url.builder()
                .id("id")
                .longUrl("http://www.longurl.com")
                .localUrl("localhost")
                .build();

        given(this.urlService.shorten("http://www.longurl.com")).willReturn(response);
        mockMvc.perform(post("/shorten").contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.longUrl").value("http://www.longurl.com"))
                .andExpect(jsonPath("$.shortUrl").value("localhost/id"));
    }

    @Test
    public void redirect() throws Exception {
        given(this.urlService.enlarge("1")).willReturn("redirected_url");
        mockMvc.perform(get("/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("redirected_url"));
    }

    @Test
    public void urlNotFound() throws Exception {
        when(this.urlService.enlarge(any())).thenThrow(new ResponseStatusException(NOT_FOUND, "message"));
        mockMvc.perform(get("/1"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.message").value("message"));
    }

    @Test
    public void invalidParams() throws Exception {
        UrlParams params = new UrlParams("invalid_url");

        mockMvc.perform(post("/shorten").contentType(APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }
}
