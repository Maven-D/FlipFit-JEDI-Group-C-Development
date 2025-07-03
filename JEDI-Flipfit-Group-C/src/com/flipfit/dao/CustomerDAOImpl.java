package com.flipfit.dao;

import com.flipfit.bean.Customer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements UserDAO<Customer> {
    @Override
    public void saveUser(Customer user) {
        String sql1 = "INSERT INTO users VALUES (?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO customers VALUES (?)";
        try (Connection conn = com.flipfit.util.DBConnectionUtil.getConnection();
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
    public Optional<Customer> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findById(String userId) {
        String sql = "SELECT * FROM users WHERE userID = ?";
//        String sql2 = "SELECT * FROM gym_owners WHERE userID = ?";

        try (Connection conn = com.flipfit.util.DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
//             PreparedStatement pstmt2 = conn.prepareStatement(sql2)
        ) {
            pstmt.setString(1, userId);
//            pstmt2.setString(1, userId);

            ResultSet rs = pstmt.executeQuery();
//            ResultSet rs2 = pstmt2.executeQuery();

            rs.next();
            Customer owner = new Customer();
            owner.setUserID(rs.getString("user_id"));
            owner.setName(rs.getString("name"));
            owner.setEmail(rs.getString("email"));
            owner.setPasswordHash(rs.getString("password_hash"));
            var role = new UserRole();
            role.setRoleId(rs.getInt("role_id"));
            role.setRoleName("Customer");
            role.setDescription("GYM CUSTOMER");
            owner.setRole(role);
            owner.setPhone(rs.getString("phone"));
            owner.setAdhaar(rs.getString("aadhaar_card"));
            owner.setAddress(rs.getString("address"));

//            rs2.next();
//            owner.setPan(rs2.getString("pancard"));
//            owner.setApprovalStatus(rs.getString("is_approved"));

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
    public List<Customer> getAll() {
        String sql = "SELECT * FROM users WHERE role_id = 1";
        List<Customer> list = new ArrayList<>();
//        String sql2 = "SELECT * FROM gym_owners";

        try (Connection conn = com.flipfit.util.DBConnectionUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)
//             PreparedStatement pstmt2 = conn.prepareStatement(sql2)
        ) {
//            pstmt.setString(1, userId);
//            pstmt2.setString(1, userId);

            ResultSet rs = pstmt.executeQuery();
//            ResultSet rs2 = pstmt2.executeQuery();

            while(rs.next()) {
                Customer owner = new Customer();
                owner.setUserID(rs.getString("user_id"));
                owner.setName(rs.getString("name"));
                owner.setEmail(rs.getString("email"));
                owner.setPasswordHash(rs.getString("password_hash"));
                var role = new UserRole();
                role.setRoleId(rs.getInt("role_id"));
                role.setRoleName("Customer");
                role.setDescription("GYM CUSTOMER");
                owner.setRole(role);
                owner.setPhone(rs.getString("phone"));
                owner.setAdhaar(rs.getString("aadhaar_card"));
                owner.setAddress(rs.getString("address"));

                list.add(owner);
            }


//            rs2.next();
//            owner.setPan(rs2.getString("pancard"));
//            owner.setApprovalStatus(rs.getString("is_approved"));

            return list;




//            while (rs.next()) {
//                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Email: " + rs.getString("email"));
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

//    @Override
//    public List<Customer> getByRole(UserRole role) {
//        return List.of();
//    }

    @Override
    public void removeUser(Customer user) {
        String sql = "DELETE FROM users WHERE userID = ?";
        String sql2 = "DELETE FROM customers WHERE userID = ?";

        try(Connection conn = com.flipfit.util.DBConnectionUtil.getConnection();
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
}
