package com.scalefocus.java.simeonyachev.controllers;

import com.scalefocus.java.simeonyachev.security.requests.AuthenticationRequest;
import com.scalefocus.java.simeonyachev.security.responses.AuthenticationResponse;
import com.scalefocus.java.simeonyachev.services.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    @ApiOperation(value = "Issues a JWT that's used for authorization purposes",
            notes = "Provide valid username and password")
    public ResponseEntity<AuthenticationResponse> login(@ApiParam("username and password in the form of JSON object")
                                                            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }
}
