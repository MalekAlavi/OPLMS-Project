package mk.ukim.finki.wp.db.service;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.db.entity.user.Instructor;
import mk.ukim.finki.wp.db.entity.user.User;
import mk.ukim.finki.wp.db.entity.user.UserEntity;
import mk.ukim.finki.wp.db.entity.user.enums.Role;
import mk.ukim.finki.wp.db.repository.InstructorRepository;
import mk.ukim.finki.wp.db.repository.UserEntityRepository;
import mk.ukim.finki.wp.db.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final UserRepository userRepository;
    private final InstructorRepository instructorRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String firstName, String lastName, String email, String password, Role role) {
        Optional<UserEntity> userEntity = userEntityRepository.findByEmail(email);

        if(userEntity.isEmpty()) {

            UserEntity newUser = UserEntity.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .role(role)
                    .build();

            userEntityRepository.save(newUser);


            if (role == Role.USER) {
                User user = new User();
                user.setUserEntity(newUser);
                userRepository.save(user);
            } else if (role == Role.INSTRUCTOR) {
                Instructor instructor = new Instructor();
                instructor.setUserEntity(newUser);
                instructorRepository.save(instructor);
            }
        }
    }
}
