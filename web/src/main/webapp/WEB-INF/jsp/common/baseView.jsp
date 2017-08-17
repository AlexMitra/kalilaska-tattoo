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
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link href="https://fonts.googleapis.com/css?family=Quicksand:500" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Roboto:300" rel="stylesheet">
        
        <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet">
        <link href="css/font-awesome.min.css" type="text/css" rel="stylesheet">
        <link href="css/formValidation.min.css" type="text/css" rel="stylesheet">
        <link href="css/general-views-style.css" type="text/css" rel="stylesheet" >
        <link href="css/error-view-style.css" type="text/css" rel="stylesheet" >
        <link href="css/personal-area-view-style.css" type="text/css" rel="stylesheet" >
        <link href="css/personal-area-dialog.css" type="text/css" rel="stylesheet" >
        <link href="css/about-us-style.css" type="text/css" rel="stylesheet" >
        
        <script src="js/jquery-3.2.1.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/formValidation.min.js"></script>
		<script src="js/framework/bootstrap.min.js"></script>
		<script src="js/allowedForbiddenToggle.js"></script>
		<script src="js/workWithAccoutTable.js"></script>
		<script src="js/accountsTableControlButton.js"></script>
		<script src="js/searchAccount.js"></script>
		
        <title>KT</title>
    </head>
    <body>
		<%@include file="menu/mainMenu.jsp"%>

        <c:if test="${viewBody != null}">            
            <c:if test="${viewBody == 'homeBody'}">
                <%@include file="body/homeBody.jsp"%>
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
        
		<footer class="footer-container footer-style">
            <div class="language-container">		    	
        			<div class="language-item">
						<form id="english-lang" action="" method="POST">
		                	<input name="command" type="hidden" value="language_en"/>
		                	<button class="language-link" type="submit" form="english-lang" value="">
		                    	<fmt:message key="footer.language.en" bundle="${ rb }" />
		                    </button>
		                </form>
		            </div>
                
        			<div class="language-item">
		                <form id="belarus-lang" action="" method="POST">
		                	<input name="command" type="hidden" value="language_be"/>
		                	<button class="language-link" type="submit" form="belarus-lang" value="">
		                    	<fmt:message key="footer.language.be" bundle="${ rb }" />
		                    </button>                	
		                </form>
		            </div>
      						
		    </div>
		    
		    <div class="footer-message-container">
		      <span>Made in Belarus</span>
		    </div>
  		</footer>
  		
		<script type="text/javascript">  			
  			function initLangForms(){
  				var fullAddress = window.location.href

  				var arr = fullAddress.split("/");  				
  				var localAddress = arr[arr.length-1];
  				if(localAddress.length == 0){
  					localAddress = "home.html";
  				}

  				document.getElementById("english-lang").setAttribute("action", localAddress);
  				document.getElementById("belarus-lang").setAttribute("action", localAddress);
  			}
  			window.onload = initLangForms;
		</script>      
        
    </body>
</html>
