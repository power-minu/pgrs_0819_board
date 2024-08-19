<%--
  Created by IntelliJ IDEA.
  User: minu
  Date: 8/19/24
  Time: 10:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시판 메인 화면</title>
</head>
<body>
<%
    String loginId = null;

    if (session != null) {
        loginId = (String) session.getAttribute("loginId");
    }
%>
<a href="<%=request.getContextPath()%>/board.do?action=list">[목록보기]</a>
<a href="<%=request.getContextPath()%>/board.do?action=writeForm">[작성하러가기]</a>

<br>

<% if (loginId == null) { %>
<a href="<%=request.getContextPath()%>/user.do?action=register">[회원가입]</a>
<a href="<%=request.getContextPath()%>/user.do?action=login">[로그인]</a>
<% } else { %>
<span><%=loginId%>님 환영합니다!</span>
<a href="<%=request.getContextPath()%>/user.do?action=logout">[로그아웃]</a>
<% } %>
</body>
</html>