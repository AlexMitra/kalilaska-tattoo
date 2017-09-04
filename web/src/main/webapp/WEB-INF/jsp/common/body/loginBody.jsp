<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<main class="body-container">
<div class="center-view-body-content-container">
    <div class="login-registration-form-container">
    	<div class="login-signup-link-title">
                <span class="login-title"><fmt:message key="login.signup.title.login" bundle="${ rb }" /></span>
                <div class="login-signup-link-container">
                    
                        <form id="registration-view-link" action="signup.html" method="POST">
                            <input type="hidden" name="command" value="registration_view" />
                        </form>
                        <button class="login-signup-link" type="submit" form="registration-view-link" value="">
                            <fmt:message key="login.signup.title.signup" bundle="${ rb }" />
                        </button>
                    
                </div>
        </div>
        
        <form id="authenticationForm" action="login.html" method="POST" enctype="utf8">
        	<input type="hidden" name="command" value="authentication" />
            <div class="form-group">
                <label class="form-label-style" for="authenticationName"><fmt:message key="form.name" bundle="${ rb }" /></label>
                <input type="text" class="form-control" id="authenticationName" placeholder="Name" name="authentication_name">
            </div>
            <div class="form-group">
                <label class="form-label-style" for="authenticationPassword"><fmt:message key="form.password" bundle="${ rb }" /></label>
                <input type="password" class="form-control" id="authenticationPassword" placeholder="Password", name="authentication_pass">
            </div>
            <div class="form-group warning-message">
                <span><c:out value="${invalidNamePassMessage}"/></span>
            </div>
<!--             <div class="form-group"> -->
<%--                 <span><c:out value="${accountForbiddenMessage}"/></span> --%>
<!--             </div> -->

            <button type="submit" class="btn btn-default form-input-style"><fmt:message key="form.logIn.button" bundle="${ rb }" /></button>
        </form>
        <c:set var="invalidNamePassMessage" value="${null}" scope="session"/>
<%--         <c:set var="accountForbiddenMessage" value="${null}" scope="session"/> --%>
        
        <script>
        $(document).ready(function() {
            $('#authenticationForm').formValidation({
                framework: 'bootstrap',
                icon: {
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                	authentication_name: {
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
                    authentication_pass: {
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
                    }
                }
            });
        });
      </script>
    </div>
    </div>
</main>
