package com.barreto.todolist.helpers;

import lombok.Getter;

import java.util.Base64;

@Getter
public class CredentialsHelper {
    private final String username;
    private final String password;

    public CredentialsHelper(String authorization) {
        var encodedAuth = authorization.substring("Basic".length()).trim();
        var decodedAuthBuffer = Base64.getDecoder().decode(encodedAuth);
        var decodedAuth = new String(decodedAuthBuffer);

        var credentials = decodedAuth.split(":");
        this.username = credentials[0];
        this.password = credentials[1];
    }
}
