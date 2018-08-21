package lundberg.urlshortener.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping(value = "/shorten", produces = APPLICATION_JSON_UTF8_VALUE)
    public Url shorten(@RequestBody UrlParams params) {
        return urlService.shorten(params.getUrl());
    }
}
