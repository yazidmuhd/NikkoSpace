package StaffAdmin.controller;


import StaffAdmin.dao.AppointmentDAO;
import customer.dao.PetDAO;
import StaffAdmin.dao.ServiceDAO;
import StaffAdmin.dao.StatusDAO;
import StaffAdmin.dao.ResultDAO;
import StaffAdmin.model.Appointment;
import StaffAdmin.model.Service;
import StaffAdmin.model.StaffAdmin;
import StaffAdmin.model.Status;
import StaffAdmin.model.Result; // Use your custom Result class here
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
    private StatusDAO statusDAO;
    private ResultDAO resultDAO;

    public AppointmentController() {
        super();
        appointmentDAO = new AppointmentDAO();
        petDAO = new PetDAO();
        serviceDAO = new ServiceDAO();
        statusDAO = new StatusDAO();
        resultDAO = new ResultDAO();
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
            	case "showUpdateAppointmentPage":
            		showUpdateAppointmentPage(request, response);
            		break;
            	case "staffAdminViewAppointmentDetails":
            	    staffAdminViewAppointmentDetails(request, response);
            	    break;
            	case "goBackToAppointmentList":
            	    goBackToAppointmentList(request, response);
            	    break;
                case "showCreateAppointmentPage":
                    showCreateAppointmentPage(request, response);
                    break;
                case "showEditAppointmentPage":
                	showEditAppointmentPage(request, response);
                    break;
                case "getPendingAppointments":
                    getAppointmentsByStatus(request, response, "Pending");
                    break;
                case "getApprovedAppointments":
                    getAppointmentsByStatus(request, response, "Approved");
                    break;
                case "getInProgressAppointments":
                    getAppointmentsByStatus(request, response, "In Progress");
                    break;
                case "getCompletedAppointments":
                    getAppointmentsByStatus(request, response, "Completed");
                    break;
                case "getRejectedAppointments":
                    getAppointmentsByStatus(request, response, "Rejected");
                    break;
                
                case "getAppointmentList":
                    getAppointmentList(request, response);
                    break;
                case "viewAppointmentDetails":
                    viewAppointmentDetails(request, response);
                    break;
                case "checkAvailability":
                    checkAvailability(request, response);
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
                case "deleteAppointment":
                    deleteAppointment(request, response);
                    break;
                case "updateAppointment":
                    updateAppointment(request, response);
                    break;
                case "updateAppointmentStatus":
                    updateAppointmentStatus(request, response);
                    break;
                case "updateStatus":
                    updateStatus(request, response);
                    break;
                case "updateAppointmentAndResult":
                	updateAppointmentAndResult(request, response);
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
            Integer custId = (Integer) session.getAttribute("custID");

            String appDate = request.getParameter("appDate");
            String appTime = request.getParameter("appTime");
            String appRemark = request.getParameter("appRemark");
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            int petId = Integer.parseInt(request.getParameter("petID"));

            if (appDate == null || appDate.isEmpty() || appTime == null || appTime.isEmpty()) {
                request.setAttribute("errorMessage", "Appointment date and time are required.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerCreateAppointment.jsp");
                dispatcher.forward(request, response);
                return;
            }

            String appDateTime = appDate + " " + appTime + ":00";

            Appointment appointment = new Appointment();
            appointment.setAppDate(Date.valueOf(appDate));
            appointment.setAppTime(Timestamp.valueOf(appDateTime));
            appointment.setServiceId(serviceId);
            appointment.setPetId(petId);
            appointment.setAppRemark(appRemark != null ? appRemark.trim() : "");

            int appId = appointmentDAO.createAppointment(appointment);
            if (appId > 0) {
                response.sendRedirect("AppointmentController?action=getAppointmentList");
            } else {
                request.setAttribute("errorMessage", "Failed to create appointment. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerCreateAppointment.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error creating appointment", e);
        }
    }

    private void getAppointmentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("custID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Integer custId = (Integer) session.getAttribute("custID");
            List<Appointment> appointmentList = appointmentDAO.getAppointmentListByCustomer(custId);

            if (appointmentList != null && !appointmentList.isEmpty()) {
                System.out.println("Appointments retrieved: " + appointmentList.size());
            } else {
                System.out.println("No appointments found for customer ID: " + custId);
            }

            request.setAttribute("appointmentList", appointmentList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerAppointmentList.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error retrieving appointments", e);
        }
    }
    
    private void deleteAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("custID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Integer appId = Integer.parseInt(request.getParameter("appId"));

            boolean isDeleted = appointmentDAO.deleteAppointmentById(appId);

            if (isDeleted) {
                response.sendRedirect("AppointmentController?action=getAppointmentList");
            } else {
                request.setAttribute("errorMessage", "Failed to delete appointment. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerAppointmentList.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error deleting appointment", e);
        }
    }
    
    private void updateAppointment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String appIdParam = request.getParameter("appId");
            String petIdParam = request.getParameter("petId");
            String serviceIdParam = request.getParameter("serviceId");
            String appDateParam = request.getParameter("appDate");
            String appTimeParam = request.getParameter("appTime");
            String appRemark = request.getParameter("appRemark");

            if (appIdParam == null || petIdParam == null || serviceIdParam == null || appDateParam == null || appTimeParam == null) {
                throw new IllegalArgumentException("Missing required parameters.");
            }

            int appId = Integer.parseInt(appIdParam);
            int petId = Integer.parseInt(petIdParam);
            int serviceId = Integer.parseInt(serviceIdParam);
            Date appDate = Date.valueOf(appDateParam);
            Timestamp appTime = Timestamp.valueOf(appDateParam + " " + appTimeParam + ":00");

            Appointment updatedAppointment = new Appointment();
            updatedAppointment.setAppId(appId);
            updatedAppointment.setPetId(petId);
            updatedAppointment.setServiceId(serviceId);
            updatedAppointment.setAppDate(appDate);
            updatedAppointment.setAppTime(appTime);
            updatedAppointment.setAppRemark(appRemark);

            boolean isUpdated = appointmentDAO.updateAppointment(updatedAppointment);

            if (isUpdated) {
                response.sendRedirect("AppointmentController?action=getAppointmentList");
            } else {
                request.setAttribute("errorMessage", "Failed to update appointment. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerEditAppointment.jsp");
                dispatcher.forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new ServletException("Invalid or missing parameters for appointment update", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error updating appointment", e);
        }
    }
    
    private void showEditAppointmentPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("custID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        try {
            Integer custId = (Integer) session.getAttribute("custID");
            int appId = Integer.parseInt(request.getParameter("appId"));

            Appointment appointment = appointmentDAO.getAppointmentById(appId);

            if (appointment == null || appointment.getPet() == null || appointment.getPet().getCustID() != custId) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized access");
                return;
            }
            List<Pet> pets = petDAO.getPetsByCustomer(custId);
            List<Service> services = serviceDAO.getAllServices();

            request.setAttribute("appointment", appointment);
            request.setAttribute("petList", pets);
            request.setAttribute("serviceList", services);

            RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerEditAppointment.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error showing edit appointment page", e);
        }
    }
    
    private void viewAppointmentDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int appId = Integer.parseInt(request.getParameter("appId"));

            Appointment appointment = appointmentDAO.getAppointmentById(appId);
            Result result = resultDAO.getResultByAppointmentId(appId);

            if (appointment != null) {
                request.setAttribute("appointment", appointment);
                request.setAttribute("result", result);
                RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerViewAppointment.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Appointment details not found.");
                response.sendRedirect("AppointmentController?action=getAppointmentList");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error retrieving appointment details", e);
        }
    }

    

    private void updateAppointmentStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int appId = Integer.parseInt(request.getParameter("appId"));
            String status = request.getParameter("status");

            boolean isUpdated = appointmentDAO.updateAppointmentStatus(appId, status);

            if (isUpdated) {
                response.sendRedirect("AdminAppointmentController?action=listPendingAppointments");
            } else {
                request.setAttribute("errorMessage", "Failed to update status. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new ServletException("Invalid appointment ID", e);
        }
    }

    
    private void getAppointmentsByStatus(HttpServletRequest request, HttpServletResponse response, String status) throws ServletException, IOException {
        try {
            List<Appointment> appointments = appointmentDAO.getAppointmentsByStatus(status);
            request.setAttribute("appointments", appointments);

            HttpSession session = request.getSession(false);
            Integer adminId = (session != null) ? (Integer) session.getAttribute("adminId") : null;

            String targetPage;
            if (adminId == null) {
                switch (status) {
                    case "Pending":
						targetPage = "AdminPendingAppointment.jsp";
						break;
                    case "Approved":
						targetPage = "AdminApprovedAppointment.jsp";
						break;
                    case "In Progress":
						targetPage = "AdminInProgress.jsp";
						break;
                    case "Completed":
						targetPage = "AdminCompleteAppointment.jsp";
						break;
                    case "Rejected":
						targetPage = "AdminRejectAppointment.jsp";
						break;
                    default:
						targetPage = "AdminPendingAppointment.jsp";
						break;
                }
            } else {
                switch (status) {
                    case "Pending":
                    	targetPage = "StaffPendingAppointment.jsp";
                    	break;
                    case "Approved":
                    	targetPage = "StaffApprovedAppointment.jsp";
                    	break;
                    case "In Progress":
                    	targetPage = "StaffInProgress.jsp";
                    	break;
                    case "Completed":
                    	targetPage = "StaffCompleteAppointment.jsp";
                    	break;
                    case "Rejected":
                    	targetPage = "StaffRejectAppointment.jsp";
                    	break;
                    default:
                    	targetPage = "StaffPendingAppointment.jsp";
                    	break;
                }
            }

            // Forward to the appropriate page
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error fetching appointments by status", e);
        }
    }



    private void updateStatus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        
        try {
            int appId = Integer.parseInt(request.getParameter("appId"));
            String status = request.getParameter("status");

            boolean isUpdated = appointmentDAO.updateAppointmentStatus(appId, status);

            if (isUpdated) {
                response.getWriter().write("{\"success\": true, \"message\": \"Appointment updated successfully.\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"Failed to update appointment.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"Error processing request.\"}");
        }
    }
    
    private void goBackToAppointmentList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("staffId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            Integer adminId = (Integer) session.getAttribute("adminId");

            List<Appointment> appointments = appointmentDAO.getAppointmentsByStatus("Pending");
            request.setAttribute("appointments", appointments);

            String targetPage = (adminId == null) ? "AdminPendingAppointment.jsp" : "StaffPendingAppointment.jsp";

            RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error fetching appointments before going back", e);
        }
    }

    private void staffAdminViewAppointmentDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int appId = Integer.parseInt(request.getParameter("appId"));

            Appointment appointment = appointmentDAO.getAppointmentById(appId);
            Result result = resultDAO.getResultByAppointmentId(appId);

            if (appointment != null) {
                request.setAttribute("appointment", appointment);
                request.setAttribute("result", result);

                HttpSession session = request.getSession(false);
                Integer adminId = (session != null) ? (Integer) session.getAttribute("adminId") : null;

                String targetPage = (adminId == null) ? "AdminViewAppointment.jsp" : "StaffViewAppointment.jsp";

                RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Appointment details not found.");
                response.sendRedirect("AppointmentController?action=getPendingAppointments");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error retrieving appointment details", e);
        }
    }

    private void showUpdateAppointmentPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int appId = Integer.parseInt(request.getParameter("appId"));

            // Fetch appointment details from DAO
            Appointment appointment = appointmentDAO.getAppointmentById(appId);
            Result result = resultDAO.getResultByAppointmentId(appId);

            if (appointment != null) {
                request.setAttribute("appointment", appointment);
                request.setAttribute("result", result);

                HttpSession session = request.getSession(false);
                Integer adminId = (session != null) ? (Integer) session.getAttribute("adminId") : null;

                String targetPage = (adminId == null) ? "AdminUpdateAppointment.jsp" : "StaffUpdateAppointment.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Appointment not found.");
                response.sendRedirect("AppointmentController?action=getPendingAppointments");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error loading update appointment page", e);
        }
    }

    private void updateAppointmentAndResult(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int appId = Integer.parseInt(request.getParameter("appId"));
            String status = request.getParameter("status");
            
            String tempDescription = request.getParameter("tempDescription");
            String body = request.getParameter("body");
            String ear = request.getParameter("ear");
            String nose = request.getParameter("nose");
            String tail = request.getParameter("tail");
            String mouth = request.getParameter("mouth");
            String other = request.getParameter("other");

            boolean isUpdated = appointmentDAO.updateAppointmentStatusAndResult(appId, status, tempDescription, body, ear, nose, tail, mouth, other);

            if (isUpdated) {
                response.sendRedirect("AppointmentController?action=getPendingAppointments");
            } else {
                request.setAttribute("errorMessage", "Failed to update appointment.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error updating appointment and result", e);
        }
    }
    
    private void checkAvailability(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String appDate = request.getParameter("appDate");
            String appTime = request.getParameter("appTime");

            // Log received parameters
            System.out.println("Received request for checkAvailability:");
            System.out.println("appDate: " + appDate);
            System.out.println("appTime: " + appTime);

            // Validate inputs
            if (appDate == null || appDate.trim().isEmpty() || appTime == null || appTime.trim().isEmpty()) {
                System.out.println("Error: Missing date or time.");
                response.getWriter().write("{\"available\": false, \"message\": \"Invalid date or time provided.\"}");
                return;
            }

            boolean isAvailable = appointmentDAO.isTimeSlotAvailable(appDate, appTime);

            System.out.println("Availability result: " + isAvailable);

            response.getWriter().write("{\"available\": " + isAvailable + ", \"message\": \"" +
                    (isAvailable ? "This time slot is available." : "This time slot is already taken.") + "\"}");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"available\": false, \"message\": \"Error checking availability: " + e.getMessage() + "\"}");
        }
    }

}
