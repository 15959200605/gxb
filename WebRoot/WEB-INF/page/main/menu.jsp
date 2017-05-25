<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>石材宝</title>
	</head>
	<body>
		<dl class="leftbar">
			<c:if test="${!empty menus}">
				<c:forEach items="${menus}" var="menuObj">
					<dd class="${menuObj.menu_cls}"><a href="javascript:add('${menuObj.menu_nm}','manager/${menuObj.menu_url}');">${menuObj.menu_nm}</a></dd>
				</c:forEach>
			</c:if>
		</dl>
	</body>
</html>
