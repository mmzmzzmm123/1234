<template>
  <view class="page" v-if="report && report.order && report.setting">
    <mbti v-if="report.order && report.order.gaugeType === 3" :report="report"/>
    <sds v-if="report.order && report.order.gaugeType === 4" :report="report"/>
    <sas v-if="report.order && report.order.gaugeType === 5" :report="report"/>
    <recommend/>
    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true"
                        @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>
  </view>
</template>
<script>
import utils from "@/utils/common";
import loginServer from '@/server/login'
import serve from '@/server/evaluation/question'
import mbti from '@/components/evaluation/mbtiResult'
import sas from '@/components/evaluation/sasResult'
import sds from '@/components/evaluation/sdsResult'
import recommend from '@/components/consult/recommend'

export default {
  components: {
    mbti,
    sas,
    sds,
    recommend
  },
  data() {
    return {
      show: false,
      orderId: null,
      userInfo: {},
      report: {}
    }
  },
  created() {
    this.orderId = utils.getParam(location.href, "orderId")
  },
  async mounted() {
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback()) {
      this.userInfo = utils.getUserInfo()
    }
    if (!await utils.checkLogin()) {
      return this.openLoginConfirm()
    }
    this.report = await serve.getReport(this.orderId)
    this.show = true
    utils.share(this.report.order.gaugeTitle, '', this.report.order.headPicture, 'https://wx.ssgpsy.com/pages/evaluation/product?id=' + this.report.order.gaugeId)
  },
  methods: {
    // 登录
    async confirmLogin() {
      await loginServer.login();
      this.$refs.popup.close()
    },
    closeLoginConfirm() {
      this.$refs.popup.close()
    },
    openLoginConfirm() {
      this.$refs.popup.open()
    },
  }
}
</script>
<style lang="scss">

</style>