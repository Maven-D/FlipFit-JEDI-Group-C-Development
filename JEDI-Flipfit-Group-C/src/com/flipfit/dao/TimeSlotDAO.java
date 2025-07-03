package com.flipfit.dao;

import com.flipfit.bean.TimeSlot;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for TimeSlot operations.
 */
public interface TimeSlotDAO {
    void save(TimeSlot timeSlot);
    Optional<TimeSlot> findBySlotId(String slotId);
    List<TimeSlot> findAvailableByGymIdAndDate(String gymId, LocalDate date);
    boolean update(TimeSlot timeSlot);
    boolean delete(String slotId);
}
