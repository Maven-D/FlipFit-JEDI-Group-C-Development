package com.flipfit.business;

import java.util.List;
import java.util.Optional;

import com.flipfit.bean.Booking;
import com.flipfit.bean.Gym;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.TimeSlot;

public interface FlipFitGymOwnerServiceInterface {

	List<Booking> viewGymBookings(String gymId);

	void manageGymDetails(Gym details);

	boolean registerNewGym(Gym newGym);

	boolean addTimeSlot(TimeSlot slot);

	public List<Gym> getAllGymsOfOwner(String ownerId);

	Optional<GymOwner> getGymOwner(String userId);

}
