package StaffAdmin.dao;

import StaffAdmin.connection.AzureSqlDatabaseConnection;
import StaffAdmin.model.StaffAdmin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffAdminDAO {

    // Validate login credentials for Admin or Staff
	public StaffAdmin validateLogin(String username, String password) {
	    String sql = "SELECT * FROM Staff WHERE username = ? AND password = ?";
	    StaffAdmin staffAdmin = null;

	    try (Connection con = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        System.out.println("Attempting to validate login for username: " + username);

	        ps.setString(1, username);
	        ps.setString(2, password);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                staffAdmin = new StaffAdmin();
	                staffAdmin.setStaffId(rs.getInt("staff_id"));
	                staffAdmin.setUsername(rs.getString("username"));
	                staffAdmin.setPassword(rs.getString("password"));
	                staffAdmin.setEmail(rs.getString("email"));
	                staffAdmin.setPhoneNumber(rs.getString("phoneNumber"));
	                staffAdmin.setBirthDate(rs.getDate("birthDate"));
	                staffAdmin.setGender(rs.getString("gender"));
	                staffAdmin.setCreatedAt(rs.getTimestamp("created_at"));
	                staffAdmin.setUpdatedAt(rs.getTimestamp("updated_at"));
	                staffAdmin.setSessionStatus(rs.getString("session_status"));
	                staffAdmin.setAdminId(rs.getObject("admin_id") != null ? rs.getInt("admin_id") : null);

	                System.out.println("Login successful for username: " + username);
	            } else {
	                System.out.println("No match found for username: " + username);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return staffAdmin;
	}


    // Create a new staff member (by Admin)
	public boolean createStaff(StaffAdmin staff) {
	    String sql = "INSERT INTO Staff (username, password, email, phoneNumber, birthDate, gender, created_at, updated_at, session_status, admin_id) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'inactive', ?)";

	    try (Connection con = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, staff.getUsername());
	        ps.setString(2, staff.getPassword());
	        ps.setString(3, staff.getEmail());
	        ps.setString(4, staff.getPhoneNumber());
	        ps.setDate(5, staff.getBirthDate());
	        ps.setString(6, staff.getGender());
	        ps.setInt(7, staff.getAdminId()); // Set admin_id based on the admin creating the account

	        int rowsInserted = ps.executeUpdate();
	        return rowsInserted > 0; // Return true if the staff account is successfully created
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}



    // Retrieve staff or admin details by ID
    public StaffAdmin getStaffById(int staffId) {
        String sql = "SELECT * FROM Staff WHERE staff_id = ?";
        StaffAdmin staffAdmin = null;

        try (Connection con = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, staffId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staffAdmin = new StaffAdmin();
                    staffAdmin.setStaffId(rs.getInt("staff_id"));
                    staffAdmin.setUsername(rs.getString("username"));
                    staffAdmin.setPassword(rs.getString("password"));
                    staffAdmin.setEmail(rs.getString("email"));
                    staffAdmin.setPhoneNumber(rs.getString("phoneNumber"));
                    staffAdmin.setBirthDate(rs.getDate("birthDate"));
                    staffAdmin.setGender(rs.getString("gender"));
                    staffAdmin.setCreatedAt(rs.getTimestamp("created_at"));
                    staffAdmin.setUpdatedAt(rs.getTimestamp("updated_at"));
                    staffAdmin.setSessionStatus(rs.getString("session_status"));
                    staffAdmin.setAdminId(rs.getObject("admin_id") != null ? rs.getInt("admin_id") : null);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffAdmin;
    }

    // Update session status (active/inactive)
    public void updateSessionStatus(int staffId, String status) {
        String sql = "UPDATE Staff SET session_status = ? WHERE staff_id = ?";
        try (Connection con = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, staffId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean updateStaff(StaffAdmin staff) {
        String sql = "UPDATE Staff SET username = ?, email = ?, phoneNumber = ?, birthDate = ?, gender = ?, updated_at = CURRENT_TIMESTAMP" +
                     (staff.getPassword() != null && !staff.getPassword().isEmpty() ? ", password = ?" : "") +
                     " WHERE staff_id = ?";

        try (Connection con = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, staff.getUsername());
            ps.setString(2, staff.getEmail());
            ps.setString(3, staff.getPhoneNumber());
            ps.setDate(4, staff.getBirthDate());
            ps.setString(5, staff.getGender());

            int paramIndex = 6;
            if (staff.getPassword() != null && !staff.getPassword().isEmpty()) {
                ps.setString(paramIndex++, staff.getPassword());
            }
            ps.setInt(paramIndex, staff.getStaffId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Return true if the profile was successfully updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public String getUsernameById(int adminId) {
        String sql = "SELECT username FROM Staff WHERE staff_id = ?";
        String username = null;

        try (Connection con = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, adminId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    username = rs.getString("username");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching username for Admin ID: " + adminId);
            e.printStackTrace();
        }
        return username != null ? username : "Unknown Admin";
    }


    public List<StaffAdmin> getAllStaffExceptAdmins() {
        List<StaffAdmin> staffList = new ArrayList<>();
        String sql = "SELECT * FROM Staff WHERE admin_id IS NOT NULL";

        try (Connection con = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                StaffAdmin staff = new StaffAdmin();
                staff.setStaffId(rs.getInt("staff_id"));
                staff.setUsername(rs.getString("username"));
                staff.setEmail(rs.getString("email"));
                staff.setPassword(rs.getString("password"));
                staff.setCreatedAt(rs.getTimestamp("created_at"));
                staff.setAdminId(rs.getInt("admin_id"));
                staffList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public List<StaffAdmin> searchStaffByUsername(String username) {
        List<StaffAdmin> staffList = new ArrayList<>();
        String sql = "SELECT * FROM Staff WHERE admin_id IS NOT NULL AND username LIKE ?";

        try (Connection con = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + username + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StaffAdmin staff = new StaffAdmin();
                    staff.setStaffId(rs.getInt("staff_id"));
                    staff.setUsername(rs.getString("username"));
                    staff.setEmail(rs.getString("email"));
                    staff.setPassword(rs.getString("password"));
                    staff.setCreatedAt(rs.getTimestamp("created_at"));
                    staff.setAdminId(rs.getInt("admin_id"));
                    staffList.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public StaffAdmin getStaffByUsername(String username) {
        String sql = "SELECT * FROM Staff WHERE username = ?";
        StaffAdmin staffAdmin = null;

        try (Connection con = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    staffAdmin = new StaffAdmin();
                    staffAdmin.setStaffId(rs.getInt("staff_id"));
                    staffAdmin.setUsername(rs.getString("username"));
                    staffAdmin.setPassword(rs.getString("password"));
                    staffAdmin.setEmail(rs.getString("email"));
                    staffAdmin.setPhoneNumber(rs.getString("phoneNumber"));
                    staffAdmin.setBirthDate(rs.getDate("birthDate"));
                    staffAdmin.setGender(rs.getString("gender"));
                    staffAdmin.setCreatedAt(rs.getTimestamp("created_at"));
                    staffAdmin.setAdminId(rs.getObject("admin_id") != null ? rs.getInt("admin_id") : null);

                    if (staffAdmin.getAdminId() != null) {
                        String adminUsername = getUsernameById(staffAdmin.getAdminId());
                        staffAdmin.setCreatedByUsername(adminUsername);
                    } else {
                        staffAdmin.setCreatedByUsername("Admin");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffAdmin;
    }

    

    

}
