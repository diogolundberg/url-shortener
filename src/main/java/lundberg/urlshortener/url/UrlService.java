package lundberg.urlshortener.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class UrlService {

    @Autowired
    private UrlIdGenerator urlIdGenerator;

    @Autowired
    private UrlRepository urlRepository;

    public Url shorten(String longUrl) {
        String id = urlIdGenerator.generateId();
        Url url = Url.builder()
                .id(id)
                .longUrl(longUrl)
                .shortUrl(id)
                .build();
        return urlRepository.save(url);
    }
}
