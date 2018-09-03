package lundberg.urlshortener.url;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
class UrlParams {
    @NotBlank
    @Pattern(regexp = "^(https?://)((([a-z0-9][a-z0-9.-]?)*[a-z0-9]){1,63})+\\.[a-z]{2,63}(:\\d{1,5})?(/.*)?$", message = "must be a valid url")
    private final String url;

    @JsonCreator
    UrlParams(String url) {
        this.url = url;
    }
}
