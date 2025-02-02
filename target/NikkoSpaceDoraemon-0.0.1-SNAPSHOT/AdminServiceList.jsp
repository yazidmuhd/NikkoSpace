<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="StaffAdmin.model.Service" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Service Page</title>
    <link rel="stylesheet" href="css/listServiceStyle.css">
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
        <li><a href="AppointmentController?action=listAppointment">Appointment</a></li>
   <li><a href="CustomerController?action=listCustomer">Customer</a></li>
   <li><a href="ServiceController?action=listServices">Service</a></li> 
   <li><a href="StaffAdminController?action=listStaff">Staff</a></li> 
   <li><a href="StaffAdminController?action=getProfile">Profile</a></li>
   <li><a href="StaffAdminController?action=logout">Sign out</a></li> 
    </ul>
</nav>

<header id="home">
    <div class="section__container header__container">
        <div class="header__content">
            <h1>List of Services</h1>
        </div>
    </div>

    <div class="service-container">
        <%
    // Retrieve the serviceList from the request attribute
    List<Service> services = (List<Service>) request.getAttribute("serviceList");

    if (services == null || services.isEmpty()) {
%>
        <h2 style="text-align: center; color: #666;">No services yet, please add a service by clicking the button below.</h2>
<%
    } else {
        for (Service service : services) {
%>
        <div class="service">
            <div class="service-details">
                <h3><%= service.getServiceName() %></h3>
                <p>
                    <% 
                        String description = service.getServiceDescription();
                        String[] descriptionItems = description.split(",");
                        for (int i = 0; i < descriptionItems.length; i++) {
                            out.print(descriptionItems[i]);
                            if (i < descriptionItems.length - 1) {
                                out.print("<br>"); 
                            }
                        }
                    %>
                </p>
                <h4>Price: RM <%= service.getServicePrice() %></h4>
            </div>
            <form action="ServiceController" method="get">
    <input type="hidden" name="action" value="updateServiceForm">
    <input type="hidden" name="serviceId" value="<%= service.getServiceId() %>">
    <button class="action-button" type="submit">Edit</button>
</form>


        </div>
<%
        }
    }
%>
    </div>

    <div style="text-align: center; margin-top: 20px;">
        <form action="ServiceController" method="get">
            <input type="hidden" name="action" value="createServiceForm">
            <button class="action-button" type="submit">
                Add New Service
            </button>
        </form>
    </div>
</header>

<footer class="footer">
    <div class="main_container footer_container">
        <div class="footer_item">
            <a href="#" class="footer_logo">
                <img class="footer_logo" src="images/nikkospacelogo.png" alt="">
            </a>
            <div class="footer_p">Your Pets is Our Priority</div>
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
