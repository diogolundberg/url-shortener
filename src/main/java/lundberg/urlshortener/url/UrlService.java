package lundberg.urlshortener.url;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

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
        Optional<Url> url = urlRepository.findById(id);
        if (url.isPresent()) {
            String longUrl = url.get().getLongUrl();
            return longUrl.matches("^(https?)://.*$") ? longUrl : String.format("https://%s", longUrl);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found");
    }

    private String local() {
        int serverPort = request.getServerPort();
        String scheme = request.getScheme();
        String name = request.getServerName();
        String port = Arrays.asList(80, 443).contains(serverPort) ? "" : String.format(":%s", serverPort);

        return String.format("%s://%s%s", scheme, name, port);
    }
}
