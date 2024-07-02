package com.example.webbanhang.dto.request;

import java.time.LocalDate;

import com.example.webbanhang.validator.DobConstraint;

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

    @DobConstraint(min = 18, message = "INVALID_DOB")
    private LocalDate ngaysinh;

    private LocalDate dob;
}
