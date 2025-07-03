package com.flipfit.business;

import com.flipfit.bean.BaseUser;
import com.flipfit.bean.GymOwner;

public interface FlipFitAuthenticationServiceInterface {
    BaseUser verifyCredentials(String email, String password);

    BaseUser validateToken(String token);

    void registerCustomer(BaseUser user);

    void registerGymOwner(GymOwner gymOwner);
}
