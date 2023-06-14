<template>
  <view class="index">
    <view class="search-box index-margin" @tap="toSearch">
      <img class="icon" src="/static/icon/search.png" />
      <span class="txt">搜索</span>
    </view>
    <view class="banner-box index-margin">
      <swiper class="ad-swiper" indicator-dots circular indicator-color="rgb(255, 255, 255, .5)"
        indicator-active-color="#FFFFFF">
        <swiper-item v-for="(item, index) in bannerList" :key="index">
          <image class="banner-img" :src="item.bannerUrl" @tap="tocourse(item.linkUrl)" />
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
    <course-tab-bar :currentIndex="0"></course-tab-bar>

    <!-- 筛选组件 -->
    
    <uni-popup ref="popupFilter" type="top" background-color="#fff">
      <filter-com :filterParams="filterParams" @close="doFilterClose">
      
      </filter-com>
    </uni-popup>
          
    <uni-popup ref="popup" type="dialog">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="close"
        @confirm="confirm"></uni-popup-dialog>
    </uni-popup>
  </view>
</template>
<script>
  import consultantList from '@/components/consult/consultantList.vue'
  import filterCom from '@/components/consult/filter.vue'
  import indexServer from '@/server/course/index'
  import loginServer from '@/server/login'
  import {
    uniPopup,
    uniPopupDialog
  } from '@dcloudio/uni-ui'
  import utils from "@/utils/common";
  import classServer from '@/server/course/class'
  export default {
    components: {
      consultantList,
      filterCom,
      uniPopup,
      uniPopupDialog
    },
    data() {
      return {
        bannerList: [],
        classList: [],
        consultantList: [{
          avatar: '../../static/course/1.png',
          name: '张三',
          tagList: [{
              id: 1,
              name: '职场心理'
            },
            {
              id: 2,
              name: '亲子关系'
            },
          ],
          introduce: '咨询师介绍咨询师介绍咨询师介绍咨询师介绍咨询师介绍咨询师介绍咨询师介绍',
        }],
        userInfo: {},
        filterParams: {
          type: 0,
          price: {
            id: 0,
            name: ''
          }
        },
        showFilter: false
      };
    },
    computed: {
      calPrice() {
        return this.filterParams.price.name ? this.filterParams.price.name : '价格'
      }
    },
    async created() {
      this.userInfo = uni.getStorageSync("userInfo")
      this.bannerList = await this.getBanner(0);

      const classData = await classServer.getClassList()
      this.classList = classData.slice(0, 8) // 取前8个类别
    },
    async mounted() {
      if (!this.userInfo && await utils.loginCallback(this.redirectUri)) {
        this.userInfo = uni.getStorageSync("userInfo")
      }
      if (!this.userInfo) {
        this.openLoginConfirm()
      }

    },
    methods: {
      async getBanner(type) {
        return await indexServer.getBannerList(type);
      },
      async getcourse(type) {
        return await indexServer.getcourseByLabel(type);
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
        uni.navigateTo({
          url: "/pages/course/class?classId=" + classId
        });
      },
      toSearch() {
        // 判断是否已经登录
        if (!this.userInfo) {
          this.openLoginConfirm()
          return
        }
        uni.navigateTo({
          url: "/pages/course/search",
        });
      },
      doFilter(type) {
        this.$set(this.filterParams, 'type', type)
        // this.showFilter = true
        this.$refs.popupFilter.open()
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