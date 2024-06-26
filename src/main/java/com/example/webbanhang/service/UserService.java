package com.example.webbanhang.service;
import com.example.webbanhang.dto.request.UserCreationRequest;
import com.example.webbanhang.dto.request.UserUpdateRequest;
import com.example.webbanhang.dto.response.UserResponse;
import com.example.webbanhang.entity.User;
import com.example.webbanhang.enums.Role;
import com.example.webbanhang.exception.AppException;
import com.example.webbanhang.exception.ErrorCode;
import com.example.webbanhang.mapper.UserMapper;
import com.example.webbanhang.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest request){
        // Kiểm tra xem email đã tồn tại hay chưa
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            // trường hợp khác thì sử dụng RuntimeException("--") để bắt lỗi RuntimeException là truong hơp không bắt được
            throw new AppException(ErrorCode.USER_EXISTED);
        }

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

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        user.setRoles(roles);
        return userRepository.save(user);
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
    private String getPasswordValidationMessage(String password){
        StringBuilder message = new StringBuilder();

        if (password.length() < 8) {
            throw new AppException(ErrorCode.SIZE_PASSWORD);
        }
        if (!password.matches(".*[0-9].*")){
            throw new AppException(ErrorCode.SO_PASSWORD);
        }
        if (!password.matches(".*[a-z].*")){
            throw new AppException(ErrorCode.CHUTHUONG_PASSWORD);
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new AppException(ErrorCode.CHUHOA_PASSWORD);
        }
        if (!password.matches(".*[@#$%^!&+=|}{<>].*")) {
            throw new AppException(ErrorCode.KYTUDATBIET_PASSWORD);
        }
        if (!password.matches("\\S+")) {
            throw new AppException(ErrorCode.KHOANGTRANG_PASSWORD);
        }

        return message.toString().trim();
    }
    private void validatePassword(String password) {
        String message = getPasswordValidationMessage(password);
        if (!message.isEmpty()) {
            throw new RuntimeException(message);
        }
    }
    // Trả thông báo
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUser(){
        log.info("In method get Users");
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.email == authentication.name")
    public UserResponse getUserid(String id){ // lấy dữ liệu theo id
        log.info("In method get user by Id");
        return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("Không có dữ liệu")));
    }

    // cập nhật thông tin
    public UserResponse updateUser(String userId, UserUpdateRequest request){

        // Kiểm tra mật khẩu
        validatePassword(request.getPassword());
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        userMapper.updateUser(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

    public UserResponse getMyInfo(){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
}


// Annotation @Autowired trong Spring đơn giản là để Spring tự động tiêm một dependency vào bean của bạn.
// Trong trường hợp này, nó giúp bạn tự động tiêm một instance của UserRepository vào biến userRepository
// của UserService. Điều này giúp bạn không cần phải tạo instance của UserRepository bằng cách mới trực tiếp,
// mà Spring sẽ làm điều đó cho bạn.