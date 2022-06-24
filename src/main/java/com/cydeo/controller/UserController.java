package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    public ResponseEntity<ResponseWrapper> getAllUsers() {
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "successfully got all users",
                                userService.listAllUsers(),
                                HttpStatus.OK
                        )
                );
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseWrapper> getUserByUsername(@PathVariable("username") String username) {
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "successfully got user",
                                userService.findByUserName(username),
                                HttpStatus.OK
                        )
                );
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ResponseWrapper(
                                "successfully got user",
                                HttpStatus.CREATED
                        )
                );
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO) {
        userService.update(userDTO);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "user successfully updated",
                                userDTO,
                                HttpStatus.OK
                        )
                );
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String username) {
        userService.deleteByUserName(username);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "user successfully deleted",
                                HttpStatus.OK
                        )
                );
    }

}
