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
			
			<c:choose>
				<c:when test="${paContent == 'startContent'}">					
					<%@include file="paContent/startPaContent.jsp" %>
				</c:when>
				<c:when test="${paContent == 'editProfileContent'}">					
					<%@include file="paContent/editProfilePaContent.jsp" %>
				</c:when>
				<c:when test="${paContent == 'addTattooConsultationContent'}">					
					<%@include file="paContent/addTattooConsultationPaContent.jsp" %>
				</c:when>
				<c:when test="${paContent == 'allTattooConsultationsContent' and personalAreaViewBean.getRole() eq masterRole}">					
					<%@include file="paContent/allTattooConsultationsPaContent.jsp" %>
				</c:when>
				<c:when test="${paContent == 'addTattooSeanceContent'}">					
					<%@include file="paContent/addTattooSeancePaContent.jsp" %>
				</c:when>
				<c:when test="${paContent == 'allTattooSeancesContent' and personalAreaViewBean.getRole() eq masterRole}">					
					<%@include file="paContent/allTattooSeancesPaContent.jsp" %>
				</c:when>
				<c:when test="${paContent == 'addTattooStyleContent' and personalAreaViewBean.getRole() eq masterRole}">					
					<%@include file="paContent/addTattooStylePaContent.jsp" %>
				</c:when>
				<c:when test="${paContent == 'allAccountsContent' and personalAreaViewBean.getRole() eq adminRole}">					
					<%@include file="paContent/allAccountsPaContent.jsp" %>
				</c:when>
				<c:otherwise>					
					<%@include file="paContent/startPaContent.jsp" %>
				</c:otherwise>			
			</c:choose>
	
	    </div>
	</main>
