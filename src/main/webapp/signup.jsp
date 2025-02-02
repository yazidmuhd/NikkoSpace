<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Signup</title>
    <link rel="stylesheet" href="css/LoginStyle.css">
    <script>
        function validateForm() {
            var password = document.getElementById("password").value.trim();
            var confirmPassword = document.getElementById("confirmPassword").value.trim();
            var phoneNumber = document.getElementById("phoneNumber").value.trim();
            var errorMessage = "";

            // Password Match Validation
            if (password !== confirmPassword) {
                errorMessage += "Passwords do not match!\\n";
            }

            // Phone Number Validation (Only Digits)
            var phoneRegex = /^[0-9]+$/;
            if (!phoneRegex.test(phoneNumber)) {
                errorMessage += "Phone number must contain only digits!\\n";
            }

            if (errorMessage !== "") {
                alert(errorMessage);
                return false;
            }

            return true;
        }

        function restrictPhoneInput(event) {
            var key = event.key;
            if (!/^[0-9]$/.test(key)) {
                event.preventDefault();
            }
        }
        
        function checkPasswordStrength() {
    		var password = document.getElementById("password").value;
    		var strengthMeter = document.getElementById("passwordStrength");
    		var strengthText = document.getElementById("strengthText");
    		var suggestionText = document.getElementById("suggestionText");

    		if (password.length === 0) {
    			strengthMeter.style.width = "0%";
    			strengthText.textContent = "";
    			suggestionText.textContent = "";
    			return;
    		}

    		var strength = 0;
    		var suggestions = [];

    		if (password.length >= 8) {
    			strength += 1;
    		} else {
    			suggestions.push("Use at least 8 characters.");
    		}

    		if (/[A-Z]/.test(password)) {
    			strength += 1;
    		} else {
    			suggestions.push("Add an uppercase letter.");
    		}

    		if (/\d/.test(password)) {
    			strength += 1;
    		} else {
    			suggestions.push("Include a number.");
    		}

    		if (/[@$!%*?&]/.test(password)) {
    			strength += 1;
    		} else {
    			suggestions.push("Use a special character (@, $, !, %, *, ?, &).");
    		}

    		if (strength === 1) {
    			strengthMeter.style.width = "25%";
    			strengthMeter.style.backgroundColor = "red";
    			strengthText.textContent = "Weak";
    		} else if (strength === 2) {
    			strengthMeter.style.width = "50%";
    			strengthMeter.style.backgroundColor = "orange";
    			strengthText.textContent = "Medium";
    		} else if (strength >= 3) {
    			strengthMeter.style.width = "100%";
    			strengthMeter.style.backgroundColor = "green";
    			strengthText.textContent = "Strong";
    		}

    		// Show suggestions if password is not strong
    		suggestionText.textContent = strength < 3 ? "Suggestions: "
    				+ suggestions.join(" ") : "";
    	}
    </script>
</head>
<body>
    <nav>
        <div class="nav__header">
            <div class="nav__logo">
                <a href="#">NikkoSpace</a>
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
                <form action="CustomerController" method="POST" onsubmit="return validateForm();">
                    <input type="hidden" name="action" value="signup">

                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required>

                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>

                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" onkeyup="checkPasswordStrength()"  required>
                    <div class="password-strength-container">
						<div id="passwordStrength" class="password-strength"></div>
						<span id="strengthText"></span>
					</div>
					<p id="suggestionText" class="suggestion-text"></p>

                    <label for="confirmPassword">Confirm Password:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
               
               		<label for="phoneNumber">Phone Number:</label>
                    <input type="text" id="phoneNumber" name="phoneNumber" required onkeypress="restrictPhoneInput(event)" maxlength="15">

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
                <p>Already have an account? <a href="login.jsp">Login here</a></p>

                <% if (request.getAttribute("errorMessage") != null) { %>
                    <p class="error-message"><%= request.getAttribute("errorMessage") %></p>
                <% } %>
            </div>
        </div>
    </header>

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
