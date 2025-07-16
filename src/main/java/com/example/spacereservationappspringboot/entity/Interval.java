package com.example.spacereservationappspringboot.entity;

import com.example.spacereservationappspringboot.exception.InvalidTimeIntervalException;
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

    public static Interval of(Date start, Date end) throws InvalidTimeIntervalException {
        if (start == null || end == null) {
            throw new InvalidTimeIntervalException("Start time and end time must not be null.");
        }
        if (start.after(end)) {
            throw new InvalidTimeIntervalException("Start time must be before the end time!");
        }
        return new Interval(start, end);
    }
}
