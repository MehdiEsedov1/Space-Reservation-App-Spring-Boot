package com.example.spacereservationappspringboot.entity;

import com.example.spacereservationappspringboot.exception.model.InvalidTimeIntervalException;
import com.example.spacereservationappspringboot.exception.type.ExceptionType;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Interval implements Serializable {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static boolean isOverlap(Interval a, Interval b) {
        return !(a.endTime.isBefore(b.startTime) || b.endTime.isBefore(a.startTime));
    }

    public static Interval of(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || start.isAfter(end)) {
            throw new InvalidTimeIntervalException(ExceptionType.BAD_REQUEST);
        }
        return new Interval(start, end);
    }
}

