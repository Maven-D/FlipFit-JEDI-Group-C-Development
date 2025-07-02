package com.flipfit.business;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;

import java.util.List;

public interface FlipFitGymOwnerServiceInterface {
    List<Booking> viewGymBookings(String gymId);

    void manageGymDetails(Gym details);
}
