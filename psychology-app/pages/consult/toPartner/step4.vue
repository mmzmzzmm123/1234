<template>
  <view class="page">
    <view class="padding">
      <template v-if="type === 'edit'">
        <psy-steps :items="items" :active="active"/>
      </template>
      <template v-else>
        <text @tap="back('1')" class="cuIcon-back">返回</text>
      </template>
    </view>

    <view class="padding-sm margin-sm bg-white">
      <view class="flex justify-between">
        <text class="psy-title">申请类型</text>
        <text v-if="type === 'edit'" class="text-orange" @tap="edit(1)">编辑</text>
      </view>
      <view class="margin-top-xs">
        {{ getType(form.type) }}
      </view>
    </view>

    <view class="padding-sm margin-sm bg-white">
      <view class="flex justify-between">
        <text class="psy-title">基础信息</text>
        <text v-if="type === 'edit'" class="text-orange" @tap="edit(1)">编辑</text>
      </view>
      <view class="flex justify-between margin-top-xs solid-bottom padding-xs">
        <text class="">真实姓名</text>
        <text>{{ form.name }}</text>
      </view>
      <view class="flex justify-between solid-bottom padding-xs">
        <text class="">性别</text>
        <text>{{ form.sex === 1 ? '男' : '女' }}</text>
      </view>
      <view class="flex justify-between solid-bottom padding-xs">
        <text class="">手机号码</text>
        <text>{{ form.phone }}</text>
      </view>
      <view class="flex justify-between solid-bottom padding-xs">
        <text class="">邮箱</text>
        <text>{{ form.email }}</text>
      </view>
      <view class="flex justify-between solid-bottom padding-xs">
        <text class="">微信号</text>
        <text>{{ form.wechat }}</text>
      </view>
      <view class="flex justify-between solid-bottom padding-xs">
        <text class="">qq号</text>
        <text>{{ form.qq }}</text>
      </view>
      <view class="flex justify-between solid-bottom padding-xs">
        <text class="">咨询地点</text>
        <text>{{ form.province }},{{ form.city }}</text>
      </view>
      <view class="flex justify-between solid-bottom padding-xs">
        <text class="">咨询语种</text>
        <text>{{ form.lang }}</text>
      </view>
      <view class="flex justify-between solid-bottom padding-xs">
        <text class="">从业年限(年)</text>
        <text>{{ form.workHours }}</text>
      </view>
      <view class="flex justify-between solid-bottom padding-xs">
        <text class="">咨询流派</text>
        <text>{{ form.genre }}</text>
      </view>
      <view class="flex justify-between solid-bottom padding-xs">
        <text class="">证件类型</text>
        <text>{{ form.card }}</text>
      </view>
      <view class="solid-bottom padding-xs">
        <text class="">证件照片</text>
        <view class="margin-top-xs" v-if="form.cardImgs && form.cardImgs.length > 0">
          <image v-for="item in form.cardImgs" style="width: 188upx;height: 182upx;margin-right: 20upx" :src="item" mode="widthFix"></image>
        </view>
      </view>
      <view class="solid-bottom padding-xs">
        <text class="">相关资质证明</text>
        <view class="margin-top-xs" v-if="form.extraImgs && form.extraImgs.length > 0">
          <image v-for="item in form.extraImgs" style="width: 188upx;height: 182upx;margin-right: 20upx" :src="item" mode="widthFix"></image>
        </view>
      </view>
    </view>

    <view class="padding-sm margin-sm bg-white">
      <view class="flex justify-between">
        <text class="psy-title">学历背景</text>
        <text v-if="type === 'edit'" class="text-orange" @tap="edit(2)">编辑</text>
      </view>
      <view class="margin-top-xs" v-if="dataList.length > 0">
        <time-line :type="1" :list="dataList.filter(a => a.type === 1)"/>
      </view>
    </view>

    <view class="padding-sm margin-sm bg-white">
      <view class="flex justify-between">
        <text class="psy-title">专业资质证明</text>
        <text v-if="type === 'edit'" class="text-orange" @tap="edit(2)">编辑</text>
      </view>
      <view class="margin-top-xs">
        <time-line :type="2" :list="dataList.filter(a => a.type === 2)"/>
      </view>
    </view>

    <view class="padding-sm margin-sm bg-white">
      <view class="flex justify-between">
        <text class="psy-title">专业证书证明</text>
        <text v-if="type === 'edit'" class="text-orange" @tap="edit(2)">编辑</text>
      </view>
      <view class="margin-top-xs">
        <time-line :type="3" :list="dataList.filter(a => a.type === 3)"/>
      </view>
    </view>

    <view class="padding-sm margin-sm bg-white">
      <view class="flex justify-between">
        <text class="psy-title">受训经历</text>
        <text v-if="type === 'edit'" class="text-orange" @tap="edit(3)">编辑</text>
      </view>
      <view class="margin-top-xs">
        <time-line :type="4" :list="dataList.filter(a => a.type === 4)"/>
      </view>
    </view>

    <view class="padding-sm margin-sm bg-white">
      <view class="flex justify-between">
        <text class="psy-title">个案咨询经历</text>
        <text v-if="type === 'edit'" class="text-orange" @tap="edit(3)">编辑</text>
      </view>
      <view class="margin-top-xs">
        <time-line :type="5" :list="dataList.filter(a => a.type === 5)"/>
      </view>
    </view>

    <view class="padding-sm margin-sm bg-white">
      <view class="flex justify-between">
        <text class="psy-title">接受督导经历</text>
        <text v-if="type === 'edit'" class="text-orange" @tap="edit(3)">编辑</text>
      </view>
      <view class="margin-top-xs">
        <time-line :type="6" :list="dataList.filter(a => a.type === 6)"/>
      </view>
    </view>

    <view class="padding-sm margin-sm bg-white">
      <view class="flex justify-between">
        <text class="psy-title">其他经历</text>
        <text v-if="type === 'edit'" class="text-orange" @tap="edit(3)">编辑</text>
      </view>
      <view class="margin-top-xs">
        <time-line :type="7" :list="dataList.filter(a => a.type === 7)"/>
      </view>
    </view>

    <view v-if="type === 'edit'"  class="padding-sm margin-sm">
      <checkbox-group class="block" @change="checkboxChange">
        <label class="block flex align-center">
          <checkbox class="orange" :class="checkBox === 'ok'?'checked':''"  value="ok" :checked="checkBox === 'ok'"/>
          <text class="margin-left-sm">我已确认并接受<text class="text-blue" @tap.stop="toPage">《咨询师入驻协议》</text></text>
        </label>
      </checkbox-group>
    </view>

    <view v-if="type === 'edit'" class="cu-bar foot bg-white tabbar border shop" style="z-index: 99">
      <view class="action" @tap="next(1)">
        <view class="cuIcon-form"></view>存草稿
      </view>
      <view class="flex justify-center">
        <view class="cu-btn line-orange round lg btn-next" @tap="back('2')">上一步</view>
        <view class="cu-btn bg-orange round lg btn-next" @tap="next(2)">确认提交</view>
      </view>
    </view>

    <login ref="loginModel" :isNav="1"></login>
  </view>
</template>

<script>
import login from "@/components/common/login";
import serve from "@/server/consult/toPartner";
import psySteps from "@/components/common/psySteps";
import itemList from "@/components/consult/toPartner/itemList";
import timeLine from "@/components/consult/toPartner/timeLine";
import itemForm from "@/components/consult/toPartner/itemForm";
export default {
  name: "ConsultPartnerStep4",
  components: { login, psySteps, itemList, itemForm, timeLine },
  data() {
    return {
      type: 'edit',
      active: 3,
      checkBox: '',
      showForm: [false, false, false, false],
      form: {},
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
  async onLoad(option) {
    // #ifdef H5
    this.$utils.share('口袋心理入驻申请', '欢迎入驻口袋心理咨询服务平台', '', 'https://wx.ssgpsy.com/pages/consult/toPartner/start')
    // #endif

    if (option && option.type) {
      this.type = option.type
    }

    this.userInfo = this.$utils.getUserInfo()
    if (!this.userInfo && await this.$utils.loginCallback()) {
      this.userInfo = this.$utils.getUserInfo()
    }
    if (!await this.$utils.checkLogin()) {
      return this.openLoginConfirm()
    }

    await this.getApply()
  },
  methods: {
    openLoginConfirm() {
      this.$refs.loginModel.open()
    },
    async getApply() {
      const res = await serve.getInfo()
      if (res.code === 200 && res.data) {
        this.form = res.data
        this.dataList = this.form.items
      }
      console.log(this.dataList)
    },
    getType(type) {
      if (type) {
        const types = [
            { value: 1, name: '合作型' },
            { value: 2, name: '签约型（团体督导）' },
            { value: 3, name: '签约型（个体督导）' },
        ]
        return types.find(a => a.value === type).name
      }
      return ''
    },
    checkboxChange(e) {
      console.log(e.detail.value)
      this.checkBox = e.detail.value[0]
    },
    edit(type) {
      uni.navigateTo({
        url: "/pages/consult/toPartner/step" + type
      });
    },
    async toPage() {
      if (!await this.$utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      uni.navigateTo({
        url: "/pages/consult/toPartner/compact"
      });
    },
    async back(type) {
      if (!await this.$utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      if (type === '2') {
        uni.navigateTo({
          url: "/pages/consult/toPartner/step3"
        });
      } else if (type === '1') {
        uni.navigateTo({
          url: "/pages/consult/toPartner/result"
        });
      }
    },
    async next(type) {
      if (!await this.$utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      if (type === 1) {
        return uni.showToast({
          icon: "success",
          title: "保存成功",
        });
      }

      if (this.checkBox !== 'ok') {
        return uni.showToast({
          icon: 'none',
          title: '请勾选 我已确认并接受《咨询师入驻协议》 后进行下一步操作',
          duration: 2000
        })
      }

      this.form.status = '1'
      const res = await serve.save(this.form)
      if (res.code === 200) {
        uni.showToast({
          icon: "success",
          title: "保存成功",
        });
      }

      uni.navigateTo({
        url: "/pages/consult/toPartner/result?type=1"
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

  .cu-form-group {
    height: 80upx !important;
  }

  .cu-form-group input {
    text-align: right;
  }

  .psy-title {
    color: #101010;
    text-align: left;
    margin-bottom: 9upx;
  }

  .plaClass {
    color: #8799a3;
  }

  .psy-title {
    color: #101010;
    text-align: left;
    margin-bottom: 9upx;
  }

  .btn-next {
    width: 250upx;
    margin-right: 30upx;
  }

  .cu-form-group .title {
    min-width: calc(4em + 15px);
  }
  .cu-form-group input {
    text-align: right;
  }

  .plaClass {
    color: #8799a3;
  }

}

</style>