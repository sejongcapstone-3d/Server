package capstone3d.Server.repository;

import capstone3d.Server.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByIdentification(String identification);
    boolean existsByNickname(String nickname);

    Optional<User> findByIdentification(String identification);
}