<template>
  <div
    class="xs-height"
    :class="className"
    :style="{ height: height, width: width }"
  >
    <FullCalendar
      ref="fullCalendar"
      defaultView="dayGridMonth"
      locale="zh-cn"
      :buttonText="buttonText"
      :plugins="calendarPlugins"
      :events="calendarEvents"
      :eventLimit="true"
      :displayEventEnd="true"
      eventLimitText="更多"
      @eventClick="handleEventClick"
    />
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
  props: {
    className: {
      type: String,
      default: "chart"
    },
    width: {
      type: String,
      default: "100%"
    },
    height: {
      type: String,
      default: "480px"
    }
  },
  data() {
    return {
      header: {
        left: "prev,next today",
        center: "title",
        right: "dayGridMonth, listWeek"
      },
      buttonText: {
        today: "今天",
        month: "月",
        list: "周列表"
      },
      calendarPlugins: [
        // plugins must be defined in the JS
        dayGridPlugin,
        //timeGridPlugin,
        //interactionPlugin, // needed for dateClick
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
    handleEventClick(info) {
      this.msgSuccess("事件: " + info.event.title);
    }
  },
  mounted() {
    this.calendarApi = this.$refs.fullCalendar.getApi();
  }
};
</script>

<style lang="scss" scope>
// you must include each plugins' css
// paths prefixed with ~ signify node_modules
@import "~@fullcalendar/core/main.css";
@import "~@fullcalendar/daygrid/main.css";
@import "~@fullcalendar/timegrid/main.css";
@import "~@fullcalendar/list/main.css";
.calendar {
  width: 820px;
  height: 100%;
}
.fc-widget-content {
  .fc-scroller.fc-day-grid-container {
    height: 100% !important;
  }
  .fc-sun,
  .fc-sat {
    background: rgba(245, 246, 248, 0.6);
    //background: rgba(109, 113, 121, 0.6);
  }
}
.xs-btns-style {
  @media screen and (max-width: 768px) {
    display: flex;
    flex-wrap: wrap;
    .el-col {
      width: auto;
    }
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
    .fc-left > .fc-today-button,
    .fc-right > .fc-listWeek-button {
      display: none;
    }
  }
}
@media (max-width: 768.98px) {
  .xs-height {
    height: 100% !important;
    .fc button {
      height: 30px;
    }
    .fc-toolbar h2 {
      font-size: 16px !important;
    }
    .fc-ltr .fc-dayGrid-view .fc-day-top .fc-day-number {
      font-size: 12px;
    }
  }
}
</style>
