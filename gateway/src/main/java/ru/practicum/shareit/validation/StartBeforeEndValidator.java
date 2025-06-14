package ru.practicum.shareit.validation;

import jakarta.validation.*;
import ru.practicum.shareit.booking.dto.BookingRequestDto;

public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, BookingRequestDto> {
    @Override
    public boolean isValid(BookingRequestDto dto, ConstraintValidatorContext context) {
        if (dto.getStart() == null || dto.getEnd() == null) {
            return true;
        }

        return dto.getStart().isBefore(dto.getEnd());
    }
}
