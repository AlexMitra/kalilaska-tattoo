<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="kalilaska" uri="/WEB-INF/tld/customtag.tld" %>


    <div class="all-area-white-background-container">
	    <div class="personal-area-center-content-container">
	        <div class="personal-area-center-form-container">
		        <div class="edit-profile-title-container">
		            <span>
		            	<fmt:message key="personalArea.addConsultation.title" bundle="${ rb }"/>
		            </span>
		        </div>
		        
		        <form id="add-tattoo-consultation-form" action="personalArea-addConsultation.html" method="POST" enctype="utf8">
		        	<input type="hidden" name="command" value="personal_area_add_consultation" />
		           
		            <div class="form-group">
		               <div class="dropdown">
				  		    <button class="btn btn-default dropdown-toggle" type="button" id="add-consultation-masterList" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				    	        <fmt:message key="form.masters.button" bundle="${ rb }"/><span class="caret"></span>
				  		    </button>		
		                    <ul class="dropdown-menu" aria-labelledby="add-consultation-masterList">
			                    <c:forEach var="master" items="${masterBeanList}" varStatus="status">
			                    	<li class="add-master-dropdown-list-item"><a href="#" onclick="addTattooEvent.addTattooConsultation(${master.id}, '${master.name}')">
			                    		<c:out value="${master.name}"/>
			                    	</a></li>
			                    </c:forEach>
		                     </ul>		  		
		                </div>

		                <div class="add-tattoo-event-master-container">
		                    <input type="hidden" id="add-tattoo-consultation-master-id" name="add_tattoo_consultation_master_id" readonly/>
		                    <input type="text" class="form-control" id="add-tattoo-consultation-master-name" name="add_tattoo_consultation_master_name" readonly/>
		                </div>
		            </div>
		            
		            <div class="form-group">
		                <div class="input-group date" id="add-tattoo-consultation-date-container">
		                    <input type="text" class="form-control" id="add-tattoo-consultation-date" name="add_tattoo_consultation_date" readonly/>
		                    <span class="input-group-addon">
		                    <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>
		            </div>
		            <script>
		                $('#add-tattoo-consultation-date-container').datetimepicker({
		                    format: 'yyyy-mm-dd hh:ii',
		                    autoclose: true
		                }).on('changeDate', function(e) {                    
		                    $('#add-tattoo-consultation-form').formValidation('revalidateField', 'add_tattoo_consultation_date');
		                });
		
		            </script>
		            
		            <script>
		                $(document).ready(function() {
		                    $('#add-tattoo-consultation-form').formValidation({
		                        framework: 'bootstrap',
		                        icon: {
		                            valid: 'glyphicon glyphicon-ok',
		                            invalid: 'glyphicon glyphicon-remove',
		                            validating: 'glyphicon glyphicon-refresh'
		                        },
		                        fields: {
		                            add_tattoo_consultation_master_name: {
		                                validators: {
		                                    notEmpty: {
		                                        message: 'Master is required'
		                                    }
		                                }
		                            },
		                            add_tattoo_consultation_date: {
		                                validators: {
		                                    notEmpty: {
		                                        message: 'Date is required'
		                                    }
		                                }
		                            }                    
		                        }
		                    });
		                });
		          </script>

	            <div class="form-group warning-message">
	                <span><c:out value="${addTattooConsultationFailure}"/></span>
	            </div>
	
	            <button type="submit" class="btn btn-default form-input-style">
	            	<fmt:message key="personalArea.addConsultation.title" bundle="${ rb }"/>
	            </button>
	        </form>
	        <c:set var="addTattooConsultationFailure" value="${null}" scope="session"/>
	       
		    </div>
	    </div>
    </div>