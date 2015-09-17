<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=Big5">
</head>
<body>
	<h1>This is annother OAuth Approval</h1>
	<p>Do you authorize '${authorizationRequest.clientId}' to access your
		protected resources?</p>
	<form id="confirmationForm" name="confirmationForm"
		action="${path}/oauth/authorize" method="post">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		 <input name="user_oauth_approval"
			value="true" type="hidden" />
		<ul>
			<c:forEach items="${scopes}" var="scope">
			<p>request:${scope.key}</p>
				<li style="display:none"><div class="form-group">
						${scope.key} <input type="radio" name="${scope.key}" value="true" checked>Approve
						<input type="radio" name="${scope.key}" value="false">Deny
					</div></li>

			</c:forEach>
			<!--  
			<li><div class="form-group">
					scope.read: <input type="radio" name="scope.read" value="true">Approve
					<input type="radio" name="scope.read" value="false" checked="">Deny
				</div></li>
			<li><div class="form-group">
					scope.write: <input type="radio" name="scope.write" value="true">Approve
					<input type="radio" name="scope.write" value="false" checked="">Deny
				</div></li>
				-->
		</ul>
		
		<label><input name="authorize" value="Authorize" type="submit"></label>
	</form>
	<p>
	<%System.out.println("System.out.println:"+request.getAttribute("scopes")); %>
		<c:out value="${scopes}"></c:out>
	</p>

</body>
</html>