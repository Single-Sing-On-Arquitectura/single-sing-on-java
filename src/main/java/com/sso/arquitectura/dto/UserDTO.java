package com.sso.arquitectura.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserDTO {

    private String user;
    private String password;
    private String name;
    private int app;

    public UserDTO() {

    }

    public UserDTO(String user, String password, String name, int app) {
        this.user = user;
        this.password = password;
        this.name = name;
        this.app = app;
    }

    public UserDTO(String user, String password, String name) {
        this.user = user;
        this.password = password;
        this.name = name;
    }
    public UserDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public UserDTO(String user, String password, int app) {
        this.user = user;
        this.password = password;
        this.app = app;
    }

}
