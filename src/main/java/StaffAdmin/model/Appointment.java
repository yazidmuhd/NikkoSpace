package StaffAdmin.model;

import java.sql.Date;
import java.sql.Timestamp;
import customer.model.Pet;
import customer.model.Service;

public class Appointment {
    private int appId;
    private Date appDate;
    private Timestamp appTime;
    private int serviceId;
    private int petId;
    private Integer staffId;
    private String appRemark;
    private String status;

    private Service service; // Related service details
    private Pet pet;         // Related pet details
    private String petName;
    private String serviceName;

    // Getters and Setters
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public Timestamp getAppTime() {
        return appTime;
    }

    public void setAppTime(Timestamp appTime) {
        this.appTime = appTime;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getAppRemark() {
        return appRemark;
    }

    public void setAppRemark(String appRemark) {
        this.appRemark = appRemark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
    
    public void setPetName(String petName) {
        this.petName = petName;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appId=" + appId +
                ", appDate=" + appDate +
                ", appTime=" + appTime +
                ", serviceId=" + serviceId +
                ", petId=" + petId +
                ", staffId=" + (staffId != null ? staffId : "N/A") +
                ", appRemark='" + appRemark + '\'' +
                ", status='" + status + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", petName='" + petName + '\'' +
                '}';
    }

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getPetName() {
		return petName;
	}
}
