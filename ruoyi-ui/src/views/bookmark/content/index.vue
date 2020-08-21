

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
      <div  class="bookmarklist" v-loading="loading">

        <el-row >

          <hr class="bookamrk-hr"/>
          <el-col :span="24" v-for="bm in bookmarkList">

            <div class="bookmark">
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





    </div>
</template>

<script>


  import { selectBymenuIdUserID, getBookmark, delBookmark, addBookmark, updateBookmark, exportBookmark } from "@/api/bookmark/bookmark";
  import { format } from 'timeago.js';

  export default {
        components: {
          format
        },

        data: function () {
            return {
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
        if (that.$route.query.menuId==undefined){
          that.queryParams.menuId=1;
        }else {
          that.queryParams.menuId=that.$route.query.menuId;
        }
        this.getList();
      },
    mounted(){

    },
    methods: {
          /** 查询书签管理列表 */
          getList() {
            this.loading = true;
            selectBymenuIdUserID(this.queryParams).then(response => {
              this.bookmarkList = response.rows;
              this.total = response.total;
              this.loading = false;
            });
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

</style>
