<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="kalilaska" uri="/WEB-INF/tld/customtag.tld" %>

<div class="all-area-white-background-container">
<div class="personal-area-stretch-content-container">
	<div class="all-accounts-title">
		<h2>
			Consultations
			<label class="switch">
				<input id="consultations-enabled-disabled-toggle" type="checkbox" checked onclick="if(this.checked){consultationAllowedForbiddenToggle.toggleEnable()} else {consultationAllowedForbiddenToggle.toggleDisable()}">
				<div class="slider round"></div>
			</label>
		</h2>
	</div>
	
	<!-- Messages -->
	<div class="form-group warning-message" align="left">
    	<span><c:out value="${approveConsultationFailure}"/></span>
    </div>
    
    <c:set var="approveConsultationFailure" value="${null}" scope="session"/>

	<!-- table -->	
		<div id="approved-consultations-table" class="table-responsive tattoo-events-table-area">
		<table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Date</th>
                                    <th>Name</th>
                                    <th>Approved</th>                                    
                                </tr>
                            </thead>
                            <tbody>
                                
			                    <c:forEach var="consultation" items="${allConsultationList}" varStatus="status">
			                    	<c:if test="${consultation.isApproved()}">
				                    	<tr>
		                                    <td><i class="fa fa-check" aria-hidden="true"></td>
		                                    <td><c:out value="${consultation.writeDate()}"/></td>
		                                    <td><c:out value="${consultation.clientName}"/></td>
		                                    <td><c:out value="${consultation.isApproved()}"/></td>
		                                </tr>
	                                </c:if>
                                </c:forEach>
                               
                            </tbody>
                        </table>
		</div>
		
		<div id="unapproved-consultations-table" class="table-responsive tattoo-events-table-area" style = "display: none;">
			<table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Date</th>
                                    <th>Name</th>
                                    <th>Approved</th>                                    
                                </tr>
                            </thead>
                            <tbody>
                                
			                    <c:forEach var="consultation" items="${allConsultationList}" varStatus="status">
			                    <c:if test="${consultation.isApproved() == false}">
			                    	<tr id="consultation-${consultation.id}" class="accounts-table-linkrow" onclick="workWithConsultationsTable.selectConsultation(this.id)">
	                                    <td><div class='checkbox'>
		                                    <label><input type='checkbox' id="checkbox-consultation-${consultation.id}" onclick = "workWithConsultationsTable.selectConsultation(this.id)" unchecked></label>
		                                    </div>	                                    
	                                    </td>
	                                    <td><c:out value="${consultation.writeDate()}"/></td>
	                                    <td><c:out value="${consultation.clientName}"/></td>
	                                    <td><c:out value="${consultation.isApproved()}"/></td>
	                                </tr>
	                            </c:if>
                                </c:forEach>
                               
                            </tbody>
                        </table>
		</div>
	
	<!-- Control buttons for unapproved -->
					<div id="unapproved-consultations-control-buttons" class="btn-group btn-group-justified" role="group" style = "display: none;">
						<form id="approve-all-consultations-link" action="personalArea-approveAllConsultations.html" method="POST" style="display:none">
                            <input type="hidden" name="command" value="personal_area_approve_all_consultations" />                            
                        </form> 
                        <div class="btn-group" role="group">                       
                        <button class="btn btn-default" type="submit" form="approve-all-consultations-link">Approve all</button>
                        </div>

                        <form id="approve-consultation-link" action="personalArea-approveConsultation.html" method="POST" style="display:none">
                            <input type="hidden" name="command" value="personal_area_approve_consultation" />
                            <input id="approve-consultation-id-input" type="hidden" name="approve_consultation_id" />
                        </form> 
                        <div class="btn-group" role="group">                          
                        <button id="approve-consultation-button" type="submit" form="approve-consultation-link" class="btn btn-default" disabled>Approve</button>
                        </div>
                    </div>

</div>
</div>