<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>石材宝</title>
		<%@include file="/WEB-INF/page/include/source.jsp"%>
	</head>
	<body>
	
		<div class="box">
  			<form action="manager/operuserht" name="userfrm" id="userfrm" method="post">
  			    <input type="hidden" name="idKey" id="idKey" value="${user.idKey}"/>
				<dl id="dl">
	      			<dt class="f14 b">后台用户信息</dt>
	      			<dd>
	      				<span class="title">用户名称：</span>
	        			<input class="reg_input" name="userNm" id="userNm" value="${user.userNm}" style="width: 240px"/>
	        			<span id="userNmTip" class="onshow"></span>
	        		</dd>
	        		<dd>
	      				<span class="title">用户账号：</span>
	        			<input class="reg_input" name="userNo" id="userNo" value="${user.userNo}" style="width: 240px"/>
	        			<span id="userNoTip" class="onshow"></span>
	        		</dd>
	        		<dd>
	      				<span class="title">电话：</span>
	        			<input class="reg_input" name="tel" id="tel" value="${user.tel}" style="width: 240px"/>
	        			<span id="telTip" class="onshow"></span>
	        		</dd>
	        		<c:if test="${empty user.idKey}">
	 				<dd>
	      				<span class="title">初始密码：</span>
	      				<input class="reg_input" name="userPwd" id="userPwd" style="width: 150px"/>
	        		</dd>
	        		</c:if>
	        		<c:if test="${!empty user.idKey}">
	 				  <input type="hidden" name="userPwd" id="userPwd" value="${user.userPwd}"/>
	        		</c:if>
	        	</dl>
	    		<div class="f_reg_but">
	    			<input type="button" value="保存" class="l_button" onclick="toSubmit();"/>
	      			<input type="button" value="返回" class="b_button" onclick="toback();"/>
	     		</div>
	  		</form>
		</div>
		<script type="text/javascript">
	
	function toSubmit() {
	   if ($.formValidator.pageIsValid() == true) {
			$("#userfrm").form('submit', {
				success : function(data) {
					if (data == "1") {
						alert("添加成功");
						toback();
					} else if (data == "2") {
						alert("修改成功");
						toback();
					}else if (data == "-2") {
						alert("账号已存在");
						return;
					} else {
						alert("操作失败");
					}
				}
			});
		}
	}
	function toback() {
		location.href = "${base}/manager/queryuserht";
	}
	$(function() {
	    $.formValidator.initConfig();
		$("#userNm").formValidator({onShow:"请输入名称",onFocus:"请输入名称",onCorrect:"通过"}).inputValidator({min:1,max:200,onError:"请输入名称"});
		$("#userNo").formValidator({onShow:"请输入账号",onFocus:"请输入账号",onCorrect:"通过"}).inputValidator({min:1,max:200,onError:"请输入账号"});
		//$("#tel").formValidator({onShow:"请输入(15个字符以内)",onFocus:"请输入(15个字符以内)",onCorrect:"通过"}).inputValidator({min:1,max:13,onError:"请输入(15个字符以内)"});
	});
	
</script>
	</body>
</html>
