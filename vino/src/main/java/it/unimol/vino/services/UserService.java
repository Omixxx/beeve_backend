package it.unimol.vino.services;


import it.unimol.vino.models.entity.User;
import it.unimol.vino.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

}
