<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="StaffAdmin.model.Service" %>
<%@ page import="customer.model.Pet" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Create Appointment</title>
    <link rel="stylesheet" href="css/CustomerCreateAppointment.css">
    <script>
    function showPopup() {
        document.getElementById("servicePopup").style.display = "flex"; // Show the popup
    }

    function closePopup() {
        document.getElementById("servicePopup").style.display = "none"; // Hide the popup
    }

    
        window.onload = function() {
            const today = new Date();
            const yyyy = today.getFullYear();
            const mm = String(today.getMonth() + 1).padStart(2, '0');
            const dd = String(today.getDate()).padStart(2, '0');
            const minDate = `${yyyy}-${mm}-${dd}`;
            document.getElementById("appDate").setAttribute("min", minDate);
        };

        function validateDate() {
            const dateInput = document.getElementById("appDate");
            const selectedDate = new Date(dateInput.value);
            const today = new Date();

            if (selectedDate < today) {
                alert("Backdating is not allowed. Please select a valid date.");
                dateInput.value = ""; 
                return false;
            }
            return true;
        }
        
        document.getElementById("appTime").addEventListener("change", function() {
            var timeInput = this.value;
            if (timeInput) {
                var minutes = parseInt(timeInput.split(":")[1]);

                if (![0, 15, 30, 45].includes(minutes)) {
                    alert("Please select a time with a 15-minute interval (e.g., 09:00, 09:15, 09:30, etc.)");
                    this.value = "";
                }
            }
        });
    


        function checkAvailability() {
            const dateInput = document.getElementById("appDate");
            const timeInput = document.getElementById("appTime");
            const messageBox = document.getElementById("availabilityMessage");

            const date = dateInput.value.trim();
            const time = timeInput.value.trim();

            if (!date || !time) {
                messageBox.style.color = "red";
                messageBox.textContent = "Please select a date and time before checking availability.";
                return;
            }

            console.log("Sending Request - Date:", date, "Time:", time);

            fetch("AppointmentController?action=checkAvailability&appDate=" + encodeURIComponent(date) + "&appTime=" + encodeURIComponent(time))
                .then(response => response.json())
                .then(data => {
                    console.log("Response received:", data);

                    if (data.available) {
                        messageBox.style.color = "green";
                        messageBox.textContent = "This time slot is available.";
                    } else {
                        messageBox.style.color = "red";
                        messageBox.textContent = "This time slot is already taken. Please choose another time.";
                    }
                })
                .catch(error => {
                    console.error("Error checking availability:", error);
                    messageBox.style.color = "red";
                    messageBox.textContent = "An error occurred while checking availability.";
                });
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
            <li><a href="CustomerIndexHome.jsp">Home</a></li>
            <li><a href="PetController?action=getPetList">Pet</a></li>
          
            <li><a href="AppointmentController?action=getAppointmentList">Appointment</a></li>
            <li><a href="CustomerServiceController?action=listServices">Service</a></li>
            <li><a href="CustomerController?action=getProfile">Profile</a></li>
            <li><a href="CustomerController?action=logout">Logout</a></li>
        </ul>
    </nav>

    <header id="home">
        <div class="section__container header__container">
            <div class="header__content">
                <h1>Create Your Pet Appointment</h1>
                <div class="form-wrapper">
                    <form action="AppointmentController" method="post" onsubmit="return validateDate()">
                        <input type="hidden" name="action" value="createAppointment">

                        <!-- Select Pet -->
                        <label for="petID">Choose Pet:</label>
                        <select name="petID" id="petID" required>
                            <option value="" disabled selected>-- Select Your Pet --</option>
                            <%
                                List<Pet> petList = (List<Pet>) request.getAttribute("petList");
                                if (petList != null && !petList.isEmpty()) {
                                    for (Pet pet : petList) {
                            %>
                                <option value="<%= pet.getPetID() %>"><%= pet.getPetName() %></option>
                            <%
                                    }
                                } else {
                            %>
                                <option value="" disabled>No pets available</option>
                            <%
                                }
                            %>
                        </select>

                        <label for="serviceId">Select Service:</label>
<a href="#" class="view-service-link" onclick="showPopup()">View Service Details</a>
                        <select name="serviceId" id="serviceId" required>
                            <option value="" disabled selected>-- Select Service --</option>
                            <%
                                List<Service> serviceList = (List<Service>) request.getAttribute("serviceList");
                                if (serviceList != null && !serviceList.isEmpty()) {
                                    for (Service service : serviceList) {
                            %>
                                <option value="<%= service.getServiceId() %>"><%= service.getServiceName() %></option>
                            <%
                                    }
                                } else {
                            %>
                                <option value="" disabled>No services available</option>
                            <%
                                }
                            %>
                        </select>

                        <label for="appDate">Appointment Date:</label>
                        <input type="date" name="appDate" id="appDate" required>

                       <label for="appTime">Appointment Time:</label>
						<input type="time" name="appTime" id="appTime" required min="09:00" max="21:00" step="900">

                        <label for="appRemark">Remarks:</label>
                        <textarea name="appRemark" id="appRemark" rows="4" placeholder="Enter any remarks"></textarea>

                        <p id="availabilityMessage" style="font-weight: bold;"></p>
                        <input type="reset" value="Reset" class="btn-reset">
                        <input type="button" value="Check" class="btn-check" onclick="checkAvailability()">
                        <input type="submit" value="Submit" class="btn-submit">
                    </form>
                </div>
            </div>
        </div>
    </header>

    <div id="servicePopup" class="popup-container" style="display: none;">
    <div class="popup-content">
        <a href="#" class="popup-close" onclick="closePopup()">× Close</a>
        <div class="packages-container">
            <% if (serviceList != null && !serviceList.isEmpty()) {
                for (Service service : serviceList) { %>
            <div class="package-card">
                <h3><%= service.getServiceName() %></h3>
                <p><%= service.getServiceDescription() %></p>
            </div>
            <% } } else { %>
            <p>No services available</p>
            <% } %>
        </div>
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
</body>
</html>
