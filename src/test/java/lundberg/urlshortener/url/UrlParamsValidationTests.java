package lundberg.urlshortener.url;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlParamsValidationTests {
    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void blankUrlShouldReturnMessage() {
        UrlParams url = new UrlParams("");
        assertThat(validationMessages(url)).contains("must not be blank");
    }

    @Test
    public void invalidPatternShouldReturnMessage() {
        UrlParams url = new UrlParams("invalid.url");
        assertThat(validationMessages(url)).contains("must be a valid url");
    }

    @Test
    public void validateWrongPatterns() {
        urlsFrom("invalid_urls.txt").forEach(url ->
                assertThat(validate(url)).as("%s validation", url).isFalse());
    }

    @Test
    public void validateRightPatterns() {
        urlsFrom("valid_urls.txt").forEach(url ->
                assertThat(validate(url)).as("%s validation", url).isTrue());
    }

    private List<String> validationMessages(UrlParams url){
        Set<ConstraintViolation<UrlParams>> violations = validator.validate(url);
        return violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }

    private Stream<String> urlsFrom(String resource) {
        InputStream inputStream = getClass().getResourceAsStream(resource);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines();
    }

    private boolean validate(String url){
        return validator.validate(new UrlParams(url)).isEmpty();
    }
}
