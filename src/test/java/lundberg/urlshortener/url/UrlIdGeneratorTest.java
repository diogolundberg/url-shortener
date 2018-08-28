package lundberg.urlshortener.url;

import org.junit.After;
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
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = UrlIdGeneratorTest.Initializer.class)
@DataRedisTest
public class UrlIdGeneratorTest {

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

    private UrlIdGenerator urlIdGenerator;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.urlIdGenerator = new UrlIdGenerator(stringRedisTemplate);
    }

    @After
    public void tearDown() {
        RedisConnection connection = stringRedisTemplate.getConnectionFactory().getConnection();
        connection.serverCommands().flushDb();
    }

    @Test
    public void shouldCreateIdsUrlKey() {
        urlIdGenerator.generateId();
        assertThat(stringRedisTemplate.hasKey("ids:url")).isTrue();
    }

    @Test
    public void shouldIncreaseByOne() {
        Stream.of("1", "2", "3", "4").forEach(id -> {
            urlIdGenerator.generateId();
            assertThat(stringRedisTemplate.opsForValue().get("ids:url")).isEqualTo(id);
        });
    }

    @Test
    public void shouldTranslateIds() {
        IntStream.range(1, UrlIdGenerator.ALPHABET.length).forEach(index ->{
            String expected = Character.toString(UrlIdGenerator.ALPHABET[index]);
            assertThat(urlIdGenerator.generateId()).isEqualTo(expected);
        });
    }

    @Test
    public void shouldIncreaseOneAndTranslateIdToABase64Dictionary() {
        char zero = UrlIdGenerator.ALPHABET[0];
        char one = UrlIdGenerator.ALPHABET[1];
        String expected = String.format("%s%s%s%s%s", one, zero, zero, zero, zero);
        stringRedisTemplate.opsForValue().increment("ids:url", Math.pow(64, 4) - 1);

        assertThat(UrlIdGenerator.ALPHABET.length).isEqualTo(64);
        assertThat(urlIdGenerator.generateId()).isEqualTo(expected);
    }
}


