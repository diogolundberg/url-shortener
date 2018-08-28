package lundberg.urlshortener.url;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("url")
class Url {
    @JsonIgnore
    private final String id;
    private final String longUrl;
    private final String shortUrl;
}
