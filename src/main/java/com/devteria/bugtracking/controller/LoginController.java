package com.devteria.bugtracking.controller;

import com.devteria.bugtracking.entity.User;
import com.devteria.bugtracking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public RedirectView login(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            model.addAttribute("username", username);
            return new RedirectView("/home");
        } else {
            model.addAttribute("error", "Invalid username or password");
            return new RedirectView("/login");
        }
    }


    @RequestMapping("/")
    public String index() {
        return "login";
    }

}
