package com.slowv.udemi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.slowv.udemi.entity.UserEntity;
import com.slowv.udemi.validation.FileNotNull;
import com.slowv.udemi.validation.FileSize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
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
    @NotBlank(message = "Email is required!")
    @Email
    String email;
    String phone;
    String address;

    @FileSize(max = 15, message = "File không được lớn quá 15MB!")
    @FileNotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    MultipartFile avatar;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    String avatarUrl;


    public static UserDto fromUser(@NonNull final UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress(),
                null,
                entity.getAvatarUrl()
        );
    }

    public UserEntity toEntity() {
        final var entity = new UserEntity();
        entity.setId(id);
        entity.setUsername(username);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setEmail(email);
        entity.setPhone(phone);
        entity.setAddress(address);
        entity.setAvatarUrl(avatarUrl);
        return entity;
    }
}