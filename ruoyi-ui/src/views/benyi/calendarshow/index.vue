<template>
  <div class="app-container">
    <el-row :gutter="24">
      <el-col :span="4" :xs="24">
        <el-row class="xs-btns-style" :gutter="10">
          <el-col v-for="item in btns" :key="item.title">
            <el-button
              type="primary"
              class="btn no-border-btn"
              :style="{ background: `${item.color}` }"
              >{{ item.title }}</el-button
            >
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

    <!-- 添加或修改园历管理(班级)对话框 -->
    <el-dialog :title="title" :visible.sync="open" class="v-dialog">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="事件名称" prop="name">
          <el-input
            disabled="true"
            v-model="form.name"
            size="small"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="活动类型" prop="type">
          <el-select
            disabled="true"
            v-model="form.type"
            size="small"
            placeholder="请选择活动类型"
          >
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="活动时间" prop="activitytime">
          <el-date-picker
            disabled="true"
            clearable
            size="small"
            class="my-date-picker"
            v-model="form.activitytime"
            type="daterange"
            value-format="yyyy-MM-dd"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            disabled="true"
            type="textarea"
            v-model="form.remark"
            size="small"
            placeholder=""
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import listPlugin from "@fullcalendar/list";
import { getAllSchoolCalendars } from "@/api/benyi/calendar";
import { getSchoolcalendarclass } from "@/api/benyi/schoolcalendarclass";
import { getSchoolcalendar } from "@/api/benyi/schoolcalendar";
export default {
  name: "fullcalendar_page",
  components: {
    FullCalendar,
  },
  data() {
    return {
      btns: [
        {
          color: "#52c41a",
          title: "家长参与",
        },
        {
          color: "#36cfc9",
          title: "教师参与",
        },
        {
          color: "#135200",
          title: "大型活动",
        },
        {
          color: "#1890ff",
          title: "教学活动",
        },
        {
          color: "#eb2f96",
          title: "节假日",
        },
      ],
      views: {
        list: {
          noEventsMessage: "暂无日程",
        },
      },
      // header: {
      //   left: "prev,next today",
      //   center: "title",
      //   right: "dayGridMonth,timeGridWeek,timeGridDay listWeek"
      // },
      // buttonText: {
      //   today: "今天",
      //   month: "月",
      //   week: "周",
      //   day: "日",
      //   list: "周列表"
      // },
      header: {
        left: "prev,next today",
        center: "title",
        right: "dayGridMonth, listWeek",
      },
      buttonText: {
        today: "今天",
        month: "月",
        list: "周列表",
      },
      calendarPlugins: [
        // plugins must be defined in the JS
        dayGridPlugin,
        //timeGridPlugin,
        //interactionPlugin, // needed for dateClick
        listPlugin,
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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 活动类型字典
      typeOptions: [],
      // 表单参数
      form: {},
      queryParams: {},
    };
  },
  created() {
    getAllSchoolCalendars(this.queryParams).then((response) => {
      this.calendarEvents = response.calendarData;
    });
    this.getDicts("sys_schoolcalendartype").then((response) => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    handleEventClick(info) {
      //console.log(info);
      var id = info.event.id;
      this.title = "园历详细信息";
      var group = info.event.groupId;
      //group==1 代表幼儿园班级园历信息
      //group==2 代表幼儿园园历信息
      if (group == "1") {
        this.open = true;
        var myArray = new Array(2);
        getSchoolcalendarclass(id).then((response) => {
          this.form = response.data;
          myArray[0] = response.data.activitytime;
          myArray[1] = response.data.activityendtime;
          //console.log(myArray);
          this.form.activitytime = myArray;
        });
      } else if (group == "2") {
        this.open = true;
        var myArray = new Array(2);
        getSchoolcalendar(id).then((response) => {
          this.form = response.data;
          myArray[0] = response.data.activitytime;
          myArray[1] = response.data.activityendtime;
          //console.log(myArray);
          this.form.activitytime = myArray;
        });
      } else {
        this.msgSuccess("事件: " + info.event.title);
      }
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        type: undefined,
        activitytime: undefined,
        remark: undefined,
      };
      this.resetForm("form");
    },
  },
  mounted() {
    this.calendarApi = this.$refs.fullCalendar.getApi();
  },
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
.fc-time {
  display: none;
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
  @media (max-width: 768.98px) {
    width: auto;
    padding: 6px 7px;
    font-size: 12px;
  }
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
      font-size: 16px;
    }
  }
}

.el-select {
  width: 100%;
}
.my-date-picker {
  width: 100% !important;
}
.no-margin ::v-deep.el-form-item__content {
  margin: 0 !important;
}
</style>