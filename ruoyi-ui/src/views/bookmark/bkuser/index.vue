<template>
  <div>


    <div class="head-top">
      <div class="goIndex">
        <el-page-header @back="goRouter(9)" content="">
        </el-page-header>
      </div>
      <div style="position: absolute;left: 47.5%;margin-top:-20px">
        设置  ·  王先生
      </div>

    </div>



    <div class="head-tag">
      <div class="mdui-btn-group head-tag-button">
<!--        <div  :class="['classification',property=='0'?' classification-click':'']" @click="showopen(0)"><span>网页</span></div>-->
        <button @click="goRouter(1)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='1'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">个人中心</button>
        <button @click="goRouter(7)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='7'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">外观设置</button>
        <button @click="goRouter(2)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='2'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">系统设置</button>
        <button @click="goRouter(3)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='3'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">导入书签</button>
        <button @click="goRouter(4)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='4'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">备份导出</button>
        <button @click="goRouter(5)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='5'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">更新日志</button>
        <button @click="goRouter(6)" :class="['mdui-btn mdui-color-theme-accent mdui-ripple ',property=='6'?' mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600':'']">关于我们</button>


      </div>
    </div>



<!--        <bkindex></bkindex>-->

    <router-view ></router-view>




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
      }
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
                menuId: 'BOOKMARK'
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
    mounted() {
      document.querySelector('body').setAttribute('style', 'background-color:#f6f5f4')
    },
    beforeDestroy() {
      document.querySelector('body').removeAttribute('style')
    }
  }
</script>
<style>


</style>
<style scoped>


  .goIndex{
    padding-left: 18px;padding-top: 18px;
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
