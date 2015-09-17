<%@ page import="com.ggs.util.CreaXmlUtil" %>
<%--
  Created by IntelliJ IDEA.
  User: GGS
  Date: 13-7-10
  Time: 下午1:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" language="java" %>
<%
    if (request.getParameter("action")!=null){
        //输出目录
        String dir = request.getParameter("dir");
        //表
        String tableName=request.getParameter("tableName");
        //主键
        String primary=request.getParameter("primary");
        //列表类型
        String listType=request.getParameter("listType");
        if(listType.equals("1")){
            out.print(CreaXmlUtil.createEditListXml(dir,tableName,primary));
        }else if(listType.equals("2")){
            out.print(CreaXmlUtil.createEditList2Xml(dir,tableName,primary));
        }
        
    }
%>
<form name="reportForm" action="r.jsp?action=1" method="post">
    <div>输出目录：<input type="text" name="dir" value="d:/wabacus-ggs/"></div>
    <div>生成表名：<input type="text" name="tableName" value=""></div>
    <div>主键：<input type="text" name="primary" value="id"></div>
    <div>列表类型：
        <select name="listType">
            <option value="1">editablelist</option>
            <option value="2">editablelist2</option>
        </select>
    </div>
    <div><input type="submit"></div>
</form>