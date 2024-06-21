package com.example.webbanhang.dto.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequest {
    private String username;
    private String password;
    private String firsName;
    private String lastName;
    private String phone;
    private String ngaysinh;
}
