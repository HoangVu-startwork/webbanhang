package com.example.webbanhang.configuration;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.webbanhang.constant.PredefinedRole;
import com.example.webbanhang.entity.Role;
import com.example.webbanhang.entity.User;
import com.example.webbanhang.repository.RoleRepository;
import com.example.webbanhang.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Configuration // Đánh dấu class này là một Spring Configuration, nơi bạn có thể định nghĩa các Bean cho ứng dụng.
@RequiredArgsConstructor // Tự động tạo constructor với tất cả các trường final, giúp giảm boilerplate code.
@FieldDefaults(
        level = AccessLevel.PRIVATE,
        makeFinal = true) // Thiết lập tất cả các trường trong class này là private và final mặc định.
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "hoangvu.startwork@gmail.com";

    @NonFinal
    static final String ADMIN_PASSWORD = "0903075546Vu!";

    @NonFinal
    @Value("${administrator.password}")
    protected String new_password;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            // Tạo vai trò USER nếu chưa tồn tại
            if (roleRepository.findById(PredefinedRole.USER_ROLE).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());
                log.info("User role has been created");
            }

            // Tạo vai trò ADMIN nếu chưa tồn tại
            if (roleRepository.findById(PredefinedRole.ADMIN_ROLE).isEmpty()) {
                roleRepository.save(Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());
                log.info("Admin role has been created");
            }

            // Tạo tài khoản admin nếu chưa tồn tại
            if (userRepository.findByEmail(ADMIN_USER_NAME).isEmpty()) {
                Role adminRole =
                        roleRepository.findById(PredefinedRole.ADMIN_ROLE).orElseThrow();
                var roles = new HashSet<Role>();
                roles.add(adminRole);

                User user = User.builder()
                        .email(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("Admin user has been created with default password, please change it");
            }
        };
    }
}
