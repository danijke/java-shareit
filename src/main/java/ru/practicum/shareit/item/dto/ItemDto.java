package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemDto {
    Long id;
    @Size(min = 2)
    String name;
    @Size(min = 2)
    String description;
    Boolean available;
}
