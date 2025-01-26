package StaffAdmin.controller;

import StaffAdmin.dao.AppointmentDAO;
import customer.dao.PetDAO;
import StaffAdmin.dao.ServiceDAO;
import StaffAdmin.model.Appointment;
import StaffAdmin.model.Service;
import customer.model.Pet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class AppointmentController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AppointmentDAO appointmentDAO;
    private PetDAO petDAO;
    private ServiceDAO serviceDAO;

    public AppointmentController() {
        super();
        appointmentDAO = new AppointmentDAO();
        petDAO = new PetDAO();
        serviceDAO = new ServiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
            }

            switch (action) {
                case "showCreateAppointmentPage":
                    showCreateAppointmentPage(request, response);
                    break;
                case "getAppointmentList":
                	getAppointmentList(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                return;
            }

            switch (action) {
                case "createAppointment":
                    createAppointment(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    private void showCreateAppointmentPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("custID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Integer custId = (Integer) session.getAttribute("custID");
            List<Pet> pets = petDAO.getPetsByCustomer(custId);
            List<Service> services = serviceDAO.getAllServices();

            System.out.println("Pets retrieved: " + (pets != null ? pets.size() : "null"));
            System.out.println("Services retrieved: " + (services != null ? services.size() : "null"));

            request.setAttribute("petList", pets);
            request.setAttribute("serviceList", services);
            RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerCreateAppointment.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new ServletException("Error loading create appointment page", e);
        }
    }


    private void createAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("custID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Retrieve customer ID from session
            Integer custId = (Integer) session.getAttribute("custID");

            // Get form inputs
            String petName = request.getParameter("petName"); // Pet name selected by the user
            String serviceName = request.getParameter("serviceName"); // Service name selected by the user
            String appDate = request.getParameter("appDate");
            String appTime = request.getParameter("appTime");
            String appRemark = request.getParameter("appRemark");

            // Validate date and time
            if (appDate == null || appDate.isEmpty() || appTime == null || appTime.isEmpty()) {
                request.setAttribute("errorMessage", "Appointment date and time are required.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerCreateAppointment.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Get pet_id from petName
            int petId = petDAO.getPetIdByNameAndCustomer(petName, custId);
            if (petId == 0) {
                request.setAttribute("errorMessage", "Invalid pet selection.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerCreateAppointment.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Get service_id from serviceName
            int serviceId = serviceDAO.getServiceIdByName(serviceName);
            if (serviceId == 0) {
                request.setAttribute("errorMessage", "Invalid service selection.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerCreateAppointment.jsp");
                dispatcher.forward(request, response);
                return;
            }

            // Create the Appointment object
            Appointment appointment = new Appointment();
            appointment.setPetId(petId);
            appointment.setServiceId(serviceId);
            appointment.setAppDate(Date.valueOf(appDate)); // Convert string to SQL Date
            appointment.setAppTime(Timestamp.valueOf(appDate + " " + appTime + ":00")); // Combine date and time
            appointment.setAppRemark(appRemark != null ? appRemark.trim() : ""); // Trim remarks or set empty
            appointment.setStatus("Pending"); // Default status

            // Call DAO to create the appointment
            boolean isCreated = appointmentDAO.createAppointment(appointment);

            if (isCreated) {
                response.sendRedirect("AppointmentController?action=getAppointmentList");
            } else {
                request.setAttribute("errorMessage", "Failed to create appointment. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerCreateAppointment.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error creating appointment", e);
        }
    }

    
    private void getAppointmentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if the customer is logged in
        if (session == null || session.getAttribute("custID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Integer custId = (Integer) session.getAttribute("custID");

            List<Appointment> appointmentList = appointmentDAO.getAppointmentsByCustomer(custId);

            request.setAttribute("appointmentList", appointmentList);

            RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerAppointmentList.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving appointments", e);
        }
    }


}
