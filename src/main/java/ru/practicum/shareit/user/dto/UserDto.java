package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.validation.*;


@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;

    @NotBlank(groups = OnPost.class)
    @Size(min = 2, groups = OnPatch.class)
    String name;

    @NotBlank(groups = OnPost.class)
    @Email(groups = {OnPost.class, OnPatch.class})
    String email;
}
