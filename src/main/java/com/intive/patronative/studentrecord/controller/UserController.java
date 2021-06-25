package com.intive.patronative.studentrecord.controller;

import com.intive.patronative.studentrecord.dto.UserDTO;
import com.intive.patronative.studentrecord.dto.UserDetailedDTO;
import com.intive.patronative.studentrecord.dto.UserResponse;
import com.intive.patronative.studentrecord.dto.UsersResponse;
import com.intive.patronative.studentrecord.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Fetch users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch successful")
    })
    public ResponseEntity<UsersResponse<Set<UserDTO>>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("{login}")
    @Operation(summary = "Fetch user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetch successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid login")
    })
    public ResponseEntity<UserResponse<UserDetailedDTO>> getUser(@PathVariable("login") final String login) {
        return ResponseEntity.ok(userService.getUser(login));
    }

}