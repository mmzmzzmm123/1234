<template>
  <view class="page">
    <view class="padding">
      <psy-steps :items="items" :active="active"/>
    </view>

    <view v-if="active === 2">
      <view class="padding-sm margin-sm bg-white">
        <view class="psy-title">受训经历
          <text class="psy-title-ext">（必填）</text>
        </view>
        <view class="margin-top-xs psy-title-ext">1. 请按每段培训时间从前到后的顺序填写（正在进行的培训，写预计完成时间）。</view>
        <view class="margin-top-xs psy-title-ext">2. 培训信息经审核后将在你的个人页面，请确保描述符合平台的建议或要求。</view>
        <view class="margin-top solid-bottom">
          <item-list :type="4" :list="dataList.filter(a => a.type === 4)" @edit="editItem" @del="delItem"/>
          <view v-if="!showForm[0]" class="cu-btn line-orange round lg btn-submit" @tap="showItem(4)">+ 增加一项</view>
        </view>

        <item-form v-if="showForm[0]" :type="4" :form="form" @add="saveItem" @cancel="hideItem(4)"/>
      </view>

      <view class="padding-sm margin-sm margin-top-lg bg-white">
        <view class="psy-title">个案咨询经历
          <text class="psy-title-ext">（必填）</text>
        </view>
        <view class="margin-top-xs psy-title-ext">请按每段经验时间从前到后的顺序填写（正在进行的督导，写预计完成时间）。</view>
        <view class="solid-bottom">
          <item-list :type="5" :list="dataList.filter(a => a.type === 5)" @edit="editItem" @del="delItem"/>
          <view v-if="!showForm[1]" class="cu-btn line-orange round lg btn-submit" @tap="showItem(5)">+ 增加一项</view>
        </view>

        <item-form v-if="showForm[1]" :type="5" :form="form" :picker="picker1" :idx="idx" @modifyIdx="modifyIdx" @add="saveItem" @cancel="hideItem(5)"/>
      </view>

      <view class="padding-sm margin-sm margin-top-lg bg-white">
        <view class="psy-title">接受督导经历
          <text class="psy-title-ext">（选填）</text>
        </view>
        <view class="margin-top-xs psy-title-ext">1. 请按每段被督导的时间从前到后的顺序填写（正在进行的督导，写预计完成时间）。</view>
        <view class="margin-top-xs psy-title-ext">2. 督导信息经审核后将在你的个人页面，请确保描述符合平台的建议或要求。</view>
        <view class="solid-bottom">
          <item-list :type="6" :list="dataList.filter(a => a.type === 6)" @edit="editItem" @del="delItem"/>
          <view v-if="!showForm[2]" class="cu-btn line-orange round lg btn-submit" @tap="showItem(6)">+ 增加一项</view>
        </view>

        <item-form v-if="showForm[2]" :type="6" :form="form" :picker="picker2" :idx="idx" @modifyIdx="modifyIdx" @add="saveItem" @cancel="hideItem(6)"/>
      </view>

      <view class="padding-sm margin-sm margin-top-lg bg-white">
        <view class="psy-title">其他经历
          <text class="psy-title-ext">（选填）</text>
        </view>
        <view class="margin-top-xs psy-title-ext">可在这部分补充其他培训经历，申请入驻时，这类培训只作为补充信息，不作为入驻审核条件。</view>
        <view class="solid-bottom">
          <item-list :type="7" :list="dataList.filter(a => a.type === 7)" @edit="editItem" @del="delItem"/>
          <view v-if="!showForm[3]" class="cu-btn line-orange round lg btn-submit" @tap="showItem(7)">+ 增加一项</view>
        </view>

        <item-form v-if="showForm[3]" :type="7" :form="form" @add="saveItem" @cancel="hideItem(7)"/>
      </view>

      <view class="cu-bar foot bg-white tabbar border shop" style="z-index: 99">
        <view class="action" @tap="next(1)">
          <view class="cuIcon-form"></view>存草稿
        </view>
        <view class="flex justify-center">
          <view class="cu-btn line-orange round lg btn-next" @tap="back">上一步</view>
          <view class="cu-btn bg-orange round lg btn-next" @tap="next(2)">下一步</view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>

import serve from "@/server/consult/toPartner";
import psySteps from "@/components/common/psySteps";
import itemList from "@/components/consult/toPartner/itemList";
import itemForm from "@/components/consult/toPartner/itemForm";
export default {
  name: "ConsultPartnerStep3",
  components: { psySteps, itemList, itemForm },
  data() {
    return {
      idx: -1,
      active: 2,
      header: {},
      showForm: [false, false, false, false],
      form: {
        id: null,
        type: null,
        startTime: null,
        endTime: null,
        param1: null,
        param2: null,
        param3: null,
        img: '',
        imgList: [],
      },
      picker1: ['个人', '家庭', '团体', '企业'],
      picker2: ['个体督导', '团体督导'],
      items: [{
        name: '基础资料'
      }, {
        name: '学历&资历'
      }, {
        name: '专业经历'
      }, {
        name: '确认提交'
      }],
      dataList: [],
    }
  },
  onLoad() {
    this.getApply()
  },
  methods: {
    modifyIdx(idx) {
      this.idx = idx
    },
    async getApply() {
      const res = await serve.getInfo()
      console.log(res)
      if (res.code === 200 && res.data) {
        this.header = res.data
        this.dataList = this.header.items
      }
    },
    initForm() {
      this.form = {
        id: null,
        pid: this.header.id,
        type: null,
        startTime: null,
        endTime: null,
        param1: null,
        param2: null,
        param3: null,
        img: '',
        imgList: [],
      }
      this.idx = -1
    },
    showItem(type, mode = 'init') {
      if (mode === 'init') {
        this.initForm()
        this.form.type = type
      }

      if (this.form.param1) {
        this.idx = this.form.type === 5 ? this.picker1.findIndex(a => a === this.form.param1) : this.form.type === 6 ? this.picker2.findIndex(a => a === this.form.param1) : -1
      }

      this.form.imgList = this.form.img ? this.form.img.split(',') : []

      this.$set(this.showForm, type-4, true)
      console.log(this.showForm)
    },
    editItem(item) {
      this.form = JSON.parse(JSON.stringify(item))
      this.showItem(item.type, 'set')
    },
    hideItem(type) {
      this.initForm()
      this.$set(this.showForm, type-4, false)
    },
    async saveItem(item) {
      console.log('saveItem')
      console.log(item)
      item.img = item.imgList.join(',')
      if (item.id) {
        await serve.editItem(item)
      } else {
        await serve.addItem(item)
      }

      this.hideItem(item.type)
      await this.getApply()
    },
    async delItem(item) {
      await serve.delItem(item.id)
      await this.getApply()
    },
    back() {
      uni.navigateTo({
        url: "/pages/consult/toPartner/step2"
      });
    },
    async next(type) {
      if (type === 1) {
        return uni.showToast({
          icon: "success",
          title: "保存成功",
        });
      }

      if (this.dataList.filter(a => a.type === 4).length === 0) {
        return uni.showToast({
          title: '请填写受训经历',
          icon: "none"
        });
      }

      if (this.dataList.filter(a => a.type === 5).length === 0) {
        return uni.showToast({
          title: '请填写个案咨询经历',
          icon: "none"
        });
      }

      if (this.header.step === 3) {
        this.header.step = 4
      }

      const res = await serve.save(this.header)
      if (res.code === 200) {
        uni.showToast({
          icon: "success",
          title: "保存成功",
        });
      }

      uni.navigateTo({
        url: "/pages/consult/toPartner/step4?type=edit"
      });
    }
  }
}
</script>

<style lang="scss">
.page {
  //padding: 0 20upx;
  padding-bottom: calc(constant(safe-area-inset-bottom) + 100px); /* 兼容 iOS 设备 */
  padding-bottom: calc(env(safe-area-inset-bottom) + 100px); /* 兼容 iPhone X 及以上设备 */

  .psy-title {
    color: #101010;
    text-align: left;
    margin-bottom: 9upx;
  }

  .psy-title-ext {
    color: #6C6C6C;
    font-size: 24upx;
  }

  .btn-submit {
    width: 500upx;
    margin: 30upx 100upx;
  }

  .btn-next {
    width: 250upx;
    margin-right: 30upx;
  }

}

</style>