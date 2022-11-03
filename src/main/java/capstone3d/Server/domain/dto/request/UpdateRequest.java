package capstone3d.Server.domain.dto.request;

import lombok.Getter;

@Getter
public class UpdateRequest {
    private String password;

    private String nickname;

    private String business_name;
}
