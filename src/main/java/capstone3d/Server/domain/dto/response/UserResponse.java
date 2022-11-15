package capstone3d.Server.domain.dto.response;

import capstone3d.Server.domain.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;

    private final String email;


    private final String nickname;

    private final String phone;

    private final String business_name;

    private UserResponse(Long id, String email, String nickname, String phone, String business_name) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
        this.business_name = business_name;
    }

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getPhone(),
                user.getBusiness_name());
    }
}