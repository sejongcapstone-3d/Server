package capstone3d.Server.domain.dto;

import capstone3d.Server.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
public class UserDetails extends org.springframework.security.core.userdetails.User {
    private final User user;

    public UserDetails(User user) {
        super(user.getEmail(), user.getPassword(), List.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                if (user.getEmail().equals("admin@admin.com")) {
                    return "ROLE_ADMIN";
                } else return "ROLE_USER";
            }
        }));
        this.user = user;
    }
}