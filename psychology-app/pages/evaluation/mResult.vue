<template>
  <view class="page" v-if="report && report.order && report.setting">
    <template v-if="report.order && report.order.gaugeType === 3">
      <mbti :report="report"/>
    </template>
    <template v-else-if="report.order && report.order.gaugeType === 4">
      <sds :report="report"/>
    </template>
    <template v-else-if="report.order && [2,8].includes(report.order.gaugeType)">
      <sas ref="sasTemp" :report="report" :lats="lats" :radarData="radarData"/>
    </template>
    <template v-else-if="report.order">
      <normal :report="report"/>
    </template>
    <wrong v-if="report.order.wrong === '1' && wrongs.length > 0" :list="wrongs"/>
    <recommend/>
    <login ref="loginModel" :isNav="3"></login>
  </view>
</template>
<script>
import login from '@/components/common/login'
import serve from '@/server/evaluation/question'
import mbti from '@/components/evaluation/mbtiResult'
import sas from '@/components/evaluation/sasResult'
import sds from '@/components/evaluation/sdsResult'
import normal from '@/components/evaluation/normalResult'
import recommend from '@/components/consult/recommend'
import wrong from '@/components/evaluation/wrong'

export default {
  components: {
    login,
    mbti,
    sas,
    sds,
    normal,
    recommend,
    wrong
  },
  data() {
    return {
      orderId: null,
      userInfo: {},
      report: {},
      lats: [],
      wrongs: [],
      radarData: {
        disableLegend: true,
        categories: [],
        series: [{
          name: "焦虑",
          data: []
        }]
      },
    }
  },
  async created() {
    this.orderId = this.$utils.getParam(location.href, "orderId")
    this.report = await serve.getReport(this.orderId)

    if ([2, 8].includes(this.report.order.gaugeType)) {
      let lats = await serve.getLats(this.report.order.gaugeId)
      this.$refs.sasTemp.initData(this.report, lats)
    }

    if (this.report.order.wrong === '1') {
      this.wrongs = await serve.getWrongList(this.report.order.gaugeId, this.report.order.id)
      console.log(this.wrongs)
    }

    // #ifdef H5
    this.$utils.share(this.report.order.gaugeTitle, '', this.report.order.headPicture, 'https://wx.ssgpsy.com/pages/evaluation/product?id=' + this.report.order.gaugeId)
    // #endif
  },
  async mounted() {
    this.userInfo = this.$utils.getUserInfo()
    if (!this.userInfo && await this.$utils.loginCallback()) {
      this.userInfo = this.$utils.getUserInfo()
    }
    if (!await this.$utils.checkLogin()) {
      return this.openLoginConfirm()
    }
  },
  methods: {
    // 登录
    openLoginConfirm() {
      this.$refs.loginModel.open()
    }
  }
}
</script>
<style lang="scss">

</style>