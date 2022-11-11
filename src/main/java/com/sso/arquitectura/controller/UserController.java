package com.sso.arquitectura.controller;

import com.google.gson.Gson;
import com.sso.arquitectura.dto.TokenDTO;
import com.sso.arquitectura.dto.UserDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Properties;

@Controller
@RequestMapping("/api")
public class UserController {

    static RestTemplate restTemplate = new RestTemplate();

    private String token;

    @PostMapping("/register")
    public String register(@RequestParam("user") String user, @RequestParam("password") String password, @RequestParam("name") String name, RedirectAttributes redirectAttrs) {
        final String URL = "http://localhost:3000/register";
        UserDTO userDTO = new UserDTO();
        userDTO.setUser(user);
        userDTO.setPassword(password);
        userDTO.setName(name);
        Model model = null;
        HttpStatus user2 = restTemplate.postForEntity(URL, userDTO, UserDTO.class).getStatusCode();

        if (user2.equals(HttpStatus.valueOf(200))) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Creado correctamente")
                    .addFlashAttribute("clase", "success");
        }
        return "redirect:/api/registration";
    }
    @PostMapping("/login")
    public String login(@RequestParam(value = "user") String user, @RequestParam(value = "password") String password,RedirectAttributes redirectAttrs) {
        final String URL = "http://localhost:3000/login";

        ResponseEntity<String> json = restTemplate.postForEntity(URL, new UserDTO(user, password, 3), String.class);
        final Gson gson = new Gson();
        final TokenDTO tokenD = gson.fromJson(json.getBody(), TokenDTO.class);
        this.token = tokenD.getToken();
       if (json.getStatusCode()==HttpStatus.valueOf(403)||json.equals(403)) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "Ya existe una sesión activa, debe cerrar sesión en el otro dispositivo")
                    .addFlashAttribute("clase", "danger");
            return "redirect:/api/loginview";
        }

        return "redirect:/api/welcome";

    }

   @PostMapping("/signoffall")
    public String signoffall(@RequestParam(value = "user") String user, @RequestParam(value = "password") String password,RedirectAttributes redirectAttrs) {
        final String URL = "http://localhost:3000/signoffall";
        Model model=null;
       HttpStatus user2 = restTemplate.postForEntity(URL, new UserDTO(user, password), UserDTO.class).getStatusCode();
        if (user2.compareTo(HttpStatus.valueOf(200)) != 0) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "No se pudieron cerrar las sesiones")
                    .addFlashAttribute("clase", "danger");
        }
       return "redirect:/api/loginview";

    }

    @GetMapping("/signoff")
    public String signoff(RedirectAttributes redirectAttrs) {
        final String URL = "http://localhost:3000/signoff";
        HttpStatus user2 = restTemplate.postForEntity(URL, token, String.class).getStatusCode();

        if (user2!=HttpStatus.valueOf(200)) {
            redirectAttrs
                    .addFlashAttribute("mensaje", "No se pudo cerrar la sesion")
                    .addFlashAttribute("clase", "danger");
        }

        return "redirect:/api/loginview";
    }


    @GetMapping("/")
    public static String inicio() {
        return "loginview";
    }

    @GetMapping("/welcome")
    public static String welcome() {
        return "welcome";
    }

    @GetMapping("/registration")
    public static String registration() {
        return "registration";
    }

    @GetMapping("/loginview")
    public static String loginView() {
        return "loginview";
    }

    @GetMapping("/signoffallview")
    public static String signoffallview(Model model) {
        return "signoffview";
    }

    @GetMapping("/error")
    public static String error(Model model) {
        return "error";
    }

}




