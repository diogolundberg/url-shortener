package lundberg.urlshortener.url;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Service
@AllArgsConstructor
class UrlService {
    private UrlIdGenerator urlIdGenerator;
    private UrlRepository urlRepository;
    private HttpServletRequest request;

    public Url shorten(String longUrl) {
        String id = urlIdGenerator.generateId();
        Url url = Url.builder()
                .id(id)
                .longUrl(longUrl)
                .localUrl(local())
                .build();
        return urlRepository.save(url);
    }

    public String enlarge(String id) {
        String url = urlRepository.findById(id).get().getLongUrl();
        return url.matches("^(https?)://.*$") ? url : String.format("https://%s", url);
    }

    private String local() {
        int serverPort = request.getServerPort();
        String scheme = request.getScheme();
        String name = request.getServerName();
        String port = Arrays.asList(80, 443).contains(serverPort) ? "" : String.format(":%s", serverPort);

        return String.format("%s://%s%s", scheme, name, port);
    }
}
