<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="kalilaska" uri="/WEB-INF/tld/customtag.tld" %>


    <div class="all-area-white-background-container">
	    <div class="personal-area-center-content-container">
	        <div class="personal-area-center-form-container">
		        <div class="edit-profile-title-container">
		            <span>
		            	<fmt:message key="personalArea.addSeance.title" bundle="${ rb }"/>
		            </span>
		        </div>
		        
		        <form id="add-tattoo-seance-form" action="personalArea-addSeance.html" method="POST" enctype="utf8">
		        	<input type="hidden" name="command" value="personal_area_add_seance" />
		           
		            <div class="form-group">
		               <div class="dropdown">
				  		    <button class="btn btn-default dropdown-toggle" type="button" id="add-seance-masterList" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
				    	        <fmt:message key="form.masters.button" bundle="${ rb }"/><span class="caret"></span>
				  		    </button>		
		                    <ul class="dropdown-menu" aria-labelledby="add-seance-masterList">
			                    <c:forEach var="master" items="${masterBeanList}" varStatus="status">
			                    	<li class="add-master-dropdown-list-item"><a href="#" onclick="addTattooEvent.addTattooSeance(${master.id}, '${master.name}')">
			                    		<c:out value="${master.name}"/>
			                    	</a></li>
			                    </c:forEach>
		                     </ul>		  		
		                </div>

		                <div class="add-tattoo-event-master-container">
		                    <input type="hidden" id="add-tattoo-seance-master-id" name="add_tattoo_seance_master_id" readonly/>
                    		<input type="text" class="form-control" id="add-tattoo-seance-master-name" name="add_tattoo_seance_master_name" readonly/>
		                </div>
		            </div>
		            
		            <div class="form-group">
		                <div class="input-group date" id="add-tattoo-seance-date-container">
		                    <input type="text" class="form-control" id="add-tattoo-seance-date" name="add_tattoo_seance_date" readonly/>
		                    <span class="input-group-addon">
		                    <span class="glyphicon glyphicon-calendar"></span>
		                    </span>
		                </div>
		            </div>
		            
		            <div class="form-group">
		                <label class="form-input-style" for="add-tattoo-seance-duration">
		                	<fmt:message key="form.duration" bundle="${ rb }"/>
		                </label>
		                <input id="add-tattoo-seance-duration" type="text" class="form-control" placeholder="1-2-3 hours" name="add_tattoo_seance_duration">
		            </div>
		            <script>
		                $('#add-tattoo-seance-date-container').datetimepicker({
		                    format: 'yyyy-mm-dd hh:ii',
		                    autoclose: true
		                }).on('changeDate', function(e) {                    
		                    $('#add-tattoo-seance-form').formValidation('revalidateField', 'add_tattoo_seance_date');
		                });
		
		            </script>
		            
		            <script>
		                $(document).ready(function() {
		                    $('#add-tattoo-seance-form').formValidation({
		                        framework: 'bootstrap',
		                        icon: {
		                            valid: 'glyphicon glyphicon-ok',
		                            invalid: 'glyphicon glyphicon-remove',
		                            validating: 'glyphicon glyphicon-refresh'
		                        },
		                        fields: {
		                            add_tattoo_seance_master_name: {
		                                validators: {
		                                    notEmpty: {
		                                        message: 'Master is required'
		                                    }
		                                }
		                            },
		                            add_tattoo_seance_date: {
		                                validators: {
		                                    notEmpty: {
		                                        message: 'Date is required'
		                                    }
		                                }
		                            },                            
		                            add_tattoo_seance_duration: {
		                                validators: {
		                                    notEmpty: {
		                                        message: 'Duration is required'
		                                    },
		                                    stringLength: {
		                                        min: 1,
		                                        max: 1,
		                                        message: 'Duration must be one number [1-3]'
		                                    },
		                                    regexp: {
		                                        regexp: /([1-3])/,
		                                        message: 'Duration must be one number [1-3]'
		                                    }
		                                }
		                            }
		                        }
		                    });
		                });
		          </script>

	            <div class="form-group warning-message">
	                <span><c:out value="${addTattooSeanceFailure}"/></span>
	            </div>
	
	            <button type="submit" class="btn btn-default form-input-style">
	            	<fmt:message key="personalArea.addSeance.title" bundle="${ rb }"/>
	            </button>
	        </form>
	        <c:set var="addTattooSeanceFailure" value="${null}" scope="session"/>
	       
		    </div>
	    </div>
    </div>