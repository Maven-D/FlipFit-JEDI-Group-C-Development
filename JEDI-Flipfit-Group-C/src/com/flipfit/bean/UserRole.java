package com.flipfit.bean;

/**
 * Represents a user role within the system, containing details
 * like its ID, name, and description.
 */
public class UserRole {

    private int roleId;
    private String roleName;
    private String description;

    /**
     * Default constructor.
     */
    public UserRole() {
    }

    /**
     * Parameterized constructor to initialize a UserRole object.
     *
     * @param roleId      The unique numeric ID for the role.
     * @param roleName    The name of the role (e.g., "CUSTOMER", "ADMIN").
     * @param description A brief description of the role's permissions and purpose.
     */
    public UserRole(int roleId, String roleName, String description) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.description = description;
    }

    // --- Getters and Setters ---

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}