<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="base" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
<meta name='viewport' content='initial-scale=1.0; maximum-scale=1.0; user-scalable=0;' />
<meta name="keywords" content="" />
<meta name="description" content="" />
<title>石材宝 APP下载页</title>
<meta name="keywords" content="MIUI6" />
<style>

   body { margin:0; font-family: "Microsoft YaHei"; }
  article{ margin: 0 auto; width: 100%; height:100%; background: #ffffff; overflow: hidden; }
  ul li{ list-style: none; }
  .page1 .section1{ margin-bottom: 100px;}
  .page1 .section2{ text-align: center; margin: 0 auto; }
  .page1 .section3{ height: 185px; text-align: center; }
  .page1 .section3 ul{ text-align: center; }
  .page1 .section3 ul li{ text-align: center; margin-bottom: 26px; }
  .page1 .section3 a{ height: 78px; width: 640px; border-radius: 39px; text-align: center; background: #078040; outline:none; display: inline-block;  }
  .page1 .section3 a img{  height: 37px; }
  .page2{ text-align: center; }
  .page3{ text-align: center; }
  .page3 .section1{ margin-bottom: 45px; margin-top: 0; }
  .page3 .section2{ margin-top:0; margin-bottom: 0; }
  .page3 ul{ padding: 0; margin: 0 auto; text-align: center; }
  .page3 ul li{ text-align: center; margin: 0 auto; }
  .page4{ text-align: center; }
  .page5{ text-align: center; }
  .main{ height:261px; overflow: hidden; border:none; position: relative; margin: 0 auto; }
  .page{ height:261px; *display: inline; *zoom:1; -webkit-transition: left 0.5s ease;
    transition: left 0.5s ease; display: inline-block; background: transparent; position: absolute; text-align: center; margin: 0 auto; padding: 0; }
  .l1{ width: 34px; vertical-align: middle; height: 34px; position: absolute; left: 26px; top: 50%; }
  .l2{ width: 232px; vertical-align: middle; margin-top: 44px; }
  .l2 img{ width: 171px; }
  .l3{ width: 34px; height: 34px; vertical-align: middle; left:255px; top: 50%; position: absolute; }
  .btn-group{ background: transparent; }
  .btn-group button{ background: transparent; border: none; outline: none; }
  .btn-group button img{ width: 22px; height: 22px; }

  body { background:#ffffff; margin:0; }
  article{ width: 100%; }
  .page1 .section1{ margin-bottom: 50px;}
  .page1 .section2{ text-align: center; margin-bottom: 38px; }
  .page1 .section2 img{ width:233px; height: 135px; }
  .page1 .section3{ height: 92px; }
  .page1 .section3 ul{ text-align: center; margin: 0; padding: 0; }
  .page1 .section3 ul li{ text-align: center; margin: 0; margin-bottom: 13px; }
  .page1 .section3 a{ height: 39px; width: 233px; border-radius: 20px; text-align: center; background: #078040; text-decoration: none; }
  .page1 .section3 a img{  height: 37px; }
  .page2{ text-align: center;border-bottom:1px #e2e2e2 solid }
  .page3{border-bottom:1px #e2e2e2 solid;text-align: center;}
  .page3 .section1 img{ width: 100%; }
  .page3 .section2{ margin: 0 auto; }
  .page4 img{ width:100%; border-bottom:1px #e2e2e2 solid}
  .page5 img{ width:100%; }
  
.footer {padding: 20px 0;}
.footer p {color: #bfbfbf;text-align: center;line-height: 20px;font-size: 12px;}
.footer p span {color: #8c8c8c;padding: 0 8px;}
.footer p a {color: #666666;}
.footer a, a:visited {text-decoration: none;}


/*判断微信浏览器*/
#share_img{display:none}
#share_img{width:100%; height:100%; overflow:hidden; text-align:right; position:fixed; top:0px; left:0px; display:none; background:#000; background:rgba(0,0,0,0.8); filter:alpha(opacity=80); -moz-opacity:0.8; -khtml-opacity: 0.8; opacity: 0.8; z-index:10000;}
#share_img img{max-width:600px; width:100%; position:relative;}


</style>
<script src="${base}/resource/mobilePage/images/jquery.1.7.1.min.js" type="text/javascript"></script>
<script src="${base}/resource/mobilePage/images/slide.js" type="text/javascript"></script>
<script type="text/javascript">
window.onload = function(){
    if(isWeiXin()){
      $("#share_img").show();
      $(".section3 li a").attr('href','#');   //清除超链接信息
      $(".section3 li ").live("click",function(){
		 $("#share_img").show();
		});
     }
	$("#share_img").live("click",function(){
	 $(this).hide();
	});
	
}
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}
</script>

</head>
<body >
<div id="share_img"><img src="${base}/resource/mobilePage/images/downs.png" /></div>
    <article>
    <div class="page1">
      <section class="section1"><img src="${base}/resource/mobilePage/images/logo.png" width="100%"  /></section>
      <section class="section2"><img src="${base}/resource/mobilePage/images/scb.png" width="100%"  /></section>
      <section class="section3">
        <ul>
          <li><a href="itms-services://?action=download-manifest&url=https://dn-scbstone.qbox.me/stoneApp.plist" >
	        <img src="${base}/resource/mobilePage/images/load1.png" alt="iphone" /></a></li>
          <li><a href="${android}" ><img src="${base}/resource/mobilePage/images/load2.png" alt="an" /></a></li>
        </ul>
      </section>
      <section class="section4"><img src="${base}/resource/mobilePage/images/bubble.png" width="100%" alt="bubble" /></section>
    </div>

    <div class="page2"><img src="${base}/resource/mobilePage/images/beauty.png" width="100%" alt="beauty" /></div>
    <div class="page3">
      <section class="section1"><img src="${base}/resource/mobilePage/images/strong.png" alt="strong" /></section>
      <section class="section2">
        <div class="main" id="main">
          <div class="page J-page">
            <ul>
              <li class="btn-group l1">
              </li>
              <li class="l2">
                <img src="${base}/resource/mobilePage/images/s1.png" alt="slide" />
              </li>
              <li class="btn-group l3">               
                <button type="button" class="next-step J-nextStep">
                  <img src="${base}/resource/mobilePage/images/next1.png" alt="next" class="J-imgn" />
                </button>
              </li>
            </ul>
          </div>
          <div class="page J-page">
            <ul>
              <li class="btn-group l1">
                <button type="button" class="last-step J-lastStep">
                  <img src="${base}/resource/mobilePage/images/last1.png" alt="last" class="J-imgl" />
                </button>
              </li>
              <li class="l2">
                <img src="${base}/resource/mobilePage/images/s2.png" alt="slide" />
              </li>
              <li class="btn-group l3">
                <button type="button" class="next-step J-nextStep">
                  <img src="${base}/resource/mobilePage/images/next1.png" alt="next" class="J-imgn" />
                </button>
              </li>
            </ul>
          </div>
          <div class="page J-page">
            <ul>
              <li class="btn-group l1">
                <button type="button" class="last-step J-lastStep">
                  <img src="${base}/resource/mobilePage/images/last1.png" alt="last" class="J-imgl" />
                </button>      
              </li>
              <li class="l2">
                <img src="${base}/resource/mobilePage/images/s3.png" alt="slide" />
              </li>
              <li class="btn-group l3">
              </li>
            </ul>
          </div>
        </div>
      </section>
    </div>
    <div class="page4"><img src="${base}/resource/mobilePage/images/cloud.png" alt="cloud" /></div>
    <div class="page5"><img src="${base}/resource/mobilePage/images/safe.png" alt="safe" /></div>
  </article>
<div class="footer">
        <p>
                石材宝<span>|</span>《福建石业》
        </p>
		<div style="display:none"> <a href="http://www.51.la/?17421388" target="_blank"><img alt="&#x6211;&#x8981;&#x5566;&#x514D;&#x8D39;&#x7EDF;&#x8BA1;" src="http://img.users.51.la/17421388.asp" style="border:none" /></a></div>
</div>
  <script type="text/javascript">
    
    //实例化对象
    var slide = new Slides({
      pages: $(".J-page"),
      nextSteps: $(".J-nextStep"),
      lastSteps: $(".J-lastStep"),
      main: $("#main"),
      imgns:$(".J-imgn"),
      imgls:$(".J-imgl"),
      width: 320
    });  
    //给实例对象初始化
    slide.start();   
  </script>
  </body>
</html>
