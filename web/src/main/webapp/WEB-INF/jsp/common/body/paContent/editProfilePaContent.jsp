<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="kalilaska" uri="/WEB-INF/tld/customtag.tld" %>


    <div class="all-area-white-background-container">
	    <div class="personal-area-center-content-container">
	        <div class="personal-area-center-form-container">
		        <div class="edit-profile-title-container">
		            <span>Edit profile</span>
		        </div>
		            
		        <form id="edit-profile-form" action="personalArea.html" method="POST" enctype="utf8">
		        	<input type="hidden" name="command" value="personal_area_edit_profile" />
		        	<input type="hidden" name="edit_profile_id" value="${personalAreaViewBean.getId()}" />
		            <div class="form-group">
		                <label class="form-label-style" for="edit-profile-name">Name*</label>
		                <input id="edit-profile-name" type="text" class="form-control" placeholder="Name" name="edit_profile_name" value="${personalAreaViewBean.getName()}" oninput="editProfile.updateInputValue(this.id)">
		            </div>
		            <div class="form-group">
		                <label class="form-label-style" for="edit-profile-email">Email*</label>
		                <input id="edit-profile-email" type="email" class="form-control" placeholder="Email" name="edit_profile_email" value="${personalAreaViewBean.getEmail()}" oninput="editProfile.updateInputValue(this.id)">
		            </div>
		            <div class="form-group">
		                <label class="form-label-style" for="edit-profile-phone">Phone</label>
		                <input id="edit-profile-phone" type="text" class="form-control" placeholder="297776655" name="edit_profile_phone" value="${personalAreaViewBean.getPhone()}" oninput="editProfile.updateInputValue(this.id)">
		            </div>
		            <c:if test="${personalAreaViewBean.getRole() eq masterRole}">
			            <div class="form-group">		            
			               <div class="dropdown">
					  		    <button class="btn btn-default dropdown-toggle" type="button" id="edit-profile-styleList" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
					    	        Styles<span class="caret"></span>
					  		    </button>		
			                    <ul class="dropdown-menu" aria-labelledby="edit-profile-styleList">
									<kalilaska:all-style-list styleList="${allStyleList}"/>
			                     </ul>		  		
			                </div>			                
			                
			                <div class="edit-profile-style-list-container">
			                    <ul id="edit-profile-own-style-list" class="edit-profile-style-list">
									<kalilaska:own-style-list styleList="${personalAreaViewBean.getStyles()}"/>
						  	    </ul>
			                </div>
	                
	            		</div>
			            <div class="form-group">
			                <label class="form-label-style" for="edit-profile-about">About</label>
			                <textarea id="edit-profile-about" form="edit-profile-form" class="form-control" rows="4" name="edit_profile_about" oninput="editProfile.updateInputValue(this.id)">${personalAreaViewBean.getAboutInfo()}</textarea>                		                
			            </div>
		            </c:if>
		            <div class="form-group">
		                <label class="form-label-style" for="edit-profile-pass">Password</label>
		                <input type="password" class="form-control" id="edit-profile-pass" placeholder="Password" name="edit_profile_pass" oninput="editProfile.updateInputValue(this.id)">
		            </div>
		            <div class="form-group">
		                <label class="form-label-style" for="edit-profile-confirm-pass">Password</label>
		                <input type="password" class="form-control" id="edit-profile-confirm-pass" placeholder="Confirm password" name="edit_profile_confirm_pass" oninput="editProfile.updateInputValue(this.id)">
		            </div>

		            <div class="form-group warning-message">
		                <span><c:out value="${editProfileFailure}"/></span>
		            </div>

		
		            <button type="submit" class="btn btn-default form-input-style">Edit profile</button>
		        </form>
		       <c:set var="editProfileFailure" value="${null}" scope="session"/>
		        
		
		        <script>
		        $(document).ready(function() {
		            $('#edit-profile-form').formValidation({
		                framework: 'bootstrap',
		                icon: {
		                    valid: 'glyphicon glyphicon-ok',
		                    invalid: 'glyphicon glyphicon-remove',
		                    validating: 'glyphicon glyphicon-refresh'
		                },
		                fields: {
		                	edit_profile_name: {
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
		                    edit_profile_email: {
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
		                    edit_profile_pass: {
		                        validators: {		                            
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
		                    edit_profile_confirm_pass: {
		                        validators: {		                            
		                            stringLength: {
		                                min: 3,
		                                max: 30,
		                                message: 'Confirm password must be more than 3 and less than 30 characters long'
		                            },
		                            regexp: {
		                                regexp: /([A-Za-z0-9])/,
		                                message: 'Confirm password can only consist of latin symbols and numbers'
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
    </div>