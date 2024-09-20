package com.igrowker.nativo.controllers;

import com.igrowker.nativo.dtos.user.*;
import com.igrowker.nativo.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/autenticacion")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/registro")
    public ResponseEntity<ResponseUserDto> registerUser(@Valid @RequestBody RequestRegisterDto requestRegisterDto) {
        ResponseUserDto registeredUser = authenticationService.signUp(requestRegisterDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/inicio-sesion")
    public ResponseEntity<ResponseLoginDto> loginUser(@Valid @RequestBody RequestLoginDto requestLoginDto) {
        ResponseLoginDto loginResponse = authenticationService.login(requestLoginDto);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verificacion-codigo")
    public ResponseEntity<?> verifyUser(@RequestBody RequestVerifyUserDto verifyUserDto) {
        try {
            authenticationService.verifyUser(verifyUserDto);
            return ResponseEntity.ok("Cuenta verificada correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/reenvio-codigo")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Código de verificación enviado.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}