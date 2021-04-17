<template>
  <div>
<!--    STYLE="position: relative"-->
    <div v-for="bm in bookmarkList"  class="bookmark"    @click="winurl(bm.noteId,bm.tiymceUeditor,bm.bookmarkId,bm.url)" @mouseover="enter(bm.bookmarkId)" @mouseleave="leave()" >
      <div class="bookmark-item" >
         <span class="bookmark-title" v-if="highlighted" v-html="highLight(bm.title,sousuo)"/>

        <span class="bookmark-title" v-if="!highlighted">{{bm.title}}</span>
        <div class="bookmark-time" v-if="noteTime">{{bm.createTime|changeTime}}</div>
      </div>



      <div class="bookmark-description" v-if="isdescription" >

        <span v-if="highlighted" v-html="highLight(bm.description,sousuo)"></span>

        <span v-if="!highlighted">{{bm.description}}</span>
      </div>



      <div class="info-wrap" v-if="isBookmarkIcon">
        <div class="info">
          <div class="bookmark-icon">
            <img :ng-src="'https://favicon.lucq.fun/?url=http://'+bm.urls"
                 :src="'https://favicon.lucq.fun/?url=http://'+bm.urls"
                 onerror="this.src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQEAYAAABPYyMiAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAgY0hSTQAAeiYAAICEAAD6AAAAgOgAAHUwAADqYAAAOpgAABdwnLpRPAAAAAZiS0dEAAAAAAAA+UO7fwAAAAlwSFlzAAAASAAAAEgARslrPgAAAmVJREFUSMftlG1IU2EUx8+5jRHzg+5OiD64WTBBjSY5ZybUJMaMiIUICUKFIGmoIIogYWKFBmtDxN1tBKIWvlS6SR+iLFDBXqSMgmzbvQ7fkAjcINmQ5u7pQwyCiOvWx/p/fPj9/5znnPM8AP+VolRTjjHhTlmZipzEk9GYao4sVaMYxXp62NgIdpLBCuLP05mZZHOYZA3p5KgMjBcUwCmw4PfVVYiBGmbW1zMzOU4QCgtTvdAeRESEyNZyJbx+dDTjVv/9YFSjSScnfabsbPYMF+AbxsYS3F5TfwPZAacpMNfUBEAljK24mMJwlLIUClTAdfwok9FXWBTtc3Phzqv7c/RWa8KnrHZM8VltbRjC92AxGkEPRpSJInjJQPpIBJoZC6nn50M19dM5J/v6/lhRxgXXDX9Qp1POcmHh2sTEQbfbvVmrUCTbr4RPaeMe8Q0eTyI36VkrW50mftjrPWCyDn9oSUuTdj4Y/1Qpl7NdzkNC98iIqsIxHdQVFaU8eWW1U1guLy1lr3Acr3O7pXi227EhDLpcCZ8UL/kK8LIYjxtDIRhEP8xGIlI8bcAKvY3FGB/zYvf81pYUL/0PvGTKoTUvjzbgCx5ZWlJq3a5lQa1Ge3yTWnp64ASYqVcUoXn3Jtja2/EpRtHj89FF8Tb25ufDOwCo8/lSL0BF/eDPzcVXeA7WDAa6J9rES1VVdJqx41JHBwCE8BgA3JU9B+3AACziMwjv7MAaPQb/wgIAnP2rDpCWtpkncjm+Ro34bWgo3FkX0O6bnARARCT6hSR6YzazXa7DfLyigo7TNtObxNb/s/oB7V8JFvW/8IQAAAAldEVYdGRhdGU6Y3JlYXRlADIwMjAtMDItMDJUMTg6MTE6NTgrMDg6MDCoc6tpAAAAJXRFWHRkYXRlOm1vZGlmeQAyMDIwLTAyLTAyVDE4OjExOjU4KzA4OjAw2S4T1QAAAGd0RVh0c3ZnOmJhc2UtdXJpAGZpbGU6Ly8vaG9tZS9hZG1pbi9pY29uLWZvbnQvdG1wL2ljb25fNWhyOG55Nmo1bWMvamluZ2RpYW5hbmxpX2tvbmd3dWppYW94aW5nX3Nob3VjYW5nLnN2Z4/vilwAAAAASUVORK5CYII='"
                 alt="" ng-show="bm.urls">
          </div>
          <div class="bookmark-official">{{bm.urls}}&nbsp;·&nbsp;</div>
          <div class="bookmark-time">{{bm.createTime|changeTime}}</div>
          <div class="bookmark-time">{{bm.bookmarkStar}}</div>
          <div class="bookmark-time" v-for="t in bm.sqTags" v-if="false">
<!--            <el-tag class="bookmark-list-tag" data-bookmarkId="t.bookmarkId" data-tagId="t.tagId"-->
<!--                    size="mini">-->
<!--              {{t.name}}-->
<!--            </el-tag>-->
          </div>
        </div>
      </div>

      <!--编辑  -->
      <div class="editAllBookMark"  v-show="seen&&bm.bookmarkId==current">
          <el-button slot="reference" @click.stop="updateStarById(bm.bookmarkId,bm.bookmarkStar)"  type="info" v-bind:class="{ activeClass: bm.bookmarkStar ==1  }"   plain size="mini" icon="el-icon-star-off"></el-button>
          <el-button type="info" plain size="mini" icon="el-icon-share" ></el-button>
          <el-button type="info" plain size="mini" icon="el-icon-edit" @click.stop="handleUpdate(bm.bookmarkId)"></el-button>
          <el-button type="danger" plain size="mini" icon="el-icon-delete" @click.stop="handleDelete(bm.bookmarkId)"></el-button>
          <div style="width: 10px"></div>
      </div>
    </div>

  </div>
</template>
<script>
  import {format} from 'timeago.js';
  import {updateBookmarkStarById} from '@/api/bookmark/bookmark';

  export default {
    components: {format,updateBookmarkStarById},
    props: {
      bookmarkList: Array,
      property: null,
      highlighted: null,//搜索是否高亮
      sousuo:null,
    },
    data: function () {
      return {
        isdescription:false,
        noteTime:true,
        isBookmarkIcon:false,
        Ueditor:undefined,
        seen:false,
        current:0,
      }
    },
    mounted(){
      this.showView(this.property);
    },
    updated(){
      // this.showView(this.property);
     },
    created() {
      // var that=this;
      // //便签ID
      // that.Ueditor = that.$route.query.Ueditor;

    },

    filters: {
      //timeago.js插件
      //计算时间，类似于几分钟前，几小时前，几天前等
      changeTime(val) {
        let time = new Date(val); //先将接收到的json格式的日期数据转换成可用的js对象日期
        return format(time, 'zh_CN'); //转换成类似于几天前的格式
      },
    },

    methods: {
      /**打开书签**/
      winurl:function(noteId,tiymceueditor,bookmarkId,url) {
       this.$emit('on-windowurl', noteId, tiymceueditor,bookmarkId,url);
      },
      /**编辑书签**/
      handleUpdate:function(bookmarkId) {
        this.$emit('on-handleUpdate', bookmarkId);
      },
      /**删除书签**/
      handleDelete:function(bookmarkId) {
      this.$emit('on-handleDelete', bookmarkId);
      },
      /** 星标 **/
      updateStarById:function(bookmarkId,bookmarkStar){
        console.log("bookmarkStar:"+bookmarkStar)
        var param={
          bookmarkId:bookmarkId,
          bookmarkStr:bookmarkStar==0?1:0
        }
        console.log("bookmarkStar2:"+param.bookmarkStar)
        updateBookmarkStarById(param).then(response => {
          if (response.code === 200) {
            this.msgSuccess("设置成功");
          }
        });
      },
      /**渲染模式**/
      showView(e) {
        var that=this;
        switch (e) {
          case 0:
            //网页模式
            that.isdescription = true;
            that.noteTime = false;
            that.isBookmarkIcon = true;
            break;
          case 1:
            //便签模式
            that.isdescription = false;
            that.noteTime = true;
            that.isBookmarkIcon = false;
            break;
          default:

        }

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
          if (msg.toLowerCase().indexOf(highLightStr) !== -1) {
            assemblyStr(msg, highLightStr)

          } else {
            htmlStr = '<span>' + msg + '</span>'
          }
        } else {
          htmlStr = '<span>' + msg + '</span>'
        }

        function assemblyStr(msgAssembly, highLightAssembly) {
          var isend=highLightAssembly.length;
          if (msgAssembly.toLowerCase().indexOf(highLightAssembly) !== -1) {
            var length = highLightAssembly.length

            var start = msgAssembly.toLowerCase().indexOf(highLightAssembly)
            htmlStr = htmlStr + '<span>' + msgAssembly.substring(0, start) + '</span>' + '<span style="color:red;">' + msgAssembly.substring(start, start+isend) + '</span>'
            msgAssembly = msgAssembly.substring(start + length, msgAssembly.length)
            assemblyStr(msgAssembly, highLightAssembly)
          } else {
            htmlStr = htmlStr + '<span>' + msgAssembly + '</span>'
          }
        }

        return htmlStr;
      },
      /**搜索高亮 结束**/

      enter(index){
        this.seen = true;
        this.current = index;
      },
      leave(){
        this.seen = false;
        this.current = null;
      },


      /**编辑**/


    }
  }
</script>
<style scoped>
  .activeClass{
    color: red;
  }

  .bookmark-item {
    display: flex;
    height: 24px;
    overflow: hidden;
  }

  .bookmark {
    position: relative;
    padding: 15px 20px;
    border-top: 1px solid #e0e1e5;
    overflow: hidden;
    line-height: 24px;
  }

  .bookmark:hover {
    background-color: #E8E8E8;

  }

  .title-icon img {
    width: 15px;
    height: 15px;
  }


  .bookmark-title {
    display: inline-block;
    color: #393939;
    flex: 1;
    -webkit-flex: 1;
    white-space: nowrap;
    text-overflow: ellipsis;
    -webkit-text-overflow: ellipsis;
    overflow: hidden;
    position: relative;
    font-size: 1.05rem;
    font-weight: 500;
  }

  .bookmark-time {
    display: block;
    color: #c2c2c2;
    margin-right: 10px;
  }
  .bookmark-description{
    font-size: 0.95rem;
    overflow: hidden;
    color: #b1b1b1;
    text-overflow:ellipsis;
    display:-webkit-box;
    -webkit-box-orient:vertical;
    -webkit-line-clamp:2;
  }
  .bookmark-official {
    float: left;
  }

  .info {
    font-size: 0.95rem;
    display: flex;
    align-items: center;
  }

  .bookmark-icon {
    display: flex;
    float: left;
    margin-right: 5px;
  }

  .bookmark-icon img {
    width: 15px;
    height: 15px;
  }

  .editAllBookMark{
    width: 200px;
    height: inherit;
    position: absolute;
    right: 0px;
    top: 10px;
    bottom: 10px;
    display:inline-flex;
    justify-content:flex-end;
    align-items:center;
  }

</style>
