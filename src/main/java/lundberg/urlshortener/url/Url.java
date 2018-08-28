package lundberg.urlshortener.url;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Url {
    private final String longUrl;
    private final String shortUrl;
}
