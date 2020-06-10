<template>
  <div class="app-container">
    <el-row :gutter="24">
      <el-col :span="4" :xs="24">
        <el-row class="xs-btns-style" :gutter="10">
          <el-col v-for="item in btns" :key="item.title">
            <el-button
              type="primary"
              class="btn no-border-btn"
              :style="{background: `${item.color}`}"
            >{{item.title}}</el-button>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="20" :xs="24">
        <div class="lunarFullCalendar">
          <div class="full-calendar">
            <lunar-full-calendar
              :events="events" 
              ref="calendar" 
              @event-selected="eventSelected" 
              :config="config" 
              @day-click="dayClick" 
            ></lunar-full-calendar>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { LunarFullCalendar } from "vue-lunar-full-calendar";
//import FullCalendar from "@fullcalendar/vue";
//import dayGridPlugin from "@fullcalendar/daygrid";
//import timeGridPlugin from "@fullcalendar/timegrid";
//import interactionPlugin from "@fullcalendar/interaction";
//import listPlugin from "@fullcalendar/list";
import { getAllSchoolCalendars } from "@/api/benyi/calendar";
export default {
  components: {
    //name: "fullcalendar_page",
    LunarFullCalendar
  },
  data() {
        let self = this;
        return {
          btns: [
            {
              color: "#52c41a",
              title: "家长参与"
            },
            {
              color: "#135200",
              title: "大型活动"
            },
            {
              color: "#1890ff",
              title: "教学活动"
            },
            {
              color: "#eb2f96",
              title: "节假日"
            }
          ],
          events: [
          {
              title  : 'event1',
              start  : '2020-06-06',
          },
          {
              title  : 'event2',
              start  : '2020-06-08',
              end    : '2020-06-12',
          },
          {
              title  : 'event3',
              start  : '2020-06-20T12:30:00',
              allDay : false,
          },{id: 6, title: "端午节", start: "2020-06-25", end: "2020-06-28", color: "#eb2f96"}
        ],
            config: {
              lunarCalendar: true,//控制中文日历是否显示为真
              //eventLimitText: '更多',
              locale:'zh-cn',//中文
              //eventColor: 'white',
              buttonText: {
                today: '今天',
                month: '月',
                week: '周',
                day: '日',
                list: "周列表"
              },
              calendarEvents: [],
              header: {
                // left: 'prevYear,prev,next,nextYear',
                // center: 'title',
                // right: 'hide,custom,today,agendaWeek'
                left: "prev,next today",
                center: "title",
                right: "hide, custom, month,agendaWeek,agendaDay, listWeek"
              },
              eventLimit: true, // 一天中显示多少条事件，多了隐藏
              firstDay: 0, // 控制周一周日那个在前面
              defaultView: 'month',
              allDaySlot: true, // agenda视图下是否显示all-day
              allDayText: '全天', // agenda视图下all-day的显示文本
              timezone: 'local', // 时区默认本地的
              slotLabelFormat: 'HH:mm', // 周视图和日视同的左侧时间显示    
              //分页方法
              viewRender(view, element) {
                self.viewRender(view, element);
              },
                    customButtons: { // 新增按钮
                      hide: {
                        text: '隐藏农历',
                        click: function () {
                          self.$refs.calendar.fireMethod('option', {
                            lunarCalendar: false
                          })
                          self.$refs.calendar.fireMethod('option', {
                            header: {
                              left: 'prevYear,prev,next,nextYear',
                              center: 'title',
                              right: 'show, custom, month,agendaWeek,agendaDay, listWeek'
                            }
                          })
                        }
                      },
                      show: {
                        text: '显示农历',
                        click: function () {
                          self.$refs.calendar.fireMethod('option', {
                            lunarCalendar: true
                          })
                          self.$refs.calendar.fireMethod('option', {
                            header: {
                              left: 'prevYear,prev,next,nextYear',
                              center: 'title',
                              right: 'hide, custom, month,agendaWeek,agendaDay, listWeek'
                            }
                          })
                        }
                      },
                    }
            },
            queryParams: {}
          }
       },
       created() {
          getAllSchoolCalendars(this.queryParams).then(response => {
            this.events = response.calendarData;
          });
        },
  methods: {
    // 注释的是功能是可以修改对应的功能值属性，比如设置  eventLimit为 false
    //  this.$refs.calendar.fireMethod('option',{
    //      eventLimit :false
    //  })
    dayClick (date, jsEvent, view) {
      // 点击当天的事件
      alert('农历数据：' + JSON.stringify(window.lunar(date._d)))
      console.log(date, jsEvent, 'dayClick')
    },
    eventSelected (event, jsEvent, view) {
      // 选中事件
      console.log(event, jsEvent, 'eventSelected')
    },
    viewRender (view, element) {
      console.log(view, element, 'viewRender')
    },
    enter () {
      this.$router.push('/explain')
    }
  }
};
// export default {
//   name: "fullcalendar_page",
//   components: {
//     LunarFullCalendar
//   },
//   data() {
//       return {
//         events: [],
//         config: {
//           lunarCalendar: true   //(Control whether the Chinese calendar shows true, unrealistic flase, default true.)
//         },
//       }
//     },
       
// };
// window.lunar(date)
</script>

<style scoped lang="scss">
$color: #45b984;
.lunarFullCalendar {
  min-width: 900px;
  height: 100%;
  background: #fff;
  //   .top {
  //     height: 100px;
  //     h2 {
  //       text-align: center;
  //       font-size: 15px;
  //     }
  //     div {
  //       font-size: 13px;
  //     }
  //   }
  .full-calendar {
    padding: 20px;
    height: calc(100% - 30px);
    /deep/ {
      .fc-unthemed thead,
      .fc-unthemed .fc-content,
      .fc-unthemed th,
      .fc-unthemed td,
      .fc-unthemed tbody,
      .fc-unthemed .fc-row,
      .fc-unthemed .fc-list-view,
      .fc-unthemed .fc-popover,
      .fc-unthemed .fc-divider,
      .fc-unthemed .fc-list-heading td {
        border-color: #eaecf1;
      }
      .fc-header-toolbar {
        height: 30px;
        margin-bottom: 0;
        line-height: 30px;
        h2 {
          font-size: 24px;
          color: #000;
        }
      }
      .fc-button {
        height: 30px;
        background: #fff;
        border-color: #fff;
        box-shadow: none;
        padding: 0;
      }
      .fc-today-button {
        font-size: 15px;
        color: $color;
      }
      .fc-show-button,
      .fc-hide-button {
        position: relative;
        width: 80px;
        border: 1px solid #bbbfcd;
        border-radius: 20px;
        font-size: 15px;
        color: #000;
      }
      .fc-next-button,
      .fc-prev-button {
        color: #7d8292;
      }
      .fc-button-group {
        margin-left: 20px;
        font-size: 15px;
        button {
          width: 80px;
          color: #000;
          border: 1px solid #bbbfcd;
        }
        button:first-child {
          border-top-left-radius: 20px;
          border-bottom-left-radius: 20px;
        }
        button:last-child {
          border-top-right-radius: 20px;
          border-bottom-right-radius: 20px;
        }
        .fc-state-active {
          background: $color;
          color: #fff;
          text-shadow: none;
        }
      }
      .fc-agendaDay-view,
      .fc-month-view,
      .fc-agendaWeek-view {
        .fc-widget-content {
          .fc-sun,
          .fc-sat {
            background: rgba(245, 246, 248, 0.6);
          }
          .fc-day-number {
            font-size: 15px;
            letter-spacing: 0;
            line-height: 13px;
            float: left;
          }
          .fc-day-top {
            padding: 15px 12px 0 12px;
          }
          .fc-day-cnTerm,
          .fc-day-cnDate {
            font-size: 12px;
            color: #7d8292;
            float: right;
          }
          .fc-more {
            font-size: 12px;
            color: #7d8292;
          }
          .fc-event {
            cursor: pointer;
            font-size: 12px;
            color: #000;
            background-color: rgba($color: $color, $alpha: 0.15) !important;
            border: 1px solid rgba($color: $color, $alpha: 0.15) !important;
            border-left: 2px solid rgba($color: $color, $alpha: 0.15) !important;
            border-radius: 0;
          }
          .fc-day-grid-event {
            padding: 1px 0 1px 5px;
          }
        }
      }
      .fc-agendaWeek-view,
      .fc-agendaDay-view {
        .fc-axis {
          font-size: 15px;
        }
        .fc-title {
          font-size: 15px;
          color: $color;
        }
      }
      .fc-month-view {
        .fc-event {
          &.fc-not-start {
            border-left: 1px solid rgba($color: $color, $alpha: 0.1) !important;
          }
        }
      }
      .fc-agendaDay-view {
        .fc-head {
          table {
            margin: 15px 0;
          }
          .fc-day-header {
            display: none;
          }
        }
      }
      .fc-head {
        .fc-day-header,
        .fc-widget-header,
        .fc-head-container {
          border-top-color: #fff;
          border-left-color: #fff;
          border-right-color: #fff;
        }
        table {
          margin: 10px 0 15px 0;
          font-size: 15px;
          color: #000;
        }
      }
    }
  }
}
.tip {
  color: $color;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
}

//============================
.calendar {
  width: 820px;
}
.xs-btns-style {
  @media screen and (max-width: 768px) {
    display: flex;
    flex-wrap: wrap;
    .el-col {
      width: auto;
    }
  }
  @media screen and (min-width: 769px) {
  }
  .el-col {
    margin-bottom: 10px;
    text-align: center;
  }
}
.btn {
  width: 96px;
}
.no-border-btn {
  border: none;
}
.fc-header-toolbar {
  @media screen and (max-width: 768px) {
    .fc-left > .fc-today-button, .fc-right > .fc-listWeek-button {
      display: none;
    }
  }
}
</style>
