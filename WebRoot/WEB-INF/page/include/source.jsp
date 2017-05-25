<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.lang.String"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<base href="<%=basePath%>"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="base" value="<%=basePath%>" />
<link rel="stylesheet" type="text/css" href="resource/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="resource/themes/icon.css">
<link rel="stylesheet" type="text/css" href="resource/css/style.css">
<link rel="stylesheet" href="resource/kindeditor/themes/default/default.css" />
<link rel="icon" href="resource/ico/favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="resource/area_zh.js"></script>
<script type="text/javascript" src="resource/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="resource/jquery.easyui.min.js"></script>
<script type="text/javascript" src="resource/formValidator/formValidator-4.1.3.js"></script>
<script type="text/javascript" src="resource/formValidator/formValidatorRegex.js"></script>
<script type="text/javascript" src="resource/common.js"></script>
<script type="text/javascript" src="resource/showMsg.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>resource/kindeditor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>resource/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" >
</script>