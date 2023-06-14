<template>
  <view class="filter-container">
    <view class="index-title">
      <view class="filter-item" :class="{ active: filterParams.type === 1 }" @tap="doFilter(1)">咨询方向</view>
      <view class="filter-item" :class="{ active: filterParams.type === 2 }" @tap="doFilter(2)">价格</view>
      <view class="filter-item" :class="{ active: filterParams.type === 3 }" @tap="doFilter(3)">筛选</view>
    </view>
    
    <view class="consult-direction" v-if="filterParams.type === 1">1</view>
    <view class="price-filter" v-else-if="filterParams.type === 2">
      <view v-for="price in priceList" class="price-list">
        <view class="price-item" :class="{ selected: selectedPriceId === price.id }" @tap="selectPrice(price)">{{ price.name }}</view>
      </view>
    </view>
    <view class="filter" v-else="filterParams.type === 3">3</view>
    
    <view class="footer">
      <view class="reset" @tap="reset">重置</view>
      <view class="confirm" @tap="confirm">确定</view>
    </view>
  </view>
</template>

<script>
  export default {
    props: ['filterParams'],
    data() {
      return {
        priceList: [
          {
            id: 1,
            name: '不限'
          },
          {
            id: 2,
            name: '500元以下'
          },
          {
            id: 3,
            name: '500-700元'
          },
          {
            id: 4,
            name: '700-900元'
          },
          {
            id: 5,
            name: '900元以上'
          },
        ],
        selectedPriceId: 0,
        selectedPriceName: '',
      }
    },
    methods: {
      doFilter(type) {
        this.filterParams.type = type
      },
      selectPrice(price) {
        this.selectedPriceId = price.id
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
    },
    created() {
      this.selectedPriceId = this.filterParams.price.id
      this.selectedPriceName = this.filterParams.price.name
    },    
  }
</script>

<style lang="scss" scoped>
  .filter-container {
    width: 100%;
    padding: 24upx;
    .index-title {
      display: flex;
      margin-bottom: 32upx;
      .filter-item {
        position: relative;
        margin-right: 84upx;
        font-size: 26rpx;
        font-weight: 400;
        color: #777777;
        line-height: 37rpx;
        
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
    
    .price-filter {
      .price-list {
        .price-item {
          margin-bottom: 32upx;
          font-size: 26rpx;
          font-family: PingFangSC-Regular, PingFang SC;
          font-weight: 400;
          color: #333333;
          line-height: 37rpx;
        }
        .selected {
          font-family: PingFangSC-Semibold, PingFang SC;
          font-weight: 600;
          color: #FF703F;
        }
      }
    }
    
    .footer {
      display: flex;
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
        font-size: 28rpx;
        font-family: PingFangSC-Semibold, PingFang SC;
        font-weight: 600;
        color: #FFFFFF;
        line-height: 40rpx;
      }
    }
  }
</style>