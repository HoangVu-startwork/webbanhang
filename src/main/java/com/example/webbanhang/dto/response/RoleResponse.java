package com.example.webbanhang.dto.response;

import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {
    private String name;

    private String description;
    Set<PermissionResponse> permissions;
}
