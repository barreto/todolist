package com.barreto.todolist.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BCryptUtils {
    public static String encrypt(String text) {
        final int DEFAULT_COST = 12;
        var textArray = text.toCharArray();
        return BCrypt.withDefaults().hashToString(DEFAULT_COST, textArray);
    }

    public static Boolean validate(String rawValue, String hash) {
        return BCrypt.verifyer().verify(rawValue.toCharArray(), hash).verified;
    }
}
