package customer.dao;

import customer.connection.AzureSqlDatabaseConnection; 
import customer.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

	public void customer(Customer customer) {
	    Connection con = null;
	    PreparedStatement pstmt = null;

	    try {
	        // Use AzureSqlDatabaseConnection to get the connection
	        con = AzureSqlDatabaseConnection.getConnection();

	        if (con == null) {
	            throw new RuntimeException("Database connection is null");
	        }

	        // Exclude cust_id and session_status as they are auto-incremented and default, respectively
	        String sql = "INSERT INTO Customers (username, password, email, phoneNumber, birthDate, gender, created_at, updated_at) " +
	                     "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
	        pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	        pstmt.setString(1, customer.getUsername());
	        pstmt.setString(2, customer.getPassword());
	        pstmt.setString(3, customer.getEmail());
	        pstmt.setString(4, customer.getPhoneNumber());
	        pstmt.setDate(5, customer.getBirthDate());
	        pstmt.setString(6, customer.getGender());

	        int rowsInserted = pstmt.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("A new customer was successfully inserted.");

	            // Retrieve the generated cust_id
	            ResultSet rs = pstmt.getGeneratedKeys();
	            if (rs.next()) {
	                int generatedId = rs.getInt(1);
	                customer.setCustId(generatedId); // Set the generated ID back to the Customer object
	                System.out.println("Generated Customer ID: " + generatedId);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (pstmt != null) pstmt.close();
	            if (con != null) con.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}



	public int validateLogin(String username, String password) {
	    String sql = "SELECT cust_id FROM Customers WHERE username = ? AND password = ?";
	    try (Connection con = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, username);
	        ps.setString(2, password);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("cust_id");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; // Return -1 for invalid login
	}

	public void updateSessionStatus(int custId, String status) {
	    String sql = "UPDATE Customers SET session_status = ? WHERE cust_id = ?";
	    try (Connection con = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, status); // Set status jadi 'inactive'
	        ps.setInt(2, custId); 
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers";

        try (Connection con = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustId(rs.getInt("cust_id"));
                customer.setUsername(rs.getString("username"));
                customer.setPassword(rs.getString("password"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phoneNumber"));
                customer.setBirthDate(rs.getDate("birthDate"));
                customer.setGender(rs.getString("gender"));
                customer.setCreatedAt(rs.getTimestamp("created_at"));
                customer.setUpdatedAt(rs.getTimestamp("updated_at"));
                customer.setSessionStatus(rs.getString("session_status"));
                customers.add(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
	
	public Customer getCustomerById(int custId) {
	    String sql = "SELECT * FROM Customers WHERE cust_id = ?";
	    Customer customer = null;

	    try (Connection con = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, custId);
	        System.out.println("Executing SQL: " + sql + " with custId=" + custId);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                customer = new Customer();
	                customer.setCustId(rs.getInt("cust_id"));
	                customer.setUsername(rs.getString("username"));
	                customer.setEmail(rs.getString("email"));
	                customer.setPhoneNumber(rs.getString("phoneNumber"));
	                customer.setBirthDate(rs.getDate("birthDate"));
	                customer.setGender(rs.getString("gender"));
	                customer.setCreatedAt(rs.getTimestamp("created_at"));
	                customer.setUpdatedAt(rs.getTimestamp("updated_at"));
	                System.out.println("Customer found: " + customer.getUsername());
	            } else {
	                System.out.println("No customer found with custId=" + custId);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return customer;
	}

	
	public boolean updateCustomerDetails(Customer customer) {
	    String sql = "UPDATE Customers SET username = ?, email = ?, phoneNumber = ?, birthDate = ?, gender = ?, updated_at = CURRENT_TIMESTAMP" +
	                 (customer.getPassword() != null ? ", password = ?" : "") + " WHERE cust_id = ?";
	    try (Connection con = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, customer.getUsername());
	        ps.setString(2, customer.getEmail());
	        ps.setString(3, customer.getPhoneNumber());
	        ps.setDate(4, customer.getBirthDate());
	        ps.setString(5, customer.getGender());

	        int paramIndex = 6;
	        if (customer.getPassword() != null) {
	            ps.setString(paramIndex++, customer.getPassword());
	        }
	        ps.setInt(paramIndex, customer.getCustId());

	        int rowsUpdated = ps.executeUpdate();
	        return rowsUpdated > 0; // Return true if the update was successful
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	
	

}
