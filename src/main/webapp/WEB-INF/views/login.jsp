<%--
  Created by IntelliJ IDEA.
  User: minu
  Date: 8/19/24
  Time: 10:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<%
  String rememberId = "";
  Cookie[] cookies = request.getCookies();
  if (cookies != null) {
    for (Cookie c : cookies) {
      if ("rememberId".equals(c.getName())) {
        rememberId = c.getValue();
        break;
      }
    }
  }
  System.out.println("rememberId = " + rememberId);
%>
<form action="<%=request.getContextPath()%>/user.do?action=login" method="post">
  ID : <input
        type="text"
        name="userid"
        value="<%= rememberId %>"
  />
  <br>
  PW : <input type="password" name="userpw"/><br>
  <input type="checkbox" name="remember" checked> ID 기억하기<br>
  <input type="submit" value="로그인">
</form>
</body>
</html>