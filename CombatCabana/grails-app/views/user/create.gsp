<%@ page import="security.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
	
		<a href="#create-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-user" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${userInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${userInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors> 
			<g:formRemote name="ajaxSignupForm" url="[action:'save',controller:'user']" update="signupModal" onSuccess="attemptToCloseModal()">
			
			<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
				<label for="username">
					<g:message code="user.username.label" default="Username" />
					<span class="required-indicator">*</span>
				</label>
				<g:textField name="username" required="" value="${userInstance?.username}"/>
			</div>
			
			<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
				<label for="password">
					<g:message code="user.password.label" default="Password" />
					<span class="required-indicator">*</span>
				</label>
				<g:passwordField name="password" required="" value="${userInstance?.password}"/>
			</div>
			
			<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'confirmPassword', 'error')} required">
				<label for="confirmPassword">
					<g:message code="user.confirmPassword.label" default="Confirm Password" />
					<span class="required-indicator">*</span>
				</label>
				<g:passwordField name="confirmPassword" required="" value="${userInstance?.confirmPassword}"/>
			</div>
			<g:submitButton name="create" class="btn btn-success" value="Create Account" />
		</g:formRemote>
		</div>
		
		<g:javascript>
			function attemptToCloseModal(){
<%--				alert('Errors exist: ${errorsExist}')--%>
				if(${!errorsExist}){
					$('#signupModal').modal('toggle')
				}
			}
		</g:javascript>
	</body>
</html>
