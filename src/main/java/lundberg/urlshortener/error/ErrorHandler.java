package lundberg.urlshortener.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE;

@ControllerAdvice
class ErrorHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public final ResponseEntity<Object> handleNotFoundException(ResponseStatusException exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = exception.getStatus();
        String message = exception.getReason();
        Error error = buildError(Error.builder().message(message), status, request);
        return handleExceptionInternal(exception, error, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Object error = body == null ? buildError(Error.builder(), status, request) : body;
        return new ResponseEntity<>(error, headers, status);
    }

    private Error buildError(Error.ErrorBuilder error, HttpStatus status, WebRequest request) {
        String path = (String) request.getAttribute(PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE, SCOPE_REQUEST);
        return error.status(status).path(path).build();
    }
}
