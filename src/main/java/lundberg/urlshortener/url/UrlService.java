package lundberg.urlshortener.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UrlService {

    @Autowired
    private UrlIdGenerator urlIdGenerator;

    public Url shorten(String longUrl) {
        String shortUrl = urlIdGenerator.generateId();
        Url url = Url.builder()
                .longUrl(longUrl)
                .shortUrl(shortUrl)
                .build();
        return url;
    }
}
