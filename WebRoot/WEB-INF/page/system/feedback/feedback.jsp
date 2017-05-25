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
			url="manager/feedbackPage" title="意见列表" iconCls="icon-save" border="false"
			rownumbers="true" fitColumns="true" pagination="true" pageSize=20 pageList="[10,20,50,100,200,500,1000]" toolbar="#tb" >
			<thead>
				<tr>
				    <th field="ck" checkbox="true"></th>
					<th field="fbId" width="50" align="center" hidden="true" rowspan="2">
						fbId
					</th>
					<th field="memberNo" width="100" align="center" rowspan="2">
						反馈用户
					</th>
					<th field="fbTime" width="150" align="center" rowspan="2">
						反馈时间
					</th>
					<th field="fbContent" width="250" align="center" rowspan="2">
						反馈内容
					</th>
				</tr>
			</thead>
		</table>
		<div id="tb" style="padding:5px;height:auto">
		    反馈用户: <input name="memberNo" id="memberNo" style="width:120px;height: 20px;" onkeydown="toQuery(event);"/>
		    <a class="easyui-linkbutton" iconCls="icon-search" href="javascript:queryproduct();">查询</a>
		    <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" href="javascript:delfeedback();">删除</a>
		</div>
		<script type="text/javascript">
		   
		    //查询产品
			function queryproduct(){
				$("#datagrid").datagrid('load',{
					url:"manager/feedbackPage",
					memberNo:$("#memberNo").val()
				});
			}
			//回车查询
			function toQuery(e){
				var key = window.event?e.keyCode:e.which;
				if(key==13){
					queryproduct();
				}
			}
			
			//删除
		function delfeedback(){
		    var ids = [];
			var rows = $("#datagrid").datagrid("getSelections");
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].fbId);
			}
			if(ids.length>0){
				if(confirm("确认要删除反馈意见吗?")){
					$.ajax({
						url:"manager/delFeedback",
						data:"id="+ids,
						type:"post",
						success:function(json){
							if(json=="1"){
							    alert("删除成功");
							    queryproduct();
							}else{
							    alert("删除失败");
							    return;
							}
						}
					});
				}
			}else{
				alert("请选择行");
			}
		}
		</script>
	</body>
</html>
