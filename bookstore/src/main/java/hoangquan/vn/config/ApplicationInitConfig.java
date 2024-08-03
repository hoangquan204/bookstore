package hoangquan.vn.config;

import hoangquan.vn.entity.Role;
import hoangquan.vn.entity.User;
import hoangquan.vn.exception.AppException;
import hoangquan.vn.exception.ErrorCode;
import hoangquan.vn.repository.RoleRepository;
import hoangquan.vn.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findUserByUsername("admin").isEmpty()){
                Role role_admin = roleRepository.findRoleByName("admin")
                        .orElseThrow(()-> new AppException(ErrorCode.ROLE_NOT_FOUND));
                Set<Role> roles = new HashSet<Role>();
                roles.add(role_admin);

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("Account admin has been created with password: admin");
            }
        };
    }
}
