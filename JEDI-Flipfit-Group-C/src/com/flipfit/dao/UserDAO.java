package com.flipfit.dao;

import com.flipfit.bean.BaseUser;
import com.flipfit.bean.UserRole;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for User operations.
 */
public interface UserDAO<T extends BaseUser> {
    void saveUser(T user);
    Optional<T> findByEmail(String email);
    Optional<T> findById(String userId);
    List<T> getAll();
    void removeUser(T user);
}
