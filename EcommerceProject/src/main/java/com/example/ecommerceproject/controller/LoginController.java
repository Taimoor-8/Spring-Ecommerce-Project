package com.example.ecommerceproject.controller;

import com.example.ecommerceproject.global.GlobalData;
import com.example.ecommerceproject.model.Role;
import com.example.ecommerceproject.model.User;
import com.example.ecommerceproject.repository.RoleRepository;
import com.example.ecommerceproject.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String login(){
        GlobalData.cart.clear();
            return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("user") User user ,
                            HttpServletRequest request
                            ) throws ServletException{
        String email = user.getEmail();
        if (email.equals("admin@gmail.com")){
            return "redirect:/admin";
        }
        else{
            return "redirect:/";
        }
    }


    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    @GetMapping("/logout")
    public String getLogout(){
        return "login";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute("user")User user ,
                               HttpServletRequest request
    ) throws ServletException {
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(2).get());
        user.setRole(roles);
        userRepository.save(user);
        request.login(user.getEmail() , user.getPassword());

        return "redirect:/";
    }

}
