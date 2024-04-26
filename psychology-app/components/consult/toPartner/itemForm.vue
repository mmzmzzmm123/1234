<template>
  <view>
    <view class="cu-form-group margin-top">
      <template v-if="[2,5,6].includes(type)">
        <view class="title">{{ title1[type-1] }}</view>
        <picker @change="pickerChange" :value="idx" :range="picker">
          <view class="picker">
            {{idx>-1?picker[idx]:'请选择'}}
          </view>
        </picker>
      </template>
      <template v-else>
        <view class="title">{{ title1[type-1] }}</view>
        <input placeholder="请输入" maxlength="100" placeholder-class="plaClass" v-model="form.param1"/>
      </template>
    </view>
    <view class="cu-form-group" v-if="[1,4,5,6].includes(type)">
      <view class="title">开始时间</view>
      <picker mode="date" fields="month" :value="form.startTime" start="1990-01" end="2030-09" @change="e => form.startTime = e.detail.value">
        <view class="picker">
          {{ form.startTime || '请选择' }}
        </view>
      </picker>
    </view>
    <view class="cu-form-group" v-if="[1,4,5,6].includes(type)">
      <view class="title">结束时间</view>
      <picker mode="date" fields="month" :value="form.endTime" start="1990-01" end="2030-09" @change="e => form.endTime = e.detail.value">
        <view class="picker">
          {{ form.endTime || '请选择' }}
        </view>
      </picker>
    </view>
    <view class="cu-form-group" v-if="[1,2,4,5,6].includes(type)">
      <view class="title">{{ title2[type-1] }}</view>
      <input placeholder="请输入" maxlength="100" placeholder-class="plaClass" v-model="form.param2"/>
    </view>
    <view class="cu-form-group" v-if="[1,5,6].includes(type)">
      <view class="title">{{ title3[type-1] }}</view>

      <template v-if="type === 6">
        <view @tap="showWay">
          <template v-if="form.param3">
            <view class="text-right text-cut" style="width: 200px">{{ form.param3 }}</view>
          </template>
          <template v-else>
            <text class="">请选择<text class="margin-left-xs cuIcon-right text-lg text-grey"></text></text>
          </template>
        </view>
      </template>
      <template v-else>
        <input placeholder="请输入" maxlength="100" placeholder-class="plaClass" v-model="form.param3"/>
      </template>
    </view>
    <view class="cu-form-group" v-if="[4,5,6].includes(type)">
      <view class="title">总计小时数</view>
      <input type="number" maxlength="11" placeholder="请输入" placeholder-class="plaClass" v-model="form.num"/>
    </view>
    <template v-if="type !== 7">
      <view class="cu-bar bg-white solid-top">
        <view class="action">
          证明照片（点击上传图片,5M以内）
        </view>
        <view class="action">
          {{form.imgList.length}}/{{limit}}
        </view>
      </view>
      <view class="psy-box-upload">
        <psy-upload :value="form.imgList" :limit="limit" @complete="result"></psy-upload>
      </view>
    </template>

    <item-way ref="itemWay" @setWay="setWay"/>

    <view class="flex justify-center">
      <view class="cu-btn line-black round lg btn-submit" @tap="cancel">取消</view>
      <view class="cu-btn bg-orange round lg btn-submit" @tap="add">确认</view>
    </view>
  </view>
</template>

<script>
import formValidation from "@/utils/formValidation";
import psyUpload from "@/components/common/psyUpload";
import itemWay from "./itemWay";

export default {
  components: {
    psyUpload,
    itemWay
  },
  name: "ItemForm",
  props: {
    limit: {
      type: Number,
      default: 2
    },
    type: {
      type: Number,
      default: 1
    },
  },
  data() {
    return {
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
      picker: [],
      idx: -1,
      title1: ['学校名称','资质类型','证书名称','培训名称','咨询类型','督导类型','个人体验经历'],
      title2: ['专业名称','证书编号','','主办机构','负责人姓名','督导姓名',''],
      title3: ['获得学位','','','','机构联系方式','督导取向',''],
    };
  },
  methods: {
    initData(form, picker = []) {
      this.form = form
      this.picker = picker
    },
    initIdx(idx) {
      this.idx = idx
    },
    pickerChange(e) {
      const idx = e.detail.value
      this.idx = idx
      this.form.param1 = this.picker[idx]
    },
    showWay() {
      this.$refs.itemWay.init(this.form.param3)
    },
    setWay(genre) {
      this.form.param3 = genre
    },
    add() {
      // 表单校验
      const title1 = this.title1[this.type - 1]
      let rules = [
        { name: "param1", rule: ["required", "minLength:2"], msg: [`请输入${title1}`, `${title1}必须2个或以上字符`] }
      ]

      if ([1,4,5,6].includes(this.type)) {
        rules.push({ name: "startTime", rule: ["required"], msg: ["请选择开始时间"] })
        rules.push({ name: "endTime", rule: ["required"], msg: ["请选择结束时间"] })
      }

      if ([1,2,4,5,6].includes(this.type)) {
        const title2 = this.title2[this.type - 1]
        rules.push( { name: "param2", rule: ["required", "minLength:2"], msg: [`请输入${title2}`, `${title2}必须2个或以上字符`] })
      }

      if ([1,5,6].includes(this.type)) {
        const title3 = this.title3[this.type - 1]
        rules.push( { name: "param3", rule: ["required", "minLength:2"], msg: [`请输入${title3}`, `${title3}必须2个或以上字符`] })
      }

      if ([4,5,6].includes(this.type)) {
        rules.push( { name: "num", rule: ["required"], msg: ['请输入总计小时数'] })
      }

      const checkRes = formValidation.validation(this.form, rules)
      if (checkRes) {
        return uni.showToast({
          title: checkRes,
          icon: "none"
        });
      }

      if (![5, 7].includes(this.type) && this.form.imgList.length === 0) {
        return uni.showToast({
          title: '请上传证明图片',
          icon: "none"
        });
      }

      this.$emit('add', this.form)
    },
    cancel() {
      this.idx = -1
      this.$emit('cancel')
    },
    result: function(e) {
      console.log(e)
      this.form.imgList = e.imgArr;
    },
  }
}
</script>

<style lang="scss">
.cu-form-group {
  height: 80upx !important;
}

.cu-form-group input {
  text-align: right;
}

.cu-form-group .title {
  min-width: calc(4em + 15px);
}

.plaClass {
  color: #8799a3;
}
.btn-submit {
  width: 300upx;
  margin: 10upx;
}

</style>