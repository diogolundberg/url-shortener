package lundberg.urlshortener.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Data
@Builder
@JsonInclude(NON_NULL)
class Error {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private final HttpStatus status;
    private final String message;
    private final String path;

    static class ErrorBuilder {
        private LocalDateTime timestamp = LocalDateTime.now();
        private HttpStatus status = INTERNAL_SERVER_ERROR;
    }

    public String getError(){
        return status.getReasonPhrase();
    }

    public Integer getStatus(){
        return status.value();
    }
}
