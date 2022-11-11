package com.sso.arquitectura.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TokenDTO {
private String token;

    public TokenDTO(String token) {
        this.token = token;
    }
}
