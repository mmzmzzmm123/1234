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
        <div>
          <FullCalendar
            ref="fullCalendar"
            defaultView="dayGridMonth"
            locale="zh-cn"
            :header="header"
            :buttonText="buttonText"
            :plugins="calendarPlugins"
            :weekends="calendarWeekends"
            :views="views"
            :events="calendarEvents"
            :eventLimit="true"
            :displayEventEnd="true"
            eventLimitText="更多"
            @eventClick="handleEventClick"
          />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import { getAllSchoolCalendars } from "@/api/benyi/calendar";
export default {
  name: "fullcalendar_page",
  components: {
    FullCalendar
  },
  data() {
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
      views: {
        list: {
          noEventsMessage: "暂无日程"
        }
      },
      header: {
        left: "prev,next today",
        center: "title",
        right: "dayGridMonth,timeGridWeek,timeGridDay listWeek"
      },
      buttonText: {
        today: "今天",
        month: "月",
        week: "周",
        day: "日",
        list: "周列表"
      },
      calendarPlugins: [
        // plugins must be defined in the JS
        dayGridPlugin,
        timeGridPlugin,
        interactionPlugin, // needed for dateClick
        listPlugin
      ],
      calendarWeekends: true,
      calendarEvents: [
        // initial event data
        // {
        //   title: 'Event Now',
        //   start: new Date(),
        //   color: '#A61000'
        // }
      ],
      calendarApi: null,
      calendarData: [],
      queryParams: {}
    };
  },
  created() {
    getAllSchoolCalendars(this.queryParams).then(response => {
      this.calendarEvents = response.calendarData;
    });
  },
  methods: {
    getCalendarEvents(info, successCallback, failureCallback) {
      // const events = this.getSchoolCalendarList();
      console.log("ceshi11111111");
      getAllSchoolCalendars(this.queryParams).then(response => {
        this.calendarData = response.calendarData;
      });
      console.log("ceshi22222222");
      const events = this.calendarData;
      console.log(this.calendarData);
      console.log(events);
      // const events = [
      //   ...this.calendarEvents,
      //   {
      //     title: '五一劳动节',
      //     start: '2020-05-01 00:00:00',
      //     end: '2020-05-06 00:00:00',
      //     color:'orange'
      //   },
      //   {
      //     start: '2020-05-02 10:00:00',
      //     end: '2020-05-02 14:00:00',
      //     title: '东南大学计算机学术会议',
      //     color:'green'
      //   }
      // ]
      successCallback(events);
    },
    toggleWeekends() {
      this.calendarWeekends = !this.calendarWeekends; // update a property
    },
    gotoPast() {
      this.calendarApi.gotoDate("2019-08-01"); // call a method on the Calendar object
    },
    handleDateClick(arg) {
      if (confirm("Would you like to add an event to " + arg.dateStr + " ?")) {
        this.calendarEvents.push({
          // add new event data
          title: "New Event",
          start: arg.date,
          allDay: arg.allDay
        });
      }
      this.calendarApi.refetchEvents();
    },
    handleEventClick(info) {
      alert("活动: " + info.event.title);
    }
  },
  mounted() {
    this.calendarApi = this.$refs.fullCalendar.getApi();
  }
};
</script>

<style  lang="scss" scope>
// you must include each plugins' css
// paths prefixed with ~ signify node_modules
@import "~@fullcalendar/core/main.css";
@import "~@fullcalendar/daygrid/main.css";
@import "~@fullcalendar/timegrid/main.css";
@import "~@fullcalendar/list/main.css";
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
</style>
