package capstone3d.Server.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Subject {

    private Long id;
    private String identification;
    private String nickname;
    private String type;

    private Subject(Long id, String identification, String nickname, String type) {
        this.id = id;
        this.identification = identification;
        this.nickname = nickname;
        this.type = type;
    }

    public static Subject atk(Long id, String identification, String nickname) {
        return new Subject(id, identification, nickname, "ATK");
    }
    public static Subject rtk(Long id, String identification, String nickname) {
        return new Subject(id, identification, nickname, "RTK");
    }
}