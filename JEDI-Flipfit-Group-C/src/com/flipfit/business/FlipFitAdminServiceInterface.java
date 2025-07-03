package com.flipfit.business;

import com.flipfit.bean.Customer;
import com.flipfit.bean.Gym;

public interface FlipFitAdminServiceInterface {
    void addCustomer(Customer customer);

    void removeCustomer(Customer customer);

    void addGym(Gym gym);

    boolean removeGym(Gym gym);
}
