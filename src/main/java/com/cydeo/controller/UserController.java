package com.cydeo.controller;

import com.cydeo.annotation.DefaultExceptionMessage;
import com.cydeo.annotation.Loggable;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.ResponseWrapper;
import com.cydeo.exception.TicketingProjectException;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name="UserController", description = "User API")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all-users")
    @RolesAllowed("Admin")
    @Operation(summary = "Get user")
    @Loggable
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
    @RolesAllowed("Admin")
    @Operation(summary = "Get user by name")
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
    @RolesAllowed("Admin")
    @Operation(summary = "Create user")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO) {
        userService.save(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ResponseWrapper(
                                "successfully create user",
                                HttpStatus.CREATED
                        )
                );
    }

    @PutMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Update user")
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
    @RolesAllowed("Admin")
    @Operation(summary = "Delete user")
    @DefaultExceptionMessage(defaultMessage = "failed to delete the user")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String username) throws TicketingProjectException {
        userService.delete(username);
        return ResponseEntity
                .ok(
                        new ResponseWrapper(
                                "user successfully deleted",
                                HttpStatus.OK
                        )
                );
    }

}
