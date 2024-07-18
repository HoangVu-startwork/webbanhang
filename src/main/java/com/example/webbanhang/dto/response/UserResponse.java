package com.example.webbanhang.dto.response;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String firsName;
    private String lastName;
    private String phone;
    private String ngaysinh;
    private String dob;
    Set<RoleResponse> roles;
}
