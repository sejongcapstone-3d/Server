package capstone3d.Server.domain.dto;

import capstone3d.Server.domain.User;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
public class UserDetails extends org.springframework.security.core.userdetails.User {
    private final User user;

    public UserDetails(User user) {
        super(user.getIdentification(), user.getPassword(), List.of(new SimpleGrantedAuthority("USER")));
        this.user = user;
    }
}