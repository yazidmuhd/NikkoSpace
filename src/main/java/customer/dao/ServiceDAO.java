package customer.dao;

import customer.connection.AzureSqlDatabaseConnection;
import customer.model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {

    // Retrieve all services from the database
	public List<Service> getAllServices() {
	    List<Service> serviceList = new ArrayList<>();
	    String sql = "SELECT * FROM Service";

	    try (Connection con = AzureSqlDatabaseConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            Service service = new Service();
	            service.setServiceId(rs.getInt("service_id"));
	            service.setServiceName(rs.getString("serviceName"));
	            service.setServicePrice(rs.getBigDecimal("servicePrice"));
	            service.setServiceDescription(rs.getString("serviceDescription"));

	            serviceList.add(service);
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Log exception details
	    }

	    return serviceList;
	}

}
