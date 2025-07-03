package com.flipfit.dao;

import com.flipfit.bean.GymOwner;
import com.flipfit.bean.UserRole;

import com.flipfit.utils.DBConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GymOwnerDAOImpl implements UserDAO<GymOwner> {
    @Override
    public void saveUser(GymOwner user) {
        String sql1 = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO gym_owners VALUES (?,?,?)";
        try (Connection conn = DBConnectionUtil.getConnection();
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
            pstmt2.setString(2, user.getPan());
            pstmt2.setString(3, user.getApprovalStatus());

            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Optional<GymOwner> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<GymOwner> findById(String userId) {
        String sql = "SELECT * FROM users WHERE userID = ?";
        String sql2 = "SELECT * FROM gym_owners WHERE userID = ?";

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)
             ) {
            pstmt.setString(1, userId);
            pstmt2.setString(1, userId);

            ResultSet rs = pstmt.executeQuery();
            ResultSet rs2 = pstmt2.executeQuery();

            rs.next();
            GymOwner owner = new GymOwner();
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

            rs2.next();
            owner.setPan(rs2.getString("pancard"));
            owner.setApprovalStatus(rs.getString("is_approved"));

            return Optional.of(owner);




//            while (rs.next()) {
//                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Email: " + rs.getString("email"));
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<GymOwner> getAll() {
        String sql = "SELECT * FROM users WHERE role_id=2";
        String sql2 = "SELECT * FROM gym_owners WHERE user_id=?";
        List<GymOwner> list = new ArrayList<>();

        try (Connection conn = DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2);
             ResultSet rs = pstmt.executeQuery();
             ) {



            while(rs.next()) {
                GymOwner owner = new GymOwner();
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


                pstmt2.setString(1, owner.getUserID());
                ResultSet rs2 = pstmt2.executeQuery();
                rs2.next();
                owner.setPan(rs2.getString("pancard"));
                owner.setApprovalStatus(rs.getString("is_approved"));

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
    public void removeUser(GymOwner user) {

    }
}
