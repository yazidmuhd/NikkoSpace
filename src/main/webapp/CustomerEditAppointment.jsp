<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="StaffAdmin.model.Appointment" %>
<%@ page import="customer.model.Pet" %>
<%@ page import="StaffAdmin.model.Service" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Customer Update Appointment Details</title>
    <link rel="stylesheet" href="css/CustomerUpdateAppointment.css">
</head>
<body>
    <nav>
        <div class="nav__header">
            <div class="nav__logo">
                <a href="#">NikkoSpace</a>
            </div>
            <div class="nav__menu__btn" id="menu-btn">
                <i class="ri-menu-line"></i>
            </div>
        </div>
        <ul class="nav__links" id="nav-links">
            <li><a href="IndexHome.jsp">Home</a></li>
            <li><a href="PetController?action=getPetList">Pet</a></li>
            <li><a href="AppointmentController?action=getAppointmentList">Appointment</a></li>
            <li><a href="CustomerController?action=getProfile">Profile</a></li>
            <li><a href="CustomerController?action=logout">Logout</a></li>
        </ul>
    </nav>

<header id="home">
        <div class="section__container header__container">
            <div class="header__content">
                <h1 style="text-align: center;">Update Appointment Details</h1>
                <h2>Modify Your Appointment Information</h2>
                <br>
                <div class="form-wrapper">
                    <%
                        Appointment appointment = (Appointment) request.getAttribute("appointment");
                        List<Pet> petList = (List<Pet>) request.getAttribute("petList");
                        List<Service> serviceList = (List<Service>) request.getAttribute("serviceList");
                    %>
                    <form action="AppointmentController" method="post">
                        <input type="hidden" name="action" value="updateAppointment">

                        <label for="appId">Appointment ID:</label>
                        <input type="text" id="appId" name="appId" value="<%= appointment.getAppId() %>" readonly><br><br>

                        <label for="petId">Pet:</label>
                        <select id="petId" name="petId" required>
                            <option value="">-- Select Your Pet --</option>
                            <%
                                if (petList != null && !petList.isEmpty()) {
                                    for (Pet pet : petList) {
                            %>
                            <option value="<%= pet.getPetID() %>" <%= (appointment.getPet() != null && pet.getPetID() == appointment.getPet().getPetID()) ? "selected" : "" %>>
                                <%= pet.getPetName() %>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select><br><br>

                        <label for="serviceId">Service:</label>
                        <select id="serviceId" name="serviceId" required>
                            <option value="">-- Select Service --</option>
                            <%
                                if (serviceList != null && !serviceList.isEmpty()) {
                                    for (Service service : serviceList) {
                            %>
                            <option value="<%= service.getServiceId() %>" <%= (appointment.getService() != null && service.getServiceId() == appointment.getService().getServiceId()) ? "selected" : "" %>>
                                <%= service.getServiceName() %>
                            </option>
                            <%
                                    }
                                }
                            %>
                        </select><br><br>

                        <label for="appDate">Appointment Date:</label>
                        <input type="date" id="appDate" name="appDate" value="<%= appointment.getAppDate() %>" required><br><br>
                        <label for="appTime">Appointment Time:</label>
                        <input type="time" id="appTime" name="appTime" value="<%= appointment.getAppTime().toLocalDateTime().toLocalTime() %>" required><br><br>

                        <label for="status">Appointment Status:</label>
<input type="text" id="status" name="status" value="<%= appointment.getStatus() %>" readonly>
<br><br>


                        <!-- Remarks -->
                        <label for="appRemark">Remarks:</label>
                        <textarea id="appRemark" name="appRemark" rows="4"><%= appointment.getAppRemark() %></textarea><br><br>

                        <!-- Buttons -->
                        <div class="button-group">
                            <a href="AppointmentController?action=getAppointmentList">
                                <button type="button" class="cancel-button">Cancel</button>
                            </a>
                            <button type="submit" class="submit-button">Update</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </header>

    <footer class="footer">
        <div class="main_container footer_container">
            <div class="footer_item">
                <img src="images/nikkospacelogo.png" alt=""
                     style="max-width: 35%; height: auto; margin: 0 45%; display: block;">
                <div class="footer_p">Your Pets are Our Priority</div>
            </div>
            <div class="footer_item">
                <h3 class="footer_item_title">Reach Us</h3>
                <ul class="footer_list">
                    <a href="#"><li class="footer_list_item">Jasin, Melaka</li></a>
                    <a href="mailto:NikkoSpace@gmail.com"><li class="footer_list_item">NikkoSpace@gmail.com</li></a>
                    <a href="tel:+601923445265"><li class="footer_list_item">+6019-2344 5265</li></a>
                    <a href="tel:+601762500959"><li class="footer_list_item">+6017 6250 0959</li></a>
                </ul>
            </div>
            <div class="footer_item">
                <h3 class="footer_item_title">Support</h3>
                <ul class="footer_list">
                    <li class="footer_list_item">Our Store</li>
                    <li class="footer_list_item">Login / Register</li>
                    <li class="footer_list_item">Cart</li>
                    <li class="footer_list_item">Shop</li>
                </ul>
            </div>
            <div class="footer_item">
                <h3 class="footer_item_title">Help</h3>
                <ul class="footer_list">
                    <li class="footer_list_item">Privacy policy</li>
                    <li class="footer_list_item">Terms of use</li>
                    <li class="footer_list_item">FAQ's</li>
                    <li class="footer_list_item">Contact</li>
                </ul>
            </div>
        </div>
        <div class="footer_bottom">
            <div class="footer_bottom_container">
                <p class="footer_copy">Copyright NikkoSpace 2024. All rights reserved.</p>
            </div>
        </div>
    </footer>
</body>
</html>
