<template>
  <div>


    <div v-for="bm in bookmarkList" class="bookmark"   @click="winurl(bm.noteId,bm.tiymceUeditor,bm.bookmarkId,bm.url)">
      <div class="bookmark-item">

        <span class="bookmark-title" >{{bm.title}}</span>
        <div class="bookmark-time" v-if="noteTime">{{bm.createTime|changeTime}}</div>
      </div>
      <div class="bookmark-description" v-if="isdescription">
        <span >{{bm.description}}</span>
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
          <div class="bookmark-time" v-for="t in bm.sqTags" v-if="false">
            <el-tag class="bookmark-list-tag" data-bookmarkId="t.bookmarkId" data-tagId="t.tagId"
                    size="mini">
              {{t.name}}
            </el-tag>
          </div>
        </div>
      </div>


    </div>

  </div>
</template>
<script>
  import {format} from 'timeago.js';

  export default {
    components: {format},
    props: {
      bookmarkList: Array,
      property: null,
    },
    data: function () {
      return {
        isdescription:false,
        noteTime:true,
        isBookmarkIcon:false,
        Ueditor:undefined,
      }
    },
    mounted(){
      this.showView(this.property);
    },
    updated(){
      this.showView(this.property);
     },
    created() {
      var that=this;
      //便签ID
      that.Ueditor = that.$route.query.Ueditor;

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
      winurl:function(noteId,tiymceueditor,bookmarkId,url) {

       this.$emit('on-windowurl', noteId, tiymceueditor,bookmarkId,url);
      }
      ,
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
            that.isdescription = true;
            that.noteTime = true;
            that.isBookmarkIcon = false;
            break;
          default:

        }

      }

    }
  }
</script>
<style scoped>

  .bookmark-item {
    display: flex;
    height: 24px;
    font-size: 13px;
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
    background-color: #D4D4D4;

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
  }

  .bookmark-time {
    display: block;
    color: #c2c2c2;
    margin-right: 10px;
  }
  .bookmark-description{
    font-size: 10px;
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
    font-size: 12px;
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

</style>
