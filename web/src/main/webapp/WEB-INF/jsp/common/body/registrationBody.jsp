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
	        <form id="registration-account-form" action="signup.html" method="POST" enctype="utf8">
	        	<input type="hidden" name="command" value="registration_account" />
	            <div class="form-group">
	                <label class="form-label-style" for="registration-account-name"><fmt:message key="form.name" bundle="${ rb }" /></label>
	                <input type="text" class="form-control" id="registration-account-name" placeholder="Name" name="registration_account_name">
	            </div>
	            <div class="form-group">
	                <label class="form-label-style" for="registration-account-email"><fmt:message key="form.email" bundle="${ rb }" /></label>
	                <input type="email" class="form-control" id="registration-account-email" placeholder="Email" name="registration_account_email">
	            </div>
	            <div class="form-group">
	                <label class="form-label-style" for="registration-account-password"><fmt:message key="form.password" bundle="${ rb }" /></label>
	                <input type="password" class="form-control" id="registration-account-password" placeholder="Password" name="registration_account_pass">
	            </div>
	            <div class="form-group">
	                <label class="form-label-style" for="registration-account-confirm-password"><fmt:message key="form.confirmPassword" bundle="${ rb }" /></label>
	                <input type="password" class="form-control" id="registration-account-confirm-password" placeholder="Confirm password" name="registration_account_confirm_pass">
	            </div>
	            
	            <div class="form-group warning-message">
		            <span><c:out value="${registerAccountFailure}"/></span>
		        </div>
	
	            <button type="submit" class="btn btn-default form-input-style"><fmt:message key="form.createAccount.button" bundle="${ rb }" /></button>
	        </form>
	        <c:set var="registerAccountFailure" value="${null}" scope="session"/>
	        
	        <script>
		        $(document).ready(function() {
		            $('#registration-account-form').formValidation({
		                framework: 'bootstrap',
		                icon: {
		                    valid: 'glyphicon glyphicon-ok',
		                    invalid: 'glyphicon glyphicon-remove',
		                    validating: 'glyphicon glyphicon-refresh'
		                },
		                fields: {
		                	registration_account_name: {
		                        validators: {
		                            notEmpty: {
		                                message: 'Name is required'
		                            },
		                            stringLength: {
		                                min: 5,
		                                max: 30,
		                                message: 'Name must be more than 5 and less than 30 characters long'
		                            },
		                            regexp: {
		                                regexp: /^(([A-Za-zА-Яа-яё]+)([\wА-Яа-яё]+)?( [A-Za-zА-Яа-яё]+)?){5,30}$/,
		                                message: 'Name can only consist of latin and cyrillic letters, numbers, _ and one space'
		                            }
		                        }
		                    },
		                    registration_account_email: {
		                        validators: {
		                            notEmpty: {
		                                message: 'Email is required'
		                            },			                            
		                            regexp: {
		                                regexp: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
		                                message: 'The input is not a valid email address'
		                            }
		                        }
		                    },
		                    registration_account_pass: {
		                        validators: {
		                        	notEmpty: {
		                                message: 'The password is required'
		                            },
		                            stringLength: {
		                                min: 3,
		                                max: 30,
		                                message: 'Password must be more than 3 and less than 30 characters long'
		                            },
		                            regexp: {
		                                regexp: /([A-Za-z0-9])/,
		                                message: 'Password can only consist of latin symbols and numbers'
		                            }
		                        }
		                    },
		                    registration_account_confirm_pass: {
		                        validators: {
		                        	notEmpty: {
		                                message: 'The confirm password is required'
		                            },
		                            stringLength: {
		                                min: 3,
		                                max: 30,
		                                message: 'Password must be more than 3 and less than 30 characters long'
		                            },
		                            regexp: {
		                                regexp: /([A-Za-z0-9])/,
		                                message: 'Password can only consist of latin symbols and numbers'
		                            },
		                            identical: {
                                        field: 'registration_account_pass',
                                        message: 'The password and its confirm are not the same'
                                    }
		                        }
		                    }
		                }
		            });
		        });
		      </script>
	    </div>
    </div>
</main>
