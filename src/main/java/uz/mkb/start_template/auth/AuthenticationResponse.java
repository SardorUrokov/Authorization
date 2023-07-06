package uz.mkb.start_template.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.mkb.start_template.entity.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    String token;
    Role role;
    String username;
}
