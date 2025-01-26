package customer.controller;

import customer.dao.ServiceDAO;
import customer.model.Service;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class CustomerServiceController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceDAO serviceDAO;

    public CustomerServiceController() {
        super();
        serviceDAO = new ServiceDAO(); // Initialize DAO for customer
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // Default action is to list services
        if (action == null || action.isEmpty()) {
            listServices(request, response);
        } else {
            switch (action) {
                case "listServices":
                    listServices(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
                    break;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listServices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Fetch services from DAO
            List<Service> services = serviceDAO.getAllServices();

            // Set the service list as a request attribute
            request.setAttribute("serviceList", services);

            // Forward the request to the CustomerServiceList.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerServiceList.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();

            // Set an error message and forward to an error page or provide a fallback
            request.setAttribute("errorMessage", "An error occurred while retrieving the services. Please try again later.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerServiceList.jsp");
            dispatcher.forward(request, response);
        }
    }
}
