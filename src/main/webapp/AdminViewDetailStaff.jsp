<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="StaffAdmin.model.StaffAdmin" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Admin View Detail Staff</title>
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
   <li><a href="AppointmentController?action=getPendingAppointments">Appointments</a></li>
   			<li><a href="CustomerController?action=listCustomer">Customer</a></li>
   			<li><a href="ServiceController?action=listServices">Service</a></li> 
   			<li><a href="StaffAdminController?action=listStaff">Staff</a></li> 
   			<li><a href="StaffAdminController?action=getProfile">Profile</a></li>
   			<li><a href="StaffAdminController?action=logout">Logout</a></li> 
        </ul>
    </nav>

    <header id="home">
        <div class="section__container header__container">
            <div class="header__content">
                <h1 style="text-align: center;">Staff Profile Details</h1>
                <div class="profile-container">
                    <div class="profile-section">
                        <h2>Staff Details</h2>
                        <%
                            StaffAdmin staff = (StaffAdmin) request.getAttribute("staff");
                            if (staff != null) {
                        %>
                        <table>
                            <tr>
                                <th>Staff ID:</th>
                                <td><%= staff.getStaffId() %></td>
                            </tr>
                            <tr>
                                <th>Username:</th>
                                <td><%= staff.getUsername() %></td>
                            </tr>
                            <tr>
                                <th>Email:</th>
                                <td><%= staff.getEmail() %></td>
                            </tr>
                            <tr>
                                <th>Phone Number:</th>
                                <td><%= staff.getPhoneNumber() %></td>
                            </tr>
                            <tr>
                                <th>Birth Date:</th>
                                <td><%= staff.getBirthDate() %></td>
                            </tr>
                            <tr>
                                <th>Gender:</th>
                                <td><%= staff.getGender() %></td>
                            </tr>
                            <tr>
                                <th>Created By:</th>
                                <td><%= staff.getCreatedByUsername() != null ? staff.getCreatedByUsername() : "N/A" %></td>
                            </tr>
                            <tr>
                                <th>Created At:</th>
                                <td><%= staff.getCreatedAt() %></td>
                            </tr>
                        </table>
                        <%
                            } else {
                        %>
                        <p style="color: red;">Staff details could not be found.</p>
                        <%
                            }
                        %>
                       
                        
					
					<div style="text-align: center; margin-top: 20px;">
    <form action="StaffAdminController" method="get">
        <input type="hidden" name="action" value="listStaff">
        <button type="submit" class="action-btn add-btn">Back to Staff List</button>
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
                    <a href="https://www.google.com/maps/place/Universiti+Teknologi+MARA+(UiTM)+Cawangan+Melaka+Kampus+Jasin">
                        <li class="footer_list_item">Jasin, Melaka</li>
                    </a>
                    <a href="mailto:capal@gmail.com">
                        <li class="footer_list_item">NikkoSpace@gmail.com</li>
                    </a>
                    <a href="tel:+601923445265">
                        <li class="footer_list_item">+6019-2344 5265</li>
                    </a>
                    <a href="tel:+601762500959">
                        <li class="footer_list_item">+6017 6250 0959</li>
                    </a>
                </ul>
            </div>
            <div class="footer_item">
                <h3 class="footer_item_title">Support</h3>
                <ul class="footer_list">
                    <a href="product.html">
                        <li class="footer_list_item">Our Store</li>
                    </a>
                    <a href="login.html">
                        <li class="footer_list_item">Login / Register</li>
                    </a>
                    <a href="cart.html">
                        <li class="footer_list_item">Cart</li>
                    </a>
                    <a href="product.html">
                        <li class="footer_list_item">Shop</li>
                    </a>
                </ul>
            </div>
            <div class="footer_item">
                <h3 class="footer_item_title">Help</h3>
                <ul class="footer_list">
                    <a href="#">
                        <li class="footer_list_item">Privacy policy</li>
                    </a>
                    <a href="#">
                        <li class="footer_list_item">Terms of use</li>
                    </a>
                    <a href="#">
                        <li class="footer_list_item">FAQ's</li>
                    </a>
                    <a href="#">
                        <li class="footer_list_item">Contact</li>
                    </a>
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
