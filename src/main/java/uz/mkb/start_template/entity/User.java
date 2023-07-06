package uz.mkb.start_template.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.mkb.start_template.audit.Auditable;
import uz.mkb.start_template.entity.abstract_entity.AbstractEntity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
@EntityListeners(Auditable.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends AbstractEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String lastName;
    String username;
    String email;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getClassName() {
        return "USER";
    }

    Date created = new Date();
    Date updated = new Date();

}
