<template>
  <view class="cu-modal" :class="modalName==='wayModal'?'show':''" @tap="hideModal">
    <view class="cu-dialog bg-white padding" @tap.stop="">
      <view class="psy-title">咨询流派
        <text class="psy-title-ext">（1-3项）</text>
      </view>
      <view class="grid col-4 padding-sm">
        <view v-for="(item,index) in ways" class="" :key="index">
          <view class='cu-tag radius cu-tag-lang margin-xs' :class="genreList.includes(item)?'bg-orange':'line-orange'" @tap="wayCheckbox" :data-value="item">{{item}}</view>
        </view>
      </view>
      <view class="cu-form-group solid">
        <view class="title">其他</view>
        <input v-model="extGenre" maxlength="50" placeholder="请输入" placeholder-class="plaClass" />
      </view>
      <view class="flex justify-center margin-top">
        <view class="cu-btn round bg-white lg margin-right cu-tag-btn" @tap="hideModal">取消</view>
        <view class="cu-btn bg-orange round lg cu-tag-btn" @tap="setWay">确认</view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  name: "ItemWay",
  data() {
    return {
      modalName: '',
      genreList: [],
      extGenre: '',
      ways: ['精神分析', '认知行为', '存在主义', '人本主义','完全疗法', '正念疗法', '叙事疗法', '焦点解决','艺术治疗', '舞动治疗', '躯体治疗', '家庭治疗', 'EFT 治疗']
    };
  },
  methods: {
    init(val) {
      this.modalName = 'wayModal'
      if (val) {
        const list = val.split(',')
        const genreList = []
        const extGenre = []
        list.sort().forEach(i => {
          if (this.ways.includes(i)) {
            genreList.push(i)
          } else {
            extGenre.push(i)
          }
        })

        this.genreList = genreList
        this.extGenre = extGenre.join(',')
      }
    },
    wayCheckbox(e) {
      const val = e.currentTarget.dataset.value

      if (this.genreList.includes(val)) {
        this.genreList.splice(this.genreList.findIndex(i => val === i), 1)
      } else if (this.genreList.length < 3) {
        this.genreList.push(val)
      }
      this.genreList.sort()
    },
    setWay() {
      const item = this.genreList.concat(this.extGenre).join(',')
      this.$emit('setWay', item)
      this.hideModal()
    },
    showModal(val) {
      this.modalName = val
    },
    hideModal() {
      this.modalName = ''
    },
  }
}
</script>

<style lang="scss">
.plaClass {
  color: #8799a3;
}
.cu-form-group .title {
  min-width: calc(4em + 15px);
}
.cu-form-group input {
  text-align: right;
}
.cu-tag-btn {
  height: 60upx;
  width: 300upx;
  border: 1upx solid #ccc;
}

</style>