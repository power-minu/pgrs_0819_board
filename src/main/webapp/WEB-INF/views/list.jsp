<%@ page import="org.example.day0819_board.model.BoardDTO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: minu
  Date: 8/19/24
  Time: 10:33 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시판 목록</title>
</head>
<body>
<table border="1">
    <%
        List<BoardDTO> bList = (List<BoardDTO>) request.getAttribute("bList");
        for(BoardDTO b: bList){
    %>
    <tr>
        <td><%=b.getNo()%></td>
        <td><a href="<%=request.getContextPath()%>/board.do?action=view&no=<%=b.getNo()%>"><%=b.getTitle()%></a></td>
        <td><%=b.getWriter()%></td>
        <td><%=b.getRegDate()%></td>
        <td><%=b.getReadCount()%></td>
    </tr>
    <%
        }
    %>
</table>
<a href="<%=request.getContextPath()%>/board.do?action=writeForm">[게시글 작성하러 가기]</a>
<a href="<%=request.getContextPath()%>">[메인으로]</a>
</body>
</html>