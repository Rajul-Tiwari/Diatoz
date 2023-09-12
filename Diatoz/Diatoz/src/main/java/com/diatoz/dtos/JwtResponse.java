package com.diatoz.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * Data transfer object representing the JWT response.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {

    private String token;

    private String type = "Bearer";

    private String email;

    private List<String> role;


}
