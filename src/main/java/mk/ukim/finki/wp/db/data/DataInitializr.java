package mk.ukim.finki.wp.db.data;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.user.Administrator;
import mk.ukim.finki.wp.db.entity.user.UserEntity;
import mk.ukim.finki.wp.db.entity.user.enums.Role;
import mk.ukim.finki.wp.db.repository.AdministratorRepository;
import mk.ukim.finki.wp.db.repository.UserEntityRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializr {

    private final PasswordEncoder passwordEncoder;
    private final UserEntityRepository userEntityRepository;
    private final AdministratorRepository administratorRepository;

    @PostConstruct
    public void addData() {
        Optional<UserEntity> optionalUserEntity = userEntityRepository.findByEmail("admin@gmail.com");

        if (optionalUserEntity.isEmpty()) {

            UserEntity userEntity = UserEntity.builder()
                    .firstName("admin")
                    .lastName("admin")
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .role(Role.ADMIN)
                    .build();

            UserEntity savedEntity = userEntityRepository.save(userEntity);


            Administrator admin = new Administrator();
            admin.setUserEntity(savedEntity);

            administratorRepository.save(admin);
        }
    }
}
