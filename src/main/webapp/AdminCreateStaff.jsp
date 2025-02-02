<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Create Staff</title>
    <link rel="stylesheet" href="css/LoginStyle.css">
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
    <div class="form-wrapper">
        <div class="header__container">
            <h2>Create Staff</h2>
            <form action="StaffAdminController" method="POST">
    <input type="hidden" name="action" value="createStaff">
                <!-- Staff Name -->
                <label for="staffName">Staff Username:</label>
                <input type="text" id="staffName" name="staffName" placeholder="Enter staff username" required>
                
                <!-- Email -->
                <label for="staffEmail">Email:</label>
                <input type="email" id="staffEmail" name="staffEmail" placeholder="Enter staff email" required>
                
                <!-- Phone -->
                <label for="staffPhone">Phone:</label>
                <input type="text" id="staffPhone" name="staffPhone" placeholder="Enter staff phone" required>
                
                <!-- Birth Date -->
                <label for="staffBirthDate">Birth Date:</label>
                <input type="date" id="staffBirthDate" name="staffBirthDate" required>
                
                <label for="staffGender">Gender:</label>
<select id="staffGender" name="staffGender" required>
    <option value="" disabled selected>Select Gender</option>
    <option value="Male">Male</option>
    <option value="Female">Female</option>
</select>

                
                <!-- Password -->
                <label for="staffPassword">Password:</label>
                <input type="password" id="staffPassword" name="staffPassword" placeholder="Enter password" required>
                
                <!-- Submit Button -->
                <button type="submit">Create Staff</button>
            </form>
            <p>
                <a href="StaffAdminController?action=listStaff">Go Back</a>
            </p>
            <% if (request.getAttribute("errorMessage") != null) { %>
                <p class="error-message"><%= request.getAttribute("errorMessage") %></p>
            <% } %>
        </div>
    </div>
</header>


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
