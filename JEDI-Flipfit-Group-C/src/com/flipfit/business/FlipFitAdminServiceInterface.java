package com.flipfit.business;

import com.flipfit.bean.Customer;
import com.flipfit.bean.Gym;

public interface FlipFitAdminServiceInterface {
<<<<<<< HEAD

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
=======
    void addCustomer(Customer customer);

    boolean removeCustomer(Customer customer);

    void addGym(Gym gym);

    boolean removeGym(Gym gym);
}
>>>>>>> 4678108a044592849fba849c12eb21ae5b8698e0
