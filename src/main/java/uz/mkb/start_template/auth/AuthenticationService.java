package uz.mkb.start_template.auth;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.mkb.start_template.entity.Role;
import uz.mkb.start_template.entity.Token;
import uz.mkb.start_template.entity.User;
import uz.mkb.start_template.repository.TokenRepository;
import uz.mkb.start_template.repository.UserRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationService {

    final JwtService jwtService;
    final UserRepository userRepository;
    final TokenRepository tokenRepository;
    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(AuthenticationController.RegisterRequest request) {

        final var exists = userRepository.existsByEmailAndUsername(request.email(), request.username());
        User user = new User();

        if (!exists) {
            user = User.builder()
                    .name(request.firstName())
                    .lastName(request.lastName())
                    .username(request.username())
                    .password(passwordEncoder.encode(request.password()))
                    .email(request.email())
                    .role(Role.USER)
                    .created(new Date())
                    .updated(new Date())
                    .build();

            userRepository.save(user);
        }

        final var jwtToken = createToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationController.AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var user = userRepository.findByUsername(request.username()).get();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public String createToken(User user){

        var jwtToken = jwtService.generateToken(user);
        Token token = Token.builder()
                .user(user)
                .generatedToken(jwtToken)
                .revoked(false)
                .expired(false)
                .created(new Date())
                .updated(new Date())
                .build();

        tokenRepository.save(token);
        return jwtToken;
    }
}