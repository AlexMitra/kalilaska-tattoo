<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="kalilaska" uri="/WEB-INF/tld/customtag.tld" %>

    <kalilaska:all-master-portfolio-dialog masterList="${requestScope.masterBeanList}"/>
    
    <main class="masters-body">
        <div class="masters-title-container">
            OUR MASTERS
        </div>
        <div class="all-masters-list-container">
			<kalilaska:all-master-work-card masterList="${requestScope.masterBeanList}"/>
        </div>

    </main>