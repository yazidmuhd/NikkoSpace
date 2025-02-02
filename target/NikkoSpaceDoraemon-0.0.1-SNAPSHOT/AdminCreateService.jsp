<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Add New Service Page</title>
    <link rel="stylesheet" href="css/IndexServiceStyle.css">
    <script>
        // Client-side validation for the form
        function validateForm() {
            const serviceName = document.getElementById("serviceName").value.trim();
            const servicePrice = document.getElementById("servicePrice").value.trim();

            if (!serviceName) {
                alert("Service name is required.");
                return false;
            }

            if (!servicePrice || parseFloat(servicePrice) <= 0) {
                alert("Service price must be a positive number.");
                return false;
            }

            return true;
        }
    </script>
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
    
    <div class="container">
        <h1>Add New Service</h1>
        <form action="ServiceController?action=createService" method="post" onsubmit="return validateForm()">
            
            <label for="serviceName">Enter Service Name:</label>
            <input type="text" id="serviceName" name="serviceName" placeholder="Enter Service Name" required>

            <label for="servicePrice">Service Price (RM):</label>
            <input type="number" id="servicePrice" name="servicePrice" placeholder="Enter Service Price" step="0.01" min="0.01" required>
            
            <label for="serviceDescription">Service Description:</label>
            <textarea id="serviceDescription" name="serviceDescription" placeholder="Enter a detailed description of the service" rows="5" required></textarea>
            
            <button type="submit" class="btn">Submit</button>
        </form>
    </div>

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
</body>
</html>
