<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en_US'}" scope="session" />

<fmt:setLocale value="${language}" scope="session" />
<fmt:setBundle basename="i18n" var="rb" />

<!DOCTYPE html>
<html  lang="${language}">

	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <meta http-equiv="X-UA-Compatible" content="ie=edge">
	    <link href="https://fonts.googleapis.com/css?family=Quicksand:500" rel="stylesheet">
	    <link href="https://fonts.googleapis.com/css?family=Roboto:300" rel="stylesheet">
	    
	    <link href="css/bootstrap.min.css" rel="stylesheet">
	    <link href="css/font-awesome.min.css" rel="stylesheet">
	    
        <link href="css/general-views-style.css" type="text/css" rel="stylesheet" >
        <link href="css/error-view-style.css" type="text/css" rel="stylesheet" >
        <link href="css/personal-area-view-style.css" type="text/css" rel="stylesheet" >
        
        <script src="js/jquery-3.2.1.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
        
	    <title>KT</title>
	</head>

	<body>
		<%@include file="../common/menu/mainMenu.jsp"%>
		
		<div class="body-container">
		    <div class="error-body-view-main-picture"><img src="images/error.jpg" alt=""></div>
		    <div class="error-content">
<%-- 		    	<p><c:out value="${errorCode}"/></p> --%>
<%-- 		        <p><c:out value="${pageNotFound}"/></p> --%>
		        <p>404</p>
		        <p>Page Not Found!</p>
		    </div>
		</div>
		
		
	</body>

</html>
