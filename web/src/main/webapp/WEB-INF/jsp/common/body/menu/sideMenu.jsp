<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <nav class="side-menu-container">
    	<ul>
			<li class="side-menu-item">
            	<form id="personalArea-view-link" action="personalArea.html" method="POST">
                	<input type="hidden" name="command" value="personal_area_view" />
            	</form>
            	<button class="side-menu-item-link" type="submit" form="personalArea-view-link" value="">
                	<i class="fa fa-home fa-lg side-menu-icon" aria-hidden="true"></i>
                	<fmt:message key="personalArea.side.menu.start" bundle="${ rb }" />
            	</button>
			</li>
			
			<li class="side-menu-item">
	            <form id="add-consultation-view-link" action="personalArea-addConsultation.html" method="POST">
	            	<input type="hidden" name="command" value="personal_area_add_consultation_view" />
	            </form>
	            <button class="side-menu-item-link" type="submit" form="add-consultation-view-link" value="">
	            	<i class="fa fa-address-book fa-lg side-menu-icon" aria-hidden="true"></i>
	            	<fmt:message key="personalArea.side.menu.add.consultation" bundle="${ rb }" />
	            </button>
            </li>
            
            <c:if test="${personalAreaViewBean.getRole() eq masterRole}">
            <li class="side-menu-item">
                <form id="all-consultations-view-link" action="personalArea-allConsultations.html" method="POST">
                    <input type="hidden" name="command" value="personal_area_all_consultations_view" />
                </form>
                <button class="side-menu-item-link" type="submit" form="all-consultations-view-link" value="">
                    <i class="fa fa-calendar fa-lg side-menu-icon" aria-hidden="true"></i>
                    <fmt:message key="personalArea.side.menu.all.consultations" bundle="${ rb }" />
                </button>
            </li>
            </c:if>
            
            <li class="side-menu-item">
                <form id="add-seance-view-link" action="personalArea-addSeance.html" method="POST">
                    <input type="hidden" name="command" value="personal_area_add_seance_view" />
                </form>
                <button class="side-menu-item-link" type="submit" form="add-seance-view-link" value="">
                    <i class="fa fa-handshake-o fa-lg side-menu-icon" aria-hidden="true"></i>
                    <fmt:message key="personalArea.side.menu.add.seance" bundle="${ rb }" />
                </button>
            </li>
            
            <c:if test="${personalAreaViewBean.getRole() eq masterRole}">
            <li class="side-menu-item">
                <form id="all-seances-view-link" action="personalArea-allSeances.html" method="POST">
                    <input type="hidden" name="command" value="personal_area_all_seances_view" />
                </form>
                <button class="side-menu-item-link" type="submit" form="all-seances-view-link" value="">
                    <i class="fa fa-calendar fa-lg side-menu-icon" aria-hidden="true"></i>
                    <fmt:message key="personalArea.side.menu.all.seances" bundle="${ rb }" />
                </button>
            </li>
            </c:if>
            
            <c:if test="${personalAreaViewBean.getRole() eq masterRole}">
            <li class="side-menu-item">
                <form id="my-works-view-link" action="personalArea-works.html" method="POST">
                    <input type="hidden" name="command" value="personal_area_works_view" />
                </form>
                <button class="side-menu-item-link" type="submit" form="my-works-view-link" value="">
                    <i class="fa fa-picture-o fa-lg side-menu-icon" aria-hidden="true"></i>
                    <fmt:message key="personalArea.side.menu.my.works" bundle="${ rb }" />
                </button>
            </li>
            </c:if>
            
            <c:if test="${personalAreaViewBean.getRole() eq masterRole}">
            <li class="side-menu-item">
                <form id="add-style-view-link" action="personalArea-addStyle.html" method="POST">
                    <input type="hidden" name="command" value="personal_area_add_style_view" />
                </form>
                <button class="side-menu-item-link" type="submit" form="add-style-view-link" value="">
                    <i class="fa fa-pencil-square-o fa-lg side-menu-icon" aria-hidden="true"></i>
                    <fmt:message key="personalArea.addTattooStyle.button" bundle="${ rb }"/>
                </button>
            </li>
			</c:if>
			
			<c:if test="${personalAreaViewBean.getRole() eq adminRole}">
			<li class="side-menu-item">
            	<form id="all-accounts-view-link" action="personalArea-allAccounts.html" method="POST">
                	<input type="hidden" name="command" value="personal_area_all_accounts_view" />
            	</form>
            	<button class="side-menu-item-link" type="submit" form="all-accounts-view-link" value="">
                	<i class="fa fa-users fa-lg side-menu-icon" aria-hidden="true"></i>
                	<fmt:message key="personalArea.side.menu.all.accounts" bundle="${ rb }" />
            	</button>
			</li>
			</c:if>

			<li class="side-menu-item">
            	<form id="logout-link" action="home.html" method="POST">
                	<input type="hidden" name="command" value="logout" />
            	</form>
            	<button class="side-menu-item-link" type="submit" form="logout-link" value="">
                	<i class="fa fa-sign-out fa-lg side-menu-icon" aria-hidden="true"></i>
                	<fmt:message key="personalArea.side.menu.logout" bundle="${ rb }" />
            	</button>
			</li>                    
		</ul>
    </nav>