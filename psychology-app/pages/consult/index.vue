<template>
  <view class="index">
    <image src="/static/consult/kefu.png" class="kefu" @tap="kefu"/>
    <view class="search-box index-margin" @tap="toSearch">
<!--      <img class="icon" src="/static/icon/search.png" />-->
<!--      <span class="txt">搜索</span>-->
      <view class="uni-input-wrapper">
        <uni-easyinput :inputBorder="false" prefixIcon="search" :styles="styles" :placeholderStyle="placeholderStyle" v-model="queryData.userName" @confirm="inputConfirm" @clear="inputConfirm" placeholder="请输入咨询师名称">
        </uni-easyinput>
      </view>
    </view>
    <view class="banner-box index-margin">
      <swiper class="ad-swiper" indicator-dots circular indicator-color="rgb(255, 255, 255, .5)"
        indicator-active-color="#FFFFFF">
        <swiper-item v-for="(item, index) in bannerList" :key="index" @tap="toClass(item)">
          <image class="banner-img" :src="item.url"/>
        </swiper-item>
      </swiper>
    </view>
    <view class="class-box index-margin">
      <view class="item" v-for="item in classList" @tap="toClass(item)">
        <image class="class-img" :src="item.url" />
        <view>{{ item.name }}</view>
      </view>
    </view>
    <view class="notice-box index-margin" v-if="notices.length > 0">
      <image src="/static/consult/notice.png" class="notice-icon"/>
      <swiper class="notice-swiper" autoplay vertical circular>
        <swiper-item v-for="(item, index) in notices" :key="index">
          <div class="notice-swiper-item">{{ item }}</div>
        </swiper-item>
      </swiper>
    </view>


    <view class="consult-box index-margin">
      <view class="index-title">
        <view class="consult-direction" @tap="doFilter(1)">擅长领域</view>
        <view class="price-filter" @tap="doFilter(2)">{{ filterParams.price || '价格' }}</view>
<!--        <view class="price-filter" @tap="doFilter(4)">{{ filterParams.city || '城市' }}</view>-->
        <view class="filter" @tap="doFilter(3)">筛选</view>
      </view>
      <consultant-list :consultantList="consultantList" @toConsultant="toConsultant"></consultant-list>
    </view>
    <consult-tab-bar :currentIndex="0"></consult-tab-bar>

    <!-- 筛选组件 -->
    <uni-popup ref="popupFilter" type="top" background-color="#fff">
      <filter-com :filterParams="filterParams"  :attrParams="attrParams" @close="doFilterClose" @submit="submit"/>
    </uni-popup>
          
    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="close"
        @confirm="confirm"/>
    </uni-popup>
  </view>
</template>
<script>
  import consultantList from '@/components/consult/consultantList.vue'
  import filterCom from '@/components/consult/filter.vue'
  import indexServer from '@/server/consult/index'
  import loginServer from '@/server/login'
  import {
    uniPopup,
    uniPopupDialog
  } from '@dcloudio/uni-ui'
  import utils from "@/utils/common";
  import classServer from '@/server/consult/class'
  export default {
    components: {
      consultantList,
      filterCom,
      uniPopup,
      uniPopupDialog
    },
    data() {
      return {
        inputValue: '',
        placeholderStyle: "color:#AAAAAA;",
        styles: {
          borderColor: '#fff'
        },
        total: 0,
        pageNum: 1,
        pageSize: 10,
        queryData: {
          catId: null,
          userName: null,
          sex: null,
          lowPrice: null,
          highPrice: null,
          serve: null,
          type: null,
          city: null,
          time: null,
          province: null,
          days: [],
          way: [],
          nand: null,
          serveId: null,
          buy: null,
          single: null
        },
        notices: [],
        bannerList: [],
        classList: [],
        consultantList: [],
        userInfo: {},
        filterParams: {
          type: 0,
          way: [],
          days: [],
          sex: null,
          time: null,
          serve: null,
          price: null,
          city: null,
          province: '不限',
          dayType: null
        },
        attrParams: {
          dateList: [],
          timeList: [],
          attrList: [],
          sexList: [],
          typeList: [],
          priceList: []
        },
        showFilter: false
      };
    },
    async mounted() {
      // #ifdef H5
      utils.share('壹加壹心理', '壹加壹心理')
      // #endif

      this.userInfo = utils.getUserInfo()

      this.getCat()
      this.getBanner(0)
      this.getConsult()
      this.getNotices()

      this.getTypes()
      this.getAttrs()
      this.getDates()
      this.getPrice()
      this.getSex()
      this.getConsultTime()
      if (!this.userInfo && await utils.loginCallback(this.redirectUri)) {
        this.userInfo = utils.getUserInfo()
      }

      // if (!utils.checkLogin()) {
      //   return this.openLoginConfirm()
      // }
    },
    methods: {
      kefu() {
        window.location.href = 'https://work.weixin.qq.com/kfid/kfc4e52289a3117e650'
      },
      async onPullDownRefresh() {
        this.resetQuery()
        this.resetFilterParams()

        await this.getConsult()
        uni.stopPullDownRefresh();
      },
      toConsultant(id) {
        uni.navigateTo({
          url: "/pages/consult/consultant?id=" + id,
        });
      },
      resetFilterParams() {
        this.filterParams = {
          type: 0,
          way: [],
          days: [],
          sex: null,
          time: null,
          serve: null,
          price: null,
          city: null,
          province: '不限',
          dayType: null
        }
      },
      resetQuery() {
        this.total = 0
        this.pageNum = 1
        this.queryData.catId = null
        this.queryData.userName = null
        this.queryData.sex = null
        this.queryData.lowPrice = null
        this.queryData.highPrice = null
        this.queryData.serve = null
        this.queryData.time = null
        this.queryData.nand = null
        this.queryData.serveId = null
        this.queryData.buy = null
        this.queryData.single = null
        this.queryData.days = []
        this.queryData.way = []
      },
      inputConfirm(val) {
        this.resetQuery()
        this.resetFilterParams()

        this.queryData.userName = val
        this.getConsult()
      },
      async getNotices() {
        this.notices = await indexServer.getNotices();
      },
      async getCat() {
        const classData = await classServer.getClassList()
        this.classList = classData.slice(0, 4) // 取前8个类别
      },
      async getTypes() {
        this.attrParams.typeList = await indexServer.getConfigByType('consult_type');
      },
      async getAttrs() {
        // this.attrParams.attrList = await indexServer.getAttrs(['consult_direction_one','consult_direction_two','consult_direction_three']);
        this.attrParams.attrList = await indexServer.getTypes();
      },
      async getPrice() {
        this.attrParams.priceList = await indexServer.getConfigByType('consult_price');
      },
      async getDates() {
        this.attrParams.dateList = await indexServer.getDates(7);
      },
      async getSex() {
        this.attrParams.sexList = await indexServer.getConfigByType('consult_sex');
      },
      async getConsultTime() {
        this.attrParams.timeList = await indexServer.getConfigByType('consult_time');
      },
      async getBanner(type) {
        this.bannerList = await indexServer.getBannerList(type);
      },
      async getConsult() {
        if (this.pageNum === 1) {
          this.consultantList = []
        }

        const res = await indexServer.getConsult(this.queryData, this.pageNum, this.pageSize)
        if (res.code === 200) {
          this.total = res.total
          this.consultantList = [...this.consultantList, ...res.rows]
        }
      },
      toClass(item) {
        if (item.type === '1' || item.cat === '1') {
          this.resetQuery()
          this.resetFilterParams()

          this.queryData.buy = item.buy
          this.queryData.single = item.single
          this.queryData.nand = item.nand

          if (item.serve && item.serve === '0') {
            this.queryData.serveId = item.serveId
          }
          if (item.price && item.price === '0') {
            this.queryData.lowPrice = item.lowPrice
            this.queryData.highPrice = item.highPrice
          }

          return this.submit()
        }

        if (item.linkUrl) {
          uni.navigateTo({
            url: item.linkUrl,
          });
        }
      },
      toSearch() {
        uni.navigateTo({
          url: "/pages/consult/search",
        });
      },
      doFilter(type) {
        this.resetQuery()
        this.$set(this.filterParams, 'type', type)
        // this.showFilter = true
        this.$refs.popupFilter.open()
      },
      submit() {
        this.pageNum = 1
        this.queryData.catId = null
        this.queryData.userName = null
        switch (this.filterParams.type) {
          case 1:
            this.queryData.way = this.filterParams.way
            break
          case 2:
            if (this.filterParams.price) {
              switch (this.filterParams.price) {
                case '200元以下':
                  this.queryData.lowPrice = 0
                  this.queryData.highPrice = 199
                  break
                case '200-300元':
                  this.queryData.lowPrice = 200
                  this.queryData.highPrice = 299
                  break
                case '300-400元':
                  this.queryData.lowPrice = 300
                  this.queryData.highPrice = 399
                  break
                case '400-500元':
                  this.queryData.lowPrice = 400
                  this.queryData.highPrice = 499
                  break
                case '500元以上':
                  this.queryData.lowPrice = 500
                  this.queryData.highPrice = 99999
                  break
              }
            }
            break
          case 3:
            this.queryData.days = this.filterParams.days
            this.queryData.sex = this.filterParams.sex
            this.queryData.time = this.filterParams.time
            this.queryData.serve = this.filterParams.serve
            this.queryData.way = this.filterParams.way
            this.queryData.dayType = this.filterParams.dayType
            break
          case 4:
            this.queryData.city = null
            this.queryData.province = null

            if (this.filterParams.province && this.filterParams.province !== '不限') {
              this.queryData.province = this.filterParams.province
              this.queryData.city = this.filterParams.city && this.filterParams.city !== '不限' ? this.filterParams.city : null
            }

            break
        }
        this.getConsult()
        this.doFilterClose()
      },
      doFilterClose() {
        this.$refs.popupFilter.close()
      },
      close() {
        this.$refs.popup.close()
      },
      async confirm() {
        await loginServer.login();
        this.$refs.popup.close()
      },
      openLoginConfirm() {
        this.$refs.popup.open();
      },
      async loadMore() {
        if (this.consultantList.length < this.total) {
          this.pageNum += 1
          await this.getConsult()
        }
      },
      onReachBottom() {
        this.loadMore()
      }
    },
  };
</script>
<style lang="scss" scoped>
  @import "@/style/common.scss";

  page {
    .index {
      padding-bottom: calc(constant(safe-area-inset-bottom) + 50px); /* 兼容 iOS 设备 */
      padding-bottom: calc(env(safe-area-inset-bottom) + 50px); /* 兼容 iPhone X 及以上设备 */
    }
    .kefu {
      width: 80upx;
      height: 80upx;
      position: fixed;
      top: 900upx;
      right: 24upx;
      z-index: 9999;
    }
    .search-box {
      margin: 32upx 24upx;
      height: 64upx;
      background: #ffffff;
      border-radius: 32upx;
      line-height: 64upx;
      display: flex;
      flex-direction: row;
      justify-content: center;
      align-items: center;

      .icon {
        width: 32upx;
        height: 32upx;
        margin-right: 28upx;
      }

      .txt {
        font-size: 12px;
        color: #aaaaaa;
      }
    }

    .banner-box {
      .banner-img {
        width: 100%;
        height: 300upx;
        border-radius: 12upx;
      }

      uni-swiper {
        height: 300upx;
      }
    }

    .class-box {
      display: flex;
      flex-direction: row;
      flex-wrap: wrap;
      justify-content: space-around;
      text-align: center;
      font-size: 12px;
      margin-bottom: 32upx;
      margin-top: 21upx;

      .item {
        width: 25%;

        .class-img {
          width: 88upx;
          height: 88upx;
        }
      }
    }

    .notice-box {
      display: flex;
      align-items: center;
      width: 702upx;
      height: 80upx;
      background: #FFFFFF;
      border-radius: 12upx;
      margin: 32upx 24upx;
      position: relative;
      .notice-icon {
        height: 44upx;
        width: 44upx;
        margin-left: 24upx;
        margin-right: 24upx;
      }
      .notice-swiper {
        width: 586upx;
        height: 37upx;
        color: rgba(51,51,51,1);
        font-size: 26upx;
      }
      .notice-swiper-item{
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }

    .consult-box {
      .index-title {
        display: flex;
        margin-bottom: 24upx;

        .consult-direction,
        .price-filter,
        .filter {
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


      }

    }

    .img-box {
      min-height: 344upx;

      .img-item-box {
        width: 682upx;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;

        .left-img {
          width: 333upx;
          height: 344upx;
          margin-right: 16upx;
        }

        .right-img {
          width: 333upx;
          height: 164upx;
          margin-bottom: 16upx;
        }

        .img {
          width: 333upx;
          height: 334upx;
          margin-right: 16upx;
          margin-bottom: 16upx;
        }

        .img:nth-child(even) {
          margin-right: 0;
        }
      }
    }



  }
</style>