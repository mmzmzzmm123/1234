<template>
  <view class="page">
    <view class="padding">
      <psy-steps :items="items" :active="active"/>
    </view>

    <view v-if="active === 1">
      <view class="padding-sm margin-sm bg-white">
        <view class="psy-title">学历背景
          <text class="psy-title-ext">（必填）</text>
        </view>
        <view class="margin-top-xs psy-title-ext">请填写国家认可的教育经历，并提供已经获得的学历证书。</view>
        <view class="margin-top solid-bottom">
          <item-list :type="1" :list="dataList.filter(a => a.type === 1)" @edit="editItem" @del="delItem"/>
          <view v-if="!showForm[0]" class="cu-btn line-orange round lg btn-submit" @tap="showItem(1)">+ 增加一项</view>
        </view>

        <item-form v-if="showForm[0]" :type="1" :form="form" @add="saveItem" @cancel="hideItem(1)"/>
      </view>

      <view class="padding-sm margin-sm margin-top-lg bg-white">
        <view class="psy-title">专业资质证明
          <text class="psy-title-ext">（必填）</text>
        </view>
        <view class="solid-bottom">
          <item-list :type="2" :list="dataList.filter(a => a.type === 2)" @edit="editItem" @del="delItem"/>
          <view v-if="!showForm[1]" class="cu-btn line-orange round lg btn-submit" @tap="showItem(2)">+ 增加一项</view>
        </view>

        <item-form v-if="showForm[1]" :type="2" :form="form" :picker="picker1" :idx="idx" @modifyIdx="modifyIdx" @add="saveItem" @cancel="hideItem(2)"/>
      </view>

      <view class="padding-sm margin-sm margin-top-lg bg-white">
        <view class="psy-title">其他证书证明
          <text class="psy-title-ext">（选填）</text>
        </view>
        <view class="solid-bottom">
          <item-list :type="3" :list="dataList.filter(a => a.type === 3)" @edit="editItem" @del="delItem"/>
          <view v-if="!showForm[2]" class="cu-btn line-orange round lg btn-submit" @tap="showItem(3)">+ 增加一项</view>
        </view>

        <item-form v-if="showForm[2]" :type="3" :form="form" @add="saveItem" @cancel="hideItem(3)"/>
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
import indexServer from '@/server/consult/index'
import psySteps from "@/components/common/psySteps";
import itemList from "@/components/consult/toPartner/itemList";
import itemForm from "@/components/consult/toPartner/itemForm";
export default {
  name: "ConsultPartnerStep2",
  components: { psySteps, itemList, itemForm },
  data() {
    return {
      idx: -1,
      active: 1,
      showForm: [false, false, false],
      header: {},
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
      picker1: [],
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
    this.getPickers()
  },
  methods: {
    modifyIdx(idx) {
      this.idx = idx
    },
    async getPickers() {
      const res = await indexServer.getConfigByType('consult_qualification');
      if (res.length > 0) {
        this.picker1 = res.map(a => a.name)
        console.log(this.picker1)
      }
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
      this.form.imgList = this.form.img ? this.form.img.split(',') : []
      if (this.form.param1 && this.form.type === 2) {
        this.idx = this.picker1.findIndex(a => a === this.form.param1)
      }

      this.$set(this.showForm, type-1, true)
    },
    editItem(item) {
      console.log(item)
      this.form = JSON.parse(JSON.stringify(item))
      this.showItem(item.type, 'set')
    },
    hideItem(type) {
      this.initForm()
      this.$set(this.showForm, type-1, false)
    },
    async saveItem(item) {
      console.log(item)

      return false
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
        url: "/pages/consult/toPartner/step1"
      });
    },
    async next(type) {
      console.log(type)
      if (type === 1) {
        return uni.showToast({
          icon: "success",
          title: "保存成功",
        });
      }

      if (this.dataList.filter(a => a.type === 1).length === 0) {
        return uni.showToast({
          title: '请填写学历背景',
          icon: "none"
        });
      }

      if (this.dataList.filter(a => a.type === 2).length === 0) {
        return uni.showToast({
          title: '请填写专业资质证明',
          icon: "none"
        });
      }

      if (this.header.step === 2) {
        this.header.step = 3
      }

      const res = await serve.save(this.header)
      if (res.code === 200) {
        uni.showToast({
          icon: "success",
          title: "保存成功",
        });
      }

      uni.navigateTo({
        url: "/pages/consult/toPartner/step3"
      });
    }
  }
}
</script>

<style lang="scss">
.page {
  padding-bottom: calc(constant(safe-area-inset-bottom) + 100px); /* 兼容 iOS 设备 */
  padding-bottom: calc(env(safe-area-inset-bottom) + 100px); /* 兼容 iPhone X 及以上设备 */
  //padding: 0 20upx;

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