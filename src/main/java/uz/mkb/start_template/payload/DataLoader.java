package uz.mkb.start_template.payload;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.mkb.start_template.entity.Role;
import uz.mkb.start_template.entity.User;
import uz.mkb.start_template.repository.UserRepository;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataLoader implements CommandLineRunner {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {
        List<User> userList = userRepository.findAll();
        if (userList.size() == 0){
            User user = User.builder()
                    .username("admin@root")
                    .role(Role.ADMIN)
                    .email("anvarov@gmail.com")
                    .name("Javlon")
                    .lastName("Anvarov")
                    .password(passwordEncoder.encode("123"))
                    .created(new Date())
                    .updated(new Date())
                    .build();
            userRepository.save(user);
        }
    }
}