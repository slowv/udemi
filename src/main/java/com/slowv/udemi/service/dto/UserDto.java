package com.slowv.udemi.service.dto;

import com.slowv.udemi.entity.UserEntity;
import lombok.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * DTO for {@link com.slowv.udemi.entity.UserEntity}
 */
@Value
public class UserDto implements Serializable {
    Long id;
    String username;
    String firstName;
    String lastName;
    String email;
    String phone;
    String address;

    public static UserDto fromUser(@NonNull final UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getAddress()
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
        return entity;
    }
}