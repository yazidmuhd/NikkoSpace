<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="StaffAdmin.model.Service" %>
<%@ page import="customer.model.Pet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Create Appointment</title>
    <link rel="stylesheet" href="css/IndexCustomerListAppointment.css">
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
            <li><a href="CustomerIndexHome.jsp">Home</a></li>
            <li><a href="PetController?action=getPetList">Pet</a></li>
            <li><a href="AppointmentController?action=showCreateAppointmentPage">Appointment</a></li>
            <li><a href="ServiceCustomerController?action=getServiceList">Service</a></li>
            <li><a href="CustomerController?action=getProfile">Profile</a></li>
            <li><a href="CustomerController?action=logout">Sign out</a></li>
        </ul>
    </nav>

    <header id="home">
        <div class="section__container header__container">
            <div class="header__content">
                <h1 style="text-align: center;">Create New Appointment</h1>
                <form action="AppointmentController" method="post">
                    <input type="hidden" name="action" value="createAppointment">
                    
                    <!-- Select Pet -->
<label for="petID">Select Pet:</label>
<select name="petID" id="petID" required>
    <option value="" disabled selected>-- Select Your Pet --</option>
    <%
        List<Pet> petList = (List<Pet>) request.getAttribute("petList");
        if (petList != null && !petList.isEmpty()) {
            for (Pet pet : petList) {
    %>
        <option value="<%= pet.getPetID() %>"><%= pet.getPetName() %></option>
    <%
            }
        } else {
    %>
        <option value="" disabled>No pets available</option>
    <%
        }
    %>
</select>

<!-- Select Service -->
<label for="serviceId">Select Service:</label>
<select name="serviceId" id="serviceId" required>
    <option value="" disabled selected>-- Select Service --</option>
    <%
        List<Service> serviceList = (List<Service>) request.getAttribute("serviceList");
        if (serviceList != null && !serviceList.isEmpty()) {
            for (Service service : serviceList) {
    %>
        <option value="<%= service.getServiceId() %>"><%= service.getServiceName() %></option>
    <%
            }
        } else {
    %>
        <option value="" disabled>No services available</option>
    <%
        }
    %>
</select>

                    
                    <label for="appDate">Appointment Date:</label>
                    <input type="date" name="appDate" id="appDate" required>
                    
                    <label for="appTime">Appointment Time:</label>
                    <input type="time" name="appTime" id="appTime" required>
                    
                    <label for="appRemark">Remarks:</label>
                    <textarea name="appRemark" id="appRemark" rows="4" placeholder="Enter any remarks"></textarea>
                    
                    <button type="submit">Submit Appointment</button>
                </form>
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
                    <li class="footer_list_item">Jasin, Melaka</li>
                    <li class="footer_list_item">NikkoSpace@gmail.com</li>
                    <li class="footer_list_item">+6019-2344 5265</li>
                    <li class="footer_list_item">+6017 6250 0959</li>
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
