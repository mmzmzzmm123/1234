<template>
  <view class="index">
    <view class="search-un-bg">
      <view class="search-box">
        <img class="icon" src="/static/evaluation/icon/search.png" />
        <input placeholder="搜索" class="uni-input ipt" v-model="searchValue" @confirm="searchSubmit" />
        <view @tap="clearIpt" v-show="searchValue.length > 0" class="clear-icon">
          <img src="/static/evaluation/icon/clear.png" />
        </view>
      </view>
      <span class="clear-txt" @tap="toHome">取消</span>
    </view>
    <view class="history-box" v-show="historyListShow">
      <view class="title"><span>历史搜索</span><img class="delete-icon" src="/static/evaluation/icon/delete.png"
          @tap="deleteHistory" />
      </view>
      <view class="list">
        <view class="item" v-for="item in hostoryList" @tap="setSearchValue(item)">{{ item }}</view>
      </view>
    </view>
    <product-list-com v-if="productListShow && productList.length > 0" :productList="productList"></product-list-com>
    <view v-show="productListShow && productList.length == 0" class="no-data">
      <img src="/static/evaluation/nothing/search-nothing.png" />
    </view>
    <message-com :message="deleteMessage" v-if="showDeleteMessage"></message-com>
    <evaluation-tab-bar></evaluation-tab-bar>
  </view>
</template>

<script>
import productListCom from '@/components/evaluation/productList'
import messageCom from '@/components/evaluation/message'
export default {
  components: { productListCom, messageCom },
  data() {
    return {
      showDeleteMessage: false,
      deleteMessage: {
        title: '确认删除全部历史记录？',
        cancelBtn: {
          text: '取消',
          callback: () => { }
        },
        submitBtn: {
          text: '确认',
          callback: () => { }
        },
      },
      historyListShow: true,
      productListShow: false,
      searchValue: '',
      hostoryList: [],
      productList: [
        {
          title: "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/evaluation/index/hot/1.jpg",
        },
        {
          title: "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/evaluation/index/hot/1.jpg",
        },
        {
          title: "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/evaluation/index/hot/1.jpg",
        },
        {
          title:
            "潜意识测试阿斯蒂芬离开家暗示分离看空间阿斯利康附近阿斯拉达咖啡机拉斯柯达附件拉萨科技发拉开距离拉上发电量开始",
          subtitle:
            "SDK发链接as康复科健康小侄女，明显内存泄漏看是就行了开车是聚类分析开具了科学城举行开具了看",
          price: 19.99,
          img: "/static/evaluation/index/hot/1.jpg",
        },
      ],

    };
  },
  created() {
    this.deleteMessage.cancelBtn.callback = this.clearDelete;
    this.deleteMessage.submitBtn.callback = this.submitDelete;
    this.hostoryList = uni.getStorageSync("historySearch").split(',');
  },
  methods: {
    deleteHistory() {
      this.showDeleteMessage = true;
    },
    clearDelete() {
      this.showDeleteMessage = false;
    },
    submitDelete() {
      this.showDeleteMessage = false;
      uni.setStorageSync("historySearch", '');
    },
    clearIpt() {
      this.searchValue = "";
      this.historyListShow = true;
      this.productListShow = false;
      this.hostoryList = uni.getStorageSync("historySearch").split(',');
    },
    setSearchValue(item) {
      this.searchValue = item;
      this.searchSubmit();
    },
    searchSubmit() {
      this.historyListShow = false;
      this.productListShow = true;
      this.historyList = [...[this.searchValue], ...this.historyList];
      uni.setStorageSync("historySearch", this.historyList.toString());
    },
    toHome() {
      uni.navigateTo({
        url: "/pages/evaluation/index",
      });
    }
  }
};
</script>

<style lang="scss">
@import "@/style/common.scss";

page {
  padding: 32upx 24upx;
  background-color: #f8f8f8;

  .search-un-bg {
    display: flex;
    flex-direction: row;
    align-items: center;
    margin-bottom: 24upx;

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
      margin-right: 24upx;

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

    .clear-txt {
      font-size: 32upx;
      font-weight: 400;
      color: #333333;
    }
  }

  .history-box {

    .title {
      display: flex;
      justify-content: space-between;
      align-items: center;
      height: 45upx;
      font-size: 32upx;
      font-weight: 600;
      color: #333333;
      line-height: 45upx;
      margin-bottom: 36upx;
    }

    .list {
      display: flex;
      flex-direction: row;
      flex-wrap: wrap;

      .item {
        padding: 0 32upx;
        line-height: 58upx;
        height: 58upx;
        margin-right: 16upx;
        margin-bottom: 16upx;
        background: #FFFFFF;
        border-radius: 36upx;
        font-size: 24upx;
      }
    }

    .delete-icon {
      width: 28upx;
      height: 28upx;
      margin-right: 6upx;
    }
  }

  .no-data {
    width: 300upx;
    height: 289upx;
    margin: 160upx 225upx;
  }
}
</style>
