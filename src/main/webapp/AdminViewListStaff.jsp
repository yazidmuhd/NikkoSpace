<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="StaffAdmin.model.StaffAdmin" %>
<%@ page import="StaffAdmin.dao.StaffAdminDAO" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>List of Staff</title>
    <link rel="stylesheet" href="css/AdminListStaff.css">
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
            <h1 style="text-align: center;">Staff Profiles</h1>
            <h2>All registered staff members</h2>

            <div class="search-form">
                <form action="StaffAdminController" method="get" style="text-align: center; margin-bottom: 20px;">
    <input type="hidden" name="action" value="searchStaff">
    <input type="text" name="search" placeholder="Search staff by username" required>
    <button type="submit" class="search-btn">Search</button>
</form>

<table>
    <thead>
        <tr>
            <th>Staff ID</th>
            <th>Username</th>
            <th>Email</th>
            <th>Password</th>
            <th>Created At</th>
            <th>Created By</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <%
        List<StaffAdmin> staffList = (List<StaffAdmin>) request.getAttribute("staffList");
        if (staffList != null && !staffList.isEmpty()) {
            for (StaffAdmin staff : staffList) {
        %>
        <tr>
            <td><%= staff.getStaffId() %></td>
            <td><%= staff.getUsername() %></td>
            <td><%= staff.getEmail() %></td>
            <td>******</td>
            <td><%= staff.getCreatedAt() %></td>
            <td><%= staff.getCreatedByUsername() %></td>
            <td><div class="btn-container">
			<a href="StaffAdminController?action=viewStaff&username=<%= staff.getUsername() %>" class="action-btn view-btn">View</a></div>
			</td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="6" style="text-align: center;">No staff found.</td>
        </tr>
        <%
        }
        %>
    </tbody>
</table>
<div style="text-align: center; margin-top: 20px;">
    <form action="StaffAdminController" method="get">
        <input type="hidden" name="action" value="loadCreateStaffPage">
        <button type="submit" class="action-btn add-btn">Add New Staff</button>
    </form>
</div>

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
