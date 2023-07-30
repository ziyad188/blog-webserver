package net.blog.springbootrestapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.blog.springbootrestapi.payload.JwtAuthResponse;
import net.blog.springbootrestapi.payload.LoginDto;
import net.blog.springbootrestapi.payload.SignUpDto;
import net.blog.springbootrestapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {
    public AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    //build login rest api
    @Operation(
            summary = "Authenticate Login REST API",
            description = "Authenticate Login REST API used to authenticate credintials"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 success"

    )
    @PostMapping(value = {"/login","/signin"})

    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
       String token = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

       return ResponseEntity.ok(jwtAuthResponse);
    }
    @Operation(
            summary = "Authenticate Register REST API",
            description = "Authenticate Register REST API used to create a user"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 success"

    )
    @PostMapping(value = {"/register","signup"})
    public ResponseEntity<String> register(@RequestBody SignUpDto signUpDto){
        String response = authService.register(signUpDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
}
