<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	session.invalidate();
	out.print("<script>top.location='"+request.getContextPath()+"/login.html';</script>");
%>