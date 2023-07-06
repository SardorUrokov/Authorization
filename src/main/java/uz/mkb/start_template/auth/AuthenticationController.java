package uz.mkb.start_template.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    public record RegisterRequest(@NotBlank(message = "firstName required") String firstName,
                                  @NotBlank(message = "lastName required") String lastName,
                                  @NotBlank(message = "username required") String username,
                                  @NotBlank(message = "email required") String email,
                                  @NotBlank(message = "password required") String password) {}

    public record AuthenticationRequest(String username, String password) {}
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> adminRegister(@RequestBody @Valid RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> auth(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}