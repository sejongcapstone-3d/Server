package capstone3d.Server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String identification;

    @JsonIgnore
    private String password;

    private String name;

    @Column(unique = true)
    private String nickname;

    private String phone;

    private String business_name;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Room> room_list = new ArrayList<>();

    public User(String identification, String password, String name,
                String nickname, String phone, String business_name) {
        this.identification = identification;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.business_name = business_name;
    }
}