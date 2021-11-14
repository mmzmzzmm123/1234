<template>
  <div :style="imageStr">
<!--    <div :style="imageStr"></div>-->

    <!--    <div class="index-top"></div>-->

    <!--    &lt;!&ndash;github&ndash;&gt;-->
    <!--    <a @click="zreaZtree" class="github-corner" target="_blank" title="Follow me on GitHub" aria-label="Follow me on GitHub">-->
    <!--      <svg width="80" height="80" viewBox="0 0 250 250" style="fill:#008000; color:#fff; position: absolute; top: 0; border: 0; right: 0;" aria-hidden="true">-->
    <!--        <path d="M0,0 L115,115 L130,115 L142,142 L250,250 L250,0 Z"></path><path d="M128.3,109.0 C113.8,99.7 119.0,89.6 119.0,89.6 C122.0,82.7 120.5,78.6 120.5,78.6 C119.2,72.0 123.4,76.3 123.4,76.3 C127.3,80.9 125.5,87.3 125.5,87.3 C122.9,97.6 130.6,101.9 134.4,103.2" fill="currentColor" style="transform-origin: 130px 106px;" class="octo-arm"></path>-->
    <!--        <path d="M115.0,115.0 C114.9,115.1 118.7,116.5 119.8,115.4 L133.7,101.6 C136.9,99.2 139.9,98.4 142.2,98.6 C133.8,88.0 127.5,74.4 143.8,58.0 C148.5,53.4 154.0,51.2 159.7,51.0 C160.3,49.4 163.2,43.6 171.4,40.1 C171.4,40.1 176.1,42.5 178.8,56.2 C183.1,58.6 187.2,61.8 190.9,65.4 C194.5,69.0 197.7,73.2 200.1,77.6 C213.8,80.2 216.3,84.9 216.3,84.9 C212.7,93.1 206.9,96.0 205.4,96.6 C205.1,102.4 203.0,107.8 198.3,112.5 C181.9,128.9 168.3,122.5 157.7,114.1 C157.9,116.9 156.7,120.9 152.7,124.9 L141.0,136.5 C139.8,137.7 141.6,141.9 141.8,141.8 Z" fill="currentColor" class="octo-body"></path>-->
    <!--      </svg>-->
    <!--    </a>-->
    <!--    <div class="sousou">-->
    <!--&lt;!&ndash;      //搜索&ndash;&gt;-->
    <!--      <div style="width: 700px;">-->
    <!--&lt;!&ndash;        <div >&ndash;&gt;-->
    <!--&lt;!&ndash;          <div class="choice-top notcopy" v-for="iconlist in iconlist">&ndash;&gt;-->
    <!--&lt;!&ndash;            <el-tooltip  :visible-arrow="visiblearro" :enterable="enterable" class="item"  effect="dark" :content="iconlist.title" placement="top" >&ndash;&gt;-->
    <!--&lt;!&ndash;            <div @click="sendEventDataTop($event)" :data-icon="iconlist.icon"><img  :ng-src="'https://favicon.lucq.fun/?url='+iconlist.icon" err-src="./images/default.ico"  ng-click="detailBookmark(bookmark);$event.stopPropagation()" :src="'https://favicon.lucq.fun/?url='+iconlist.icon"  ></div>&ndash;&gt;-->
    <!--&lt;!&ndash;            </el-tooltip>&ndash;&gt;-->
    <!--&lt;!&ndash;          </div>&ndash;&gt;-->

    <!--&lt;!&ndash;        </div>&ndash;&gt;-->

    <!--        <div style="margin-top: 15px;">-->
    <!--          <el-input placeholder="百度一下" v-model="sousou" class="sousoucss notcopy" ref="sousouref">-->
    <!--            <img @click="IsCord()"  slot="prefix"  :src="sousouicon" style="width: 20px;height: 20px;position: absolute;top: 50%;margin-top: -10px;">-->
    <!--             <el-button  type="primary" slot="append" icon="el-icon-search" style="width: 80px"  @click="getUrl"></el-button>-->
    <!--          </el-input>-->
    <!--        </div>-->

    <!--        <transition name="el-zoom-in-top">-->
    <!--            <el-card class="box-card notcopy" v-show="isSousouCard" style="width: 100%;opacity: 0.7;margin-top: 10px;height:250px;overflow:auto">-->

    <!--              <div class="choice" v-for="iconlist in iconlist" >-->
    <!--            <div  @click="sendEventData($event)" :data-icon="iconlist.icon"><img err-src="https://favicon.lucq.fun/?url=https://www.5118.com/"  class="sousouicon"  :ng-src="'https://favicon.lucq.fun/?url='+iconlist.icon"  :src="'https://favicon.lucq.fun/?url='+iconlist.icon"  ><span>{{iconlist.title}}</span></div>-->
    <!--              </div>-->
    <!--            </el-card>-->

    <!--        </transition>-->
    <!--      </div>-->
    <!--    </div>-->


    <el-container style="" class="notCopyText">
      <el-header height="55px" class="header-top">
        <div class="top-icon-list">
          <i class="el-icon-setting"></i>
          <i class="el-icon-sunrise-1"></i>
          <i class="el-icon-moon-night"></i>
        </div>

      </el-header>
      <el-header height="200px" class="header-below">


        <!--        <el-input v-model="input" placeholder="请输入内容"></el-input>-->
        <div style="min-width: 500px;margin-top: 80px">
          <el-input placeholder="百度一下" v-model="sousou" class="sousoucss notcopy " ref="sousouref">
            <img @click="IsCord()" slot="prefix" :src="sousouicon"
                 style="width: 20px;height: 20px;position: absolute;top: 50%;margin-top: -10px;">
            <!--            <el-button  type="primary" slot="append" icon="el-icon-search" style="width: 80px"  @click="getUrl"></el-button>-->
          </el-input>
        </div>

      </el-header>
      <el-container style=" padding-top: 15px"  >

        <!-- 左侧-->
        <el-aside class="mains-left mbl" width="400px" >
          <div class="label-title">
            <div class="title">
              <el-dropdown trigger="click">
              <span class="el-dropdown-link menu-list" >
                最新收藏<i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item icon="el-icon-star-off">我的星标</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-reading">稍后再读</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>

            <div class="status">
              <span>最新</span>
              <el-divider direction="vertical"></el-divider>
              <span>热度</span>
            </div>
          </div>
          <el-divider class="mians-hr"></el-divider>
          <div class="contenList" v-infinite-scroll="load" style="overflow:auto">
            <div v-for="o in count" :key="o" class="bookmarContext item">

              <div class="bookmar-title text-lenght">
                {{'阿达瓦发我的发我法师法师法师法师法挖的挖的挖的挖的挖的挖的挖列表内容 ' + o }}
              </div>
              <div class="bookmark-tag-Time">
                <div class="b-left text-lenght">#java #php 我是个簡介</div>
                <div class="b-center">22小时前</div>
                <div class="b-right">
                  <i class="el-icon-view">22</i>
                </div>

              </div>

            </div>
          </div>
        </el-aside>



      <!-- 中间-->
        <el-main class="mains mbl" width="500px" >
          <div class="mains-title">
            <div class="title">
              <el-dropdown trigger="click">
              <span class="el-dropdown-link menu-list" >
               常用导航<i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item icon="el-icon-star-off">我的星标</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-reading">稍后再读</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>

            </div>
            <div class="status">
              <span>最新</span>
              <el-divider direction="vertical"></el-divider>
              <span>热度</span>
            </div>
          </div>
          <el-divider class="mians-hr"></el-divider>
          <div class="contenList" v-infinite-scroll="load" style="overflow:auto;">
            <el-row :gutter="10">
              <draggable
                class="dragArea list-group"
                :list="list1"
                :group="people"
                @change="log"
              >

              <el-col  :xs="12" :sm="4" v-for="item in iconlist" :key="item.id" class="bookmarkUrl urlWd " @click.native="urlOpen(item.icon)">
                <div class="bookmarkImg">
                <img err-src="https://favicon.lucq.fun/?url=https://www.5118.com/" class="sousouicon"
                     :ng-src="'https://favicon.lucq.fun/?url='+item.icon"
                     :src="'https://favicon.lucq.fun/?url='+item.icon" />
                </div>
                <div class="bookmarkTitle">

                  <div class="titles">{{item.title }}</div>
                  <div class="desc text-lenght">我是个简介我是个简介我是个简介我是个简介我是个简介我是个简介我是个简介我是个简介我是个简介我是个简介我是个简介</div>



                </div>

              </el-col>

              </draggable>
            </el-row>
          </div>
        </el-main>


        <!-- 右侧-->
        <el-aside class="mains-right mbl" width="400px">
          <div class="label-title">
            <div class="title">
              <el-dropdown trigger="click">
              <span class="el-dropdown-link menu-list" >
                发现推荐<i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item icon="el-icon-star-off">我的星标</el-dropdown-item>
                  <el-dropdown-item icon="el-icon-reading">稍后再读</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>

            <div class="status">
              <span>最新</span>
              <el-divider direction="vertical"></el-divider>
              <span>热度</span>
            </div>
          </div>
          <el-divider class="mians-hr"></el-divider>
          <div class="contenList" v-infinite-scroll="load" style="overflow:auto">
            <div v-for="o in count" :key="o" class="bookmarContext item">

              <div class="bookmar-title text-lenght">
                {{'阿达瓦发我的发我法师法师法师法师法挖的挖的挖的挖的挖的挖的挖列表内容 ' + o }}
              </div>
              <div class="bookmark-tag-Time">
                <div class="b-left text-lenght">#java #php 我是个簡介</div>
                <div class="b-center">22小时前</div>
                <div class="b-right">
                  <i class="el-icon-view">22</i>
                </div>

              </div>

            </div>
          </div>
        </el-aside>

      </el-container>
    </el-container>

  </div>
</template>


<script>
  import draggable from "vuedraggable";
  export default {
    components: {draggable},
    data: function () {
      return {
        count: 15,
        enterable: false,
        visiblearro: false,//提示箭头
        curMenu: null,
        sousou: '百度一下',//搜索文字
        zTree_Menu: null,
        isSousouCard: false,//切换搜索源
        iconurl: 'https://favicon.lucq.fun/?url=',
        sousouicon: 'https://favicon.lucq.fun/?url=https://www.baidu.com/',//默认搜索图片
        sousou: '',
        sousouVlaue: '百度一下',
        imgSrc: 'http://img.netbian.com/file/2020/0904/de2f77ed1090735b441ba5e4c2b460ca.jpg',
        iconlist: [
          {iconID: "1", icon: "https://www.baidu.com", title: "百度"},
          {iconID: "2", icon: "https://www.google.com/", title: "谷歌搜索引擎"},
          {iconID: "3", icon: "https://www.so.com/", title: "搜索"},
          {iconID: "4", icon: "https://www.sogou.com/", title: "搜狗"},
          {iconID: "5", icon: "https://cn.bing.com/", title: "必应"},
          {iconID: "6", icon: "https://yz.m.sm.cn/", title: "神马搜索"},
          {iconID: "7", icon: "https://www.jd.com/", title: "京东"},
          {iconID: "8", icon: "https://taobao.com/", title: "淘宝"},
          {iconID: "7", icon: "https://y.qq.com", title: "QQ音乐"},
          {iconID: "7", icon: "https://weibo.com/", title: "微博"},
          {iconID: "7", icon: "http://www.dianping.com/", title: "大众点评"},
          {iconID: "7", icon: "https://mail.sina.com.cn/", title: "新浪邮箱"},
          {iconID: "7", icon: "http://caiyunapp.com", title: "彩云天气"},
          {iconID: "7", icon: "https://www.yhd.com/", title: "一号店"},
          {iconID: "7", icon: "https://www.mi.com/", title: "小米"},
          {iconID: "7", icon: "https://www.tianyancha.com/", title: "天眼查"},
          {iconID: "7", icon: "https://translate.google.com/", title: "谷歌翻译"},
          {iconID: "7", icon: "https://open.163.com/", title: "网易公开课"},
          {iconID: "7", icon: "http://mooc.org/", title: "MOCC"},
          {iconID: "7", icon: "https://www.w3school.com.cn/", title: "w3school"},
          {iconID: "7", icon: "https://segmentfault.com/", title: "segmentfault"},
          {iconID: "7", icon: "https://www.y-english.org/", title: "云英语"},
          {iconID: "7", icon: "https://www.tutorialspoint.com/", title: "Tutorials Point"},
          {iconID: "7", icon: "https://github.com/", title: "GitHub"},
          {iconID: "7", icon: "https://mail.sina.com.cn/", title: "新浪邮箱"},
          {iconID: "7", icon: "https://movie.douban.com/", title: "豆瓣电影"},
        ],
        imageStr: {
          backgroundImage: 'url(https://img-operation.csdnimg.cn/csdn/silkroad/img/1633686296539.jpg)',
          width: '100%',
          height: '100%',
          backgroundRepeat: 'no-repeat',
          backgroundSize: '100% 100%',
        },
        imageStrOne: {
          backgroundColor: '#D4D4D4',
          width: '100%',
          height: '100%',
          backgroundRepeat: 'no-repeat',
          backgroundSize: '100% 100%',
        }
      }
    },
    methods: {
      urlOpen(url){
        console.log(url)
        window.open(url);
      },
      load() {
        if(this.count >=40){
          return;
        }
        this.count += 5
      },
      //显示隐藏 数据源
      IsCord: function () {
        var that = this;
        that.isSousouCard = !that.isSousouCard;
      },
      //切换搜索源
      sendEventData: function (ev) {
        var that = this;
        var node = ev.currentTarget;
        that.sousouicon = that.iconurl + node.dataset.icon;
        that.isSousouCard = !that.isSousouCard;
        that.$refs.sousouref.focus()
      },
      sendEventDataTop: function (ev) {
        var that = this;
        var node = ev.currentTarget;
        that.sousouicon = that.iconurl + node.dataset.icon;
        that.$refs.sousouref.focus()

      },


      //新打开新窗口
      getUrl: function () {
        var that = this;
        if (that.sousou != null && that.sousou != undefined && that.sousou != '') {
          var url = that.sousouicon + that.sousou;
          window.open(url);
          //window.location.href=url;
        } else {
          // that.$notify({
          //   title:'提示',
          //   type: 'success',
          //   message: '搜了个寂寞,内容不能为空!',
          // });
          that.$refs.sousouref.focus()
        }

      }
    },
    mounted() {
      var that = this;
      //光标自动选中
      that.$refs.sousouref.focus()


    },

  }
</script>
<style scoped>
  .bookmarkUrl {
    margin-bottom: 10px;
    margin-left: 11px;
    height: 70px;
    border-radius: 4px;
    line-height: 50px;
    border: 1px solid rgba(255,255,255,0.2);
    display: flex;
    padding-top: 10px;
  }
  .bookmarkImg{
    width: 40px;
    height: 40px;
    /*border: 1px solid red;*/
    line-height: 30px;
    float: left;
    border-radius: 50%;
    background:rgba(255,255,255,0.2);
    margin-right: 3px;
    margin-top: 4px;
  }
  .bookmarkImg img{
    margin-left: 10px;
    margin-top: 10px;
    width: 20px;
    height: 20px;

  }
  .bookmarkTitle{
    width: 70%;
  }
  .bookmarkTitle .titles{

    font-size: 12px;
    height: 25px;
    line-height: 25px;

  }
  .bookmarkTitle .desc{
    height: 25px;
    line-height: 25px;
  }


  .bookmarkUrl:hover{
    background:rgba(255,255,255,0.2);
  }
  .bookmarContext{
    border-radius: 2px;
    padding: 7px;
  }
  .bookmarContext:hover{
    background:rgba(255,255,255,0.4);
}

  .el-divider{
    background: rgba(255,255,255,0.2);
}







  .mains-left {
    margin-left: 10px;
    padding: 7px 7px;
    /*background-color:transparent;*/

  }

  .mains-right {
    margin-right: 10px;
    padding: 7px 7px;
  }

  .header-top {
    /*background-color: #1e80ff;*/
  }

  .header-below {
    min-width: 800px;
    display: flex;
    justify-content: center;
    align-content: center;
    /*background-color: #1e80ff;*/
  }

  .label-title {
    display: flex;
    flex-direction: row;
    height: 35px;
    font-size: 14px;
    align-items: center;
  }

  .mains-title .status {
    position: absolute;
    right: 0;
    margin-right: 11px;


  }

  .mains {
    padding: 7px 7px;
    margin-left: 10px;
    margin-right: 10px;

  }

  .mains-title {
    font-size: 14px;
    display: flex;
    flex-direction: row;
    height: 35px;
    font-size: 14px;
    align-items: center;
  }

  .title {
    width: 400px;
    float: left;
  }
  .menu-list{
    width: 100px;
    display: block;
    text-align: center;
    height: 30px;
    line-height: 30px;
    /*background-color: #E8F3FF;*/
    background:rgba(255,255,255,0.2);
    color: #5289FF;
  }

  .label-title .status {
    width: 120px;
    float: right;
  }

  .contenList {
    font-size: 14px;
    height: 580px;
    padding-bottom: 150px;
  }

  .mians-hr {
    margin-top: 2px;
    margin-bottom: 8px;
  }
  .top-icon-list{
    color: #ffffff;
    float: right;
    width: 140px;
    font-size: 30px;
    line-height: 55px;
    opacity: 0.7;
  }
  .top-icon-list i{
    margin-left: 10px;
  }

  .bookmar-title{
    width: 100%;
    height: 30px;
  }


  .bookmark-tag-Time{
    width: 100%;
    display: flex;
    height: 30px;
    color: #50565c;
  }
  .bookmark-tag-Time .b-right{

  }

  .bookmar-title{
    font-size: 15px;
    font-weight: 500;
  }

  .b-left{

  }



</style>
<style>
  /*文本超长变成点*/
  .text-lenght{
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
  }
  /*禁止复制*/
  .notCopyText{
    -moz-user-select: none; /* Firefox私有属性 */
    -webkit-user-select: none; /* WebKit内核私有属性 */
    -ms-user-select: none; /* IE私有属性(IE10及以后) */
    -khtml-user-select: none; /* KHTML内核私有属性 */
    -o-user-select: none; /* Opera私有属性 */
    user-select: none; /* CSS3属性 */
  }
  /*毛玻璃*/
  .maoboli {
    background: hsla(0, 0%, 100%, 0.11) border-box;
    overflow: hidden;
    box-shadow: 0 0 0 1px hsla(0, 0%, 100%, 0.10) inset, 0 .3em .3em rgba(0, 0, 0, 0.15);
    text-shadow: 0 1px 1px hsla(0, 0%, 100%, .3);
  }

  .maoboli::before {
    content: '';
    z-index: -1;
    -webkit-filter: blur(6.4px);
    filter: blur(10px);
  }
  .mbl{
    background:rgba(255,255,255,0.2);
    backdrop-filter: blur(3px);
    -webkit-backdrop-filter: blur(3px);
  }
</style>
<style scoped>


  @media (min-width: 1800px) {
    .urlWd {
      width: 14.8% !important;
    }
  }

  @media screen and (min-width: 1500px) and (max-width: 1800px) {
    .urlWd {
      width: 23% !important;
    }
  }

  @media screen and (min-width: 1400px) and (max-width: 1499px) {
    .urlWd {
      width: 30% !important;
    }
  }
  @media screen and (min-width: 1200px) and (max-width: 1399px) {
    .urlWd {
      width: 30% !important;
    }
  }


  @media (max-width: 1200px) {
    .urlWd {
      width: 95% !important;
    }
  }
</style>
<style scoped>


  .main {
    /*font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;*/
    /*display: flex;*/
    /*justify-content: center;*/
    /*!*align-items: center;*!*/
    /*padding-top: 20%;*/
    /*height: 100%;*/
    /*background-color: #f7f7f7;*/
    /*background-image: url("https://s1.ax1x.com/2020/08/11/aOHstP.jpg");*/
    /*background-size: cover;*/
  }

  /*.sousou{*/
  /*  display: flex;*/
  /*  flex-direction: row;  !* 子元素横向排列 *!*/
  /*  justify-content: center; !* 相对父元素水平居中 *!*/
  /*  align-items: center; !*  子元素相对父元素垂直居中 *!*/
  /*}*/

  /*.sousoucss{*/
  /*  opacity: 0.6;*/
  /*}*/
  /*.sousoucss input{*/
  /*  border:0;*/
  /*}*/

  /*.sousoucss button:hover{*/
  /*  color: #1c84c6;*/
  /*}*/
  /*.el-input-group__append{*/
  /*  background-color: #1f2d3d!important;*/
  /*  opacity: 0.7;*/
  /*}*/


  .choice {
    margin: 5px 5px 5px 5px;
    background:rgba(255,255,255,0.2);
  }

  .choice div {
    float: left;
    height: 30px;
    line-height: 30px;
    border-radius: 5px;
    text-align: center;
    margin: 5px 5px 5px 5px;
    /*box-shadow:0 0 1px #000 ;*/
    background: none #fff;
    border: 1px solid rgba(34, 36, 38, .15);
    color: rgba(0, 0, 0, .87);
    -webkit-box-shadow: none;
    box-shadow: none;
    font-size: 14px;
    font-weight: 700;
    /*font-family: Lato,'Helvetica Neue',Arial,Helvetica,sans-serif;*/
    align-items: center;
    vertical-align: middle;
    width: fit-content;
    /*width:-webkit-fit-content;*/
    width: -moz-fit-content;
    padding-left: 12px;
    padding-right: 12px;
  }

  .choice div i {
    margin-right: 1px;
  }

  .choice div:hover {
    color: #1f1f1f;

  }

  /**顶部*/
  .index-top {
    width: 100%;
    height: 50px;
    background-color: #3c5d5b;
    position: absolute;
    top: 0;
    opacity: 0.2;

  }

  /*搜索 */
  .sousouicon {
    width: 16px;
    height: 16px;
    vertical-align: middle;
    margin-right: 3px;
    margin-bottom: 3px;

  }

  /*搜索top 上面的*/
  .choice-top img {
    width: 16px;
    height: 16px;
    vertical-align: middle;
    margin-bottom: 3px;
  }

  .choice-top div {
    float: left;
    height: 30px;
    line-height: 30px;
    border-radius: 5px;
    text-align: center;
    margin: 5px 5px 5px 5px;
    background-color: #3c5d5b;
    border: 1px solid rgba(34, 36, 38, .15);
    padding-left: 12px;
    padding-right: 12px;
    opacity: 0.8;

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
    /*background-color: #fff;*/
    background:rgba(255,255,255,0.2);
    -webkit-border-radius: 2em;
    -moz-border-radius: 2em;
    border-radius: 2em;
  }

  /*滚动条的设置*/

  ::-webkit-scrollbar-thumb {
    /*background-color: #606d71;*/
    background: rgba(218, 219, 222, 0.6);
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


  /*禁止复制*/
  .notcopy {
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -khtml-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
  }

</style>

