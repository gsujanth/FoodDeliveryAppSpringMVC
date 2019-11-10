<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Index page</title>
</head>
<body>
<a href="/WEB-INF/views/login.jsp"></a>

<c:url value="/menuList" var="showMenuUrl" />
<button class="btn btn-info" onclick="location.href='${showMenuUrl}/1'">Show Menu</button>
</body>
</html>