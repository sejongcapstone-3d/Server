package capstone3d.Server.api;

import capstone3d.Server.domain.dto.UserDetails;
import capstone3d.Server.domain.dto.request.LoginRequest;
import capstone3d.Server.domain.dto.request.SignUpRequest;
import capstone3d.Server.domain.dto.request.UpdateRequest;
import capstone3d.Server.domain.dto.response.TokenResponse;
import capstone3d.Server.domain.dto.response.UpdateResponse;
import capstone3d.Server.domain.dto.response.UserResponse;
import capstone3d.Server.jwt.JwtTokenProvider;
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
        return jwtTokenProvider.createTokensByLogin(userResponse);
    }

    @GetMapping("/reissue")
    public TokenResponse reissue(
            @AuthenticationPrincipal UserDetails userDetails
    ) throws JsonProcessingException {
        UserResponse userResponse = UserResponse.of(userDetails.getUser());
        return jwtTokenProvider.reissueAtk(userResponse);
    }

    @PutMapping("/user")
    public UpdateResponse update(
            @RequestBody UpdateRequest updateRequest
            ) {
        return userService.update(updateRequest);
    }

    @PostMapping("/user")
    public String withdraw(
            @RequestBody Map<String, String> passwordMap
    ) {
        userService.withdraw(passwordMap.get("password"));
        return "redirect:/";
    }
}