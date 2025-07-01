<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<script>
	
function isValidPassword(password) {
	var regex = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
	return regex.test(password);
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

	suggestionText.textContent = strength < 3 ? "Suggestions: "
			+ suggestions.join(" ") : "";
}
</script>
<head>
    <meta charset="ISO-8859-1">
    <title>Update Profile</title>
    <link rel="stylesheet" href="css/CustomerUpdateProfile.css">
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
            <li><a href="CustomerIndexHome.jsp">Home</a></li>
            <li><a href="PetController?action=getPetList">Pet</a></li>
            <li><a href="AppointmentController?action=getAppointmentList">Appointment</a></li>
            <li><a href="ServiceController?action=getServiceList">Service</a></li>
            <li><a href="CustomerController?action=getProfile">Profile</a></li>
            <li><a href="CustomerController?action=logout">Logout</a></li>
        </ul> 
    </nav>

    <!-- Profile Update Form -->
<div class="form-wrapper">
    <div class="header__container">
        <h2>Update Your Profile</h2>
        <form action="CustomerController" method="post">
    <input type="hidden" name="action" value="updateProfile">
    <input type="hidden" name="custID" value="<%= session.getAttribute("custID") %>">

            <label for="username">Username:</label>
            <input type="text" name="username" id="username" value="<%= request.getAttribute("username") %>" required />

            <label for="email">Email:</label>
            <input type="email" name="email" id="email" value="<%= request.getAttribute("email") %>" required />

            <label for="phoneNumber">Phone Number:</label>
            <input type="text" name="phoneNumber" id="phoneNumber" value="<%= request.getAttribute("phoneNumber") %>" required />

            <label for="birthDate">Birth Date:</label>
            <input type="date" name="birthDate" id="birthDate" value="<%= request.getAttribute("birthDate") %>" required />

            <label for="gender">Gender:</label>
<select id="gender" name="gender" style="padding: 15px 18px; font-size: 1rem; border: none; border-radius: 10px; background: linear-gradient(to right, #e9ecef, #ffffff); box-shadow: inset 1px 1px 5px rgba(0, 0, 0, 0.1), inset -1px -1px 5px rgba(255, 255, 255, 0.7); color: #333; appearance: none;">
    <option value="Male" <%= "Male".equals(request.getAttribute("gender")) ? "selected" : "" %>>Male</option>
    <option value="Female" <%= "Female".equals(request.getAttribute("gender")) ? "selected" : "" %>>Female</option>
</select>

			<label for="password">Password:</label>
                    <input type="password" id="password" name="password" onkeyup="checkPasswordStrength()"  >
                    <div class="password-strength-container">
						<div id="passwordStrength" class="password-strength"></div>
						<span id="strengthText"></span>
					</div>
					<p id="suggestionText" class="suggestion-text"></p>

                    <label for="confirmPassword">Confirm Password:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" >

            <button type="submit" class="btn-primary">Update Profile</button>
            <button type="button" class="btn-cancel" onclick="window.location.href='CustomerController?action=getProfile'">Cancel</button>
        </form>
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
