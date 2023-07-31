<template>
    <scroll-view scroll-y scroll-with-animation class="filter-container">
    <view class="index-title">
      <view class="filter-item" :class="{ active: filterParams.type === 1 }" @tap="doFilter(1)">擅长领域</view>
      <view class="filter-item" :class="{ active: filterParams.type === 2 }" @tap="doFilter(2)">价格</view>
      <view class="filter-item" :class="{ active: filterParams.type === 3 }" @tap="doFilter(3)">筛选</view>
    </view>

    <view class="consult-direction" v-if="filterParams.type === 1">
      <view class="consult-direction-content" v-for="item in attrParams.attrList">
        <view class="consult-direction-title">
          {{ item.label }}
          <view class="consult-direction-tab-more">可多选</view>
        </view>
        <view class="consult-direction-tabs">
          <view class="consult-direction-tab" @tap="selectAttr(item.value, 'all')">
            全部
          </view>
          <view class="consult-direction-tab" :class="{ selected: tab.flag }" v-for="tab in item.children" @tap="selectAttr(item.value, tab.value)">
            {{ tab.label }}
          </view>
        </view>
      </view>
    </view>
    <view class="price-filter" v-else-if="filterParams.type === 2">
      <view v-for="item in attrParams.priceList" class="price-list">
        <view class="price-item" :class="{ selected: filterParams.price !== null && filterParams.price === item.name }" @tap="selectType(item.name, 'price')">{{ item.name }}</view>
      </view>
    </view>
    <view class="consult-filter" v-else-if="filterParams.type === 3">
      <view class="consult-filter-tab-content">
        <view class="consult-filter-tab-title">
          咨询师性别
        </view>
        <view class="consult-filter-tabs">
          <view class="consult-filter-tab" @tap="selectType('不限', 'sex')">
            不限
          </view>
          <view class="consult-filter-tab" :class="{ selected: filterParams.sex !== null && item.name === filterParams.sex }" v-for="item in attrParams.sexList" @tap="selectType(item.name, 'sex')">
            {{ item.name }}性咨询师
          </view>
        </view>
      </view>

      <view class="consult-filter-tab-content">
        <view class="consult-filter-tab-title">
          咨询师方式
        </view>
        <view class="consult-filter-tabs">
          <view class="consult-filter-tab" @tap="selectType('不限', 'serve')">
            不限
          </view>
          <view class="consult-filter-tab" :class="{ selected: filterParams.serve !== null && item.value === filterParams.serve }" v-for="item in attrParams.typeList" @tap="selectType(item.value, 'serve')">
            {{ item.name }}
          </view>
        </view>
      </view>

      <view class="consult-filter-tab-content">
        <view class="consult-filter-tab-title">
          咨询时段
          <view class="consult-filter-tab-more">可多选</view>
        </view>
        <view>
          <uni-grid :column="7" borderColor="#fff">
            <uni-grid-item v-for="item in attrParams.dateList">
              <view class="grid-item-box" @tap="selectType(item.date, 'days')">
                <view class="grid-item-box-month">{{ item.month }}</view>
                <view class="grid-item-box-item" :class="{ selected: filterParams.days.includes(item.date) }">
                  <view class="grid-item-box-item-day" :class="{ selected: filterParams.days.includes(item.date) }">{{ item.day }}</view>
                  <view class="grid-item-box-item-week" :class="{ selected: filterParams.days.includes(item.date) }">{{ item.week }}</view>
                </view>
              </view>
            </uni-grid-item>
          </uni-grid>
        </view>
      </view>

      <view class="consult-filter-tab-content">
        <view class="consult-filter-tab-title">
          咨询时段
        </view>
        <view class="consult-filter-tabs">
          <view class="consult-filter-tab" :class="{ selected: filterParams.time !== null && item.name === filterParams.time }" v-for="item in attrParams.timeList" @tap="selectType(item.name, 'time')">
            {{ item.name }}
          </view>
        </view>
      </view>
    </view>
    
    <view class="footer">
      <view class="reset" @tap="reset">重置</view>
      <view class="confirm" @tap="confirm">确定</view>
    </view>
    </scroll-view>
</template>

<script>
  export default {
    props: ['filterParams','attrParams'],
    data() {
      return {
      }
    },
    created() {
      console.log(this.attrParams.sexList)
      console.log(this.attrParams.typeList)
    },
    methods: {
      doFilter(type) {
        this.filterParams.type = type
        this.reset()
      },
      selectAttr(value, val) {
        this.attrParams.attrList.forEach(node => {
          if (node.value === value) {
            if (val === 'all') {
              node.flag = !node.flag
              node.children.forEach(item => {
                item.flag = node.flag
              })
            } else {
              node.children.forEach(item => {
                if (item.value === val) {
                  item.flag = !item.flag
                }
              })
              if (node.children.filter(i => i.flag).length === node.children.length) {
                node.flag = true
              }
            }
          }
        })
      },
      selectType(val, type) {
        if (type === 'days') {
          if (this.filterParams.days.includes(val)) {
            this.filterParams.days.splice(this.filterParams.days.findIndex(a => a === val), 1)
          } else {
            this.filterParams.days.push(val)
          }
        } else {
          this.filterParams[type] = this.filterParams[type] === val ? undefined : val
        }
      },
      reset() {
        this.attrParams.attrList.forEach(a => {
          a.flag = false
          if (a.children && a.children.length > 0) {
            a.children.forEach(b => {
              b.flag = false
            })
          }
        })
        this.filterParams.way = []
        this.filterParams.days = []
        this.filterParams.sex = null
        this.filterParams.time = null
        this.filterParams.serve = null
        this.filterParams.price = null
        this.filterParams.dayType = null
      },
      confirm() {
        switch(this.filterParams.type) {
          case 1:
            const attr = []
            this.attrParams.attrList.forEach(a => {
              if (a.children && a.children.length > 0) {
                a.children.filter(it => it.flag).forEach(b => {
                  attr.push(b.value)
                })
              }
            })

            console.log(attr)
            this.filterParams.way = attr
          break;
          case 2:
          case 3:
            console.log(this.filterParams)
          break;
        }
        this.$emit('submit')
      }
    }
  }
</script>

<style lang="scss" scoped>
  .filter-container {
    padding: 26upx;
    padding-bottom: 100upx;
    height: 740upx;
    .index-title {
      display: flex;
      position: fixed;
      z-index: 9;
      top: 0;
      height: 88upx;
      width: 100%;
      background-color: #FFFFFF;
      align-items: center;
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
      margin-top: 60upx;
      .consult-direction-title {
        height: 42upx;
        font-size: 30upx;
        font-weight: 600;
        color: #333333;
        line-height: 42upx;
        position: relative;
        margin-bottom: 6upx;
        margin-top: 6upx;
      }
      .consult-direction-tab-more {
        position: absolute;
        top: 10upx;
        right: 80upx;
        color: #777777;
        font-size: 24upx;
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
      margin-top: 60upx;
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
      margin-top: 60upx;
      .consult-filter-tab-content {
        margin-top: 26upx;
      }
      .consult-filter-tab-title {
        height: 42upx;
        font-size: 30upx;
        font-weight: 600;
        color: #333333;
        line-height: 42upx;
        position: relative;
        margin-bottom: 20upx;
      }
      .consult-filter-tab-more {
        position: absolute;
        top: 10upx;
        right: 60upx;
        color: #777777;
        font-size: 24upx;
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
      .grid-item-box {
        background-color: #fff;
        display: flex;
        justify-content: center;
        text-align: center;
        flex-wrap: wrap;
      }
      .grid-item-box-month {
        color: #AAAAAA;
        font-size: 24upx;
      }
      .grid-item-box-item {
        width: 72rpx;
        height: 86rpx;
        border-radius: 4rpx;
        margin-top: 6upx;
        .grid-item-box-item-day {
          font-size: 30upx;
          font-weight: 600;
          color: #333333;
          .selected {
            color: #FFFFFF;
          }
        }
        .grid-item-box-item-week {
          font-size: 24upx;
          font-weight: 400;
          color: #777777;
          .selected {
            color: #FFFFFF;
          }
        }
        .selected {
          background: #FF703F;
          color: #FFFFFF;
        }
      }
    }
    
    .footer {
      display: flex;
      position: fixed;
      z-index: 9;
      bottom: 0;
      height: 92upx;
      width: 100%;
      background-color: #FFFFFF;
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