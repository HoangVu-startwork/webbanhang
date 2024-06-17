package com.example.webbanhang.dto.request;


import jakarta.validation.constraints.Size;

import java.time.LocalDate;

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
    private LocalDate dob;

}
