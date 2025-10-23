package com.elkabani.firstspringboot.controllers;

import com.elkabani.firstspringboot.dtos.ChangePasswordRequest;
import com.elkabani.firstspringboot.dtos.RegisterUserRequest;
import com.elkabani.firstspringboot.dtos.UpdateUserRequest;
import com.elkabani.firstspringboot.dtos.UserDto;
import com.elkabani.firstspringboot.entities.User;
import com.elkabani.firstspringboot.mappers.UserMapper;
import com.elkabani.firstspringboot.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto> getAllUsers
            (@RequestHeader(value = "x-auth-token", required = false) String authToken,
             @RequestParam(required = false, defaultValue = "name") String sort) {
        System.out.println("Auth Token: " + authToken);
        return userRepository.findAll(Sort.by(sort)).stream().
                map(user -> userMapper.toDto(user)).toList();
        // Logic to retrieve all users from the database
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping
    public UserDto createUser(@RequestBody RegisterUserRequest request) {
        var user = userMapper.toEntity(request);
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        return userDto;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id,
                                              @RequestBody UpdateUserRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.update(request, user);
        userRepository.save(user);
        var userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id,
                                               @RequestBody ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        if(!user.getPassword().equals(request.getOldPassword())) {
            return new  ResponseEntity <>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(request.getNewPassword());// Fo
        userRepository.save(user);
        return ResponseEntity.noContent().build();
        // rbidden
    }
}