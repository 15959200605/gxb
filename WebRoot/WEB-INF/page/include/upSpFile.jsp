<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>卡宝包</title>
	<%@include file="/WEB-INF/page/include/source.jsp"%>
	<style>
		.file-box{position:relative;width:70px;height: auto;}
		.uploadBtn{background-color:#FFF; border:1px solid #CDCDCD;height:22px;line-height: 22px;width:70px;}
		.uploadFile{ position:absolute; top:0; right:0; height:22px; filter:alpha(opacity:0);opacity: 0;width:70px;}
	</style>
  </head>
  <body>
  	<div class="file-box">
	  	<form action="manager/uploadsptemp" name="uploadspfrm" id="uploadspfrm" method="post" enctype="multipart/form-data">
			<input type="button" class="uploadBtn" value="上传视频" />
			<input type="file" name="upFile" id="upFile" onchange="toupload(this);" class="uploadFile"/>
	  	</form>
  	</div>
  	
  	<script type="text/javascript">
		function toupload(obj){
			var bool = window.parent.checksp(obj);
				if(bool){
				$("#uploadspfrm").form('submit',{
					success:function(data){
						var file = $("#upFile") 
						file.after(file.clone().val("")); 
						file.remove(); 
						if(data!="-2"){
							window.parent.showSp(data);
						}else {
							window.parent.showUploadMsgs(2);
						}
					}
				});
			}
		} 
  	</script>
  </body>
</html>
