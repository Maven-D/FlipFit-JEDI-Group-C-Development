package com.flipfit.bean;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a specific time slot available for booking at a gym.
 * It includes details like the slot timing, date, and capacity.
 */
public class TimeSlot {

    private String slotID;
    private String gymID;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private int availableSeats;

    // Getters and Setters

    /**
     * Gets the unique identifier for the time slot.
     * @return A string representing the slot ID.
     */
    public String getSlotID() {
        return slotID;
    }

    /**
     * Sets the unique identifier for the time slot.
     * @param slotID A string containing the slot ID.
     */
    public void setSlotID(String slotID) {
        this.slotID = slotID;
    }

    /**
     * Gets the ID of the gym this time slot belongs to.
     * @return A string representing the gym ID.
     */
    public String getGymID() {
        return gymID;
    }

    /**
     * Sets the ID of the gym this time slot belongs to.
     * @param gymID A string containing the gym ID.
     */
    public void setGymID(String gymID) {
        this.gymID = gymID;
    }

    /**
     * Gets the start time of the slot.
     * @return A LocalTime object representing the start time.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the slot.
     * @param startTime A LocalTime object for the start time.
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the slot.
     * @return A LocalTime object representing the end time.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the slot.
     * @param endTime A LocalTime object for the end time.
     */
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the date of the time slot.
     * @return A LocalDate object representing the date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the time slot.
     * @param date A LocalDate object for the date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the number of available seats for this slot.
     * @return An integer representing the available seat count.
     */
    public int getAvailableSeats() {
        return availableSeats;
    }

    /**
     * Sets the number of available seats for this slot.
     * @param availableSeats An integer for the available seat count.
     */
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
}
