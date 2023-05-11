package electronic.commerce.service;

import electronic.commerce.dto.entity.Role;
import electronic.commerce.dto.entity.User;
import electronic.commerce.dto.request.AuthenticationRequest;
import electronic.commerce.dto.request.RegisterRequest;
import electronic.commerce.dto.request.ReqRole;
import electronic.commerce.dto.response.AuthenticationResponse;
import electronic.commerce.dto.response.RespStatus;
import electronic.commerce.exception.ExceptionConstants;
import electronic.commerce.exception.MyException;
import electronic.commerce.repository.RoleRepository;
import electronic.commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public RespStatus addRole(ReqRole reqRole) throws MyException {
        if (reqRole == null) {
            throw new MyException(ExceptionConstants.DATA_NULL, "Request null");
        }
        Role role = new Role();
        role.setName(reqRole.getName());
        roleRepository.save(role);

        return RespStatus.getMessage();

    }


    public RespStatus addRoleToUser(String email, String name) {

        User u = userRepository.findUserByEmail(email);
        log.info("name-- : "+name);
        Role r = roleRepository.findRoleByName(name);
        log.info("r-- : "+r);
        u.getRoles().add(r);
        userRepository.save(u);

        return RespStatus.getMessage();
    }
}
