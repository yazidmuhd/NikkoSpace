package StaffAdmin.dao;

import StaffAdmin.connection.AzureSqlDatabaseConnection;
import StaffAdmin.model.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ResultDAO {

	public boolean createPlaceholderResult(int appId) {
	    String insertResultQuery = "INSERT INTO Result (tempDescription, body, ear, nose, tail, mouth, other, app_id) VALUES ('', '', '', '', '', '', '', ?)";

	    try (Connection connection = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement resultStmt = connection.prepareStatement(insertResultQuery)) {

	        resultStmt.setInt(1, appId);

	        return resultStmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public Result getResultByAppointmentId(int appId) {
        String query = "SELECT * FROM Result WHERE app_id = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, appId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Result result = new Result();
                    result.setTempDescription(resultSet.getString("tempDescription"));
                    result.setBody(resultSet.getString("Body"));
                    result.setEar(resultSet.getString("Ear"));
                    result.setNose(resultSet.getString("Nose"));
                    result.setTail(resultSet.getString("Tail"));
                    result.setMouth(resultSet.getString("Mouth"));
                    result.setOther(resultSet.getString("Other"));
                    return result;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
