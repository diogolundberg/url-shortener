package lundberg.urlshortener.url;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;

@Data
class UrlParams {
    private final String url;

    @JsonCreator
    public UrlParams(String url) {
        this.url = url;
    }
}
