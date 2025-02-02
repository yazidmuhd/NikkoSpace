package customer.controller;

import customer.dao.CustomerDAO;
import customer.dao.PetDAO;
import customer.model.Customer;
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
import java.util.List;


public class CustomerController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    public CustomerController() {
        super();
        customerDAO = new CustomerDAO(); 
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
                return;
            }

            switch (action) {
                case "signup":
                    signupCustomer(request, response);
                    break;
                case "login":
                    loginCustomer(request, response);
                    break;
                case "logout":
                    logoutCustomer(request, response);
                    break;
                case "getProfile":
                    getProfile(request, response);
                    break;
                case "updateProfile": 
                    updateCustomerProfile(request, response);
                    break;
                case "loadUpdateProfile": 
                    loadUpdateProfile(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    private void signupCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phoneNumber = request.getParameter("phoneNumber");
        String birthDateStr = request.getParameter("birthDate");
        String gender = request.getParameter("gender");

        System.out.println("Received Signup Request: " + username + ", " + email);

        if (username == null || email == null || password == null || phoneNumber == null || birthDateStr == null || gender == null) {
            System.out.println("ERROR: Missing input fields.");
            request.setAttribute("errorMessage", "All fields are required.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            Date birthDate = Date.valueOf(birthDateStr);

            Customer customer = new Customer();
            customer.setUsername(username);
            customer.setEmail(email);
            customer.setPassword(password);
            customer.setPhoneNumber(phoneNumber);
            customer.setBirthDate(birthDate);
            customer.setGender(gender);

            int generatedCustomerId = customerDAO.customer(customer);

            if (generatedCustomerId > 0) { 
                System.out.println("Signup successful! Redirecting to login.jsp...");
                response.sendRedirect("login.jsp"); 
            } else {
                System.out.println("ERROR: Signup failed.");
                request.setAttribute("errorMessage", "Signup failed. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR: Exception occurred - " + e.getMessage());
            request.setAttribute("errorMessage", "An error occurred during signup. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("signup.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void loginCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            int custId = customerDAO.validateLogin(username, password); // Validate login credentials
            if (custId > 0) {
                HttpSession session = request.getSession(); 
                session.setAttribute("username", username); 
                session.setAttribute("custID", custId); // Store the custID in the session

                customerDAO.updateSessionStatus(custId, "active"); // Optionally, update session status in DB

                response.sendRedirect("CustomerController?action=getProfile"); // Redirect after login
            } else {
                request.setAttribute("errorMessage", "Invalid username or password.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
        }
    }


    private void logoutCustomer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false); // Retrieve the existing session
        if (session != null) {
            Integer custID = (Integer) session.getAttribute("custID"); // Retrieve custID
            if (custID != null) {
                customerDAO.updateSessionStatus(custID, "inactive"); // Optionally, update session status in DB
            }
            session.invalidate(); // Invalidate the session
        }
        response.sendRedirect("login.jsp"); // Redirect to login page after logout
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing.");
            return;
        }

        try {
            switch (action) {
                case "listCustomer":
                	listCustomers(request, response);
                    break;
                case "viewCustomer":
                	viewCustomer(request, response);
                    break;
                case "getProfile":
                    getProfile(request, response);
                    break;
                case "logout":
                    logoutCustomer(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
        }
    }
    
    private void getProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);

        if (session != null) {
            Integer custId = (Integer) session.getAttribute("custID");
            System.out.println(custId);

            if (custId != null) {
                Customer customer = customerDAO.getCustomerById(custId);

                if (customer != null) {
                    request.setAttribute("username", customer.getUsername());
                    request.setAttribute("email", customer.getEmail());
                    request.setAttribute("phoneNumber", customer.getPhoneNumber());
                    request.setAttribute("birthDate", customer.getBirthDate());
                    request.setAttribute("gender", customer.getGender());

                    RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("login.jsp");
                }
            } else {
                response.sendRedirect("login.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
    private void updateCustomerProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            Integer custId = (Integer) session.getAttribute("custID");
            if (custId == null) {
                String custIdParam = request.getParameter("custID");
                if (custIdParam == null || custIdParam.isEmpty()) {
                    throw new IllegalArgumentException("Customer ID is missing in the request.");
                }
                custId = Integer.parseInt(custIdParam);
            }

            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String birthDateStr = request.getParameter("birthDate");
            String gender = request.getParameter("gender");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");

            if (password != null && !password.isEmpty() && !password.equals(confirmPassword)) {
                request.setAttribute("error", "Passwords do not match.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("updateProfile.jsp");
                dispatcher.forward(request, response);
                return;
            }

            Date birthDate = Date.valueOf(birthDateStr);

            Customer customer = new Customer();
            customer.setCustId(custId);
            customer.setUsername(username);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setBirthDate(birthDate);
            customer.setGender(gender);
            if (password != null && !password.isEmpty()) {
                customer.setPassword(password);
            }
            boolean isUpdated = customerDAO.updateCustomerDetails(customer);
            if (isUpdated) {
                Customer updatedCustomer = customerDAO.getCustomerById(custId);

                request.setAttribute("user_Id", updatedCustomer.getCustId());
                request.setAttribute("username", updatedCustomer.getUsername());
                request.setAttribute("email", updatedCustomer.getEmail());
                request.setAttribute("phoneNumber", updatedCustomer.getPhoneNumber());
                request.setAttribute("birthDate", updatedCustomer.getBirthDate());
                request.setAttribute("gender", updatedCustomer.getGender());
                request.setAttribute("updatedAt", updatedCustomer.getUpdatedAt());

                RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("error", "Failed to update profile. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("updateProfile.jsp");
                dispatcher.forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Invalid input: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("updateProfile.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating your profile.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("updateProfile.jsp");
            dispatcher.forward(request, response);
        }
    }

    	
    private void loadUpdateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
        if (session != null) {
            Integer custId = (Integer) session.getAttribute("custID");

            if (custId != null) {
                Customer customer = customerDAO.getCustomerById(custId);

                if (customer != null) {
                    request.setAttribute("user_Id", customer.getCustId());
                    request.setAttribute("username", customer.getUsername());
                    request.setAttribute("email", customer.getEmail());
                    request.setAttribute("phoneNumber", customer.getPhoneNumber());
                    request.setAttribute("birthDate", customer.getBirthDate());
                    request.setAttribute("gender", customer.getGender());
                    request.setAttribute("updatedAt", customer.getUpdatedAt());

                    RequestDispatcher dispatcher = request.getRequestDispatcher("updateProfile.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("profile.jsp");
                }
            } else {
                response.sendRedirect("login.jsp");
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
    
    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 try {
             HttpSession session = request.getSession(false);
             Integer adminId = (Integer) session.getAttribute("adminId");
             List<Customer> customers = customerDAO.getAllCustomers();

             request.setAttribute("customerList", customers);

             String destination = (adminId == null) ? "AdminViewListCustomer.jsp" : "StaffViewListCustomer.jsp";
             RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
             dispatcher.forward(request, response);

         } catch (Exception e) {
             e.printStackTrace();
             request.setAttribute("errorMessage", "Unable to retrieve customers. Please try again later.");
             String fallback = "StaffViewListCustomer.jsp";
             RequestDispatcher dispatcher = request.getRequestDispatcher(fallback);
             dispatcher.forward(request, response);
         }
    }

    private void viewCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("custId"));
        try {
            Customer customer = customerDAO.getCustomerById(customerId);

            PetDAO petDAO = new PetDAO();
            List<Pet> petList = petDAO.getPetsByCustomerID(customerId);

            if (customer != null) {
                request.setAttribute("customer", customer);
                request.setAttribute("petList", petList);

                HttpSession session = request.getSession(false);
                Integer adminId = (Integer) session.getAttribute("adminId");
                String destination = (adminId == null) ? "AdminViewCustomer.jsp" : "StaffViewCustomer.jsp";

                RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Customer not found.");
                String fallback = (Integer) request.getSession(false).getAttribute("adminId") == null
                        ? "AdminViewListCustomer.jsp"
                        : "StaffViewListCustomer.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(fallback);
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to retrieve customer details. Please try again later.");
            String fallback = (Integer) request.getSession(false).getAttribute("adminId") == null
                    ? "AdminViewListCustomer.jsp"
                    : "StaffViewListCustomer.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(fallback);
            dispatcher.forward(request, response);
        }
    }

    
}