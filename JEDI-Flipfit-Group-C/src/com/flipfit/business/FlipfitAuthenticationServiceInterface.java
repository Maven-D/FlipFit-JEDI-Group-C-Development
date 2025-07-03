package com.flipfit.business;

import com.flipfit.bean.BaseUser;

public interface FlipfitAuthenticationServiceInterface {

	BaseUser verifyCredentials(String email, String password);

	BaseUser validateToken(String token);

	void registerUser(BaseUser user);

}