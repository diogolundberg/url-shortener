package lundberg.urlshortener.url;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
class UrlIdGenerator {
    private StringRedisTemplate stringRedisTemplate;

    public UrlIdGenerator(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String generateId() {
        Long id = stringRedisTemplate.opsForValue().increment("ids:url", 1);
        return String.valueOf(id);
    }
}
