package lundberg.urlshortener.url;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("url")
class Url {
    @JsonIgnore
    private String id;

    private String longUrl;

    @JsonIgnore
    @Transient
    private String localUrl;

    @JsonProperty
    public String getShortUrl(){
        return String.join("/", localUrl, id);
    }
}
