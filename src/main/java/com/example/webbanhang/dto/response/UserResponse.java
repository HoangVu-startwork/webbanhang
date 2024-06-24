package com.example.webbanhang.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String firsName;
    private String lastName;
    private String phone;
    private String ngaysinh;
    private String dob;
    private Set<String> roles;
}
