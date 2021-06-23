package com.intive.patronative.studentrecord.controller;

import com.intive.patronative.studentrecord.dto.EditedUserDTO;
import com.intive.patronative.studentrecord.dto.SaveUserDTO;
import com.intive.patronative.studentrecord.dto.SaveUserResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;

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

    @PostMapping
    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creation successful"),
            @ApiResponse(responseCode = "400", description = "Invalid data passed"),
    })
    public ResponseEntity<UserResponse<SaveUserResponse>> saveUser(@RequestBody final SaveUserDTO userSaveRequest) {
        final var response = userService.saveUser(userSaveRequest);
        return ResponseEntity.status(CREATED).body(response);
    }


    @PostMapping("/{login}/image")
    @Operation(summary = "Upload user profile image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid login or image")
    })
    public ResponseEntity<Void> uploadImage(@PathVariable(name = "login") final String login, @RequestParam(required = false) final MultipartFile image) {
        userService.uploadImage(login, image);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{login}/deactivate")
    @Operation(summary = "Deactivate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deactivation successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid login")
    })
    public ResponseEntity<Void> deactivateUser(@PathVariable(name = "login") final String login) {
        userService.deactivateUserByLogin(login);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{login}/image")
    @Operation(summary = "Update user profile image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid login or image")
    })
    public ResponseEntity<Void> updateImage(@PathVariable(name = "login") final String login, @RequestParam(required = false) final MultipartFile image) {
        userService.uploadImage(login, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{login}/image")
    @Operation(summary = "Delete user's image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image removed"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid login")
    })
    public ResponseEntity<Void> deleteImage(@PathVariable(name = "login") final String login) {
        userService.deleteImage(login);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{login}")
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update successful"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "422", description = "Invalid data passed")
    })
    public ResponseEntity<Void> update(@PathVariable(name = "login") final String login, @RequestBody final EditedUserDTO editedUserDTO) {
        userService.updateUser(editedUserDTO, login);
        return ResponseEntity.ok().build();
    }

}