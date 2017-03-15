package com.domain.androidtemplate.domain.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Class that represents a User.
 */
@Getter
@RequiredArgsConstructor
public class User {

    public final static User EMPTY = new User(-1, "", "", "");

    private final long id;

    private final String email;

    private final String fullName;

    private final String token;
}
