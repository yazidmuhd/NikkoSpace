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
<footer class="footer">
      <div class="main_container footer_container">
          <div class="footer_item">
				<img src="images/nikkospacelogo.png" alt="" style="max-width: 35%; height: auto; margin: 0 45%; display: block;">
              <div class="footer_p">Your Pets is Our Priority</div>
          </div>
          <div class="footer_item">
              <h3 class="footer_item_title">Reach Us</h3>
              <ul class="footer_list">
                  <a href="https://www.google.com/maps/place/Universiti+Teknologi+MARA+(UiTM)+Cawangan+Melaka+Kampus+Jasin/@2.2213964,102.4505406,17z/data=!3m1!4b1!4m6!3m5!1s0x31d1c2904d683dc3:0x216b1d164eba26a1!8m2!3d2.2213964!4d102.4531155!16s%2Fg%2F11cjkvzt2m?entry=ttu">
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

    <script src="https://unpkg.com/scrollreveal"></script> 
    <script src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script> 
    <script src="main.js"></script>
</body>
</html>
