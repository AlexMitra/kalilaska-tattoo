<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="kalilaska" uri="/WEB-INF/tld/customtag.tld" %>

<div class="all-area-white-background-container">
<div class="personal-area-stretch-content-container">
	<div class="all-accounts-title">
		<h2>
			<fmt:message key="personalArea.allSeances.title" bundle="${ rb }"/>
			<label class="switch">
				<input id="seances-enabled-disabled-toggle" type="checkbox" checked onclick="if(this.checked){seanceAllowedForbiddenToggle.toggleEnable()} else {seanceAllowedForbiddenToggle.toggleDisable()}">
				<div class="slider round"></div>
			</label>
		</h2>
	</div>
	
	<!-- Messages -->
	<div class="form-group warning-message" align="left">
    	<span><c:out value="${approveSeanceFailure}"/></span>
    </div>
    
    <c:set var="approveSeanceFailure" value="${null}" scope="session"/>

	<!-- table -->	
		<div id="approved-seances-table" class="table-responsive tattoo-events-table-area">
			<table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Date</th>
                                    <th>Duration (hrs)</th>
                                    <th>Name</th>
                                    <th>Cost (per hour)</th>                                    
                                </tr>
                            </thead>
                            <tbody>
                                
			                    <c:forEach var="seance" items="${allSeanceList}" varStatus="status">
			                    <c:if test="${seance.costPerHour != null}">
			                    	<tr>
	                                    <td><i class="fa fa-check" aria-hidden="true"></i></td>	                                    
	                                    <td><c:out value="${seance.writeDate()}"/></td>
	                                    <td><c:out value="${seance.duration}"/></td>
	                                    <td><c:out value="${seance.clientName}"/></td>
	                                    <td><c:out value="${seance.costPerHour}"/></td>
	                                </tr>
	                            </c:if>
                                </c:forEach>
                               
                            </tbody>
                        </table>
		</div>
		
		<div id="unapproved-seances-table" class="table-responsive tattoo-events-table-area" style = "display: none;">
		<table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Date</th>
                                    <th>Duration (hrs)</th>
                                    <th>Name</th>
                                    <th>Cost (per hour)</th>                                    
                                </tr>
                            </thead>
                            <tbody>
                                
			                    <c:forEach var="seance" items="${allSeanceList}" varStatus="status">
			                    	<c:if test="${seance.costPerHour == null}">
				                    	<tr>
		                                    <td><i class="fa fa-exclamation" aria-hidden="true"></i></td>
		                                    <td><c:out value="${seance.writeDate()}"/></td>
	                                    	<td><c:out value="${seance.duration}"/></td>
	                                    	<td><c:out value="${seance.clientName}"/></td>
	                                    	<td>
		                                    	<form action="personalArea-approveSeance.html" method="POST">
		                                            <input type="hidden" name="command" value="personal_area_approve_seance"/>
		                                            <input type="hidden" name="approve_seance_id" value="${seance.id}"/>
		                                            <input type="text" class="input-with-border" name="approve_seance_cost"/>
		                                            <button type="submit" class="btn btn-default small-button">
		                                            	<fmt:message key="personalArea.controlButton.approve" bundle="${ rb }"/>
		                                            </button>
	                                        	</form>
	                                    	</td>
		                                </tr>
	                                </c:if>
                                </c:forEach>
                               
                            </tbody>
                        </table>
		</div>
</div>
</div>