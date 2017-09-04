<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



    <div class="modal fade" id="add-account-dialog" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content" align="left">
                <div class="modal-header">
                    <button id="add-account-dialog-close" type="button" class="close" data-dismiss="modal" aria-label="Close" ><span aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title"><fmt:message key="personalArea.allAccounts.dialog.addAccount.title" bundle="${ rb }" /></h3>
                </div>
                <div class="modal-body">                	
					<div class="dialog-form-screen">
	                	<form id="add-account-form" class="form-horizontal" action="personalArea-addAccount.html" method="POST" enctype="utf8">
	                		<input name="command" type="hidden" value="personal_area_add_account"/>
	                		
	                		<div class="form-group">
	                            <label class="control-label" for="add-account-name"><fmt:message key="form.name" bundle="${ rb }" /></label>
	                            <input type="text" class="form-control" id="add-account-name" name="registration_account_name"/>                            
	                        </div>
	                        
	                        <div class="form-group">
	                            <label class="control-label" for="add-account-email"><fmt:message key="form.email" bundle="${ rb }" /></label>
	                            <input type="email" class="form-control" id="add-account-email" name="registration_account_email"/>                            
	                        </div>
	                        
	                        <div class="form-group">
	                            <label class="control-label" for="add-account-password"><fmt:message key="form.password" bundle="${ rb }" /></label>
	                            <input type="password" class="form-control" id="add-account-password" name="registration_account_pass"/>                            
	                        </div>
	                        <div class="form-group">
	                            <label class="control-label" for="add-account-confirm-password"><fmt:message key="form.confirmPassword" bundle="${ rb }" /></label>
	                            <input type="password" class="form-control" id="add-account-confirm-password" name="registration_account_confirm_pass"/>                            
	                        </div>
	                        <div class="modal-footer">                	
			                    <button id="add-account-dialog-close2" type="button" class="btn btn-default" data-dismiss="modal" ><fmt:message key="form.close.button" bundle="${ rb }" /></button>
			                    <button id="add-account-add-button" class="btn btn-primary" type="submit" form="add-account-form" onclick=""><fmt:message key="form.createAccount.button" bundle="${ rb }" /></button>
                			</div>
	                    </form>
	                    
                    </div>
                </div>
                
                <script>
			        $(document).ready(function() {
			            $('#add-account-form').formValidation({
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
			                            },
			                            identical: {
	                                        field: 'registration_account_pass',
	                                        message: 'The password and its confirm are not the same'
	                                    }
			                        }
			                    }
			                    
			                }
			                
			            }).on('success.form.fv', function(e) {
			            	accountsTableAddButton.addAccount();
	                    })
			        });
			      </script>
            </div>
        </div>
    </div>

