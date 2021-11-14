<template>
  <div>





<!--    <div class="head-tag">-->
<!--      <div class="mdui-btn-group head-tag-button">-->
<!--&lt;!&ndash;        <div  :class="['classification',property=='0'?' classification-click':'']" @click="showopen(0)"><span>网页</span></div>&ndash;&gt;-->
<!--        <button @click="goRouter(1)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='1'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">个人中心</button>-->
<!--&lt;!&ndash;        <button @click="goRouter(7)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='7'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">外观设置</button>&ndash;&gt;-->
<!--        <button @click="goRouter(2)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='2'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">系统设置</button>-->
<!--        <button @click="goRouter(3)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='3'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">导入书签</button>-->
<!--        <button @click="goRouter(4)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='4'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">备份导出</button>-->
<!--        <button @click="goRouter(5)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='5'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">更新日志</button>-->
<!--        <button @click="goRouter(6)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='6'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">关于我们</button>-->


<!--      </div>-->
<!--    </div>-->



<!--        <bkindex></bkindex>-->











    <el-container Style="background-color: #f7f7f7">
      <el-aside  class="aside-left">

        <div class="goIndex">
          <el-page-header @back="goRouter(9)" content="">
          </el-page-header>
        </div>


        <div  class="main-top user">
          <div style="width: 70px">
            <el-avatar :size="45" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
          </div>
          <div class="user-name-account">
            <span class="userName">王先生</span>
            <span class="userAccount">43278047</span>
          </div>
      </div>

        <div class="titleAll">

          <div class="title-top">
            <span>设置</span>
          </div>

          <div class="title" @click="goRouter(itme.id)" :class="[property==itme.id ? ' getClass':'']" v-for="itme in list">

            <span v-show="property==itme.id" style="width: 3px;height: 19px;background-color: #0072c9;margin-left: 3px"></span>
            <span v-show="property!=itme.id" style="width: 3px;height: 19px;margin-left: 3px"></span>

           <i :class="itme.icon"></i>
            <span>{{itme.name}}</span>
          </div>

<!--          <div class="title"  @click="goRouter(3)" :class="[property=='3' ? ' getClass':'']">-->
<!--            <span v-show="property=='3'" style="width: 3px;height: 19px;background-color: #0072c9;margin-left: 3px"></span>-->
<!--            <span v-show="property!='3'" style="width: 3px;height: 19px;margin-left: 3px"></span>-->
<!--            <i class="el-icon-setting"></i>-->
<!--            <span>隐私、搜索和服务 </span>-->
<!--          </div>-->

<!--          <div class="title"  @click="goRouter(4)" :class="[property=='4' ? ' getClass':'']">-->
<!--            <span v-show="property=='4'" style="width: 3px;height: 19px;background-color: #0072c9;margin-left: 3px"></span>-->
<!--            <span v-show="property!='4'" style="width: 3px;height: 19px;margin-left: 3px"></span>-->

<!--            <i class="el-icon-setting"></i>-->
<!--            <span>外观 </span>-->
<!--          </div>-->

<!--          <div class="title"  @click="goRouter(5)" :class="[property=='5' ? ' getClass':'']">-->
<!--            <span v-show="property=='5'" style="width: 3px;height: 19px;background-color: #0072c9;margin-left: 3px"></span>-->
<!--            <span v-show="property!='5'" style="width: 3px;height: 19px;margin-left: 3px"></span>-->

<!--            <i class="el-icon-setting"></i>-->
<!--            <span>开始、主页和新建标签页 </span>-->
<!--          </div>-->

<!--          <div class="title"  @click="goRouter(6)" :class="[property=='6' ? ' getClass':'']">-->
<!--            <span v-show="property=='6'" style="width: 3px;height: 19px;background-color: #0072c9;margin-left: 3px"></span>-->
<!--            <span v-show="property!='6'" style="width: 3px;height: 19px;margin-left: 3px"></span>-->

<!--            <i class="el-icon-setting"></i>-->
<!--            <span>共享、复制和粘贴 </span>-->
<!--          </div>-->

<!--          <div class="title"  @click="goRouter(7)" :class="[property=='7' ? ' getClass':'']">-->
<!--            <span v-show="property=='7'" style="width: 3px;height: 19px;background-color: #0072c9;margin-left: 3px"></span>-->
<!--            <span v-show="property!='7'" style="width: 3px;height: 19px;margin-left: 3px"></span>-->

<!--            <i class="el-icon-setting"></i>-->
<!--            <span>Cookie 和网站权限 </span>-->
<!--          </div>-->
        </div>


      </el-aside>
      <el-main>  <router-view ></router-view></el-main>
    </el-container>



















  </div>

</template>
<script>

  import bkindex from "./tool/bkindex.vue";
  import userAvatar from "../../system/user/profile/userAvatar.vue";
  export default {
    name: 'areaTree',
    components: {bkindex,userAvatar},

    data: function () {
      return {
        property:1,
        list:[
          {
            "id":1,
            "name":"个人中心",
            "path":"/bkindex",
            "icon":"el-icon-user"
          },{
            "id":2,
            "name":"系统设置",
            "path":"/userSettings",
            "icon":"el-icon-setting"
          }
          ,{
            "id":3,
            "name":"导入书签",
            "path":"/importHtml",
            "icon":"el-icon-upload2"
          }
          ,{
            "id":4,
            "name":"备份导出",
            "path":"/exportHtml",
            "icon":"el-icon-download"
          }
          ,{
            "id":5,
            "name":"更新日志",
            "path":"/issueLog",
            "icon":"el-icon-setting"
          }
          ,{
            "id":6,
            "name":"关于我们",
            "path":"/aboutUs",
            "icon":"el-icon-tickets"
          }
         ]
      }
    },
    mounted(){
      this.property=2;
      // document.querySelector('body').setAttribute('style', 'background-color:#f6f5f4')
    },
    methods: {

      goRouter(e){
        var that = this;
        //样式选中
        if (0<e&&e<7) {
          this.property=e;
        }

        switch (e) {
          case 9:
            //全部书签 返回
            that.$router.push({
              path: "/content",
              query: {
                menuId: 'newest'
              }
            })
            break;
          case 1:
            //个人信息
            that.$router.push({
              path: "/bkindex",
            })
            break;
          case 2:
            that.$router.push({
              path: "/userSettings",
            })
            break;
          case 3:
            that.$router.push({
              path: "/importHtml",
            })
            break;
          case 4:
            that.$router.push({
              path: "/exportHtml",
            })
            break;
          case 5:
            that.$router.push({
              path: "/issueLog",
            })
            break;
          case 6:
            that.$router.push({
              path: "/aboutUs",
            })
            break;
          default:
            that.$router.push({
              path: "/ceshi",
            })
        }

      },


      goBack() {
        var that = this;

      }
    },

    beforeDestroy() {
      // document.querySelector('body').removeAttribute('style')
    }
  }
</script>
<style>


</style>
<style scoped>
  .user-name-account{
    width: 250px;margin-top: -10px
  }

  .aside-left{
    width: 340px;
    height: 800px;
    border-right: 1px solid #b6b6b6;
    background-color: #f7f7f7
  }
  .main-top{
    width: 250px;
    height:60px;
    margin-top: 20px;
    margin-left: 10px;
  }

  .userName {
    width: 100%;
    float: left;
    font-size: 15px;
    font-weight: 600;
    height: 20px;
  }

  .userAccount {
    width: 100%;
    float: left;
    font-size: 12px;
    color: #9b9b9a;
    height: 20px;
  }

.user{
  display: flex;
  align-items: center;
}
  .title{
    width: 100%;
    height: 32px;
    line-height: 35px;
    display: flex;
    flex-direction:row;
    align-items:center;
    border-radius-: 2px;
  }
  .title-top{
    width: 100%;
    height: 25px;
    line-height: 30px;
    align-items:center;
    margin-left: 13px;
    font-size: 14px;
    color: #808080;
    margin-bottom: 3px;
  }

  .title span{
    font-size: 15px;
    color: #262626;

  }
  .title i{
    margin-left: 7px;
    margin-right: 7px;
  }
  .title:hover{
    background-color: #e5e5e5;
  }
  .titleAll{
  }

  .getClass{
    /*border-left: 3px solid #0072C9;*/
    background-color: #e5e5e5;
  }

  .goIndex{
    padding-top: 10px;
    padding-left: 8px;
  }
  .head-tag {
    width: 100%;
    height: 48px;
    background-color: #ffffff;
    display: flex
  }

  .head-tag-button {
    display: flex;
    justify-content: space-between;
    width: 45%;
    align-items: center;
    margin: 0 auto;
  }
  .head-tag-button button{
    border-radius: 3px;
    font-weight: 600;
    height: 30px;
    line-height: 30px;
  }

  .head-top {
    width: 100%;
    height: 48px;
    background-color: #ffffff;
  }

  .main {
    width: 100%;
    height: 100%;
    background-color: #f6f5f4;
    border-top: 1px solid #D4D4D4;
  }

  .mian-content {
    width: 50%;
    height: 500px;
    margin: 0 auto
  }

</style>
