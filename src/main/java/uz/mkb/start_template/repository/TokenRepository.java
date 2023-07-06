package uz.mkb.start_template.repository;

import java.util.Optional;

import uz.mkb.start_template.entity.Token;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByGeneratedToken(String generatedToken);

    Optional<Token> findByUserId(Long user_id);
}