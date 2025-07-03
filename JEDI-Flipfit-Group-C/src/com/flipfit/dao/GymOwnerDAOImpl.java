package com.flipfit.dao;

import com.flipfit.bean.GymOwner;
import com.flipfit.bean.UserRole;

import com.flipfit.util.DBConnectionUtil;

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
        String sql = "SELECT * FROM users WHERE user_id = ?";
        String sql2 = "SELECT * FROM gym_owners WHERE user_id = ?";

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
            owner.setUserID(rs.getString("user_id"));
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
            owner.setApprovalStatus(rs2.getString("is_approved"));

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
                owner.setUserID(rs.getString("user_id"));
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
                owner.setApprovalStatus(rs2.getString("is_approved"));

                list.add(owner);
            }




            return list;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }





    @Override
    public void removeUser(GymOwner user) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        String sql2 = "DELETE FROM gym_owners WHERE user_id = ?";

        try(Connection conn = DBConnectionUtil.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt.setString(1, user.getUserID());
            pstmt2.setString(1, user.getUserID());

            int rs = pstmt.executeUpdate();
            int rs2 = pstmt2.executeUpdate();

            System.out.println(rs+" row(s) in user deleted.");
            System.out.println(rs2+" row(s) in gym_owner deleted.");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    public boolean update(GymOwner user) {
        String updateUserSQL = "UPDATE users SET name = ?, email = ?, password_hash = ?, phone = ?, address = ?, aadhaar_card = ? WHERE user_id = ?";
        String updateGymOwnerSQL = "UPDATE gym_owners SET pancard = ?, is_approved = ? WHERE user_id = ?";
        Connection conn = null;
        try {
            conn = DBConnectionUtil.getConnection();
            // 1. Start transaction
            conn.setAutoCommit(false);

            // 2. Update users table
            try (PreparedStatement pstmtUser = conn.prepareStatement(updateUserSQL)) {
                pstmtUser.setString(1, user.getName());
                pstmtUser.setString(2, user.getEmail());
                pstmtUser.setString(3, user.getPasswordHash());
                pstmtUser.setString(4, user.getPhone());
                pstmtUser.setString(5, user.getAddress());
                pstmtUser.setString(6, user.getAdhaar());
                pstmtUser.setString(7, user.getUserID());
                pstmtUser.executeUpdate();
            }

            // 3. Update gym_owners table
            try (PreparedStatement pstmtOwner = conn.prepareStatement(updateGymOwnerSQL)) {
                pstmtOwner.setString(1, user.getPan());
                pstmtOwner.setString(2, user.getApprovalStatus());
                pstmtOwner.setString(3, user.getUserID());
                pstmtOwner.executeUpdate();
            }

            // 4. Commit transaction if both updates succeed
            conn.commit();
            System.out.println("DAO: Successfully updated gym owner with ID: " + user.getUserID());
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            // 5. Rollback transaction on error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            // 6. Ensure connection is always closed
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset to default
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
