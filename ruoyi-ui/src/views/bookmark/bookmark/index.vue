<template>
  <div @click="closePopup()">

    <el-container class="isbookmarkContainer">
      <el-aside class="isBookmarkAside" :style="asideHeight">
      <el-header class="header-sousou" >
        <div class="sousou-left">
          <div class="sousouleft-switch" @click="drawer = true"><i class="el-icon-s-unfold"/></div>
          <div class="sousou-leftico" @click="drawer = true"><img
            src="https://favicon.lucq.fun/?url=https://www.baidu.com"/></div>
        </div>
        <div class="sousou-input" >
          <el-input slot="reference" style="width: 300px;" @keyup.enter.native="gosousuo" placeholder="请输入要查找的关键词" v-model="sousuo"
                    size="small" >
            <i slot="prefix" class="el-input__icon el-icon-search"></i>
          </el-input>
        </div>

        <div class="sousouright-icon">
          <inform></inform>

        </div>

        <div class="sousouright-iconadd">
          <el-dropdown trigger="click" size="small" :hide-on-click="true" >
              <span class="el-dropdown-link">
<!--          <i class="el-icon-plus" style="font-size: 18px;"/>-->
                <el-button icon="el-icon-plus" style="border:0px;font-size: 18px;" size="mini"></el-button>
             </span>
            <el-dropdown-menu slot="dropdown" class="sq-dropdown">
              <el-dropdown-item class="filter-item" icon="el-icon-plus" @click.native="addbookmarkurl">添加连接
              </el-dropdown-item>
              <el-dropdown-item class="filter-item" icon="el-icon-plus" @click.native="addbooNote" >添加文本</el-dropdown-item>
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="d">导入书签</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <div class="header-list">
          <el-dropdown trigger="click" size="small" :hide-on-click="false">
              <span class="el-dropdown-link">

           <el-avatar style="margin-top: 5px" :size="28"
                      src="https://up.raindrop.io/collection/templates/social-media-logos-6/97social.png"></el-avatar>
              </span>
            <el-dropdown-menu slot="dropdown" class="sq-dropdown">
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="a">外观显示</el-dropdown-item>
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="b">用户中心</el-dropdown-item>
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="d">建议反馈</el-dropdown-item>
              <el-dropdown-item class="filter-item" icon="el-icon-plus" command="e">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>


          <div class="filter-tbar">
            <div class="filter-classification">
              <div  :class="['classification',property=='0'?' classification-click':'']" @click="showopen(0)"><span>全部</span></div>
              <div  :class="['classification',property=='4'?' classification-click':'']" @click="showopen(4)"><span>书签</span></div>
              <div :class="['classification',property=='1'?' classification-click':'']" @click="showopen(1)"><span>文本</span></div>
              <div :class="['classification',property=='2'?' classification-click':'']" @click="showopen(2)"><span>图片</span></div>
<!--              <div :class="['classification',property=='3'?' classification-click':'']" @click="showopen(3)"><span>其他</span></div>-->
            </div>

            <div class="setUpThe">
              <div class="filter-content">
                <el-dropdown trigger="hover" size="small" @command="handleCommand">
                  <div class="el-dropdown-link dropdownList">
<!--                    <i class="el-icon-document-checked "></i>-->
                    <el-button icon="el-icon-document-checked" style="border:0px;font-size: 18px;" size="mini"></el-button>
                  </div>
                  <el-dropdown-menu slot="dropdown" class="filter-sort-dropdown">
                    <el-dropdown-item class="filter-item" command="0"><i class="el-icon-bottom"></i>按时间排序(正)
                    </el-dropdown-item>
                    <el-dropdown-item class="filter-item" command="1"><i class="el-icon-bottom"></i>按时间排序(反)
                    </el-dropdown-item>
<!--                    <el-dropdown-item class="filter-item" command="2"><i class="el-icon-bottom"></i>按标题A-Z排序(正)-->
<!--                    </el-dropdown-item>-->
<!--                    <el-dropdown-item class="filter-item" command="3"><i class="el-icon-bottom"></i>按标题A-Z排序(反)-->
<!--                    </el-dropdown-item>-->
                  </el-dropdown-menu>
                </el-dropdown>

              </div>
              <div class="filter-content">
                <el-dropdown trigger="hover" size="small" @command="showPatternState">
                  <div class="el-dropdown-link dropdownList">
                    <el-button icon="el-icon-tickets" style="border:0px;font-size: 18px;" size="mini"></el-button>
                  </div>
                  <el-dropdown-menu slot="dropdown" class="filter-sort-dropdown">
                    <el-dropdown-item class="filter-item" command="0"><i class="el-icon-bottom"></i>默认模式</el-dropdown-item>
                    <el-dropdown-item class="filter-item" command="1"><i class="el-icon-bottom"></i>简洁模式</el-dropdown-item>
                    <el-dropdown-item class="filter-item" command="2" ><i class="el-icon-bottom"></i>卡片模式</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>

              </div>


            </div>
          </div>

          <div v-if="queryParams.sousuo!=''" class="sousuoContent">
            <i class="el-icon-success"></i>
            <!--      <span> 当前搜索的内容是:{{queryParams.sousuo}}</span>-->
            <span> "{{sousuo}}"共为您找到约{{total}}个结果</span>
          </div>

          <div class="nullbookmark" v-if="showimg">
            <div class="nullbookmark-img">
            </div>
            <div  class="nullbookmark-text">
              空空如也
            </div>
          </div>


<!--              三窗口展示-->
        <div class="bookmarklist" :style="datalist"  infinite-scroll-distance="10" v-loading="loading" v-if="!showimg"
             v-infinite-scroll="load"
             infinite-scroll-disabled="disabled" style="overflow:auto;" infinite-scroll-immediate="false">
    <BookmarkOne @on-windowurl="windowurl" @on-handleUpdate="handleUpdate" @on-handleDelete="handleDelete" :key="property +'-'+ showPattern" :property="property" :showPattern="showPattern"  :highlighted="highlighted" :sousuo="sousuo"  :listloading="listloading" :loading="loading" :bookmarkList="bookmarkList"></BookmarkOne>

          <p v-if="listloading" class="listhint"><i class="el-icon-loading"></i>加载中...</p>
          <p v-if="listnoMore&&!showimg" class="listhint">没有更多了</p>
        </div>


         <!-- 添加或修改书签管理对话框 -->
          <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">

              <el-form-item label="书签标题" prop="title">
                <el-input v-model="form.title" placeholder="请输入书签标题"/>
              </el-form-item>
              <el-form-item label="书签地址" prop="url">
                <el-input v-model="form.url" placeholder="请输入书签地址"/>
              </el-form-item>
              <el-form-item label="书签描述" prop="description">
                <el-input v-model="form.description" type="textarea" placeholder="请输入书签描述" run dev
                          :autosize="{minRows: 3, maxRows:4}" :style="{width: '100%'}"></el-input>
              </el-form-item>
              <el-form-item prop="menuId">
                <div class="labelname">请选择上级目录</div>
                <treeselect class="menutreeselect" v-model="form.parentId" :options="menuOptions"
                            :normalizer="normalizer"/>
              </el-form-item>

              <el-form-item label="书签标签:" prop="label">
                <el-tag
                  class="bookmarktag"
                  v-for="tag in form.sqTags"
                  :key="tag.tagId"
                  closable
                  type="success"
                  :disable-transitions="false"
                  @close="taghandleClose(tag.tagId)"
                  v-if="tag.name!='TAGDELETE'"
                >
                  {{tag.name}}
                </el-tag>
                <el-input
                  class="input-new-tag"
                  v-if="inputVisible"
                  v-model="inputValue"
                  ref="saveTagInput"
                  size="small"
                  @keyup.enter.native="handleInputConfirm"
                  @blur="handleInputConfirm"
                >
                </el-input>
                <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 新增标签</el-button>

              </el-form-item>

              <!--        0公开显示 1隐藏显示 2好友显示-->
              <el-form-item label="选择状态" prop="start">
                <el-radio-group v-model="form.start" size="medium">
                  <el-radio v-for="(item, index) in bookmarkstatus" :key="index" :label="item.value"
                            :disabled="item.disabled">{{item.name}}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
              <!--           1.未读稍后再看 2 已读 2.續看-->
              <el-form-item label="选择类型" prop="type">
                <el-radio-group v-model="form.type" size="medium">
                  <el-radio v-for="(item, index) in bookmarktype" :key="index" :label="item.value"
                            :disabled="item.disabled">{{item.name}}
                  </el-radio>
                </el-radio-group>
              </el-form-item>


            </el-form>
            <div slot="footer" class="dialog-footer">

              <el-button class="butWidth" size="small" type="primary" @click="submitForm">确 定</el-button>
              <el-button class="butWidth" style="margin-left: 0px"  size="small" @click="cancel">取 消</el-button>

            </div>
          </el-dialog>



        <!-- 添加或修改书签管理对话框 -->
        <el-dialog :title="title"  :visible.sync="addBookMakrUrlOpen" width="600px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" >
            <el-input v-model="form.url" placeholder="请输入书签地址" class="addUrl-input"/>
            <el-input v-model="form.title" placeholder="请输入书签标题" class="addUrl-input"/>
            <el-form-item label="书签标签:" prop="label">
              <el-tag
                class="bookmarktag"
                v-for="tag in form.sqTags"
                :key="tag.tagId"
                closable
                type="success"
                :disable-transitions="false"
                @close="taghandleClose(tag.tagId)"
                v-if="tag.name!='TAGDELETE'"
              >
                {{tag.name}}
              </el-tag>
              <el-input
                class="input-new-tag"
                v-if="inputVisible"
                v-model="inputValue"
                ref="saveTagInput"
                size="small"
                @keyup.enter.native="handleInputConfirm"
                @blur="handleInputConfirm"
              >
              </el-input>
              <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 新增标签</el-button>

            </el-form-item>

            <!--        0公开显示 1隐藏显示 2好友显示-->
<!--            <el-form-item label="选择状态" prop="start">-->
<!--              <el-radio-group v-model="form.start" size="medium">-->
<!--                <el-radio v-for="(item, index) in bookmarkstatus" :key="index" :label="item.value"-->
<!--                          :disabled="item.disabled">{{item.name}}-->
<!--                </el-radio>-->
<!--              </el-radio-group>-->
<!--            </el-form-item>-->
<!--            &lt;!&ndash;           1.未读稍后再看 2 已读 2.續看&ndash;&gt;-->
<!--            <el-form-item label="选择类型" prop="type">-->
<!--              <el-radio-group v-model="form.type" size="medium">-->
<!--                <el-radio v-for="(item, index) in bookmarktype" :key="index" :label="item.value"-->
<!--                          :disabled="item.disabled">{{item.name}}-->
<!--                </el-radio>-->
<!--              </el-radio-group>-->
<!--            </el-form-item>-->


          </el-form>
<!--          <div slot="footer" class="dialog-footer">-->

            <el-button class="butWidth" size="small" type="primary" @click="submitForm">确 定</el-button>
            <el-button class="butWidth" style="margin-left: 0px"  size="small" @click="cancel">取 消</el-button>

<!--          </div>-->
        </el-dialog>















        </el-aside>

        <el-main class="isBookmarkMain" v-if="isMain">
                  <el-header class="mianUrl-top" >
                    <div class="mianUrl-top-left">
                      <i class="el-icon-folder-delete" @click="closeIsMain"></i>
                      <i class="el-icon-rank" ></i>
                    </div>
                    <div class="mianUrl-top-right">
                      <i class="el-icon-edit-outline" ></i>
                      <i class="el-icon-position" @click="windowurlOpen"></i>
                    </div>
                  </el-header>
<!--                  <div class="main-url-title">部分网站不允许内嵌套打开,请在设置中选择自己喜欢的打开方式!</div>-->
                  <div class="mianUrl-botoom"  v-loading="iframeLoading"  >
<!--                    webkitallowfullscreen="true" mozallowfullscreen="true" allowfullscreen="true"-->
<!--                    <iframe sandbox="allow-forms allow-scripts allow-popups" class="openurl" :src="gourl"  target="_self"  tabindex="-1"  />-->
<!--                    <router-view :key="$route.query.t"></router-view>-->
                  <TinyMceEdit :key="noteId" :ueditor="Ueditor"  :noteid="noteId"></TinyMceEdit>

                  </div>
        </el-main>
      </el-container>




  </div>
</template>

<script>
  import inform from '../common/inform.vue'
  import {listMenuByUserId} from "@/api/bookmark/menu";
  import { getUserProfile } from "@/api/system/user";
  import Treeselect from "@riophae/vue-treeselect";
  import BookmarkOne from "../../../components/BookmarkList";
  import TinyMceEdit from "../common/nqEdit";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import {
    selectBymenuIdUserID,
    selectBydelete,
    selectByUseridList,
    getBookmark,
    delBookmark,
    addBookmark,
    updateBookmark,
    exportBookmark,
    listByUserAndPolymerization
  } from "@/api/bookmark/bookmark";
  import {
    selectBymenuNote,
    userGetNoteInfo,
    addUserNote
  } from "@/api/note/note";
  import {format} from 'timeago.js';

  export default {
    components: {
      format, Treeselect,BookmarkOne,TinyMceEdit,inform
    },

    data: function () {
      return {
        //网页0 文本1 其他1
        property:0,
        //列表展示的模式
        showPattern:0,
        sousuo: '',
        drawer: false,
        // 遮罩层
        // loading: true,
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        //是否弹窗收藏书签
        addBookMakrUrlOpen:false,
        busy: false,
        showimg: false,
        loading: false,
        iframeLoading:false,
        queryParams: {
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
          sqTags: [],
          sort: 0,
          sousuo: '',
          type:'',
          bkOrderBy:'',
        },
        inputVisible: false, //标签
        inputValue: '', //标签
        tagcount: 0, //标签虚拟ID
        bookmarkList: [],
        urltext: '?from=yunshuqian.com',//网址域名起推广作用
        // 书签菜单树选项
        menuOptions: [],
        // 表单参数
        form: {},
        // 表单校验
        rules: {},
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
        datalist: {
          height: "",
        },
        listloading: false,//滚动加载提示
        listnoMore: false,
        total: 0,//总条数
        noMore: false,//是否可以滚动瀑布流
        highlighted: true,//搜索是否高亮
        //点击的网址
        gourl:'https://element.eleme.cn/#/zh-CN/theme',
        //便签系统开始
        noteParams: {
          pageNum: 1,
          pageSize: 15,
          noteId: undefined,
          userId: undefined,
          title: undefined,
          description: undefined,
          menuId: undefined,
          background: undefined,
          noteCount: undefined,
          noteSort: undefined,
          isState: undefined,
          readProgress: undefined,
          isStar: undefined,
          isDelete: undefined,
          topFlag: undefined,
          isShare: undefined,
          isEncryption: undefined,
          createUserName: undefined,
          tiymceUeditor:undefined
        },
        asideHeight: {
          // width: "100%!important",
          width: "400px!important",
        },
        isMain:true,
        menuId:undefined,//当前目录

        Ueditor:undefined,//点击的编辑器文章id
        noteId:undefined, //点击的noteId
        user:'',//登陆的用户信息
        sortState:true,//是否进行请求书签的拼接,切换排序规则时,不能进行拼接，重新渲染数据  false表示切换了 true没切换



      }
    },

    filters: {
      //timeago.js插件
      //计算时间，类似于几分钟前，几小时前，几天前等
      changeTime(val) {
        let time = new Date(val); //先将接收到的json格式的日期数据转换成可用的js对象日期
        return format(time, 'zh_CN'); //转换成类似于几天前的格式
      },
    },
    // mounted() {
    //
    //   // document.querySelector("#datalist").addEventListener('scroll', this.handleScroll)
    // },
    computed: {
      /**list加载完毕就禁止滚动**/

      /**是否禁用滚动**/
      disabled() {
        return this.listloading || this.noMore
      }
    },
    created() {
      var that = this;
      var routedata = that.$route.query.menuId;
      var sousuo = that.$route.query.sousuo;
      var property = that.$route.query.property;
      var bookmarkId = that.$route.query.bookmarkId;
      if (routedata == undefined) {
        // that.queryParams.menuId = 1;
      } else {
        that.queryParams.menuId = routedata;
        that.noteParams.menuId = routedata;
      }

      if (property != null && property != undefined && property != ''){
        that.property =property;
      }


      //搜索值
      if (sousuo != null && sousuo != undefined && sousuo != '') {
        this.queryParams.sousuo = sousuo;
        this.sousuo=sousuo
      }

      console.log("routedata："+routedata)
      if ((sousuo != null && sousuo != undefined && sousuo != '')||routedata == 'newest'||routedata == 'asterisk'||routedata == 'seeYouLater'||routedata == 'recycle') {
        that.queryParams.menuId=null;

        this.listByUserAndPolymerization(routedata);
        //全部书签
        // this.getBookmarkList();
      //
      // } else if (routedata == 'RECYCLE') {
      //   //回收站
      //   this.getrecycleList();

      } else {
        //根据menuId查询
        this.getBypropertyList(that.property);
      }

      //自动获取高度
      window.addEventListener('resize', this.getHeight);
      this.getHeight()

      //获取当前的用户信息
      // this.getUser();

    },
    mounted() {
      this.closeIsMain();

      window['closePopup'] = (e) => {
        this.closePopup(e)
      }
    },
    methods: {

      getUser() {
        getUserProfile().then(response => {
          this.user = response.data;
          this.roleGroup = response.roleGroup;
          this.postGroup = response.postGroup;
        });
      },
      closeIsMain(){
        this.asideHeight.width="100%!important"
        this.isMain=false;
      },
      openIsMain(){
        this.asideHeight.width="400px!important"
        this.isMain=true;
      },


      /**自动获取高度**/
      getHeight() {
        // if (window.innerHeight<=750) {
        //    this.asideHeight.height=='750px';
        //   return;
        // }
        this.datalist.height = (window.innerHeight - (window.innerHeight / 7.5)) + 'px';

      },

      /**滚动监控**/
      load() {
        console.log("滚动开始")
        var that = this;
        //判断是否加载了所有数据
        var i = that.queryParams.pageNum + 1;
        var m = that.noteParams.pageNum + 1;

        that.$set(that.queryParams, 'pageNum', i)
        that.$set(that.noteParams, 'pageNum', m)
        var listcount = Math.ceil(that.total / 15);


        if (i > listcount||m > listcount) {
          //加载完毕了 禁止滚动
          that.noMore = true;
          if (that.queryParams.pageNum!=0&&that.noteParams.pageNum!=0) {
            that.listnoMore = true;
          }
          that.listloading = false
          return;
        } else {
          this.listloading = true
          setTimeout(() => {
            switch(this.property) {
              case 0:
                this.getListConcat();
                break;
              case 1:
                this.getListNoteConcat();
                break;
              default:
            }
          }, 1000);
        }
      },

      /**切换显示 全部 网页 文本 其他**/
      showopen(e) {
        var that=this;
        that.property=e;
        //缓存状态
        that.$store.state.property=e;
        this.showimg=false;
        //初始化
        this.queryParams.pageNum=1;
        this.noteParams.pageNum=1;
        this.bookmarkList=[];

        this.getBypropertyList(e);

      },
      /**切换显示 全部 网页 文本 其他**/
      showPatternState(e) {
        var that=this;
        that.showPattern=e;
        console.log("showPattern =" +e)
        // //缓存状态
        // that.$store.state.showPattern=e;

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

      /**书签编辑设置的 标签开始**/
      taghandleClose(tag) {
//1. 首先我们要得到这个对象
        var tina = this.form.sqTags.filter((p) => {
          return p.tagId == tag;
        });
//2. 其次得到这个对象在数组中对应的索引
        var index = this.form.sqTags.indexOf(tina[0]);
//3. 如果存在则将其删除，index > -1 代表存在
//       index > -1 && this.form.sqTags.splice(index, 1);
        if (index > -1) {
          if (this.form.sqTags[index].tagId < 0) {
            this.form.sqTags.splice(index, 1);
          }else{
            this.form.sqTags[index].name = "TAGDELETE";
          }
        }
        console.log(this.form.sqTags);
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
          //添加
          var updatetag = {name: inputValue, bookmarkId: this.form.bookmarkId, tagId: this.tagcount};
          console.log("updatetag")
          this.form.sqTags.push(updatetag);
        }
        this.inputVisible = false;
        this.inputValue = '';
        console.log(this.form.sqTags);
      },


      /**书签编辑设置的 标签结束**/







      /** 修改按钮操作 */
      handleUpdate(bookmarkId) {
        console.log("handleUpdate："+bookmarkId);
        this.reset();
        const ibookmarkId = bookmarkId || this.ids
        getBookmark(ibookmarkId).then(response => {
          this.form = response.data;
          if(this.form.sqTags==null){
            this.form.sqTags=[];
          }
          this.open = true;
          this.title = "书签编辑管理";
        });
      },
      /** 提交按钮 修改和新增 */
      submitForm: function () {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.bookmarkId != undefined) {
              updateBookmark(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }else{
                  this.msgError("新增失败,系统错误!");
                  this.open = false;
                }
              });
            } else {
              addBookmark(this.form).then(response => {
                if (response.code === 200) {
                  if(response.data === 'repetition'){
                    this.msgSuccess("新增失败,此书签已经存在了!");
                    this.open = false;
                    return;
                  }
                  this.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                }else{
                  this.msgError("新增失败,系统错误!");
                  this.open = false;
                }
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(bookmarkId) {
        const bookmarkIds = bookmarkId || this.ids;
        this.$confirm('是否确认删除此条书签数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delBookmark(bookmarkIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function () {
        });
      },
      // 取消按钮
      cancel() {
        this.addBookMakrUrlOpen = false;
        this.reset();
      },
      // 统一的表单重置
      reset() {
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
          createTime: undefined,
          sqTags: [],
          sort: undefined,

        };
        this.resetForm("form");
      },    // 表单重置
      resetNote() {
        this.form = {
          noteId: undefined,
          userId: undefined,
          title: undefined,
          description: undefined,
          menuId: undefined,
          background: undefined,
          noteCount: undefined,
          noteSort: undefined,
          isState: undefined,
          readProgress: undefined,
          isStar: undefined,
          isDelete: undefined,
          topFlag: undefined,
          isShare: undefined,
          isEncryption: undefined,
          createTime: undefined,
          updateTime: undefined,
          createUserName: undefined
        };
        this.resetForm("form");
      },


      /** 回收站**/
      getrecycleList() {
        this.loading = true;
        selectBydelete(this.queryParams).then(response => {
          if (response.code == 200) {
            this.bookmarkList = this.bookmarkList.concat(response.rows);
            this.total = response.total;
            this.listloading = false
            this.loading = false;
            console.log("请求完毕" + this.queryParams.pageNum)
          } else {
            //出错了加载完毕了 禁止滚动
            this.noMore = true;
            this.listloading = false
            this.loading = false;
          }
        });
      },
      // /** 全部书签**/
      // getBookmarkList() {
      //   this.loading = true;
      //   selectByUseridList(this.queryParams).then(response => {
      //       if (response.code == 200) {
      //         this.bookmarkList = this.bookmarkList.concat(response.rows);
      //         this.total = response.total;
      //         this.listloading = false
      //         this.loading = false;
      //         console.log("请求完毕" + this.queryParams.pageNum)
      //       } else {
      //         //出错了加载完毕了 禁止滚动
      //         this.noMore = true;
      //         this.listloading = false
      //         this.loading = false;
      //       }
      //   });
      // },
      /** 最新 星标 回收站 稍后看**/
      listByUserAndPolymerization(str) {
        console.log(" 最新 星标 回收站 稍后看");
        this.loading = true;
        this.queryParams.type=str;
        this.queryParams.bkOrderBy="";
        listByUserAndPolymerization(this.queryParams).then(response => {
            if (response.code == 200) {
              //如果进行了排序切换 就不能进行拼接
              if (this.sortState){
                  this.bookmarkList = this.bookmarkList.concat(response.rows);
              }else{
                  this.bookmarkList = response.rows;
                  this.sortState = false;
              }
              this.total = response.total;
              this.listloading = false
              this.loading = false;
              if (response.total == 0){
                this.showimg = true;//空提示
              }
              console.log("请求完毕" + this.queryParams.pageNum)
            } else {
              //出错了加载完毕了 禁止滚动
              this.noMore = true;
              this.listloading = false
              this.loading = false;
              this.showimg = true;
            }
        });
      },
      /**根据条件查询*/
      getBypropertyList(e){
        switch(e) {
          case 0:
            this.getListConcat();
            break;
          case 1:
            this.getListNoteConcat();
            break;
          default:
            this.loading = true;

            //延迟只为了 动画效果 好看
            setTimeout(()=>{
              this.loading = false;
              this.bookmarkList=[]
              setTimeout(()=>{
                this.showimg = true;
              },80)
            },200)

        }
      },

      /**切换排序规则**/
      handleCommand(command) {
        if (this.queryParams.sort != command){
          this.sortState = false;//是否切换了新的排序规则方式 false表示切换了 true没切换
        }
        this.queryParams.sort = command;

        this.getBypropertyList(this.property);

      },
      /**添加遍签**/
      addbooNote() {
        addUserNote(this.noteParams).then(response => {
          if (response.code === 200) {
            this.msgSuccess("新增成功");
            this.getNoteList();
          }
        });
      },

      /** 查询书签管理列表 */
      getList() {
        this.loading = true;
        listByUserAndPolymerization(this.queryParams).then(response => {
          if (response.code == 200) {
            this.bookmarkList = response.rows;
            this.total = response.total;
            this.loading = false;
            this.showimg=response.total==0?true:false;
            this.listloading = false
          } else {
            this.showimg = true;
            this.loading = false;
            this.listloading = false
            //出错了加载完毕了 禁止滚动
            this.noMore = true;
          }
        });
      },
      /**查询书签 滚动加载分页拼接*/
      getListConcat(){
        var that=this;
        this.loading = true;
        var routedata = this.queryParams.menuId;
        if(routedata == 'newest'||routedata == 'asterisk'||routedata == 'seeYouLater'||routedata == 'recycle'){
          this.listByUserAndPolymerization(routedata);
        }else{
          //查看目录下的书签
          this.listByUserAndPolymerization(routedata);
        }
      },
      /**查询便签 滚动加载分页拼接*/
      getListNoteConcat(){
        var that=this;
        this.loading = true;
        if(this.queryParams.menuId=='BOOKMARK'){
          //全部便签
          this.loading = false;
          this.showimg = true;
        }else if (this.queryParams.menuId == 'RECYCLE') {
          //回收站便签
          this.loading = false;
          this.showimg = true;
        }else{
          //查看目录下的便签
          this.getNoteListConcat();
        }
      },
      //查看目录下的书签
      getlistByMenuId(){
        selectBymenuIdUserID(this.queryParams).then(response => {
          if (response.code == 200) {
            this.bookmarkList = this.bookmarkList.concat(response.rows);
            this.total = response.total;
            this.listloading = false
            this.loading = false;
            this.showimg=response.rows==0?true:false;
          } else {
            //出错了加载完毕了 禁止滚动
            this.noMore = true;
            this.listloading = false
            this.loading = false;
          }
        });
      },


      /** 查询便签管理列表 */
      getNoteList() {
        this.loading = true;
        selectBymenuNote(this.noteParams).then(response => {
            this.bookmarkList = response.rows;
            this.total = response.total;
            this.loading = false;
            this.showimg=response.rows==0?true:false;
        });
      },
      /**查询便签 滚动加载分页拼接*/
      getNoteListConcat(){
        this.loading = true;
        selectBymenuNote(this.noteParams).then(response => {
          if ( response.code == 200) {
            this.bookmarkList = this.bookmarkList.concat(response.rows);
            this.total = response.total;
            this.loading = false;
            this.listloading = false
            if (response.total==0||this.bookmarkList.length==0) {
              this.showimg=true;
            }
          }else {
            //出错了加载完毕了 禁止滚动
            this.noMore = true;
            this.listloading = false
            this.loading = false;
            this.showimg=true;
          }
        });
      },
      /**网站内便签打开 网页*/
      windowurl(noteId, tiymceueditor,bookmarkId,url) {
        var that=this;
        switch (that.property) {
          case 0:
            /**网页新窗口打开*/
            window.open(url);
            break;
          case 1:
            /**编辑器内部打开*/
            this.openIsMain();
            that.Ueditor=tiymceueditor;//点击的编辑器文章id
            that.noteId=noteId; //点击的noteId
            break;
          default:
            /**网页新窗口打开*/
            window.open(url);
        }


      },
      /**新窗口打开*/
      windowurlOpen() {
       window.open(this.gourl);
      },


      /**搜索功能**/
      gosousuo() {
        var that = this;
        that.$router.push({
          path: "/content",
          query: {
            sousuo: this.sousuo,
            t:Date.now(),
          }
        })
      },

      /** 查询部门下拉树结构 */
      getTreeselect() {
        if (this.zNodes != null && this.zNodes.length != 0) {
          this.menuOptions = [];
          const data = {menuId: 0, menuName: '顶级菜单', children: []};
          data.children = this.handleTree(this.zNodes, "menuId", "parentId");
          this.menuOptions.push(data);
          return;
        }

        listMenuByUserId().then(response => {
          this.menuOptions = [];
          const data = {menuId: 0, menuName: '顶级菜单', children: []};
          data.children = this.handleTree(response.data, "menuId", "parentId");
          this.menuOptions.push(data);
        });
      },


      /** 新增书签Url操作 */
      addbookmarkurl: function () {
        this.reset();
        // this.getTreeselect();
        this.addBookMakrUrlOpen = true;

        // getMenu(e.getAttribute("data-menuId")).then(response => {
        //   this.form = response.data;
        //   this.open = true;
        //   this.title = "修改书签菜单";
        // });
      },
      //关闭弹窗
      closePopup: function() {
        if (document.getElementById("popupDiv")){
        document.getElementById("popupDiv").style.display = 'none';
        }
      }

    },


  }
</script>


<style scoped>
/*.isbookmarkContainer{*/
/*  background-color: #f0f0f2;*/
/*  background-image: url(https://shijiechao.oss-cn-hangzhou.aliyuncs.com/wp-content/uploads/2020/12/top_bg.png);*/
/*  background-repeat: no-repeat;*/
/*  background-attachment: fixed;*/
/*  background-position: center top;*/
/*}*/

  .button-new-tag {
    margin-left: 5px;
    height: 30px;
    line-height: 28px;
    padding-top: 0;
    padding-bottom: 0;
  }

  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }

  /**编辑标签 结束**/


  .filler-tag span {
    font-size: 12px;
    margin-left: 5px;
    background-color: #FFFFFF;
    color: #399de0;
    margin-bottom: 4px;
  }

  .filler-tag span:hover {
    background-color: #E8F3FC;
    color: #1988E0;
  }

  .bookamrk-hr {
    margin-top: 0;
  }


  .filter-sort-dropdown {
    box-shadow: 0 2px 15px 0 rgba(0, 0, 0, .2) !important;
    font-weight: 600 !important;
  }

  .filter-sort-dropdown div {
    display: none !important;
  }

  .nullbookmark {
    display: flex;
    flex-flow: column;
    margin-top: 10%;
  }

  .nullbookmark-img {
    margin: 0 auto;
    width: 450px;
    height: 250px;
    justify-content: center;
    align-content: center;
    background-image: url("../../../assets/image/ts.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    opacity: 0.75;
  }

  .nullbookmark-text {
    margin: 0 auto;
    width: 250px;
    text-align: center;
    color: #D4D4D4;
    font-size: 18px;
    /*font-family: "Arial","Microsoft YaHei","黑体","宋体",sans-serif;*/
  }

  /*.editBookamrk {*/
  /*  width: 270px;*/
  /*  height: 70px;*/
  /*  position: absolute;*/
  /*  background-color: #acd7ff;*/
  /*  right: 0;*/
  /*  display: none;*/
  /*}*/

  .editlist {
    display: flex;
    width: 100%;
    height: 70px;
    flex-flow: wrap;
    align-items: center;

  }

  .bookmark-time {
    float: left;
  }


  .editlist div {
    margin-left: 10px;
    width: 50px;
    height: 35px;
    align-content: center;

  }

  .bookmarktag {
    margin-right: 5px;
  }

  .bookmark-official {
    float: left;
  }

  .info {
    font-size: 12px;
    display: flex;
    align-items: center;
  }

  .bookmark-icon {
    float: left;
    margin-right: 5px;
  }

  .bookmark-icon img {
    width: 15px;
    height: 15px;

  }

  .filter-item:hover {
    background-color: #E5E5E5 !important;
    color: #000 !important;
  }

  .filter-tbar {
    display: flex;
    position: relative;
    width: 100%;
    height: 40px;
    -webkit-box-pack: justify;
    justify-content: space-between;
    align-items: center;

    box-sizing: border-box;

  }

  .filter-classification {
    display: flex;
    -webkit-box-align: center;
    align-items: center;

  }

  .setUpThe {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 13px;
  }

  .filter-content {
    margin-left: 2px;

  }

  .dropdownList {
    display: flex;
    justify-content: center;
    font-size: 18px;
  }

  .dropdownList i {
    margin-right: 4px;
    font-weight: 600;
  }

  .dropdownList span {
    line-height: initial;
    font-size: 17px;
  }


  .classification {
    width: 60px;
    height: 27px;
    margin-left: 10px;
    text-align: center;
    line-height: 26px;
    color: #a4a4a4;
  }

  .classification-click {
    border-radius: 19px;
    /*background: #606c88; !* fallback for old browsers *!*/
    /*background: -webkit-linear-gradient(to right, #3f4c6b, #606c88); !* Chrome 10-25, Safari 5.1-6 *!*/
    /*background: linear-gradient(to right, #3f4c6b, #606c88); !* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ *!*/
    background-color: #ffffff;
    color: #a4a4a4;
    box-shadow: 0 1px 4px 0 rgba(0, 0, 0, 0.4)
  }

  .bookmark-list-tag {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 4px;
    height: 18px;
    line-height: 18px;
    margin-top: -2px;
    border-radius: 1px;
    color: #7794D8;
    background-color: #FFFFFF;
    border-radius: 0px;
    border: 1px solid #EAEAEF;
  }

  .bookmark-list-tag:hover {
    color: #569cd5;
  }

  .listhint {
    width: 100%;
    text-align: center;
    height: 20px;

    color: #808080;
    /*background: #dcdfe682;*/
    font-weight: 600;
    padding: 0;
    margin: 0;
    padding-bottom: 50px;
  }

  .bookmark-title:hover {

    color: #3C4DAC;
  }

  .description {
    color: #9EABB3;
    margin-top: 5px;
    margin-bottom: 5px;
  }

  .info-wrap {
    color: #9EABB3;
  }

  /**evanyou背景特效**/

  .bookmark-title {
    /*设置文本框大小*/
    white-space: nowrap;
    /*设置内容不换行*/
    text-overflow: ellipsis;
    /*设置文字超出文本框的内容显示成...*/
    overflow: hidden;
    /*超出部分隐藏*/
    font-size: 17px;
    font-weight: 600;
    margin-top: 10px;
    margin-bottom: 2px;
  }



  .description {
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

  .bookmarklist {
    width: 100%;

  }


  /**滚动条美化*/

  /*滚动条的宽度*/

  ::-webkit-scrollbar {
    width: 5px;
    height: 3px;
    /*background-color: red;*/
  }

  /*外层轨道。可以用display:none让其不显示，也可以添加背景图片，颜色改变显示效果*/

  ::-webkit-scrollbar-track {
    width: 6px;
    background-color: transparent;
    -webkit-border-radius: 2em;
    -moz-border-radius: 2em;
    border-radius: 2em;
  }

  /*滚动条的设置*/

  ::-webkit-scrollbar-thumb {
    /*设置为透明 隐藏掉*/
    background-color: #606d71;
    /*background-color: transparent;*/
    background-clip: padding-box;
    min-height: 28px;
    -webkit-border-radius: 2em;
    -moz-border-radius: 2em;
    border-radius: 2em;

  }

  /*滚动条移上去的背景*/

  ::-webkit-scrollbar-thumb:hover {
    background-color: #7c6fff;
  }

  /*滚动条美化结束*/


  .sousuoContent {
    width: 100%;
    height: 24px;
    background-color: #F2F2F2;
    color: #6a6a6a;
    font-weight: 600;
    line-height: 24px;
    border-radius: 3px;
    text-indent: 4px;
    /*text-align:center;*/
    margin-bottom: 4px;

  }


  .bookmark {
    height: 70px;
    /*border-bottom: 1px solid #D9D9D9;*/

  }

  .bookmark2 {
    height: 52px;
    /*border-bottom: 1px solid #D9D9D9;*/
    white-space: nowrap;
   border-top:1px solid #D4D4D4 ;
  }

  .sousouright-icon {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 50px;
    margin: 0 auto;

  }

  .sousouright-iconadd {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 60px;
  }

  .sq-dropdown {
    top: 33px !important;
    box-shadow: 0 2px 15px 0 rgba(0, 0, 0, .2) !important;
    font-weight: 800 !important;
  }

  .sq-dropdown div {
    display: none !important;
  }

  .sousouleft-switch {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-right: 10px;
    width: 30px;
    height: 30px;

  }

  .sousouleft-switch i {
    font-size: 30px !important;
    opacity: 0.8;


  }

  .sousou-leftico {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 30px;
    height: 30px;
    border-radius: 3px;
    border: 1px solid #e6e6e6;
    margin-right: 10px;


  }

  .sousou-leftico img {

  }

  .header-sousou {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 50px;
    padding-left: 0px;

  }


  .header-sousou input {

    margin-top: 9px;
    border: 0px;
    background-color: #f2f2f2;
    border-radius: 0 !important;
    /*border:  1px solid #f2f2f2;*/


  }

  .header-sousou input:focus {
    background-color: #FFFFFF !important;
    border: 1px solid #C0C4CC;
  }


  .sousou-input {
    display: flex;
    width: 100%;
  }

  .sousou-input i {
    font-size: 15px;
  }


  .header-list {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 35px;
  }

  .header-list:hover {
    color: #7a6df0;

  }

  .header-list i {
    font-size: 25px;
    margin-right: 2px;
  }

  .sousou-left {

    margin-left: 12px;

  }
  .isBookmarkAside{
    padding: 0px;
    background-color: #ffffff;
    width: 400px!important;
    /*width: 100%!important;*/
    box-shadow: inset -1px 0 0 rgba(0, 0, 0, .1);
  }
  .isBookmarkMain{
    padding: 0px;
  }

  .mianUrl{
    padding: 0px;
    color: #8F8F8F;
  }
  .mianUrl-top{
    display: flex;
    padding: 0px!important;
    height: 50px;
  }
  .mianUrl-top div{
    width: 50%;
    height: 50px;
    background-color: #FFFFFF;
    font-size: 22px;
    color: #414141;
    padding-top: 19px;
  }
  .mianUrl i{
    display: block;
    width: 30px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    margin-top: 15px;
    border-radius: 2px;
  }

  .mianUrl-top-left i{
    float: left;
    margin-left: 15px;
  }
  .mianUrl i:hover{
    background-color: #F2F2F2;
  }
  .mianUrl-top-right i{
    float: right;
    margin-right: 15px;
  }

  .mianUrl-botoom{
  padding-bottom: 20px;

  }
  .openurl{
    background-color: #ffffff;
    border: 0px;
    width: 100%;
    height: 100%;
  }
  .main-url-title{
    width: 100%;
    color: #565656;
    text-indent: 15px;
  }
.butWidth{
  width: 100%;
  margin-top: 10px;
}
  .addUrl-input{
    margin-bottom: 13px;
  }


</style>
