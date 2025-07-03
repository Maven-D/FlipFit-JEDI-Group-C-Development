package com.flipfit.business;

import com.flipfit.bean.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public interface FlipFitGymServiceInterface {
    /**
     * Public static method to allow other classes to access the master list.
     *
     * @return The list of all time slots.
     */
//    static List<TimeSlot> getAllTimeSlots() {
//        return FlipFitGymServiceImpl.getAllTimeSlots();
//    }

    List<TimeSlot> getAvailability(String gymId, LocalDate date);

    void addTimeSlot(TimeSlot slot);
}
