<template >

  <el-container>
    <transition name="el-zoom-in-left">
    <el-aside width="300px" style="height:900px" v-show="isShowZtree" class="transition-box">


      <el-header  class="aside-logo">
        <img src="https://s1.ax1x.com/2020/08/15/dACqUO.png"/>

      </el-header>



      <div class="aside-title"><i class="el-icon-s-tools"></i><span>全部书签</span></div>
      <div class="aside-title"><i class="el-icon-help"></i><span>发现</span></div>
      <div class="aside-title"><i class="el-icon-s-platform"></i><span>任意门</span></div>
      <div class="aside-title"><i class="el-icon-message-solid"></i><span>收件箱</span></div>
      <div class="reminder">我的收藏</div>
      <div class="areaTree" >
        <ul id="treeDemo" class="ztree" ></ul>
      </div>
      <div class="reminder">工具箱</div>
      <div class="aside-title"><i class="el-icon-s-tools"></i><span>所有书签</span></div>
      <div class="aside-title"><i class="el-icon-help"></i><span>发现书签</span></div>
      <div class="aside-title"><i class="el-icon-s-platform"></i><span>任意门</span></div>
      <div class="aside-title"><i class="el-icon-message-solid"></i><span>收件箱</span></div>

<!--      <el-footer class="aside-navigation">-->
<!--      </el-footer>-->
    </el-aside>

    </transition>

    <el-drawer
      title="我是标题"
      :visible.sync="drawer"
      :direction="direction"
      :modal-append-to-body="drawerS"
      :append-to-body="isShowZtree"
    >
      <span>我来啦!</span>
    </el-drawer>



<!--    <el-button @click="isShowZtree = !isShowZtree">Click Me</el-button>-->
    <el-container>

      <el-header  class="header-sousou" style="height: 50px">
      <div class="sousou-left">
        <div class="sousouleft-switch" @click="drawer = true"><i class="el-icon-s-unfold"/></div>
        <div class="sousou-leftico" @click="drawer = true"><img src="https://favicon.lucq.fun/?url=https://www.baidu.com"/></div>
      </div>

        <div class="sousou-input">

        <el-input placeholder="请输入书签名字" v-model="sousou" size="small">
        <i slot="prefix" class="el-input__icon el-icon-search"></i>
      </el-input>
        </div>

        <div class="sousouright-icon"><el-badge :value="5" :max="99" class="item" ><i class="el-icon-message-solid" style="font-size: 17px;"></i></el-badge></div>

        <div class="sousouright-icon">
          <el-dropdown trigger="click" size="small" :hide-on-click="false"  >
              <span class="el-dropdown-link">
          <i class="el-icon-plus" style="font-size: 18px;"/>
             </span>
            <el-dropdown-menu  slot="dropdown" class="sq-dropdown">
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="a">添加连接</el-dropdown-item>
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="b">添加文本</el-dropdown-item>
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="d" >导入书签</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>


          </div>





        <div class="header-list">
          <el-dropdown trigger="click" size="small" :hide-on-click="false"  >
              <span class="el-dropdown-link">
           <el-avatar  :size="28" src="https://up.raindrop.io/collection/templates/social-media-logos-6/97social.png"></el-avatar>
              </span>
            <el-dropdown-menu  slot="dropdown" class="sq-dropdown">
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="a">外观显示</el-dropdown-item>
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="b">用户中心</el-dropdown-item>
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="d" >建议反馈</el-dropdown-item>
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="e" >退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>


      </el-header>
      <el-main class="bookmarkmain" >

        <router-view></router-view>


      </el-main>
      <el-footer>Footer</el-footer>

    </el-container>

  </el-container>
















</template>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>

<script>
  // 下面的是单个Vue组件引用的外部静态文件，也可以在main.js文件中引用
  import "../ztree/jquery-1.4.4.min.js"
 import "../ztree/jquery.ztree.core.js"
  import "../ztree/demo.css"
  import "../ztree/zTreeStyle.css"
  import "../ztree/jquery.ztree.exedit.js"
  import { listMenuByUserId  } from "@/api/bookmark/menu";
  export default {
    name: 'areaTree',
    components:{

    },

    data:function(){
      return{
        drawerS:false,
        drawer: false,
        direction: 'ltr',
        swictxuanran:true,
        sousou:'',//搜索书签
        enterable:false,
        isShowZtree:true,//ztree树显示
        expandAll:false,//是否展开ztree树
        curMenu:null,
        zTree_Menu:null,
        setting:{
          view: {
            showLine: false,
            showIcon: true,
            selectedMulti: false,
            dblClickExpand: false,
            addHoverDom: this.addHoverDom,
            removeHoverDom: this.removeHoverDom,
            addDiyDom: this.addDiyDom,
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
            onClick: this.OnClickzTree,
            // onCheck: this.zTreeOnCheck,

          }
        },
        zNodes:[
          // { id:1, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.baidu.com"},
          // { id:2, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://movie.douban.com/"},
          // { id:3, pId:2, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:4, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.w3school.com.cn/"},
          // { id:5, pId:4, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:6, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:7, pId:6, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:8, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://open.163.com/"},
          // { id:9, pId:8, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:1123, pId:0, name:"文件夹",icon:"https://favicon.lucq.fun/?url=https://y.qq.com"},
          // { id:1142, pId:1, name:"收件箱",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:111, pId:19, name:"收件箱1",icon:"https://favicon.lucq.fun/?url=https://mail.sina.com.cn/"},
          // { id:112, pId:111, name:"收件箱2",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:113, pId:112, name:"收件箱3",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:114, pId:113, name:"收件箱4",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:1321, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:2322, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:3323, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:4324, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:5325, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:6399, pId:114, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:32, pId:399, name:"照片",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:12, pId:1, name:"垃圾邮件",icon:"https://favicon.lucq.fun/?url=https://www.google.com/"},
          // { id:13, pId:1, name:"草稿",icon:"https://favicon.lucq.fun/?url=https://yz.m.sm.cn/"},
          // { id:14, pId:1, name:"已发送邮件",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:15, pId:1, name:"已删除邮件",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:322, pId:11, name:"快速视图",icon:"https://favicon.lucq.fun/?url=https://taobao.com/"},
          // { id:31, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:131, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:231, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:331, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:431, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:531, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:631, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:731, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          // { id:831, pId:3, name:"文档",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          ],
        bookmark:[
          {id:1,title:"最大的骄傲于最大的自卑都表示心灵的最软弱无力。——斯宾诺莎",description:"阅读使人充实，会谈使人敏捷，写作使人精确。——培根",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          {id:1,title:"意志坚强的人能把世界放在手中像泥块一样任意揉捏。——歌德",description:"最具挑战性的挑战莫过于提升自我。——迈克尔·F·斯特利",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          {id:1,title:"要知道对好事的称颂过于夸大，也会招来人们的反感轻蔑和嫉妒。——培根",description:"意志命运往往背道而驰，决心到最后会全部推倒。——莎士比亚",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          {id:1,title:"要使整个人生都过得舒适、愉快，这是不可能的，因为人类必须具备一种能应付逆境的态度。——卢梭",description:"只有把抱怨环境的心情，化为上进的力量，才是成功的保证。——罗曼·罗兰",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          {id:1,title:"希望的灯一旦熄灭，生活刹那间变成了一片黑暗。——普列姆昌德",description:"到很多东西的诀窍，就是一下子不要学很多。——洛克",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          {id:1,title:"我这个人走得很慢，但是我从不后退。——亚伯拉罕·林肯",description:"重复别人所说的话，只需要教育；而要挑战别人所说的话，则需要头脑。——玛丽·佩蒂博恩·普尔",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          {id:1,title:"我们若已接受最坏的，就再没有什么损失。——卡耐基",description:"书籍把我们引入最美好的社会，使我们认识各个时代的伟大智者。——史美尔斯",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
          {id:1,title:"我的努力求学没有得到别的好处，只不过是愈来愈发觉自己的无知。——笛卡儿",description:"少而好学，如日出之阳；壮而好学，如日中之光；志而好学，如炳烛之光。——刘向",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},

        ]
      }
    },
    created() {
      var that=this;
      //书签菜单
      that.getList();
    },
    methods:{
      /** 查询用户的书签菜单Menu */
      getList() {
        listMenuByUserId().then(response => {
          this.zNodes = response.data;
          console.log(response.data)
          //加载Ztree树
          $.fn.zTree.init($("#treeDemo"), this.setting, this.zNodes).expandAll(this.expandAll);
        });

      },


    addDiyDom:function(treeId, treeNode) {
      // console.log("自定义ztree:"+treeId.tId+"___treeNode："+treeNode.tId)
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
  	var editStr = "<span class="+treeNode.tId+"_sz onclick='editBookmark()' style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:8px' onfocus='this.blur();'><i class='el-icon-edit'></i></span>";
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
      //节点点击
      OnClickzTree:function(event,treeId, treeNode){
      // alert("11"+treeNode.tId );

        // $("." + treeNode.tId + "_sz").unbind().remove();
        // var switchObjspan = $("#" + treeNode.tId + "_span");
        //
        // var confCount = $("#" + treeNode.tId + "_count").length;
        // if (confCount>0) return;
        // var editStr = "<span class="+treeNode.tId+"_count onclick='alert(1111111);return false;' style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:8px' onfocus='this.blur();'>12</span>";
        // switchObjspan.after(editStr);
      return false;
      },
      //显示隐藏 ztree菜单
      zreaZtree:function () {
      var that=this;
        that.isShowZtree = !that.isShowZtree;

      },



      editBookmark:function(e){

        alert("this button");

        if ( e && e.stopPropagation )
        //因此它支持W3C的stopPropagation()方法
          e.stopPropagation();
        else
        //否则，我们需要使用IE的方式来取消事件冒泡
          window.event.cancelBubble = true;

        return false;


      },

    },
    mounted(){

      window['editBookmark'] = () => {
        this.editBookmark()
      }

    },
    handleCommand(command) {
      this.$message('click on item ' + command);
    },





  }
</script>
<style >






  body{
    font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
  }


  .ztree li ul{  margin:0;  padding:0}
  .ztree li {line-height:32px}
  .ztree li a {width:200px;height:32px;padding-top: 0px;font-size: 14px;width:100%}
  .ztree li a:hover {text-decoration:none; background-color: #D4D4D4;}
  .ztree li a span.button.switch {vertical-align:middle}
  .ztree.showIcon li a span.button.switch {visibility:visible}
  .ztree li a.curSelectedNode {background-color:#D4D4D4;border:0;height:32px;}
  .ztree li span {line-height:32px;}
  .ztree li span.button {margin-top: -4px;margin-left:3px;width: 21px;height: 21px;}

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

  /*.areaTree{*/
  /*  width: 300px;*/
  /*  !*height: 700px;*!*/
  /*  font-weight: 400;*/
  /* !*overflow:auto;*!*/
  /*  background-color: #F6F6F6;*/
  /* !* background:url('https://ftp.bmp.ovh/imgs/2020/08/4ac1d6b4f41049ef.jpg') no-repeat;*!*/
  /*  background-size: 100% 100%;*/

  /*}*/

  aside{
    padding:0;
    margin-bottom: 0;
    /*background:url('https://ftp.bmp.ovh/imgs/2020/08/4ac1d6b4f41049ef.jpg') no-repeat;*/
    background-color: #f6f6f6;
    /*background: url(http://csssecrets.io/images/tiger.jpg) no-repeat;*/
    /*background-size: cover;*/
    /*z-index: -1;!*-1 可以当背景*!*/
    /*-webkit-filter: blur(3px);*/
    /*filter: blur(3px);*/


  }

  .aside-title{
    height: 32px;
  }
  .aside-title:hover{
    background-color: #c5c5c5;
  }
  .aside-title i{
    margin-left: 26px;
    font-size: 20px;
    margin-right: 11px;
    vertical-align:middle;
  }
  .aside-title span{
    font-size: 14px;
    margin-right: 11px;
  }
  .aside-logo{
    background-color: #fff;
    opacity: 0.9;
  }
  .aside-logo img{
    margin-top: 5px;
    background-repeat: no-repeat;
    height: 50px;

  }

  .reminder{
    margin-left: 27px;
    color: #878787;
    opacity: 0.6;
    font-size: 14px;
    font-weight: 500;
  }

  .aside-navigation{
    width: 100%;
    height: 30px;
    background-color: #a0c4ff;

  }
  .filter{
    /*width: 100%;*/
    /*height: 30px;*/
    /*position: relative;*/
    /*margin-top: 0;*/
    /*background-color: #ffffff;*/
    /*margin-bottom: 5px;*/

    display: flex;
    flex-wrap: wrap;
  }
  .filter-sort{
    color: #7e868d;
    box-shadow: inset 0 0 0 1px rgba(0,0,0,.08), inset 0 -1px 0 rgba(0,0,0,.04);
    margin-right: 4px;
    border-radius: 2px;
    height: 22px;
    font-size: 12px;
    line-height: 22px;
    text-align: center;
    float: left;
    padding: 0 8px;
  }
  .filter-sort:hover{
    color: #6f8eee;
    background-color: #e6e6e6;

  }
  .bookmarkmain{
    padding-top: 0px!important;
  }
  .separator{
    float: left;
  }
  .filter-sort i{
    margin-left: -4px;
    margin-right: -1px;
  }
  .filter div{
    margin-bottom: 3px;
  }
  .header-sousou{
    display: flex;
  }

  .header-sousou input{
    margin-top: 8px;
    border: 0px;
    background-color: #f2f2f2;
    border-radius: 0!important;


  }
  .header-sousou input:focus{
    background-color: #FFFFFF!important;
    border:  1px solid #f2f2f2;
  }


  .header-sousou i{
    font-size: 1px;
    margin-top: 6px;
  }

  .margintop{
    margin-left: 10px;
  }



  .sousou-input{

     width: 82.5%;
    margin-right: 15px;


  }
  .header-list{
    /*line-height: 50px;*/
    margin-top: 10px;
    /*align-content: center;*/
    /*justify-content: center;*/
  }
  .header-list:hover{
    color: #7a6df0;

  }
  .header-list i{
    font-size: 25px;
    margin-right: 2px;
  }




  .main-label span i{
    margin-left: 5px;
  }

  .main-label span{
    line-height: 22px;
  }
  .main-label :nth-child(1){
    margin-right: -1px;
  }
  .main-label :nth-child(2){
    margin-right: 5px;
  }

  .bookmark-title{
    /*设置文本框大小*/
    white-space: nowrap;
    /*设置内容不换行*/
    text-overflow: ellipsis;
    /*设置文字超出文本框的内容显示成...*/
    overflow: hidden;
    /*超出部分隐藏*/
    font-size: 17px;
    font-weight: 600;
  }

  .description{
    /*设置文本框大小*/
    white-space: nowrap;
    /*设置内容不换行*/
    text-overflow: ellipsis;
    /*设置文字超出文本框的内容显示成...*/
    overflow: hidden;
    /*超出部分隐藏*/
    color: #545454;
    font-size: 13px;
  }
.bookmark{
  height: 65px;
}

  .bookmark-icon{
    float: left;
    margin-right: 5px;

  }
  .bookmark-icon img{
    width: 14px;
    height: 14px;
    vertical-align:middle;
  }



  .bookmark-official{
    float: left;
  }
  .info{
    font-size: 12px;

  }
  .info-wrap{
    color: #8c8c8c;
  }
  .bookmark-time{
    float: left;


  }

  .bookmark-hr{
    background-color: #fff!important;
   margin-top: 2px!important;
    margin-bottom: 7px!important;
  }
.sousouleft-switch{

  width: 30px;
  height: 30px;
  line-height: 50px;
  margin-top: 3px;
  margin-right: 5px;

}

  .sousouleft-switch i{
   font-size: 30px;
    opacity: 0.8;

  }
  .sousou-left{
    display: flex;
  }

  .sousou-leftico{

    width: 30px;
    height: 30px;
    border: #d1e9ff solid 1px;
    border-radius: 5px;
    opacity:0.7;
    text-align: center;
    line-height: 30px;
    margin-top: 9px;
    margin-right: 12px;

  }

  .sousou-leftico img{
    margin-top: 5px;
  }

  .filter-item:hover{
    background-color: #E5E5E5!important;
    color: #000!important;
  }

  .announcement{
    margin-top: 20px;
    width: 24%;
    height: 400px;
    /*background-color: #ff5f85;*/
    float: left;
    border-left:#8c8c8c 1px solid ;
    margin-left: 10px;
  }
  .bookmarklist{
    width: 100%;
    height: 800px;
    /*background-color: #fff;*/
    float: left
  }
  .sousouright-icon{
    margin-right: 24px;
    margin-top: 10px;
  }
  .sq-dropdown{
    top:33px!important;
    box-shadow: 0 2px 15px 0 rgba(0,0,0,.2)!important;
    font-weight: 800!important;
  }
  .sq-dropdown div{
    display: none!important;
  }





</style>

