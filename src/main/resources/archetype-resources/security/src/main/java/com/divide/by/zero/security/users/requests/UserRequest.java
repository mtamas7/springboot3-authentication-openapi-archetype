package com.divide.by.zero.security.users.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String roles;
}
