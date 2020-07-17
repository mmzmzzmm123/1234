<template>
  <div class="dashboard-editor-container">


    <el-row :gutter="32">
      <el-col :xs="24" :sm="12" :lg="6">
        <div class="ibox">
          <div id="chart_zhengpin_avg" style="width: 100%;height: 204px;left: 0%;"></div>
          <h2 class="text-center" style="margin-top: 0px;">正品率</h2>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <div class="ibox">
          <div id="chart_change_moulding_time"  style="width: 100%;height: 204px;left: 0%;"></div>
          <h2 class="text-center" style="margin-top: 0px;">平均换模时间</h2>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <div class="ibox">
          <div id="chart_density"  style="width: 100%;height: 204px;left: 0%;"></div>
          <h2 class="text-center" style="margin-top: 0px;">平均密度</h2>
        </div>
      </el-col>
      <el-col :xs="24" :sm="12" :lg="6">
        <div class="ibox">
          <div id="chart_energy_avg"  style="width: 100%;height: 204px;left: 0%;"></div>
          <h2 class="text-center" style="margin-top: 0px;">产能达标率</h2>
        </div>
      </el-col>
    </el-row>

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <div class="ibox ">
        <div class="ibox-title">
          <h5>产线明细 </h5>
        </div>
        <div class="ibox-content">
          <el-table
            :data="tableData"
            stripe
            border
            style="width: 100%">
            <el-table-column
              prop="line"
              label="线号"
              width="100">
            </el-table-column>
            <el-table-column
              prop="Moulds"
              label="模具">
            </el-table-column>
            <el-table-column
              prop="sumbox"
              label="完成箱数">
            </el-table-column>
            <el-table-column
              prop="avg_currentcapacity"
              label="平均实际产能">
            </el-table-column>
            <el-table-column
              prop="avg_normalcapacity"
              label="平均标准产能">
            </el-table-column>
            <el-table-column
              prop="capacityrate"
              label="产能达标率">
            </el-table-column>
            <el-table-column
              prop="yieldrate"
              label="正品达标率">
            </el-table-column>
            <el-table-column
              prop="avg_density"
              label="平均密度">
            </el-table-column>
            <el-table-column
              prop="lastchangemouldtime"
              label="上次换模时间">
            </el-table-column>


          </el-table>
        </div>
      </div>
    </el-row>

  </div>
</template>

<script>
  import echarts from 'echarts'

  require('echarts/theme/macarons') // echarts theme
  import resize from '../dashboard/mixins/resize'
  import {getLastDay} from '@/api/sd_dashboard/sd_dashboard'


  const lineChartData = {
    xAxisData: [],
    actualData: []
  }

  export default {
    mixins: [resize],
    name: 'density30day',
    data() {
      return {
        tableData: []
      }
    },

    created() {
      this.getData()

    },
    mounted() {


    },
    methods: {
      getData() {

        getLastDay().then(response => {

          var j;
          var avg_zhengpin_avg = 0;
          var avg_density = 0;
          var avg_energy_avg = 0;
          var avg_chang_moulding_time = 0;
          var sum_quantity = 0;
          var sum_currentfinishedquantity = 0;
          j = 0;

          var data_ajax = response.data;

          for (var i = 0; i < response.data.length; i++) {
            lineChartData.xAxisData.push((response.data[i].time + '').replace(' 00:00:00.0', ''))
            lineChartData.actualData.push(parseFloat(response.data[i].density).toFixed(4))


            this.tableData.push({
              line: response.data[i].line,
              Moulds: response.data[i].Moulds,
              sumbox: response.data[i].sumbox,
              avg_currentcapacity: (response.data[i].avg_currentcapacity * 1 * response.data[i].sum_box_meter / response.data[i].sum_device_meter).toFixed(2),
              avg_normalcapacity: (response.data[i].avg_normalcapacity * 1).toFixed(2),
              capacityrate: (response.data[i].avg_currentcapacity / response.data[i].avg_normalcapacity * response.data[i].sum_box_meter / response.data[i].sum_device_meter * 100).toFixed(2) + "%",
              yieldrate: (response.data[i].sum_box_meter / response.data[i].sum_device_meter * 100).toFixed(2) + "%",
              avg_density: (response.data[i].avg_density * 1).toFixed(4),
              lastchangemouldtime: (response.data[i].lastchangemouldtime),

            })

            if (data_ajax[i].lastchangemouldtime !== 0) {
              avg_chang_moulding_time = avg_chang_moulding_time + (data_ajax[i].lastchangemouldtime) * 1;
              j = j + 1;
            }
            avg_density = parseFloat(avg_density) + parseFloat(data_ajax[i].avg_density);
            avg_energy_avg = avg_energy_avg + (data_ajax[i].avg_currentcapacity / data_ajax[i].avg_normalcapacity);
            avg_zhengpin_avg = avg_zhengpin_avg + (data_ajax[i].sum_box_meter / data_ajax[i].sum_device_meter);
            sum_quantity = 2000;
            sum_currentfinishedquantity = sum_currentfinishedquantity + (data_ajax[i].sumbox) * 1;


          }

          const chart_energy_avg = echarts.init(document.getElementById("chart_energy_avg"));
          const chart_zhengpin_avg = echarts.init(document.getElementById("chart_zhengpin_avg"));
          const chart_density = echarts.init(document.getElementById("chart_density"));
          const chart_change_moulding_time = echarts.init(document.getElementById("chart_change_moulding_time"));

          // 设置仪表盘数据
          option_zhengpin_avg.series[0].data[0].value = (avg_zhengpin_avg / data_ajax.length * 100).toFixed(0);
          chart_zhengpin_avg.setOption(option_zhengpin_avg, true);

          option_density.series[0].data[0].value = (avg_density / data_ajax.length).toFixed(3);
          chart_density.setOption(option_density, true);

          //option_change_moulding_time.series[0].data[0].value = (avg_chang_moulding_time / data_ajax.length).toFixed(0);
          option_change_moulding_time.series[0].data[0].value =  0 ;
          chart_change_moulding_time.setOption(option_change_moulding_time, true);

          option_energy_avg.series[0].data[0].value = (avg_energy_avg / data_ajax.length * avg_zhengpin_avg / data_ajax.length * 100).toFixed(0);
          chart_energy_avg.setOption(option_energy_avg, true);

        })
      }
    }

  }


  var colorTemplate_energy_avg = [//50-85  85-100  100-120
    [0.5, "rgba(255,0,0,0.8)"],
    [5 / 7, "rgba(0,180,0,0.8)"],
    [1, "rgba(0,200,250,0.8)"]
  ];
  var colorTemplate_zhengpin_avg = [//50-75  75-85  85-100
    [6 / 10, "rgba(255,0,0,0.8)"],
    [7 / 10, "rgba(0,180,0,0.8)"],
    [1, "rgba(0,200,250,0.8)"]
  ];
  var colorTemplate_density = [//0.4-0.42  红  0.4-0.38  0.38-0.36
    [1 / 3, "rgba(0,200,250,0.8)"],
    [2 / 3, "rgba(0,180,0,0.8)"],
    [1, "rgba(255,0,0,0.8)"]
  ];
  var colorTemplate_change_moulding_time = [ //30-70  蓝  70-90 绿  90-120 红
    [4 / 9, "rgba(0,200,250,0.8)"],
    [6 / 9, "rgba(0,180,0,0.8)"],
    [1, "rgba(255,0,0,0.8)"]
  ];


  var data_energy_avg = [{
    // name: "产能达标率",
    value: 50,
  }];

  var data_zhengpin_avg = [{
    // name: "正品率",
    value: 0,
  }];
  var data_density = [{
    // name: "密度",
    value: 0,
  }];
  var data_change_moulding_time = [{
    // name: "换模时间",
    value: 0,
  }];

  // 指定图表的配置项和数据
  var option_energy_avg = {

    //backgroundColor: "#000",

    tooltip: { // 本系列特定的 tooltip 设定。
      show: true,
      formatter: "{b}：{c}%",
      backgroundColor: "rgba(50,50,50,0.7)", // 提示框浮层的背景颜色。注意：series.tooltip 仅在 tooltip.trigger 为 'item' 时有效。
      borderColor: "#333", // 提示框浮层的边框颜色。...
      borderWidth: 0, // 提示框浮层的边框宽。...
      padding: 5, // 提示框浮层内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距。...
      textStyle: { // 提示框浮层的文本样式。...
        // color ,fontStyle ,fontWeight ,fontFamily ,fontSize ,lineHeight ,.......
      },
    },

    series: [{
      name: "单仪表盘示例", // 系列名称,用于tooltip的显示，legend 的图例筛选，在 setOption 更新数据和配置项时用于指定对应的系列。
      type: "gauge", // 系列类型
      radius: "80%", // 参数:number, string。 仪表盘半径,默认 75% ，可以是相对于容器高宽中较小的一项的一半的百分比，也可以是绝对的数值。
      center: ["50%", "55%"], // 仪表盘位置(圆心坐标)
      startAngle: 225, // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
      endAngle: -45, // 仪表盘结束角度,默认 -45
      clockwise: true, // 仪表盘刻度是否是顺时针增长,默认 true。
      min: 50, // 最小的数据值,默认 0 。映射到 minAngle。
      max: 120, // 最大的数据值,默认 100 。映射到 maxAngle。
      splitNumber: 7, // 仪表盘刻度的分割段数,默认 10。
      axisLine: { // 仪表盘轴线(轮廓线)相关配置。
        show: true, // 是否显示仪表盘轴线(轮廓线),默认 true。
        lineStyle: { // 仪表盘轴线样式。
          color: colorTemplate_energy_avg, //仪表盘的轴线可以被分成不同颜色的多段。每段的  结束位置(范围是[0,1]) 和  颜色  可以通过一个数组来表示。默认取值：[[0.2, '#91c7ae'], [0.8, '#63869e'], [1, '#c23531']]
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 15, //轴线宽度,默认 30。
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        }
      },

      splitLine: { // 分隔线样式。
        show: true, // 是否显示分隔线,默认 true。
        length: 21, // 分隔线线长。支持相对半径的百分比,默认 30。
        lineStyle: { // 分隔线样式。
          color: "#eee", //线的颜色,默认 #eee。
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 2, //线度,默认 2。
          type: "solid", //线的类型,默认 solid。 此外还有 dashed,dotted
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        }
      },

      axisTick: { // 刻度(线)样式。
        show: true, // 是否显示刻度(线),默认 true。
        splitNumber: 5, // 分隔线之间分割的刻度数,默认 5。
        length: 8, // 刻度线长。支持相对半径的百分比,默认 8。
        lineStyle: { // 刻度线样式。
          color: "#eee", //线的颜色,默认 #eee。
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 1, //线度,默认 1。
          type: "solid", //线的类型,默认 solid。 此外还有 dashed,dotted
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        },
      },

      axisLabel: { // 刻度标签。
        show: true, // 是否显示标签,默认 true。
        distance: 1, // 标签与刻度线的距离,默认 5。
        color: "#000", // 文字的颜色,默认 #fff。
        fontSize: 10, // 文字的字体大小,默认 5。
        formatter: "{value}", // 刻度标签的内容格式器，支持字符串模板和回调函数两种形式。 示例:// 使用字符串模板，模板变量为刻度默认标签 {value},如:formatter: '{value} kg'; // 使用函数模板，函数参数分别为刻度数值,如formatter: function (value) {return value + 'km/h';}
      },

      pointer: { // 仪表盘指针。
        show: true, // 是否显示指针,默认 true。
        length: "70%", // 指针长度，可以是绝对数值，也可以是相对于半径的百分比,默认 80%。
        width: 5, // 指针宽度,默认 8。
      },

      itemStyle: { // 仪表盘指针样式。
        color: "auto", // 指针颜色，默认(auto)取数值所在的区间的颜色
        opacity: 1, // 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
        borderWidth: 0, // 描边线宽,默认 0。为 0 时无描边。
        borderType: "solid", // 柱条的描边类型，默认为实线，支持 'solid', 'dashed', 'dotted'。
        borderColor: "#000", // 图形的描边颜色,默认 "#000"。支持的颜色格式同 color，不支持回调函数。
        //shadowBlur: 10, // (发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
        //shadowColor: "#000", // 阴影颜色。支持的格式同color。
      },

      emphasis: { // 高亮的 仪表盘指针样式
        itemStyle: {
          //高亮 和正常  两者具有同样的配置项,只是在不同状态下配置项的值不同。
        }
      },

      title: { // 仪表盘标题。
        show: true, // 是否显示标题,默认 true。
        offsetCenter: [0,
          "20%"], //相对于仪表盘中心的偏移位置，数组第一项是水平方向的偏移，第二项是垂直方向的偏移。可以是绝对的数值，也可以是相对于仪表盘半径的百分比。
        color: "#000", // 文字的颜色,默认 #333。
        fontSize: 15, // 文字的字体大小,默认 15。
      },

      detail: { // 仪表盘详情，用于显示数据。
        show: true, // 是否显示详情,默认 true。
        offsetCenter: [0,
          "50%"], // 相对于仪表盘中心的偏移位置，数组第一项是水平方向的偏移，第二项是垂直方向的偏移。可以是绝对的数值，也可以是相对于仪表盘半径的百分比。
        color: "auto", // 文字的颜色,默认 auto。
        fontSize: 20, // 文字的字体大小,默认 15。
        formatter: "{value}%", // 格式化函数或者字符串
      },

      data: data_energy_avg
    }]

  };
  // 指定图表的配置项和数据
  var option_zhengpin_avg = {

    //backgroundColor: "#000",

    tooltip: { // 本系列特定的 tooltip 设定。
      show: true,
      formatter: "{b}：{c}%",
      backgroundColor: "rgba(50,50,50,0.7)", // 提示框浮层的背景颜色。注意：series.tooltip 仅在 tooltip.trigger 为 'item' 时有效。
      borderColor: "#333", // 提示框浮层的边框颜色。...
      borderWidth: 0, // 提示框浮层的边框宽。...
      padding: 5, // 提示框浮层内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距。...
      textStyle: { // 提示框浮层的文本样式。...
        // color ,fontStyle ,fontWeight ,fontFamily ,fontSize ,lineHeight ,.......
      },
    },

    series: [{
      name: "单仪表盘示例", // 系列名称,用于tooltip的显示，legend 的图例筛选，在 setOption 更新数据和配置项时用于指定对应的系列。
      type: "gauge", // 系列类型
      radius: "80%", // 参数:number, string。 仪表盘半径,默认 75% ，可以是相对于容器高宽中较小的一项的一半的百分比，也可以是绝对的数值。
      center: ["50%", "55%"], // 仪表盘位置(圆心坐标)
      startAngle: 225, // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
      endAngle: -45, // 仪表盘结束角度,默认 -45
      clockwise: true, // 仪表盘刻度是否是顺时针增长,默认 true。
      min: 50, // 最小的数据值,默认 0 。映射到 minAngle。
      max: 100, // 最大的数据值,默认 100 。映射到 maxAngle。
      splitNumber: 5, // 仪表盘刻度的分割段数,默认 10。

      axisLine: { // 仪表盘轴线(轮廓线)相关配置。
        show: true, // 是否显示仪表盘轴线(轮廓线),默认 true。
        lineStyle: { // 仪表盘轴线样式。
          color: colorTemplate_zhengpin_avg, //仪表盘的轴线可以被分成不同颜色的多段。每段的  结束位置(范围是[0,1]) 和  颜色  可以通过一个数组来表示。默认取值：[[0.2, '#91c7ae'], [0.8, '#63869e'], [1, '#c23531']]
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 15, //轴线宽度,默认 30。
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        }
      },

      splitLine: { // 分隔线样式。
        show: true, // 是否显示分隔线,默认 true。
        length: 21, // 分隔线线长。支持相对半径的百分比,默认 30。
        lineStyle: { // 分隔线样式。
          color: "#eee", //线的颜色,默认 #eee。
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 2, //线度,默认 2。
          type: "solid", //线的类型,默认 solid。 此外还有 dashed,dotted
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        }
      },

      axisTick: { // 刻度(线)样式。
        show: true, // 是否显示刻度(线),默认 true。
        splitNumber: 5, // 分隔线之间分割的刻度数,默认 5。
        length: 8, // 刻度线长。支持相对半径的百分比,默认 8。
        lineStyle: { // 刻度线样式。
          color: "#eee", //线的颜色,默认 #eee。
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 1, //线度,默认 1。
          type: "solid", //线的类型,默认 solid。 此外还有 dashed,dotted
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        },
      },

      axisLabel: { // 刻度标签。
        show: true, // 是否显示标签,默认 true。
        distance: 1, // 标签与刻度线的距离,默认 5。
        color: "#000", // 文字的颜色,默认 #fff。
        fontSize: 10, // 文字的字体大小,默认 5。
        formatter: "{value}", // 刻度标签的内容格式器，支持字符串模板和回调函数两种形式。 示例:// 使用字符串模板，模板变量为刻度默认标签 {value},如:formatter: '{value} kg'; // 使用函数模板，函数参数分别为刻度数值,如formatter: function (value) {return value + 'km/h';}
      },

      pointer: { // 仪表盘指针。
        show: true, // 是否显示指针,默认 true。
        length: "70%", // 指针长度，可以是绝对数值，也可以是相对于半径的百分比,默认 80%。
        width: 5, // 指针宽度,默认 8。
      },

      itemStyle: { // 仪表盘指针样式。
        color: "auto", // 指针颜色，默认(auto)取数值所在的区间的颜色
        opacity: 1, // 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
        borderWidth: 0, // 描边线宽,默认 0。为 0 时无描边。
        borderType: "solid", // 柱条的描边类型，默认为实线，支持 'solid', 'dashed', 'dotted'。
        borderColor: "#000", // 图形的描边颜色,默认 "#000"。支持的颜色格式同 color，不支持回调函数。
        //shadowBlur: 10, // (发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
        //shadowColor: "#000", // 阴影颜色。支持的格式同color。
      },

      emphasis: { // 高亮的 仪表盘指针样式
        itemStyle: {
          //高亮 和正常  两者具有同样的配置项,只是在不同状态下配置项的值不同。
        }
      },

      title: { // 仪表盘标题。
        show: true, // 是否显示标题,默认 true。
        offsetCenter: [0,
          "20%"], //相对于仪表盘中心的偏移位置，数组第一项是水平方向的偏移，第二项是垂直方向的偏移。可以是绝对的数值，也可以是相对于仪表盘半径的百分比。
        color: "#000", // 文字的颜色,默认 #333。
        fontSize: 15, // 文字的字体大小,默认 15。
      },

      detail: { // 仪表盘详情，用于显示数据。
        show: true, // 是否显示详情,默认 true。
        offsetCenter: [0,
          "50%"], // 相对于仪表盘中心的偏移位置，数组第一项是水平方向的偏移，第二项是垂直方向的偏移。可以是绝对的数值，也可以是相对于仪表盘半径的百分比。
        color: "auto", // 文字的颜色,默认 auto。
        fontSize: 20, // 文字的字体大小,默认 15。
        formatter: "{value}%", // 格式化函数或者字符串
      },

      data: data_zhengpin_avg
    }]

  };
  // 指定图表的配置项和数据
  var option_density = {

    //backgroundColor: "#000",

    tooltip: { // 本系列特定的 tooltip 设定。
      show: true,
      formatter: "{c}",
      backgroundColor: "rgba(50,50,50,0.7)", // 提示框浮层的背景颜色。注意：series.tooltip 仅在 tooltip.trigger 为 'item' 时有效。
      borderColor: "#333", // 提示框浮层的边框颜色。...
      borderWidth: 0, // 提示框浮层的边框宽。...
      padding: 5, // 提示框浮层内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距。...
      textStyle: { // 提示框浮层的文本样式。...
        // color ,fontStyle ,fontWeight ,fontFamily ,fontSize ,lineHeight ,.......
      },
    },

    series: [{
      name: "单仪表盘示例", // 系列名称,用于tooltip的显示，legend 的图例筛选，在 setOption 更新数据和配置项时用于指定对应的系列。
      type: "gauge", // 系列类型
      radius: "80%", // 参数:number, string。 仪表盘半径,默认 75% ，可以是相对于容器高宽中较小的一项的一半的百分比，也可以是绝对的数值。
      center: ["50%", "55%"], // 仪表盘位置(圆心坐标)
      startAngle: 225, // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
      endAngle: -45, // 仪表盘结束角度,默认 -45
      clockwise: true, // 仪表盘刻度是否是顺时针增长,默认 true。
      min: 0.36, // 最小的数据值,默认 0 。映射到 minAngle。
      max: 0.42, // 最大的数据值,默认 100 。映射到 maxAngle。
      splitNumber: 6, // 仪表盘刻度的分割段数,默认 10。

      axisLine: { // 仪表盘轴线(轮廓线)相关配置。
        show: true, // 是否显示仪表盘轴线(轮廓线),默认 true。
        lineStyle: { // 仪表盘轴线样式。
          color: colorTemplate_density, //仪表盘的轴线可以被分成不同颜色的多段。每段的  结束位置(范围是[0,1]) 和  颜色  可以通过一个数组来表示。默认取值：[[0.2, '#91c7ae'], [0.8, '#63869e'], [1, '#c23531']]
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 15, //轴线宽度,默认 30。
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        }
      },

      splitLine: { // 分隔线样式。
        show: true, // 是否显示分隔线,默认 true。
        length: 21, // 分隔线线长。支持相对半径的百分比,默认 30。
        lineStyle: { // 分隔线样式。
          color: "#eee", //线的颜色,默认 #eee。
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 2, //线度,默认 2。
          type: "solid", //线的类型,默认 solid。 此外还有 dashed,dotted
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        }
      },

      axisTick: { // 刻度(线)样式。
        show: true, // 是否显示刻度(线),默认 true。
        splitNumber: 5, // 分隔线之间分割的刻度数,默认 5。
        length: 8, // 刻度线长。支持相对半径的百分比,默认 8。
        lineStyle: { // 刻度线样式。
          color: "#eee", //线的颜色,默认 #eee。
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 1, //线度,默认 1。
          type: "solid", //线的类型,默认 solid。 此外还有 dashed,dotted
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        },
      },

      axisLabel: { // 刻度标签。
        show: true, // 是否显示标签,默认 true。
        distance: 1, // 标签与刻度线的距离,默认 5。
        color: "#000", // 文字的颜色,默认 #fff。
        fontSize: 10, // 文字的字体大小,默认 5。
        formatter: "{value}", // 刻度标签的内容格式器，支持字符串模板和回调函数两种形式。 示例:// 使用字符串模板，模板变量为刻度默认标签 {value},如:formatter: '{value} kg'; // 使用函数模板，函数参数分别为刻度数值,如formatter: function (value) {return value + 'km/h';}
      },

      pointer: { // 仪表盘指针。
        show: true, // 是否显示指针,默认 true。
        length: "70%", // 指针长度，可以是绝对数值，也可以是相对于半径的百分比,默认 80%。
        width: 5, // 指针宽度,默认 8。
      },

      itemStyle: { // 仪表盘指针样式。
        color: "auto", // 指针颜色，默认(auto)取数值所在的区间的颜色
        opacity: 1, // 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
        borderWidth: 0, // 描边线宽,默认 0。为 0 时无描边。
        borderType: "solid", // 柱条的描边类型，默认为实线，支持 'solid', 'dashed', 'dotted'。
        borderColor: "#000", // 图形的描边颜色,默认 "#000"。支持的颜色格式同 color，不支持回调函数。
        //shadowBlur: 10, // (发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
        //shadowColor: "#000", // 阴影颜色。支持的格式同color。
      },

      emphasis: { // 高亮的 仪表盘指针样式
        itemStyle: {
          //高亮 和正常  两者具有同样的配置项,只是在不同状态下配置项的值不同。
        }
      },

      title: { // 仪表盘标题。
        show: true, // 是否显示标题,默认 true。
        offsetCenter: [0,
          "20%"], //相对于仪表盘中心的偏移位置，数组第一项是水平方向的偏移，第二项是垂直方向的偏移。可以是绝对的数值，也可以是相对于仪表盘半径的百分比。
        color: "#000", // 文字的颜色,默认 #333。
        fontSize: 15, // 文字的字体大小,默认 15。
      },

      detail: { // 仪表盘详情，用于显示数据。
        show: true, // 是否显示详情,默认 true。
        offsetCenter: [0,
          "50%"], // 相对于仪表盘中心的偏移位置，数组第一项是水平方向的偏移，第二项是垂直方向的偏移。可以是绝对的数值，也可以是相对于仪表盘半径的百分比。
        color: "auto", // 文字的颜色,默认 auto。
        fontSize: 20, // 文字的字体大小,默认 15。
        formatter: "{value}", // 格式化函数或者字符串
      },

      data: data_density
    }]

  };
  // 指定图表的配置项和数据
  var option_change_moulding_time = {

    //backgroundColor: "#000",

    tooltip: { // 本系列特定的 tooltip 设定。
      show: true,
      formatter: "{b}：{c}%",
      backgroundColor: "rgba(50,50,50,0.7)", // 提示框浮层的背景颜色。注意：series.tooltip 仅在 tooltip.trigger 为 'item' 时有效。
      borderColor: "#333", // 提示框浮层的边框颜色。...
      borderWidth: 0, // 提示框浮层的边框宽。...
      padding: 5, // 提示框浮层内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距。...
      textStyle: { // 提示框浮层的文本样式。...
        // color ,fontStyle ,fontWeight ,fontFamily ,fontSize ,lineHeight ,.......
      },
    },

    series: [{
      name: "单仪表盘示例", // 系列名称,用于tooltip的显示，legend 的图例筛选，在 setOption 更新数据和配置项时用于指定对应的系列。
      type: "gauge", // 系列类型
      radius: "80%", // 参数:number, string。 仪表盘半径,默认 75% ，可以是相对于容器高宽中较小的一项的一半的百分比，也可以是绝对的数值。
      center: ["50%", "55%"], // 仪表盘位置(圆心坐标)
      startAngle: 225, // 仪表盘起始角度,默认 225。圆心 正右手侧为0度，正上方为90度，正左手侧为180度。
      endAngle: -45, // 仪表盘结束角度,默认 -45
      clockwise: true, // 仪表盘刻度是否是顺时针增长,默认 true。
      min: 30, // 最小的数据值,默认 0 。映射到 minAngle。
      max: 120, // 最大的数据值,默认 100 。映射到 maxAngle。
      splitNumber: 9, // 仪表盘刻度的分割段数,默认 10。

      axisLine: { // 仪表盘轴线(轮廓线)相关配置。
        show: true, // 是否显示仪表盘轴线(轮廓线),默认 true。
        lineStyle: { // 仪表盘轴线样式。
          color: colorTemplate_change_moulding_time, //仪表盘的轴线可以被分成不同颜色的多段。每段的  结束位置(范围是[0,1]) 和  颜色  可以通过一个数组来表示。默认取值：[[0.2, '#91c7ae'], [0.8, '#63869e'], [1, '#c23531']]
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 15, //轴线宽度,默认 30。
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        }
      },

      splitLine: { // 分隔线样式。
        show: true, // 是否显示分隔线,默认 true。
        length: 21, // 分隔线线长。支持相对半径的百分比,默认 30。
        lineStyle: { // 分隔线样式。
          color: "#eee", //线的颜色,默认 #eee。
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 2, //线度,默认 2。
          type: "solid", //线的类型,默认 solid。 此外还有 dashed,dotted
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        }
      },

      axisTick: { // 刻度(线)样式。
        show: true, // 是否显示刻度(线),默认 true。
        splitNumber: 5, // 分隔线之间分割的刻度数,默认 5。
        length: 8, // 刻度线长。支持相对半径的百分比,默认 8。
        lineStyle: { // 刻度线样式。
          color: "#eee", //线的颜色,默认 #eee。
          opacity: 1, //图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
          width: 1, //线度,默认 1。
          type: "solid", //线的类型,默认 solid。 此外还有 dashed,dotted
          //shadowBlur: 10, //(发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
          //shadowColor: "#000", //阴影颜色。支持的格式同color。
        },
      },
      axisLabel: { // 刻度标签。
        show: true, // 是否显示标签,默认 true。
        distance: 1, // 标签与刻度线的距离,默认 5。
        color: "#000", // 文字的颜色,默认 #fff。
        fontSize: 10, // 文字的字体大小,默认 5。
        formatter: "{value}", // 刻度标签的内容格式器，支持字符串模板和回调函数两种形式。 示例:// 使用字符串模板，模板变量为刻度默认标签 {value},如:formatter: '{value} kg'; // 使用函数模板，函数参数分别为刻度数值,如formatter: function (value) {return value + 'km/h';}
      },
      pointer: { // 仪表盘指针。
        show: true, // 是否显示指针,默认 true。
        length: "70%", // 指针长度，可以是绝对数值，也可以是相对于半径的百分比,默认 80%。
        width: 5, // 指针宽度,默认 8。
      },
      itemStyle: { // 仪表盘指针样式。
        color: "auto", // 指针颜色，默认(auto)取数值所在的区间的颜色
        opacity: 1, // 图形透明度。支持从 0 到 1 的数字，为 0 时不绘制该图形。
        borderWidth: 0, // 描边线宽,默认 0。为 0 时无描边。
        borderType: "solid", // 柱条的描边类型，默认为实线，支持 'solid', 'dashed', 'dotted'。
        borderColor: "#000", // 图形的描边颜色,默认 "#000"。支持的颜色格式同 color，不支持回调函数。
        //shadowBlur: 10, // (发光效果)图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
        //shadowColor: "#000", // 阴影颜色。支持的格式同color。
      },
      emphasis: { // 高亮的 仪表盘指针样式
        itemStyle: {
          //高亮 和正常  两者具有同样的配置项,只是在不同状态下配置项的值不同。
        }
      },
      title: { // 仪表盘标题。
        show: true, // 是否显示标题,默认 true。
        offsetCenter: [0,
          "20%"], //相对于仪表盘中心的偏移位置，数组第一项是水平方向的偏移，第二项是垂直方向的偏移。可以是绝对的数值，也可以是相对于仪表盘半径的百分比。
        color: "#000", // 文字的颜色,默认 #333。
        fontSize: 15, // 文字的字体大小,默认 15。
      },
      detail: { // 仪表盘详情，用于显示数据。
        show: true, // 是否显示详情,默认 true。
        offsetCenter: [0,
          "50%"], // 相对于仪表盘中心的偏移位置，数组第一项是水平方向的偏移，第二项是垂直方向的偏移。可以是绝对的数值，也可以是相对于仪表盘半径的百分比。
        color: "auto", // 文字的颜色,默认 auto。
        fontSize: 20, // 文字的字体大小,默认 15。
        formatter: "{value}", // 格式化函数或者字符串
      },

      data: data_change_moulding_time
    }]

  };


</script>


<style lang="scss" scoped>
  .dashboard-editor-container {
    padding: 32px;
    background-color: rgb(240, 242, 245);
    position: relative;

    .chart-wrapper {
      background: #fff;
      padding: 16px 16px 0;
      margin-bottom: 32px;
    }
  }

  @media (max-width: 1024px) {
    .chart-wrapper {
      padding: 8px;
    }
  }
</style>

