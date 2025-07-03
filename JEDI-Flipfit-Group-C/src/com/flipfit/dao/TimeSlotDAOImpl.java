// File: com/flipfit/dao/TimeSlotDAOImpl.java
package com.flipfit.dao;

import com.flipfit.bean.TimeSlot;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the TimeSlotDAO interface.
 */
public class TimeSlotDAOImpl implements TimeSlotDAO {

    private static final List<TimeSlot> timeSlotTable = new ArrayList<>();

    static {
        timeSlotTable.add(new TimeSlot(UUID.randomUUID().toString(), "gym01", LocalTime.of(6, 0), LocalTime.of(7, 0), LocalDate.now(), 10));
        timeSlotTable.add(new TimeSlot(UUID.randomUUID().toString(), "gym01", LocalTime.of(7, 0), LocalTime.of(8, 0), LocalDate.now(), 5));
        timeSlotTable.add(new TimeSlot(UUID.randomUUID().toString(), "gym02", LocalTime.of(8, 0), LocalTime.of(9, 0), LocalDate.now(), 15));
        timeSlotTable.add(new TimeSlot(UUID.randomUUID().toString(), "gym02", LocalTime.of(9, 0), LocalTime.of(10, 0), LocalDate.now(), 10));
    }

    @Override
    public void save(TimeSlot timeSlot) {
        System.out.println("DAO: Saving time slot " + timeSlot.getSlotID());
        timeSlotTable.add(timeSlot);
    }

    @Override
    public Optional<TimeSlot> findBySlotId(String slotId) {
        System.out.println("DAO: Searching for time slot with ID: " + slotId);
        return timeSlotTable.stream()
                .filter(ts -> ts.getSlotID().equals(slotId))
                .findFirst();
    }

    @Override
    public List<TimeSlot> findAvailableByGymIdAndDate(String gymId, LocalDate date) {
        System.out.println("DAO: Fetching available slots for gym " + gymId + " on " + date);
        return timeSlotTable.stream()
                .filter(ts -> ts.getGymID().equals(gymId) && ts.getDate().isEqual(date) && ts.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(TimeSlot slotToUpdate) {
        System.out.println("DAO: Updating time slot with ID: " + slotToUpdate.getSlotID());
        for (int i = 0; i < timeSlotTable.size(); i++) {
            if (timeSlotTable.get(i).getSlotID().equals(slotToUpdate.getSlotID())) {
                timeSlotTable.set(i, slotToUpdate);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(String slotId) {
        System.out.println("DAO: Deleting time slot with ID: " + slotId);
        return timeSlotTable.removeIf(ts -> ts.getSlotID().equals(slotId));
    }

    @Override
    public List<TimeSlot> getAll() {
        return new ArrayList<>(timeSlotTable);
    }
}
