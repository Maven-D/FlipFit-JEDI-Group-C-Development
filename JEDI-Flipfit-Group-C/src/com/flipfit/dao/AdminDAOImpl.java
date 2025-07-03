package com.flipfit.dao;

import com.flipfit.bean.BaseUser;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.SystemAdmin;
import com.flipfit.bean.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminDAOImpl implements UserDAO<SystemAdmin> {
    @Override
    public void saveUser(SystemAdmin user) {
        String sql1 = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO system_admins VALUES (?)";
        try (Connection conn = com.flipfit.utils.DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {

            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPasswordHash());
            pstmt.setInt(5, user.getRole().getRoleId());
            pstmt.setString(6, user.getPhone());
            pstmt.setString(7, user.getAddress());
            pstmt.setString(8, user.getAdhaar());

            pstmt2.setString(1, user.getUserID());


            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<SystemAdmin> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<SystemAdmin> findById(String userId) {
        return Optional.empty();
    }

    @Override
    public List<SystemAdmin> getAll() {
        return List.of();
    }

    public List<BaseUser> getAllUsers() {
        String sql = "SELECT * FROM users";

        List<BaseUser> list = new ArrayList<>();

        try (Connection conn = com.flipfit.utils.DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();
        ) {



            while(rs.next()) {
                SystemAdmin owner = new SystemAdmin();
                owner.setUserID(rs.getString("userID"));
                owner.setName(rs.getString("name"));
                owner.setEmail(rs.getString("email"));
                owner.setPasswordHash(rs.getString("password_hash"));
                var role = new UserRole();
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleName("GymOwner");
                role.setDescription("GYM OWNER");
                owner.setRole(role);
                owner.setPhone(rs.getString("phone"));
                owner.setAdhaar(rs.getString("aadhaar_card"));
                owner.setAddress(rs.getString("address"));




                list.add(owner);
            }




            return list;


//            while (rs.next()) {
//                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Email: " + rs.getString("email"));
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public List<SystemAdmin> getByRole(UserRole role) {
        return List.of();
    }

    @Override
    public void removeUser(SystemAdmin user) {

    }
}
