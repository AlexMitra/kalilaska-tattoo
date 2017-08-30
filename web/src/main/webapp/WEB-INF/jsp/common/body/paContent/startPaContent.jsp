<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="kalilaska" uri="/WEB-INF/tld/customtag.tld" %>
<%@ page import="by.kalilaska.ktattoo.webutil.FollowDayType"  %>

		
            <div class="personal-area-content-grid-container">
                <div class="personal-area-avatar-container">
                	<div class="personal-area-avatar-image-container">
                    	<img src="${personalAreaViewBean.getPhotoURL() ne null ? personalAreaViewBean.getPhotoURL() : 'images/nullPhoto.JPG'}" class="avatar-photo"/>
                    	<div class="update-avatar-button-container">
                            <form id="avatar-edit-link" action="personalArea-updateAvatar.html" method="POST" enctype="multipart/form-data">
<!--                                 <input type="hidden" name="command" value="updateAvatar" /> -->
                                <label for="update-avatar-input" class="update-avatar-label">Update photo</label>
                                <input id="update-avatar-input" class="update-avatar-input" type="file" name="uploadFile" onchange="document.getElementById('update-avatar-submit').click();">
                            </form>
                            <button id="update-avatar-submit" class="update-avatar-button" type="submit" form="avatar-edit-link" style="display: none;">Update photo</button>
                        </div>
                        <c:if test="${personalAreaViewBean.getPhotoURL() != null}">
                        	<div class="delete-avatar-button-container">
	                            <form id="avatar-delete-link" action="personalArea-deleteAvatar.html" method="POST">
	                                <input type="hidden" name="command" value="delete_avatar" />
	                            </form>
	                            <button class="delete-avatar-button" type="submit" form="avatar-delete-link">
	                                <i class="fa fa-times" aria-hidden="true"></i>
	                            </button>
                        	</div>
                        </c:if>
                    </div>
                    <form id="profile-edit-link" action="personalArea-edit.html" method="POST">
                        <input type="hidden" name="command" value="personal_area_edit_profile_view" />
                    </form>
                    <button class="account-profile-edit-button" type="submit" form="profile-edit-link">
                    	<fmt:message key="personalArea.profile.button.edit" bundle="${ rb }" />
                    </button>
                </div>
                <div class="personal-area-title-container">
                    <span class="account-name"><c:out value="${personalAreaViewBean.getName()}"/></span>
                    
<!--                Error messages -->
                    <span class="warning-message"> <c:out value="${UpdateAvatarFailure}"/></span>
                    <span class="warning-message"> <c:out value="${DeleteAvatarFailure}"/></span>
                    <c:set var="UpdateAvatarFailure" value="${null}" scope="session"/>
                    <c:set var="DeleteAvatarFailure" value="${null}" scope="session"/>
                    
                    
                    <c:if test="${personalAreaViewBean.isAllowed() eq false}">
                    	<span class="signal-message wrong-message"><i class="fa fa-frown-o fa-lg" aria-hidden="true"></i> Forbidden!</span>
                    </c:if>

					<kalilaska:follow-user-event-notice consultationList="${personalAreaViewBean.getConsultations()}" 
					seanceList="${personalAreaViewBean.getSeances()}"/>
					
                    <div class="working-message-container">
                        <span class="signal-message working-message">
                        	<fmt:message key="personalArea.profile.marker.consultation" bundle="${ rb }" />
                        </span>
                        <span class="signal-message working-message">
                        	<fmt:message key="personalArea.profile.marker.seance" bundle="${ rb }" />
                        </span>
                    </div>                    
                    <div class="account-info">
                        <p class="account-info-item-value"><span class="account-info-item">
	                        <fmt:message key="personalArea.profile.info.email" bundle="${ rb }" />
	                        </span><c:out value="${personalAreaViewBean.getEmail()}"/>
                        </p>
                        <p class="account-info-item-value"><span class="account-info-item">
	                        <fmt:message key="personalArea.profile.info.phone" bundle="${ rb }" />
	                        </span><c:out value="${personalAreaViewBean.getPhone()}"/>
                        </p>
                        <p class="account-info-item-value"><span class="account-info-item">
	                        <fmt:message key="personalArea.profile.info.role" bundle="${ rb }" />
	                        </span><c:out value="${personalAreaViewBean.getRole()}"/>
                        </p>
                        <c:if test="${personalAreaViewBean.getRole() eq masterRole}">
                        	<c:if test="${personalAreaViewBean.getStyles() == null or personalAreaViewBean.getStyles().size() == 0}">
                        		<p class="account-info-item-value-warning"><span class="account-info-item">
		                        	<fmt:message key="personalArea.profile.info.style" bundle="${ rb }" />
			                        </span>
		                        </p>
                        	</c:if>
                        	<c:if test="${personalAreaViewBean.getStyles() != null and personalAreaViewBean.getStyles().size() > 0}">
		                        <p class="account-info-item-value"><span class="account-info-item">
		                        	<fmt:message key="personalArea.profile.info.style" bundle="${ rb }" />
			                        </span><c:out value="${personalAreaViewBean.writeStyles()}"/>
		                        </p>
	                        </c:if>
	                        <c:if test="${personalAreaViewBean.getAboutInfo() == null or personalAreaViewBean.getAboutInfo().length() == 0}">
		                        <p class="account-info-item-value-warning"><span class="account-info-item">
		                        	<fmt:message key="personalArea.profile.info.about" bundle="${ rb }" />
			                        </span>
		                        </p>
	                        </c:if>
	                        <c:if test="${personalAreaViewBean.getAboutInfo() != null and personalAreaViewBean.getAboutInfo().length() > 0}">
		                        <p class="account-info-item-value"><span class="account-info-item">
		                        	<fmt:message key="personalArea.profile.info.about" bundle="${ rb }" />
			                        </span><c:out value="${personalAreaViewBean.getAboutInfo()}"/>
		                        </p>
	                        </c:if>
                        </c:if>
                    </div>
                </div>
                <div class="own-consultations-seances-container">
                    <span class="consultations-seances-title">
                    	<fmt:message key="personalArea.profile.consultation.list.title" bundle="${ rb }" />
                    </span>
                    <span class="consultations-seances-value">
                    	<kalilaska:follow-consultation-message consultationList="${personalAreaViewBean.getConsultations()}"/>
                    </span>
                    
                    <c:if test="${personalAreaViewBean.getConsultations() ne null and personalAreaViewBean.getConsultations().size() gt 0}">                    	
                    	<ul class="consultations-seances-list">
							<kalilaska:own-consultation-list consultationList="${personalAreaViewBean.getConsultations()}"/>
                    	</ul>
                    </c:if>
                    
                    
                </div>
                <div class="own-consultations-seances-container">                    
                    <span class="consultations-seances-title">
                    	<fmt:message key="personalArea.profile.seance.list.title" bundle="${ rb }" />
                    </span>
                    <span class="consultations-seances-value">
							<kalilaska:follow-seance-message seanceList="${personalAreaViewBean.getSeances()}"/>
                    </span>

                    <c:if test="${personalAreaViewBean.getSeances() ne null and personalAreaViewBean.getSeances().size() gt 0}">	                    
	                    <ul class="consultations-seances-list">
	                    	<kalilaska:own-seance-list seanceList="${personalAreaViewBean.getSeances()}"/>
	                    </ul>
                    </c:if>
                
                </div>
            </div>