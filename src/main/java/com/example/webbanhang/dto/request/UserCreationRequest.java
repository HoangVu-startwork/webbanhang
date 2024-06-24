package com.example.webbanhang.dto.request;
import java.time.LocalDate;
import java.util.Set;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationRequest {
    private String username;

//    @Size(min = 8, message = "SIZE_PASSWORD")
    private String password;
    private String email;
    private String firsName;
    private String lastName;
    private String phone;
    private String ngaysinh;
    private LocalDate dob;
    private Set<String> roles;
}
