package electronic.commerce.controller;

import electronic.commerce.dto.request.AuthenticationRequest;
import electronic.commerce.dto.request.RegisterRequest;
import electronic.commerce.dto.request.ReqRole;
import electronic.commerce.dto.response.AuthenticationResponse;
import electronic.commerce.dto.response.RespStatus;
import electronic.commerce.exception.MyException;
import electronic.commerce.service.AuthenticationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
       return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/addRole")
    public RespStatus addRole(@RequestBody ReqRole reqRole) throws MyException {

        return service.addRole(reqRole);

    }

    @PostMapping("/addRoleToUser")
    public RespStatus addRoleToUser(@RequestBody FormClass form) throws MyException {

        return service.addRoleToUser(form.username,form.name);

    }

    @Data
    public static class FormClass{
        private String username;
        private String name;
    }



}
