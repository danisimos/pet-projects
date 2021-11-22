package ru.itis.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class User {
    private Integer id;
    private String login;
    private String firstName;
    private String lastName;
    private Integer age;
    private String password;
}
