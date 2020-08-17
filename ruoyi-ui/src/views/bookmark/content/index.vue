

<template>
    <div>

      <div class="filter" >
        <div class="filter-sort">
          <el-dropdown trigger="click" size="small">
              <span class="el-dropdown-link">
            <span>按时间排序(正)</span> <i class="el-icon-caret-bottom"></i>
              </span>
            <el-dropdown-menu slot="dropdown" >
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
      <div  class="bookmarklist">

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
                    <img :err-src='bm.icon'   :ng-src="bm.icon"  :src="bm.icon"  />
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
              loading:true,
              bookmark:[
                {id:1,title:"最大的骄傲于最大的自卑都表示心灵的最软弱无力。——斯宾诺莎",description:"阅读使人充实，会谈使人敏捷，写作使人精确。——培根",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
                {id:1,title:"意志坚强的人能把世界放在手中像泥块一样任意揉捏。——歌德",description:"最具挑战性的挑战莫过于提升自我。——迈克尔·F·斯特利",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
                {id:1,title:"要知道对好事的称颂过于夸大，也会招来人们的反感轻蔑和嫉妒。——培根",description:"意志命运往往背道而驰，决心到最后会全部推倒。——莎士比亚",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
                {id:1,title:"要使整个人生都过得舒适、愉快，这是不可能的，因为人类必须具备一种能应付逆境的态度。——卢梭",description:"只有把抱怨环境的心情，化为上进的力量，才是成功的保证。——罗曼·罗兰",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
                {id:1,title:"希望的灯一旦熄灭，生活刹那间变成了一片黑暗。——普列姆昌德",description:"到很多东西的诀窍，就是一下子不要学很多。——洛克",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
                {id:1,title:"我这个人走得很慢，但是我从不后退。——亚伯拉罕·林肯",description:"重复别人所说的话，只需要教育；而要挑战别人所说的话，则需要头脑。——玛丽·佩蒂博恩·普尔",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
                {id:1,title:"我们若已接受最坏的，就再没有什么损失。——卡耐基",description:"书籍把我们引入最美好的社会，使我们认识各个时代的伟大智者。——史美尔斯",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},
                {id:1,title:"我的努力求学没有得到别的好处，只不过是愈来愈发觉自己的无知。——笛卡儿",description:"少而好学，如日出之阳；壮而好学，如日中之光；志而好学，如炳烛之光。——刘向",official:"www.baidu.com",time:"2020-10-08",icon:"https://favicon.lucq.fun/?url=https://www.sogou.com/"},

              ],
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
         this.queryParams.menuId=1;
         this.getList();
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

</style>
