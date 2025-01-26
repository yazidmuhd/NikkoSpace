package StaffAdmin.controller;

import StaffAdmin.dao.StaffAdminDAO;
import StaffAdmin.model.StaffAdmin;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class StaffAdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StaffAdminDAO staffAdminDAO;

    public StaffAdminController() {
        super();
        staffAdminDAO = new StaffAdminDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
            return;
        }

        try {
            switch (action) {
                case "login":
                    login(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
                case "updateProfile":
                    updateProfile(request, response);
                    break;
                case "getProfile":
                    getProfile(request, response);
                    break;
                case "loadUpdateProfile":
                    loadUpdateProfile(request, response);
                    break;
                case "createStaff":
                	createStaff(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("StaffAdminLogin.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            response.sendRedirect("StaffAdminLogin.jsp");
            return;
        }

        try {
            switch (action) {
                case "logout":
                    logout(request, response);
                    break;
                case "getProfile":
                    getProfile(request, response);
                    break;
                case "loadUpdateProfile":
                    loadUpdateProfile(request, response);
                    break;
                case "loadCreateStaffPage":
                    loadCreateStaffPage(request, response);
                    break;
                case "listStaff":
                    listStaff(request, response);
                    break;
                case "searchStaff":
                    searchStaff(request, response);
                    break;
                case "viewStaff":
                    viewStaffDetails(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        StaffAdmin staffAdmin = staffAdminDAO.validateLogin(username, password);

        if (staffAdmin != null) {
            HttpSession session = request.getSession();

            session.setAttribute("staffId", staffAdmin.getStaffId());
            session.setAttribute("username", staffAdmin.getUsername());
            session.setAttribute("email", staffAdmin.getEmail());
            session.setAttribute("phoneNumber", staffAdmin.getPhoneNumber());
            session.setAttribute("birthDate", staffAdmin.getBirthDate());
            session.setAttribute("gender", staffAdmin.getGender());
            session.setAttribute("sessionStatus", "active");
            session.setAttribute("adminId", staffAdmin.getAdminId());

            staffAdminDAO.updateSessionStatus(staffAdmin.getStaffId(), "active");

            if (staffAdmin.getAdminId() == null) {
                response.sendRedirect("AdminProfile.jsp");
            } else {
                response.sendRedirect("StaffProfile.jsp");
            }
        } else {
            request.setAttribute("errorMessage", "Invalid username or password.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("StaffAdminLogin.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Integer staffId = (Integer) session.getAttribute("staffId");
            if (staffId != null) {
                staffAdminDAO.updateSessionStatus(staffId, "inactive");
            }
            session.invalidate();
        }
        response.sendRedirect("StaffAdminLogin.jsp");
    }

    private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staffId") == null) {
            response.sendRedirect("StaffAdminLogin.jsp");
            return;
        }

        int staffId = (Integer) session.getAttribute("staffId");
        String username = request.getParameter("username").trim();
        String email = request.getParameter("email").trim();
        String phoneNumber = request.getParameter("phoneNumber").trim();
        Date birthDate = Date.valueOf(request.getParameter("birthDate"));
        String gender = request.getParameter("gender").trim();
        String password = request.getParameter("password").trim();

        StaffAdmin staffAdmin = new StaffAdmin();
        staffAdmin.setStaffId(staffId);
        staffAdmin.setUsername(username);
        staffAdmin.setEmail(email);
        staffAdmin.setPhoneNumber(phoneNumber);
        staffAdmin.setBirthDate(birthDate);
        staffAdmin.setGender(gender);

        if (!password.isEmpty()) {
            staffAdmin.setPassword(password);
        }

        boolean isUpdated = staffAdminDAO.updateStaff(staffAdmin);

        if (isUpdated) {
            session.setAttribute("username", username);
            session.setAttribute("email", email);
            session.setAttribute("phoneNumber", phoneNumber);
            session.setAttribute("birthDate", birthDate);
            session.setAttribute("gender", gender);

            if (staffAdmin.getAdminId() == null) {
                response.sendRedirect("AdminProfile.jsp");
            } else {
                response.sendRedirect("StaffProfile.jsp");
            }
        } else {
            request.setAttribute("errorMessage", "Failed to update profile. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("AdminProfileUpdate.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void getProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staffId") == null) {
            response.sendRedirect("StaffAdminLogin.jsp");
            return;
        }

        int staffId = (Integer) session.getAttribute("staffId");
        StaffAdmin staffAdmin = staffAdminDAO.getStaffById(staffId);

        if (staffAdmin != null) {
            String adminUsername = null;
            if (staffAdmin.getAdminId() != null) {
                adminUsername = staffAdminDAO.getUsernameById(staffAdmin.getAdminId());
                System.out.println("Admin Username Retrieved: " + adminUsername);
            } else {
                System.out.println("This account was not created by an admin (Admin ID is null).");
            }

            request.setAttribute("adminUsername", adminUsername != null ? adminUsername : "N/A");
            request.setAttribute("username", staffAdmin.getUsername());
            request.setAttribute("email", staffAdmin.getEmail());
            request.setAttribute("phoneNumber", staffAdmin.getPhoneNumber());
            request.setAttribute("birthDate", staffAdmin.getBirthDate());
            request.setAttribute("gender", staffAdmin.getGender());

            RequestDispatcher dispatcher = staffAdmin.getAdminId() == null
                    ? request.getRequestDispatcher("AdminProfile.jsp")
                    : request.getRequestDispatcher("StaffProfile.jsp");
            dispatcher.forward(request, response);
        } else {
            System.out.println("No StaffAdmin found for Staff ID: " + staffId);
            response.sendRedirect("StaffAdminLogin.jsp");
        }
    }




    private void loadUpdateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("staffId") == null) {
            response.sendRedirect("StaffAdminLogin.jsp");
            return;
        }

        int staffId = (Integer) session.getAttribute("staffId");
        StaffAdmin staffAdmin = staffAdminDAO.getStaffById(staffId);

        if (staffAdmin != null) {
            request.setAttribute("staffId", staffAdmin.getStaffId());
            request.setAttribute("username", staffAdmin.getUsername());
            request.setAttribute("email", staffAdmin.getEmail());
            request.setAttribute("phoneNumber", staffAdmin.getPhoneNumber());
            request.setAttribute("birthDate", staffAdmin.getBirthDate());
            request.setAttribute("gender", staffAdmin.getGender());

            if (staffAdmin.getAdminId() == null) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("AdminProfileUpdate.jsp");
                dispatcher.forward(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("StaffProfileUpdate.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            response.sendRedirect("StaffAdminLogin.jsp");
        }
    }
    
    private void createStaff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("staffId") == null || 
            session.getAttribute("adminId") != null) {
            
            response.sendRedirect("StaffAdminLogin.jsp");
            return;
        }

        int adminStaffId = (Integer) session.getAttribute("staffId");

        String username = request.getParameter("staffName").trim();
        String email = request.getParameter("staffEmail").trim();
        String phoneNumber = request.getParameter("staffPhone").trim();
        String password = request.getParameter("staffPassword").trim();
        Date birthDate = Date.valueOf(request.getParameter("staffBirthDate"));
        String gender = request.getParameter("staffGender").trim();

        StaffAdmin staffAdmin = new StaffAdmin();
        staffAdmin.setUsername(username);
        staffAdmin.setPassword(password);
        staffAdmin.setEmail(email);
        staffAdmin.setPhoneNumber(phoneNumber);
        staffAdmin.setBirthDate(birthDate);
        staffAdmin.setGender(gender);
        staffAdmin.setAdminId(adminStaffId); 

        boolean isCreated = staffAdminDAO.createStaff(staffAdmin);

        if (isCreated) {
            request.setAttribute("successMessage", "Staff account created successfully!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("AdminViewListStaff.jsp");
            dispatcher.forward(request, response);
        } else {
            // Handle error
            request.setAttribute("errorMessage", "Failed to create staff account. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("AdminCreateStaff.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void loadCreateStaffPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
 
        if (session == null || session.getAttribute("staffId") == null || 
            session.getAttribute("adminId") != null) {
        	
            response.sendRedirect("StaffAdminLogin.jsp");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("AdminCreateStaff.jsp");
        dispatcher.forward(request, response);
    }

    private void listStaff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<StaffAdmin> staffList = staffAdminDAO.getAllStaffExceptAdmins();

            for (StaffAdmin staff : staffList) {
                if (staff.getAdminId() != null) {
                    String adminUsername = staffAdminDAO.getUsernameById(staff.getAdminId());
                    staff.setCreatedByUsername(adminUsername);
                } else {
                    staff.setCreatedByUsername("Admin");
                }
            }

            request.setAttribute("staffList", staffList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("AdminViewListStaff.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving staff list.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("AdminViewListStaff.jsp");
            dispatcher.forward(request, response);
        }
    }


    private void searchStaff(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchKeyword = request.getParameter("search");

        try {
            List<StaffAdmin> staffList = staffAdminDAO.searchStaffByUsername(searchKeyword);

            for (StaffAdmin staff : staffList) {
                if (staff.getAdminId() != null) {
                    String adminUsername = staffAdminDAO.getUsernameById(staff.getAdminId());
                    staff.setCreatedByUsername(adminUsername);
                } else {
                    staff.setCreatedByUsername("Admin");
                }
            }

            request.setAttribute("staffList", staffList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("AdminViewListStaff.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while searching for staff.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("AdminViewListStaff.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    private void viewStaffDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        try {
            StaffAdmin staff = staffAdminDAO.getStaffByUsername(username);
            if (staff != null) {
                request.setAttribute("staff", staff);
                RequestDispatcher dispatcher = request.getRequestDispatcher("AdminViewDetailStaff.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Staff not found.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("AdminViewListStaff.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to retrieve staff details. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("AdminViewListStaff.jsp");
            dispatcher.forward(request, response);
        }
    }
}
