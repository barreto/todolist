package com.barreto.todolist.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping
    public void create(@RequestBody UserModel userModel) throws JsonProcessingException {
        System.out.println("Name: " + userModel.getName());
        System.out.println("Username: " + userModel.getUsername());
        System.out.println(new ObjectMapper().writeValueAsString(userModel));
    }

}
