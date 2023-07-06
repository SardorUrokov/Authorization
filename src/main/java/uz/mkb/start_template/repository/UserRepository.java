package uz.mkb.start_template.repository;

import java.util.Optional;
import uz.mkb.start_template.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByEmailAndUsername (String email, String username);
}