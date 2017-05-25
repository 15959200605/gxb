<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
   <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
   <style type="text/css">
		body, html {width: 100%;height: 100%;margin:0;}
		#allmap{width:100%;height:100%;font-size:13px;}
		
	</style>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=2b79c282b0f365ca9782cceb499e6022"></script>
	
  </head>
  
  <body>
	 <div id="allmap"></div>


 <script type="text/javascript">
    // 百度地图API功能
    var map = new BMap.Map("allmap");
    var cityLng = "${oldLng}";
    var cityLat = "${oldLat}";

    var city = "${city}";
    var province = "${province}";
    var address ="地址:"+"${regaddress}";

    var point="";
    var marker="";
    if(cityLng!="" && cityLat!=""){
      point = new BMap.Point(cityLng,cityLat);
	  marker = new BMap.Marker(point);  // 创建标注
	  map.addOverlay(marker);              // 将标注添加到地图中
	  
    }else{
   
       // point = new BMap.Point(118.678514, 24.880592);
	//  marker = new BMap.Marker(point);  // 创建标注
	  
	  	if(city!= ""){
		 map.centerAndZoom(city,15);    
		}
		else
		{
		 map.centerAndZoom(procince,8);    
		}
	
	
	}
	map.centerAndZoom(point, 14);
     // map.addControl(new BMap.NavigationControl());					    // 添加平移缩放控件
     //map.addControl(new BMap.ScaleControl());							// 添加比例尺控件
    //map.addControl(new BMap.OverviewMapControl());						// 添加缩略地图控件
	//map.enableScrollWheelZoom();										// 启用滚轮放大缩小
    
	map.addControl(new BMap.MapTypeControl());							// 添加地图类型控
	
	var opts = {
	  width :80,     // 信息窗口宽度
	  //height: 30,     // 信息窗口高度
	  title : "${merchantNm}", // 信息窗口标题
	 // enableMessage:true,//设置允许信息窗发送短息
	  //message:"亲"
	}
	var infoWindow = new BMap.InfoWindow(address , opts);  // 创建信息窗口对象 
	marker.addEventListener("click", function(){          
	map.openInfoWindow(infoWindow,point); //开启信息窗口
	
	});
    
    
    
</script>
	</body>
</html>
