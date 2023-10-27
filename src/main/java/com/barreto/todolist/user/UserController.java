package com.barreto.todolist.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody UserCreateInDTO userCreateInDTO, UriComponentsBuilder uriComponentsBuilder) throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(userCreateInDTO));

        var userModel = new UserModel(userCreateInDTO);
        var isExistingUser = userRepository.findByUsername(userModel.getUsername()) != null;

        if (isExistingUser) return ResponseEntity.badRequest().body("This username already exists");

        userModel.encryptPassword();
        var userCreated = userRepository.save(userModel);

        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(userCreated.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable UUID id) {
        var user = this.userRepository.findById(id);

        if (user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

        var userCreateOutDTO = new UserCreateOutDTO(user.get());

        return ResponseEntity.ok(userCreateOutDTO);
    }
}
