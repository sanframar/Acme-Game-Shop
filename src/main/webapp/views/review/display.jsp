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
	<fieldset>
		<legend class="dashLegend"><b><jstl:out value="${review.title}" /></b></legend>
		<br/>
		
		<jstl:out value="${review.moment}" />
		<!-- <fmt:formatDate type="both" dateStyle="short" timeStyle="short" value="${review.moment}" /> -->
		<br/><br/>
		
		<jstl:out value="${review.description}" />
	</fieldset>
	<br/>
	
	<ul>
		<li>
			<b><spring:message code="review.score" />:</b>
			<jstl:out value="${review.score}" />
		</li>
		
		<li>
			<b><spring:message code="review.draft" />:</b>
			<jstl:out value="${review.draft}" />
		</li>
		<security:authorize access="isAuthenticated()">
			<li>
				<b><spring:message code="review.game" />:</b>
				<a href="game/display.do?gameId=${review.game.id}"><jstl:out value="${review.game.title}" /></a>
			</li>
			
			<li>
				<b><spring:message code="review.critic" />:</b>
				<a href="profile/display.do?actorId=${review.critic.id}"><jstl:out value="${review.critic.name}" /></a>
			</li>
		</security:authorize>
		<security:authorize access="!isAuthenticated()">
			<li>
				<b><spring:message code="review.game" />:</b>
				<a href="game/displayNotAuth.do?gameId=${review.game.id}"><jstl:out value="${review.game.title}" /></a>
			</li>
			
			<li>
				<b><spring:message code="review.critic" />:</b>
				<jstl:out value="${review.critic.name}" />
			</li>
		</security:authorize>
		
	</ul>
	
	<jstl:if test="${isMine}">
		<acme:button code="review.edit" url="review/critic/edit.do?reviewId=${review.id}"/>
	</jstl:if>
	
</div>

