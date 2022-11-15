package capstone3d.Server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String nickname;

    private String phone;

    private String business_name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Room> room_list = new ArrayList<>();


    /*회원수정 메소드*/

    public void updateNickName(String nickName) {
        this.nickname = nickName;
    }

    public void updateBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public void updatePassword(String password) {
        this.password = password;
    }


}