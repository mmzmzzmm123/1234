<template>
  <view class="index">
    <view class="search-box index-margin" @tap="toSearch">
      <img class="icon" src="/static/curriculum/icon/search.png" />
      <span class="txt">搜索</span>
    </view>
    <view class="banner-box index-margin">
      <swiper class="ad-swiper" indicator-dots circular>
        <swiper-item v-for="(item, index) in bannerList" :key="index">
          <image class="banner-img" :src="item.bannerUrl" @tap="toProduct(item.linkUrl)" />
        </swiper-item>
      </swiper>
    </view>
    <view class="class-box index-margin">
      <view class="item" v-for="item in classList">
        <img class="class-img" :src="item.classPic" />
        <view>{{ item.className }}</view>
      </view>
    </view>
    <view class="banner-box banner-box1 index-margin">
      <swiper class="ad-swiper" indicator-dots circular>
        <swiper-item v-for="(item, index) in bannerList1" :key="index">
          <image class="banner-img" :src="item.bannerUrl" @tap="toProduct(item.linkUrl)" />
        </swiper-item>
      </swiper>
    </view>
    <view class="img-box index-margin">
      <view class="index-title">
        限时福利
        <span class="more" @tap="toMore">
          更多
          <img class="img" src="/static/more.png" />
        </span>
      </view>
      <view class="img-item-box">
        <view class="left-img" v-show="bannerList2.length > 0"
          @tap="toProduct(bannerList2.length > 0 ? bannerList2[0].linkUrl : '')">
          <img :src="bannerList2.length > 0 ? bannerList2[0].bannerUrl : ''" />
        </view>
        <view class="right-img-box" v-show="bannerList2.length > 2">
          <view class="right-img" @tap="toProduct(bannerList2.length > 1 ? bannerList2[1].linkUrl : '')">
            <img :src="bannerList2.length > 1 ? bannerList2[1].bannerUrl : ''" />
          </view>
          <view class="right-img" @tap="toProduct(bannerList2.length > 2 ? bannerList2[2].linkUrl : '')">
            <img :src="bannerList2.length > 2 ? bannerList2[2].bannerUrl : ''" />
          </view>
        </view>
      </view>
    </view>
    <view class="product-box index-margin">
      <view class="index-title" @tap="toMore">
        精选好课
        <span class="more">
          更多
          <img class="img" src="/static/more.png" /></span>
      </view>
      <product-list-com :productList="productList"></product-list-com>
    </view>
    <curriculum-tab-bar :currentIndex="0"></curriculum-tab-bar>
    </view>
</template>
<script>
import productListCom from '@/components/curriculum/productList'
import indexServer from '@/server/curriculum/index'
export default {
  components: { productListCom},
  data() {
    return {
      bannerList: [],
      bannerList1: [],
      bannerList2: [],
      bannerList3: [],
      classList: [
        {
          classPic: "/static/curriculum/index/1.png",
          className: "智商测试",
          id: 16,
        },
        {
          classPic: "/static/curriculum/index/2.png",
          className: "情感测试",
          id: 36,
        },
        {
          classPic: "/static/curriculum/index/3.png",
          className: "专业测评",
          id: 72,
        }, {
          classPic: "/static/curriculum/index/4.png",
          className: "倾诉聆听",
          id: 72,
        },
      ],
      hotList: [],
      productList: []
    };
  },
  async created() {
    this.bannerList = await this.getBanner(0);
    this.bannerList1 = await this.getBanner(1);
    this.bannerList2 = await this.getBanner(2);
    this.bannerList3 = await this.getBanner(3);
    this.productList = await this.getProduct(0);
    this.hotList = await this.getProduct(1);
  },
  methods: {
    async getBanner(type) {
      return await indexServer.getBannerList(type);
    },
    async getProduct(type) {
      return await indexServer.getProductByLabel(type);
    },
    toProduct(url) {
      uni.navigateTo({ url });
    },
    toSearch() {
      uni.navigateTo({
        url: "/pages/curriculum/search",
      });
    },
    toMore() {
      uni.navigateTo({
        url: "/pages/curriculum/class",
      });
    }
  },
};
</script>
<style lang="scss">
@import "@/style/common.scss";

page {
  background-color: #F8F8F8;
  padding-left: 24upx;

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
      font-size: 24upx;
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

  .banner-box1 {
    uni-swiper {
      height: 200upx;
    }

    .banner-img {
      height: 200upx;
    }
  }

  .class-box {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    text-align: center;
    font-size: 26upx;
    margin-bottom: 32upx;
    margin-top: 21upx;

    .class-img {
      width: 88upx;
      height: 88upx;
    }
  }

  .hot-box {
    margin-left: 24upx;

    .list-box {
      display: flex;
      flex-direction: row;
      overflow-x: auto;
      min-height: 350upx;

      .item {
        display: flex;
        flex-direction: column;
        margin-right: 16upx;
        width: 194upx;
        position: relative;

        .ranking {
          position: absolute;
          top: 16upx;
          left: 0;
          color: #f4a200;
          line-height: 30upx;
          width: 81upx;
          height: 32upx;
          background: #ffee23;
          border-radius: 0 100upx 100upx 0;
          font-size: 22upx;
          padding-left: 10upx;
        }

        .img {
          width: 194upx;
          height: 240upx;
          margin-bottom: 16upx;
        }

        .txt {
          height: 80upx;
          line-height: 40upx;
        }
      }
    }
  }

  .index-title {
    margin-top: 32upx;
    margin-bottom: 16upx;
    font-size: 32upx;
    font-weight: 600;
    color: #333333;
    line-height: 45upx;
    display: flex;
    justify-content: space-between;

    .more {
      font-size: 24upx;
      font-weight: 400;
      color: #333333;
      align-items: center;
      display: flex;

      .img {
        margin-left: 12upx;
        width: 12upx;
        height: 24upx;
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
