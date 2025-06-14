package ru.practicum.shareit.booking.repo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>, QuerydslPredicateExecutor<Booking> {

    @Query("SELECT b FROM Booking b " +
            "JOIN FETCH b.item i " +
            "JOIN FETCH i.owner o " +
            "JOIN FETCH b.booker bk " +
            "WHERE b.id = :id AND (o.id = :userId OR bk.id = :userId)")
    Optional<Booking> findByIdAndBookerOrOwner(@Param("id") Long id, @Param("userId") Long userId);

}
