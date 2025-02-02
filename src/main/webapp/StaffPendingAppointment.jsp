<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="StaffAdmin.model.Appointment"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Pending Appointments</title>
    <link rel="stylesheet" href="css/IndexCustomerListAppointment.css">
</head>
<body>
    <nav>
        <div class="nav__header">
            <div class="nav__logo">
                <a href="#">NikkoSpace</a>
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
            <h1 style="text-align: center;">Manage Appointments</h1>
            <div style="text-align: center; margin: 20px 0;">
            <button class="btn-pending" onclick="window.location.href='AppointmentController?action=getPendingAppointments'">Pending</button>
			<button class="btn-in-progress" onclick="window.location.href='AppointmentController?action=getApprovedAppointments'">Approved</button>
			<button class="btn-in-progress" onclick="window.location.href='AppointmentController?action=getInProgressAppointments'">In-Progress</button>
			<button class="btn-complete" onclick="window.location.href='AppointmentController?action=getCompletedAppointments'">Completed</button>
			<button class="btn-rejected" onclick="window.location.href='AppointmentController?action=getRejectedAppointments'">Rejected</button>
            </div>
            <section class="appointment-list">
                <div class="container">
                    <table>
                        <thead>
                            <tr>
                                <th>Application ID</th>
                                <th>Pet Name</th>
                                <th>Service Name</th>
                                <th>Appointment Date</th>
                                <th>Appointment Time</th>
                                <th>Remarks</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
    						List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
    						if (appointments != null && !appointments.isEmpty()) {
    						    for (Appointment appointment : appointments) {
							%>

                            <tr id="appointment-<%= appointment.getAppId() %>">
                                <td><%= appointment.getAppId() %></td>
                                <td><%= appointment.getPetName() %></td>
                                <td><%= appointment.getServiceName() %></td>
                                <td><%= appointment.getAppDate() %></td>
                                <td><%= appointment.getAppTime() %></td>
                                <td><%= appointment.getAppRemark() %></td>
                                <td id="status-<%= appointment.getAppId() %>"><%= appointment.getStatus() %></td>
                                <td id="actions-<%= appointment.getAppId() %>">
                                    <button type="button" class="btn-accept" onclick="updateStatus('<%= appointment.getAppId() %>', 'Approved')">Approve</button>
                                    <button type="button" class="btn-reject" onclick="updateStatus('<%= appointment.getAppId() %>', 'Rejected')">Reject</button>
                                </td>
                            </tr>
                            <%
                                    }
                                } else {
                            %>
                            <tr>
                                <td colspan="8" style="text-align: center;">No Pending Appointments Found</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </div>
</header>

    <script>
    function updateStatus(appId, status) {
        if (!confirm("Are you sure you want to update Appointment " + appId + " to " + status + "?")) {
            return;
        }

        let formData = new URLSearchParams();
        formData.append("appId", appId);
        formData.append("status", status);

        fetch('AppointmentController?action=updateStatus', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData.toString()
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("Appointment " + appId + " status updated to " + status);
                location.reload(); // Refresh the page to reflect changes
            } else {
                alert("Failed to update appointment status. Please try again.");
            }
        })
        .catch(error => console.error("Error:", error));
    }

    </script>
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
</body>
</html>
