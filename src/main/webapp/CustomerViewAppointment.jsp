<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="StaffAdmin.model.Appointment"%>
<%@ page import="StaffAdmin.model.Result"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Customer View Appointment Details</title>
    <link rel="stylesheet" href="css/AdminViewAppointment.css">
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
            <li><a href="CustomerController?action=getProfile">Profile</a></li>
            <li><a href="CustomerController?action=logout">Logout</a></li>
        </ul>
    </nav>

    <header id="home">
        <div class="section__container header__container">
            <div class="header__content">
                <h1 style="text-align: center;">Your Pet Appointment Details</h1>
                <h2>Details of your pet appointment</h2>
                <div class="container">
                    <table>
                        <thead>
                            <tr>
                                <th>Field</th>
                                <th>Details</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                Appointment appointment = (Appointment) request.getAttribute("appointment");
                                if (appointment != null) {
                            %>
                            <tr>
                                <td>Application ID</td>
                                <td><%= appointment.getAppId() %></td>
                            </tr>
                            <tr>
                                <td>Pet Name</td>
                                <td><%= appointment.getPet() != null ? appointment.getPet().getPetName() : "No Pet" %></td>
                            </tr>
                            <tr>
                                <td>Package</td>
                                <td><%= appointment.getService() != null ? appointment.getService().getServiceName() : "No Service" %></td>
                            </tr>
                            <tr>
                                <td>Appointment Date</td>
                                <td><%= appointment.getAppDate() %></td>
                            </tr>
                            <tr>
                                <td>Appointment Time</td>
                                <td><%= appointment.getAppTime() %></td>
                            </tr>
                            <tr>
                                <td>Remarks</td>
                                <td><%= appointment.getAppRemark() != null ? appointment.getAppRemark() : "None" %></td>
                            </tr>
                            <tr>
                                <td>Status</td>
                                <td><%= appointment.getStatus() != null ? appointment.getStatus() : "No Status" %></td>
                            </tr>
                            <%
                                } else {
                            %>
                            <tr>
                                <td colspan="2">No appointment details found.</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>

                <h2 style="text-align: center;">Result of your pet</h2>
                <div class="container">
                    <table>
                        <thead>
                            <tr>
                                <th>Category</th>
                                <th>Description</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                Result result = (Result) request.getAttribute("result");
                                if (result != null) {
                            %>
                            <tr>
                                <td>Temperament</td>
                                <td><%= result.getTempDescription() %></td>
                            </tr>
                            <tr>
                                <td>Body</td>
                                <td><%= result.getBody() %></td>
                            </tr>
                            <tr>
                                <td>Ear</td>
                                <td><%= result.getEar() %></td>
                            </tr>
                            <tr>
                                <td>Nose</td>
                                <td><%= result.getNose() %></td>
                            </tr>
                            <tr>
                                <td>Tail</td>
                                <td><%= result.getTail() %></td>
                            </tr>
                            <tr>
                                <td>Mouth</td>
                                <td><%= result.getMouth() %></td>
                            </tr>
                            <tr>
                                <td>Others</td>
                                <td><%= result.getOther() %></td>
                            </tr>
                            <%
                                } else {
                            %>
                            <tr>
                                <td colspan="2">No result details found.</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>

                <div class="header__btn">
                    <button
                        onclick="window.location.href='AppointmentController?action=getAppointmentList'">
                        Back <span><i class="ri-arrow-right-line"></i></span>
                    </button>
                </div>
            </div>
        </div>
    </header>

    <footer class="footer">
        <div class="main_container footer_container">
            <!-- Footer content -->
        </div>
    </footer>
</body>
</html>
