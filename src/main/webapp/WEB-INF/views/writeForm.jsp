<%--
  Created by IntelliJ IDEA.
  User: minu
  Date: 8/19/24
  Time: 10:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>글 작성 화면</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/board.do" method="post">
    <input type="hidden" name="_method" value="post">
    <input type="text" placeholder="제목을 입력하세요." id="title" name="tttt"/><br/>
    <textarea placeholder="내용을 입력하세요." name="cccc"></textarea><br/>
    <input type="submit" value="작성완료">
</form>
</body>
</html>