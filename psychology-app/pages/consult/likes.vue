<template>
  <view class="page">
    <uni-list :border="true">
      <uni-list-chat v-for="item in list"  ellipsis="1" :avatar-circle="true" :title="item.consultName" :avatar="item.avatar" :note="item.info" :to="'/pages/consult/consultant?id=' + item.consultId" />
    </uni-list>

    <uni-load-more status="no-more" :content-text="{ contentnomore: '- 已经到底啦 -' }" />

    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>
  </view>
</template>

<script>
import server from "@/server/consult/user";
import utils from "@/utils/common";
import loginServer from "@/server/login"

export default {
  data() {
    return {
      list: [],
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderByColumn: 'createTime',
        isAsc: 'descending',
      },
    }
  },
  async mounted() {
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback()) {
      this.userInfo = utils.getUserInfo()
    }
    if (!await utils.checkLogin()) {
      return this.openLoginConfirm()
    }

    await this.getLikes()
  },
  methods: {
    // 登录
    async confirmLogin () {
      await loginServer.login();
      this.$refs.popup.close()
    },
    closeLoginConfirm() {
      this.$refs.popup.close()
    },
    openLoginConfirm() {
      this.$refs.popup.open()
    },
    async getLikes() {
      const res = await server.getLikes(this.queryParams)
      if (res.code == 200) {
        this.total = res.total
        this.list = [...this.list, ...res.rows]
      }
      console.log(res)
    },
    async loadMore() {
      if (this.list.length < this.total) {
        this.pageNum += 1
        await this.getConsult()
      }
    },
    onReachBottom() {
      this.loadMore()
    },
    back() {
      uni.navigateTo({
        url: "/pages/consult/user",
      });
    },
  }
}
</script>

<style scoped>

</style>