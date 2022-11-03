package com.sso.arquitectura.controller;

import com.sso.arquitectura.dto.UserDTO;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/api")
public class UserController {

    static RestTemplate restTemplate = new RestTemplate();

    private String token;

    @PostMapping("/login")
    public void login(@RequestParam(value = "user") String user, @RequestParam(value = "password") String password) {
        final String URL = "http://localhost:3000/login";
        Model model = null;

        ResponseEntity<UserDTO> user2 = restTemplate.postForEntity(URL, new UserDTO(user, password, 3), UserDTO.class);
        HttpStatus statusCode = user2.getStatusCode();
        if (statusCode.compareTo(HttpStatus.valueOf(200)) != 0) {
            model.addAttribute("message", restTemplate.postForObject(URL, new UserDTO(user, password, 3), UserDTO.class).toString());
            error(model);
        }
        token = user2.toString();
        welcome();

    }

    @PostMapping("/signoffall")
    public static void signoffall(@RequestParam(value = "user") String user, @RequestParam(value = "password") String password) {
        final String URL = "http://localhost:3000/signoffall";
        Model model=null;
        ResponseEntity<UserDTO> user2 = restTemplate.postForEntity(URL, new UserDTO(user, password), UserDTO.class);
        HttpStatus statusCode = user2.getStatusCode();
        if (statusCode.compareTo(HttpStatus.valueOf(200)) != 0) {
            model.addAttribute("message", restTemplate.postForObject(URL, new UserDTO(user, password, 3), UserDTO.class).toString());
            error(model);
        }
        loginView();

    }

    @PostMapping("/signoff")
    public void signoff() {
        final String URL = "http://localhost:3000/signoff";


        ResponseEntity<String> user2 = restTemplate.postForEntity(URL, token, String.class);
        Model model = null;
        if (user2.getStatusCode().equals(500)) {
            error(model);
        }
        loginView();
    }


    @PostMapping("/register")
    public static void register(@RequestParam("user") String user, @RequestParam("password") String password, @RequestParam("name") String name) {
        final String URL = "http://localhost:3000/register";
        UserDTO userDTO = new UserDTO();
        userDTO.setUser(user);
        userDTO.setPassword(password);
        userDTO.setName(name);

        ResponseEntity<UserDTO> user2 = restTemplate.postForEntity(URL, userDTO, UserDTO.class);
        System.out.println(user2.getBody());

        if (user2.getStatusCode().equals(500)) {

            welcome();
        }
        registration();
    }

    @GetMapping("/")
    public static String inicio() {
        return "index";
    }

    @GetMapping("/welcome")
    public static String welcome() {
        return "welcome";
    }

    @GetMapping("/loginview")
    public static String loginView() {
        return "loginview";
    }

    @GetMapping("/registration")
    public static String registration() {
        return "registration";
    }

    @GetMapping("/signoffallview")
    public static String signoffallview(Model model) {
        return "signoffallview";
    }

    @GetMapping("/error")
    public static String error(Model model) {
        return "error";
    }

}




