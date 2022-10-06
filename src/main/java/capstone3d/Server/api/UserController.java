package capstone3d.Server.api;

import capstone3d.Server.domain.Dto.LoginRequest;
import capstone3d.Server.domain.Dto.SignUpRequest;
import capstone3d.Server.domain.Dto.TokenResponse;
import capstone3d.Server.domain.Dto.UserResponse;
import capstone3d.Server.jwt.TokenProvider;
import capstone3d.Server.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/sign-up")
    public UserResponse singUp(
            @RequestBody SignUpRequest signUpRequest
    ) {
        return userService.singUp(signUpRequest);
    }

    @PostMapping("/login")
    public TokenResponse login(
            @RequestBody LoginRequest loginRequest
    ) throws JsonProcessingException {
        UserResponse userResponse = userService.login(loginRequest);
        return tokenProvider.createTokensByLogin(userResponse);
    }
}