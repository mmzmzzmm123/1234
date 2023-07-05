<template>
  <view class="index">
    <view class="search-box index-margin" @tap="toSearch">
<!--      <img class="icon" src="/static/icon/search.png" />-->
<!--      <span class="txt">搜索</span>-->
      <view class="uni-input-wrapper">
        <uni-easyinput prefixIcon="search" :styles="styles" :placeholderStyle="placeholderStyle" v-model="queryData.userName" @confirm="inputConfirm" placeholder="请输入咨询师名称">
        </uni-easyinput>
      </view>
    </view>
    <view class="banner-box index-margin">
      <swiper class="ad-swiper" indicator-dots circular indicator-color="rgb(255, 255, 255, .5)"
        indicator-active-color="#FFFFFF">
        <swiper-item v-for="(item, index) in bannerList" :key="index">
          <image class="banner-img" :src="item.url"/>
        </swiper-item>
      </swiper>
    </view>
    <view class="class-box index-margin">
      <view class="item" v-for="item in classList" @tap="toClass(item.id)">
        <img class="class-img" :src="item.url" />
        <view>{{ item.name }}</view>
      </view>
    </view>


    <view class="consult-box index-margin">
      <view class="index-title">
        <view class="consult-direction" @tap="doFilter(1)">咨询方向</view>
        <view class="price-filter" @tap="doFilter(2)">{{ calPrice }}</view>
        <view class="filter" @tap="doFilter(3)">筛选</view>
      </view>
      <consultant-list :consultantList="consultantList"></consultant-list>
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
        queryData: {
          catId: null,
          userName: null,
          sex: null,
          lowPrice: null,
          highPrice: null,
          serve: null,
          type: null,
          days: [],
          way: []
        },
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
    computed: {
      calPrice() {
        return this.filterParams.price ? this.filterParams.price : '价格'
      }
    },
    async mounted() {
      this.userInfo = utils.getUserInfo()

      this.getCat()
      this.getBanner(0)
      this.getConsult()

      this.getTypes()
      this.getAttrs()
      this.getDates()
      this.getPrice()
      this.getSex()
      this.getConsultTime()
      if (!this.userInfo && await utils.loginCallback(this.redirectUri)) {
        // this.userInfo = uni.getStorageSync("userInfo")
        this.userInfo = utils.getUserInfo()
      }
      if (!this.userInfo) {
        this.openLoginConfirm()
      }

    },
    methods: {
      resetQuery() {
        this.queryData.catId = null
        this.queryData.userName = null
        this.queryData.sex = null
        this.queryData.lowPrice = null
        this.queryData.highPrice = null
        this.queryData.serve = null
        this.queryData.days = []
        this.queryData.way = []
      },
      inputConfirm(val) {
        if (val && val !== '') {
          this.resetQuery()
          this.queryData.userName = val
          this.getConsult()
        }
      },
      async getCat() {
        const classData = await classServer.getClassList()
        this.classList = classData.slice(0, 4) // 取前8个类别
      },
      async getTypes() {
        this.attrParams.typeList = await indexServer.getConfigByType('consult_type');
      },
      async getAttrs() {
        this.attrParams.attrList = await indexServer.getAttrs(['consult_direction_one','consult_direction_two','consult_direction_three']);
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
        // const data = {
        //   catId: 1,
        //   userName: 'hello123',
        //   sex: '男',
        //   lowPrice: 0,
        //   highPrice: 107,
        //   serve: '面对面咨询',
        //   type: '上午',
        //   days: ['2023-06-27', '2023-06-28'],
        //   way: ['原生家庭关系a','育儿b','易怒c','成长b']
        // }
        this.consultantList = await indexServer.getConsult(this.queryData)
      },
      async tocourse(url) {
        // 判断是否已经登录
        if (!this.userInfo) {
          this.openLoginConfirm()
          return
        }
        uni.navigateTo({
          url
        });
      },
      toClass(classId) {
        // 判断是否已经登录
        if (!this.userInfo) {
          this.openLoginConfirm()
          return
        }
        this.resetQuery()
        this.queryData.catId = classId
        this.getConsult()
      },
      toSearch() {
        // 判断是否已经登录
        if (!this.userInfo) {
          this.openLoginConfirm()
          return
        }
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
        // console.log('submit')
        // console.log(this.filterParams)
        // console.log(this.queryData)
        this.queryData.catId = null
        this.queryData.userName = null
        switch (this.filterParams.type) {
          case 1:
            this.queryData.way = this.filterParams.way
            break
          case 2:
            if (this.filterParams.price) {
              switch (this.filterParams.price) {
                case '500元以下':
                  this.queryData.lowPrice = 0
                  this.queryData.highPrice = 499
                  break
                case '500-700元':
                  this.queryData.lowPrice = 500
                  this.queryData.highPrice = 699
                  break
                case '700-900元':
                  this.queryData.lowPrice = 700
                  this.queryData.highPrice = 899
                  break
                case '900元以上':
                  this.queryData.lowPrice = 900
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
      }
    },
  };
</script>
<style lang="scss" scoped>
  @import "@/style/common.scss";

  page {
    background-color: #F8F8F8;

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
        height: 260upx;
      }

      uni-swiper {
        height: 260upx;
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