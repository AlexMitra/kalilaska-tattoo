<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="kalilaska" uri="/WEB-INF/tld/customtag.tld" %>

<!-- Modals -->
<c:forEach var="photo" items="${personalAreaViewBean.getPhotos()}" varStatus="status">
	<c:if test="${not photo.isDone()}">
	  <div class="modal fade bs-example-modal-lg" id="gallery-photo-modal-${photo.id}" tabindex="-1" role="dialog">
	      <div class="modal-dialog" role="document">
	        <div class="modal-content">
	          <div class="modal-header">
	            <button class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	            <h4 class="modal-title portfolio-dialog-title">
	            	<fmt:message key="personalArea.myWorks.modal.title" bundle="${ rb }"/>
	            </h4>
	          </div>        
	          
	          <div class="modal-footer">
	           <form class="inline-form" action="personalArea-tattooPhotoDone.html" method="POST">
	                        <input type="hidden" name="command" value="personal_area_tattoo_work_done" />
	                        <input type="hidden" name="tattoo_photo_id" value="${photo.id}" />
	                        <button class="btn btn-default black-button" type="submit" >
	                        <fmt:message key="personalArea.dialog.question" bundle="${ rb }"/>
	                    </button>
	            </form>
	                    
	            <button type="button" class="btn btn-default" data-dismiss="modal">
	            	<fmt:message key="form.close.button" bundle="${ rb }"/>
	            </button>        
	          </div>
	        </div>
	      </div>
	    </div>
	</c:if>
</c:forEach>


<div class="all-area-white-background-container">
<div class="personal-area-stretch-content-container">
	<div class="all-accounts-title">
		<h2 class="inline-element"><fmt:message key="personalArea.myWorks.title" bundle="${ rb }"/></h2>
		<form id="add-tattoo-photo-link" class="inline-element" action="personalArea-addTattoPhoto.html" method="POST" enctype="multipart/form-data">                            
			<label for="add-tattoo-photo-input" class="add-tattoo-photo-button">
				<fmt:message key="personalArea.myWorks.addPhoto.button" bundle="${ rb }"/>
			</label>
			<input id="add-tattoo-photo-input" class="update-avatar-input" type="file" name="uploadFile" onchange="document.getElementById('add-tattoo-photo-submit').click();">
		</form>
		<button id="add-tattoo-photo-submit" type="submit" form="add-tattoo-photo-link" style="display: none;"></button>
	</div>
	
	<!-- Messages -->
	<div class="form-group warning-message" align="left">
    	<span><c:out value="${addTattooWorkFailure}"/></span>
    </div>
    
    <c:set var="addTattooWorkFailure" value="${null}" scope="session"/>

	<!-- gallery -->
                <div class="all-gallery-container">     
                    <div class="gallery-container">
						<c:forEach var="photo" items="${personalAreaViewBean.getPhotos()}" varStatus="status">
						    <c:if test="${photo.isDone()}">
								<img class="gallery-photo" src="${photo.url}" alt="">
				            </c:if>
		                </c:forEach>
                    </div>
                    <div class="gallery-container gallery-title">
                        <h2 class="inline-element">
                        	<fmt:message key="personalArea.myWorks.sketches.title" bundle="${ rb }"/>
                        </h2>
                        <form id="add-tattoo-sketch-link" class="inline-element" action="personalArea-addTattoSketch.html" method="POST" enctype="multipart/form-data">                            
                            <label for="add-tattoo-sketch-input" class="add-tattoo-photo-button">
                            	<fmt:message key="personalArea.myWorks.addSketch.button" bundle="${ rb }"/>
                            </label>
                            <input id="add-tattoo-sketch-input" class="update-avatar-input" type="file" name="uploadFile" onchange="document.getElementById('add-tattoo-sketch-submit').click();">
                        </form>
                        <button id="add-tattoo-sketch-submit" type="submit" form="add-tattoo-sketch-link" style="display: none;"></button>
                    </div>
                    <div class="gallery-container">
						<c:forEach var="photo" items="${personalAreaViewBean.getPhotos()}" varStatus="status">
						    <c:if test="${not photo.isDone()}">
						    	<a href="#" data-toggle="modal" data-target="#gallery-photo-modal-${photo.id}">
									<img class="gallery-photo" src="${photo.url}" alt="">
								</a>
				            </c:if>
		                </c:forEach>
                    </div>
                </div>

</div>
</div>