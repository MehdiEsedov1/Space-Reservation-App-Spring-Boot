package com.example.spacereservationappspringboot.entity;

import com.example.spacereservationappspringboot.exception.model.InvalidTimeIntervalException;
import com.example.spacereservationappspringboot.exception.type.ExceptionType;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Interval implements Serializable {
    private Date startTime;
    private Date endTime;

    public static boolean isOverlap(Interval a, Interval b) {
        return !(a.endTime.before(b.startTime) || b.endTime.before(a.startTime));
    }

    public static Interval of(Date start, Date end) {
        if (start == null || end == null) {
            throw new InvalidTimeIntervalException(ExceptionType.BAD_REQUEST);
        }
        if (start.after(end)) {
            throw new InvalidTimeIntervalException(ExceptionType.BAD_REQUEST);
        }
        return new Interval(start, end);
    }
}
