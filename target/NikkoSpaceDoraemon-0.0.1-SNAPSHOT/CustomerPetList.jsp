<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
<%@ page import="customer.model.Pet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Customer Pet List</title>
    <link rel="stylesheet" href="css/CustomerPetList.css">
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
            <li><a href="CustomerServiceController?action=listServices">Service</a></li>
            <li><a href="CustomerController?action=getProfile">Profile</a></li>
            <li><a href="CustomerController?action=logout">Sign out</a></li>
        </ul>
    </nav>

    <header id="home">
    <div class="section__container header__container">
        <div class="header__content">
            <h1 style="text-align: center;">Your Pet Profiles</h1>
            <h2 style="text-align: center;">All registered pet profiles</h2>
            <div class="container">
                <table>
                    <thead>
                        <tr>
                            <th>Pet Name</th>
                            <th>Pet Weight (kg)</th>
                            <th>Pet ID</th>
                            <th>Pet Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            // Retrieve the pet list from the request attribute
                            List<Pet> petList = (List<Pet>) request.getAttribute("petList");
                            
                            if (petList != null && !petList.isEmpty()) {
                                for (Pet pet : petList) {
                        %>
                        <tr>
                            <td><%= pet.getPetName() %></td>
                            <td><%= pet.getPetWeight() %></td>
                            <td><%= pet.getPetID() %></td>
                            <td><%= pet.getPetStatus() != null ? pet.getPetStatus() : "Unknown" %></td>
                            <td>
                                <div class="btn-container">
                                    <form action="PetController" method="get">
                                        <input type="hidden" name="action" value="viewPet">
                                        <input type="hidden" name="petID" value="<%= pet.getPetID() %>">
                                        <button class="action-btn view-btn" type="submit">View</button>
                                    </form>
                                    <form action="PetController" method="get">
                                        <input type="hidden" name="action" value="updatePet">
                                        <input type="hidden" name="petID" value="<%= pet.getPetID() %>">
                                        <button class="action-btn update-btn" type="submit">Update</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="5" style="text-align: center;">No pets found.</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
            <div class="header__btn">
                <button onclick="window.location.href='CustomerCreatePet.jsp'">
                    Add Pet <span><i class="ri-arrow-right-line"></i></span>
                </button>
            </div>
        </div>
    </div>
</header>


    <footer class="footer">
        <div class="main_container footer_container">
            <div class="footer_item">
                <img src="images/nikkospacelogo.png" alt="" style="max-width: 35%; height: auto; margin: 0 45%; display: block;">
                <div class="footer_p">Your Pets Are Our Priority</div>
            </div>
            <div class="footer_item">
                <h3 class="footer_item_title">Reach Us</h3>
                <ul class="footer_list">
                    <li class="footer_list_item"><a href="https://www.google.com/maps/place/...">Jasin, Melaka</a></li>
                    <li class="footer_list_item"><a href="mailto:capal@gmail.com">NikkoSpace@gmail.com</a></li>
                    <li class="footer_list_item"><a href="tel:+601923445265">+6019-2344 5265</a></li>
                    <li class="footer_list_item"><a href="tel:+601762500959">+6017 6250 0959</a></li>
                </ul>
            </div>
            <div class="footer_item">
                <h3 class="footer_item_title">Support</h3>
                <ul class="footer_list">
                    <li class="footer_list_item"><a href="product.html">Our Store</a></li>
                    <li class="footer_list_item"><a href="login.html">Login / Register</a></li>
                    <li class="footer_list_item"><a href="cart.html">Cart</a></li>
                    <li class="footer_list_item"><a href="product.html">Shop</a></li>
                </ul>
            </div>
            <div class="footer_item">
                <h3 class="footer_item_title">Help</h3>
                <ul class="footer_list">
                    <li class="footer_list_item"><a href="#">Privacy Policy</a></li>
                    <li class="footer_list_item"><a href="#">Terms of Use</a></li>
                    <li class="footer_list_item"><a href="#">FAQ's</a></li>
                    <li class="footer_list_item"><a href="#">Contact</a></li>
                </ul>
            </div>
        </div>
        <div class="footer_bottom">
            <div class="footer_bottom_container">
                <p class="footer_copy">Copyright NikkoSpace 2024. All rights reserved.</p>
            </div>
        </div>
    </footer>
</body>
</html>
