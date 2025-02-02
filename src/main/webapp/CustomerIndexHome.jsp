<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Home</title>
    <link rel="stylesheet" href="styleIndexHome.css">
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
          <h4>Welcome</h4>
          <h1>Nikko<br /><span>Space</span></h1>
          <h2>Love, Treat, Care For Your Pet</h2>
          <p>
            From gentle grooming to expert care, we ensure your cats shine with health and beauty.
          </p>
          <div class="header__btn">
            <button>
              Start Here!
              <span><i class="ri-arrow-right-line"></i></span>
            </button>
          </div>
        </div>
        <div class="header__image">
          <img
            src="images/header-bg.png"
            alt="header-bg"
            class="header__image-bg"
          />
          <img src="images/header.png" alt="header" />
        </div>
      </div>
      <div class="header__bottom"></div>
    </header>

    <section class="section__container intro__container">
      <p class="section__subheader">Intro</p>
      <h2 class="section__header">Get to know us more</h2>
      <div class="intro__grid">
        <div class="intro__card">
          <div class="intro__image">
            <img src="images/intro-1.png" alt="intro" />
          </div>
          <h4>Appointment</h4>
          <p>
            Dont wait! Secure your cat's grooming spot for a fresh and fabulous look!
          </p>
          <a href="AppointmentController?action=getAppointmentList">Read More</a>
        </div>
        <div class="intro__card">
          <div class="intro__image">
            <img src="images/intro-2.png" alt="intro" />
          </div>
          <h4>Pet Profile</h4>
          <p>
            Build a detailed profile for your furry friend to ensure their care is as unique as they are.
          </p>
          <a href="PetController?action=getPetList">Read More</a>
        </div>
        <div class="intro__card">
          <div class="intro__image">
            <img src="images/intro-3.png" alt="intro" />
          </div>
          <h4>Contact Us</h4>
          <p>
            Got a question? Contact us and let’s make your pet’s day even better!
          </p>
          <a href="tel:+601923445265">Read More</a>
        </div>
      </div>
    </section>

    <section class="section__container about__container" id="about">
      <p class="section__subheader">About Us</p>
      <h2 class="section__header">What we can do for you</h2>
      <div class="about__row">
        <div class="about__image">
          <img src="images/about-1.jpg" alt="about" />
        </div>
        <div class="about__content">
          <span><img src="images/about-1-icon.png" alt="about-icon" /></span>
          <h4>Help your feline friends stay happy and healthy</h4>
          <p>
            One of our grooming packages that includes premium shampoo & conditioner specially formulated for all skin & fur types.
          </p>
        </div>
      </div>
      <div class="about__row">
        <div class="about__image">
          <img src="images/about-2.jpg" alt="about" />
        </div>
        <div class="about__content">
          <span><img src="images/about-2-icon.png" alt="about-icon" /></span>
          <h4>Bring out the best with our Show Grooming</h4>
          <p>
            The products we use are commonly used for Show Grooming worth RM300. As cat lovers, we strictly understand that our feline friends need some good pampering too!
          </p>
        </div>
      </div>
      <div class="about__row">
        <div class="about__image">
          <img src="images/about-3.jpg" alt="about" />
        </div>
        <div class="about__content">
          <span><img src="images/about-3-icon.png" alt="about-icon" /></span>
          <h4>Here at NikkoSpace, we provide the best</h4>
          <p>
            Pampering service for our feline friends done by our professionally trained groomers. Our premium grooming includes feline manicure and spa too!
          </p>
        </div>
      </div>
    </section>

    <section class="product" id="store">
      <div class="section__container product__container">
        <p class="section__subheader">Products</p>
        <h2 class="section__header">Featured pet packages</h2>
        <div class="product__grid">
          <div class="product__card">
            <img src="images/product-1.jpg" alt="product" />
            <h4>Daily maintenance package</h4>
            <p>
              Keep your pet looking their best every day with our Daily Maintenance Package.
            </p>
            <h3>Starting with RM35</h3>
          </div>
          <div class="product__card">
            <img src="images/product-2.jpg" alt="product" />
            <h4>Luxe package</h4>
            <p>
              Indulge your pet in luxury with our premium Luxe Package.
            </p>
            <h3>Starting with RM45</h3>
          </div>
          <div class="product__card">
            <img src="images/product-3.jpg" alt="product" />
            <h4>Premium full treatment package</h4>
            <p>
              Pamper your pet from head to tail with our Premium Full Treatment Package.
            </p>
            <h3>Starting with RM85</h3>
          </div>
        </div>
      </div>
    </section>

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
