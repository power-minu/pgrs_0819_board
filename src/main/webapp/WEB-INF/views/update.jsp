<%@ page import="org.example.day0819_board.model.BoardDTO" %><%--
  Created by IntelliJ IDEA.
  User: minu
  Date: 8/19/24
  Time: 12:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>글 수정 화면</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/board.do" method="post">
    <input type="hidden" name="_method" value="put">
    <input type="hidden" name="nnn" value="<%=((BoardDTO) request.getAttribute("board")).getNo()%>">
    <input type="text" placeholder="제목을 입력하세요." value="<%=((BoardDTO) request.getAttribute("board")).getTitle()%>" name="tttt"><br>
    <textarea placeholder="내용을 입력하세요." name="cccc"><%=((BoardDTO) request.getAttribute("board")).getContent()%></textarea><br>
    <input type="submit" value="작성완료">
</form>
</body>
</html>