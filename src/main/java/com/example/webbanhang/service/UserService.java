package com.example.webbanhang.service;


import com.example.webbanhang.cto.request.UserCreationRequest;
import com.example.webbanhang.cto.request.UserUpdateRequest;
import com.example.webbanhang.entity.User;
import com.example.webbanhang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request){
        // Kiểm tra xem email đã tồn tại hay chưa
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        // Kiểm tra mật khẩu
        validatePassword(request.getPassword());


        if (!isEmailValid(request.getEmail())) {
            throw new RuntimeException("Email không hợp lệ");
        }

        // LocalDate currentDate = LocalDate.now(); // Tạo một đối tượng LocalDate từ ngày tháng năm hiện tại
        LocalDateTime currentDateTime = LocalDateTime.now();
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFirsName(request.getFirsName());
        user.setLastName(request.getLastName());
        user.setDob(currentDateTime);
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
            message.append("Mật khẩu phải có ít nhất 8 ký tự. ");
        }
        if (!password.matches(".*[0-9].*")){
            message.append("Mật khẩu phải chứa ít nhất một chữ số. ");
        }
        if (!password.matches(".*[a-z].*")){
            message.append("Mật khẩu phải chứa ít nhất một chữ thưởng. ");
        }
        if (!password.matches(".*[A-Z].*")) {
            message.append("Mật khẩu phải chứa ít nhất một chữ hoa. ");
        }
        if (!password.matches(".*[@#$%^!&+=|}{<>].*")) {
            message.append("Mật khẩu phải chứa ít nhất một ký tự đặc biệt. ");
        }
        if (!password.matches("\\S+")) {
            message.append("Mật khẩu không được chứa khoảng trắng. ");
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

    public List<User> getUser(){
        return userRepository.findAll(); // lấy toàn bộ csdl
    }


    public User getUserid(String id){ // lấy dữ liệu theo id
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Không có dữ liệu"));
    }

    // cập nhật thông tin
    public User updateUser(String userId, UserUpdateRequest request){

        // Kiểm tra mật khẩu
        validatePassword(request.getPassword());

        User user = getUserid(userId);

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirsName(request.getFirsName());
        user.setLastName(request.getLastName());

        return userRepository.save(user);
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }
}


// Annotation @Autowired trong Spring đơn giản là để Spring tự động tiêm một dependency vào bean của bạn.
// Trong trường hợp này, nó giúp bạn tự động tiêm một instance của UserRepository vào biến userRepository
// của UserService. Điều này giúp bạn không cần phải tạo instance của UserRepository bằng cách mới trực tiếp,
// mà Spring sẽ làm điều đó cho bạn.