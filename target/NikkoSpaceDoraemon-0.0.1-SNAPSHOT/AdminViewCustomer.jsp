<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="customer.model.Customer" %>
<%@ page import="customer.model.Pet" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Details</title>
<link rel="stylesheet" href="css/StaffViewCustomer.css">
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
   			<li><a href="StaffAdminController?action=listStaff">Staff</a></li> 
   			<li><a href="StaffAdminController?action=getProfile">Profile</a></li>
   			<li><a href="StaffAdminController?action=logout">Sign out</a></li> 
        </ul>
    </nav>

    <header id="home">
        <div class="section__container header__container">
            <div class="header__content">
                <h1 style="text-align: center;">Customer Details</h1>
                <div class="customer-details">
                    <div class="customer-info" style="display: flex; align-items: center; gap: 20px;">
                        <img src="images/default_profile.jpg" alt="Profile Picture"
                             style="width: 150px; height: 150px; border-radius: 50%; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);">
                        <div>
                            <%
                                // Retrieve customer and pets from request attributes
                                Customer customer = (Customer) request.getAttribute("customer");
                                List<Pet> pets = (List<Pet>) request.getAttribute("petList");

                                if (customer != null) {
                            %>
                            <h2><%= customer.getUsername() %></h2>
                            <p><strong>Phone:</strong> <%= customer.getPhoneNumber() %></p>
                            <p><strong>Birthdate:</strong> <%= customer.getBirthDate() %></p>
                            <p><strong>Gender:</strong> <%= customer.getGender() %></p>
                            <p><strong>Email:</strong> <%= customer.getEmail() %></p>
                            <p><strong>Password:</strong> <span id="password" style="cursor: pointer;"
                                                               onclick="togglePassword(this, '<%= customer.getPassword() %>')">
                                ********</span>
                            </p>
                            <%
                                } else {
                            %>
                            <p style="color: red;">Customer not found.</p>
                            <%
                                }
                            %>
                        </div>
                    </div>
                    <div class="pet-details" style="margin-top: 30px;">
                        <h2>Pets</h2>
                        <div class="pet-card" style="display: flex; gap: 20px; margin-top: 20px;">
                            <%
                                if (pets != null && !pets.isEmpty()) {
                                    for (Pet pet : pets) {
                            %>
                            <div style="border: 1px solid #ccc; border-radius: 10px; padding: 20px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);">
                                <img src="images/default_pet.jpg" alt="Pet Picture"
                                     style="width: 100px; height: 100px; border-radius: 10px;">
                                <h3><%= pet.getPetName() %></h3>
                                <p><strong>Weight (KG):</strong> <%= pet.getPetWeight() %></p>
                                <p><strong>Status:</strong> <%= pet.getPetStatus() %></p>
                            </div>
                            <%
                                    }
                                } else {
                            %>
                            <p style="color: gray;">No pets available for this customer.</p>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>
                <br>
                <div class="header__btn">
                    <form action="CustomerController" method="get">
                <input type="hidden" name="action" value="listCustomer">
                <button type="submit" class="btn-primary">Back<span><i class="ri-arrow-right-line"></i></span></button>
            </form>
                </div>
                
            </div>
        </div>
    </header>

    <footer class="footer">
        <div class="main_container footer_container">
            <div class="footer_item">
                <img src="images/nikkospacelogo.png" alt="" style="max-width: 35%; height: auto; margin: 0 45%; display: block;">
                <div class="footer_p">Your Pets are Our Priority</div>
            </div>
            <div class="footer_item">
                <h3 class="footer_item_title">Reach Us</h3>
                <ul class="footer_list">
                    <a href="#"><li class="footer_list_item">Jasin, Melaka</li></a>
                    <a href="mailto:NikkoSpace@gmail.com"><li class="footer_list_item">NikkoSpace@gmail.com</li></a>
                    <a href="tel:+601923445265"><li class="footer_list_item">+6019-2344 5265</li></a>
                    <a href="tel:+601762500959"><li class="footer_list_item">+6017 6250 0959</li></a>
                </ul>
            </div>
            <div class="footer_item">
                <h3 class="footer_item_title">Support</h3>
                <ul class="footer_list">
                    <a href="#"><li class="footer_list_item">Our Store</li></a>
                    <a href="#"><li class="footer_list_item">Login / Register</li></a>
                    <a href="#"><li class="footer_list_item">Cart</li></a>
                    <a href="#"><li class="footer_list_item">Shop</li></a>
                </ul>
            </div>
            <div class="footer_item">
                <h3 class="footer_item_title">Help</h3>
                <ul class="footer_list">
                    <a href="#"><li class="footer_list_item">Privacy policy</li></a>
                    <a href="#"><li class="footer_list_item">Terms of use</li></a>
                    <a href="#"><li class="footer_list_item">FAQ's</li></a>
                    <a href="#"><li class="footer_list_item">Contact</li></a>
                </ul>
            </div>
        </div>
        <div class="footer_bottom">
            <div class="footer_bottom_container">
                <p class="footer_copy">Copyright NikkoSpace 2024. All rights reserved.</p>
            </div>
        </div>
    </footer>
    <script>
        function togglePassword(element, password) {
            if (element.innerText === '********') {
                element.innerText = password; // Show password
            } else {
                element.innerText = '********'; // Hide password
            }
        }
    </script>

</body>
</html>
