<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Add New Service Page</title>
    <link rel="stylesheet" href="css/StaffUpdateService.css">
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
   <li><a href="StaffAdminController?action=getProfile">Profile</a></li>
   <li><a href="StaffAdminController?action=logout">Sign out</a></li>
		</ul>
	</nav>

    
   <header id="home">
		<div class="section__container header__container">
			<div class="header__content">
				<h1 style="text-align: center;">
					Add New Service<br />
					<h2>Enter details for the new service package</h2>
				</h1>
<div class="form-container">

        <form action="ServiceController?action=createService" method="post" onsubmit="return validateForm()">
            <div class="form-group">
            
            <label for="serviceName">Enter Service Name:</label>
            <input type="text" id="serviceName" name="serviceName" placeholder="Enter Service Name" required>

            <label for="servicePrice">Service Price (RM):</label>
            <input type="number" id="servicePrice" name="servicePrice" placeholder="Enter Service Price" step="0.01" min="0.01" required>
            
            <label for="serviceDescription">Service Description:</label>
            <textarea id="serviceDescription" name="serviceDescription" placeholder="Enter a detailed description of the service" rows="5" required></textarea>
            
            <button type="submit" class="btn">Submit</button>
        </form>
        
        <form action="addNewPackageController" method="post"
						onsubmit="return validateForm()">
						<div class="form-group">
							<label for="serviceName">Enter Service Name:</label>
            <input type="text" id="serviceName" name="serviceName" placeholder="Enter Service Name" required>
						</div>
						<div class="form-group">
							<label for="servicePrice">Service Price (RM):</label>
            <input type="number" id="servicePrice" name="servicePrice" placeholder="Enter Service Price" step="0.01" min="0.01" required>
            
						</div>

						<div class="form-group">
							<label for="serviceDescription">Service Description:</label>
            <textarea id="serviceDescription" name="serviceDescription" placeholder="Enter a detailed description of the service" rows="5" required></textarea>
            
						</div>

						
					</form>
        
        </div>
    </div>
    </div>
    </header>
	<footer class="footer">
		<div class="main_container footer_container">
			<div class="footer_item">
				<img src="images/nikkospacelogo.png" alt=""
					style="max-width: 35%; height: auto; margin: 0 45%; display: block;">
				<div class="footer_p">Your Pets is Our Priority</div>
			</div>
			<div class="footer_item">
				<h3 class="footer_item_title">Reach Us</h3>
				<ul class="footer_list">
					<a
						href="https://www.google.com/maps/place/Universiti+Teknologi+MARA+(UiTM)+Cawangan+Melaka+Kampus+Jasin/@2.2213964,102.4505406,17z/data=!3m1!4b1!4m6!3m5!1s0x31d1c2904d683dc3:0x216b1d164eba26a1!8m2!3d2.2213964!4d102.4531155!16s%2Fg%2F11cjkvzt2m?entry=ttu">
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
				<p class="footer_copy">Copyright NikkoSpace 2024. All rights
					reserved.</p>
			</div>
		</div>
	</footer>

	<script>
		function validateForm() {
			var packageName = document.getElementById("packageName").value;
			var packageDescription = document
					.getElementById("packageDescription").value;
			var shorthairPrice = document.getElementById("shorthairPrice").value;
			var longhairPrice = document.getElementById("longhairPrice").value;

			if (packageName == "" || packageDescription == ""
					|| shorthairPrice == "" || longhairPrice == "") {
				alert("Please fill in all fields.");
				return false;
			}

			return true;
		}
	</script>
</body>
</html>
