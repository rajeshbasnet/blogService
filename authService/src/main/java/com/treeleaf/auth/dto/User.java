package com.treeleaf.auth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
}
