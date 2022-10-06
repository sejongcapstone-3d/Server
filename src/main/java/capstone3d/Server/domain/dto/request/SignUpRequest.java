package capstone3d.Server.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    private String identification;

    private String password;

    private String name;

    private String nickname;

    private String phone;

    private String business_name;
}