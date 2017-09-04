<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



    <div class="modal fade" id="edit-account-dialog" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content" align="left">
                <div class="modal-header">
                    <button id="edit-account-dialog-close" type="button" class="close" data-dismiss="modal" aria-label="Close" ><span aria-hidden="true">&times;</span></button>
                    <h3 id="edit-account-dialog-title" class="modal-title"><fmt:message key="personalArea.allAccounts.dialog.editAccount.title" bundle="${ rb }" /> </h3>
                </div>
                <div class="modal-body">                	
					<div class="dialog-form-screen">
	                	<form id="edit-account-form" class="form-horizontal" action="personalArea-editAccount.html" method="POST" enctype="utf8">
	                		<input name="command" type="hidden" value="personal_area_edit_account"/>
	                		<input id="edit-account-id" name="edit_account_id" type="hidden" value=""/>
	                		
	                		<div class="form-group">
	                            <label class="control-label" for="edit-account-name"><fmt:message key="form.name" bundle="${ rb }" /></label>
	                            <input id="edit-account-name" type="text" class="form-control" name="edit_account_name" value="" oninput="accountsTableEditButton.updateInputValue(this.id)"/>
	                        </div>
	                        
	                        <div class="form-group">
	                            <label class="control-label" for="edit-account-email"><fmt:message key="form.email" bundle="${ rb }" /></label>
	                            <input id="edit-account-email" type="email" class="form-control" name="edit_account_email" value="" oninput="accountsTableEditButton.updateInputValue(this.id)"/>
	                        </div>
	                        
	                        <div class="form-group">
	                            <label class="control-label" for="edit-account-phone"><fmt:message key="form.phone" bundle="${ rb }" /></label>
	                            <input id="edit-account-phone" type="text" class="form-control" id="edit-account-phone" name="edit_account_phone" placeholder="297776655" value="" oninput="accountsTableEditButton.updateInputValue(this.id)"/>
	                        </div>
	                        <div class="form-group">                        
	                        	<div class="dropdown">
		  							<button class="btn btn-default dropdown-toggle" type="button" id="edit-account-rolesList" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
		    						<fmt:message key="personalArea.allAccounts.dialog.roles.button" bundle="${ rb }" /><span class="caret"></span>
		  							</button>
	  								<c:if test="${personalAreaViewBean.getRole() eq 'Administrator'}">
		  								<c:set var="allRoles" value="${personalAreaViewBean.getRoles()}"/>
		  							
			  							<ul class="dropdown-menu" aria-labelledby="edit-account-rolesList">
			  								<c:forEach items="${allRoles}" var="role">		
			    							<li><a id="li-${role.getName()}" href="#" onclick="accountsTableEditButton.changeRole(this.id)"><c:out value="${role.getName()}"/></a></li>    							
			    							</c:forEach>
			  							</ul>
		  							</c:if>
								</div>
								<br>
							
								<div id="edit-account-role-marker"></div>
								<input id="edit-account-role" type="hidden" name="edit_account_role" value=""/>
							</div>
	                        
	                        <div class="modal-footer">                	
			                    <button id="edit-account-dialog-close2" type="button" class="btn btn-default" data-dismiss="modal" ><fmt:message key="form.close.button" bundle="${ rb }" /></button>
			                    <button id="edit-account-edit-button" class="btn btn-primary" type="submit" form="edit-account-form" ><fmt:message key="form.editAccount.button" bundle="${ rb }" /></button>
                			</div>
	                    </form>
	                    
                    </div>
                </div>
                
                <script>
			        $(document).ready(function() {
			            $('#edit-account-form').formValidation({
			                framework: 'bootstrap',
			                icon: {
			                    valid: 'glyphicon glyphicon-ok',
			                    invalid: 'glyphicon glyphicon-remove',
			                    validating: 'glyphicon glyphicon-refresh'
			                },
			                fields: {
			                	edit_account_name: {
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
			                                regexp: /^[\wА-Яа-яё]+( [A-Za-zА-Яа-яё]+)?$/,
			                                message: 'Name can only consist of latin and cyrillic letters, numbers, _ and one space'
			                            }
			                        }
			                    },
			                    edit_account_email: {
			                        validators: {
			                            notEmpty: {
			                                message: 'Email is required'
			                            },			                            
			                            regexp: {
			                                regexp: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
			                                message: 'The input is not a valid email address'
			                            }
			                        }
			                    }			                    			                    			                    
			                }
			                
			            }).on('success.form.fv', function(e) {
			            	accountsTableEditButton.editAccount();
	                    })
			        });			        
			      </script>
            </div>
        </div>
    </div>