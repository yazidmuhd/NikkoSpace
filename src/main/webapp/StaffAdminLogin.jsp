<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Staff Login</title>
    <!-- Link to external CSS -->
    <link rel="stylesheet" href="css/LoginStyle.css">
</head>
<body>
    <nav> 
        <div class="nav__header"> 
            <!-- Logo -->
            <div class="nav__logo"> 
                <a href="#">NikkoSpace</a> 
            </div> 
            <div class="nav__menu__btn" id="menu-btn"> 
                <i class="ri-menu-line"></i> 
            </div> 
        </div> 
        <ul class="nav__links" id="nav-links"> 
            
            <li><a href="login.jsp">Customer?</a></li> 
        </ul> 
    </nav>

    <div class="form-wrapper">
    <div class="header__container">
        <h2>Staff Login</h2>
        <!-- Login form -->
        <form action="StaffAdminController" method="POST" class="auth-form">
            <input type="hidden" name="action" value="login">
            
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" placeholder="Enter your username" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>
            
            <button type="submit" class="btn-primary">Login</button>
        </form>
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p class="error-message"><%= request.getAttribute("errorMessage") %></p>
        <% } %>
    </div>
</div>


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
