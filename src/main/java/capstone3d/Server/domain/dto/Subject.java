package capstone3d.Server.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Subject {

    private Long id;
    private String email;
    private String nickname;
    private String type;

    private Subject(Long id, String email, String nickname, String type) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.type = type;
    }

    public static Subject atk(Long id, String email, String nickname) {
        return new Subject(id, email, nickname, "ATK");
    }
    public static Subject rtk(Long id, String email, String nickname) {
        return new Subject(id, email, nickname, "RTK");
    }
}