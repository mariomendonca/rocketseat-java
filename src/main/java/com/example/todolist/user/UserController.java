package com.example.todolist.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<User> create(@RequestBody User user) {
        return userRepository.save(user);
    }
}
