package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    Long id;

    @NotBlank
    String name;

    @NotBlank
    @Email
    String email;
}
