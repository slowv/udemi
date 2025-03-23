package com.slowv.udemi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slowv.udemi.validation.FileNotNull;
import com.slowv.udemi.validation.FileSize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * DTO for {@link com.slowv.udemi.entity.UserEntity}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto implements Serializable {
    Long id;
    @NotBlank(message = "Username is required!")
    String username;
    String firstName;
    String lastName;
    String fullName;
    @NotBlank(message = "Email is required!")
    @Email
    String email;
    String phone;

    AddressDto address;

    @FileSize(max = 15, message = "File không được lớn quá 15MB!")
    @FileNotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    MultipartFile avatar;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String avatarUrl;
}