<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="by.kalilaska.ktattoo.webname.AccountRoleNameList" session="true"%>
<c:set var="adminRole" value="${AccountRoleNameList.ADMINISTRATOR}" scope="session"/>
<c:set var="masterRole" value="${AccountRoleNameList.MASTER}" scope="session"/>
<c:set var="userRole" value="${AccountRoleNameList.USER}" scope="session"/>

	<main class="personal-area-body-container">

		<%@include file="menu/sideMenu.jsp"%>
	
	    <div class="personal-area-content">
	    
		    <c:if test="${paContent != null}">
		    	<c:if test="${paContent == 'startContent'}">
					<%@include file="paContent/startPaContent.jsp" %>
				</c:if>
				<c:if test="${paContent == 'editProfileContent'}">
					<%@include file="paContent/editProfilePaContent.jsp" %>
				</c:if>
				<c:if test="${paContent == 'allAccountsContent'}">
					<%@include file="paContent/allAccountsPaContent.jsp" %>
				</c:if>
			</c:if>
		
			<c:if test="${paContent == null}">
				<%@include file="paContent/startPaContent.jsp" %>
			</c:if>
	
	    </div>
	</main>
