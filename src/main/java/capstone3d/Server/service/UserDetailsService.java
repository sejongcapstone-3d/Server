package capstone3d.Server.service;

import capstone3d.Server.domain.User;
import capstone3d.Server.domain.dto.UserDetails;
import capstone3d.Server.exception.BadRequestException;
import capstone3d.Server.repository.UserRepository;
import capstone3d.Server.response.StatusMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadRequestException(StatusMessage.Not_Found_User));
        return new UserDetails(user);
    }
}