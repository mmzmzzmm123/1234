<template>
  <view class="class-page">
    <view class="search-box index-margin" @tap="toSearch">
      <img class="icon" src="/static/icon/search.png" />
      <span class="txt">搜索</span>
    </view>
    <view class="class-box">
      <view class="left-class-view">
        <view v-for="productClass in classList" v-bind:key="productClass.id">
          <view class="left-class" :class="
            classSelectedIndex === productClass.id ? 'selected' : ''
          " @tap="productClassSelected(productClass)">{{ productClass.name }}</view>
        </view>
      </view>
      <view class="item-list">
        <view class="current-class">{{className}}</view>
        <view class="product-item" v-show=" productList.length>0" v-for="product in (productList||[])"
          @tap="toProduct(product)">
          <view class="txt-box">
            <view class="title txt-overflow txt-overflow-line2">{{
            product.title
            }}</view>
            <view class="sub-title txt-overflow">{{ product.subtitle }}</view>
            <view class="price"><span class="icon">￥</span>{{ product.price }}</view>
          </view>
          <view class="img-box">
            <img :src="product.headPicture" />
          </view>
        </view>
        <view class="footer" v-show=" productList.length>0">已经到底了</view>
        <view v-show=" productList.length==0" class="no-data">
          <img class="img" src="/static/nothing/search-nothing.png" />
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import productListCom from '@/components/productList'
import classServer from '@/server/class'
export default {
  components: { productListCom },
  data() {
    return {
      searchValue: '',
      classSelectedIndex: null,
      className: '全部',
      classList: [],
      productList: []
    };
  },
  async created() {
    this.classList = [...[{ name: '全部', id: null }], ...await classServer.getClassList()];
    this.productList = await classServer.getProductByClassId(this.classSelectedIndex);
  },
  methods: {
    toSearch() {
      uni.navigateTo({
        url: "/pages/search/index",
      });
    },
    async productClassSelected(currentClass) {
      this.classSelectedIndex = currentClass.id;
      this.className = currentClass.name;
      this.productList = await classServer.getProductByClassId(this.classSelectedIndex);
    },
    toProductList(productClassId) {
      uni.navigateTo({
        url: "/pages/product/productList?productClassId=" + productClassId,
      });
    },
  },
};
</script>

<style lang="scss">
@import "@/style/common.scss";

page {
  background-color: #f8f8f8;

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

  .class-box {
    display: flex;
    flex-direction: row;

    .left-class-view {
      background: #f6f6f6;
      width: 152upx;
      overflow-y: auto;

      .left-class {
        height: 100upx;
        line-height: 102upx;
        font-size: 26upx;
        color: #777;
        padding-left: 24upx;

        &.selected {
          color: #333333;
          font-weight: 600;
          position: relative;

          &::before {
            content: "";
            display: inline-block;
            width: 5upx;
            height: 102upx;
            background: #FF3F64;
            left: 0upx;
            top: 0;
            position: absolute;
          }
        }
      }
    }

    .item-list {
      height: 100vh;
      overflow-y: auto;
      width: 600upx;
      background-color: #fff;

      .current-class {
        font-size: 26upx;
        font-weight: 400;
        color: #777777;
        line-height: 77upx;
        padding-left: 24upx;
      }

      .footer {

        &::before,
        &::after {
          left: 177upx;
          background-color: #777777;
        }

        &::after {
          right: 177upx;
          left: unset;
        }
      }
    }
  }


  .product-item {
    display: flex;
    flex-direction: row;
    width: calc(100% -48upx);
    padding: 24upx 0;
    background: #ffffff;
    margin-bottom: 16upx;
    margin: 0 16upx;
    border-bottom: 1px solid #ccc;

    .txt-box {
      width: calc(100% - 198upx);
      flex: 1;
      margin-right: 15upx;

      .title {
        height: 84upx;
        font-size: 30upx;
        font-weight: 600;
        color: #333333;
        line-height: 42upx;
      }

      .sub-title {
        font-size: 26upx;
        font-weight: 400;
        color: #333333;
        height: 37upx;
        line-height: 37upx;
      }
    }

    .img-box {
      width: 183upx;
      height: 208upx;
      border-radius: 8upx;
    }

    .price {
      margin-top: 39upx;
    }
  }

  .no-data {
    width: 300upx;
    height: 289upx;
    margin: 160upx;

    .img {
      width: 100%;
    }
  }

}
</style>
