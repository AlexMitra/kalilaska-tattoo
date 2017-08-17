<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<span>Hello</span>
        
        <div class="login-form-container" align="left">
	        <form action="personalArea/loadImage.html" method="POST" enctype="multipart/form-data">
				<div class="form-group">
	    			<label for="accountImage">File input</label>
	    			<input type="file" id="accountImage" name="uploadFile" size="50">
	    		</div>
	    		<button type="submit" class="btn btn-default">Submit</button>
	        </form>        

    	</div>
<!--     	<div class="main-picture"><img src="images/forSkype.jpg" alt=""></div> -->