<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="kalilaska" uri="/WEB-INF/tld/customtag.tld" %>


    <div class="all-area-white-background-container">
	    <div class="personal-area-center-content-container">
	        <div class="personal-area-center-form-container">
		        <div class="edit-profile-title-container">
		            <span>Add tattoo style</span>
		        </div>
		        
		        <form id="add-tattoo-style-form" action="personalArea-addStyle.html" method="POST" enctype="utf8">
		        	<input type="hidden" name="command" value="personal_area_add_style" />
		           
		            <div class="form-group">
		                <label class="form-input-style" for="add-tattoo-style-name">Name*</label>
		                <input id="add-tattoo-style-name" type="text" class="form-control" placeholder="Name" name="add_tattoo_style_name">
		            </div>
		            
		            <div class="form-group">
		                <label class="form-input-style" for="add-tattoo-style-description">Description</label>
		                <textarea id="add-tattoo-style-description" form="add-tattoo-style-form" class="form-control" rows="4" name="add_tattoo_style_description"></textarea>                
		            </div>		            

		            <div class="form-group warning-message">
		                <span><c:out value="${addTattooStyleFailure}"/></span>
		            </div>
		
		            <button type="submit" class="btn btn-default form-input-style">Add style</button>
		        </form>

		        <c:set var="addTattooStyleFailure" value="${null}" scope="session"/>		        
		
		        <script>
			        $(document).ready(function() {
			            $('#add-tattoo-style-form').formValidation({
			                framework: 'bootstrap',
			                icon: {
			                    valid: 'glyphicon glyphicon-ok',
			                    invalid: 'glyphicon glyphicon-remove',
			                    validating: 'glyphicon glyphicon-refresh'
			                },
			                fields: {
			                	add_tattoo_style_name: {
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
			                    }                    
			                }
			            });
			        });
		      </script>

		    </div>
	    </div>
    </div>