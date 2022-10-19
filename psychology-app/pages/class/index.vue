<template>
  <view class="class-page">
    <view class="search-box index-margin">
      <img class="icon" src="/static/icon/search.png" />
      <input placeholder="搜索" class="uni-input ipt" v-model="searchValue" @confirm="searchSubmit" />
      <view @tap="clearIpt" v-show="searchValue.length>0" class="clear-icon">
        <img src="/static/icon/clear.png" />
      </view>
    </view>
    <view class="class-box">
      <view class="left-class-view">
        <view v-for="productClass in classList" v-bind:key="productClass.id">
          <view class="left-class" :class="
            productClassSelectedIndex === productClass.id ? 'selected' : ''
          " @tap="productClassSelected(productClass.id)">{{ productClass.name }}</view>
        </view>
      </view>
      <view class="item-list">
        <product-list-com v-if=" productList.length>0" :productList="productList"></product-list-com>
        <view v-show=" productList.length==0" class="no-data">
          <img src="/static/nothing/search-nothing.png" />
        </view>
      </view>
    </view>
  </view>
</template>

<script>
import productListCom from '@/components/productList'
export default {
  components: { productListCom },
  data() {
    return {
      searchValue: '',
      productClassSelectedIndex: 1,
      classList: [
        {
          id: 1,
          name: "趣味",
        },
        {
          id: 2,
          name: "趣味评测",
        },
        {
          id: 3,
          name: "趣味评测",
        },
        {
          id: 4,
          name: "趣味测",
        },
        {
          id: 5,
          name: "趣味评测",
        },
        {
          id: 6,
          name: "趣味评测",
        },
        {
          id: 7,
          name: "趣味评测",
        },
        {
          id: 8,
          name: "趣味评测",
        },
        {
          id: 9,
          name: "趣味评测",
        },
      ],
      productList1: [],
      productList: [
        {
          title: "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/index/hot/1.jpg",
        },
        {
          title: "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/index/hot/1.jpg",
        },
        {
          title: "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/index/hot/1.jpg",
        },
        {
          title:
            "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近阿斯拉达咖啡机拉斯柯达附件拉萨科技发拉开距离拉上发电量开始",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/index/hot/1.jpg",
        },
        {
          title:
            "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近阿斯拉达咖啡机拉斯柯达附件拉萨科技发拉开距离拉上发电量开始",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/index/hot/1.jpg",
        },
        {
          title:
            "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近阿斯拉达咖啡机拉斯柯达附件拉萨科技发拉开距离拉上发电量开始",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/index/hot/1.jpg",
        },
        {
          title:
            "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近阿斯拉达咖啡机拉斯柯达附件拉萨科技发拉开距离拉上发电量开始",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/index/hot/1.jpg",
        },
        {
          title:
            "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近阿斯拉达咖啡机拉斯柯达附件拉萨科技发拉开距离拉上发电量开始",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/index/hot/1.jpg",
        },
      ],
    };
  },
  async onLoad() { },
  methods: {
    productClassSelected(productClassId) {
      this.productClassSelectedIndex = productClassId;
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
page {
  background-color: #f8f8f8;

  .search-box {
    flex: 1;
    height: 64upx;
    background: #fff;
    border-radius: 32upx;
    line-height: 64upx;
    display: flex;
    flex-direction: row;
    padding: 0 26upx;
    justify-content: left;
    align-items: center;
    margin: 32upx 24upx 16upx;

    .icon {
      width: 32upx;
      height: 32upx;
    }

    .ipt {
      margin-left: 26upx;
      flex: 1;
      font-size: 26upx;
      color: #333333;
    }

    .clear-icon {
      width: 40upx;
      height: 40upx;

      img {
        margin: 6upx;
        width: 28upx;
        height: 28upx;
      }
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
        padding: 0 24upx;

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

      .footer {

        &::before,
        &::after {
          left: 197upx;
        }

        &::after {
          right: 197upx;
        }
      }
    }
  }

}
</style>
