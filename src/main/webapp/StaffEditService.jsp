<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="StaffAdmin.model.Service" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Service Details</title>
<link rel="stylesheet" href="css/updatePackageStyle.css">
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
<h1 style="text-align: center;">Update Service Details</h1>
<h2  style="text-align: center;">Detail's of the update service</h2>
<div class="form-wrapper">
    <form action="ServiceController" method="post">
    <input type="hidden" name="action" value="updateService">
    <input type="hidden" name="serviceId" value="<%= ((Service) request.getAttribute("service")).getServiceId() %>">

    <label for="serviceName">Service Name:</label>
    <input type="text" id="serviceName" name="serviceName" value="<%= ((Service) request.getAttribute("service")).getServiceName() %>" required><br><br>

    <label for="servicePrice">Service Price:</label>
    <input type="text" id="servicePrice" name="servicePrice" value="<%= ((Service) request.getAttribute("service")).getServicePrice() %>" required><br><br>

    <label for="serviceDescription">Service Description:</label>
    <textarea id="serviceDescription" name="serviceDescription" rows="5" required><%= ((Service) request.getAttribute("service")).getServiceDescription() %></textarea><br><br>

    <input type="submit" value="Update">
</form>

</div>
<script>
function validateForm() {
    var packageName = document.getElementById("packageName").value.trim();
    var packagePrice = document.getElementById("packagePrice").value.trim();

    if (packageName === "") {
        alert("Please enter the package name.");
        return false;
    }

    if (packagePrice === "" || isNaN(packagePrice) || Number(packagePrice) <= 0) {
        alert("Please enter a valid package price.");
        return false;
    }

    return true;
}

</script>
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
	<script src="https://unpkg.com/scrollreveal"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.js"></script>
	<script src="main.js"></script>
</body>
</html>
