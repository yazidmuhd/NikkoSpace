package customer.dao;

import customer.connection.AzureSqlDatabaseConnection; 
import customer.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

	public int customer(Customer customer) {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    int generatedCustId = 0;

    try {
        con = AzureSqlDatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("ERROR: Database connection is null");
            return 0;
        }

        con.setAutoCommit(false); // Start transaction

        // INSERT statement for Customer
        String insertQuery = "INSERT INTO Customers (username, password, email, phoneNumber, birthDate, gender, created_at, updated_at) " +
                             "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        // Use Statement.RETURN_GENERATED_KEYS to retrieve auto-generated ID
        pstmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, customer.getUsername());
        pstmt.setString(2, customer.getPassword()); // Consider hashing before inserting
        pstmt.setString(3, customer.getEmail());
        pstmt.setString(4, customer.getPhoneNumber());
        pstmt.setDate(5, customer.getBirthDate());
        pstmt.setString(6, customer.getGender());

        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted == 0) {
            con.rollback(); // Rollback if no rows inserted
            throw new SQLException("Failed to insert customer.");
        }

        // Retrieve the generated customer ID
        rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            generatedCustId = rs.getInt(1);
            customer.setCustId(generatedCustId);
            System.out.println("Customer registered with ID: " + generatedCustId);
            con.commit(); // Commit the transaction
        } else {
            con.rollback();
            throw new SQLException("ERROR: Failed to retrieve generated customer ID.");
        }

    } catch (SQLException e) {
        try {
            if (con != null) con.rollback(); // Rollback in case of error
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
        e.printStackTrace();
        System.out.println("ERROR: SQL Exception occurred - " + e.getMessage());
        return 0;
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return generatedCustId;
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
	    return -1; 
	}

	public void updateSessionStatus(int custId, String status) {
	    String sql = "UPDATE Customers SET session_status = ? WHERE cust_id = ?";
	    try (Connection con = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, status); 
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
