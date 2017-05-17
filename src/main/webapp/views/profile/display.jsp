<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div>
	<ul>
		<li>
			<b><spring:message code="profile.name"/>:</b>
			<jstl:out value="${profile.name}" />
		</li>
		
		<li>
			<b><spring:message code="profile.surname"/>:</b>
			<jstl:out value="${profile.surname}"/>
		</li>
		
		<li>
			<b><spring:message code="profile.email"/>:</b>
			<jstl:out value="${profile.email}"/>
		</li>
		
		<li>
			<b><spring:message code="profile.phone" />:</b>
			<jstl:out value="${profile.phone}" />
		</li>
		
		<jstl:if test="${account=='customer'}">
			
		 <li>	
			<b><spring:message code="profile.birthDate" />:</b>
			<fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${profile.birthdate}" />
		</li> 
		
		</jstl:if>
		
		<jstl:if test="${account=='developer'}">
		<li>
			<b><spring:message code="profile.company" />:</b>
			<jstl:out value="${profile.company}" />
		</li>
		
		</jstl:if>
		
		<jstl:if test="${account=='critic'}">
		<li>
			<b><spring:message code="profile.magazine" />:</b>
			<jstl:out value="${profile.magazine}" />
		</li>
		
		</jstl:if>
		
	</ul>
	
</div>

