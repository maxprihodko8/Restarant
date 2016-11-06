<%@ page import="java.util.Calendar" %>
<!-- обратите внимание на spring тэги -->
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
  <title>Index Page</title>
</head>

<body>

<spring:form method="post"  modelAttribute="userJSP" action="check-user">

  Name: <spring:input path="name"/>  <br/>
  Password: <spring:input path="password"/>   <br/>
  <spring:button>Next Page</spring:button>

</spring:form>
<h2> The time is = <%=Calendar.getInstance().getTime()%> </h2>
</body>

</html>
