<template>
  <div>

    <div class="filter-tbar">
      <div class="filter-classification">
        <div class="classification " @click="showopen(0)"><span>网页</span></div>
        <div class="classification" @click="showopen(1)"><span>文本</span></div>
        <div class="classification" @click="showopen(2)"><span>其他</span></div>
        <!--        <div class="classification" @click="showopen(3)"><span>其他</span></div>-->
      </div>
      <div class="setUpThe">
        <div class="filter-content">
          <el-dropdown trigger="hover" size="small" @command="handleCommand">
            <div class="el-dropdown-link dropdownList">
              <i class="el-icon-document-checked "></i> <span>排序</span>
            </div>
            <el-dropdown-menu slot="dropdown" class="filter-sort-dropdown">
              <el-dropdown-item class="filter-item" command="0"><i class="el-icon-bottom"></i>按时间排序(正)
              </el-dropdown-item>
              <el-dropdown-item class="filter-item" command="1"><i class="el-icon-bottom"></i>按时间排序(反)
              </el-dropdown-item>
              <el-dropdown-item class="filter-item" command="2"><i class="el-icon-bottom"></i>按标题A-Z排序(正)
              </el-dropdown-item>
              <el-dropdown-item class="filter-item" command="3"><i class="el-icon-bottom"></i>按标题A-Z排序(反)
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

        </div>
        <div class="filter-content">
          <el-dropdown trigger="hover" size="small">
            <div class="el-dropdown-link dropdownList">
              <i class="el-icon-tickets "></i> <span>列表</span>
            </div>
            <el-dropdown-menu slot="dropdown" class="filter-sort-dropdown">
              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>按时间排序(正)</el-dropdown-item>
              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>按时间排序(反)</el-dropdown-item>
              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>按字母A-Z排序</el-dropdown-item>
              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>按字母A-Z排序</el-dropdown-item>
              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>按网站A-Z排序</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

        </div>
<!--        <div class="filter-content">-->
<!--          <el-dropdown trigger="hover" size="small">-->
<!--            <div class="el-dropdown-link dropdownList">-->
<!--              <i class="el-icon-setting "></i> <span>设置</span>-->
<!--            </div>-->
<!--            <el-dropdown-menu slot="dropdown" class="filter-sort-dropdown">-->
<!--              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>编辑</el-dropdown-item>-->
<!--              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>批量处理</el-dropdown-item>-->
<!--              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>排序</el-dropdown-item>-->
<!--              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>按网站A-Z排序</el-dropdown-item>-->
<!--            </el-dropdown-menu>-->
<!--          </el-dropdown>-->
<!--        </div>-->
      </div>
    </div>

    <div v-if="queryParams.sousuo!=''" class="sousuoContent">
      <i class="el-icon-success"></i>
      <!--      <span> 当前搜索的内容是:{{queryParams.sousuo}}</span>-->
      <span> 共为您找到约{{total}}个结果</span>
    </div>

    <div class="nullbookmark" v-if="showimg">
      <div class="nullbookmark-img">
      </div>
      <div style="color: #000000" class="nullbookmark-text">
        此目录还未添加书签
      </div>
    </div>


    <div class="bookmarklist" :style="datalist" infinite-scroll-distance="10" v-loading="loading" v-if="showbookmark"
         v-infinite-scroll="load"
         infinite-scroll-disabled="disabled" style="overflow:auto;" infinite-scroll-immediate="false">
      <el-row>
        <!--        <hr class="bookamrk-hr" v-if="!queryParams.sousuo!=''"/>-->
        <el-col :span="24" v-for="bm in bookmarkList">
          <div class="editBookamrk ">
            <div class="editlist">
              <div>
                <el-button plain size="small" @click="handleUpdate(bm.bookmarkId)">修改</el-button>
              </div>
              <div>
                <el-button plain size="small" @click="handleDelete(bm.bookmarkId)">删除</el-button>
              </div>
              <div>
                <el-button plain size="small">笔记</el-button>
              </div>
              <div>
                <el-button plain size="small">分享</el-button>
              </div>
            </div>
          </div>


          <div class="bookmark " :data-id="bm.id" @click="windowurl(bm.url,bm.bookmarkId)">
            <p class="bookmark-title" v-if="highlighted"><span v-html="highLight(bm.title,queryParams.sousuo)"/></p>
            <p class="bookmark-title" v-if="!highlighted">{{bm.title}}</p>
            <div class="">
              <p class="description" v-if="highlighted"><span v-html="highLight(bm.description,queryParams.sousuo)"/>
              </p>
              <p class="description" v-if="!highlighted">{{bm.description}}</p>
            </div>
            <div class="info-wrap">
              <div class="info">
                <div class="bookmark-icon">
                  <!--                    <img :err-src='bm.icon'   :ng-src="bm.icon"  :src="bm.icon"  />-->
                  <img :ng-src="'https://favicon.lucq.fun/?url=http://'+bm.urls"
                       :src="'https://favicon.lucq.fun/?url=http://'+bm.urls"
                       onerror="this.src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQEAYAAABPYyMiAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAgY0hSTQAAeiYAAICEAAD6AAAAgOgAAHUwAADqYAAAOpgAABdwnLpRPAAAAAZiS0dEAAAAAAAA+UO7fwAAAAlwSFlzAAAASAAAAEgARslrPgAAAmVJREFUSMftlG1IU2EUx8+5jRHzg+5OiD64WTBBjSY5ZybUJMaMiIUICUKFIGmoIIogYWKFBmtDxN1tBKIWvlS6SR+iLFDBXqSMgmzbvQ7fkAjcINmQ5u7pQwyCiOvWx/p/fPj9/5znnPM8AP+VolRTjjHhTlmZipzEk9GYao4sVaMYxXp62NgIdpLBCuLP05mZZHOYZA3p5KgMjBcUwCmw4PfVVYiBGmbW1zMzOU4QCgtTvdAeRESEyNZyJbx+dDTjVv/9YFSjSScnfabsbPYMF+AbxsYS3F5TfwPZAacpMNfUBEAljK24mMJwlLIUClTAdfwok9FXWBTtc3Phzqv7c/RWa8KnrHZM8VltbRjC92AxGkEPRpSJInjJQPpIBJoZC6nn50M19dM5J/v6/lhRxgXXDX9Qp1POcmHh2sTEQbfbvVmrUCTbr4RPaeMe8Q0eTyI36VkrW50mftjrPWCyDn9oSUuTdj4Y/1Qpl7NdzkNC98iIqsIxHdQVFaU8eWW1U1guLy1lr3Acr3O7pXi227EhDLpcCZ8UL/kK8LIYjxtDIRhEP8xGIlI8bcAKvY3FGB/zYvf81pYUL/0PvGTKoTUvjzbgCx5ZWlJq3a5lQa1Ge3yTWnp64ASYqVcUoXn3Jtja2/EpRtHj89FF8Tb25ufDOwCo8/lSL0BF/eDPzcVXeA7WDAa6J9rES1VVdJqx41JHBwCE8BgA3JU9B+3AACziMwjv7MAaPQb/wgIAnP2rDpCWtpkncjm+Ro34bWgo3FkX0O6bnARARCT6hSR6YzazXa7DfLyigo7TNtObxNb/s/oB7V8JFvW/8IQAAAAldEVYdGRhdGU6Y3JlYXRlADIwMjAtMDItMDJUMTg6MTE6NTgrMDg6MDCoc6tpAAAAJXRFWHRkYXRlOm1vZGlmeQAyMDIwLTAyLTAyVDE4OjExOjU4KzA4OjAw2S4T1QAAAGd0RVh0c3ZnOmJhc2UtdXJpAGZpbGU6Ly8vaG9tZS9hZG1pbi9pY29uLWZvbnQvdG1wL2ljb25fNWhyOG55Nmo1bWMvamluZ2RpYW5hbmxpX2tvbmd3dWppYW94aW5nX3Nob3VjYW5nLnN2Z4/vilwAAAAASUVORK5CYII='"
                       alt="" ng-show="bm.urls">
                </div>
                <div class="bookmark-official">{{bm.urls}}&nbsp;·&nbsp;</div>
                <div class="bookmark-time">{{bm.createTime|changeTime}}</div>
                <div class="bookmark-time" v-for="t in bm.sqTags">
                  <el-tag class="bookmark-list-tag" data-bookmarkId="t.bookmarkId" data-tagId="t.tagId" size="mini">
                    {{t.name}}
                  </el-tag>

                </div>


              </div>
            </div>
          </div>
        </el-col>

      </el-row>
      <p v-if="listloading" class="listhint"><i class="el-icon-loading"></i>加载中...</p>
      <p v-if="listnoMore" class="listhint">没有更多了</p>
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
        <el-form-item label="所属目录" prop="menuId">
          <el-input v-model="form.menuId" placeholder="请选择上级目录"/>
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>


  import {
    selectBymenuIdUserID,
    selectBydelete,
    selectByUseridList,
    getBookmark,
    delBookmark,
    addBookmark,
    updateBookmark,
    exportBookmark
  } from "@/api/bookmark/bookmark";
  import {format} from 'timeago.js';

  export default {
    components: {
      format
    },

    data: function () {
      return {
        // 遮罩层
        loading: true,
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        busy: false,
        showbookmark: true,
        showimg: false,
        loading: false,
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
        },
        inputVisible: false, //标签
        inputValue: '', //标签
        tagcount: 0, //标签虚拟ID
        bookmarkList: [],
        urltext: '?from=yunshuqian.com',//网址域名起推广作用
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
    mounted() {

      // document.querySelector("#datalist").addEventListener('scroll', this.handleScroll)
    },
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
      if (routedata == undefined) {
        // that.queryParams.menuId = 1;
      } else {
        that.queryParams.menuId = routedata;
      }

      //搜索值
      if (sousuo != null && sousuo != undefined && sousuo != '') {
        this.queryParams.sousuo = sousuo;
      }
      if (routedata == 'BOOKMARK') {
        //全部书签
        this.getBookmarkList();

      } else if (routedata == 'RECYCLE') {
        //回收站
        this.getrecycleList();

      } else {
        //根据menuId查询
        this.getList();
      }

      //自动获取高度
      window.addEventListener('resize', this.getHeight);
      this.getHeight()

    },
    mounted() {
      this.newBookmark();
    },
    methods: {
      /**初始化 分类全部的意思**/
      newBookmark() {
        //默认选中 全部
        document.getElementsByClassName("classification")[0].classList.add("classification-click");
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
        var that=this;
        //判断是否加载了所有数据
        var i=that.queryParams.pageNum+1;
        that.$set(that.queryParams,'pageNum',i)
        console.log("this.queryParams.pageNum:"+that.queryParams.pageNum)
        var listcount = Math.ceil(that.total / 15);
        console.log("该目录共有页数:" +listcount)

        if (i > listcount) {
          //加载完毕了 禁止滚动
          that.noMore = true;
          that.listnoMore = true;
          that.listloading = false
          console.log("禁止滚动了")
          return;
        } else {

          this.listloading = true
          setTimeout(() => {
            selectBymenuIdUserID(this.queryParams).then(response => {
              if (response.rows.length != 0 && response.code == 200) {
                console.log("response.rows" + response.rows)
                this.bookmarkList = this.bookmarkList.concat(response.rows);

                this.total = response.total;
                this.listloading = false
                console.log("请求完毕"+that.queryParams.pageNum)
              } else {
                //出错了
                //加载完毕了 禁止滚动
                this.noMore = true;
                this.listloading = false
              }
            });
          }, 1000);
        }

        // if (this.queryParams.pageNum = listcount) {
        //   //加载完毕了 禁止滚动
        //   that.noMore = true;
        //   that.listnoMore = true;
        //   that.listloading = false
        //   console.log("刚刚好2页 禁止滚动了")
        // }

      },

      /**切换显示 全部 网页 文本 其他**/
      showopen(e) {
        document.getElementsByClassName("classification")[e].classList.add("classification-click");
        for (var i = 0; i < 4; i++) {
          if (i != e) {
            document.getElementsByClassName("classification")[i].classList.remove('classification-click');
          }
        }
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
          }
          this.form.sqTags[index].name = "TAGDELETE";
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

          this.form.sqTags.push(updatetag);
        }
        this.inputVisible = false;
        this.inputValue = '';
        console.log(this.form.sqTags);
      },


      /**书签编辑设置的 标签结束**/







      /** 修改按钮操作 */
      handleUpdate(bookmarkId) {
        this.reset();
        const ibookmarkId = bookmarkId || this.ids
        getBookmark(ibookmarkId).then(response => {
          this.form = response.data;
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
                }
              });
            } else {
              addBookmark(this.form).then(response => {
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
        this.open = false;
        this.reset();
      },
      // 表单重置
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
          sqTags: undefined,
          sort: undefined,
        };
        this.resetForm("form");
      },
      /** 回收站**/
      getrecycleList() {
        this.loading = true;
        selectBydelete(this.queryParams).then(response => {
          if (response.total != 0 && response.code == 200) {
            this.bookmarkList = response.rows;
            this.total = response.total;
            this.loading = false;
          } else {
            this.showbookmark = false;
            this.showimg = true;
          }
        });
      },
      /** 全部书签**/
      getBookmarkList() {
        this.loading = true;
        selectByUseridList(this.queryParams).then(response => {
          if (response.total != 0 && response.code == 200) {
            this.bookmarkList = response.rows;
            this.total = response.total;
            this.loading = false;
          } else {
            this.showbookmark = false;
            this.showimg = true;
          }
        });
      },

      /**切换排序规则**/
      handleCommand(command) {
        this.queryParams.sort = command;
        this.getList();
      },

      /** 查询书签管理列表 */
      getList() {
        this.loading = true;
        selectBymenuIdUserID(this.queryParams).then(response => {
          if (response.total != 0 && response.code == 200) {
            this.bookmarkList = response.rows;
            this.total = response.total;
            this.loading = false;
          } else {
            this.showbookmark = false;
            this.showimg = true;
          }
        });
      },
      windowurl(url, bookmarkId) {
        // window.open(url+this.urltext);
        window.open(url);
      },

      /**搜索高亮 开始**/
      highLight(item, highLight) {
        return this.highLightTableMsg(item, highLight)
      },
      highLightTableMsg(msg, highLightStr) {
        if (msg == null) {
          msg = ''
        }
        if (highLightStr == null) {
          highLightStr = ''
        }
        if (msg instanceof Object) {
          msg = JSON.stringify(msg)
        }
        if (highLightStr instanceof Object) {
          highLightStr = JSON.stringify(highLightStr)
        }
        if (!(msg instanceof String)) {
          msg = msg.toString()
        }
        if (!(highLightStr instanceof String)) {
          highLightStr = highLightStr.toString()
        }
        var htmlStr = ''
        if (highLightStr.length > 0) {
          if (msg.indexOf(highLightStr) !== -1) {
            assemblyStr(msg, highLightStr)

          } else {
            htmlStr = '<span>' + msg + '</span>'
          }
        } else {
          htmlStr = '<span>' + msg + '</span>'
        }

        function assemblyStr(msgAssembly, highLightAssembly) {
          if (msgAssembly.indexOf(highLightAssembly) !== -1) {
            var length = highLightAssembly.length
            // alert(length)
            var start = msgAssembly.indexOf(highLightAssembly)
            htmlStr = htmlStr + '<span>' + msgAssembly.substring(0, start) + '</span>' + '<span style="color:red;">' + highLightAssembly + '</span>'
            msgAssembly = msgAssembly.substring(start + length, msgAssembly.length)
            assemblyStr(msgAssembly, highLightAssembly)
          } else {
            htmlStr = htmlStr + '<span>' + msgAssembly + '</span>'
          }
        }

        return htmlStr;
      },
      /**搜索高亮 结束**/

    },


  }
</script>


<style scoped>


  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
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
    width: 250px;
    height: 250px;
    justify-content: center;
    align-content: center;
    background-image: url("https://s1.ax1x.com/2020/08/22/dawFp9.png");
    background-repeat: no-repeat;
    background-size: 100% 100%;
  }

  .nullbookmark-text {
    margin: 0 auto;
    width: 250px;
    text-align: center;
    color: #D4D4D4 !important;

  }

  .editBookamrk {
    width: 270px;
    height: 70px;
    position: absolute;
    background-color: #acd7ff;
    /*right: 0;编辑*/
    display: none;
  }

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

  .bookmark-hr {
    background-color: #fff !important;
    margin-top: 2px !important;
    margin-bottom: 7px !important;
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

  .filter-tbar {
    display: flex;
    position: relative;
    width: 100%;
    height: 40px;
    -webkit-box-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
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
  }

  .filter-content {
    margin-left: 18px;

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
    background: #606c88; /* fallback for old browsers */
    background: -webkit-linear-gradient(to right, #3f4c6b, #606c88); /* Chrome 10-25, Safari 5.1-6 */
    background: linear-gradient(to right, #3f4c6b, #606c88); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
    color: #FFFFFF;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.4)
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
    color: #808080;
    /*background: #dcdfe682;*/
    font-weight: 600;
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
    height: 600px;
    /*background-color: #fff;*/
    float: left
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
    /*background-color: #606d71;*/
    background-color: transparent;
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




</style>
