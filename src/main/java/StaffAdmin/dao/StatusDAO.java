package StaffAdmin.dao;

import StaffAdmin.connection.AzureSqlDatabaseConnection;
import StaffAdmin.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StatusDAO {

	public boolean createStatus(Status status) {
	    String insertStatusQuery = "INSERT INTO Status (statusName, app_id) VALUES (?, ?)";

	    try (Connection connection = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement statusStmt = connection.prepareStatement(insertStatusQuery)) {

	        statusStmt.setString(1, status.getStatusName());
	        statusStmt.setInt(2, status.getAppId());

	        return statusStmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}



}
