package com.example.webbanhang.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.webbanhang.constant.PredefinedRole;
import com.example.webbanhang.dto.request.UserCreationRequest;
import com.example.webbanhang.dto.request.UserUpdateRequest;
import com.example.webbanhang.dto.response.UserResponse;
import com.example.webbanhang.entity.Role;
import com.example.webbanhang.entity.User;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.UserMapper;
import com.example.webbanhang.repository.RoleRepository;
import com.example.webbanhang.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        // Kiểm tra xem email đã tồn tại hay chưa

        // Kiểm tra mật khẩu
        validatePassword(request.getPassword());

        if (!isEmailValid(request.getEmail())) {
            throw new RuntimeException("Email không hợp lệ");
        }

        // LocalDate currentDate = LocalDate.now(); // Tạo một đối tượng LocalDate từ ngày tháng năm hiện tại
        LocalDateTime currentDateTime = LocalDateTime.now();
        User user = userMapper.toUser(request);
        // mã hóa password
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDob(currentDateTime);
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        user.setRoles(roles);

        // user.setRoles(roles);
        return userMapper.toUserResponse(user);
    }

    // Phương thức kiểm tra định dạng email
    private boolean isEmailValid(String email) {
        // Biểu thức chính quy kiểm tra định dạng email
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches() && email.endsWith("@gmail.com");
    }

    // kiểm tra điều kiện của password
    private String getPasswordValidationMessage(String password) {
        StringBuilder message = new StringBuilder();

        if (!containsDigit(password)) {
            throw new AppException(ErrorCode.SO_PASSWORD);
        }
        if (!containsLowercase(password)) {
            throw new AppException(ErrorCode.CHUTHUONG_PASSWORD);
        }
        if (!containsUppercase(password)) {
            throw new AppException(ErrorCode.CHUHOA_PASSWORD);
        }
        if (!containsSpecialCharacter(password)) {
            throw new AppException(ErrorCode.KYTUDATBIET_PASSWORD);
        }
        if (containsWhitespace(password)) {
            throw new AppException(ErrorCode.KHOANGTRANG_PASSWORD);
        }

        return message.toString().trim();
    }

    private static boolean containsDigit(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsLowercase(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsUppercase(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsSpecialCharacter(String str) {
        String specialCharacters = "@#$%^!&+=|}{<>";
        for (char c : str.toCharArray()) {
            if (specialCharacters.indexOf(c) >= 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsWhitespace(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isWhitespace(c)) {
                return true;
            }
        }
        return false;
    }

    private void validatePassword(String password) {
        String message = getPasswordValidationMessage(password);
        if (!message.isEmpty()) {
            throw new RuntimeException(message);
        }
    }

    // Trả thông báo
    // @PreAuthorize("hasRole('ADMIN')")  // này cho "roles" -> "name":

    // @PreAuthorize("hasAuthority('APPROVE_POST')") // này cho roles -> name -> permissions -> name
    public List<UserResponse> getUser() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse getUserid(String id) { // lấy dữ liệu theo id
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
    // cập nhật thông tin
    public UserResponse updateUser(String userId, UserUpdateRequest request) {

        // Kiểm tra mật khẩu
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if (request.getPassword() != null) {
            validatePassword(request.getPassword());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Cập nhật các trường khác nếu không null
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getFirsName() != null) {
            user.setFirsName(request.getFirsName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        // Nếu có roles mới, cập nhật roles
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
            var roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse updatePassword(String userId, UserUpdateRequest request) {
        // Kiểm tra mật khẩu
        validatePassword(request.getPassword());
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Cập nhật mật khẩu
        user.setPassword(request.getPassword());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
}

// Annotation @Autowired trong Spring đơn giản là để Spring tự động tiêm một dependency vào bean của bạn.
// Trong trường hợp này, nó giúp bạn tự động tiêm một instance của UserRepository vào biến userRepository
// của UserService. Điều này giúp bạn không cần phải tạo instance của UserRepository bằng cách mới trực tiếp,
// mà Spring sẽ làm điều đó cho bạn.
