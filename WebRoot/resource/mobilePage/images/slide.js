//Slides组件，用于页面切换
var Slides = function(opts) {
  this.pages = opts.pages || $(".J-page");
  this.nextSteps = opts.nextSteps || $(".J-nextStep");
  this.lastSteps = opts.lastSteps || $(".J-lastStep");
  this.width = opts.width || 450;
  this.main = opts.main || $("#main");
  this.imgns = opts.imgns || $(".J-imgn");
  this.imgls = opts.imgls || $(".J-imgl");
  this.oldLeft = [];  //保存所有页面left值的数组
}

//getStyle函数用于获取样式值
Slides.prototype.getStyle = function(elem) {
  return elem.currentStyle? elem.currentStyle : window.getComputedStyle(elem, null);
}

//start页面初始化，给所有按钮添加
Slides.prototype.start = function() { 
  var _pages = this.pages;
  var _width = this.width;
  var _oldLeft = this.oldLeft;
  var _nextSteps = this.nextSteps;
  var _lastSteps = this.lastSteps;
  var _imgns = this.imgns;
  var _imgls = this.imgls;
  this.main.width(_width);

  //给每个页面的left值赋值
  _pages.each(function(index,elem){
    $(elem).css({
      width : _width + "px",
      left : _width * index + "px"
    })
    _oldLeft[index] = _width * index;
  })

  for(var i = 0,len = _nextSteps.length; i < len; i++){
    (function(){
      _nextSteps[i].onclick = function(){
        for(var j = 0; j < _pages.length; j++){
          (function(){                                     
            _pages[j].style.left = _oldLeft[j] - _width + "px";
            _oldLeft[j] =_oldLeft[j] - _width ;
          })(j);                     
        }                 
      }
    })(i);
  }
  
  for(var i = 0,len = _lastSteps.length; i < len; i++){
    (function(){
      _lastSteps[i].onclick = function(){
        for(var j = 0; j < _pages.length; j++){
          (function(){                                      
            _pages[j].style.left = _oldLeft[j] + _width + "px";
            _oldLeft[j] = _oldLeft[j] + _width ;
          })(j);         
        }
      }
    })(i);
  }

  for(var i = 0,len = _imgns.length; i < len; i++){
    (function(){
      _imgns[i].addEventListener("touchstart",  function(){
        this.src = "/about/down/next2.png";
      }, false);
       _imgns[i].addEventListener("touchend",  function(){
        this.src = "/about/down/next1.png";
      }, false);
    })(i)
  }

  for(var i = 0,len = _imgls.length; i < len; i++){
    (function(){
      _imgls[i].addEventListener("touchstart",  function(){
        this.src = "/about/down/last2.png";
      }, false);
       _imgls[i].addEventListener("touchend",  function(){
        this.src = "/about/down/last1.png";
      }, false);
    })(i)
  }
}


