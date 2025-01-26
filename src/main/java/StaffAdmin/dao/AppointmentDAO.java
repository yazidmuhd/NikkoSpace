package StaffAdmin.dao;

import StaffAdmin.connection.AzureSqlDatabaseConnection;
import StaffAdmin.model.Appointment;
import StaffAdmin.model.Service;
import customer.model.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AppointmentDAO {
	public boolean createAppointment(Appointment appointment) {
	    String query = "INSERT INTO Appointment (appDate, appTime, service_id, pet_id, appRemark) " +
	                   "VALUES (?, ?, ?, ?, ?)";

	    try (Connection connection = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

	        preparedStatement.setDate(1, appointment.getAppDate());
	        preparedStatement.setTimestamp(2, appointment.getAppTime());
	        preparedStatement.setInt(3, appointment.getServiceId());
	        preparedStatement.setInt(4, appointment.getPetId());
	        preparedStatement.setString(5, appointment.getAppRemark());

	        int rowsInserted = preparedStatement.executeUpdate();
	        return rowsInserted > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

    
    public Appointment getAppointmentById(int appId) throws SQLException {
        String query = "SELECT a.*, s.* FROM Appointment a " +
                       "JOIN Service s ON a.service_id = s.service_id " +
                       "WHERE a.app_id = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, appId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppId(rs.getInt("app_id"));
                    appointment.setAppDate(rs.getDate("appdate"));
                    appointment.setAppTime(rs.getTimestamp("apptime"));
                    appointment.setServiceId(rs.getInt("service_id"));
                    appointment.setPetId(rs.getInt("pet_id"));
                    appointment.setStaffId(rs.getObject("staff_id") != null ? rs.getInt("staff_id") : null);
                    appointment.setAppRemark(rs.getString("appremark"));
                    appointment.setStatus(rs.getString("status"));

                    // Populate the Service object
                    Service service = new Service();
                    service.setServiceId(rs.getInt("service_id"));
                    service.setServiceName(rs.getString("servicename"));
                    service.setServicePrice(rs.getBigDecimal("serviceprice"));
                    service.setServiceDescription(rs.getString("servicedescription"));
                    appointment.setService(service);

                    return appointment;
                }
            }
        }
        return null;
    }
    
    public List<Appointment> getAppointmentsByCustomerId(int customerId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT a.*, p.petName, s.serviceName " +
                       "FROM Appointment a " +
                       "JOIN Pet p ON a.pet_id = p.pet_id " +
                       "JOIN Service s ON a.service_id = s.service_id " +
                       "WHERE p.cust_id = ?";

        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppId(resultSet.getInt("app_id"));
                    appointment.setAppDate(resultSet.getDate("appDate"));
                    appointment.setAppTime(resultSet.getTimestamp("appTime"));
                    appointment.setServiceId(resultSet.getInt("service_id"));
                    appointment.setPetId(resultSet.getInt("pet_id"));
                    appointment.setAppRemark(resultSet.getString("appRemark"));
                    appointment.setStatus(resultSet.getString("status"));

                    // Populate Pet details
                    Pet pet = new Pet();
                    pet.setPetID(resultSet.getInt("pet_id"));
                    pet.setPetName(resultSet.getString("petName"));
                    appointment.setPet(pet);

                    // Populate Service details
                    Service service = new Service();
                    service.setServiceId(resultSet.getInt("service_id"));
                    service.setServiceName(resultSet.getString("serviceName"));
                    appointment.setService(service);

                    appointments.add(appointment);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }
    
    public List<Appointment> getAppointmentsByCustomer(int customerId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT a.*, p.petName, s.serviceName " +
                       "FROM Appointment a " +
                       "JOIN Pet p ON a.pet_id = p.pet_id " +
                       "JOIN Service s ON a.service_id = s.service_id " +
                       "WHERE p.cust_id = ?";

        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppId(resultSet.getInt("app_id"));
                    appointment.setAppDate(resultSet.getDate("appDate"));
                    appointment.setAppTime(resultSet.getTimestamp("appTime"));
                    appointment.setServiceId(resultSet.getInt("service_id"));
                    appointment.setPetId(resultSet.getInt("pet_id"));
                    appointment.setAppRemark(resultSet.getString("appRemark"));
                    appointment.setStatus(resultSet.getString("status"));
                    appointment.setPetName(resultSet.getString("petName")); // Populate petName

                    // Populate Service details
                    Service service = new Service();
                    service.setServiceId(resultSet.getInt("service_id"));
                    service.setServiceName(resultSet.getString("serviceName"));
                    appointment.setService(service);

                    appointments.add(appointment);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }
    
    
    

}
