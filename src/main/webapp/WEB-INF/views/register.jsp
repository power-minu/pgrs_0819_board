<%--
  Created by IntelliJ IDEA.
  User: minu
  Date: 8/19/24
  Time: 11:54 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/user.do?action=register" method="post">
    ID : <input type="text" name="userid"/><br>
    PW : <input type="password" name="userpw"/><br>
    <input type="submit" value="회원가입">
</form>
</body>
</html>
