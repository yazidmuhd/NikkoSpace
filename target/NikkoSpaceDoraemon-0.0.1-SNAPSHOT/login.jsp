<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Customer Login</title>
    <link rel="stylesheet" href="css/LoginStyle.css">
</head>
<body>
    <!-- Navigation Bar -->
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
            <li><a href="index.jsp">Home</a></li>
            
            <li><a href="signup.jsp">Create an account</a></li>
            <li><a href="StaffAdminLogin.jsp">Staff?</a></li>
        </ul>
    </nav>

    <!-- Login Form Section -->
    <header id="home">
        <div class="form-wrapper">
            <div class="header__container">
                <h2>Customer Login</h2>
                <!-- Form action updated to match CustomerController and action=login -->
                <form action="CustomerController" method="POST">
                    <input type="hidden" name="action" value="login">
                    
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>
                    
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>
                    
                    <button type="submit">Login</button>
                </form>
                <p>
                    Don't have an account? <a href="signup.jsp">Signup here</a>
                </p>
                
                <!-- Display error message if login fails -->
                <% if (request.getAttribute("errorMessage") != null) { %>
                    <p class="error-message"><%=request.getAttribute("errorMessage")%></p>
                <% } %>
            </div>
        </div>
    </header>

    <!-- Footer Section -->
    <footer class="footer">
        <div class="main_container footer_container">
            <div class="footer_item">
                <a href="#" class="footer_logo">
                    <img class="footer_logo" src="images/skyhigh_logo_fit.png" alt="NikkoSpace Logo">
                </a>
                <div class="footer_p">Your Pets Are Our Priority</div>
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
                    <li class="footer_list_item">Privacy Policy</li>
                    <li class="footer_list_item">Terms of Use</li>
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
