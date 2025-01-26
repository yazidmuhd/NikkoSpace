package customer.controller;

import customer.dao.PetDAO;
import customer.model.Pet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class PetController
 */
public class PetController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PetDAO petDAO;

    public PetController() {
        super();
        petDAO = new PetDAO(); // Instantiate PetDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "getPetList":
                    getPetList(request, response);
                    break;
                case "viewPet":
                    viewPet(request, response);
                    break;
                case "updatePet":
                    loadUpdatePetForm(request, response); // New method for loading update form
                    break;
                default:
                    response.sendRedirect("Profile.jsp");
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "addPet":
                    addPet(request, response);
                    break;
                case "updatePet":
                    updatePet(request, response);
                    break;
                default:
                    response.sendRedirect("profile.jsp");
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void addPet(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	HttpSession session = request.getSession(false);

        if (session != null) {
            Integer custID = (Integer) session.getAttribute("custID");
            System.out.println(custID);
        
        Pet pet = new Pet();
        pet.setPetName(request.getParameter("petName"));
        pet.setPetWeight(Double.parseDouble(request.getParameter("petWeight")));
        System.out.println("petName: " + request.getParameter("petName"));
        System.out.println("petWeight: " + request.getParameter("petWeight"));
        pet.setPetStatus("Alive");
        pet.setCustID(custID); 
        petDAO.addPet(pet);
        response.sendRedirect("PetController?action=getPetList");
    }}

    private void updatePet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Retrieve pet details from the request
        int petID = Integer.parseInt(request.getParameter("petID"));
        String petName = request.getParameter("petName");
        double petWeight = Double.parseDouble(request.getParameter("petWeight"));
        String petStatus = request.getParameter("petStatus");

        // Get customer ID from the session
        HttpSession session = request.getSession(false); // Get existing session
        if (session == null || session.getAttribute("custID") == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if no session or custID is found
            return;
        }
        int custID = (int) session.getAttribute("custID");

        // Create a Pet object with the updated details
        Pet pet = new Pet();
        pet.setPetID(petID);
        pet.setPetName(petName);
        pet.setPetWeight(petWeight);
        pet.setPetStatus(petStatus);
        pet.setCustID(custID); // Set custID for reference

        // Update the pet in the database
        boolean isUpdated = petDAO.updatePet(pet);

        if (isUpdated) {
            // Redirect to the pet list page after successful update
            response.sendRedirect("PetController?action=getPetList");
        } else {
            // Handle update failure
            request.setAttribute("errorMessage", "Failed to update pet details. Please try again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerEditPet.jsp");
            dispatcher.forward(request, response);
        }
    }



    private void getPetList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(false); // Get the session, but don't create a new one
        if (session != null) {
            Integer custID = (Integer) session.getAttribute("custID"); // Retrieve custID from session

            if (custID != null) {
                List<Pet> petList = petDAO.getPetsByCustomerID(custID); // Fetch pet list
                request.setAttribute("petList", petList);
                RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerPetList.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("login.jsp"); // Redirect to login if custID is missing
            }
        } else {
            response.sendRedirect("login.jsp"); // Redirect to login if no session exists
        }
    }


    private void viewPet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int petID = Integer.parseInt(request.getParameter("petID"));
        Pet pet = petDAO.getPetByID(petID); // Fetch pet details from the database

        if (pet != null) {
            request.setAttribute("petName", pet.getPetName());
            request.setAttribute("petWeight", pet.getPetWeight());
            request.setAttribute("petStatus", pet.getPetStatus());
            request.setAttribute("custID", pet.getCustID());
            RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerViewPet.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("PetController?action=getPetList");
        }
    }
    
    private void loadUpdatePetForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Retrieve petID from request parameter
        int petID = Integer.parseInt(request.getParameter("petID"));
        
        // Fetch the pet details from the database
        Pet pet = petDAO.getPetByID(petID);

        // Check if the pet exists
        if (pet != null) {
            // Set pet details as request attributes
            request.setAttribute("petID", pet.getPetID());
            request.setAttribute("petName", pet.getPetName());
            request.setAttribute("petWeight", pet.getPetWeight());
            request.setAttribute("petStatus", pet.getPetStatus());
            request.setAttribute("custID", pet.getCustID());

            // Forward to CustomerEditPet.jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerEditPet.jsp");
            dispatcher.forward(request, response);
        } else {
            // Redirect to pet list if pet not found
            response.sendRedirect("PetController?action=getPetList");
        }
    }


}
