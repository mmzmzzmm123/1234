<template>
  <div id="areaTree" style="width: 300px">
    <div class="ztr"  style="width: 100%">
      <ul id="treeDemo" class="ztree" ></ul>
    </div>

  </div>
</template>


<script>
  // 下面的是单个Vue组件引用的外部静态文件，也可以在main.js文件中引用
  import "../ztree/jquery-1.4.4.min.js"
 import "../ztree/jquery.ztree.core.js"
  import "../ztree/demo.css"
  import "../ztree/zTreeStyle.css"

  export default {
    name: 'areaTree',
    components:{

    },
    data:function(){
      return{
        curMenu:null,
        zTree_Menu:null,
        setting:{
          view: {
            showLine: false,
            showIcon: true,
            selectedMulti: false,
            dblClickExpand: false,
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
          { id:1, pId:0, name:"文件夹",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:11, pId:1, name:"收件箱",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:111, pId:11, name:"收件箱1",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:112, pId:111, name:"收件箱2",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:113, pId:112, name:"收件箱3",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:114, pId:113, name:"收件箱4",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:32, pId:114, name:"照片",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:32, pId:114, name:"照片",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:32, pId:114, name:"照片",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:32, pId:114, name:"照片",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:32, pId:114, name:"照片",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:399, pId:114, name:"照片",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:32, pId:399, name:"照片",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:12, pId:1, name:"垃圾邮件",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:13, pId:1, name:"草稿",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:14, pId:1, name:"已发送邮件",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:15, pId:1, name:"已删除邮件",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:3, pId:1, name:"快速视图",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:31, pId:3, name:"文档",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"},
          { id:32, pId:3, name:"照片",icon:"https://s1.ax1x.com/2020/07/27/akpZ4O.png"}
          ]
      }
    },
    methods:{
      // freshArea: function(){
      //   $.fn.zTree.init($("#treeDemo"), this.setting, this.zNodes);
      // },
      // zTreeOnClick: function(event, treeId, treeNode) {
      //   console.log(treeNode.tId + ", " + treeNode.name);
      // },
      // zTreeOnCheck: function(event, treeId, treeNode) {
      //   console.log(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
      //   var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
      //     checkCount = zTree.getCheckedNodes(true).length,//选中
      //     nocheckCount = zTree.getCheckedNodes(false).length,//未选中
      //     changeCount = zTree.getChangeCheckedNodes().length;//获取输入框勾选状态被改变的节点集合（与原始数据 checkedOld 对比）
      //   var checkedNames = [],checkedIds = [];
      //   for (var i = 0; i <= zTree.getCheckedNodes(true).length - 1; i++) {
      //     checkedIds.push(zTree.getCheckedNodes(true)[i].id);
      //     checkedNames.push(zTree.getCheckedNodes(true)[i].name);
      //   };
      //   console.log(checkedIds);
      //   console.log(checkedNames);
      // },
      // beforeClick: function(treeId, treeNode) {
      //   var zTree = $.fn.zTree.getZTreeObj("treeDemo");
      //   // zTree.checkNode(treeNode, !treeNode.checked, null, true);
      //   zTree.checkNode(treeNode, !treeNode.checked, true, true); //第二个参数!treeNode.checked和"",省略此参数效果等同，则根据对此节点的勾选状态进行 toggle 切换，第三个参数设置为true时候进行父子节点的勾选联动操作 ，第四个参数true 表示执行此方法时触发 beforeCheck & onCheck 事件回调函数；false 表示执行此方法时不触发事件回调函数
      //   return false;
      // }



       addDiyDom:function(treeId, treeNode) {
    var spaceWidth = 5;
    var switchObj = $("#" + treeNode.tId + "_switch"),
      icoObj = $("#" + treeNode.tId + "_ico");
    switchObj.remove();
    icoObj.before(switchObj);

    if (treeNode.level > 1) {
      var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
      switchObj.before(spaceStr);
    }
  },

   beforeClick:function(treeId, treeNode) {
    if (treeNode.level == 0 ) {
      var zTree = $.fn.zTree.getZTreeObj("treeDemo");
      zTree.expandNode(treeNode);
      return false;
    }
    return true;
  }
  //







    },
    mounted(){
      //$.fn.zTree.init($("#treeDemo"), this.setting, this.zNodes).expandAll(true);

      var _this = this

      var treeObj = $("#treeDemo");
      $.fn.zTree.init(treeObj, this.setting, this.zNodes);
      _this.zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
      curMenu = zTree_Menu.getNodes()[0].children[0].children[0];
      _this.zTree_Menu.selectNode(curMenu);

      treeObj.hover(function () {
        if (!treeObj.hasClass("showIcon")) {
          treeObj.addClass("showIcon");
        }
      }, function() {
        treeObj.removeClass("showIcon");
      });

    },

  }
</script>
<style>

  .ztree li ul{  margin:0;  padding:0}
  .ztree li {line-height:30px}
  .ztree li a {width:200px;height:30px;padding-top: 0px;;width:100%}
  .ztree li a:hover {text-decoration:none; background-color: #E7E7E7;}
  .ztree li a span.button.switch {vertical-align:middle}
  .ztree.showIcon li a span.button.switch {visibility:visible}
  .ztree li a.curSelectedNode {background-color:#D4D4D4;border:0;height:30px;}
  .ztree li span {line-height:30px;}
  .ztree li span.button {margin-top: -7px;;margin-left:2px}
  .ztree li span.button.switch {width: 16px;height: 16px;}

  .ztree li a.level0 span {font-size: 100%;font-weight: bold}
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




</style>
