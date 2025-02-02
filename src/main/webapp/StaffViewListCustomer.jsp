<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="java.util.List" %>
<%@ page import="customer.model.Customer" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index Customer Pet List</title>
<link rel="stylesheet" href="css/StaffViewListCustomer.css">
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
   <li><a href="AppointmentController?action=getPendingAppointments">Appointments</a></li>
   <li><a href="CustomerController?action=listCustomer">Customer</a></li>
   <li><a href="ServiceController?action=listServices">Service</a></li>
   <li><a href="StaffAdminController?action=getProfile">Profile</a></li>
   <li><a href="StaffAdminController?action=logout">Logout</a></li>
		</ul>
	</nav>

	<header id="home">
		<div class="section__container header__container">
			<div class="header__content">
				<h1 style="text-align: center;">Customer Profiles</h1>
				<h2>All registered customers</h2>
				<div class="container">
					<table>
            <thead>
                <tr>
                    <th>Customer Name</th>
                    <th>Phone Number</th>
                    <th>Birthdate</th>
                    <th>Gender</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Retrieve the list of customers from the request attribute
                    List<Customer> customers = (List<Customer>) request.getAttribute("customerList");
                    if (customers != null && !customers.isEmpty()) {
                        for (Customer customer : customers) {
                %>
                <tr>
                    
                    <td><%= customer.getUsername() %></td>
                    <td><%= customer.getPhoneNumber() %></td>
                    <td><%= customer.getBirthDate() %></td>
                    <td><%= customer.getGender() %></td>
                    <td><%= customer.getEmail() %></td>
                    <td>
                        <form action="CustomerController" method="get">
    					<input type="hidden" name="action" value="viewCustomer">
    					<input type="hidden" name="custId" value="<%= customer.getCustId() %>">
    					<button class="action-btn view-btn" type="submit">View</button>
						</form>

                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7" style="text-align: center;">No customers found.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
				</div>
			</div>
		</div>
	</header>

	<footer class="footer">
		<div class="main_container footer_container">
			<div class="footer_item">
				<img src="images/nikkospacelogo.png" alt=""
					style="max-width: 35%; height: auto; margin: 0 45%; display: block;">
				<div class="footer_p">Your Pets are Our Priority</div>
			</div>
			<div class="footer_item">
				<h3 class="footer_item_title">Reach Us</h3>
				<ul class="footer_list">
					<a
						href="https://www.google.com/maps/place/Universiti+Teknologi+MARA+(UiTM)+Cawangan+Melaka+Kampus+Jasin">
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

	<script>
		// Dummy JavaScript Functions for View and Edit
		function viewPet(petName) {
			alert("View details for: " + petName);
			// Future implementation: Redirect to a view page or show a modal
		}

		function editPet(petName) {
			alert("Edit details for: " + petName);
			// Future implementation: Redirect to an edit page or show a modal
		}
	</script>
	<script>
		function togglePassword(element, password) {
			if (element.innerText === '*******') {
				element.innerText = password;
			} else {
				element.innerText = '*******';
			}
		}
	</script>
</body>
</html>
