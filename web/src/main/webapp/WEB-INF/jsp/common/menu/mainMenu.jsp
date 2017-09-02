<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="all-menu-container">
            <div class="site-name-container">
                <span>KALILASKA<i class="fa fa-hand-peace-o" aria-hidden="true"></i>TATTOO</span>
            </div>
            <div class="menu-container">
                
                    <div class="menu-item">
                        <form id="home-view-link" action="home.html" method="GET">
                            <input type="hidden" name="command" value="home_view" />
                        </form>
                        <button class="menu-item-link" type="submit" form="home-view-link" value="">
                            <fmt:message key="menu.bar.home" bundle="${ rb }" />
                        </button>
                    </div>
                    
                    <div class="menu-item">
                        <form id="masters-view-link" action="masters.html" method="GET">
                            <input type="hidden" name="command" value="masters_view" />
                        </form>
                        <button class="menu-item-link" type="submit" form="masters-view-link" value="">
                            <fmt:message key="menu.bar.masters" bundle="${ rb }" />
                        </button>
                    </div>

<!--                     <div class="menu-item"> -->
<!--                         <form id="login-view-link" action="login.html" method="POST"> -->
<!--                             <input type="hidden" name="command" value="login_view" /> -->
<!--                         </form> -->
<!--                         <button class="menu-item-link" type="submit" form="login-view-link" value=""> -->
<%--                             <fmt:message key="menu.bar.login" bundle="${ rb }" /> --%>
<!--                         </button> -->
<!--                     </div> -->

<!--                     <div class="menu-item"> -->
<!--                         <form id="registration-view-link" action="registration.html" method="POST"> -->
<!--                             <input type="hidden" name="command" value="registration_view" /> -->
<!--                         </form> -->
<!--                         <button class="menu-item-link" type="submit" form="registration-view-link" value=""> -->
<%--                             <fmt:message key="menu.bar.registration" bundle="${ rb }" /> --%>
<!--                         </button> -->
<!--                     </div> -->

                    <div class="menu-item">
                        <form id="personalArea-view-link" action="personalArea.html" method="GET">
                            <input type="hidden" name="command" value="personal_area_view" />
                        </form>
                        <button class="menu-item-link" type="submit" form="personalArea-view-link" value="">
                            <fmt:message key="menu.bar.personalArea" bundle="${ rb }" />
                        </button>
                    </div>
                    
                    <div class="menu-item">
                        <form id="aboutUs-view-link" action="about-us.html" method="GET">
                            <input type="hidden" name="command" value="about_us_view" />
                        </form>
                        <button class="menu-item-link" type="submit" form="aboutUs-view-link" value="">
                            <fmt:message key="menu.bar.aboutUs" bundle="${ rb }" />
                        </button>
                    </div>
                
            </div>
 </nav>