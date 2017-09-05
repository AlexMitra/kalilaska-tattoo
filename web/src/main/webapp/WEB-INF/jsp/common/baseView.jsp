<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <c:set var="language" value="${not empty sessionScope.language ? sessionScope.language : not empty language ? language : 'en_US'}" scope="session" /> --%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en_US'}" scope="session" />

<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="i18n" var="rb" />

<!DOCTYPE html>
<html lang="${language}">
    <head>
		<%@include file="header/header.jsp"%>
    </head>
    <body>
		<%@include file="menu/mainMenu.jsp"%>

		<c:choose>
			<c:when test="${viewBody == 'homeBody'}">
				<%@include file="body/homeBody.jsp"%>
			</c:when>
			<c:when test="${viewBody == 'mastersBody'}">
				<%@include file="body/mastersBody.jsp"%>
			</c:when>
			<c:when test="${viewBody == 'loginBody'}">
				<%@include file="body/loginBody.jsp"%>
			</c:when>
			<c:when test="${viewBody == 'registrationBody'}">
				<%@include file="body/registrationBody.jsp"%>
			</c:when>
			<c:when test="${viewBody == 'personalAreaBody'}">
				<%@include file="body/personalAreaBody.jsp"%>
			</c:when>
			<c:when test="${viewBody == 'aboutUsBody'}">
				<%@include file="body/aboutUsBody.jsp"%>
			</c:when>
			<c:otherwise>
				<%@include file="body/homeBody.jsp"%>
			</c:otherwise>
		</c:choose>

        <%@include file="footer/footer.jsp"%>
    </body>
</html>
