package com.example.webbanhang.exception;

import org.springframework.http.ResponseEntity; // ResponseEntity: dùng để đại diện cho toàn bộ HTTP response bao gồm mã trạng thái (status code), header, và body.
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice; // ControllerAdvice: là một annotation đặc biệt của @Component, cho phép xử lý ngoại lệ trên toàn bộ ứng dụng trong một component xử lý toàn cục.
import org.springframework.web.bind.annotation.ExceptionHandler; // ExceptionHandler: là một annotation dùng để xử lý các ngoại lệ cụ thể và gửi phản hồi tuỳ chỉnh về cho client.\

import java.util.Objects;

// giúp trả về thông báo trên body của respon chứ không phải báo lỗi trên Terminal
@ControllerAdvice
public class GlobalExceptionHandler {
    // Annotation @ControllerAdvice được sử dụng để định nghĩa một class xử lý ngoại lệ toàn cục cho tất cả các controller.
    // Điều này có nghĩa là bất kỳ ngoại lệ nào được ném ra trong bất kỳ controller nào trong ứng dụng đều có thể được
    // xử lý bởi các phương thức trong class này.
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<String> handlingRuntimeException(RuntimeException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    // trả thông báo trong UserCretionRequest
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<String> handlingValidation(MethodArgumentNotValidException exception){
        return ResponseEntity.badRequest().body(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
    }

}
// ResponseEntity.badRequest() là một cách viết tắt để tạo một ResponseEntity với trạng thái 400.
// .body(exception.getMessage()) thiết lập nội dung của phản hồi là thông báo của ngoại lệ.


// Mục đích của class và phương thức này là xử lý RuntimeException một cách tập trung, đảm bảo rằng thay vì ứng dụng bị
// lỗi hoặc hiển thị một stack trace (dấu vết lỗi) trên terminal, nó sẽ trả về một thông báo lỗi thân thiện trong body của
// HTTP response. Điều này cải thiện trải nghiệm người dùng bằng cách cung cấp phản hồi rõ ràng và giữ cho log của ứng dụng
// sạch sẽ hơn bằng cách tránh in ra các stack trace không cần thiết trên terminal.



// Cách hoạt động:
//Khi một phương thức trong controller của bạn nhận vào các tham số không hợp lệ, Spring sẽ ném ra ngoại lệ MethodArgumentNotValidException.
//Spring sẽ tìm kiếm một phương thức có anotations @ExceptionHandler và xử lý ngoại lệ tương ứng.
//Phương thức handlingValidation sẽ được gọi, nhận vào đối tượng MethodArgumentNotValidException.
//Phương thức này tạo ra phản hồi HTTP 400 với nội dung là mã thông báo chi tiết về lỗi, sau đó trả về cho client.