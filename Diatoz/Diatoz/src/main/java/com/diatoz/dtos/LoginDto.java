package com.diatoz.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object representing the login credentials.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginDto {

    private String email;
    private String password;



}
