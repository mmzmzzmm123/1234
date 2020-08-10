<template >
  <div class="main">

    <div class="index-top"></div>

    <!--github-->
    <a @click="zreaZtree" class="github-corner" target="_blank" title="Follow me on GitHub" aria-label="Follow me on GitHub">
      <svg width="80" height="80" viewBox="0 0 250 250" style="fill:#008000; color:#fff; position: absolute; top: 0; border: 0; right: 0;" aria-hidden="true">
        <path d="M0,0 L115,115 L130,115 L142,142 L250,250 L250,0 Z"></path><path d="M128.3,109.0 C113.8,99.7 119.0,89.6 119.0,89.6 C122.0,82.7 120.5,78.6 120.5,78.6 C119.2,72.0 123.4,76.3 123.4,76.3 C127.3,80.9 125.5,87.3 125.5,87.3 C122.9,97.6 130.6,101.9 134.4,103.2" fill="currentColor" style="transform-origin: 130px 106px;" class="octo-arm"></path>
        <path d="M115.0,115.0 C114.9,115.1 118.7,116.5 119.8,115.4 L133.7,101.6 C136.9,99.2 139.9,98.4 142.2,98.6 C133.8,88.0 127.5,74.4 143.8,58.0 C148.5,53.4 154.0,51.2 159.7,51.0 C160.3,49.4 163.2,43.6 171.4,40.1 C171.4,40.1 176.1,42.5 178.8,56.2 C183.1,58.6 187.2,61.8 190.9,65.4 C194.5,69.0 197.7,73.2 200.1,77.6 C213.8,80.2 216.3,84.9 216.3,84.9 C212.7,93.1 206.9,96.0 205.4,96.6 C205.1,102.4 203.0,107.8 198.3,112.5 C181.9,128.9 168.3,122.5 157.7,114.1 C157.9,116.9 156.7,120.9 152.7,124.9 L141.0,136.5 C139.8,137.7 141.6,141.9 141.8,141.8 Z" fill="currentColor" class="octo-body"></path>
      </svg>
    </a>











    <div style="height: 700px;width: 270px;margin-top: 50px"></div>

    <div style="height: 700px;width: 500px;margin-top: 50px">
    </div>
    <div  class="areaTree" >
        <ul id="treeDemo" class="ztree" ></ul>
    </div>















    <div class="sousou">
<!--      //搜索-->


      <div style="width: 50%;height: 200px;margin: 0 auto;margin-top: 19%">

        <div >


          <div class="choice-top" v-for="iconlist in iconlist">
            <el-tooltip  :visible-arrow="visiblearro" :enterable="enterable" class="item"  effect="dark" :content="iconlist.title" placement="top" >
            <div @click="sendEventDataTop($event)" :data-icon="iconlist.icon"><img :ng-src="'https://favicon.lucq.fun/?url='+iconlist.icon" err-src="./images/default.ico"  ng-click="detailBookmark(bookmark);$event.stopPropagation()" :src="'https://favicon.lucq.fun/?url='+iconlist.icon"  ></div>
            </el-tooltip>
          </div>

        </div>

        <div style="margin-top: 15px;">
          <el-input placeholder="百度一下" v-model="sousou" class="sousoucss" ref="sousouref">
            <img @click="IsCord()"  slot="prefix"  :src="sousouicon" style="width: 20px;height: 20px;position: absolute;top: 50%;margin-top: -10px;">
              <el-button   slot="append" icon="el-icon-search"  circle @click="getUrl"></el-button>
          </el-input>
        </div>

        <transition name="el-zoom-in-top">
            <el-card class="box-card" v-show="isSousouCard" style="width: 100%;opacity: 0.7;margin-top: 10px;height:200px;overflow:auto">

              <div class="choice" v-for="iconlist in iconlist" >
            <div  @click="sendEventData($event)" :data-icon="iconlist.icon"><img err-src="https://favicon.lucq.fun/?url=https://www.5118.com/"  class="sousouicon"  :ng-src="'https://favicon.lucq.fun/?url='+iconlist.icon"  :src="'https://favicon.lucq.fun/?url='+iconlist.icon"  ><span>{{iconlist.title}}</span></div>
              </div>
            </el-card>

        </transition>




<!--        <el-card class="box-card" v-show="isSousouCard" style="width: 500px;opacity: 0.7">-->
<!--          <div class="choice" v-for="iconlist in iconlist">-->
<!--            <div  @click="sendEventData($event)" :data-icon="iconlist.icon"><img err-src="https://favicon.lucq.fun/?url=https://www.5118.com/"  class="sousouicon"  :ng-src="'https://favicon.lucq.fun/?url='+iconlist.icon"  :src="'https://favicon.lucq.fun/?url='+iconlist.icon"  ><span>{{iconlist.title}}</span></div>-->
<!--&lt;!&ndash;            ng-click="detailBookmark(bookmark);$event.stopPropagation()"     &ndash;&gt;-->
<!--          </div>-->
<!--        </el-card>-->

      </div>







    </div>




  </div>
</template>


<script>
  // 下面的是单个Vue组件引用的外部静态文件，也可以在main.js文件中引用
  import "../ztree/jquery-1.4.4.min.js"
 import "../ztree/jquery.ztree.core.js"
  import "../ztree/demo.css"
  import "../ztree/zTreeStyle.css"
  import "../ztree/jquery.ztree.exedit.js"

  export default {
    name: 'areaTree',
    components:{

    },

    data:function(){
      return{
        enterable:false,
        visiblearro:true,//提示箭头
        isShowZtree:false,//ztree树显示
        expandAll:false,//是否展开ztree树
        curMenu:null,
        sousou:'百度一下',//搜索文字
        zTree_Menu:null,
        isSousouCard:false,//切换搜索源
        iconurl:'https://favicon.lucq.fun/?url=',
        sousouicon:'https://favicon.lucq.fun/?url=https://www.baidu.com/',//默认搜索图片
        sousou:'',
        sousouVlaue:'百度一下',
        setting:{
          view: {
            showLine: false,
            showIcon: true,
            selectedMulti: false,
            dblClickExpand: false,
            addHoverDom: this.addHoverDom,
            removeHoverDom: this.removeHoverDom,
            addDiyDom: this.addDiyDom
          },
          check: {
            enable: true,
            nocheckInherit: false ,
            chkboxType: { "Y": "p", "N": "s" }
          },
          data: {
            simpleData: {
              enable: true
            }
          },
          callback: {
            beforeClick: this.beforeClick,
            onClick: this.zTreeOnClick,
            onCheck: this.zTreeOnCheck,
          }
        },
        zNodes:[
          { id:1, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.baidu.com"},
          { id:2, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://movie.douban.com/"},
          { id:3, pId:2, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:4, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.w3school.com.cn/"},
          { id:5, pId:4, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:6, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:7, pId:6, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:8, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://open.163.com/"},
          { id:9, pId:8, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:1123, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://y.qq.com"},
          { id:1142, pId:1, name:"收件箱",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:111, pId:19, name:"收件箱1",icon:"https://favicon.lucq.fun/?url=https://mail.sina.com.cn/"},
          { id:112, pId:111, name:"收件箱2",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:113, pId:112, name:"收件箱3",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:114, pId:113, name:"收件箱4",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:1321, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:2322, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:3323, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:4324, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:5325, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:6399, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:32, pId:399, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:12, pId:1, name:"垃圾邮件",icon:"https://favicon.lucq.fun/?url=https://www.google.com/"},
          { id:13, pId:1, name:"草稿",icon:"https://favicon.lucq.fun/?url=https://yz.m.sm.cn/"},
          { id:14, pId:1, name:"已发送邮件",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:15, pId:1, name:"已删除邮件",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:322, pId:11, name:"快速视图",icon:"https://favicon.lucq.fun/?url=https://taobao.com/"},
          { id:31, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:131, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:231, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:331, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:431, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:531, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:631, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:731, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          { id:831, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          ]
        ,
        iconlist:[
         {iconID:"1",icon:"https://www.baidu.com",title:"百度"},
         {iconID:"2",icon:"https://www.google.com/",title:"谷歌搜索引擎"},
         {iconID:"3",icon:"https://www.so.com/",title:"搜索"},
         {iconID:"4",icon:"https://www.sogou.com/",title:"搜狗"},
         {iconID:"5",icon:"https://cn.bing.com/",title:"必应"},
         {iconID:"6",icon:"https://yz.m.sm.cn/",title:"神马搜索"},
          {iconID:"7",icon:"https://www.jd.com/",title:"京东"},
          {iconID:"8",icon:"https://taobao.com/",title:"淘宝"},
          {iconID:"7",icon:"https://y.qq.com",title:"QQ音乐"},
          {iconID:"7",icon:"https://weibo.com/",title:"微博"},
          {iconID:"7",icon:"http://www.dianping.com/",title:"大众点评"},
          {iconID:"7",icon:"https://mail.sina.com.cn/",title:"新浪邮箱"},
          {iconID:"7",icon:"http://caiyunapp.com",title:"彩云天气"},
          {iconID:"7",icon:"https://www.yhd.com/",title:"一号店"},
          {iconID:"7",icon:"https://www.mi.com/",title:"小米"},
          {iconID:"7",icon:"https://www.tianyancha.com/",title:"天眼查"},
          {iconID:"7",icon:"https://translate.google.com/",title:"谷歌翻译"},
          {iconID:"7",icon:"https://open.163.com/",title:"网易公开课"},
          {iconID:"7",icon:"http://mooc.org/",title:"MOCC"},
          {iconID:"7",icon:"https://www.w3school.com.cn/",title:"w3school"},
          {iconID:"7",icon:"https://segmentfault.com/",title:"segmentfault"},
          {iconID:"7",icon:"https://www.y-english.org/",title:"云英语"},
          {iconID:"7",icon:"https://www.tutorialspoint.com/",title:"Tutorials Point"},
          {iconID:"7",icon:"https://github.com/",title:"GitHub"},
          {iconID:"7",icon:"https://mail.sina.com.cn/",title:"新浪邮箱"},
          {iconID:"7",icon:"https://movie.douban.com/",title:"豆瓣电影"},
          {iconID:"7",icon:"http://www.zzrbl.com/",title:"猪猪电影网"},

        ]
      }
    },
    methods:{

    addDiyDom:function(treeId, treeNode) {
      console.log("自定义ztree:"+treeId.tId+"___treeNode："+treeNode.tId)
    var spaceWidth = 20;
    var switchObj = $("#" + treeNode.tId + "_switch"),
      icoObj = $("#" + treeNode.tId + "_ico");
    switchObj.remove();
    icoObj.before(switchObj);

    if (treeNode.level > -1) {
      var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
      switchObj.before(spaceStr);
      var switchObjspan = $("#" + treeNode.tId + "_span");




      var editStr = "<span class="+treeNode.tId+"_count style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:8px' onfocus='this.blur();'>99</span>";
      switchObjspan.after(editStr);
    }
  },

  addHoverDom:function(treeId, treeNode) {
  	var confCount = $("."+treeNode.tId+"_sz").length;
  	//console.log("进入addHoverDom:统计"+treeNode.tId+"_sz 的数量:"+confCount);
  	if (confCount>0) return;
  	//if (treeNode.parentNode && treeNode.parentNode.id!=1) return;
  	var switchObjspan = $("#" + treeNode.tId + "_span");
  	var editStr = "<span class="+treeNode.tId+"_sz onclick='alert(2222);return false;' style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:8px' onfocus='this.blur();'>删</span>";
  	switchObjspan.after(editStr);

    $("." + treeNode.tId + "_count").unbind().remove();


    //绑定编辑
   // document.getElementsByClassName(treeNode.tId + "_sz").onclick=function(){alert(this.value)};
    //document.getElementsByClassName(treeNode.tId + "_sz").addEventListener('click', editBookmark);
   // $("." + treeNode.tId + "_sz").addEventListener('click', editBookmark);
  },

  removeHoverDom:function(treeId, treeNode) {
  	//console.log("进入removeHoverDom:"+"." + treeNode.tId + "_sz")
  	//if (treeNode.parentTId && treeNode.getParentNode().id!=1) return;

  	$("." + treeNode.tId + "_sz").unbind().remove();
    var switchObjspan = $("#" + treeNode.tId + "_span");
    var editStr = "<span class="+treeNode.tId+"_count onclick='alert(1111111);return false;' style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:8px' onfocus='this.blur();'>12</span>";
    switchObjspan.after(editStr);

  },
  //点击展开
   beforeClick:function(treeId, treeNode) {
    //if (treeNode.level != 19990 ) {
       var zTree = $.fn.zTree.getZTreeObj("treeDemo");
       zTree.expandNode(treeNode);
    //  return false;
    // }
    //return true;
  },
      //显示隐藏 ztree菜单
      zreaZtree:function () {
      var that=this;
        that.isShowZtree = !that.isShowZtree;

      },
      //显示隐藏 数据源
      IsCord:function(){
        var that=this;
        that.isSousouCard=!that.isSousouCard;
      },
      //切换搜索源
      sendEventData:function(ev){
      var that=this;
        var node = ev.currentTarget;
        that.sousouicon=that.iconurl+node.dataset.icon;
        that.isSousouCard=!that.isSousouCard;
        that.$refs.sousouref.focus()
      },
      sendEventDataTop:function(ev){
        var that=this;
        var node = ev.currentTarget;
        that.sousouicon=that.iconurl+node.dataset.icon;
        that.$refs.sousouref.focus()

      },



      editBookmark:function(){
        var that=this;
        alert("this button");
// //如果提供了事件对象，则这是一个非IE浏览器
//         if ( e && e.stopPropagation )
//         //因此它支持W3C的stopPropagation()方法
//           e.stopPropagation();
//         else
//         //否则，我们需要使用IE的方式来取消事件冒泡
//           window.event.cancelBubble = true;

      },
      //新打开新窗口
      getUrl:function () {
      var that=this;
      if (that.sousou!=null&&that.sousou!=undefined&&that.sousou!='') {
        var url=that.sousouicon+that.sousou;
        window.open(url);
        //window.location.href=url;
        }else {
        // that.$notify({
        //   title:'提示',
        //   type: 'success',
        //   message: '搜了个寂寞,内容不能为空!',
        // });
        that.$refs.sousouref.focus()
        }


      }
    },
    mounted(){
      var that=this;
      $.fn.zTree.init($("#treeDemo"), this.setting, this.zNodes).expandAll(that.expandAll);

      //光标自动选中
      that.$refs.sousouref.focus()

      // var _this = this
      //
      // var treeObj = $("#treeDemo");
      // $.fn.zTree.init(treeObj, this.setting, this.zNodes);
      // _this.zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
      // curMenu = zTree_Menu.getNodes()[0].children[0].children[0];
      // _this.zTree_Menu.selectNode(curMenu);
      //
      // treeObj.hover(function () {
      //   if (!treeObj.hasClass("showIcon")) {
      //     treeObj.addClass("showIcon");
      //   }
      // }, function() {
      //   treeObj.removeClass("showIcon");
      // });

    },




  }
</script>
<style >
  body{
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
  }


  .ztree li ul{  margin:0;  padding:0}
  .ztree li {line-height:30px}
  .ztree li a {width:200px;height:30px;padding-top: 0px;font-size: 14px;width:100%}
  .ztree li a:hover {text-decoration:none; background-color: #E3E3E3;}
  .ztree li a span.button.switch {vertical-align:middle}
  .ztree.showIcon li a span.button.switch {visibility:visible}
  .ztree li a.curSelectedNode {background-color:#D4D4D4;border:0;height:30px;}
  .ztree li span {line-height:30px;}
  .ztree li span.button {margin-top: -4px;margin-left:3px}
  .ztree li span.button.switch {width: 16px;height: 16px;}

  /*.ztree li a.level0 span {font-size: 100%;font-weight: bold}*/
  .ztree li span.button {background-image:url("../ztree/left_menuForOutLook.png"); *background-image:url("../ztree/left_menuForOutLook.gif")}
  .ztree li span.button.switch.level0 {width: 20px; height:20px}
  .ztree li span.button.switch.level1 {width: 20px; height:20px}
  .ztree li span.button.noline_open {background-position: 0 0;}
  .ztree li span.button.noline_close {background-position: -18px 0;}
  .ztree li span.button.noline_open.level0 {background-position: 0 0;}
  .ztree li span.button.noline_close.level0 {background-position:-18px 0;}


  .ztree li span.button.ico_close{vertical-align: middle}

  .ztree li span.button.ico_open{vertical-align: middle}

  .ztree li span.button.ico_docu {vertical-align: middle}


  .ztr{
  /*//background{ width:100%;height:100%;position:absolute;top:0px;opacity: 0.6;background-image: url(https://s1.ax1x.com/2020/07/27/akFjER.jpg);background-size: 500px 100px;}*/
  /*background:red;*/
  }

  .areaTree{
    width: 300px;
    /*height: 700px;*/
    font-weight: 400;
   /*overflow:auto;*/
    background-color: #F6F6F6;
   /* background:url('https://ftp.bmp.ovh/imgs/2020/08/4ac1d6b4f41049ef.jpg') no-repeat;*/
    background-size: 100% 100%;
    /*position:relative;*/
    /*animation-name:mymove;*/
    /*animation-duration:1s;!* 5s表示执行动画的时间，0或不写则无动画效果 *!*/
    /*!* 兼容调试如果animation-name执行，那么-wekit-animation-name则不执行 *!*/
    /*-webkit-animation-name:mymove;*/
    /*-webkit-animation-duration:1s;!* 5s表示执行动画的时间，0或不写则无动画效果 *!*/

  }

  @keyframes mymove /* 对应animation-name，里面为执行的动画*/
  {
    from {left:-200px;}/*执行动画的初始位置*/
    to {left:0px;}/*动画结束位置*/
    0% {
      opacity: 0.6; /*初始状态 透明度为10%*/
    }
    50% {
      opacity: 0.9; /*中间状态 透明度为50%*/
    }
    100% {
      opacity: 1; /*结尾状态 不透明*/
    }
  }

  @-webkit-keyframes mymove/* 对应-webkit-animation-name，里面为执行的动画*/
  {
    from {left:-200px;}/*执行动画的初始位置*/
    to {left:300px;}/*动画结束位置*/
  }

.app-main{
width: 100%;
  height: 500px;
  background-image: url('https://byr-navi.com/images/backgrounds/14.jpg') ;
  background-size: 100%;
  background-repeat: no-repeat;
  height: 500px;
}


  .sousoucss{
    opacity: 0.6;

  }
  .sousoucss button:hover{
    color: #1c84c6;
  }
  .el-input-group__append{
    background-color: #1f2d3d!important;
    opacity: 0.7;
  }


  .choice {
    margin: 5px 5px 5px 5px;
    background-color: #FFFFFF;

  }
  .choice div{
    float:left;
    height: 30px;
    line-height: 30px;
    border-radius: 5px;
    text-align: center;
    margin: 5px 5px 5px 5px;
    /*box-shadow:0 0 1px #000 ;*/
    background: none #fff;
    border: 1px solid rgba(34,36,38,.15);
    color: rgba(0,0,0,.87);
    -webkit-box-shadow: none;
    box-shadow: none;
    font-size: 14px;
    font-weight: 700;
    /*font-family: Lato,'Helvetica Neue',Arial,Helvetica,sans-serif;*/
    align-items: center;
    vertical-align:middle;
    width:fit-content;
    /*width:-webkit-fit-content;*/
    width:-moz-fit-content;
    padding-left:12px;
    padding-right:12px;
  }
  .choice div i{
    margin-right: 1px;
  }
  .choice div:hover{
    color: #1c84c6;

  }
/**顶部*/
  .index-top{
    width: 100%;
    height: 50px;
    background-color: #3c5d5b;
    position: absolute;
    top: 0;
    opacity: 0.2;

  }

  /*搜索 */
  .sousouicon{
    width:16px;
    height:16px;
    vertical-align:middle;
    margin-right: 3px;
    margin-bottom: 3px;

  }
/*搜索top 上面的*/
  .choice-top img{
    width:16px;
    height:16px;
    vertical-align:middle;
    margin-bottom: 3px;
  }
  .choice-top div{
    float:left;
    height: 30px;
    line-height: 30px;
    border-radius: 5px;
    text-align: center;
    margin: 5px 5px 5px 5px;
    background-color: #3c5d5b;
    border: 1px solid rgba(34,36,38,.15);
    padding-left:12px;
    padding-right:12px;
    opacity: 0.8;

  }


  /**滚动条美化*/

  /*滚动条的宽度*/

  ::-webkit-scrollbar {
    width:5px;
    height:3px;
    /*background-color: red;*/
  }

  /*外层轨道。可以用display:none让其不显示，也可以添加背景图片，颜色改变显示效果*/

  ::-webkit-scrollbar-track {
    width: 6px;
    background-color:#fff;
    -webkit-border-radius: 2em;
    -moz-border-radius: 2em;
    border-radius:2em;
  }

  /*滚动条的设置*/

  ::-webkit-scrollbar-thumb {
    background-color:#606d71;
    background-clip:padding-box;
    min-height:28px;
    -webkit-border-radius: 2em;
    -moz-border-radius: 2em;
    border-radius:2em;

  }
  /*滚动条移上去的背景*/

  ::-webkit-scrollbar-thumb:hover {
    background-color: #7c6fff;
  }
/*滚动条美化结束*/
</style>

