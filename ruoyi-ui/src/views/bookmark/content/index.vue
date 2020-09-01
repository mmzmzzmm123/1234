

<template >
    <div >

      <div class="filter" >
        <div class="filter-sort" >
          <el-dropdown trigger="click" size="small">
              <span class="el-dropdown-link">
            <span>按时间排序(正)</span> <i class="el-icon-caret-bottom"></i>
              </span>
            <el-dropdown-menu slot="dropdown" class="filter-sort-dropdown">
              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>按时间排序(正)</el-dropdown-item>
              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>按时间排序(反)</el-dropdown-item>
              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>按字母A-Z排序</el-dropdown-item>
              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>按字母A-Z排序</el-dropdown-item>
              <el-dropdown-item class="filter-item"><i class="el-icon-bottom"></i>按网站A-Z排序</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>





        </div>
        <div >
          <el-divider direction="vertical"></el-divider>
        </div>
        <div class="filler-tag">
          <el-tag size="small">我的最爱</el-tag>
        </div>
        <div class="filler-tag">
          <el-tag size="small" >小型标签</el-tag>
        </div>
        <div class="filler-tag">
          <el-tag size="small" >小型标签</el-tag>
        </div>
        <div class="filler-tag">
          <el-tag size="small" >小型标签</el-tag>
        </div>
        <div class="filler-tag">
          <el-tag size="small" >小型标签</el-tag>
        </div>
        <div class="filler-tag">
          <el-tag size="small" >小型标签</el-tag>
        </div>
        <div class="filler-tag">
          <el-tag size="small" >...</el-tag>
        </div>



      </div>

      <div class="nullbookmark" v-if="showimg">
          <div class="nullbookmark-img">

          </div>
        <div style="color: #000000" class="nullbookmark-text">
          此目录还未添加书签
        </div>

      </div>

      <div  class="bookmarklist" v-loading="loading" v-if="showbookmark">

        <el-row >

          <hr class="bookamrk-hr"/>
          <el-col :span="24" v-for="bm in bookmarkList">

            <div class="editBookamrk">
              <div class="editlist">
                <div> <el-button plain size="small" @click="handleUpdate(bm.bookmarkId)">修改</el-button></div>
                <div> <el-button plain size="small" @click="handleDelete(bm.bookmarkId)">删除</el-button></div>
                <div> <el-button plain size="small">笔记</el-button></div>
                <div> <el-button plain size="small">分享</el-button></div>

              </div>


            </div>

            <div class="bookmark" :data-id="bm.id" @click="windowurl(bm.url,bm.bookmarkId)">
              <p class="bookmark-title">{{bm.title}}</p>
              <div class="">
                <p class="description">{{bm.description}}</p>
              </div>
              <div class="info-wrap">
                <div class="info">
                  <div class="bookmark-icon">
<!--                    <img :err-src='bm.icon'   :ng-src="bm.icon"  :src="bm.icon"  />-->
                    <img :ng-src="'https://favicon.lucq.fun/?url=http://'+bm.urls" :src="'https://favicon.lucq.fun/?url=http://'+bm.urls" onerror="this.src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQEAYAAABPYyMiAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAgY0hSTQAAeiYAAICEAAD6AAAAgOgAAHUwAADqYAAAOpgAABdwnLpRPAAAAAZiS0dEAAAAAAAA+UO7fwAAAAlwSFlzAAAASAAAAEgARslrPgAAAmVJREFUSMftlG1IU2EUx8+5jRHzg+5OiD64WTBBjSY5ZybUJMaMiIUICUKFIGmoIIogYWKFBmtDxN1tBKIWvlS6SR+iLFDBXqSMgmzbvQ7fkAjcINmQ5u7pQwyCiOvWx/p/fPj9/5znnPM8AP+VolRTjjHhTlmZipzEk9GYao4sVaMYxXp62NgIdpLBCuLP05mZZHOYZA3p5KgMjBcUwCmw4PfVVYiBGmbW1zMzOU4QCgtTvdAeRESEyNZyJbx+dDTjVv/9YFSjSScnfabsbPYMF+AbxsYS3F5TfwPZAacpMNfUBEAljK24mMJwlLIUClTAdfwok9FXWBTtc3Phzqv7c/RWa8KnrHZM8VltbRjC92AxGkEPRpSJInjJQPpIBJoZC6nn50M19dM5J/v6/lhRxgXXDX9Qp1POcmHh2sTEQbfbvVmrUCTbr4RPaeMe8Q0eTyI36VkrW50mftjrPWCyDn9oSUuTdj4Y/1Qpl7NdzkNC98iIqsIxHdQVFaU8eWW1U1guLy1lr3Acr3O7pXi227EhDLpcCZ8UL/kK8LIYjxtDIRhEP8xGIlI8bcAKvY3FGB/zYvf81pYUL/0PvGTKoTUvjzbgCx5ZWlJq3a5lQa1Ge3yTWnp64ASYqVcUoXn3Jtja2/EpRtHj89FF8Tb25ufDOwCo8/lSL0BF/eDPzcVXeA7WDAa6J9rES1VVdJqx41JHBwCE8BgA3JU9B+3AACziMwjv7MAaPQb/wgIAnP2rDpCWtpkncjm+Ro34bWgo3FkX0O6bnARARCT6hSR6YzazXa7DfLyigo7TNtObxNb/s/oB7V8JFvW/8IQAAAAldEVYdGRhdGU6Y3JlYXRlADIwMjAtMDItMDJUMTg6MTE6NTgrMDg6MDCoc6tpAAAAJXRFWHRkYXRlOm1vZGlmeQAyMDIwLTAyLTAyVDE4OjExOjU4KzA4OjAw2S4T1QAAAGd0RVh0c3ZnOmJhc2UtdXJpAGZpbGU6Ly8vaG9tZS9hZG1pbi9pY29uLWZvbnQvdG1wL2ljb25fNWhyOG55Nmo1bWMvamluZ2RpYW5hbmxpX2tvbmd3dWppYW94aW5nX3Nob3VjYW5nLnN2Z4/vilwAAAAASUVORK5CYII='" alt=""  ng-show="bm.urls">


                  </div>
                  <div class="bookmark-official">{{bm.urls}}&nbsp;·&nbsp;</div><div class="bookmark-time">{{bm.createTime|changeTime}}</div>
                </div>
              </div>

            </div>

            <el-divider class="bookmark-hr"></el-divider>
          </el-col>
        </el-row>
      </div>


      <!-- 添加或修改书签管理对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">

          <el-form-item label="书签标题" prop="title">
            <el-input v-model="form.title" placeholder="请输入书签标题" />
          </el-form-item>
          <el-form-item label="书签地址" prop="url">
            <el-input v-model="form.url" placeholder="请输入书签地址" />
          </el-form-item>
          <el-form-item label="书签描述" prop="description">
            <el-input v-model="form.description" type="textarea" placeholder="请输入书签描述" run dev
                      :autosize="{minRows: 3, maxRows:4}" :style="{width: '100%'}"></el-input>
          </el-form-item>

          <el-form-item label="书签标签" prop="label">
            <el-input v-model="form.label" placeholder="请输入标签" />
          </el-form-item>
          <el-form-item label="所属目录" prop="menuId">
            <el-input v-model="form.menuId" placeholder="请选择上级目录" />
          </el-form-item>
<!--          0公开显示1隐藏显示2好友显示3稍后再看-->
          <el-form-item label="选择状态" prop="start">
            <el-input v-model="form.start" placeholder="" />
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


  import { selectBymenuIdUserID,selectBydelete,selectByUseridList, getBookmark, delBookmark, addBookmark, updateBookmark, exportBookmark } from "@/api/bookmark/bookmark";
  import { format } from 'timeago.js';

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
              showbookmark:true,
              showimg:false,
              loading:false,
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
              },
              bookmarkList:[],
              urltext:'?from=yunshuqian.com',//网址域名起推广作用
              // 表单参数
              form: {},
              // 表单校验
              rules: {
              }
            }
        },
     filters: {
      //timeago.js插件
      //计算时间，类似于几分钟前，几小时前，几天前等
      changeTime(val){
        let time = new Date(val); //先将接收到的json格式的日期数据转换成可用的js对象日期
        return format(time, 'zh_CN'); //转换成类似于几天前的格式
      }
    },
      created() {
        var that=this;
        var routedata=that.$route.query.menuId;
        if (routedata==undefined){
          that.queryParams.menuId=1;
        }else {
          that.queryParams.menuId=routedata;
        }

        if (routedata=='BOOKMARK'){
          //全部书签
          this.getBookmarkList();

        }else if(routedata=='RECYCLE'){
          //回收站
          this.getrecycleList();

        }else{
          //根据menuId查询
          this.getList();
        }

      },
    mounted(){

    },
    methods: {
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
      submitForm: function() {
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
        }).then(function() {
          return delBookmark(bookmarkIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
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
          createTime: undefined
        };
        this.resetForm("form");
      },
          /** 回收站**/
          getrecycleList() {
            this.loading = true;
            selectBydelete(this.queryParams).then(response => {
              if (response.total!=0&&response.code==200){
                this.bookmarkList = response.rows;
                this.total = response.total;
                this.loading = false;
              }else {
                this.showbookmark = false;
                this.showimg = true;
              }
            });
          },
      /** 全部书签**/
      getBookmarkList() {
        this.loading = true;
        selectByUseridList(this.queryParams).then(response => {
          if (response.total!=0&&response.code==200){
            this.bookmarkList = response.rows;
            this.total = response.total;
            this.loading = false;
          }else {
            this.showbookmark = false;
            this.showimg = true;
          }
        });
      },


          /** 查询书签管理列表 */
          getList() {
            this.loading = true;
            selectBymenuIdUserID(this.queryParams).then(response => {
              if (response.total!=0&&response.code==200){
              this.bookmarkList = response.rows;
              this.total = response.total;
              this.loading = false;
              }else {
                this.showbookmark = false;
                this.showimg = true;
              }
            });
          },
      windowurl(url,bookmarkId){
        // window.open(url+this.urltext);
        window.open(url);



      },
  },


    }
</script>



<style>
  .filler-tag span{
    font-size: 12px;
    margin-left: 5px;
    background-color: #FFFFFF;
    color: #399de0;
    margin-bottom: 4px;
  }

  .filler-tag span:hover{
    background-color: #E8F3FC;
    color: #1988E0;
  }
  .bookamrk-hr{
   margin-top: 0;
  }

  .filter-sort-dropdown{
    top:65px!important;
    box-shadow: 0 2px 15px 0 rgba(0,0,0,.2)!important;
    font-weight: 600!important;
  }
  .filter-sort-dropdown div{
    display: none!important;
  }
  .nullbookmark{
    display: flex;
    flex-flow: column;
    margin-top: 10%;
  }
  .nullbookmark-img{
    margin: 0 auto;
    width:250px;
    height: 250px;
    justify-content: center;
    align-content: center;
    background-image: url("https://s1.ax1x.com/2020/08/22/dawFp9.png") ;
    background-repeat: no-repeat;
    background-size: 100% 100%;
  }

  .nullbookmark-text{
    margin: 0 auto;
    width:250px;
    text-align: center;
    color: #D4D4D4!important;

  }
  .editBookamrk{
    width: 270px;
    height: 70px;
    position: absolute;
    background-color: #acd7ff;
    right: 0;
  }
  .editlist{
    display: flex;
    width: 100%;
    height: 70px;
    flex-flow: wrap;
    align-items: center;
  }
  .editlist div{
    margin-left:10px;
    width: 50px;
    height: 35px;
    align-content: center;

  }


</style>
