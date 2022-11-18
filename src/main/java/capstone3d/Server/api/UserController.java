package capstone3d.Server.api;

import capstone3d.Server.domain.dto.UserDetails;
import capstone3d.Server.domain.dto.request.LoginRequest;
import capstone3d.Server.domain.dto.request.SignUpRequest;
import capstone3d.Server.domain.dto.request.UpdateRequest;
import capstone3d.Server.domain.dto.response.UserResponse;
import capstone3d.Server.jwt.JwtTokenProvider;
import capstone3d.Server.response.AllResponse;
import capstone3d.Server.response.StatusMessage;
import capstone3d.Server.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/sign-up")
    public AllResponse singUp(
            @RequestBody SignUpRequest signUpRequest
    ) {
        return new AllResponse(StatusMessage.Sign_Up_Success.getStatus(),StatusMessage.Sign_Up_Success.getMessage(), 1, userService.singUp(signUpRequest));
    }

    @PostMapping("/login")
    public AllResponse login(
            @RequestBody LoginRequest loginRequest
    ) throws JsonProcessingException {
        UserResponse userResponse = userService.login(loginRequest);
        return new AllResponse(StatusMessage.Login_Success.getStatus(), StatusMessage.Login_Success.getMessage(), 0, jwtTokenProvider.createTokensByLogin(userResponse));
    }

    @GetMapping("/reissue")
    public AllResponse reissue(
            @AuthenticationPrincipal UserDetails userDetails
    ) throws JsonProcessingException {
        UserResponse userResponse = UserResponse.of(userDetails.getUser());
        return new AllResponse(StatusMessage.Reissue_Token_Success.getStatus(), StatusMessage.Reissue_Token_Success.getMessage(), 0, jwtTokenProvider.reissueAtk(userResponse));
    }

    @PutMapping("/user")
    public AllResponse update(
            @RequestBody UpdateRequest updateRequest
            ) {
        return new AllResponse(StatusMessage.Update_Success.getStatus(), StatusMessage.Update_Success.getMessage(), 0, userService.update(updateRequest));
    }

    @PostMapping("/user")
    public AllResponse withdraw(
            @RequestBody Map<String, String> passwordMap
    ) {
        userService.withdraw(passwordMap.get("password"));
        return new AllResponse(StatusMessage.Withdraw_Success.getStatus(), StatusMessage.Withdraw_Success.getMessage(), 0, null);
    }
}