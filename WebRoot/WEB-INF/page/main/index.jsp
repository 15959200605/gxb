<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>石材宝</title>
		<%@include file="/WEB-INF/page/include/source.jsp"%>
		<link rel="stylesheet" type="text/css" href="resource/css/dtree.css">
		<script type="text/javascript" src="resource/dtree.js"></script>
		<script type="text/javascript" src="resource/md5.js"></script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false" class="head">
			<div style="padding-right: 20px;padding-top: 25px;">
			 <B><font size="+0">姓名</font></B>：${usr.usrNm}
			    <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" 
					href="javascript:modifypwdwin();" style="font-size: 16px;font-weight: bold;color: #10598A;">修改密码</a>	
				<a class="easyui-linkbutton" iconCls="icon-back" plain="true" 
					href="javascript:loginout();" style="font-size: 16px;font-weight: bold;color: #10598A;">退出</a>
			</div>
		</div>
		<div data-options="region:'west',split:true,title:'功能导航'" style="width:150px;">
			<div class="easyui-accordion" height="100%" style="width:150px;border:0;" data-options="fit:true">
				<c:if test="${!empty pmenus}">
					<c:forEach items="${pmenus}" var="menuObj">
						<div title="${menuObj.menu_nm}" data-options="href:'manager/nextmenu?id=${menuObj.id_key}'" style="overflow:auto;"></div>
					</c:forEach>
				</c:if>
			</div>
		</div>
		<div data-options="region:'center'">
			<div id="mainTab" class="easyui-tabs" data-options="fit:true,border:false">
				<div title="首页" style="padding:10px">
					<iframe name="mainiframe" id="mainiframe" src="manager/main" frameborder="0" marginheight="0" marginwidth="0"></iframe>
				</div>
			</div>
		</div>
		<div data-options="region:'south',border:false" class="bottom">
			Copyright © 2014-2015 厦门思讯网络科技有限公司
		</div>
		<div id="treeDiv" class="easyui-window" style="width:400px;height:580px;" 
			minimizable="false" modal="true" collapsible="false" closed="true">
			<form name="rolemenufrm" id="rolemenufrm" method="post">
				<input type="hidden" name="roleid" id="roleid"/>
				<input type="hidden" name="opertype" id="opertype"/>
				<div id="divHYGL" class="menuTree" data-options="region:'north'" style="width: 380px;height:510px;overflow: auto;padding-left: 5px;">
					<div id="divHYGL_tree" class="dtree"></div>
				</div>
				<div style="text-align: center;" data-options="region:'south',border:false">
					<a class="easyui-linkbutton" href="javascript:saverolepri();">保存</a>
					&nbsp;&nbsp;
					<a class="easyui-linkbutton" href="javascript:closetreewin();">关闭</a>
				</div>
			</form>
		</div>
		
		<div id="treeDivNews" class="easyui-window" style="width:400px;height:580px;" 
			minimizable="false" modal="true" collapsible="false" closed="true">
			<form name="deptmenufrm" id="deptmenufrm" method="post">
				<input type="hidden" name="tpId" id="tpId"/>
				<div id="divHYGLNews" class="menuTree" data-options="region:'north'" style="width: 380px;height:510px;overflow: auto;padding-left: 5px;">
					<div id="divHYGLNews_tree" class="dtree"></div>
				</div>
				<div style="text-align: center;" data-options="region:'south',border:false">
					<a class="easyui-linkbutton" href="javascript:saveNewsDept();">保存</a>
					&nbsp;&nbsp;
					<a class="easyui-linkbutton" href="javascript:closetreewin2();">关闭</a>
				</div>
			</form>
		</div>
		<div id="rcmenu" class="easyui-menu" style="display: none; width: 100px;background-color: #87CEFF;">
			<div id="closecurrent">
				关闭当前窗口
			</div>
			<div id="closeall">
				关闭全部
			</div>
			<div id="closeother">
				关闭其他
			</div>
		</div>
		<div id="modifypwd" class="easyui-window" style="width:340px;height:auto;padding:10px;overflow: hidden;" 
			minimizable="false" maximizable="false" collapsible="false" closed="true" resizable="false" title="修改用户密码">
			<form action="manager/modify" id="usrFrm" method="post">
				<table width="100%" border="0" cellspacing="2px">
					<tr>
						<td width="70px" align="right">旧密码：</td>
						<td>
							<input class="reg_input" type="password" name="oldpwd" id="oldpwd" style="width:132px;"/>
							<span id="oldpwdTip" class="onshow" style="font-size: 9px;color: red"></span>
						</td>
					</tr>
					<tr>
						<td width="70px" align="right">新密码：</td>
						<td>
							<input class="reg_input" type="password" name="newpwd" id="newpwd" style="width:132px;"/>
							<span id="newpwdTip" class="onshow" style="font-size: 9px;color: red"></span>
						</td>
					</tr>
					<tr>
						<td width="70px" align="right">确认密码：</td>
						<td>
							<input class="reg_input" type="password" name="confirpwd" id="confirpwd" style="width:132px;"/>
							<span id="confirpwdTip" class="onshow" style="font-size: 9px;color: red"></span>
						</td>
					</tr>
					<tr>
							<td align="center" colspan="2">
								<a class="easyui-linkbutton" href="javascript:updateUsr();" id="regbtn">修改</a>
								<a class="easyui-linkbutton" href="javascript:hideUsrWin();">关闭</a>
							</td>
					</tr>
				</table>	
			</form>
		</div>	
		<script type="text/javascript">
			$(function(){
				$(".tabs-header").bind('contextmenu',function(e){
					e.preventDefault();
					$('#rcmenu').menu('show', {
						left: e.pageX,
						top: e.pageY
					});
				});
				//关闭当前窗口
				$("#closecurrent").bind("click",function(){
					var tab = $('#mainTab').tabs('getSelected');
					var index = $('#mainTab').tabs('getTabIndex',tab);
					$('#mainTab').tabs('close',index);
				});
				//关闭所有标签页
				$("#closeall").bind("click",function(){
					var tablist = $('#mainTab').tabs('tabs');
					for(var i=tablist.length-1;i>=0;i--){
						$('#mainTab').tabs('close',i);
					}
				});
				//关闭非当前标签页（先关闭右侧，再关闭左侧）
				$("#closeother").bind("click",function(){
					var tablist = $('#mainTab').tabs('tabs');
					var tab = $('#mainTab').tabs('getSelected');
					var index = $('#mainTab').tabs('getTabIndex',tab);
					for(var i=tablist.length-1;i>index;i--){
						$('#mainTab').tabs('close',i);
					}
					var num = index-1;
					for(var i=num;i>=0;i--){
						$('#mainTab').tabs('close',0);
					}
				});
			})
			function add(title,operUrl){
				if($("#mainTab").tabs('exists',title)){
					$("#mainTab").tabs("select",title);
				}else{
					var content = '<iframe src="'+operUrl+'" frameborder="0" marginheight="0" marginwidth="0" width="100%" height="100%"></iframe>';
					$('#mainTab').tabs('add',{
						title: title,
						content:content,
						closable: true
					});
				}
			}
			function closeWin(title){
				$('#mainTab').tabs('close',title);
			}
			//退出
			function loginout(){
				if(confirm("是否退出?")){
					window.location.href="${base}/manager/loginout";
				}
			}
			//分配权限
			function torolemenu(idKey){
				$("#divHYGL_tree").empty();
				$("#roleid").val(idKey);
				$("#opertype").val("menu");
				$.ajax({
					type:"post",
					url:"manager/menutree",
					data:"id="+idKey,
					success:function(data){
						if(data){
							loadTree_menu("divHYGL","divHYGL_tree","分配权限",data);
						}
					}
				});
				$("#treeDiv").window('open');
			}
			//分配用户
			function toroleusr(idKey){
				$("#divHYGL_tree").empty();
				$("#roleid").val(idKey);
				$("#opertype").val("usr");
				$.ajax({
					type:"post",
					url:"manager/usrtree",
					data:"id="+idKey,
					success:function(data){
						if(data){
							loadTree_usr("divHYGL","divHYGL_tree","分配用户",data);
						}
					}
				});
				$("#treeDiv").window({title:"分配用户"});
				$("#treeDiv").window('open');
			}
			//订阅单位
			function toNewsDept(tpId){
				$("#divHYGLNews_tree").empty();
				$("#tpId").val(tpId);
				$.ajax({
					type:"post",
					url:"manager/deptTree",
					data:"id="+tpId,
					success:function(data){
						if(data){
							loadTree_News("divHYGLNews","divHYGLNews_tree","订阅单位",data);
						}
					}
				});
				$("#treeDivNews").window({title:"订阅单位"});
				$("#treeDivNews").window('open');
			}

			//显示订阅单位树
			function loadTree_News(treeName,objDiv,title,data){
				var treeName=treeName+"_d";
				var objTree =treeName;
				objTree = new dTree(treeName);
				objTree.add(0,-1,title);
				if(data){
					for(var i=0;i<data.length;i++){
						var nodeid  = data[i].deptId;
						var nodevl  = data[i].deptNm;
						var isUse = data[i].useCount;
						var parid   = 0;
		   				var chosevalue;
		   				if(isUse==0){
					    	chosevalue="<input type=\"checkbox\" name=\"deptId\" value=\""+nodeid+"\" />";
		   				}else{
		   					chosevalue="<input type=\"checkbox\" name=\"deptId\" checked=\"checked\" value=\""+nodeid+"\" />";
			   				}
						objTree.add(nodeid,parid,nodevl+chosevalue,"javascript:void();");	
					}
				    eval(treeName +"= objTree");
				    document.getElementById(objDiv).innerHTML=objTree;
				}
			}
			//保存订阅单位
			function saveNewsDept(){
				$("#deptmenufrm").form('submit',{
					url:"manager/saveNewsDept",
					success:function(data){
						showMsg(data);
						closetreewin2();
					}
				});
			}
			function closetreewin2(){
				$("#treeDivNews").window('close');
			}

			
			//显示用户树
			function loadTree_usr(treeName,objDiv,title,data){
				var treeName=treeName+"_d";
				var objTree =treeName;
				objTree = new dTree(treeName);
				objTree.add(0,-1,title);
				if(data){
					for(var i=0;i<data.length;i++){
						var nodeid  = data[i].usrid;
						var nodevl  = data[i].usrnm;
						var parid   = 0;
						var isuse   = data[i].isuse;
		   				var chosevalue;
					    if(isuse=="0"){
					    	chosevalue="<input type=\"checkbox\" name=\"usrid\" value=\""+nodeid+"\" />";
					    }else{
					    	chosevalue="<input type=\"checkbox\" name=\"usrid\" checked=\"checked\" value=\""+nodeid+"\" />";
					    }
						objTree.add(nodeid,parid,nodevl+chosevalue,"javascript:void();");	
					}
				    eval(treeName +"= objTree");
				    document.getElementById(objDiv).innerHTML=objTree;
				}
			}
			//保存角色分配功能
			function saverolepri(){
				var opertype = $("#opertype").val();
				var url = opertype=="menu"?"manager/saverolemenu":"manager/saveroleusr";
				$("#rolemenufrm").form('submit',{
					url:url,
					success:function(data){
						showMsg(data);
						closetreewin();
					}
				});
			}
			function closetreewin(){
				$("#treeDiv").window('close');
			}
			//显示菜单树
			function loadTree_menu(treeName,objDiv,title,data){
				var treeName=treeName+"_d";
				var objTree =treeName;
				objTree = new dTree(treeName);
				objTree.add(0,-1,title);
				if(data){
					for(var i=0;i<data.length;i++){
						var nodeid  = data[i].idKey;
						var nodevl  = data[i].menuNm;
						var parid   = data[i].PId;
						var menuTp = data[i].menuTp;
		   				var menuSeq = data[i].menuSeq;
		   				var chosevalue;
					    if(menuSeq=="0"){
					    	chosevalue="<input type=\"checkbox\" name=\"menuid\" value=\""+nodeid+"\" id=\""+nodeid+"_"+parid+"_"+menuTp+"\" onclick=\"setCheckboxSelected(this,'"+parid+"','"+menuTp+"')\"/>";
					    }else{
					    	chosevalue="<input type=\"checkbox\" name=\"menuid\" checked=\"checked\" value=\""+nodeid+"\" id=\""+nodeid+"_"+parid+"_"+menuTp+"\" onclick=\"setCheckboxSelected(this,'"+parid+"','"+menuTp+"')\"/>";
					    }
						objTree.add(nodeid,parid,nodevl+chosevalue,"javascript:void();");	
					}
				    eval(treeName +"= objTree");
				    document.getElementById(objDiv).innerHTML=objTree;
				}
			}
			//设置复选框选中状态
			function setCheckboxSelected(obj,parentId,menuTp){
				//如果是父级菜单只向上选
				if(parentId==0){
					//向下选
					setChecked(obj,1);
				}else{
					setChecked(obj,0);
					if(menuTp==0){
						//向下选
						setChecked(obj,1);
					}
				}
			}
			//向上、向下选
			function setChecked(obj,tp){
				var objId_1 = obj.id;
				var index_1 = objId_1.indexOf("_");
				//菜单id
				var menuId = objId_1.substring(0,index_1);
				var objId_2 = objId_1.substring(index_1+1);
				var index_2 = objId_2.indexOf("_");
				//父id
				var pId = objId_2.substring(0,index_2);
				//菜单类型
				var menuTp = objId_2.substring(index_2+1);
				if(tp==1){
					if(menuTp==1){return;}
				}else if(tp==0){
					var xzCount=getCheckedCount(pId);
					var dqChecked = obj.checked;
					if(dqChecked){
						if(xzCount!=1){
							return;
						}
					}else{
						if(xzCount!=0){
							return;
						}
					}
					if(pId==0){return;}
				}
				var baseTreeDiv = document.getElementById("baseTreeDiv");
				var inputObjs = baseTreeDiv.getElementsByTagName("input");
				for(var i=0;i<inputObjs.length;i++){
					var tempId_1 = inputObjs[i].id;
					var tempIndex_1 = tempId_1.indexOf("_");
					//菜单id
					var tempMenuId = tempId_1.substring(0,tempIndex_1);
					var tempId_2 = tempId_1.substring(tempIndex_1+1);
					var tempIndex_2 = tempId_2.indexOf("_");
					//父id
					var tempPId = tempId_2.substring(0,tempIndex_2);
					//菜单类型
					var tempMenuTp = tempId_2.substring(tempIndex_2+1);
					if(tp==1){
						if(tempPId==menuId){
							inputObjs[i].checked=obj.checked;
							if(tempMenuTp!=1){
								setChecked(inputObjs[i],tp)
							}
						}
					}else if(tp==0){
						if(tempMenuId==pId){
							inputObjs[i].checked=obj.checked;
							if(pId!=0){
								setChecked(inputObjs[i],tp);
							}
						}
					}
					
				}
			}
			//获取当前级别选中的个数
			function getCheckedCount(pId){
				//当前级别已选中的个数
				var xzCount=0;
				var baseTreeDiv = document.getElementById("baseTreeDiv");
				var inputObjs = baseTreeDiv.getElementsByTagName("input");
				for(var i=0;i<inputObjs.length;i++){
					var tempId_1 = inputObjs[i].id;
					var tempIndex_1 = tempId_1.indexOf("_");
					//菜单id
					var tempMenuId = tempId_1.substring(0,tempIndex_1);
					var tempId_2 = tempId_1.substring(tempIndex_1+1);
					var tempIndex_2 = tempId_2.indexOf("_");
					//父id
					var tempPId = tempId_2.substring(0,tempIndex_2);
					if(tempPId==pId){
						if(inputObjs[i].checked)
							xzCount++;
					}
				}
				return xzCount;
			}
			function modifypwdwin(){
				$("#modifypwd").window("open");
			}
			function updateUsr(){
				var oldpwd = $("#oldpwd").val();
				var newpwd = $("#newpwd").val();
				var confirpwd = $("#confirpwd").val();
				if(trim(oldpwd)==""){
					$("#oldpwdTip").text("旧密码必须填写");
					$("#oldpwdTip").show();
					return;
				}else{
					$("#oldpwdTip").hide();
				}
				
				if(trim(newpwd)==""){
					$("#newpwdTip").text("新密码必须填写");
					$("#newpwdTip").show();
					return;
				}else{
					$("#newpwdTip").hide();
				}
				
				if(trim(confirpwd)==""){
					$("#confirpwdTip").text("确认密码必须填写");
					$("#confirpwdTip").show();
					return;
				}else{
					$("#confirpwdTip").hide();
				}
				
				if(trim(newpwd)!=trim(confirpwd)){
					$("#confirpwdTip").text("密码不一致");
					$("#confirpwdTip").show();
					return;
				}else{
					$("#confirpwdTip").hide();
				}
				
				$.ajax({
					type:"post",
					url:"manager/modifypwd",
					data:{
						"oldpwd":hex_md5(oldpwd),
						"newpwd":hex_md5(newpwd)
					},
					success:function(data){
						if(data){
							showMsg(data.msg);
							if(data.status==1){
								hideUsrWin();
							}
						}
					}
				});
			}
			function hideUsrWin(){
				 $("#oldpwd").val("");
				 $("#newpwd").val("");
				 $("#confirpwd").val("");
				 $("#oldpwdTip").hide();
				 $("#newpwdTip").hide();
				 $("#confirpwdTip").hide();
				 $("#modifypwd").window("close");
			}
		</script>
	</body>
</html>
