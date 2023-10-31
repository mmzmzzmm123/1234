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
import normal from '@/components/evaluation/normalResult'
import recommend from '@/components/consult/recommend'
import wrong from '@/components/evaluation/wrong'

export default {
  components: {
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
    this.orderId = utils.getParam(location.href, "orderId")
    this.report = await serve.getReport(this.orderId)

    if ([2,8].includes(this.report.order.gaugeType)) {
      let lats = await serve.getLats(this.report.order.gaugeId)
      this.$refs.sasTemp.initData(this.report ,lats)
    }

    if (this.report.order.wrong === '1') {
      this.wrongs = await serve.getWrongList(this.report.order.gaugeId, this.report.order.id)
      console.log(this.wrongs)
    }

    utils.share(this.report.order.gaugeTitle, '', this.report.order.headPicture, 'https://wx.ssgpsy.com/pages/evaluation/product?id=' + this.report.order.gaugeId)
  },
  async mounted() {
    this.userInfo = utils.getUserInfo()
    if (!this.userInfo && await utils.loginCallback()) {
      this.userInfo = utils.getUserInfo()
    }
    if (!await utils.checkLogin()) {
      return this.openLoginConfirm()
    }
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
    }
  }
}
</script>
<style lang="scss">

</style>