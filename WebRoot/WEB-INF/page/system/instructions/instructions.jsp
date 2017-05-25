<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
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
		<table id="datagrid" class="easyui-datagrid" fit="true" singleSelect="true"
			url="manager/instructionsPage" border="false"
			rownumbers="true" fitColumns="true" pagination="true" title="关于我们"
			pageSize=20 pageList="[10,20,50,100,200,500,1000]" toolbar="#tb">
			<thead>
				<tr>
				    <th field="id" width="10" align="center" hidden="true">
						id
					</th>
					<th field="istNm" width="100" align="center">
					    名称
					</th>
				</tr>
			</thead>
		</table>
		<div id="tb" style="padding:5px;height:auto">
			<a class="easyui-linkbutton" iconCls="icon-edit" plain="true" href="javascript:toupdateinstructions();">修改</a>
		</div>
		<script type="text/javascript">
		    //修改
			function toupdateinstructions(){
				var row = $('#datagrid').datagrid('getSelected');
				if (row){
					var id = row.id;
					window.location.href="${base}/manager/tooperinstructions?Id="+id;
				}else{
					alert("请选择行");
				}
			}
			</script>
	</body>
</html>
