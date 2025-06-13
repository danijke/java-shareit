package ru.practicum.shareit.booking.model;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum BookingStatus {
    WAITING("Ожидает подтверждения"),
    APPROVED("Подтверждено"),
    REJECTED("Отклонено"),
    CANCELED("Отменено");
    private final String displayName;
}
