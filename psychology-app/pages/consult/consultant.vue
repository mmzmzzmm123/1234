<template>
  <view class="page">
    <view class="header">
<!--      <uni-nav-bar v-if="false" height="88upx" :backgroundColor="`rgba(255,255,255,${opcity})`" fixed left-icon="closeempty" right-icon="more-filled" :border="false" title="咨询师详情" @clickLeft="goHome"/>-->
    </view>

    <view class="info">
      <view class="info-header">
        <image :src="consultInfo.avatar" class="info-avatar-img"/>
        <view class="info-content">
          <view class="info-content-userName">
            <text>{{ consultInfo.userName }}</text>
            <view class="info-content-gps" v-if="consultInfo.province">
              <image src="/static/consult/gps.png" />
              <text v-if="consultInfo.province">{{ consultInfo.province + '-' + consultInfo.city }}</text>
            </view>
          </view>
          <view class="info-content-info">{{ consultInfo.info }}</view>
          <view class="info-content-mode">{{ consultInfo.mode }}</view>
        </view>
      </view>

      <view class="info-nums">
        <view class="info-nums-item">
          <view class="info-nums-item-num">{{ consultInfo.workNum }}人</view>
          <view class="info-nums-item-title">咨询人数</view>
        </view>
        <view class="info-nums-item">
          <text class="info-nums-item-num">{{ consultInfo.workTime }}小时</text>
          <text class="info-nums-item-title">服务时长</text>
        </view>
        <view class="info-nums-item">
          <text class="info-nums-item-num">{{ consultInfo.workHours }}年</text>
          <text class="info-nums-item-title">从业年限</text>
        </view>
      </view>
    </view>

    <view class="tabs segmented-main">
      <view class="tabs-list">
        <view v-for="i in items" class="tab" :class="{ selected: mainCur === i.id }" @tap="tabSelect(i.id)">
          <view>{{ i.name }}</view>
        </view>
      </view>
      <view class="tabs-bg"></view>
    </view>

    <scroll-view class="content-y" scroll-y :scroll-into-view="'main-'+mainCur" @scroll="scroll">
      <view class="serve" key="0" id="main-0">
        <text class="serve-title">服务套餐</text>
        <serve-list :serveList="serveList" :num="3" @toBuy="toBuy"/>
        <view class="serve-more" @tap="openMore">
          更多服务 >
        </view>
      </view>

      <view key="1" id="main-1" class="course">
        <view class="course-header">
          <text class="course-title">TA的心理课</text>
          <view class="course-more" @tap="toCourseMore">
            查看更多 >
          </view>
        </view>
        <course :courseList="courseList"/>
      </view>

      <view key="2" id="main-2" class="consult-desc">
        <consult-desc :info="consultInfo"/>
        <!--          <view class="consult-detail" v-html="consultInfo.detail"></view>-->
      </view>

      <view  key="3" id="main-3" class="content-time">
        <view class="content-time-title">可约时间</view>
        <time-box ref="timeBox" @doOk="setWorkData"/>
      </view>
    </scroll-view>

    <view class="page-bottom">
      <view class="page-bottom-image" @tap="goHome">
        <image src="/static/course/menu/index.png" class="page-bottom-icon"/>
        <text class="page-bottom-home">首页</text>
      </view>
      <button @tap="buy" class="page-bottom-btn">
        <text class="text_26">立即预约</text>
      </button>
    </view>

    <uni-popup ref="popup" type="dialog" class="uni-popup-ok">
      <uni-popup-dialog mode="base" content="您尚未登录, 是否使用微信静默登录" :duration="2000" :before-close="true" @close="closeLoginConfirm" @confirm="confirmLogin"/>
    </uni-popup>

    <uni-popup ref="selectServe" type="bottom" class="uni-popup-ok" background-color="#fff">
      <view class="bottom-serve">
        <view class="bottom-serve-header">
          服务套餐
        </view>
        <uni-icons type="closeempty" size="16" class="bottom-close" @tap="closeServe"/>
        <serve-list :serveList="serveList" :num="99" @toBuy="toBuy"/>
      </view>
    </uni-popup>
  </view>
</template>

<script>
import timeBox from '@/components/consult/detail/timeBox.vue'
import course from '@/components/consult/detail/course.vue'
import consultDesc from '@/components/consult/detail/consultDesc.vue'
import serveList from '@/components/consult/detail/serveList.vue'
import utils from "@/utils/common";
import indexServer from '@/server/consult/index'
import consultServer from "@/server/consult/consult";
import orderServer from "@/server/consult/order";
import loginServer from '@/server/login'
export default {
  components: {timeBox, serveList, course, consultDesc },
  data() {
    return {
      mainCur: 0,
      verticalNavTop: 0,
      height: 64, //header高度
      top: 0, //标题图标距离顶部距离
      scrollH: 200, //滚动总高度
      opcity: 0,
      dateList: [],
      works: [],
      tabs: [],
      serveList: [],
      courseList: [],
      sroHeight: [],
      items: [
        {
          id: 0,
          name: '服务套餐'
        },
        {
          id: 1,
          name: 'TA的心理课'
        },
        {
          id: 2,
          name: '老师介绍'
        },
        {
          id: 3,
          name: '可约时间'
        }],
      current: 0,
      userInfo: {},
      consultInfo: {},
      consultId: 0,
      redirectUri:location.href,
      currentCatalogue: {},
      scrollTop: 0,
      old: {
        scrollTop: 0
      }
    };
  },
  created() {
  },
  async mounted() {
    this.userInfo = utils.getUserInfo()
    if (!utils.checkLogin()) {
      return this.openLoginConfirm()
    }
    this.consultId = utils.getParam(location.href, "id")
    this.getConsultServe()
    this.getConsultWorks()
    this.getDates()
    await this.getConsultInfo()
    await this.getConsultCourseByName()
    this.openTime()
  },
  // onPageScroll(e) {
  //   let scroll = e.scrollTop <= 0 ? 0 : e.scrollTop;
  //   // console.log(scroll)
  //   // console.log(this.opcity)
  //   let opcity = scroll / this.scrollH;
  //   if (this.opcity >= 1 && opcity >= 1) {
  //     return;
  //   }
  //   this.opcity = opcity
  // },
  methods: {
    toCourseMore () {
      uni.navigateTo({
        url: "/pages/course/class",
      })
    },
    tabSelect(id) {
      this.mainCur = id
    },
    scroll(e) {
      let tabHeight = 0
      for (let i = 0; i < 4; i++) {
        uni.createSelectorQuery().select("#main-"+i).boundingClientRect((rect) => {
          const top = tabHeight
          tabHeight = tabHeight + rect.height;
          this.sroHeight.push({
            id: i,
            top: top,
            bottom: tabHeight
          })
        }).exec()
      }

      let scrollTop = e.detail.scrollTop + 10;
      for (let i = 0; i < this.sroHeight.length; i++) {
        if (scrollTop > this.sroHeight[i].top && scrollTop < this.sroHeight[i].bottom) {
          this.mainCur = this.sroHeight[i].id
          // console.log(scrollTop)
          return false
        }
      }
    },
    async getConsultInfo() {
      this.consultInfo = await consultServer.getConsultInfo(this.consultId)
      if (this.consultInfo.tabs) {
        const list = []
        const tabs = this.consultInfo.tabs.split(',')
        const items = JSON.parse(this.consultInfo.way)
        console.log(items)
        tabs.forEach(a => {
          const it = {
            name: a,
            child: []
          }
          items.filter(b => b[0] === a).forEach(c => {
            it.child.push(c[1])
          })
          list.push(it)
        })

        this.consultInfo.tabList = list
      }
      // if (this.consultInfo.mode) {
      //   this.consultInfo.mode = this.consultInfo.mode.split(',').join('/');
      // }
      this.consultInfo.qualification = this.consultInfo.qualification ? this.consultInfo.qualification.split(',') : []

      this.consultInfo.experiences = this.consultInfo.experience && this.consultInfo.experience !== '[]' ? JSON.parse(this.consultInfo.experience) : []
      // #ifdef H5
      console.log('consultInfo: ', this.consultInfo)
      utils.share(this.consultInfo.nickName, this.consultInfo.share ? this.consultInfo.share : '', this.consultInfo.avatar)
      // #endif
    },
    async getConsultCourseByName() {
      this.courseList = await consultServer.getConsultCourseByName(this.consultInfo.userName)
    },
    async getConsultServe() {
      this.serveList = await consultServer.getConsultServe(this.consultId)
    },
    async getConsultWorks() {
      this.works = await consultServer.getConsultWorksById(this.consultId)
    },
    async getDates() {
      this.dateList = await indexServer.getDates(7);
    },
    openTime() {
      this.$refs.timeBox.getWorkList(this.works, this.dateList)
    },
    setWorkData(workId, time, workName) {
      // 缓存一小时
      utils.put('time', time, 3600)
      utils.put('workId', workId, 3600)
      utils.put('workName', workName, 3600)
    },
    goHome() {
      uni.redirectTo({
        url: "/pages/consult/index",
      })
    },
    openMore() {
      this.$refs.selectServe.open('bottom')
    },
    buy() {
      this.$refs.selectServe.open('bottom')
    },
    async toBuy(item) {
      if (!utils.checkLogin()) {
        this.confirmServe()
        return this.openLoginConfirm()
      }
      const data = {
        payStatus: 2,
        userId: this.userInfo.userId
      }
      // 是否未下单用户校验
      console.log(item)
      if (item.bound === 1) {
        const list = await orderServer.getOrderList(data)
        if (list && list.length > 0) {
          return uni.showToast({
            icon: 'none',
            title: '仅新用户可购买',
            duration: 2000
          })
        }
      }

      uni.navigateTo({
        url: "/pages/consult/orderConfirm?cId=" + this.consultId + '&sId=' + item.id,
      });
    },
    closeServe() {
      this.$refs.selectServe.close()
    },
    confirmServe() {
      this.$refs.selectServe.close()
    },
    // 登录
    async confirmLogin () {
      await loginServer.login();
      this.$refs.popup.close()
    },
    closeLoginConfirm() {
      this.$refs.popup.close()
    },
    openLoginConfirm() {
      this.$refs.popup.open()
    }
  },
};
</script>

<style lang="scss">
.page {
  background-color: #F8F8F8;
  position: relative;
  //width: 750upx;
  //height: 2800upx;
  //overflow: hidden;
  display: flex;
  flex-direction: column;
  //padding-bottom: calc(24rpx + env(safe-area-inset-bottom));
}
.header {
  width: 100%;
  height: 252upx;
  background: linear-gradient(133deg, #FF8C65 0%, #FF6C39 100%);
}
.info {
  //z-index: 1;
  margin-top: -86upx;
  width: 100%;
  //height: 258upx;
  background: #FFFFFF;
  border-radius: 32upx 32upx 0upx 0upx;
}
.info-header {
  display: flex;
}
.info-avatar-img {
  width: 172upx;
  height: 172upx;
  border-radius: 86upx;
  margin-top: -86upx;
  margin-left: 24upx;
}
.info-content {
  width: 554upx;
  margin-left: 32upx;
}
.info-content-userName {
  position: relative;
  margin-top: -52upx;
  color: #FFFFFF;
  font-size: 30upx;
  font-weight: 600;
  text-align: left;
  white-space: nowrap;
  .info-content-gps {
    position: absolute;
    right: 30upx;
    top: 6upx;
    image {
      width: 16upx;
      height: 18upx;
    }
    text {
      margin-left: 6upx;
      font-size: 22upx;
      font-weight: 400;
      color: #FFFFFF;
    }
  }
}
.info-content-tags {
  width: 248upx;
  height: 34upx;
  margin-top: 8upx;
  flex-direction: row;
  display: flex;
  justify-content: space-between;
}
.info-content-tag {
  background-color: rgba(255,112,63,0.100000);
  border-radius: 4upx;
  height: 34upx;
  display: flex;
  flex-direction: column;
  width: 120upx;
}
.info-content-tag-text {
  width: 88upx;
  height: 30upx;
  overflow-wrap: break-word;
  color: rgba(255,112,63,1);
  font-size: 22upx;
  
  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 30upx;
  margin: 2upx 0 0 16upx;
}
.info-content-info {
  width: 498upx;
  height: 74upx;
  overflow-wrap: break-word;
  color: #777777;
  font-size: 26upx;
  margin-top: 16upx;
}
.info-content-mode {
  font-size: 24upx;
  font-weight: 400;
  color: #AAAAAA;
}
.info-nums {
  width: 702upx;
  height: 124upx;
  background: #FFFFFF;
  box-shadow: 0upx 2upx 10upx 0upx rgba(175,45,0,0.1);
  border-radius: 12upx;
  margin: 32upx;
  display: flex;
  align-items: center;
  justify-content: space-evenly;
}
.info-nums-item {
  width: 96upx;
  height: 76upx;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
}
.info-nums-item-num {
  height: 37upx;
  overflow-wrap: break-word;
  color: rgba(51,51,51,1);
  font-size: 26upx;
  
  font-weight: 500;
  text-align: left;
  white-space: nowrap;
  line-height: 37upx;
  margin-left: 2upx;
}
.info-nums-item-title {
  height: 31upx;
  overflow-wrap: break-word;
  color: rgba(119,119,119,1);
  font-size: 22upx;

  font-weight: normal;
  text-align: left;
  white-space: nowrap;
  line-height: 30upx;
  margin-top: 8upx;
}
.tabs {
  //margin-top: 24upx;
  height: 72upx;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  background-color: #FFFFFF;
  .tabs-list {
    height: 72upx;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin: 0upx 51upx;
    .tab {
      color: #777777;
      font-size: 28upx;
      &.selected {
        border-bottom: 6upx solid;
        //border-bottom-width: thin;
        font-weight: 600;
        color: #FF703F;
      }
    }
  }
}
.segmented-main {
  //margin-bottom: 20upx;
  //padding: 10upx;
  position: sticky;
  top: 0;
  z-index: 998;
}
.tabs-bg {
  background: #F8F8F8;
  height: 10upx;
  width: 100%;
}
.content-y {
  height: 1500upx;
  margin-top: 16upx;
}
.serve {
  background-color: #FFFFFF;
  display: flex;
  flex-direction: column;
  justify-content: flex-center;
  padding: 24upx;
  .serve-title {
    width: 120upx;
    height: 42upx;
    overflow-wrap: break-word;
    color: rgba(51,51,51,1);
    font-size: 30upx;
    font-weight: 500;
    text-align: center;
    white-space: nowrap;
    line-height: 42upx;
    margin-left: 24upx;
  }
  .serve-more {
    height: 60upx;
    color: rgba(51,51,51,1);
    font-size: 26upx;
    text-align: center;
    line-height: 60upx;
  }
}

.course {
  background-color: rgba(255,255,255,1.000000);
  max-height: 432upx;
  margin-top: 16upx;
  position: relative;
  display: flex;
  flex-direction: column;
  .course-header {
    padding: 24upx 24upx 0 24upx;
    height: 42upx;
    display: flex;
    justify-content: space-between;
  }
  .course-title {
    height: 42upx;
    color: rgba(51,51,51,1);
    font-size: 30upx;
    font-weight: 500;
    font-family: PingFangSC-Medium, PingFang SC;
    line-height: 42upx;
  }
  .course-more {
    height: 37upx;
    margin-top: 2upx;
    color: rgba(51,51,51,1);
    font-size: 26upx;
    line-height: 37upx;
  }
}

.content-time {
  //height: 200upx;
  margin-top: 16upx;
  background-color: #FFFFFF;
  padding-top: 32upx;
  .content-time-title {
    color: rgba(51,51,51,1);
    font-size: 36rpx;
    font-family: PingFangSC-Medium;
    font-weight: 500;
    margin-left: 24upx;
    margin-bottom: 32upx;
  }
}
.consult-desc {
  .consult-detail {
    background-color: #FFFFFF;
    margin-top: 16upx;
    margin-bottom: 16upx;
    width: calc(100% - 48upx);
    padding-left: 24upx;
    padding-right: 24upx;
    padding-bottom: 150upx;
    ::v-deep img {
      width: 100%;
    }
  }
}

.page-bottom {
  padding-bottom: constant(safe-area-inset-bottom); /* 兼容 iOS 设备 */
  padding-bottom: env(safe-area-inset-bottom); /* 兼容 iPhone X 及以上设备 */
  position: fixed;
  bottom: 0;
  z-index: 99;
  background-color: #FFFFFF;
  //width: 750upx;
  height: 120upx;
  margin-top: 40upx;
  display: flex;
  align-items: center;
  .page-bottom-image {
    width: 48upx;
    height: 80upx;
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin: 10upx 0 0 40upx;
  }
  .page-bottom-icon {
    width: 48upx;
    height: 48upx;
  }
  .page-bottom-home {
    color: rgba(51,51,51,1);
    font-size: 20upx;
    font-weight: normal;
    text-align: left;
    white-space: nowrap;
    line-height: 28upx;
    margin: 4upx 0 0 4upx;
  }
  .page-bottom-btn {
    background-color: rgba(255,112,63,1.000000);
    color: rgba(255,255,255,1);
    border-radius: 40upx;
    font-size: 28upx;
    font-weight: 600;
    height: 80upx;
    line-height: 80upx;
    width: 598upx;
    margin: 9upx 24upx 0 40upx;
    text-align: center;
  }
}
.uni-popup-ok {
  z-index: 1000;
}
.bottom-serve {
  background-color: #fff;
  position: relative;
  padding: 24upx;
  .bottom-serve-header {
    height: 80upx;
    line-height: 80upx;
    text-align: center;
    color: rgba(51,51,51,1);
    font-size: 30upx;
    font-weight: 500;
  }
  .bottom-close {
    position: absolute;
    right: 24upx;
    top: 24upx;
  }
}
</style>
