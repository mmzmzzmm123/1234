<template>
  <div class="box" ref="box" @click="closePopup()">

    <el-container class="box" ref="box">
      <transition name="el-zoom-in-left">
        <el-aside :style="asideHeight" v-show="isShowZtree" class="transition-box left isaside"
                  style="overflow-x:hidden;overflow-y: hidden;" >

          <el-header class="aside-logo ">
            <!--        <img src="https://s1.ax1x.com/2020/08/15/dACqUO.png"/>-->
            <div class="logoname">
              <span>藏趣云书签</span>
            </div>
            <div class="logoright ">
              <i class="el-icon-plus mdui-ripple"/>
            </div>
          </el-header>



          <div class="main-right" >
<!--            <svg-icon icon-class="tool" class="svgicon"/>-->
            <div class="aside-title" @click="goRouter(8)"><i class="el-icon-folder"></i>
              <span>最新</span>
              <span class="menuCount">112</span>
            </div>
            <div class="aside-title" @click="goRouter(9)"><i class="el-icon-star-off"></i>
              <span>星标</span>
              <span class="menuCount">92</span>
            </div>
            <div class="aside-title" @click="goRouter(10)"><i class="el-icon-reading"></i>
              <span>稍后看</span>
              <span class="menuCount">5</span>
            </div>
<!--            <div class="aside-title"><i class="el-icon-view"></i>-->
<!--              <span>发现</span>-->
<!--              <span class="menuCount"></span>-->
<!--            </div>-->
<!--            <div class="aside-title"><i class="el-icon-message"></i>-->
<!--              <span>收件箱</span>-->
<!--              <span class="menuCount">5</span>-->
<!--            </div>-->





            <div class="aside-titleB" @click="menuListShowCk"   @mouseenter="eidtMenuText=!eidtMenuText" @mouseleave="eidtMenuText=!eidtMenuText">
              <i :class="menuListShow ? 'el-icon-caret-bottom aside-titleB_childi_one':'el-icon-caret-right aside-titleB_childi_one'"   ></i>
              <i class="el-icon-folder-opened aside-titleB_childi_two"></i>
              <span  style="width: 100%">我的收藏</span>
              <span style="display: flex;flex-direction:row;min-width: 65px" v-show="eidtMenuText">
                <i class="el-icon-search" style="font-size: 16px;margin-left: 5px;margin-top: 1px;" @click.stop="searchBkMenuCk"></i>
                <i class="el-icon-folder-add" style="font-size: 16px;margin-left: 5px;margin-top: 1px;" @click.stop="addBkMenuCk"></i>
              </span>
            </div>





            <div style="display: flex;justify-items: center;align-items: center">
              <el-input
                v-if="!addBkMenu"
                placeholder="输入收藏夹名字"
                v-model="input4"
                size="mini"
                style="width: 80%;margin-left: 5%"
              >
                <i slot="prefix" class="el-input__icon el-icon-circle-plus-outline"></i>
              </el-input>
              <i v-if="!addBkMenu" @click="addBkMenuCk" class="el-icon-close" style="font-size: 25px;margin-left: 5px;margin-bottom: 2px"></i>
            </div>

            <div style="display: flex;justify-items: center;align-items: center">
              <el-input
                v-if="!searchBkMenu"
                placeholder="搜索目录"
                v-model="input4"
                size="mini"
                style="width: 80%;margin-left: 5%"
              >
                <i slot="prefix" class="el-icon-search" style="margin-left: 5px"></i>
              </el-input>
              <i v-if="!searchBkMenu" @click="searchBkMenuCk" class="el-icon-close" style="font-size: 25px;margin-left: 5px;margin-bottom: 2px"></i>
            </div>

            <!-- 目录-->
            <el-collapse-transition>
              <div  class="areaTree transition-box" v-show="menuListShow">
                <ul id="treeDemo"  class="ztree"></ul>
              </div>
            </el-collapse-transition>



            <div class="reminder" STYLE="">工具箱</div>



              <!-- TAG标签 -->

                <usertag/>














<!--            <div class="aside-title"><i class="el-icon-s-flag" style="color: #569cd5"></i><span>RSS订阅</span></div>-->
<!--            <div class="aside-title" @click="goRouter(7)"><i class="el-icon-collection"></i><span>功能管理</span></div>-->
            <div class="aside-title" @click="goRouter(6)"><i class="el-icon-delete" ></i><span>垃圾桶</span></div>
<!--            <div class="aside-title"><i class="el-icon-chat-dot-square"></i><span>意见反馈</span></div>-->
<!--            <div class="aside-title" @click="goRouter(2)"><i class="el-icon-suitcase"></i><span>小工具</span></div>-->
            <div class="aside-title " style="margin-bottom: 100px"  @click="goRouter(11)"><i class="el-icon-setting"></i><span>更多设置</span></div>

          </div>






        </el-aside>
      </transition>




      <!-- 拖拽 -->
      <div class="isresize" style="cursor:w-resize">
      </div>



      <!--    <el-button @click="isShowZtree = !isShowZtree">Click Me</el-button>-->
      <el-container class="mid">
        <el-main class="bookmarkmain">
          <router-view :key="$route.query.t"></router-view>
        </el-main>
      </el-container>



    </el-container>
    <!--  编辑弹窗-->
    <el-dialog :title="title"  :show-close="false" :visible.sync="open" width="500px" class="menuedit" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" >
        <el-form-item prop="menuName" v-show="openType == 1 || openType == 4 ">
          <el-input class="custom-input" v-model="form.menuName" placeholder="请输入目录名称"/>
        </el-form-item>

        <el-form-item prop="menuIcon" v-show="openType == 2">
           <el-avatar :src="form.menuIcon" style="float: left;background-color: #ffffff;"></el-avatar>
          <el-input  class="custom-input"  style="float: left;margin-left:5px;width: 90%" v-model="form.menuIcon" placeholder="请输入远程图片地址(建议高:120宽:120)"/>
        </el-form-item>
        <el-form-item prop="parentId" v-show="openType == 3">
          <treeselect class="menutreeselect" v-model="form.parentId" :options="menuOptions" :normalizer="normalizer"/>
        </el-form-item>

      </el-form>


      <el-button class="butWidth" size="small" type="primary" @click="submitForm()">确 定</el-button>
      <el-button class="butWidth" style="margin-left: 0px"  size="small" @click="cancel">取 消</el-button>


<!--        <el-button type="danger"  @click="deleteMmenu(form.menuId)">删除</el-button>-->

    </el-dialog>


    <!--背景特效-->
    <canvas id="evanyou"></canvas>
  </div>






</template>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<style scoped src="../ztree/demo.css"/>




<script>
  // 下面的是单个Vue组件引用的外部静态文件，也可以在main.js文件中引用
  import {addBookmark} from "@/api/bookmark/bookmark";
  import { getToken } from '@/utils/auth'
  import { evanyoucss } from '@/utils/special.js'
  import {listMenu, getMenu, delMenu, addMenu, updateMenu, exportMenu,listMenuByUserId,listByMenuId} from "@/api/bookmark/menu";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import "../ztree/jquery.ztree.core.js"
  import "../ztree/zTreeStyle.css"
  import "../ztree/jquery.ztree.exedit.js"
  import usertag from '../tag/usertag.vue'

  export default {
    name: 'areaTree',
    components: {
      Treeselect,usertag
    },

    data: function () {
      return {
        addBkMenu:true,//添加书签目录
        searchBkMenu:true,//搜索目录
        menuListShow:true,//目录list
        eidtMenuText:false,//我的收藏



        queryParams: {
          userId: undefined,
          menuName: undefined,
          menuUrl: undefined,
          menuIcon: undefined,
          parentId: undefined,
          menuOrder: undefined,
        },
        bookmarkParams: {
          pageNum: 1,
          pageSize: 15,
          userid: undefined,
          title: undefined,
          url: undefined,
          urls: undefined,
          description: undefined,
          image: undefined,
          label: undefined,
          menuId: undefined,
          zcount: undefined,
          idelete: undefined,
          start: undefined,
          sqTags: []
        },
        // 书签菜单树选项
        menuOptions: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层 编辑添加
        open: false,
        // 表示是/1.修改名称/2.修改图标/3.移动目录 /4.新增目录
        openType:undefined,
        //添加连接
        addopen: false,
        //书签URL
        bookamkrurl: '',
        //添加url
        addurlopen: true,
        // 表单参数
        form: {},
        // 表单校验
        rules: {},
        drawerS: false,
        drawer: false,
        direction: 'ltr',
        swictxuanran: true,
        sousuo: '',//搜索书签
        enterable: false,
        isShowZtree: true,//ztree树显示
        expandAll: false,//是否展开ztree树
        curMenu: null,
        zTree_Menu: null,
        setting: {

          view: {
            showLine: false,
            showIcon: true,
            selectedMulti: false,
            dblClickExpand: false,
            addHoverDom: this.addHoverDom,
            removeHoverDom: this.removeHoverDom,
            addDiyDom: this.addDiyDom,
            //展开折叠动画
            expandSpeed: "normal",
            nameIsHTML: true,
          },
          check: {
            enable: true,
            nocheckInherit: false,
            chkboxType: {"Y": "p", "N": "s"}
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "menuId",
              pIdKey: "parentId",
              rootPId: 0,
            }
          },
          callback: {
             beforeClick: this.BeforeClick,
            onClick: this.OnClickzTree,
            // onCheck: this.zTreeOnCheck,
            //补获展开和折叠
            // onExpand: this.zTreeBeforeExpand,
            beforeExpand: this.beforeExpand,
          },
          async: {
            enable: true, // 开启异步加载
            url: "/dev-api/bookmark/menu/listByMenuIdP", //对应的后台请求路径
            dataType: "json",
            type: "post",
            dataFilter: this.ajaxDataFilter,
            autoParam: ["menuId=menuId"], // 异步加载时需要自动提交父节点属性的参数
            headers:{
              'Authorization' : 'Bearer ' + getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
            }
          },
          key: {
            isParent: "parent"
          }
        },
        zNodes: [],
        //当前ztree 点击选中的 treeNode 值
        treeNodeClick:undefined,
        bookmark: [],
        inputVisible: false, //标签
        inputValue: '', //标签
        tagcount: 0, //标签虚拟ID
        sqTags: [],
        bookmarkstatus: [{
          "name": "公开",
          "value": 1
        }, {
          "name": "私密",
          "value": 2
        }],
        bookmarktype: [{
          "name": "已阅读",
          "value": 1
        }, {
          "name": "稍后读",
          "value": 2
        }, {
          "name": "待续读",
          "value": 3
        }],
        asideHeight: {
          height: "",
          width: "",
        },

      }
    },
    mounted() {
      window['editBookmark'] = (e,b,openType) => {
        this.editBookmark(e,b,openType)
      },
      window['deleteMmenu'] = (e) => {
        this.deleteMmenu(e)
      },
      window['openPopup'] = (e) => {
        this.openPopup(e)
      },
        window['closePopup'] = (e) => {
          this.closePopup(e)
        },
        window['removeHoverDoms'] = (e) => {
          this.removeHoverDoms(e)
        },
        //div拖动
        this.dragControllerDivs();
      /**背景彩带**/
      // this.backgroundcss();

      /**ebabyou 背景特效**/
      // evanyoucss();

      /**粒子球特效**/
      // this.backgroundparticle();
    },
    created() {
      var that = this;
      //书签菜单
      that.getList();
      window.addEventListener('resize', this.getHeight);
      this.getHeight()
    },

    methods: {
      /**添加书签目录**/
      addBkMenuCk(){
        this.addBkMenu = this.addBkMenu?false:true;
        this.searchBkMenu = true;
        this.menuListShow = true;
      },
      /**搜索书签目录**/
      searchBkMenuCk(){
        this.searchBkMenu = this.searchBkMenu?false:true;
        this.addBkMenu = true;
        this.menuListShow = true;
      },
      /**搜索书签目录**/
      menuListShowCk(){
        this.menuListShow = this.menuListShow?false:true;
        this.addBkMenu = true;
        this.searchBkMenu = true;
      },




      /**图片失败显示**/
      errorHandler() {
        return true
      },
      removeHoverDoms:function(e){
        if (e.getAttribute("data-treetId") != null ) {
          var tid = e.getAttribute("data-treetId");
          var bookmarkCount = e.getAttribute("data-bookmarkCount");
          console.log("tid："+tid+" bookmarkCount:"+bookmarkCount)
           $("." + tid + "_count").unbind().remove();
           $("." + tid + "_sz").unbind().remove();
           var switchObjspan = $("#" + tid + "_span");
           var editStr = "<span class=" + tid + "_count  style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:0.8rem' onfocus='this.blur();'>" + bookmarkCount + "</span>";
           switchObjspan.after(editStr);
        }

      },


      /**div拖拽宽度**/
      dragControllerDivs: function () {
        console.log("开始拖拽")
        var resize = document.getElementsByClassName('isresize');
        var left = document.getElementsByClassName('main-right');
        // var mid = document.getElementsByClassName('el-container mid is-vertical');
       var mid = document.getElementsByClassName('mid');
        var box = document.getElementsByClassName('box');
        var transition = document.getElementsByClassName('transition-box');

        for (let i = 0; i < resize.length; i++) {
          // 鼠标按下事件
          resize[i].onmousedown = function (e) {
            //颜色改变提醒
            resize[i].style.background = 'transparent';
            var startX = e.clientX;
            resize[i].left = resize[i].offsetLeft;
            // 鼠标拖动事件
            document.onmousemove = function (e) {
              var endX = e.clientX;
              var moveLen = resize[i].left + (endX - startX); // （endx-startx）=移动的距离。resize[i].left+移动的距离=左边区域最后的宽度
              var maxT = box[i].clientWidth - resize[i].offsetWidth; // 容器宽度 - 左边区域的宽度 = 右边区域的宽度

              if (moveLen < 32) moveLen = 32; // 左边区域的最小宽度为32px
              if (moveLen > maxT - 150) moveLen = maxT - 150; //右边区域最小宽度为150px

              resize[i].style.left = moveLen; // 设置左侧区域的宽度

              for (let j = 0; j < left.length; j++) {
                left[j].style.width = moveLen + 'px';
                // this.asideHeight.width = moveLen + 'px';
                mid[j].style.width = (box[i].clientWidth - moveLen - 10) + 'px';
              }
            };
            // 鼠标松开事件
            document.onmouseup = function (evt) {
              //颜色恢复
              resize[i].style.background = 'transparent';
              document.onmousemove = null;
              document.onmouseup = null;
              resize[i].releaseCapture && resize[i].releaseCapture(); //当你不在需要继续获得鼠标消息就要应该调用ReleaseCapture()释放掉
            };
            resize[i].setCapture && resize[i].setCapture(); //该函数在属于当前线程的指定窗口里设置鼠标捕获
            return false;
          };
        }
      },



      /**自动获取高度**/
      getHeight() {
        // if (window.innerHeight<=750) {
        //    this.asideHeight.height=='750px';
        //   return;
        // }
        this.asideHeight.height = window.innerHeight + 'px';

      },


      /**书签编辑设置的 标签开始**/
      taghandleClose(tag) {
//1. 首先我们要得到这个对象
        var tina = this.sqTags.filter((p) => {
          return p.tagId == tag;
        });
//2. 其次得到这个对象在数组中对应的索引
        var index = this.sqTags.indexOf(tina[0]);
//3. 如果存在则将其删除，index > -1 代表存在
        index > -1 && this.sqTags.splice(index, 1);

        console.log(this.sqTags);
      },
      showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },

      handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.tagcount = this.tagcount - 1;
          var updatetag = {name: inputValue, bookmarkId: "bookmarkId", tagId: this.tagcount};
          this.sqTags.push(updatetag);
        }
        this.inputVisible = false;
        this.inputValue = '';
        console.log(this.sqTags);
      },


      /**书签编辑设置的 标签结束**/



      /** 提交按钮 修改和新增 */
      addbookmark: function () {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.form.sqTags = this.sqTags;
            addBookmark(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.addopen = false;
                this.getList();
              }
            });
          }

        });
      },
      // 表单重置
      bookmarkreset() {
        this.form = {
          bookmarkId: undefined,
          userid: undefined,
          title: undefined,
          url: undefined,
          urls: undefined,
          description: undefined,
          image: undefined,
          label: undefined,
          menuId: undefined,
          zcount: undefined,
          idelete: undefined,
          start: undefined,
          createTime: undefined
        };
        this.resetForm("form");
      },
      // 取消按钮
      bookmarkcancel() {
        this.addopen = false;
        this.bookmarkreset();
      },


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
        // if (this.zNodes != null && this.zNodes.length != 0) {
        //   this.menuOptions = [];
        //   const data = {menuId: 0, menuName: '顶级菜单', children: []};
        //   data.children = this.handleTree(this.zNodes, "menuId", "parentId");
        //   this.menuOptions.push(data);
        //   return;
        // }

        listMenuByUserId().then(response => {
          this.menuOptions = [];
          const data = {menuId: 0, menuName: '顶级菜单', children: []};
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
        // this.queryParams.menuId = 0;
        listByMenuId(0).then(response => {
          this.zNodes = response.data;
          //判断是否有子节点--通过isContainSon是否大于0（也就是有终端）,添加父节点为true
          this.zNodes.forEach((item)=>{
            if(item.subordinate == 1){
              item.isParent = true;
            }
          })
          //加载Ztree树
          // $.fn.zTree.init($("#treeDemo"), this.setting, this.zNodes).expandAll(this.expandAll);
          $.fn.zTree.init($("#treeDemo"), this.setting, this.zNodes);
        });

      },
      /** 提交按钮 */
      submitForm: function () {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.menuId != undefined) {
              if (this.form.menuId == this.form.parentId) {
                this.msgError("不能将上级菜单设置为本身");
                return;
              }
              console.log("this.form.parentId"+this.form.parentId)
              if (this.form.parentId!=0&&(this.form.parentId == null||this.form.parentId == ''||'undefined'==this.form.parentId)) {
                this.msgError("请选择上级菜单");
                return;
              }

              updateMenu(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  // this.getList();
                  //https://www.cnblogs.com/remember-forget/p/8461212.html
                  //刷新
                  // this.refreshParentNode();
                }
              });
            } else {
              console.log("this.form="+this.form)
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
      //刷新子节点
      refreshNode(){
        /*根据 treeId 获取 zTree 对象*/
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
          type = "refresh",
          silent = false,
          /*获取 zTree 当前被选中的节点数据集合*/
          nodes = zTree.getSelectedNodes();
          if (nodes!=null&&nodes.length!=0){
        /*强行异步加载父节点的子节点。[setting.async.enable = true 时有效]*/
        zTree.reAsyncChildNodes(nodes[0], type, silent);
          }else{
            this.msgSuccess("请选择需要刷新的目录");
          }
      },
      //刷新父节点
      refreshParentNode() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
          type = "refresh",
          silent = false,
          nodes = zTree.getSelectedNodes();
        /*根据 zTree 的唯一标识 tId 快速获取节点 JSON 数据对象*/
        var parentNode = zTree.getNodeByTId(nodes[0].parentTId);
        /*选中指定节点*/
        zTree.selectNode(parentNode);
        zTree.reAsyncChildNodes(parentNode, type, silent);
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
        this.sqTags = [],
          this.sousuo = '',
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
      beforeExpand: function(treeId, treeNode){
        console.log("1beforeExpand：treeNode.tId="+treeNode.tId)
        // var id=treeNode.tId + "_ico";
        // console.log("id:"+id+"document.getElementById(id):"+document.getElementById(id))
        // //异步加载动画
        // var classVal = document.getElementById(id).getAttribute("class");
        // //添加
        // classVal = classVal.concat(" sayncIoc");
        // document.getElementById(id).setAttribute("class",classVal );
        // console.log("document.getElementById(id).getAttribute(\"class\")="+document.getElementById(id).getAttribute("class"))
        // //删除
        //  classVal = classVal.replace("sayncIoc","");
        // document.getElementById(id).setAttribute("class",classVal );

      },
      /** 异步获取节点 处理数据*/
      ajaxDataFilter: function (treeId, parentNode, responseData){
        console.log("2ajaxDataFilter")
        var ajaxData = responseData.data;
        if (responseData&&ajaxData.length!=0) {
          for(var i =0; i < ajaxData.length; i++) {
            if(ajaxData[i].subordinate == 1){
              ajaxData[i].isParent = true;
            }
          }
        }
        return ajaxData;
      },
      addDiyDom: function (treeId, treeNode) {
        // console.log("自定义ztree:"+treeId.tId+"___treeNode："+treeNode.tId)
        var spaceWidth = 20;
        var switchObj = $("#" + treeNode.tId + "_switch"),
          icoObj = $("#" + treeNode.tId + "_ico");
        switchObj.remove();
        icoObj.before(switchObj);

        if (treeNode.level > -1) {
          var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level) + "px'></span>";
          switchObj.before(spaceStr);
          var switchObjspan = $("#" + treeNode.tId + "_span");


          // console.log("进入addHoverDom:统计"+treeNode.bookmarkCount+"_sz 的数量:"+confCount);
          // console.log("addDiyDom:统计2"+$.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes()[0].bookmarkCount);
          //console.log("addDiyDom:统计2"+$.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes()[0]);


          var editStr = "<span class=" + treeNode.tId + "_count style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:0.8rem' onfocus='this.blur();'>" + treeNode.bookmarkCount + "</span>";
          switchObjspan.after(editStr);
        }
      },

      addHoverDom: function (treeId, treeNode) {
        // console.log("addHoverDom...........")
        if (this.treeNodeClick !=null && this.treeNodeClick !=undefined){
          //解决节点选中后 移出鼠标无法触发 removeHoverDom 方法的问题
          // this.removeHoverDom(null,this.treeNodeClick);
        }
        var confCount = $("." + treeNode.tId + "_sz").length;
        if (confCount > 0) return;
        $("." + treeNode.tId + "_sz").unbind().remove();
        $("." + treeNode.tId + "_count").unbind().remove();
        //if (treeNode.parentNode && treeNode.parentNode.id!=1) return;
        var switchObjspan = $("#" + treeNode.tId + "_span");
        // onmouseout='removeHoverDoms(this)' //选中删除切换
        // var editStr = "<span  class=" + treeNode.tId + "_sz data-parentId=" + treeNode.parentId + " data-menuId=" + treeNode.menuId + " data-treetId="+ treeNode.tId +" data-bookmarkCount="+treeNode.bookmarkCount+" onclick='editBookmark(this)' style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:0.8rem' onfocus='this.blur();'><i style='margin-top: 9px;font-size: 16px' class='el-icon-edit'></i></span>";

        var editStr = "<span  class=" + treeNode.tId + "_sz data-parentId=" + treeNode.parentId + " data-menuId=" + treeNode.menuId + " data-treetId="+ treeNode.tId +" data-bookmarkCount="+treeNode.bookmarkCount+" onclick='openPopup(this)' style='z-index:888;color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:0.8rem' onfocus='this.blur();'><i style='margin-top: 9px;font-size: 16px' class='el-icon-more'></i></span>";



        //var  editStr = "<el-popover placement='bottom-start' width='200' trigger='click' ><ul class='item-menu'><li class='item-menu-title '><span>新增嵌套目录</span></li><el-divider></el-divider><li class='item-menu-title '><span>修改名称</span></li><li class='item-menu-title '><span>修改图标</span></li><li class='item-menu-title '><span>分享</span></li></ul><el-button style='float: right' slot='reference'>click 激活</el-button></el-popover>"
        switchObjspan.after(editStr);
      },

      removeHoverDom: function (treeId, treeNode) {
        // console.log("removeHoverDom...........")

        $("." + treeNode.tId + "_count").unbind().remove();
        $("." + treeNode.tId + "_sz").unbind().remove();
        var switchObjspan = $("#" + treeNode.tId + "_span");
        var editStr = "<span class=" + treeNode.tId + "_count  style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:0.8rem' onfocus='this.blur();'>" + treeNode.bookmarkCount + "</span>";
        switchObjspan.after(editStr);

      },
      //点击展开
      BeforeClick: function (treeId, treeNode) {
        console.log("展开---")
        this.treeNodeClick = treeNode;
        // removeHoverDom (treeId, treeNode);
        //if (treeNode.level != 19990 ) {
        //    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        //    zTree.expandNode(treeNode);

        //  return false;
        // }
        //return true;
      },
      //点击节点 然后异步加载 伪异步加载树
      zTreeBeforeExpand: function (event, treeId, treeNode) {
        console.log("捕获展开---异步加载树")
        console.log("1.开始准备发送请求!")
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var parentZNode = treeObj.getNodeByParam("menuId", treeNode.menuId, null);//获取指定父节点
        const childNodes = treeObj.transformToArray(treeNode);//获取子节点集合
        //点击事件后将子节点清空后在进行拼接
        //删除指定节点
        console.log("childNodes"+JSON.stringify(childNodes))
        for (var i = 0; i < childNodes.length; i++) {
          console.log(treeNode+"  "+childNodes[i])
          if(childNodes[i].menuId!=treeNode.menuId){
            treeObj.removeNode(childNodes[i]);
          }
        }
        //因为子节点还包括组织，所以这里需要筛选一下
        // if(treeNode.isParent){
        console.log("2.开始准备发送请求!")
        listByMenuId(treeNode.menuId).then(response => {
          if (response.data.length!=0) {
            //点击事件后将子节点清空后在进行拼接
            treeNode.children = [];
            console.log("当前节点的treeNode.children："+treeNode.children)
            const childrenData=eval(response.data)
            console.log("3.获取数据"+JSON.stringify(childrenData))
            //判断子节点是否包含子元素
            for(var i in childrenData){
              if(childrenData[i].subordinate == 1){
                childrenData[i].isParent = true;
              }
            };
            // treeObj.refresh();
            treeObj.addNodes(parentZNode,childrenData, false);    //添加节点
          }
        });
      },
      /** 点击跳转**/
      //节点点击
      OnClickzTree: function (event, treeId, treeNode) {
        var that = this;
        var property=that.$store.state.property;
        if (property==null||property==''||property==undefined){
          property=0;
        }
        //如果当前的节点已经展开了 收缩的时候就不请求了
        // if (!treeNode.open){
        that.$router.push({
          path: "/content",
          query: {
            menuId: treeNode.menuId,
            property:property,
            t:Date.now(),
          }
        })
        // }

        $("." + treeNode.tId + "_sz").unbind().remove();
        var switchObjspan = $("#" + treeNode.tId + "_span");
        var editStr = "<span class=" + treeNode.tId + "_count  style='color: #9e9e9e;float:right;display: inline-block;margin-right: 15px;font-size:0.8rem' onfocus='this.blur();'>" + treeNode.bookmarkCount + "</span>";
        switchObjspan.after(editStr);
        //展开当前菜单
        // var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        // zTree.expandNode(treeNode);

        // console.log("当前节点已经展开:"+treeNode.open)

      },
      //显示隐藏 ztree菜单
      zreaZtree: function () {
        var that = this;
        that.isShowZtree = !that.isShowZtree;

      },
      goRouter: function (e) {
        var flag=e;
        var that = this;
        switch (flag) {
          case 1:
            that.$router.push({
              path: "/profile",
            })
            break;
          case 2:
            //工具箱
            that.$router.push({
              path: "/tool",
            })
            break;
          case 3:
            // 跳转NqQuillEdit测试页面
            that.$router.push({
              path: "/nqEdit",
            })
            break;
          case 4:
            //跳转测试页面
            that.$router.push({
              path: "/ceshi",
            })
            break;
          case 5:
            //导入页面
            that.$router.push({
              path: "/importHtml",
            })
            break;
          case 6:
            //回收站
            that.$router.push({
              path: "/content",
              query: {
                menuId: 'recycle',
                t:Date.now()
              }
            })
            break;
          case 7:
            //用户标签
            that.$router.push({
              path: "/userTagAll",
            })
            break;
          case 8:
            //全部最新书签
            that.$router.push({
              path: "/content",
              query: {
                menuId: 'newest',
                t:Date.now(),
              }
            })
            break;
          case 9:
            //星标
            that.$router.push({
              path: "/content",
              query: {
                menuId: 'asterisk',
                t:Date.now(),
              }
            })
            break;
          case 10:
            //稍后看
            that.$router.push({
              path: "/content",
              query: {
                menuId: 'seeYouLater',
                t:Date.now(),
              }
            })
            break;
          case 11:
            //用户中心
            that.$router.push({
              path: "/bkindex",
            })
            break;
          default:
            that.$router.push({
              path: "/content",
              query: {
                menuId: 'newest',
                t:Date.now(),
              }
            })
        }
      },
      editBookmark: function (menuId,parentId,openType) {
        //关闭弹窗
        this.closePopup();
        this.reset();
        if (menuId != null && parentId != null) {
          this.form.parentId = parentId;
        }
        //当前操作类型
        this.openType = openType;

        //新增的时候不需要menuId
        if (this.openType == 4){
          var menu = {
            "parentId":menuId,
            "menuIcon":"https://z3.ax1x.com/2021/05/23/gOcI2j.png"
          };
          this.form = menu;
          this.title = "新增嵌套目录";
          this.open = true;
          return;
        }
        getMenu(menuId).then(response => {
          this.form = response.data;
          if (this.openType == 1){
            this.title = "新名称";
          } else if (this.openType == 2){
            this.title = "新图标";
          } else if (this.openType == 3){
            this.title = "移动目录";
            //菜单树
            this.getTreeselect();
          }
          //请求完在打开
          this.open = true;
        });

        // //阻止冒泡事件
        // if (e && e.stopPropagation)
        // //因此它支持W3C的stopPropagation()方法
        //   e.stopPropagation();
        // else
        // //否则，我们需要使用IE的方式来取消事件冒泡
        //   window.event.cancelBubble = true;
        // return false;
      },

      //删除书签目录
      deleteMmenu(menuId) {
        //关闭弹窗
        this.closePopup();
        this.$confirm('是否删除此目录菜单?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          delMenu(menuId).then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.open = false;
            // this.refreshParentNode();
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      openPopup: function(e) {
        console.log("openPopup................")

        console.log("当前菜单的目录数据............")

        console.log("[data-menuId]............" + e.getAttribute("data-menuId"))
        console.log("[data-parentId]............" + e.getAttribute("data-parentId"))
        var menuId = e.getAttribute("data-menuId");
        var parentId = e.getAttribute("data-parentId");



        var posX = 0, posY = 0;
        var event = event || window.event;
        event.stopPropagation();
        if (event.pageX || event.pageY) {
          posX = event.pageX;
          posY = event.pageY;
        } else if (event.clientX || event.clientY) {
          posX = event.clientX + document.documentElement.scrollLeft + document.body.scrollLeft;
          posY = event.clientY + document.documentElement.scrollTop + document.body.scrollTop;
        }

        this.creatDiv(menuId,parentId);


        //判断浏览器是否Chrome
        var isChrome = window.navigator.userAgent.indexOf("Chrome") !== -1;

        if (isChrome) {
          document.getElementById("popupDiv").style.left = posX + "px";
          document.getElementById("popupDiv").style.top = posY + "px";
        } else {
          //不是Chrome，默认为IE
          document.getElementById("popupDiv").style.pixelLeft = posX;
          document.getElementById("popupDiv").style.pixelTop = posY;
        }
      },
      //创建弹窗DIV
      creatDiv: function(menuIdval,parentIdval) {

        var s = document.createElement("div");
        //创建前 删除之前的dom生成的代码
        if (document.getElementById("popupDiv") && document.getElementById("popupDiv").parentNode != null) {
          document.getElementById("popupDiv").parentNode.removeChild(document.getElementById("popupDiv"));
        }
        s.innerHTML = "<div id = 'popupDiv' style=\"width:200px\">" +
          "<ul class=\"item-menu\">" +
          "<li class=\"item-menu-title\" style='margin-top: -8px;' onclick='editBookmark(" + menuIdval + "," + parentIdval + ",4)'><span>新增嵌套目录</span></li>" +
          "<hr size=\"1px\" noshade=true >" +
          "<li class=\"item-menu-title\"  onclick='editBookmark(" + menuIdval + "," + parentIdval + ",3)'><span>移动目录</span></li>" +
          "<hr size=\"1px\" noshade=true>" +
          "<li class=\"item-menu-title\" onclick='editBookmark(" + menuIdval + "," + parentIdval + ",1)'><span>改名称</span></li>" +
          "<li class=\"item-menu-title\" onclick='editBookmark(" + menuIdval + "," + parentIdval + ",2)'><span>改图标</span></li>" +
          "<li class=\"item-menu-title\" ><span>加密</span></li>" +
          "<li class=\"item-menu-title\" ><span>分享</span></li>" +
          "<li class=\"item-menu-title\" style='margin-bottom: -8px;' onclick='deleteMmenu(" + menuIdval + ")'><span>刪除</span></li>" +
          "</ul>" +
          "</div>";

        document.getElementsByTagName("body")[0].appendChild(s);
      },
      //提交数据
      doSubmit: function() {
        //dosomething
        document.getElementById("popupDiv").style.display = 'none';
      },
      //关闭弹窗
      closePopup: function() {
        // document.getElementById("popupDiv").style.display = 'none';
        if(document.getElementById("popupDiv") && document.getElementById("popupDiv").parentNode!=null){
        document.getElementById("popupDiv").parentNode.removeChild(document.getElementById("popupDiv"));
        }
      }

    },

    destroyed() {
      window.removeEventListener('resize', this.getHeight)
    },
    handleCommand(command) {
      this.$message('click on item ' + command);
    }
  }
</script>
<style scoped>

  body {
    /*font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;*/
    font-family: "Merriweather", "Open Sans", "Microsoft Jhenghei", "Microsoft Yahei", sans-serif;
  }



  .ztr {
    /*//background{ width:100%;height:100%;position:absolute;top:0px;opacity: 0.6;background-image: url(https://s1.ax1x.com/2020/07/27/akFjER.jpg);background-size: 500px 100px;}*/
    /*background:red;*/
  }

  /*.areaTree{*/

  /*  height: 400px;*/
  /*  overflow:scroll;*/
  /*  font-weight: 400;*/
  /*overflow:auto;*/
  /*  background-color: #F6F6F6;*/
  /*  background:url('https://ftp.bmp.ovh/imgs/2020/08/4ac1d6b4f41049ef.jpg') no-repeat;*/
  /*  background-size: 100% 100%;*/

  /*}*/

  #app {
    overflow-y: hidden;

  }

  .isaside {
    padding: 0;
    margin-bottom: 0;
    /*background:url('https://ftp.bmp.ovh/imgs/2020/08/4ac1d6b4f41049ef.jpg') no-repeat;*/
    background-color: #F6F5F4;
    /*background: url("https://s1.ax1x.com/2020/08/16/dEcqVU.jpg") no-repeat;*/


    /*background: url("https://s1.ax1x.com/2020/10/03/03WM4K.md.jpg") no-repeat;*/
    /*background: url("https://s1.ax1x.com/2020/10/03/03fri6.png") no-repeat;*/


    /*background: url("https://s1.ax1x.com/2020/10/03/03RCSe.jpg") no-repeat;*/
    /*background-color: #2E90AE;*/
    /*background: -webkit-linear-gradient(to right, #000C40, #F0F2F0);  !* Chrome 10-25, Safari 5.1-6 *!*/
    /*background: linear-gradient(to right, #000C40, #F0F2F0); !* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ *!*/

    /*opacity:0.9;*/
    /*filter:alpha(opacity=90);*/


    filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";
    -moz-background-size: 100% 100%;
    background-size: 100% 100%;


    /*background: url("https://s1.ax1x.com/2020/10/03/03sAeK.md.jpg") no-repeat, linear-gradient(90deg, rgb(107, 165, 255) 40%, rgb(197, 197, 197) 100%) ;*/
    /*background-repeat: no-repeat;*/
    /*filter:"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale')";*/
    /*-moz-background-size:100% 100%;*/
    /*background-size:400px 100%;*/

    /*background-color: #2E90AE;*/
    /*background: -webkit-linear-gradient(to right, #000C40, #F0F2F0);  !* Chrome 10-25, Safari 5.1-6 *!*/
    /*!*background: linear-gradient(to right, #000C40, #F0F2F0); !* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ *!*!*/


    /*background-size: cover;*/
    /*z-index: -1;!*-1 可以当背景*!*/
    /*-webkit-filter: blur(3px);*/
    /*filter: blur(3px);*/
    box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);

  }
  /*光标拖拽*/
  /*.isaside{*/
  /*  cursor:w-resize*/
  /*}*/
  .aside-titleB{
    display: flex;
    height: 32px;
    align-items: center;
    justify-items: center;
  }
  .aside-titleB:hover {
    background-color: #e8e8e8;
  }
/**第一个元素**/
  .aside-titleB_childi_one{
    margin-left: 5px;
    font-size: 15px;
    margin-right: 11px;
    vertical-align: middle;
  }
  /**第二个元素**/
  .aside-titleB_childi_two{
    margin-left: -4px;
    font-size: 20px;
    margin-right: 11px;
    vertical-align: middle;
  }
  .aside-titleB span{
    font-size: 14px;
  }


  .aside-title {
    height: 32px;
  }

  .aside-title:hover {
    background-color: #e8e8e8;
  }

  .aside-title i {
    margin-left: 26px;
    font-size: 20px;
    margin-right: 11px;
    vertical-align: middle;
  }
  .svgicon{
    margin-left: 26px;
    font-size: 18px;
    margin-right: 11px;
    vertical-align: middle;
  }

  .aside-title span {
    font-size: 14px;
    margin-right: 11px;
  }

  .aside-logo {
    display: flex;
    align-items: center;
  }

  .aside-logo div {
    width: 50%;
  }

  .logoname span {
    margin-left: 8px;
    font-family: "PingFang SC";
    font-size: 120%;
    font-weight: 800;

  }

  .logoright i {
    float: right;
    display: block;
    width: 30px;
    height: 28px;
    background-color: #E6E4E1;
    color: #5C5C5C;
    text-align: center;
    line-height: 28px;
    border-radius: 2px;
  }




  .reminder {
    color: #878787;
    opacity: 0.7;
    font-size: 14px !important;
    font-weight: 500 !important;
    margin-left: 27px ;

  }

  .aside-navigation {
    width: 100%;
    height: 30px;
    background-color: #a0c4ff;

  }

  .filter {
    display: flex;
    flex-wrap: wrap;
  }

  .filter-sort {
    color: #7e868d;
    box-shadow: inset 0 0 0 1px rgba(0, 0, 0, .08), inset 0 -1px 0 rgba(0, 0, 0, .04);
    margin-right: 4px;
    border-radius: 2px;
    height: 22px;
    font-size: 12px;
    line-height: 22px;
    text-align: center;
    float: left;
    padding: 0 8px;

  }

  .filter-sort:hover {
    color: #6f8eee;
    background-color: #e6e6e6;

  }


  .bookmarkmain {
    padding: 0px !important;
  }

  .separator {
    float: left;
  }
  .menuCount{
    float: right;
    margin-right:18px!important;
    color: #9e9e9e
  }

  .filter-sort i {
    margin-left: -4px;
    margin-right: -1px;
  }

  .filter div {
    margin-bottom: 3px;
  }


  .main-label span i {
    margin-left: 5px;
  }

  .main-label span {
    line-height: 22px;
  }

  .main-label :nth-child(1) {
    margin-right: -1px;
  }

  .main-label :nth-child(2) {
    margin-right: 5px;
  }

  .labelname {
    font-weight: 800;
    color: rgb(0, 0, 0);
    float: left;
    margin-bottom: -10px;
    margin-right: 5px;
  }

  .el-form-item__content {
    margin-left: 0px !important;
  }

  .el-dialog__body {
    padding-top: 0px !important;
  }

  .custom-input input {
    border: 0px;
    border-radius: 0px;
    border-bottom: 1px solid #C0C4CC;
  }

  .custom-input input:focus {
    border: 0px;
    border-radius: 0px;
    border-bottom: 1px solid #695fff;
  }

  /*添加bookmarkurl*/
  .addbookmarkurl input {
    border: 0px;
    border-radius: 0px;
    border-bottom: 1px solid #409EFF;
  }

  .addbookmarkurl input:focus {
    border-bottom: 1px solid #409EFF;
  }

  .addbookmarkurl input:hover {

    border-bottom: 1px solid #409EFF;
  }


  .addbookmarkurl .el-dialog {
    top: 40%;
  }


  .addbookmarkurl .el-dialog__body {
    padding-right: 10px;
    height: 60px !important;
  }

  .addbookmarkurl-input {
    width: 82% !important;
    float: left;
    font-size: 13px;
  }

  .addbookmarkurl-button {
    float: left;
    margin-left: 5px;
    border: 1px solid #409EFF !important;
  }

  .addbookmarkurl-button:hover {
    background-color: #409EFF !important;
    color: #FFFFFF !important;
  }

  .el-dialog {
    border-radius: 10px !important;
  }

  /*编辑目录 选择目录菜单样式调整*/
  .vue-treeselect__control {
    padding-top: 6px !important;
  }

  .isaside {
    box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);
  }



  .main-right {
    width: 300px;
    height: 95%;
    min-height: 40%;
    overflow: auto;
    z-index: 1;
  }

  .main-right::-webkit-scrollbar {
    /*滚动条整体样式*/
    width: 0px;
    /*高宽分别对应横竖滚动条的尺寸*/
    height: 12px !important;

  }

  /*定义滑块 样式*/
  .main-right::-webkit-scrollbar-thumb {
    border-radius: 3px;
    /*height: 10px!important;    !* 滚动条滑块长度 *!*/
    background-color: #6ba5ff;


  }

  /*定义滚动条轨道 内阴影+圆角 透明效果*/
  ::-webkit-scrollbar-track {
    background-color: transparent;

  }


  .tabBarList{
    display: flex;
    float: left;
    width: 100%;
    height: 50px;
    background-color: #e6e6e6;
    text-align: center;
    align-content: center;
    align-items: center;
  }
  .tabBarList div{
    display: flex;
    width: 33.33%;
    height: 50px;
    align-content: center;
    align-items: center;
    text-align: center;
  }

  .tabBarList div i{
    margin: 0 auto;
    font-size: 25px;

  }

  .popover-suosou {
    /*width: 85% ;*/
    top: 40px;
    /*left: 72px;*/
  }

  /* 拖拽相关样式 */
  /*包围div样式*/

  /*拖拽区div样式*/
  .isresize {
     width: 5px;
    display: -webkit-flex;
    justify-content: center;
    align-items: center;
    /*background-color: red;*/
    color: red;
    z-index: 1;
  }

  /*拖拽区鼠标悬停样式*/
  /*.isresize:hover {*/
  /*  color: #a4a4a4;*/
  /*}*/

  /*.isresize i {*/
  /*  margin-left: 2px;*/
  /*  transform: rotate(90deg);*/
  /*  width: 10px;*/
  /*  !*color: transparent;*!*/
  /*  !*background-color: black;*!*/
  /*}*/

  .main-right {
    min-width: 250px;
  }

  /*不可复制*/
  .isaside {
    -moz-user-select: none; /* Firefox私有属性 */
    -webkit-user-select: none; /* WebKit内核私有属性 */
    -ms-user-select: none; /* IE私有属性(IE10及以后) */
    -khtml-user-select: none; /* KHTML内核私有属性 */
    -o-user-select: none; /* Opera私有属性 */
    user-select: none; /* CSS3属性 */
  }

  /* 拖拽相关样式 结束 */
  #evanyou {
    position: fixed;
    width: 100%;
    height: 100%;
    left: 0;
    top: 0;
    z-index: -1;
  }

  .butWidth{
    width: 100%;
    margin-top: 10px;
  }


</style>
<style>

  #popupDiv {
    position: absolute;
    z-index: 1;
    background-color: #ffffff;
    border:1px solid #dedede;
    border-radius: 3px;
  }



  /** 菜单栏的弹窗*/
  .item-menu{

  }
  .item-menu-title{
    margin-left:-40px;
    list-style:none;
    height: 28px;
    line-height: 28px;
    font-size: 14px;
    color: #000;
  }
  .item-menu-title:hover{
    background-color: #1987e1;
    color:#FFFFFF;
  }
  .item-menu-title span{
    margin-left:15px;
  }
  .item-menu hr{
    margin-left:-40px;
    border-color:#dedede;

  }





  /** 菜单栏的弹窗*/

</style>

