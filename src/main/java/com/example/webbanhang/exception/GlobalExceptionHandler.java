package com.example.webbanhang.exception;

import com.example.webbanhang.dto.request.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // ResponseEntity: dùng để đại diện cho toàn bộ HTTP response bao gồm mã trạng thái (status code), header, và body.
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice; // ControllerAdvice: là một annotation đặc biệt của @Component, cho phép xử lý ngoại lệ trên toàn bộ ứng dụng trong một component xử lý toàn cục.
import org.springframework.web.bind.annotation.ExceptionHandler; // ExceptionHandler: là một annotation dùng để xử lý các ngoại lệ cụ thể và gửi phản hồi tuỳ chỉnh về cho client.\
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

// giúp trả về thông báo trên body của respon chứ không phải báo lỗi trên Terminal
@ControllerAdvice
public class GlobalExceptionHandler {
    // Annotation @ControllerAdvice được sử dụng để định nghĩa một class xử lý ngoại lệ toàn cục cho tất cả các controller.
    // Điều này có nghĩa là bất kỳ ngoại lệ nào được ném ra trong bất kỳ controller nào trong ứng dụng đều có thể được
    // xử lý bởi các phương thức trong class này.
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());

        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorcode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());

        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }

    // trả thông báo trong UserCretionRequest
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e){

        }

        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<String>> handlingRuntimeException(RuntimeException ex, WebRequest request) {
        ApiResponse<String> response = new ApiResponse<>();
        response.setMessage(ex.getMessage());
        response.setResult("Failure");

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ApiResponse> t(RuntimeException ex) {
        ApiResponse response = new ApiResponse();
        response.setMessage(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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


// Khung lệnh handlingValidation
// - Annotation @ExceptionHandler:
// @ExceptionHandler(value = MethodArgumentNotValidException.class) đánh dấu rằng phương thức này sẽ
// được gọi khi xảy ra ngoại lệ MethodArgumentNotValidException.

// Phương thức handlingValidation:
//Đây là phương thức xử lý ngoại lệ. Nó nhận vào một đối tượng MethodArgumentNotValidException làm tham số.

//Lấy thông báo lỗi từ ngoại lệ:
//String enumKey = exception.getFieldError().getDefaultMessage();
//Lấy thông báo lỗi từ ngoại lệ. getFieldError().getDefaultMessage() trả về thông báo lỗi mặc định cho trường không hợp lệ.

//Xác định mã lỗi (ErrorCode):
//ErrorCode errorCode = ErrorCode.INVALID_KEY;
//Đầu tiên, gán một mã lỗi mặc định là INVALID_KEY.
//try { errorCode = ErrorCode.valueOf(enumKey); } catch (IllegalArgumentException e){ }
//Sau đó, thử chuyển đổi chuỗi enumKey thành một giá trị của ErrorCode enum. Nếu không thành công (ngoại lệ IllegalArgumentException),
// mã lỗi sẽ giữ nguyên là INVALID_KEY.

//Tạo đối tượng ApiResponse:
//ApiResponse apiResponse = new ApiResponse();
//Tạo một đối tượng ApiResponse để trả về trong phản hồi.

//Thiết lập mã và thông báo lỗi cho ApiResponse:
//apiResponse.setCode(errorCode.getCode());
//apiResponse.setMessage(errorCode.getMessage());
//Thiết lập mã và thông báo lỗi dựa trên errorCode đã xác định.

//Trả về ResponseEntity:
//return ResponseEntity.badRequest().body(apiResponse);
//Trả về một đối tượng ResponseEntity với mã trạng thái HTTP 400 (Bad Request) và nội dung là đối tượng ApiResponse.