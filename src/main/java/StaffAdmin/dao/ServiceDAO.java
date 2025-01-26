package StaffAdmin.dao;

import StaffAdmin.model.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import StaffAdmin.connection.AzureSqlDatabaseConnection;

public class ServiceDAO {

    // Create a new service
	public boolean createService(Service service) {
	    String sql = "INSERT INTO SERVICE (SERVICENAME, SERVICEPRICE, SERVICEDESCRIPTION) VALUES (?, ?, ?)";
	    try (Connection con = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, service.getServiceName());
	        ps.setBigDecimal(2, service.getServicePrice());
	        ps.setString(3, service.getServiceDescription());

	        int rowsInserted = ps.executeUpdate();
	        return rowsInserted > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}


    // Update an existing service
    public boolean updateService(Service service) {
        String sql = "UPDATE SERVICE SET SERVICENAME = ?, SERVICEPRICE = ?, SERVICEDESCRIPTION = ? WHERE SERVICE_ID = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, service.getServiceName());
            preparedStatement.setBigDecimal(2, service.getServicePrice());
            preparedStatement.setString(3, service.getServiceDescription());
            preparedStatement.setInt(4, service.getServiceId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get a service by ID
    public Service getServiceById(int serviceId) {
        String sql = "SELECT * FROM SERVICE WHERE SERVICE_ID = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, serviceId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Service service = new Service();
                    service.setServiceId(resultSet.getInt("SERVICE_ID"));
                    service.setServiceName(resultSet.getString("SERVICENAME"));
                    service.setServicePrice(resultSet.getBigDecimal("SERVICEPRICE"));
                    service.setServiceDescription(resultSet.getString("SERVICEDESCRIPTION"));
                    return service;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all services
    public List<Service> getAllServices() {
        String sql = "SELECT * FROM SERVICE";
        List<Service> services = new ArrayList<>();
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Service service = new Service();
                service.setServiceId(resultSet.getInt("SERVICE_ID"));
                service.setServiceName(resultSet.getString("SERVICENAME"));
                service.setServicePrice(resultSet.getBigDecimal("SERVICEPRICE"));
                service.setServiceDescription(resultSet.getString("SERVICEDESCRIPTION"));
                services.add(service);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    // Delete a service (optional if needed)
    public boolean deleteService(int serviceId) {
        String sql = "DELETE FROM SERVICE WHERE SERVICE_ID = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, serviceId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getServiceIdByName(String serviceName) {
        String query = "SELECT service_id FROM Service WHERE serviceName = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, serviceName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("service_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if no matching service is found
    }

}
