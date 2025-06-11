package ru.practicum.shareit.booking.model;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.time.LocalDateTime;

public enum BookingState {
    ALL {
        @Override
        public BooleanExpression toPredicate(BooleanExpression expr) {
            return expr;
        }
    },
    CURRENT {
        @Override
        public BooleanExpression toPredicate(BooleanExpression expr) {
            LocalDateTime now = LocalDateTime.now();
            return expr
                    .and(QBooking.booking.start.loe(now))
                    .and(QBooking.booking.end.goe(now));
        }
    },
    PAST {
        @Override
        public BooleanExpression toPredicate(BooleanExpression expr) {
            return expr
                    .and(QBooking.booking.end.lt(LocalDateTime.now()));
        }
    },
    FUTURE {
        @Override
        public BooleanExpression toPredicate(BooleanExpression expr) {
            return expr
                    .and(QBooking.booking.start.gt(LocalDateTime.now()));
        }
    },
    WAITING {
        @Override
        public BooleanExpression toPredicate(BooleanExpression expr) {
            return expr
                    .and(QBooking.booking.status.eq(BookingStatus.WAITING));
        }
    },
    REJECTED {
        @Override
        public BooleanExpression toPredicate(BooleanExpression expr) {
            return expr
                    .and(QBooking.booking.status.eq(BookingStatus.REJECTED));
        }
    };

    public abstract BooleanExpression toPredicate(BooleanExpression expr);
}