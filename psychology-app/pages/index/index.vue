<template>
  <view class="index">
    <view class="search-box index-margin" @tap="toSearch">
      <img class="icon" src="/static/icon/search.png" />
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
    <view class="hot-box">
      <view class="index-title">热门推荐</view>
      <view class="list-box">
        <view class="item" v-for="(item, index) in hotList" @tap="toProduct('/pages/product/index?id='+item.id)">
          <view class="ranking" v-if="index < 3">Top{{ index + 1 }}</view>
          <img class="img" :src="item.headPicture" />
          <span class="txt txt-overflow txt-overflow-line2">{{
          item.title
          }}</span>
          <span class="price">
            <span class="icon">￥</span>{{ item.price }}
          </span>
        </view>
      </view>
    </view>
    <view class="img-box index-margin">
      <view class="index-title">
        限时福利
        <span class="more">
          更多
          <img class="img" src="/static/index/more.png" />
        </span>
      </view>
      <view class="img-item-box">
        <view class="left-img" @tap="toProduct(bannerList2.length>0?bannerList2[0].linkUrl:'')">
          <img :src="bannerList2.length>0?bannerList2[0].bannerUrl:''" />
        </view>
        <view class="right-img-box">
          <view class="right-img" @tap="toProduct(bannerList2.length>1?bannerList2[1].linkUrl:'')">
            <img :src="bannerList2.length>1?bannerList2[1].bannerUrl:''" />
          </view>
          <view class="right-img" @tap="toProduct(bannerList2.length>2?bannerList2[2].linkUrl:'')">
            <img :src="bannerList2.length>2?bannerList2[2].bannerUrl:''" />
          </view>
        </view>
      </view>
    </view>
    <view class="img-box index-margin">
      <view class="index-title">
        全面评估
        <span class="more">
          更多
          <img class="img" src="/static/index/more.png" />
        </span>
      </view>
      <view class="img-item-box">
        <img class="img" v-for="item in bannerList3" :src="item.bannerUrl" @tap="toProduct(item.linkUrl)" />
      </view>
    </view>
    <view class="product-box index-margin">
      <view class="index-title">
        精选测评
        <span class="more">
          更多
          <img class="img" src="/static/index/more.png" /></span>
      </view>
      <product-list-com :productList="productList"></product-list-com>
    </view>
  </view>
</template>
<script>
import productListCom from '@/components/productList'
import indexServer from '@/server/index'
export default {
  components: { productListCom },
  data() {
    return {
      bannerList: [],
      bannerList1: [],
      bannerList2: [],
      bannerList3: [],
      classList: [
        {
          classPic: "/static/index/1.png",
          className: "智商测试",
          id: 16,
        },
        {
          classPic: "/static/index/2.png",
          className: "情感测试",
          id: 36,
        },
        {
          classPic: "/static/index/3.png",
          className: "专业测评",
          id: 72,
        }, {
          classPic: "/static/index/4.png",
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
        url: "/pages/search/index",
      });
    },
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
