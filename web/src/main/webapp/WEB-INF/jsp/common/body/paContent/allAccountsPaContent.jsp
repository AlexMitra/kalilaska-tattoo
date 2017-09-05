<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="kalilaska" uri="/WEB-INF/tld/customtag.tld" %>

	<%@include file="allAccountsAddAccountDialog.jsp"%>
	<%@include file="allAccountsEditAccountDialog.jsp"%>
	<%@include file="allAccountsForbideAccountDialog.jsp"%>
	<%@include file="allAccountsAllowAccountDialog.jsp"%>
	<%@include file="allAccountsDeleteAccountDialog.jsp"%>

<div class="all-area-white-background-container">
<div class="personal-area-stretch-content-container">
	<div class="all-accounts-title">
		<h2><fmt:message key="personalArea.allAccounts.title" bundle="${ rb }" />
			<label class="switch">
				<input id="accounts-enabled-disabled-toggle" type="checkbox" checked onclick="if(this.checked){accountAllowedForbiddenToggle.toggleEnable()} else {accountAllowedForbiddenToggle.toggleDisable()}">
				<div class="slider round"></div>
			</label>
		</h2>
	</div>
	
	<!-- Messages -->
	<div class="form-group warning-message" align="left">
    	<span><c:out value="${createAccountFailure}"/></span>
    </div>
    <div class="form-group warning-message" align="left">
    	<span><c:out value="${editAccountFailure}"/></span>
    </div>
    <div class="form-group warning-message" align="left">
    	<span><c:out value="${forbideAccountFailure}"/></span>
    </div>
    <div class="form-group warning-message" align="left">
    	<span><c:out value="${allowAccountFailure}"/></span>
    </div>
    <div class="form-group warning-message" align="left">
    	<span><c:out value="${deleteAccountFailure}"/></span>
    </div>
    <c:set var="createAccountFailure" value="${null}" scope="session"/>
    <c:set var="editAccountFailure" value="${null}" scope="session"/>
    <c:set var="forbideAccountFailure" value="${null}" scope="session"/>
    <c:set var="allowAccountFailure" value="${null}" scope="session"/>
    <c:set var="deleteAccountFailure" value="${null}" scope="session"/>


	<!-- table -->
	<div id="allowed-accounts-table" class="table-responsive accounts-table-area" style="display: block;">
		<kalilaska:all-accounts accountList="${personalAreaViewBean.getAccounts()}" allowed="${true}">			
		</kalilaska:all-accounts>
	</div>
	
	<div id="forbidden-accounts-table" class="table-responsive accounts-table-area" style="display: none;">
		<kalilaska:all-accounts accountList="${personalAreaViewBean.getAccounts()}" allowed="${false}">			
		</kalilaska:all-accounts>
	</div>
	
	<!-- Control buttons for Allowed -->
	<div  id="allowed-accounts-control-buttons" class="btn-group btn-group-justified" role="group">
	                                    	
		<div class="btn-group" role="group">
			<button id="unselect-all-account-button" type="button" class="btn btn-default" aria-label="Unselect All" disabled onclick="workWithAccountsTable.unselectAllCheckboxes()"><fmt:message key="personalArea.allAccounts.unselectAll.button" bundle="${ rb }" /></button>
		</div>
		<div class="btn-group" role="group">
			<button id="add-account-button" type="button" class="btn btn-default" data-toggle="modal" data-target="#add-account-dialog" onclick="accountsTableAddButton.showDialog()"><fmt:message key="personalArea.allAccounts.add.button" bundle="${ rb }" /></button>
		</div>
		<div class="btn-group" role="group">
			<button id="update-account-button" type="button" class="btn btn-default" data-toggle="modal" data-target="#edit-account-dialog" onclick="accountsTableEditButton.showDialog()" disabled><fmt:message key="personalArea.allAccounts.edit.button" bundle="${ rb }" /></button>
		</div>
		<div class="btn-group" role="group">
			<button id="forbide-account-button" type="button" class="btn btn-danger" data-toggle="modal" data-target="#forbide-account-dialog" onclick="accountsTableForbideButton.showDialog()" disabled ><fmt:message key="personalArea.allAccounts.forbide.button" bundle="${ rb }" /></button>
		</div>  										
	  										
	</div>
										
	<!-- Control buttons for Forbidden -->
	<div id="forbidden-accounts-control-buttons" class="btn-group btn-group-justified" role="group" style = "display: none;">
											
		<div class="btn-group" role="group">
			<button id="allow-account-button" type="button" class="btn btn-primary" data-toggle="modal" data-target="#allow-account-dialog" onclick="accountsTableAllowButton.showDialog()" disabled ><fmt:message key="personalArea.allAccounts.allow.button" bundle="${ rb }" /></button>
		</div>
		<div class="btn-group" role="group">
			<button id="delete-account-button" type="button" class="btn btn-danger" data-toggle="modal" data-target="#delete-account-dialog" onclick="accountsTableDeleteButton.showDialog()" disabled ><fmt:message key="personalArea.allAccounts.delete.button" bundle="${ rb }" /></button>
		</div>
	</div>
</div>
</div>