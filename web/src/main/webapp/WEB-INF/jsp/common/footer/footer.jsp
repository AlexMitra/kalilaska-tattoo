<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
		      <span><fmt:message key="footer.message" bundle="${ rb }" /></span>
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