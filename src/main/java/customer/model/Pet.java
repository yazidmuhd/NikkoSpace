package customer.model;

public class Pet {
    private int petID;
    private String petName;
    private double petWeight;
    private byte[] petPicture; // Blob stored as byte array
    private String petStatus;
    private int custID;

    // Getters and Setters
    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public double getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(double petWeight) {
        this.petWeight = petWeight;
    }

    public byte[] getPetPicture() {
        return petPicture;
    }

    public void setPetPicture(byte[] petPicture) {
        this.petPicture = petPicture;
    }

    public String getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(String petStatus) {
        this.petStatus = petStatus;
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }



    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petID +
                ", petName='" + petName + '\'' +
                ", petWeight=" + petWeight +
                ", petPicture=" + (petPicture != null ? "Exists" : "No Picture") +
                ", petStatus='" + petStatus + '\'' +
                ", custId=" + custID +
                '}';
    }
}
