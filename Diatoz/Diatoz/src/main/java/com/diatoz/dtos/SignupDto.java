package com.diatoz.dtos;


import lombok.*;

/**
 * Data transfer object representing the signup information.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SignupDto {


    private String name;
    private String password;
    private String email;

}
