<template>
  <view class="class-page">
    <view class="search-box index-margin" @tap="toSearch">
      <img class="icon" src="/static/icon/search.png" />
      <span class="txt">搜索</span>
    </view>
    <view class="class-box">
      <view class="left-class-view">
        <view v-for="courseClass in classList" v-bind:key="courseClass.id">
          <view class="left-class" :class="
            classSelectedIndex === courseClass.typeId ? 'selected' : ''
          " @tap="courseClassSelected(courseClass)">{{ courseClass.name }}</view>
        </view>
      </view>
      <view class="item-list">
        <view class="current-class">{{ className }}</view>
        <view class="course-item" v-show="courseList.length > 0" v-for="course in (courseList || [])"
          @tap="tocourse(course.courseId)">
          <view class="img-box">
            <img :src="course.url" />
          </view>
          <view class="txt-box">
            <view class="title txt-overflow txt-overflow-line2">{{
                course.name
            }}</view>
            <view class="bottom-box">
              <view class="author txt-overflow">{{ course.author  }}</view>
              <view class="price"><span class="icon">￥</span>{{ course.price }}</view>
            </view>            
          </view>         
        </view>
        <view class="footer" v-show="courseList.length > 0">已经到底了</view>
        <view v-show="courseList.length == 0" class="no-data">
          <img class="img" src="/static/nothing/search-nothing.png" />
        </view>
      </view>
    </view>
    <course-tab-bar :currentIndex="1"></course-tab-bar>
  </view>
</template>

<script>
import courseListCom from '@/components/course/courseList'
import classServer from '@/server/course/class'
export default {
  components: { courseListCom },
  data() {
    return {
      searchValue: '',
      classSelectedIndex: null,
      className: '全部',
      classList: [],
      courseList: []
    };
  },
  async created() {
    this.classList = [...[{ name: '全部', typeId: null }], ...await classServer.getClassList()];
    this.courseList = await classServer.getcourseByClassId(this.classSelectedIndex);
  },
  methods: {
    toSearch() {
      uni.navigateTo({
        url: "/pages/course/search",
      });
    },
    async courseClassSelected(currentClass) {
      this.classSelectedIndex = currentClass.typeId;
      this.className = currentClass.name;
      this.courseList = await classServer.getcourseByClassId(this.classSelectedIndex);
    },
    tocourse(courseId) {
      uni.navigateTo({
        url: "/pages/course/courseDetail?id=" + courseId,
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
            background: #FF703F;
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
        // border-bottom: 1px solid #ccc;
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


  .course-item {
    display: flex;
    flex-direction: row;
    width: calc(100% -48upx);
    padding: 24upx 0;
    background: #ffffff;
    margin-bottom: 16upx;
    margin: 0 16upx;
    border-bottom: 1px solid #cccccc;

   .img-box {
       width: 172rpx;
       height: 172rpx;
       border-radius: 8upx;
   }
   .txt-box {
       width: calc(100% - 198upx);
       flex: 1;
       margin-left: 32upx;
   
       .title {
           height: 84upx;
           font-size: 30upx;
           font-weight: 600;
           color: #333333;
           line-height: 42upx;
       }
       .bottom-box {
         margin-top: 51upx;
         display: flex;
         align-items: center;
         justify-content: space-between;
         .author {
             font-size: 26upx;
             font-weight: 400;
             color: #777777;
             height: 37upx;
             line-height: 37upx;
         }
         .price {
             font-size: 22upx;
         }
       }
       
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
