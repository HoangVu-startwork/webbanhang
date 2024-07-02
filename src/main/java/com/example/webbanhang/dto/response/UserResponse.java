package com.example.webbanhang.dto.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Set<RoleResponse> roles;
}
