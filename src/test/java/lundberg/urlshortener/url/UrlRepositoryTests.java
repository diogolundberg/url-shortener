package lundberg.urlshortener.url;


import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = UrlRepositoryTests.Initializer.class)
@DataRedisTest
public class UrlRepositoryTests {

    private static int REDIS_PORT = 6379;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.redis.host:" + redis.getContainerIpAddress(),
                    "spring.redis.port:" + redis.getMappedPort(REDIS_PORT))
                    .applyTo(context);
        }
    }

    @ClassRule
    public static GenericContainer redis = new GenericContainer("redis:4.0").withExposedPorts(REDIS_PORT);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UrlRepository urlRepository;

    private Url url = Url.builder()
            .id("generated_id")
            .longUrl("www.longurl.com")
            .build();

    @Test
    public void saveShouldPersistAllAttributes() {
        String key = String.format("url:%s", url.getId());
        urlRepository.save(url);

        assertThat(stringRedisTemplate.opsForHash().keys(key)).containsOnly("_class", "id", "longUrl");
        assertThat(stringRedisTemplate.opsForHash().get(key, "id")).isEqualTo(url.getId());
        assertThat(stringRedisTemplate.opsForHash().get(key, "longUrl")).isEqualTo(url.getLongUrl());
    }

    @Test
    public void saveShouldReturnUrlObject() {
        assertThat(urlRepository.save(url)).isEqualTo(url);
    }

    @Test
    public void findByIdShouldReturnUrlObject() {
        urlRepository.save(url);
        assertThat(urlRepository.findById("generated_id")).contains(url);
    }
}
