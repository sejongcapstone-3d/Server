package capstone3d.Server.exception;

import capstone3d.Server.response.StatusMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BadRequestException extends RuntimeException {
    StatusMessage statusMessage;
}