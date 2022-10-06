package capstone3d.Server.domain.dto;

import lombok.Getter;

@Getter
public class Subject {

    private final Long id;
    private final String identification;
    private final String nickname;
    private final String type;

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