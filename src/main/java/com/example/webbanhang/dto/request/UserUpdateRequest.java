package com.example.webbanhang.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    private String username;
    private String password;
    private String firsName;
    private String lastName;
    private String phone;
    private String ngaysinh;
    List<String> roles;

}
