package capstone3d.Server.domain.dto.response;

import lombok.Getter;

@Getter
public class UserLoginResponse {
    private final TokenResponse tokenResponse;
    private final UserResponse userResponse;

    public UserLoginResponse(TokenResponse tokenResponse, UserResponse userResponse) {
        this.tokenResponse = tokenResponse;
        this.userResponse = userResponse;
    }
}
