package com.flipfit.business;

import com.flipfit.bean.Customer;
import com.flipfit.bean.Gym;

public interface FlipFitAdminServiceInterface {

	/**
	 * Adds a new customer to the system.
	 * @param customer The Customer object to add.
	 */
	void addCustomer(Customer customer);

	/**
	 * Removes a customer from the system.
	 * @param customer The Customer object to remove.
	 * @return true if removal was successful, false otherwise.
	 */
	boolean removeCustomer(Customer customer);

	/**
	 * Adds a new gym to the system.
	 * @param gym The Gym object to add.
	 */
	void addGym(Gym gym);

	/**
	 * Removes a gym from the system.
	 * @param gym The Gym object to remove.
	 * @return true if removal was successful, false otherwise.
	 */
	boolean removeGym(Gym gym);

}