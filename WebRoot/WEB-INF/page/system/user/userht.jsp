<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>石材宝</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<%@include file="/WEB-INF/page/include/source.jsp"%>
	</head>

	<body>
		<table id="datagrid" class="easyui-datagrid" fit="true" singleSelect="false"
			url="manager/userPage?isSj=1" title="后台用户列表" iconCls="icon-save" border="false"
			rownumbers="true" fitColumns="true" pagination="true" 
			pageSize=20 pageList="[10,20,50,100,200,500,1000]" toolbar="#tb">
			<thead>
				<tr>
				    <th field="ck" checkbox="true"></th>
					<th field="idKey" width="50" align="center" hidden="true">
						idKey
					</th>
					<th field="userNm" width="80">
						用户名称
					</th>
					<th field="userNo" width="80">
						用户账号
					</th>
					<th field="tel" width="80" align="center">
						联系电话
					</th>
					<th field="regtimeStr" width="120" align="center">
						注册时间
					</th>
					</tr>
			</thead>
		</table>
		<div id="tb" style="padding:5px;height:auto">
		    用户名称: <input name="userNm" id="userNm" style="width:120px;height: 20px;" onkeydown="toQuery(event);"/>
		    <a class="easyui-linkbutton" iconCls="icon-search" href="javascript:queryuser();">查询</a>
			<a class="easyui-linkbutton" iconCls="icon-add" plain="true" href="javascript:tooperuser();">添加</a>
			<a class="easyui-linkbutton" iconCls="icon-edit" plain="true" href="javascript:getSelected();">修改</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" plain="true" href="javascript:deleteuser();">删除</a>
		</div>
		
		<script type="text/javascript">
			
		    //查询企业
			function queryuser(){
				$("#datagrid").datagrid('load',{
					url:"manager/userPage?isSj=1",
					userNm:$("#userNm").val()
				});
			}
			//回车查询
			function toQuery(e){
				var key = window.event?e.keyCode:e.which;
				if(key==13){
					queryuser();
				}
			}
			//添加
			function tooperuser(){
				location.href="${base}/manager/tooperuserht";
			}
			//修改
			function getSelected(){
			  var rows = $("#datagrid").datagrid("getSelections");
			  if(rows.length<=1){
				var row = $('#datagrid').datagrid('getSelected');
				if (row){
				    location.href="${base}/manager/tooperuserht?id="+row.idKey;
				}else{
					alert("请选择要修改的行！");
				}
			  }else{
			       alert("不能选择多行");
			  }	
			}
			 //删除用户
		    function deleteuser(){
		     var ids = [];
				var rows = $("#datagrid").datagrid("getSelections");
				for(var i=0;i<rows.length;i++){
					ids.push(rows[i].idKey);
				}
			  if(ids.length>0){
				var row = $('#datagrid').datagrid('getSelected');
				if (row){
				   $.ajax({
					url:"manager/deleteUser",
					type:"post",
					data:"ids="+ids,
					success:function(data){
						if(data=='1'){
						    alert("删除成功");
							queryuser();
						}else if(data=='2'){
						    alert("你的选择含有已登录账号，删除失败");
							return;
						}else{
							alert("删除失败");
							return;
						}
					}
				   });
				}else{
					alert("请选择要删除的行！");
				}
			  }
			}
		</script>
	</body>
</html>
