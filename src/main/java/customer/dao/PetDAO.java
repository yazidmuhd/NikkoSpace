package customer.dao;

import customer.connection.AzureSqlDatabaseConnection;
import customer.model.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {
    
    // Add Pet
    public void addPet(Pet pet) throws SQLException {
        String sql = "INSERT INTO Pet (petName, petWeight, cust_id) VALUES (?, ?, ?)";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pet.getPetName());
            ps.setDouble(2, pet.getPetWeight());
            ps.setInt(3, pet.getCustID());
            ps.executeUpdate();
        }
    }

    // Update Pet
    public boolean updatePet(Pet pet) throws SQLException {
        String sql = "UPDATE PET SET PETNAME = ?, PETWEIGHT = ?, PETSTATUS = ? WHERE PET_ID = ? AND CUST_ID = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, pet.getPetName());
            ps.setDouble(2, pet.getPetWeight());
            ps.setString(3, pet.getPetStatus());
            ps.setInt(4, pet.getPetID());
            ps.setInt(5, pet.getCustID());

            return ps.executeUpdate() > 0; 
        }
    }


    // Get Pet by ID
    public Pet getPetByID(int petID) throws SQLException {
        String sql = "SELECT * FROM PET WHERE PET_ID = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, petID); 
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pet pet = new Pet();
                    pet.setPetID(rs.getInt("PET_ID")); 
                    pet.setPetName(rs.getString("PETNAME"));
                    pet.setPetStatus(rs.getString("PETSTATUS")); 
                    pet.setCustID(rs.getInt("CUST_ID")); 
                    pet.setPetWeight(rs.getDouble("PETWEIGHT"));
                    return pet; 
                }
            }
        }
        return null;
    }

    public List<Pet> getPetsByCustomerID(int custID) throws SQLException {
        List<Pet> petList = new ArrayList<>();
        String sql = "SELECT * FROM Pet WHERE cust_id = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, custID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pet pet = new Pet();
                    pet.setPetID(rs.getInt("pet_id"));
                    pet.setPetName(rs.getString("petName"));
                    pet.setPetWeight(rs.getDouble("petWeight"));
                    pet.setPetStatus(rs.getString("petStatus"));
                    pet.setCustID(rs.getInt("cust_id"));
                    petList.add(pet);
                }
            }
        }
        return petList;
    }

    public Integer getCustomerIdByPetId(int petId) {
        String sql = "SELECT cust_id FROM Pet WHERE pet_id = ?";
        try (Connection con = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, petId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cust_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no matching pet is found
    }

    public List<Pet> getPetsByCustomer(int customerId) {
        List<Pet> pets = new ArrayList<>();
        String query = "SELECT * FROM PET WHERE cust_id = ?";

        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             
            preparedStatement.setInt(1, customerId);
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Pet pet = new Pet();
                    pet.setPetID(rs.getInt("pet_id"));
                    pet.setPetName(rs.getString("petName"));
                    pet.setPetWeight(rs.getDouble("petWeight"));
                    pet.setPetStatus(rs.getString("petStatus"));
                    pet.setCustID(rs.getInt("cust_id"));
                    pets.add(pet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }
    
    public String getPetNameById(int petId) {
        String query = "SELECT petName FROM Pet WHERE pet_id = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, petId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("petName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int getPetIdByNameAndCustomer(String petName, int custId) {
        String query = "SELECT pet_id FROM Pet WHERE petName = ? AND cust_id = ?";
        try (Connection connection = AzureSqlDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, petName);
            preparedStatement.setInt(2, custId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("pet_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if no matching pet is found
    }



}
