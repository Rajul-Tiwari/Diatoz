package com.diatoz.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserModel {


    @Id
    private String id;

    private String name;
    private String email;
    private String password;
    private boolean isAdmin;

    @DBRef
    private Role role;
}
