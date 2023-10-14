package com.example.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    final UserRepository userRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody User newUser) {
        User user = userRepository.findByUsername(newUser.getUsername());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user already exists");
        }

        String hashedPassword = BCrypt.withDefaults().hashToString(12, newUser.getPassword().toCharArray());
        newUser.setPassword(hashedPassword);

        User userCreated = userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
