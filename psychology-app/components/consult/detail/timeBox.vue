<template>
  <view class="time-page">
    <view class="time-list">
      <view class="time-content-main">
        <view class="time-content" v-for="a in workList">
          <view class="time-content-day" :class="{ selected: a.flag }">
            <text class="time-content-week">{{ a.week }}</text>
            <text class="time-content-date">{{ a.day }}</text>
          </view>
          <view v-if="a.child.length === 0" class="time-content-list">
            <view>
              <view class="time-item-full-tag">
                <text class="time-item-full-tag-text">已满</text>
              </view>
            </view>
          </view>
          <view v-if="a.child.length > 0" class="time-content-list">
            <view v-for="i in a.child">
              <view class="time-item-tag" :class="{ selected: i.id === workId && i.time === time }" @tap="selectDay(i.id, a.day, i.time)">
                <text class="time-item-tag-text">{{ i.s }}</text>
                <view class="time-item-tag-block"></view>
                <text class="time-item-tag-text">{{ i.e }}</text>
              </view>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>
<script>

export default {
  data() {
    return {
      time: -1,
      workId: 0,
      workList: [],
      userInfo: {},
    };
  },
  methods: {
    getWorkList(works, dateList) {
      const arr = []
      const today = dateList[0].date
      const curTime =  new Date().getHours()
      dateList.forEach(a => {
        const item = {
          week: a.week,
          day:  a.date.substr(5, 10),
          flag: false,
          child: []
        }
        works.filter(b => a.date === b.day && b.live !== '[]').forEach(c => {
          const live = JSON.parse(c.live)
          live.filter(d => (a.date === today && d > curTime) || a.date !== today).forEach(it => {
            item.child.push({
              flag: false,
              id: c.id,
              time: it,
              s: it + ':00',
              e: it + ':50'
            })
          })
        })
        arr.push(item)
      })
      console.log(arr)
      console.log(12321)
      this.workList = arr
    },
    selectDay(id, day, time) {
      this.workList.forEach(a => a.flag = false)

      if (this.workId === id && this.time === time) {
        this.workId = 0
        this.time = -1
      } else {
        this.workId = id
        this.time = time
        this.workList.find(a => a.day === day).flag = true
      }

      this.ok()
    },
    async open() {
      this.getWorkList()
    },
    ok() {
      let workName = ''
      if (this.workId > 0 && this.time > 0) {
        workName = this.time + ':00-' + this.time + ':50'
      }

      this.$emit('doOk', this.workId, this.time, workName)
      // this.close()
    },
    cancel() {
      this.workId = 0
      this.workList.forEach(a => a.flag = false)
      this.$emit('cancel')
      this.close()
    }
  }
};
</script>
<style lang="scss" scoped>
.time-page {
  //overflow-y: auto;
  position: relative;
  background-color: #FFFFFF;
  padding-bottom: 138upx;
  //height: 1000upx;

  .time-list {
    position: relative;
    display: flex;
    flex-direction: column;
  }
  .time-content-main {
    .time-content {
      display: flex;
      margin-left: 24upx;
    }
    .time-content-day {
      //width: 70upx;
      height: 78upx;
      margin: 10upx;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      margin-right: 58upx;
      &.selected {
        .time-content-week,
        .time-content-date {
          color: #FF703F;
        }
      }
      .time-content-week {
        width: 60upx;
        color: rgba(51,51,51,1);
        font-size: 30upx;
        font-weight: 600;
        margin-left: 4upx;
      }
      .time-content-date {
        //width: 68upx;
        color: rgba(119,119,119,1);
        font-size: 24upx;
        margin-top: 2upx;
      }
    }
    .time-content-list {
      display: flex;
      flex-wrap: wrap;
    }

    .time-item-full-tag {
      margin: 10upx;
      background-color: rgba(248,248,248,1.000000);
      border-radius: 8upx;
      height: 96upx;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      width: 96upx;

      .time-item-full-tag-text {
        color: rgba(119,119,119,1);
        font-size: 26upx;
      }
    }

    .time-item-tag {
      border-radius: 8upx;
      width: 96upx;
      height: 96upx;
      border: 1px solid rgba(119,119,119,1);
      margin: 10upx;
      display: flex;
      text-align: center;
      flex-direction: column;
      align-items: center;
      justify-content: center;

      .time-item-tag-text {
        color: rgba(51,51,51,1);
        font-size: 26upx;
      }
      .time-item-tag-block {
        background-color: rgba(51,51,51,1.000000);
        width: 12upx;
        height: 2upx;
        //margin: 1upx 0 0 42upx;
        margin: 1upx auto;
      }
      &.selected {
        background-color: rgba(255,112,63,1.000000);
        color: #FFFFFF;
        border: none;
        .time-item-tag-text {
          color: #FFFFFF;
        }
        .time-item-tag-block {
          background-color: #FFF;
        }
      }
    }
  }
  .tab-bar-wrap {
    .box_btn {
      position: fixed;
      bottom: 0;
      background-color: #FFFFFF;
      width: 100%;
      height: 128upx;
      flex-direction: row;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
    .button_1 {
      border-radius: 40upx;
      height: 80upx;
      line-height: 80upx;
      border: 1upx solid #777777;
      background: #FFFFFF;
      color: rgba(51,51,51,1);
      font-size: 28upx;
      text-align: center;
      width: 339upx;
    }
    .button_2 {
      background-color: rgba(255,112,63,1.000000);
      border-radius: 40upx;
      height: 80upx;
      line-height: 80upx;
      width: 339upx;
      color: rgba(255,255,255,1);
      font-size: 28upx;
    }
  }
}
</style>
