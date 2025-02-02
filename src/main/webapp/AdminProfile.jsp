<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile</title>
    <link rel="stylesheet" href="css/CustomerViewProfile.css">
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
   <li><a href="AppointmentController?action=getPendingAppointments">Appointments</a></li>
   <li><a href="CustomerController?action=listCustomer">Customer</a></li>
   <li><a href="ServiceController?action=listServices">Service</a></li>
              <li><a href="StaffAdminController?action=listStaff">Staff</a></li>
   <li><a href="StaffAdminController?action=getProfile">Profile</a></li>
   <li><a href="StaffAdminController?action=logout">Logout</a></li> 
  </ul> 
 </nav>

<header id="home">
   <div class="form-wrapper">
    <div class="header__container">
        <h2>Welcome, <%= session.getAttribute("username") %>!</h2>
        <h3>Your Profile</h3>
        <table class="profile-table">
            <tr>
                <th style="padding: 10px;">Username:</th>
                <td style="padding: 10px;"><%= session.getAttribute("username") %></td>
            </tr>
            <tr>
                <th style="padding: 10px;">Email:</th>
                <td style="padding: 10px;"><%= session.getAttribute("email") %></td>
            </tr>
            <tr>
                <th style="padding: 10px;">Phone Number:</th>
                <td style="padding: 10px;"><%= session.getAttribute("phoneNumber") %></td>
            </tr>
            <tr>
                <th style="padding: 10px;">Birth Date:</th>
                <td style="padding: 10px;"><%= session.getAttribute("birthDate") %></td>
            </tr>
            <tr>
                <th style="padding: 10px;">Gender:</th>
                <td style="padding: 10px;"><%= session.getAttribute("gender") %></td>
            </tr>
        </table>
        <div class="button-group">
        <div style="margin-top: 1.5rem;text-align:center">
            <form action="StaffAdminController" method="POST">
    <input type="hidden" name="action" value="loadUpdateProfile">
    <button type="submit" class="btn update">Update Profile</button>
</form>
 <br>
            <form action="StaffAdminController" method="POST">
    <input type="hidden" name="action" value="logout">
    <button type="submit" class="btn logout">Logout</button>
</form>
        </div>
        </div>
    </div>
</div>
</header>


    <!-- Footer -->
    <footer class="footer">
    <div class="main_container footer_container">
        <div class="footer_item">
            <a href="#" class="footer_logo">
                <img class="footer_logo" src="images/skyhigh logo fit.png" alt="NikkoSpace Logo">
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
<script src="https://unpkg.com/scrollreveal"></script>
<script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
<script src="main.js"></script>
</body>
</html>
