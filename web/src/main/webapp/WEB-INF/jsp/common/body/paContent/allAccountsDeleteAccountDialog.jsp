<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



    <div class="modal fade" id="delete-account-dialog" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content" align="left">
                <div class="modal-header">
                    <button id="delete-account-dialog-close" type="button" class="close" data-dismiss="modal" aria-label="Close" ><span aria-hidden="true">&times;</span></button>
                    <h3 id="delete-account-dialog-title" class="modal-title"><fmt:message key="personalArea.allAccounts.dialog.deleteAccount.title" bundle="${ rb }" /> </h3> </div>
                <div class="modal-body">                	

                	<h4><fmt:message key="personalArea.allAccounts.dialog.question" bundle="${ rb }" /></h4>
                    
                </div>
                
                <div class="modal-footer">
                	<form id="delete-account-form" action="personalArea-deleteAccount.html" method="POST" enctype="utf8">
                		<input name="command" type="hidden" value="personal_area_delete_account"/>
                    	<input id="delete-account-id-input" name="delete_account_id" type="hidden" value=""/>
                    </form>
                    <button id="delete-account-dialog-close2" type="button" class="btn btn-default" data-dismiss="modal" ><fmt:message key="form.close.button" bundle="${ rb }" /></button>
                    <button class="btn btn-danger" type="submit" form="delete-account-form" onclick="accountsTableDeleteButton.deleteAccount()"><fmt:message key="form.deleteAccount.button" bundle="${ rb }" /></button>
                </div>
            </div>
        </div>
    </div>
