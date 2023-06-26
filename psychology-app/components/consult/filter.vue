<template>
  <view class="filter-container">
    <view class="index-title">
      <view class="filter-item" :class="{ active: filterParams.type === 1 }" @tap="doFilter(1)">咨询方向</view>
      <view class="filter-item" :class="{ active: filterParams.type === 2 }" @tap="doFilter(2)">价格</view>
      <view class="filter-item" :class="{ active: filterParams.type === 3 }" @tap="doFilter(3)">筛选</view>
    </view>
    
    <view class="consult-direction" v-if="filterParams.type === 1">
      <view class="consult-direction-content" v-for="item in attrList">
        <view class="consult-direction-title">
          {{ item.title }}
        </view>
        <view class="consult-direction-tabs">
          <view class="consult-direction-tab" @tap="selectAttr(item.title, 'all')">
            全部
          </view>
          <view class="consult-direction-tab" :class="{ selected: tab.flag }" v-for="tab in item.child" @tap="selectAttr(item.title, tab.value)">
            {{ tab.name }}
          </view>
        </view>
      </view>
    </view>
    <view class="price-filter" v-else-if="filterParams.type === 2">
      <view v-for="price in priceList" class="price-list">
        <view class="price-item" :class="{ selected: selectedPriceId === price.value }" @tap="selectPrice(price)">{{ price.name }}</view>
      </view>
    </view>
    <view class="consult-filter" v-else="filterParams.type === 3">
      <view class="consult-filter-tab-content">
        <view class="consult-filter-tab-title">
          咨询师性别
        </view>
        <view class="consult-filter-tabs">
          <view class="consult-filter-tab" @tap="selectSex(0)">
            不限
          </view>
          <view class="consult-filter-tab" :class="{ selected: item.flag }" v-for="item in sexList" @tap="selectSex(item.value)">
            {{ item.name }}性咨询师
          </view>
        </view>
      </view>

      <view class="consult-filter-tab-content">
        <view class="consult-filter-tab-title">
          咨询师方式
        </view>
        <view class="consult-filter-tabs">
          <view class="consult-filter-tab" @tap="selectSex(0)">
            不限
          </view>
          <view class="consult-filter-tab" :class="{ selected: item.flag }" v-for="item in typeList" @tap="selectType(item.value)">
            {{ item.name }}
          </view>
        </view>
      </view>
    </view>
    
    <view class="footer">
      <view class="reset" @tap="reset">重置</view>
      <view class="confirm" @tap="confirm">确定</view>
    </view>
  </view>
</template>

<script>
  export default {
    props: ['filterParams','attrList','sexList','typeList','priceList'],
    data() {
      return {
        sexAll: false,
        typeAll: false,
        selectedPriceId: 0,
        selectedPriceName: '',
      }
    },
    created() {
      this.selectedPriceId = this.filterParams.price.id
      this.selectedPriceName = this.filterParams.price.name
      this.sexAll = this.sexList.length > 0 && this.sexList.length === this.sexList.filter(i => i.flag).length
      this.typeAll = this.typeList.length > 0 && this.typeList.length === this.typeList.filter(i => i.flag).length
      console.log(this.sexList)
      console.log(this.typeList)
    },
    methods: {
      doFilter(type) {
        this.filterParams.type = type
      },
      selectAttr(title, val) {
        this.attrList.forEach(node => {
          if (node.title === title) {
            if (val === 'all') {
              node.flag = !node.flag
              node.child.forEach(item => {
                item.flag = node.flag
              })
            } else {
              node.child.forEach(item => {
                if (item.value === val) {
                  item.flag = !item.flag
                }
              })
              if (node.child.filter(i => i.flag).length === node.child.length) {
                node.flag = true
              }
            }
          }
        })
      },
      selectSex(val) {
        this.sexList.forEach(i => {
          if (val === 0) {
            i.flag = !this.sexAll
          } else if (i.value === val) {
            i.flag = !i.flag
          }
        })
        if (val !== 0) {
          this.sexAll = this.sexList.length > 0 && this.sexList.length === this.sexList.filter(i => i.flag).length
        }
      },
      selectType(val) {
        this.typeList.forEach(i => {
          if (val === 0) {
            i.flag = !this.typeAll
          } else if (i.value === val) {
            i.flag = !i.flag
          }
        })
        this.typeAll = this.typeList.length > 0 && this.typeList.length === this.typeList.filter(i => i.flag).length
      },
      selectPrice(price) {
        this.selectedPriceId = price.value
        this.selectedPriceName = price.name
      },
      reset() {
        switch(this.filterParams.type) {
          case 1: 
          
          break;
          case 2:
          this.selectedPriceId = 0
          this.selectedPriceName = ''
          break;
          case 3:
          
          break;
        }
      },
      confirm() {
        switch(this.filterParams.type) {
          case 1: 
          
          break;
          case 2:
          this.filterParams.price.id = this.selectedPriceId
          this.filterParams.price.name = this.selectedPriceName
          break;
          case 3:
          
          break;
        }
        this.$emit('close')
      }
    }
  }
</script>

<style lang="scss" scoped>
  .filter-container {
    width: 100%;
    padding: 26upx;
    .index-title {
      display: flex;
      margin-bottom: 32upx;
      .filter-item {
        position: relative;
        margin-right: 84upx;
        font-size: 26upx;
        font-weight: 400;
        color: #777777;
        line-height: 37upx;
        
        &::after {
          content: "";
          position: absolute;
          border: 4px solid transparent;
          border-top-color: #777777;
          top: 8px;
          right: -15px;
        }
      }
      .active {
        color: #FF703F;
        &::after {
          border: 4px solid transparent;
          border-bottom-color: #FF703F;
          top: 4px;
        }
      }
    }

    .consult-direction {
      .consult-filter-tab-content {
        padding-top: 18upx;
        padding-top: 18upx;

      }
      .consult-direction-title {
        height: 42upx;
        font-size: 30upx;
        font-weight: 600;
        color: #333333;
        line-height: 42upx;
      }
      .consult-direction-tabs {
        display: flex;
        flex-wrap: wrap;
        box-sizing: border-box;
      }
      .consult-direction-tab {
        border-radius: 8upx;
        width: 150upx;
        height: 60upx;
        line-height: 60upx;
        background: #F8F8F8;
        margin: 10upx 10upx 10upx 0;
        text-align: center;
        font-size: 24upx;
        font-weight: 400;
      }
      .selected {
        background: #FF703F;
        color: #FFFFFF;
      }
    }
    
    .price-filter {
      .price-list {
        .price-item {
          margin-bottom: 32upx;
          font-size: 26upx;
          font-weight: 400;
          color: #333333;
          line-height: 37upx;
        }
        .selected {
          font-weight: 600;
          color: #FF703F;
        }
      }
    }

    .consult-filter {
      .consult-filter-content {
        padding-top: 18upx;
        padding-top: 18upx;

      }
      .consult-filter-tab-title {
        height: 42upx;
        font-size: 30upx;
        font-weight: 600;
        color: #333333;
        line-height: 42upx;
      }
      .consult-filter-tabs {
        display: flex;
        flex-wrap: wrap;
        box-sizing: border-box;
      }
      .consult-filter-tab {
        border-radius: 8upx;
        width: 150upx;
        height: 60upx;
        line-height: 60upx;
        background: #F8F8F8;
        margin: 10upx 10upx 10upx 0;
        text-align: center;
        font-size: 24upx;
        font-weight: 400;
      }
      .selected {
        background: #FF703F;
        color: #FFFFFF;
      }
    }
    
    .footer {
      display: flex;
      margin-top: 100px;
      .reset {
        height: fit-content;
        border-radius: 40upx;
        border: 1upx solid #777777;
        padding: 20upx 142upx;
        font-size: 28upx;
        font-family: PingFangSC-Regular, PingFang SC;
        font-weight: 400;
        color: #333333;
        line-height: 40upx;
        margin-right: 24upx;
      }
      .confirm {
        height: fit-content;
        background: #FF703F;
        border-radius: 40upx;
        padding: 20upx 142upx;
        font-size: 28upx;
        font-family: PingFangSC-Semibold, PingFang SC;
        font-weight: 600;
        color: #FFFFFF;
        line-height: 40upx;
      }
    }
  }
</style>