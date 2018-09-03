package lundberg.urlshortener.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
class UrlController {
    @Autowired
    private UrlService urlService;

    @PostMapping(value = "/shorten", produces = APPLICATION_JSON_UTF8_VALUE)
    public Url shorten(@Valid @RequestBody UrlParams params) {
        return urlService.shorten(params.getUrl());
    }

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ModelAndView redirect(@PathVariable String id) {
        return new ModelAndView(String.format("redirect:%s", urlService.enlarge(id)));
    }
}
