<template>
  <div>
    <el-dialog :title="title"
               :visible.sync="dialogTableVisible"
               :modal="false">
      <el-image style="width:267px; height: 220px"
                :src="url"
                fit="fill">
      </el-image>
      <el-table :data="gridData"
                style="display: inline-block;width: calc(100% - 267px);"
                max-height="217">
        <el-table-column property="time"
                         label=时间
                         width="160"
                         fixed
                         :render-header="renderHeader"></el-table-column>
        <el-table-column v-for="(sensorWarning,index) in sensorWarningList"
                         :key="index"
                         :property="`${sensorWarning['factors_name']}_calc`"
                         :label="sensorWarning['factors_name']"
                         width="160">
          <template slot-scope="scope">
            <div :style="{'color':textStyle(scope)}">
              {{scope.row[scope.column.property]}}
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div ref="chart"
           style="width:100%; height:372px"></div>
    </el-dialog>
  </div>
</template>

<script>
import { globalBus } from "@/page/group/globalBus";
import { getMonitorData } from "@/api/monitor";
import { formatDate } from "@/utils/time.js"
export default {
  name: "myDialog",
  data () {
    return {
      dialogTableVisible: false,
      url: require("../../../public/icon/test.jpg"),
      gridData: [],
      title: '监测信息',
      option: {
        tooltip: {
          trigger: 'axis'
        },
        legend: {},
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value}'
          }
        },
        series: [
        ]
      },
      monitorRecord: [],
      sensorWarningList: [],
      showLength: 10,
      value1: [],
    };
  },
  mounted () {
    globalBus.$on("show", this.init);
  },
  methods: {
    init (url) {
      // if (obj.name) {
      //   this.title = obj.name + '_' + obj.location
      // } else {
      //   this.title = '监测信息'
      // }
      getMonitorData(url).then(response => {
        // 获取数据
        this.monitorRecord = response.result.records;
        this.sensorWarningList = response.result.sensorWarningList
        this.makeData()
      })
    },
    // 根据时间过滤
    filterByTime () {
      this.option.series.forEach(series => series.data = [])
      this.option.xAxis.data = []
      let time1 = this.value1[0]
      let time2 = this.value1[1]
      let dataFilter = this.gridData.filter(dataTable => {
        return new Date(dataTable.time) >= new Date(time1) && new Date(dataTable.time) <= new Date(time2)
      })
      dataFilter.forEach(everyData => {
        for (let key in everyData) {
          if (key == 'time') {
            this.option.xAxis.data.push(everyData[key])
          } else {
            let index = this.option.series.findIndex(everySeries => key.indexOf(everySeries.name) != -1)
            this.option.series[index].data.push(everyData[key])
          }
        }
      })
      this.gridData = dataFilter
    },
    makeData () {
      const that = this
      //赋值前先把所有都清空
      that.gridData = [];
      that.option.xAxis.data = [];
      that.option.series = []
      for (let i = 0; i < that.showLength; i++) {
        let columnData = {}
        const formate_date = formatDate(parseInt(that.monitorRecord[i].Time))
        columnData.time = formate_date
        that.option.xAxis.data[i] = formate_date
        for (let item in that.monitorRecord[i]) {
          if (item.indexOf('_calc') != -1) {
            // 此处的this为何是未定义？
            console.log(this)
            // 组织表格展示的数据
            let splitTitle = item.split('.')
            let newTitle = splitTitle[splitTitle.length - 1]
            columnData[newTitle] = that.monitorRecord[i][item]
            // 组织图表展示的数据
            let seriesData = that.option.series.find((series) => {
              return series.name == newTitle.split('_')[0]
            })
            // 如果存在，则先找到index，然后将数据添加到data里面。如果不存在，则新建一个对象添加到series里面，再将数据添加到新建对象的data里面
            if (seriesData) {
              let index = that.option.series.findIndex(item => item.name == seriesData.name)
              that.option.series[index].data.push(that.monitorRecord[i][item])
            } else {
              let length = that.option.series.push({
                name: newTitle.split('_')[0],
                type: 'line',
                data: []
              })
              that.option.series[length - 1].data.push(that.monitorRecord[i][item])
            }
          }
        }
        that.gridData.push(columnData)
      }
    },
    renderHeader (h) {
      return (
        <span>
          时间
          <el-popover placement='bottom' trigger="click" >
            <span slot="reference">
              <i class="el-icon-date"></i>
            </span>
            <el-date-picker
              value={this.value1}
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              onInput={value => this.value1 = value}
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-popover>
          <el-popover placement='bottom' trigger="click" >
            <span slot="reference">
              <i class="el-icon-search"></i>
            </span>
            <el-input
              value={this.showLength}
              type='number'
              onInput={value => this.showLength = value}
            ></el-input>
          </el-popover>
        </span >
      );
    }
  },
  computed: {
    textStyle () {
      return function (scope) {
        let property = scope.column.property
        let data = scope.row[property]

        if (property == 'temperature_calc') return
        let warning = this.sensorWarningList.filter(sensorWarning => {
          return property.indexOf(sensorWarning['factors_name']) != -1
        })

        const warning_up = parseInt(warning[0].threshold_warning_up)
        const warning_lower = parseInt(warning[0].threshold_warning_lower)
        const alarm_lower = parseInt(warning[0].threshold_alarm_lower)
        const alarm_up = parseInt(warning[0].threshold_alarm_up)

        if (data > warning_lower && data < warning_up) {
          return 'green'
        } else if ((data > warning_up && data < alarm_up) ||
          (data < warning_lower && data > alarm_lower)) {
          return 'orange'
        } else {
          return 'red'
        }
      }
    }
  },
  watch: {
    option: {
      handler (newValue, oldValue) {
        // echarts初始化
        this.dialogTableVisible = true;
        //nextTick在dom更新完之后再调用其函数
        this.$nextTick(() => {
          const chart = this.$refs.chart;
          if (chart) {
            const myChart = this.$echarts.init(chart)
            myChart.setOption(newValue)
            // window.addEventListener("resize", function () {
            //   myChart.resize()
            // })
            // this.$on('hook:destroyed', () => {
            //   window.removeEventListener("resize", function () {
            //     myChart.resize();
            //   });
            // })
          }
        })
      },
      deep: true
    },
    showLength (newValue, oldValue) {
      this.makeData()
    },
    value1 (newValue, oldValue) {
      this.filterByTime()
      document.body.click()
    }
  },
  beforeDestroy () {
    globalBus.$off('show')
  }
}
</script>

<style scoped lang='css'>
.el-table__cell {
  background-color: #fff !important;
  color: #bcc8d4 !important;
}
.el-dialog__body {
  background-color: #fff !important;
}
.el-dialog__header {
  background-color: #fff;
  color: #bcc8d4;
}
.avue-dialog .el-dialog__title,
.avue-dialog .el-drawer__header {
  color: #bcc8d4;
}
.avue-form__group--flex {
  background-color: #fff;
}
</style>
