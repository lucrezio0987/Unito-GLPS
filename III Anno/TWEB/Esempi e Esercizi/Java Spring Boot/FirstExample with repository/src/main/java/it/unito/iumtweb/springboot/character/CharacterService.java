package it.unito.iumtweb.springboot.character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {
    @Autowired
    private CharacterRepository characterRepository;

    //  Use characterRepository to perform database operations
}
