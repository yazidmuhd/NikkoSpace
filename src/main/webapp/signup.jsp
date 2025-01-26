<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Signup</title>
    <link rel="stylesheet" href="css/LoginStyle.css">
    <script>
        function validatePasswords() {
            var password = document.getElementById("password").value.trim();
            var confirmPassword = document.getElementById("confirmPassword").value.trim();
            if (password !== confirmPassword) {
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
            <li><a href="index.jsp">Home</a></li>
            
            <li><a href="login.jsp">Sign in</a></li>
            <li><a href="StaffAdminLogin.jsp">Staff?</a></li>
        </ul>
    </nav>

    <header id="home">
        <div class="form-wrapper">
            <div class="header__container">
                <h2>Signup</h2>
                <form action="CustomerController" method="POST" onsubmit="return validatePasswords();">
                <input type="hidden" name="action" value="signup">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>

                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>

                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required>

                    <label for="confirmPassword">Confirm Password:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>

                    <label for="phoneNumber">Phone Number:</label>
                    <input type="text" id="phoneNumber" name="phoneNumber" required>

                    <label for="birthDate">Birth Date:</label>
                    <input type="date" id="birthDate" name="birthDate" required>

                    <label for="gender">Gender:</label>
<select id="gender" name="gender" required>
    <option value="" disabled selected>Select Gender</option>
    <option value="Male">Male</option>
    <option value="Female">Female</option>
</select>

				

                    <button type="submit">Signup</button>
                </form>
                <p>
                    Already have an account? <a href="login.jsp">Login here</a>
                </p>
                <% if (request.getAttribute("errorMessage") != null) { %>
                    <p class="error-message"><%=request.getAttribute("errorMessage")%></p>
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
