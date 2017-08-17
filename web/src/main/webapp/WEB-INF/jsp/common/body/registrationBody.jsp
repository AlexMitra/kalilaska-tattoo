<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<main class="body-container">
	<div class="center-view-body-content-container">
	    <div class="login-registration-form-container">
	    	<div class="login-signup-link-title">                
                <div class="login-signup-link-container">
                    
                        <form id="login-view-link" action="login.html" method="POST">
                            <input type="hidden" name="command" value="login_view" />
                        </form>
                        <button class="login-signup-link" type="submit" form="login-view-link" value="">
                            <fmt:message key="login.signup.title.login" bundle="${ rb }" />
                        </button>
                    
                </div>
                <span class="signup-title"><fmt:message key="login.signup.title.signup" bundle="${ rb }" /></span>
        	</div>
	        <form  enctype="utf8">
	            <div class="form-group">
	                <label class="form-input-style" for="registrationName"><fmt:message key="form.name" bundle="${ rb }" /></label>
	                <input type="text" class="form-control" id="registrationName" placeholder="Name" name="registration_name">
	            </div>
	            <div class="form-group">
	                <label class="form-input-style" for="registrationEmail"><fmt:message key="form.email" bundle="${ rb }" /></label>
	                <input type="email" class="form-control" id="registrationEmail" placeholder="Email" name="registration_email">
	            </div>
	            <div class="form-group">
	                <label class="form-input-style" for="registrationPassword"><fmt:message key="form.password" bundle="${ rb }" /></label>
	                <input type="password" class="form-control" id="registrationPassword" placeholder="Password" name="registration_pass">
	            </div>
	
	            <button type="submit" class="btn btn-default form-input-style"><fmt:message key="form.createAccount.button" bundle="${ rb }" /></button>
	        </form>
	    </div>
    </div>
</main>
