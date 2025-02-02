package StaffAdmin.dao;

import StaffAdmin.connection.AzureSqlDatabaseConnection;
import StaffAdmin.model.Appointment;
import StaffAdmin.model.Result;
import customer.model.Pet;
import customer.model.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

	public int createAppointment(Appointment appointment) {
    String insertAppointmentQuery = "INSERT INTO Appointment (appDate, appTime, service_id, pet_id, appRemark) VALUES (?, ?, ?, ?, ?)";
    String insertStatusQuery = "INSERT INTO Status (statusName, app_id) VALUES ('Pending', ?)";
    String insertResultQuery = "INSERT INTO Result (tempDescription, Body, Ear, Nose, Tail, Mouth, Other, app_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = AzureSqlDatabaseConnection.getConnection()) {
        connection.setAutoCommit(false); // Start transaction

        int appId = 0;

        // Insert Appointment and Retrieve Generated ID
        try (PreparedStatement appointmentStmt = connection.prepareStatement(insertAppointmentQuery, Statement.RETURN_GENERATED_KEYS)) {
            appointmentStmt.setDate(1, appointment.getAppDate());
            appointmentStmt.setTimestamp(2, appointment.getAppTime());
            appointmentStmt.setInt(3, appointment.getServiceId());
            appointmentStmt.setInt(4, appointment.getPetId());
            appointmentStmt.setString(5, appointment.getAppRemark());

            int rowsInserted = appointmentStmt.executeUpdate();
            if (rowsInserted == 0) {
                connection.rollback();
                throw new SQLException("Failed to insert appointment.");
            }

            // Retrieve the generated app_id
            try (ResultSet rs = appointmentStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    appId = rs.getInt(1);
                } else {
                    connection.rollback();
                    throw new SQLException("Failed to retrieve generated app_id.");
                }
            }
        }

        // Insert Status
        try (PreparedStatement statusStmt = connection.prepareStatement(insertStatusQuery)) {
            statusStmt.setInt(1, appId);
            statusStmt.executeUpdate();
        }

        // Insert Default Result Values
        try (PreparedStatement resultStmt = connection.prepareStatement(insertResultQuery)) {
            resultStmt.setString(1, "-"); 
            resultStmt.setString(2, "-");
            resultStmt.setString(3, "-");
            resultStmt.setString(4, "-");
            resultStmt.setString(5, "-");
            resultStmt.setString(6, "-");
            resultStmt.setString(7, "-");
            resultStmt.setInt(8, appId);
            resultStmt.executeUpdate();
        }

        connection.commit(); // Commit all changes
        return appId; // Return the generated appointment ID

    } catch (SQLException e) {
        e.printStackTrace();
        return 0; // Return 0 on failure
    }
}


	public int createAppointmentAndGetId(Appointment appointment) {
	    String query = "INSERT INTO Appointment (appDate, appTime, service_id, pet_id, appRemark) VALUES (?, ?, ?, ?, ?)";

	    try (Connection connection = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

	        preparedStatement.setDate(1, appointment.getAppDate());
	        preparedStatement.setTimestamp(2, appointment.getAppTime());
	        preparedStatement.setInt(3, appointment.getServiceId());
	        preparedStatement.setInt(4, appointment.getPetId());
	        preparedStatement.setString(5, appointment.getAppRemark());

	        int rowsInserted = preparedStatement.executeUpdate();
	        if (rowsInserted > 0) {
	            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                    return generatedKeys.getInt(1); // Return the generated app_id
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0; // Return 0 if insertion or retrieval fails
	}



    public int getLastInsertedAppointmentId() {
        String query = "SELECT MAX(app_id) AS lastAppId FROM Appointment";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("lastAppId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if no data is found
    }

    public List<Appointment> getAppointmentsByCustomer(int customerId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT a.*, p.petName, s.serviceName, st.statusName " +
                       "FROM Appointment a " +
                       "JOIN Pet p ON a.pet_id = p.pet_id " +
                       "JOIN Service s ON a.service_id = s.service_id " +
                       "LEFT JOIN Status st ON a.app_id = st.app_id " +
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
                    appointment.setStatus(resultSet.getString("statusName")); // Status from Status table

                    // Populate Pet and Service details
                    appointment.setPetName(resultSet.getString("petName"));
                    appointment.setServiceName(resultSet.getString("serviceName"));

                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    
    public List<Appointment> getAppointmentListByCustomer(int customerId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT a.app_id, a.appDate, a.appTime, a.appRemark, p.petName, s.serviceName, st.statusName " +
                       "FROM Appointment a " +
                       "JOIN Pet p ON a.pet_id = p.pet_id " +
                       "JOIN Service s ON a.service_id = s.service_id " +
                       "LEFT JOIN Status st ON a.app_id = st.app_id " +
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
                    appointment.setAppRemark(resultSet.getString("appRemark"));

                    // Populate Pet
                    Pet pet = new Pet();
                    pet.setPetName(resultSet.getString("petName"));
                    appointment.setPet(pet); // Set the Pet object in Appointment

                    // Populate Service
                    Service service = new Service();
                    service.setServiceName(resultSet.getString("serviceName"));
                    appointment.setService(service); // Set the Service object in Appointment

                    // Populate Status
                    appointment.setStatus(resultSet.getString("statusName")); // Status from Status table

                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    
    public boolean deleteAppointmentById(int appId) {
        String deleteResultQuery = "DELETE FROM Result WHERE app_id = ?";
        String deleteStatusQuery = "DELETE FROM Status WHERE app_id = ?";
        String deleteAppointmentQuery = "DELETE FROM Appointment WHERE app_id = ?";

        try (Connection connection = AzureSqlDatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Delete from Result table
            try (PreparedStatement resultStmt = connection.prepareStatement(deleteResultQuery)) {
                resultStmt.setInt(1, appId);
                resultStmt.executeUpdate();
            }

            // Delete from Status table
            try (PreparedStatement statusStmt = connection.prepareStatement(deleteStatusQuery)) {
                statusStmt.setInt(1, appId);
                statusStmt.executeUpdate();
            }

            // Delete from Appointment table
            try (PreparedStatement appointmentStmt = connection.prepareStatement(deleteAppointmentQuery)) {
                appointmentStmt.setInt(1, appId);
                int rowsDeleted = appointmentStmt.executeUpdate();

                if (rowsDeleted > 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAppointment(Appointment appointment) {
        String query = "UPDATE Appointment SET appDate = ?, appTime = ?, service_id = ?, pet_id = ?, appRemark = ? WHERE app_id = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDate(1, appointment.getAppDate());
            preparedStatement.setTimestamp(2, appointment.getAppTime());
            preparedStatement.setInt(3, appointment.getServiceId());
            preparedStatement.setInt(4, appointment.getPetId());
            preparedStatement.setString(5, appointment.getAppRemark());
            preparedStatement.setInt(6, appointment.getAppId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public Appointment getAppointmentById(int appId) {
        String query = "SELECT a.app_id, a.appDate, a.appTime, a.appRemark, p.pet_id, p.petName, p.cust_id, " +
                       "s.service_id, s.serviceName, st.statusName " +
                       "FROM Appointment a " +
                       "JOIN Pet p ON a.pet_id = p.pet_id " +
                       "JOIN Service s ON a.service_id = s.service_id " +
                       "LEFT JOIN Status st ON a.app_id = st.app_id " +
                       "WHERE a.app_id = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, appId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppId(resultSet.getInt("app_id"));
                    appointment.setAppDate(resultSet.getDate("appDate"));
                    appointment.setAppTime(resultSet.getTimestamp("appTime"));
                    appointment.setAppRemark(resultSet.getString("appRemark"));

                    // Populate Pet details
                    Pet pet = new Pet();
                    pet.setPetID(resultSet.getInt("pet_id"));
                    pet.setPetName(resultSet.getString("petName"));
                    pet.setCustID(resultSet.getInt("cust_id"));
                    appointment.setPet(pet);

                    // Populate Service details
                    Service service = new Service();
                    service.setServiceId(resultSet.getInt("service_id"));
                    service.setServiceName(resultSet.getString("serviceName"));
                    appointment.setService(service);

                    // Set Status
                    appointment.setStatus(resultSet.getString("statusName"));

                    return appointment;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no appointment is found
    }
    
    public List<Appointment> getAppointmentsByStatus(String status) {
        List<Appointment> appointments = new ArrayList<>();
        
        String query = "SELECT a.app_id, a.appDate, a.appTime, a.appRemark, " +
                       "COALESCE(st.statusName, 'Pending') AS statusName, " +
                       "p.petName, s.serviceName " +
                       "FROM Appointment a " +
                       "JOIN Pet p ON a.pet_id = p.pet_id " +
                       "JOIN Service s ON a.service_id = s.service_id " +
                       "LEFT JOIN Status st ON a.app_id = st.app_id " +
                       "WHERE st.statusName = ?";

        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, status);

            System.out.println("Executing Query: " + query);
            System.out.println("Status Parameter: " + status); // Debugging Output

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppId(resultSet.getInt("app_id"));
                    appointment.setAppDate(resultSet.getDate("appDate"));
                    appointment.setAppTime(resultSet.getTimestamp("appTime"));
                    appointment.setAppRemark(resultSet.getString("appRemark"));
                    appointment.setStatus(resultSet.getString("statusName"));
                    appointment.setPetName(resultSet.getString("petName"));
                    appointment.setServiceName(resultSet.getString("serviceName"));
                    appointments.add(appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Appointments Found: " + appointments.size()); // Debugging Output
        return appointments;
    }

    public boolean updateAppointmentStatus(int appId, String status) {
        String updateStatusQuery = "UPDATE Status SET statusName = ? WHERE app_id = ?";
        
        try (Connection connection = AzureSqlDatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Update Status table
            try (PreparedStatement statusStmt = connection.prepareStatement(updateStatusQuery)) {
                statusStmt.setString(1, status);
                statusStmt.setInt(2, appId);
                if (statusStmt.executeUpdate() <= 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateAppointmentStatusAndResult(int appId, String status, String tempDescription, String body, String ear, String nose, String tail, String mouth, String other) {
        String updateStatusQuery = "UPDATE Status SET statusName = ? WHERE app_id = ?";
        String updateResultQuery = "UPDATE Result SET tempDescription = ?, body = ?, ear = ?, nose = ?, tail = ?, mouth = ?, other = ? WHERE app_id = ?";

        try (Connection connection = AzureSqlDatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            // Update Status
            try (PreparedStatement statusStmt = connection.prepareStatement(updateStatusQuery)) {
                statusStmt.setString(1, status);
                statusStmt.setInt(2, appId);
                if (statusStmt.executeUpdate() <= 0) {
                    connection.rollback();
                    return false;
                }
            }

            // Update Result
            try (PreparedStatement resultStmt = connection.prepareStatement(updateResultQuery)) {
                resultStmt.setString(1, tempDescription);
                resultStmt.setString(2, body);
                resultStmt.setString(3, ear);
                resultStmt.setString(4, nose);
                resultStmt.setString(5, tail);
                resultStmt.setString(6, mouth);
                resultStmt.setString(7, other);
                resultStmt.setInt(8, appId);
                if (resultStmt.executeUpdate() <= 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Result getResultByAppointmentId(int appId) {
        String query = "SELECT result_id, tempDescription, body, ear, nose, tail, mouth, other " +
                       "FROM Result WHERE app_id = ?";
        
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, appId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Result result = new Result();
                    result.setResultId(resultSet.getInt("result_id"));
                    result.setTempDescription(resultSet.getString("tempDescription"));
                    result.setBody(resultSet.getString("body"));
                    result.setEar(resultSet.getString("ear"));
                    result.setNose(resultSet.getString("nose"));
                    result.setTail(resultSet.getString("tail"));
                    result.setMouth(resultSet.getString("mouth"));
                    result.setOther(resultSet.getString("other"));
                    result.setAppId(appId);
                    return result;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

   public boolean isTimeSlotAvailable(String appDate, String appTime) {
    String query = "SELECT COUNT(*) FROM Appointment WHERE FORMAT(appDate, 'yyyy-MM-dd') = ? AND FORMAT(appTime, 'HH:mm') = ?";

    try (Connection connection = AzureSqlDatabaseConnection.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query)) {

        preparedStatement.setString(1, appDate);
        preparedStatement.setString(2, appTime);

        System.out.println("Executing Query: " + query);
        System.out.println("Date: " + appDate + " | Time: " + appTime);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                System.out.println("Count of existing appointments: " + count);
                return count == 0; // True if count is 0 (slot available)
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}



}
