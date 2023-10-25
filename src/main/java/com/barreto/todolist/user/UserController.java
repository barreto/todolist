package com.barreto.todolist.user;

import com.barreto.todolist.utils.BCryptUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody UserModel userModel) throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(userModel));

        var isExistingUser = userRepository.findByUsername(userModel.getUsername()) != null;

        if (isExistingUser) return ResponseEntity.badRequest().body("This username already exists");

        var passwordHash = BCryptUtils.encrypt(userModel.getPassword());
        userModel.setPassword(passwordHash);

        var userCreated = userRepository.save(userModel);
        var userCreateOutDTO = new UserCreateOutDTO(userCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreateOutDTO);
    }
}
