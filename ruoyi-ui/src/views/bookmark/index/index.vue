<template >
  <div>

  <el-container>
    <transition name="el-zoom-in-left">
    <el-aside width="300px" style="height:900px;box-shadow: inset -1px 0 0 rgba(0,0,0,.1);" v-show="isShowZtree" class="transition-box">


      <el-header  class="aside-logo">
        <img src="https://s1.ax1x.com/2020/08/15/dACqUO.png"/>

      </el-header>



      <div class="aside-title" @click="goBookmarkList"><i class="el-icon-s-tools"></i><span>全部书签</span></div>
      <div class="aside-title"><i class="el-icon-help"></i><span>发现</span></div>
      <div class="aside-title"><i class="el-icon-s-platform"></i><span>任意门</span></div>
      <div class="aside-title"><i class="el-icon-message-solid"></i><span>收件箱</span></div>
      <div class="reminder">我的收藏</div>
      <div class="areaTree" >
        <ul id="treeDemo" class="ztree" ></ul>
      </div>
      <div class="reminder">工具箱</div>
      <div class="aside-title"><i class="el-icon-s-flag" style="color: red"></i><span>书签同步</span></div>
      <div class="aside-title"><i class="el-icon-s-management" ></i><span>标签管理</span></div>
      <div class="aside-title" @click="gorecycle"><i class="el-icon-delete-solid"></i><span>回收站</span></div>
      <div class="aside-title" @click="importHtml"><i class="el-icon-s-platform"></i><span>导入书签</span></div>
      <div class="aside-title"><i class="el-icon-s-comment"></i><span>意见反馈</span></div>
      <div class="aside-title"><i class="el-icon-s-grid"></i><span>其他设置</span></div>

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

        <router-view :key="$route.query.menuId"></router-view>


      </el-main>
      <el-footer>Footer</el-footer>

    </el-container>

  </el-container>



<!--  编辑弹窗-->
  <el-dialog  :title="title"  :visible.sync="open" width="500px" class="menuedit"  append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item prop="menuName">
        <div class="labelname">菜单名称</div>
        <el-input class="custom-input" v-model="form.menuName" placeholder="请输入菜单名称" />
      </el-form-item>
      <el-form-item  prop="menuIcon">
        <div class="labelname">菜单图标</div>
        <el-input class="custom-input" v-model="form.menuIcon" placeholder="请输入菜单图标" />
      </el-form-item>
      <el-form-item  prop="parentId">
        <div class="labelname">上级菜单</div>
        <treeselect class="custom-input"  v-model="form.parentId" :options="menuOptions" :normalizer="normalizer" placeholder="请选择父菜单id" />
      </el-form-item>
      <el-form-item  prop="menuOrder">
        <div class="labelname">排序</div>
        <el-input class="custom-input" v-model="form.menuOrder" placeholder="请输入菜单排序" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">

      <el-button type="danger"  round  @click="deleteMmenu(form.menuId)">删除</el-button>


      <el-button type="primary"  round  @click="submitForm">确定</el-button>
      <el-button round  @click="cancel">取消</el-button>

    </div>
  </el-dialog>




<!--  &lt;!&ndash; 添加链接&ndash;&gt;-->
<!--    <el-dialog  title="添加链接"  :visible.sync="addurlopen" width="500px"  append-to-body class="addbookmarkurl">-->


<!--      <el-input  v-model="bookamkrurl" placeholder="输入链接地址 例：https://withpinbox.com" class="addbookmarkurl-input"  />-->

<!--      <el-button  plain  class="addbookmarkurl-button"  round  @click="submitForm">确定</el-button>-->

<!--    </el-dialog>-->






  </div>


</template>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>

<script>
  // 下面的是单个Vue组件引用的外部静态文件，也可以在main.js文件中引用

  import { listMenu, getMenu, delMenu, addMenu, updateMenu, exportMenu } from "@/api/bookmark/menu";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import "../ztree/jquery-1.4.4.min.js"
 import "../ztree/jquery.ztree.core.js"
  import "../ztree/demo.css"
  import "../ztree/zTreeStyle.css"
  import "../ztree/jquery.ztree.exedit.js"
  import { listMenuByUserId  } from "@/api/bookmark/menu";
  export default {
    name: 'areaTree',
    components:{
      Treeselect
    },

    data:function(){
      return{
        queryParams: {
          userId: undefined,
          menuName: undefined,
          menuUrl: undefined,
          menuIcon: undefined,
          parentId: undefined,
          menuOrder: undefined,
        },
        // 书签菜单树选项
        menuOptions: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层 编辑添加
        open: false,
        //书签URL
        bookamkrurl:'',
        //添加url
        addurlopen:true,
        // 表单参数
        form: {},
        // 表单校验
        rules: {
        },
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
        zNodes:[],
        bookmark:[]
      }
    },
    created() {
      var that=this;
      //书签菜单
      that.getList();
    },
    methods:{

      /** 转换书签菜单数据结构 */
      normalizer(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.menuId,
          label: node.menuName,
          children: node.children
        };
      },
      /** 查询部门下拉树结构 */
      getTreeselect() {
        listMenuByUserId().then(response => {
          this.menuOptions = [];
          const data = { menuId: 0, menuName: '顶级菜单', children: [] };
          data.children = this.handleTree(response.data, "menuId", "parentId");
          this.menuOptions.push(data);
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      /** 查询用户的书签菜单Menu */
      getList() {
        listMenuByUserId().then(response => {
          this.zNodes = response.data;
          console.log(response.data)
          //加载Ztree树
          $.fn.zTree.init($("#treeDemo"), this.setting, this.zNodes).expandAll(this.expandAll);
        });

      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.menuId != undefined) {
              if (this.form.menuId==this.form.parentId){
                this.msgError("不能讲上级菜单设置为本身");
                return;
              }


              updateMenu(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addMenu(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          }
        });
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.getTreeselect();
        this.open = true;
        this.title = "添加书签菜单";
      },
      // /** 修改按钮操作 */
      // handleUpdate(row) {
      //   this.reset();
      //   this.getTreeselect(); //树
      //   if (row != undefined) {
      //     this.form.parentId = row.menuId;
      //   }
      //   getMenu(row.menuId).then(response => {
      //     this.form = response.data;
      //     this.open = true;
      //     this.title = "修改书签菜单";
      //   });
      // },
      // 表单重置
      reset() {
        this.form = {
          menuId: undefined,
          userId: undefined,
          menuName: undefined,
          menuUrl: undefined,
          menuIcon: undefined,
          parentId: undefined,
          menuOrder: undefined,
          createTime: undefined
        };
        this.resetForm("form");
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

  	var editStr = "<span class="+treeNode.tId+"_sz data-parentId="+treeNode.parentId+" data-menuId="+treeNode.menuId+" onclick='editBookmark(this)' style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:8px' onfocus='this.blur();'><i class='el-icon-edit'></i></span>";
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
    //    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    //    zTree.expandNode(treeNode);

    //  return false;
    // }
    //return true;
  },
      /** 点击跳转**/
      //节点点击
      OnClickzTree:function(event,treeId, treeNode){
        var that=this;
        that.$router.push({
          path: "/content",
          query: {
            menuId: treeNode.menuId
          }
        })


        // $("." + treeNode.tId + "_sz").unbind().remove();
        // var switchObjspan = $("#" + treeNode.tId + "_span");
        //
        // var confCount = $("#" + treeNode.tId + "_count").length;
        // if (confCount>0) return;
        // var editStr = "<span class="+treeNode.tId+"_count onclick='alert(1111111);return false;' style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:8px' onfocus='this.blur();'>12</span>";
        // switchObjspan.after(editStr);
      // return false;
      },
      //显示隐藏 ztree菜单
      zreaZtree:function () {
      var that=this;
        that.isShowZtree = !that.isShowZtree;

      },
      /**跳转导入页面**/
      importHtml:function(){
        this.$router.push({
          path: "/importHtml",
        })

      },



      editBookmark:function(e){
        this.reset();
        this.getTreeselect();
        if (e.getAttribute("data-menuId")!=null&&e.getAttribute("data-parentId")!=null) {
          this.form.parentId = e.getAttribute("data-parentId");
        }
        getMenu(e.getAttribute("data-menuId")).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改书签菜单";
        });


        //阻止冒泡事件
        if ( e && e.stopPropagation )
        //因此它支持W3C的stopPropagation()方法
          e.stopPropagation();
        else
        //否则，我们需要使用IE的方式来取消事件冒泡
          window.event.cancelBubble = true;
        return false;
      },

      //删除书签目录
      deleteMmenu(menuId){

        this.$confirm('是否删除此目录菜单?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            delMenu(menuId).then(response => {
              // if(){}
              this.$message({
                type: 'success',
                message: '删除成功!'
              });

              this.open = false;
              this.getList();
            });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });


      },
      // 全部书签
      goBookmarkList(){
        var that=this;
        that.$router.push({
          path: "/content",
          query: {
            menuId: 'BOOKMARK'
          }
        })

      },
      // 回收站
      gorecycle(){
        var that=this;
        that.$router.push({
          path: "/content",
          query: {
            menuId: 'RECYCLE'
          }
        })

      },

    },
    mounted(){
      window['editBookmark'] = (e) => {
        this.editBookmark(e)
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
    /*background-color: #fff;*/
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
  .labelname{
    font-weight: 800;
    color: rgb(0, 0, 0);
    float: left;
    margin-bottom: -10px;
  }
  .el-form-item__content{
    margin-left: 0px!important;
  }
  .el-dialog__body{
    padding-top: 0px!important;
  }
  .custom-input input{
    border: 0px;
    border-radius: 0px;
    border-bottom: 1px solid #C0C4CC;
  }
  .custom-input input:focus{
    border: 0px;
    border-radius: 0px;
    border-bottom: 1px solid #695fff;
  }

  /*添加bookmarkurl*/
  .addbookmarkurl input{
    border: 0px;
    border-radius: 0px;
    border-bottom: 1px solid #409EFF;
  }
  .addbookmarkurl input:focus{
    border-bottom: 1px solid #409EFF;
  }
  .addbookmarkurl input:hover{

    border-bottom: 1px solid #409EFF;
  }


  .addbookmarkurl .el-dialog{
    top: 40%;
  }


  .addbookmarkurl .el-dialog__body{
    padding-right: 10px;
    height: 60px!important;
  }

  .addbookmarkurl-input {
    width: 82%!important;
    float: left;
    font-size: 13px;
  }
 .addbookmarkurl-button {
   float: left;
   margin-left: 5px;
   border: 1px solid #409EFF!important;
  }
  .addbookmarkurl-button:hover {
  background-color: #409EFF!important;
    color: #FFFFFF!important;
  }

  .el-dialog{
    border-radius: 10px!important;
  }





</style>

