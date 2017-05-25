<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统客户端版本管理</title>
		<%@include file="/WEB-INF/page/include/source.jsp"%>
		<style>
		.file-box{position:relative;width:70px;height: auto;}
		.uploadBtn{background-color:#FFF; border:1px solid #CDCDCD;height:22px;line-height: 22px;width:70px;}
		.uploadFile{ position:absolute; top:0; right:0; height:22px; filter:alpha(opacity:0);opacity: 0;width:70px;}
	</style>
	</head>
	<body>
		<table  id="datagrid" class="easyui-datagrid" fit="true" singleSelect="true"
			url="manager/queryVersionPage" title="系统客户端版本列表" iconCls="icon-save" border="false"
			rownumbers="true" fitColumns="true" pagination="true" pagePosition=3
			pageSize=20 pageList="[10,20,50,100,200,500,1000]" toolbar="#tb">
			<thead>
				<tr>
					<th field="id" width="50" align="center" hidden="true">
						id
					</th>
					<th field="versionName" width="150" align="center">
						版本名称
					</th>
					<th field="versionTime" width="280" align="center">
						版本更新时间
					</th>
					<th field="memberNm" width="280" align="center">
						版本更新发布人
					</th>
					<th field="versionType" width="280" align="center" formatter="formatterLX">
						版本系统类型
					</th>
				</tr>
			</thead>
		</table>
		<div id="tb" style="padding:5px;height:auto">
			版本名称:<input name="versionName" id="versionName" style="width:76px;height: 20px;" onkeydown="toQuery(event);"/>
			<a class="easyui-linkbutton" iconCls="icon-search"  href="javascript:queryVersion();">查询</a>
			<a class="easyui-linkbutton" iconCls="icon-add" plain="true" href="javascript:toaddVersion();">添加</a>
			<a class="easyui-linkbutton" iconCls="icon-edit" plain="true" href="javascript:getSelected();">修改</a>
			<a class="easyui-linkbutton" iconCls="icon-remove" plain="true" href="javascript:toDel();">删除</a>
			<a class="easyui-linkbutton" iconCls="icon-ok" plain="true" href="javascript:veInformationPushxx();">信息推送</a>
		</div>
		<!--  信息推送框-->
		<div id="xxtsdiv" class="easyui-window" style="width:340px;height:auto;padding:10px;overflow: hidden;" 
			minimizable="false" maximizable="false" collapsible="false" closed="true" title="信息推送">
			<table width="100%" border="0" cellspacing="2px">
				    <tr>
						<td>
							<textarea rows="6" cols="50" id="remo" name="remo"></textarea>
						</td>
					</tr>
					<tr>
						<td style="text-align: center;">
							<br/><a class="easyui-linkbutton" href="javascript:veInformationPush();" id="regbtn">确认推送</a>
						</td>
					</tr>
			</table>	
		</div>	
		<script type="text/javascript">
			//版本系统类型
			function formatterLX(val,row){
				if(val=="0"){
					return "Android版本";
				}else{
					return "IOS版本";
				}
			}
			//回车查询
			function toQuery(e){
				var key = window.event?e.keyCode:e.which;
				if(key==13){
					queryVersion();
				}
			}
			//查询版本
			function queryVersion(){
				$("#datagrid").datagrid('load',{
					url:"manager/queryVersionPage",
					versionName:$("#versionName").val()
				});
			}
			//添加会员
			function toaddVersion(){
				window.location.href="<%=request.getContextPath()%>/manager/toOperVersion";
			}
			//删除
			function toDel(){
				var row = $('#datagrid').datagrid('getSelected');
				if (row){
					if(confirm("是否删除该成员?")){
						$.ajax({
							url:"manager/delVersion",
							data:"id="+row.id,
							type:"post",
							success:function(json){
								if(json){
									queryVersion();
									showMsg(json);
								}
							}
						});
					}
				}else{
					alert("请选择行");
				}
			}
			//获取选中行的值(修改)
			function getSelected(){
				var row = $('#datagrid').datagrid('getSelected');
				if (row){
					window.location.href="${base}/manager/toOperVersion?id="+row.id;
				}else{
					alert("请选择行");
				}
			}
			
			//信息推送框
		    function veInformationPushxx(){
		       var rows = $("#datagrid").datagrid("getSelections");
			   if(rows.length<=1){
			   var row = $('#datagrid').datagrid('getSelected');
			   if (row){
			       $.ajax({
						url:"manager/veInformationPushxx",
						type:"post",
						data:"id="+row.id,
						success:function(data){
							if(data){
							   $("#remo").val(data.versionName); 
							   $("#xxtsdiv").window("open");
							}
						}
					});
			     }else{
					alert("请选择要推送的行！");
				}
			  }else{
			       alert("不能选择多行");
			  }	
			}
			//信息推送
			function veInformationPush(){
			  var remo=$("#remo").val();
			  $.ajax({
					url:"manager/veInformationPush",
					type:"post",
					data:"remo="+remo,
					success:function(data){
						if(data=='1'){
						    alert("推送成功");
						    $("#xxtsdiv").window("close");
							queryVersion();
						}else{
							alert("推送失败");
							$("#xxtsdiv").window("close");
							return;
						}
					}
			  });
			}
		</script>
	</body>
</html>
