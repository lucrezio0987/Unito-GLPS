package it.unito.iumtweb.springboot.character;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    // You can add custom query methods if needed
}
