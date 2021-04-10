<template>
  <div>


    <div class="head-top">
      <div class="goIndex">
        <el-page-header @back="goBack" content="">
        </el-page-header>
      </div>
      <div style="position: absolute;left: 47.5%;margin-top:-20px">
        设置  ·  王先生
      </div>

    </div>



    <div class="head-tag">
      <div class="mdui-btn-group head-tag-button">
        <button type="button"
                class="mdui-btn mdui-color-theme-accent mdui-ripple mdui-btn-active mdui-color-blue-50 mdui-text-color-blue-600">
          个人中心
        </button>
        <button type="button" class="mdui-btn mdui-color-theme-accent mdui-ripple">系统设置</button>
        <button class="mdui-btn mdui-color-theme-accent mdui-ripple">导入书签</button>
        <button class="mdui-btn mdui-color-theme-accent mdui-ripple">备份导出</button>
        <button class="mdui-btn mdui-color-theme-accent mdui-ripple">更新日志</button>
        <button class="mdui-btn mdui-color-theme-accent mdui-ripple">关于我们</button>
      </div>
    </div>



<!--        <bkindex></bkindex>-->

    <router-view ></router-view>




  </div>

</template>
<script>

  import bkindex from "../../system/user/profile/bkindex.vue";
  import userAvatar from "../../system/user/profile/userAvatar.vue";
  export default {
    name: 'areaTree',
    components: {bkindex,userAvatar},

    data: function () {
      return {}
    },
    methods: {
      goBack() {
        var that = this;
        //全部书签
        that.$router.push({
          path: "/content",
          query: {
            menuId: 'BOOKMARK'
          }
        })
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
    width: 50%;
    margin: 0 auto;
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
