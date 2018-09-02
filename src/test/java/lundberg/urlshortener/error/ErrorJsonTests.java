package lundberg.urlshortener.error;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RunWith(SpringRunner.class)
@JsonTest
public class ErrorJsonTests {
    @Autowired
    private JacksonTester<Error> json;

    private Error error = Error.builder().build();

    @Test
    public void serializeAllFields() throws Exception {
        Clock clock = Clock.fixed(Instant.parse("2001-01-01T12:00:00Z"), ZoneOffset.UTC);
        Error error = Error.builder()
                .timestamp(LocalDateTime.now(clock))
                .status(NOT_FOUND)
                .message("message")
                .path("path")
                .build();

        assertThat(json.write(error)).isStrictlyEqualToJson("error.json");
    }

    @Test
    public void shouldFormatHttpStatus() throws Exception {
        Error error = Error.builder().status(NOT_FOUND).build();

        assertThat(json.write(error)).hasJsonPathValue("$.status");
        assertThat(json.write(error)).extractingJsonPathValue("$.status").isEqualTo(404);
        assertThat(json.write(error)).hasJsonPathStringValue("$.error");
        assertThat(json.write(error)).extractingJsonPathStringValue("$.error").isEqualTo("Not Found");
    }

    @Test
    public void shouldFormatTimestamp() throws Exception {
        Clock clock = Clock.fixed(Instant.parse("2001-01-01T12:00:00Z"), ZoneOffset.UTC);
        Error error = Error.builder().timestamp(LocalDateTime.now(clock)).build();

        assertThat(json.write(error)).hasJsonPathStringValue("$.timestamp");
        assertThat(json.write(error)).extractingJsonPathStringValue("$.timestamp").isEqualTo("01-01-2001 12:00:00");
    }

    @Test
    public void ignoreEmptyFields() throws Exception {
        assertThat(json.write(error)).doesNotHaveJsonPathValue("$.path");
        assertThat(json.write(error)).doesNotHaveJsonPathValue("$.message");
    }

    @Test
    public void haveDefaultValueForStatus() throws Exception {
        assertThat(json.write(error)).hasJsonPathValue("$.status");
        assertThat(json.write(error)).hasJsonPathStringValue("$.error");
    }

    @Test
    public void haveDefaultValueForTimestamp() throws Exception {
        assertThat(json.write(error)).hasJsonPathStringValue("$.timestamp");
    }
}
