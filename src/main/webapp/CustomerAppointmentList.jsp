<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="StaffAdmin.model.Appointment" %>
<%@ page import="StaffAdmin.model.Service" %>
<%@ page import="customer.model.Pet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Customer Appointment List</title>
    <link rel="stylesheet" href="css/IndexCustomerListAppointment.css">
</head>
<body>
    <!-- Navigation -->
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
            <li><a href="AppointmentController?action=getAppointmentList">Appointment</a></li>
            <li><a href="ServiceController?action=getServiceList">Service</a></li>
            <li><a href="CustomerController?action=getProfile">Profile</a></li>
            <li><a href="CustomerController?action=logout">Sign out</a></li>
        </ul>
    </nav>

    <!-- Header -->
    <header id="home">
        <div class="section__container header__container">
            <div class="header__content">
                <h1 style="text-align: center;">Your List of Appointments</h1>
                <h2>All your details of pet appointments</h2>

                <!-- Appointment List -->
                <section class="appointment-list">
                    <div class="container">
                        <table>
                            <thead>
                                <tr>
                                    <th>Appointment ID</th>
                                    <th>Pet Name</th>
                                    <th>Service Name</th>
                                    <th>Appointment Date</th>
                                    <th>Appointment Time</th>
                                    <th>Remarks</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    // Retrieve appointmentList from the request attribute
                                    List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointmentList");

                                    if (appointments == null || appointments.isEmpty()) {
                                %>
                                    <tr>
                                        <td colspan="8" style="text-align: center;">No appointments found. Please create a new appointment.</td>
                                    </tr>
                                <%
                                    } else {
                                        for (Appointment appointment : appointments) {
                                %>
                                    <tr>
                                        <td><%= appointment.getAppId() %></td>
                                        <td><%= appointment.getPet().getPetName() %></td> 
                                        <td><%= appointment.getService().getServiceName() %></td>
                                        <td><%= appointment.getAppDate() %></td>
                                        <td><%= appointment.getAppTime() %></td>
                                        <td><%= appointment.getAppRemark() %></td>
                                        <td><%= appointment.getStatus() %></td>
                                        <td>
                                            <form action="AppointmentController" method="get" style="display: inline;">
                                                <input type="hidden" name="action" value="viewAppointmentDetails">
                                                <input type="hidden" name="appId" value="<%= appointment.getAppId() %>">
                                                <button type="submit" class="btn-view">View</button>
                                            </form>
                                            <% if ("Pending".equalsIgnoreCase(appointment.getStatus())) { %>
                                                <form action="AppointmentController" method="get" style="display: inline;">
                                                    <input type="hidden" name="action" value="showEditAppointmentPage">
                                                    <input type="hidden" name="appId" value="<%= appointment.getAppId() %>">
                                                    <button type="submit" class="btn-update">Update</button>
                                                </form>
                                            <% } %>
                                        </td>
                                    </tr>
                                <%
                                        }
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </section>
                <br />
                <div class="header__btn">
                    <button onclick="window.location.href='CustomerCreateAppointment.jsp'">
                        Add Appointment <span><i class="ri-arrow-right-line"></i></span>
                    </button>
                </div>
            </div>
        </div>
    </header>

    <!-- Footer -->
    <footer class="footer">
        <div class="main_container footer_container">
            <div class="footer_item">
                <img src="images/nikkospacelogo.png" alt="" style="max-width: 35%; height: auto; margin: 0 45%; display: block;">
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
