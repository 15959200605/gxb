<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
		#l-map{height:100%;width:78%;float:left;border-right:2px solid #bcbcbc;}
		#r-result{height:100%;width:20%;float:left;}
	</style>
		<script type="text/javascript"
			src="http://api.map.baidu.com/api?v=1.5&ak=A6lwDScPTc1VDRsD6GAa0N8X"></script>
		<title>获取门店坐标</title>
	</head>

	<body>
		<div id="allmap"></div>
		<script type="text/javascript">
		//
		var map = new BMap.Map("allmap");									// 创建Map实例
		var cityLng = "${cityLng}";
		var cityLat = "${cityLat}";
		var searchCondition = "${searchCondition}";
		if(cityLng!="" && cityLat!=""){
			map.centerAndZoom(new BMap.Point(cityLng, cityLat), 13);  		// 初始化地图,设置中心点坐标和地图级别
		}else{
			map.centerAndZoom(new BMap.Point(118.678514, 24.880592), 13);  	// 初始化地图,泉州坐标
		}
		map.addControl(new BMap.NavigationControl());					    // 添加平移缩放控件
		map.addControl(new BMap.ScaleControl());							// 添加比例尺控件
		map.addControl(new BMap.OverviewMapControl());						// 添加缩略地图控件
		map.enableScrollWheelZoom();										// 启用滚轮放大缩小
		map.addControl(new BMap.MapTypeControl());							// 添加地图类型控件
		var cityNm = "${cityNm}";
		if(cityNm != ""){
			map.setCurrentCity(cityNm);
		}else{
			map.setCurrentCity("泉州");
		}
		
		//右键菜单
		var contextMenu = new BMap.ContextMenu();
		var txtMenuItem = [
		  {
		   text:'放大',
		   callback:function(){map.zoomIn()}
		  },
		  {
		   text:'缩小',
		   callback:function(){map.zoomOut()}
		  },
		  {
		   text:'获取坐标',
		   callback:function(p){
		   		window.parent.setCoordinate(p.lng,p.lat,map.getZoom());
			}
		  },
		  {
		   text:'恢复初始位置',
		   callback:function(){map.reset();}
		  }
		 ];
		
		 for(var i=0; i < txtMenuItem.length; i++){
		 	contextMenu.addItem(new BMap.MenuItem(txtMenuItem[i].text,txtMenuItem[i].callback,10));
		  	if(i==1) {
		   		contextMenu.addSeparator();
		  	}
		 }
		 map.addContextMenu(contextMenu);
		 //添加标注
		 var oldLng = "${oldLng}";
		 var oldLat = "${oldLat}";
		 if(oldLng!="" && oldLat!=""){
		 	var existPoint = new BMap.Point(oldLng,oldLat);
		 	var marker = new BMap.Marker(existPoint);
		 	map.addOverlay(marker);
		 }else{
		 	if(searchCondition){
			 	var local = new BMap.LocalSearch(map, {
				    renderOptions:{map: map}
				 });
				 local.search(searchCondition);
			 }
		 }
		 map.addEventListener("click", function(e){
		 	if(e.overlay){
		 		if(confirm("是否获取坐标?")){
		 			window.parent.setCoordinate(e.overlay.getPosition().lng,e.overlay.getPosition().lat,map.getZoom());
		 		}
		 	}
		 });
	</script>
	</body>
</html>
