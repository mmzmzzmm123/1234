<template>
  <view class="page" v-if="report && report.order && report.setting">
    <mbti v-if="report.order && report.order.gaugeType === 3" :report="report"/>
    <sds v-if="report.order && report.order.gaugeType === 4" :report="report"/>
    <sas v-if="report.order && [5,6].includes(report.order.gaugeType)" :report="report" :lats="lats" :radarData="radarData"/>
    <normal v-if="report.order && ![3,4,5,6].includes(report.order.gaugeType)" :report="report"/>
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

export default {
  components: {
    mbti,
    sas,
    sds,
    normal,
    recommend
  },
  data() {
    return {
      orderId: null,
      userInfo: {},
      report: {},
      lats: [],
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
    const data = await serve.getReport(this.orderId)
    if ([5,6].includes(data.order.gaugeType)) {
      const lats = await serve.getLats(data.order.gaugeId)
      if (lats.length > 0) {
        this.radarData.series[0].name = this.getGaugeType(data.order.gaugeType)
        lats.forEach((a, index) => {
          a.score = 0
          if (a.questionIds) {
            const arr = a.questionIds.split(",")
            const res = data.results.filter(b => arr.includes(b.questionsId + '')) || []
            const score = res.reduce((a, c) => a + c.score, 0)
            this.radarData.categories[index] = a.name
            this.radarData.series[0].data[index] = score
            a.score = Math.round(score / res.length * 25)
            a.title = this.getResultTitle(a.name, a.score)
          }
        })
        this.lats = lats.sort((a,b) => parseInt(a.y) - parseInt(b.y))
      }
    }

    this.report = data
    utils.share(data.order.gaugeTitle, '', data.order.headPicture, 'https://wx.ssgpsy.com/pages/evaluation/product?id=' + data.order.gaugeId)
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
    },
    getGaugeType(type) {
      let name = '测评'
      switch (type) {
        case 1:
          name = '普通计算'
          break;
        case 2:
          name = '多维计算'
          break;
        case 3:
          name = 'MBTI'
          break;
        case 4:
          name = 'SDS'
          break;
        case 5:
          name = 'SAS'
          break;
        case 6:
          name = 'SCL-90'
          break;
      }
      return name
    },
    getResultTitle(lat, percentage) {
      let title = ''
      if (percentage < 25) {
        title = `从测评结果来看，您没有${lat}的情况或${lat}的情况较轻。`
      } else if (percentage >= 25 && percentage < 60) {
        title = `从测评结果来看，您的${lat}的情况较明显。`
      } else if (percentage >= 60) {
        title = `从测评结果来看，您的${lat}的情况比较严重。`
      }
      return title
    }
  }
}
</script>
<style lang="scss">

</style>