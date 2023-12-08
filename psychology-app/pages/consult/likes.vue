<template>
  <view class="page">
    <uni-list :border="true">
      <uni-list-chat v-for="item in list"  ellipsis="1" :avatar-circle="true" :title="item.consultName" :avatar="item.avatar" :note="item.info" :to="'/pages/consult/consultant?id=' + item.consultId" />
    </uni-list>

    <uni-load-more status="no-more" :content-text="{ contentnomore: '- 已经到底啦 -' }" />

    <login ref="loginModel" :isNav="1"></login>
  </view>
</template>

<script>
import login from "@/components/common/login";
import server from "@/server/consult/user";

export default {
  components: { login },
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
    this.userInfo = this.$utils.getUserInfo()
    if (!this.userInfo && await this.$utils.loginCallback()) {
      this.userInfo = this.$utils.getUserInfo()
    }
    if (!await this.$utils.checkLogin()) {
      return this.openLoginConfirm()
    }

    await this.getLikes()
  },
  methods: {
    // 登录
    openLoginConfirm() {
      this.$refs.loginModel.open()
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