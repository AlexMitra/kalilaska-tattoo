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

        <c:if test="${viewBody != null}">            
            <c:if test="${viewBody == 'homeBody'}">
                <%@include file="body/homeBody.jsp"%>
            </c:if>
            
            <c:if test="${viewBody == 'mastersBody'}">
                <%@include file="body/mastersBody.jsp"%>
            </c:if>
            
            <c:if test="${viewBody == 'loginBody'}">
                <%@include file="body/loginBody.jsp"%>
            </c:if>
            
            <c:if test="${viewBody == 'registrationBody'}">
                <%@include file="body/registrationBody.jsp"%>
            </c:if>
            
            <c:if test="${viewBody == 'personalAreaBody'}">
                <%@include file="body/personalAreaBody.jsp"%>
            </c:if>
            
            <c:if test="${viewBody == 'aboutUsBody'}">
                <%@include file="body/aboutUsBody.jsp"%>
            </c:if>
        </c:if>

        <c:if test="${viewBody == null}">
            <%@include file="body/homeBody.jsp"%>
        </c:if>
        

        <%@include file="footer/footer.jsp"%>
    </body>
</html>
