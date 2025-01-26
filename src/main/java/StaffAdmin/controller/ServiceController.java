package StaffAdmin.controller;

import StaffAdmin.dao.ServiceDAO;
import StaffAdmin.model.Service;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ServiceController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServiceDAO serviceDAO;

    public ServiceController() {
        super();
        serviceDAO = new ServiceDAO(); // Initialize DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if (action == null || action.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
                return;
            }

            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("staffId") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            boolean isAdmin = isAdmin(session);

            switch (action) {
                case "listServices":
                    listServices(request, response, isAdmin);
                    break;
                case "createServiceForm":
                    showCreateServiceForm(request, response, isAdmin);
                    break;
                case "updateServiceForm":
                    showUpdateServiceForm(request, response, isAdmin);
                    break;
                case "viewServiceDetails":
                    viewServiceDetails(request, response, isAdmin);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
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
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
                return;
            }

            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("staffId") == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            boolean isAdmin = isAdmin(session);

            switch (action) {
                case "createService":
                    createService(request, response, isAdmin);
                    break;
                case "updateService":
                    updateService(request, response, isAdmin);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action: " + action);
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    private boolean isAdmin(HttpSession session) {
        Integer adminId = (Integer) session.getAttribute("adminId");
        return adminId == null; // If adminId is null, the user is an admin
    }

    private void listServices(HttpServletRequest request, HttpServletResponse response, boolean isAdmin) throws ServletException, IOException {
        try {
            List<Service> services = serviceDAO.getAllServices();
            request.setAttribute("serviceList", services);

            String targetPage = isAdmin ? "AdminServiceList.jsp" : "StaffServiceList.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error listing services", e);
        }
    }

    private void showCreateServiceForm(HttpServletRequest request, HttpServletResponse response, boolean isAdmin) throws ServletException, IOException {
        String targetPage = isAdmin ? "AdminCreateService.jsp" : "StaffCreateService.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
        dispatcher.forward(request, response);
    }

    private void createService(HttpServletRequest request, HttpServletResponse response, boolean isAdmin) throws ServletException, IOException {
        try {
            String serviceName = request.getParameter("serviceName");
            BigDecimal servicePrice = new BigDecimal(request.getParameter("servicePrice"));
            String serviceDescription = request.getParameter("serviceDescription");

            Service service = new Service();
            service.setServiceName(serviceName);
            service.setServicePrice(servicePrice);
            service.setServiceDescription(serviceDescription);

            serviceDAO.createService(service);
            String redirectPage = isAdmin ? "ServiceController?action=listServices" : "ServiceController?action=listServices";
            response.sendRedirect(redirectPage);
        } catch (Exception e) {
            throw new ServletException("Error creating service", e);
        }
    }

    private void showUpdateServiceForm(HttpServletRequest request, HttpServletResponse response, boolean isAdmin) throws ServletException, IOException {
        try {
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            Service service = serviceDAO.getServiceById(serviceId);
            request.setAttribute("service", service);

            String targetPage = isAdmin ? "AdminEditService.jsp" : "StaffEditService.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error showing update form", e);
        }
    }

    private void updateService(HttpServletRequest request, HttpServletResponse response, boolean isAdmin) throws ServletException, IOException {
        try {
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            String serviceName = request.getParameter("serviceName");
            BigDecimal servicePrice = new BigDecimal(request.getParameter("servicePrice"));
            String serviceDescription = request.getParameter("serviceDescription");

            Service service = new Service();
            service.setServiceId(serviceId);
            service.setServiceName(serviceName);
            service.setServicePrice(servicePrice);
            service.setServiceDescription(serviceDescription);

            serviceDAO.updateService(service);
            String redirectPage = isAdmin ? "ServiceController?action=listServices" : "ServiceController?action=listServices";
            response.sendRedirect(redirectPage);
        } catch (Exception e) {
            throw new ServletException("Error updating service", e);
        }
    }

    private void viewServiceDetails(HttpServletRequest request, HttpServletResponse response, boolean isAdmin) throws ServletException, IOException {
        try {
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));
            Service service = serviceDAO.getServiceById(serviceId);
            request.setAttribute("service", service);

            String targetPage = isAdmin ? "AdminServiceView.jsp" : "StaffServiceView.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error viewing service details", e);
        }
    }
}
