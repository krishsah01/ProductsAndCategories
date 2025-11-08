package com.elkabani.firstspringboot.controllers;

import com.elkabani.firstspringboot.dtos.RegisterUserRequest;
import com.elkabani.firstspringboot.dtos.UserDto;
import com.elkabani.firstspringboot.mappers.UserMapper;
import com.elkabani.firstspringboot.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/ui/users")
public class UsersUIController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public String listUsers(Model model, @RequestParam(defaultValue = "name") String sort){
        var users = userRepository.findAll(Sort.by(sort))
                .stream()
                .map(userMapper::toDto)
                .toList();
        model.addAttribute("users", users);
        return "users/list";
    }
    @GetMapping("/new")
    public String showCreateUserForm(Model model){
        model.addAttribute("user", new RegisterUserRequest());
        model.addAttribute("isEdit", false);
        return "users/form";
    }
    @PostMapping
    public String createUser(@ModelAttribute RegisterUserRequest registerUserRequest, RedirectAttributes redirectAttributes){
        var user = userMapper.toEntity(registerUserRequest);
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
        return "redirect:/ui/users";
    }
    @GetMapping("/{id}")
    public String viewUser(@PathVariable long id, Model model, RedirectAttributes redirectAttributes){
        if(!userRepository.existsById(id)){
            redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
            return "redirect:/ui/users";
        }
        var user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", userMapper.toDto(user));
        return "users/view";
    }
//    @GetMapping("/{id}/edit")
//    public String showEditUserForm(@PathVariable Long id, Model model) {
//        var user = userRepository.findById(id).orElseThrow();
//        model.addAttribute("user", userMapper.toDto(user));
//        model.addAttribute("isEdit", true);
//        return "users/form";
//    }
}


