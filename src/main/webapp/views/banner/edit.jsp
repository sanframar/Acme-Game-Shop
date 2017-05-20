<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form method="post" action="banner/administrator/edit.do" modelAttribute="banner" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<acme:input code="banner.picture" path="picture" />
	
	<jstl:if test="${banner.id != 0}">
		<acme:submit name="save" code="banner.save" />
	</jstl:if>
	<jstl:if test="${banner.id == 0}">
		<acme:submit name="register" code="banner.save" />
	</jstl:if>
	<acme:cancel url="banner/administrator/list.do" code="banner.cancel" />
</form:form>
