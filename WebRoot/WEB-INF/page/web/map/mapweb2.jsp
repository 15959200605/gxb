<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
   <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
   <style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
		#l-map{height:100%;width:78%;float:left;border-right:2px solid #bcbcbc;}
		#r-result{height:100%;width:20%;float:left;}
	</style>
		<script type="text/javascript"
			src="http://api.map.baidu.com/api?v=2.0&ak=2b79c282b0f365ca9782cceb499e6022"></script>
  </head>
  
  <body>
		<div id="allmap"></div>
		<script type="text/javascript">
		//
		var map = new BMap.Map("allmap");									// 创建Map实例
		var cityLng = "${oldLng}";
		var cityLat = "${oldLat}";
		var searchCondition = "${searchCondition}";
		if(cityLng!="" && cityLat!=""){
			map.centerAndZoom(new BMap.Point(cityLng, cityLat), 13);  		// 初始化地图,设置中心点坐标和地图级别
		}else{
			map.centerAndZoom(new BMap.Point(118.103886, 24.489231), 13);  	// 初始化地图,厦门坐标
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
			map.setCurrentCity("厦门");
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
