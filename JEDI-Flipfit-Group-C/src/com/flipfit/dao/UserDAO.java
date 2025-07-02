// File: com/flipfit/dao/UserDAO.java
package com.flipfit.dao;

import com.flipfit.bean.BaseUser;
import com.flipfit.bean.UserRole;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for User operations.
 */
public interface UserDAO {
    void saveUser(BaseUser user);
    Optional<BaseUser> findByEmail(String email);
    Optional<BaseUser> findById(String userId);
    List<BaseUser> getAll();
    List<BaseUser> getByRole(UserRole role);
}
