<template>
  <view class="page">
    <view class="padding">
      <psy-steps :items="items" :active="active"/>
    </view>

    <view class="padding-sm margin-sm bg-white">
      <view class="psy-title">申请类型
        <text class="psy-title-ext">（必填）</text>
      </view>
      <view class="margin-top-xs psy-title-ext">说明：平台咨询师分为合作型和签约型两类。</view>
      <view class="margin-top-xs psy-title-ext">请根据入驻标准及自身情况选择相应的入驻类型。</view>
      <view class="margin-top-xs psy-title-ext" style="color: #1890FF" @tap="openStep">【点击了解壹加壹心理服务平台咨询师入驻介绍】</view>
      <view class="psy-title margin-top-sm">我要申请</view>
      <radio-group class="block" @change="typeChange">
        <label class="margin-top-xs block" v-for="(item, index) in types" :key="item.value">
          <radio class='orange' :class="form.type == item.value?'checked':''" :value="item.value" :checked="form.type == item.value"/>
          <text class="margin-left-sm">{{ item.name }}</text>
        </label>
      </radio-group>
    </view>

    <view class="margin-sm" style="margin-bottom: 100upx">
      <view class="cu-form-group margin-top">
        <view class="title">真实姓名</view>
        <input placeholder="请输入" placeholder-class="plaClass" v-model="form.name" maxlength="15"/>
      </view>
      <view class="cu-form-group">
        <view class="title">性别</view>
        <radio-group class="block" @change="sexChange">
          <label class="margin-top-xs margin-left" v-for="(item, index) in sexs" :key="item.value">
            <radio class='orange' :class="form.sex == item.value?'checked':''" :value="item.value" :checked="form.sex == item.value"/>
            <text class="margin-left-sm">{{ item.name }}</text>
          </label>
        </radio-group>
      </view>
      <view class="cu-form-group">
        <view class="title">手机号码</view>
        <input type="number" placeholder="请输入" placeholder-class="plaClass" maxlength="11" v-model="form.phone"/>
      </view>
      <view class="cu-form-group">
        <view class="title">邮箱</view>
        <input placeholder="请输入" placeholder-class="plaClass" v-model="form.email" maxlength="255"/>
      </view>
      <view class="cu-form-group">
        <view class="title">微信号</view>
        <input placeholder="请输入" placeholder-class="plaClass" v-model="form.wechat" maxlength="50"/>
      </view>
      <view class="cu-form-group">
        <view class="title">qq号</view>
        <input v-model="form.qq" type="number" placeholder="请输入" placeholder-class="plaClass" maxlength="10"/>
      </view>
      <view class="cu-form-group">
        <view class="title" @tap="showPicker">咨询地点</view>
        <view @tap="showPicker">
          <template v-if="form.province">
            {{ form.province + ',' + form.city }}
          </template>
          <template v-else>
            <text class="text-grey">请选择所在城市<text class="margin-left-sm cuIcon-right text-lg text-grey"></text></text>
          </template>
        </view>
      </view>
      <view class="cu-form-group">
        <view class="title">咨询语种</view>
        <view @tap="showModal('langModal')">
          <template v-if="form.lang">
            {{ form.lang }}
          </template>
          <template v-else>
            <text class="text-grey">请选择<text class="margin-left-sm cuIcon-right text-lg text-grey"></text></text>
          </template>
        </view>
      </view>
      <view class="cu-form-group">
        <view class="title">从业年限(年)</view>
        <input v-model="form.workHours" type="number" placeholder="请输入" placeholder-class="plaClass" />
      </view>
      <view class="cu-form-group">
        <view class="title">咨询流派</view>
        <view @tap="showWay">
          <template v-if="form.genre">
            <view class="text-right text-cut" style="width: 200px">{{ form.genre }}</view>
          </template>
          <template v-else>
            <text class="text-grey">请选择咨询流派<text class="margin-left-sm cuIcon-right text-lg text-grey"></text></text>
          </template>
        </view>
      </view>
      <view class="cu-form-group">
        <view class="title">证件类型</view>
        <picker v-model="form.card" @change="cardChange" :value="form.card" :range="picker">
          <view class="picker">
            <text v-if="form.card">
              {{ form.card }}
            </text>
            <text v-else class="text-grey">
              请选择
            </text>
          </view>
        </picker>
      </view>

      <view class="cu-bar bg-white margin-top">
        <view class="action">
          证件照片（正反面，2张）
        </view>
        <view class="action">
          {{form.cardImgs.length}}/2
        </view>
      </view>
      <view class="psy-box-upload bg-white">
        <psy-upload :value="form.cardImgs" :limit="2" @complete="result1"></psy-upload>
      </view>
    </view>

    <!--picker-view start-->
    <view class="mask-screen" @tap="hidePicker" v-show="showPickerStatus"></view>
    <view class="picker-box" :animation="animationData" v-show="showPickerStatus">
      <view class="picker-header list-item">
        <view class="btn-cancle" hover-class="opcity" hover-stay-time="150" @tap="hidePicker">取消</view>
        <view class="btn-sure" hover-class="opcity" hover-stay-time="150" @tap="doPicker">确定</view>
      </view>
      <picker-view indicator-style="height: 50px;" class="picker-view" :value="value" @change="columnPicker">
        <picker-view-column>
          <view v-for="(item,index) in proviceArr" :key="index" class="item">{{item}}</view>
        </picker-view-column>
        <picker-view-column>
          <view v-for="(item,index) in cityArr" :key="index" class="item">{{item}}</view>
        </picker-view-column>
      </picker-view>
    </view>
    <!--picker-view end-->

    <!--picker-view start-->
    <view class="cu-modal" :class="modalName==='langModal'?'show':''" @tap="hideModal">
      <view class="cu-dialog padding" @tap.stop="">
        <view class="psy-title">咨询语种
          <text class="psy-title-ext">（至少1项）</text>
        </view>
        <view class="grid col-4 padding-sm">
          <view v-for="(item,index) in langs" class="" :key="index">
            <view class='cu-tag radius cu-tag-lang margin-xs' :class="form.langList.includes(item)?'bg-orange':'line-orange'" @tap="langCheckbox" :data-value="item">{{item}}</view>
          </view>
        </view>
        <view class="flex justify-center margin-top">
          <view class="cu-btn round bg-white lg margin-right cu-tag-btn" @tap="hideModal">取消</view>
          <view class="cu-btn bg-orange round lg cu-tag-btn" @tap="setLang">确认</view>
        </view>
      </view>
    </view>

    <item-way ref="itemWay" @setWay="setWay"/>
    <!--picker-view end-->

    <view class="cu-modal" :class="modalName==='Image'?'show':''">
      <view class="cu-dialog">
        <view class="bg-img" style="background-image: url('/static/consult/partnerTypes.jpg');min-height:1480upx;width: 680upx">
          <view class="cu-bar justify-end text-white">
            <view class="action" @tap="hideModal">
              <text class="cuIcon-close "></text>
            </view>
          </view>
        </view>
        <view class="cu-bar bg-white">
          <view class="action margin-0 flex-sub  solid-left" @tap="hideModal">我知道了</view>
        </view>
      </view>
    </view>

    <view class="cu-bar foot bg-white tabbar border shop" style="z-index: 99">
      <view class="action" @tap="submit(1)">
        <view class="cuIcon-form"></view>存草稿
      </view>
      <view class="bg-orange round submit margin-xs" @tap="submit(2)">下一步</view>
    </view>

    <login ref="loginModel" :isNav="1"></login>
  </view>
</template>

<script>

import login from "@/components/common/login";
import serve from "@/server/consult/toPartner";
import cityUtil from "@/utils/pc-city";
import formValidation from "@/utils/formValidation";
import psyUpload from "@/components/common/psyUpload";
import psySteps from "@/components/common/psySteps";
import itemWay from "@/components/consult/toPartner/itemWay";
export default {
  name: "ConsultPartnerStep1",
  components: { login, psySteps, psyUpload, itemWay },
  data() {
    return {
      active: 0,
      modalName: '',
      items: [{
        name: '基础资料'
      }, {
        name: '学历&资历'
      }, {
        name: '专业经历'
      }, {
        name: '确认提交'
      }],
      picker: ['身份证', '护照', '回乡证（港澳台）'],
      langs: ['普通话', '英语', '日语', '韩语'],
      form: {
        id: null,
        step: 0,
        type: null,
        userId: null,
        sex: null,
        name: null,
        qq: null,
        wechat: null,
        phone: null,
        email: null,
        city: null,
        province: null,
        lang: null,
        langList: [],
        workHours: null,
        genre: null,
        card: null,
        cardImg: '',
        cardImgs: [],
      },
      types: [
        {
          value: '1',
          name: '合作型',
        },
        {
          value: '2',
          name: '签约型（团体督导）',
        },
        {
          value: '3',
          name: '签约型（个体督导）',
        }
      ],
      sexs: [
        {
          value: '1',
          name: '男',
        },
        {
          value: '2',
          name: '女',
        }
      ],
      value: [0, 0],
      animationData: "",
      showPickerStatus: false,
      cityData: cityUtil.getCascaderData(),
      proviceArr: [],
      cityArr: [],
    }
  },
  async onLoad() {
    // #ifdef H5
    this.$utils.share('壹加壹心理入驻申请', '欢迎入驻壹加壹心理咨询服务平台', '', 'https://wx.ssgpsy.com/pages/consult/toPartner/start')
    // #endif

    // 编辑时替换实际
    this.proviceArr = this.toArr(this.cityData)
    this.cityArr = this.toArr(this.cityData[0].children)

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
    async getApply() {
      const res = await serve.getInfo()
      console.log(res)
      if (res.code === 200 && res.data) {
        Object.assign(this.form, res.data)
      }
      console.log(this.form)
    },
    openStep() {
      this.modalName = 'Image'
      // uni.navigateTo({
      //   url: "/pages/consult/toPartner/notice"
      // });
    },
    /* picker-city start*/
    toArr(object) {
      let arr = [];
      for (let i in object) {
        arr.push(object[i].name);
      }
      return arr;
    },
    //picker change切换事件
    columnPicker(e) {
      this.value = e.detail.value
      this.cityArr = this.toArr(this.cityData[this.value[0]].children)
    },
    //确定按钮
    doPicker() {
        this.form.province = this.cityData[this.value[0]].name
        this.form.city = this.cityData[this.value[0]].children[this.value[1]].name
        this.showPickerStatus = false
    },
    // 显示picker-view
    showPicker() {
      const animation = uni.createAnimation({
        duration: 220,
        timingFunction: "linear",
        delay: 0
      });
      animation.translateY(500).step()
      this.animationData = animation.export()
      this.showPickerStatus = true

      setTimeout(function () {
        animation.translateY(0).step()
        this.animationData = animation.export()
      }.bind(this), 200)
    },
    // 隐藏picker-view
    hidePicker() {
      this.showPickerStatus = false
    },
    /* picker-city end*/
    showModal(val) {
      this.modalName = val
    },
    hideModal() {
      this.modalName = ''
    },
    setLang() {
      this.form.lang = this.form.langList.join(',')
      this.hideModal()
    },
    showWay() {
      this.$refs.itemWay.init(this.form.genre)
    },
    setWay(genre) {
      this.form.genre = genre
    },
    langCheckbox(e) {
      const val = e.currentTarget.dataset.value
      if (this.form.langList.includes(val)) {
        this.form.langList.splice(this.form.langList.findIndex(i => val === i), 1)
      } else {
        this.form.langList.push(val)
      }
      this.form.langList.sort()
    },
    typeChange(e) {
      console.log(e.detail.value)
      this.form.type = e.detail.value
    },
    sexChange(e) {
      this.form.sex = e.detail.value
    },
    cardChange(e) {
      this.form.card = this.picker[e.detail.value]
    },
    result1: function(e) {
      console.log(e)
      this.form.cardImg = e.imgArr.join(',');
      this.form.cardImgs = e.imgArr;
    },
    openLoginConfirm() {
      this.$refs.loginModel.open()
    },
    async submit(type) {
      if (!await this.$utils.checkLogin()) {
        return this.openLoginConfirm()
      }

      console.log(this.form)
      // 下一步
      if (type === 2) {
        //表单规则
        const rules = [
          { name: "type", rule: ["required"], msg: ["请选择申请类型"] },
          { name: "name", rule: ["required", "minLength:2", "maxLength:15"], msg: ["请输入姓名", "姓名必须2个或以上字符", "姓名不能超过15个字符"] },
          { name: "sex", rule: ["required"], msg: ["请选择性别"] },
          { name: "phone", rule: ["required", "isMobile"], msg: ["请输入手机号", "请输入正确的手机号"] },
          { name: "email", rule: ["required", "isEmail"], msg: ["请输入邮箱", "请输入正确的邮箱"] },
          { name: "wechat", rule: ["required", "minLength:2", "maxLength:50"], msg: ["请输入微信号", "微信号必须2个或以上字符", "微信号不能超过50个字符"] },
          { name: "qq", rule: ["required", "minLength:5", "maxLength:11"], msg: ["请输入qq号", "qq号最短为5位数字", "qq号不能超过11位数字"] },
          { name: "city", rule: ["required"], msg: ["请选择咨询地点"] },
          { name: "lang", rule: ["required"], msg: ["请选择咨询语种"] },
          { name: "workHours", rule: ["required"], msg: ["请输入咨询年限"] },
          { name: "genre", rule: ["required"], msg: ["请选择咨询流派"] },
          { name: "card", rule: ["required"], msg: ["请选择证件类型"] },
        ];

        //进行表单检查
        const checkRes = formValidation.validation(this.form, rules)
        console.log(checkRes)
        if (checkRes) {
          return uni.showToast({
            title: checkRes,
            icon: "none"
          });
        }

        if (this.form.cardImgs.length < 2) {
          return uni.showToast({
            icon: 'none',
            title: '请上传证件照片'
          })
        }

        if (this.form.step === 1) {
          this.form.step = 2
        }
      }

      const res = await serve.save(this.form)
      if (res.code === 200) {
        uni.showToast({
          icon: "success",
          title: "保存成功",
        });
      }

      this.getApply()

      if (type === 2) {
        uni.navigateTo({
          url: "/pages/consult/toPartner/step2"
        });
      }
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
  .cu-tag-lang {
    width: 130upx;
  }

  .cu-tag-btn {
    height: 60upx;
    width: 300upx;
    border: 1upx solid #ccc;
  }

  .psy-top {
    margin-top: 40upx;
  }

  .psy-top-xs {
    margin-top: 10upx;
  }

  .psy-top-sm {
    margin-top: 20upx;
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

  /* picker-city start*/
  .mask-screen {
    width: 100%;
    height: 100%;
    position: fixed;
    top: 0;
    left: 0;
    background: #000;
    opacity: 0.5;
    overflow: hidden;
    z-index: 9;
  }

  .picker-box {
    width: 100%;
    position: fixed;
    bottom: 0;
    z-index: 9999;
    background: #fff;
  }

  .picker-header {
    width: 100%;
    height: 90rpx;
    padding: 0 46rpx;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-sizing: border-box;
    font-size: 32rpx;
    background: #fff;
  }

  .list-item::after {
    left: 0;
  }

  .btn-cancle {
    padding: 20rpx;
    color: #888;
  }

  .btn-sure {
    padding: 20rpx;
    color: #FF703F;
  }

  .picker-view {
    width: 100%;
    height: 260px;
  }

  .item {
    line-height: 50px;
    text-align: center;
  }
  /* picker-city end*/

}

</style>