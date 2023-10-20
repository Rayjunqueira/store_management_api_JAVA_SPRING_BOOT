package com.example.softwaresystemapi.controllers;

import com.example.softwaresystemapi.dtos.UserDto;
import com.example.softwaresystemapi.mappers.UserMapper;
import com.example.softwaresystemapi.models.UserModel;
import com.example.softwaresystemapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {
    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    final UserService userService;
    final UserMapper userMapper;

    public UserController(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto) {
        try {
            if (userService.existsByEmail(userDto.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists in database!");
            }

            UserModel userModel = userMapper.toUserModel(userDto);
            userModel.setPassword(passwordEncoder().encode(userModel.getPassword()));

            LocalDate localDate = LocalDate.now();
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            userModel.setCreatedAt(date);

            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot create user. Check if the fields sent in your request are correct.");
        }
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id) {
        try {
            Optional<UserModel> userModelOptional = userService.findById(id);
            if (!userModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The user could not be found. Please check if the sent ID is correct.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Valid UserDto userDto) {
        try {
            Optional<UserModel> userModelOptional = userService.findById(id);
            if (!userModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            UserModel userModel = userModelOptional.get();
            userModel.setName(userDto.getName());
            userModel.setEmail(userDto.getEmail());
            userModel.setPassword(passwordEncoder().encode(userDto.getPassword()));

            return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There was an issue! Check if the credentials are correct.");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
        try {
            Optional<UserModel> userModelOptional = userService.findById(id);
            if (!userModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            userService.delete(userModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The user could not be found. Please check if the sent ID is correct.");
        }
    }
}