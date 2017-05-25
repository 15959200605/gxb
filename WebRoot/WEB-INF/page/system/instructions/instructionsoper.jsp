<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>笨汉</title>
		<%@include file="/WEB-INF/page/include/source.jsp"%>
	</head>
	<body>
	
		<div class="box">
  			<form action="manager/operinstructions" name="venturefrm" id="venturefrm" method="post">
  				<input type="hidden" name="id" id="id" value="${instructions.id}"/>
  			    <input type="hidden" name="istNm" id="istNm" value="${instructions.istNm}"/>
  			    <dl id="dl">
	      			<dt class="f14 b">关于我们信息</dt>
	      			<dd>
	      				<span class="title">内容：</span>
	      			</dd>
	      			<dd style="margin-left:60px;margin-top:-100px;height:500px;">
	        			<textarea name="remo" id="remo" style="width: 900px;height: 400px;">${instructions.remo}</textarea>
	           			<!-- <span id="remoTip" class="onshow"></span>  -->
	        		</dd>
	        	</dl>
	    		<div class="f_reg_but">
	    			<input type="button" value="保存" class="l_button" onclick="toSubmit();"/>
	      			<input type="button" value="返回" class="b_button" onclick="toback();"/>
	     		</div>
	  		</form>
		</div>
		<script type="text/javascript">
		   function toSubmit(){
		     	kindEditor.sync();
				$("#venturefrm").form('submit',{
						success:function(data){
							if(data=="1"){
								alert("添加成功");
								toback();
							}else if(data=="2"){
								alert("修改成功");
								toback();
							}else{
								alert("操作失败");
							}
						}
			     });
			}
			function toback(){
				location.href="${base}/manager/queryinstructions";
			}
			$(function(){
			    addEditor("remo","venturefrm");//文本编辑框
			});
		
		</script>
	</body>
</html>
