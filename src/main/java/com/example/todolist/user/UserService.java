package com.example.todolist.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    final UserRepository userRepository;

    public User createUser(User newUser) {
        User user = userRepository.findByUsername(newUser.getUsername());

        if (user != null) {

        }
    }
}
