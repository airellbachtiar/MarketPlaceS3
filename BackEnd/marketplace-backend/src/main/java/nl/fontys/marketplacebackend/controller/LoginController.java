package nl.fontys.marketplacebackend.controller;

import lombok.RequiredArgsConstructor;
import nl.fontys.marketplacebackend.dto.logindtos.LoginRequestDTO;
import nl.fontys.marketplacebackend.dto.logindtos.LoginResponseDTO;
import nl.fontys.marketplacebackend.service.loginservices.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request)
    {
        LoginResponseDTO loginResponseDTO = loginService.login(request);
        return ResponseEntity.ok(loginResponseDTO);
    }
}
