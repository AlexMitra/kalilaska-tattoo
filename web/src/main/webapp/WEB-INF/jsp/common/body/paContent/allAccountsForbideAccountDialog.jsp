<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



    <div class="modal fade" id="forbide-account-dialog" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content" align="left">
                <div class="modal-header">
                    <button id="forbide-account-dialog-close" type="button" class="close" data-dismiss="modal" aria-label="Close" ><span aria-hidden="true">&times;</span></button>
                    <h3 id="forbide-account-dialog-title" class="modal-title"><fmt:message key="personalArea.allAccounts.dialog.forbideAccount.title" bundle="${ rb }" /> </h3> </div>
                <div class="modal-body">                	

                	<h4><fmt:message key="personalArea.allAccounts.dialog.question" bundle="${ rb }" /></h4>
                    
                </div>
                
                <div class="modal-footer">
                	<form id="forbide-account-form" action="personalArea-forbideAccount.html" method="POST" enctype="utf8">
                		<input name="command" type="hidden" value="personal_area_forbide_account"/>
                    	<input id="forbide-account-id-input" name="forbide_account_id" type="hidden" value=""/>
                    </form>
                    <button id="forbide-account-dialog-close2" type="button" class="btn btn-default" data-dismiss="modal" ><fmt:message key="form.close.button" bundle="${ rb }" /></button>
                    <button class="btn btn-danger" type="submit" form="forbide-account-form" onclick="accountsTableForbideButton.forbideAccount()"><fmt:message key="form.forbideAccount.button" bundle="${ rb }" /></button>
                </div>
            </div>
        </div>
    </div>
