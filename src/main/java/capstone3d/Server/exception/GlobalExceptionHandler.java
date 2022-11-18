package capstone3d.Server.exception;

import capstone3d.Server.response.AllResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<AllResponse> handle(BadRequestException e) {
        return AllResponse.ErrorResponseEntity(e.getStatusMessage());
    }
}