package lundberg.urlshortener.url;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
class UrlIdGenerator {
    static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_1234567890".toCharArray();
    private int base = ALPHABET.length;
    private StringRedisTemplate stringRedisTemplate;

    UrlIdGenerator(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    String generateId() {
        long id = stringRedisTemplate.opsForValue().increment("ids:url", 1);
        return encode(id, "");
    }

    private String encode(final long id, final String code){
        if (id <= 0) return code;
        return encode(id/base, ALPHABET[(int)id % base] + code);
    }
}
