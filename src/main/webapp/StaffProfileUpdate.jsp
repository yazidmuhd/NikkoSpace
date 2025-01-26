<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Profile</title>
    <link rel="stylesheet" href="css/LoginStyle.css">
    <script>
        function validatePasswords() {
            const newPassword = document.getElementById("password").value.trim();
            const confirmPassword = document.getElementById("confirmPassword").value.trim();
            if (newPassword && newPassword !== confirmPassword) {
                alert("Passwords do not match!");
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
   <li><a href="StaffAdminController?action=getProfile">Profile</a></li>
   <li><a href="StaffAdminController?action=logout">Sign out</a></li>
        </ul>
    </nav>

    <div class="form-wrapper">
        <div class="header__container">
            <h2>Update Your Profile</h2>
            <form action="StaffAdminController" method="POST" onsubmit="return validatePasswords()">
                <!-- Action for Updating Profile -->
                <input type="hidden" name="action" value="updateProfile">
                <input type="hidden" name="staff_Id" value="<%= request.getAttribute("staffId") %>" />
                
                <label for="username">Username:</label>
                <input type="text" name="username" id="username" value="<%= request.getAttribute("username") %>" required />
                
                <label for="email">Email:</label>
                <input type="email" name="email" id="email" value="<%= request.getAttribute("email") %>" required />
                
                <label for="phoneNumber">Phone Number:</label>
                <input type="text" name="phoneNumber" id="phoneNumber" value="<%= request.getAttribute("phoneNumber") %>" required />
                
                <label for="birthDate">Birth Date:</label>
                <input type="date" name="birthDate" id="birthDate" value="<%= request.getAttribute("birthDate") %>" required />
                
                <label for="gender">Gender:</label>
                <input list="gender-options" id="gender" name="gender" placeholder="Select Gender" value="<%= request.getAttribute("gender") %>" required>
                <datalist id="gender-options">
                    <option value="Male"></option>
                    <option value="Female"></option>
                </datalist>
                
                <label for="password">New Password:</label>
                <input type="password" name="password" id="password" placeholder="Enter new password" />
                
                <label for="confirmPassword">Confirm Password:</label>
                <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm new password" />
                
                <button type="submit" class="btn-primary">Update Profile</button>
            </form>
            
            <% if (request.getAttribute("error") != null) { %>
                <p class="error-message"><%= request.getAttribute("error") %></p>
            <% } %>
            
            <p>Last Updated At: <%= request.getAttribute("updatedAt") != null ? request.getAttribute("updatedAt") : "Not Available" %></p>
            
            <form action="StaffAdminController" method="POST">
                <input type="hidden" name="action" value="getProfile">
                <button type="submit" class="btn-primary">Back to Profile</button>
            </form>
        </div>
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

    <script src="https://unpkg.com/scrollreveal"></script>
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
    <script src="main.js"></script>
</body>
</html>
