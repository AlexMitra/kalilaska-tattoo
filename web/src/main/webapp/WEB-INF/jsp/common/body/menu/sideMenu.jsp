<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <div class="side-menu-container">
    	<ul>
			<li class="side-menu-item">
            	<form id="personalArea-view-link" action="personalArea.html" method="POST">
                	<input type="hidden" name="command" value="personal_area_view" />
            	</form>
            	<button class="side-menu-item-link" type="submit" form="personalArea-view-link" value="">
                	<fmt:message key="personalArea.side.menu.start" bundle="${ rb }" />
            	</button>
			</li>

			<li class="side-menu-item">
            	<form id="all-accounts-view-link" action="personalArea-allAccounts.html" method="POST">
                	<input type="hidden" name="command" value="personal_area_all_accounts_view" />
            	</form>
            	<button class="side-menu-item-link" type="submit" form="all-accounts-view-link" value="">
                	<fmt:message key="personalArea.side.menu.all.accounts" bundle="${ rb }" />
            	</button>
			</li>

			<li class="side-menu-item">
            	<form id="logout-link" action="home.html" method="POST">
                	<input type="hidden" name="command" value="logout" />
            	</form>
            	<button class="side-menu-item-link" type="submit" form="logout-link" value="">
                	<fmt:message key="personalArea.side.menu.logout" bundle="${ rb }" />
            	</button>
			</li>                    
		</ul>
    </div>