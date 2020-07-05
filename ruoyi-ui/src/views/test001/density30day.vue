<template>
  <div>
    <h1>test001</h1>

    <div id="chart_change_moulding_time" style="width: 80%;height: 600px;left: 10%;"></div>



  </div>

</template>

<script>
  import request from '@/utils/request'
  import echarts from 'echarts'
  require('echarts/theme/macarons') // echarts theme
  import resize from "../dashboard/mixins/resize";

  export default {
    name: "index",
    created() {
    },
    methods: {
      /** 查询服务器信息 */
      getList() {
        getServer().then(response => {
          console.log(response);
          console.log(response.data);
          console.log(response.data[0]);
          console.log(JSON.parse(response.data[0]));
        });
      },

      drawchart() {
        const chart_change_moulding_time = echarts.init(document.getElementById("chart_change_moulding_time"));
        const option_change_moulding_time = {
          tooltip: {
            formatter: '{a} <br/>{b} : {c}%'
           },
          toolbox: {
            feature: {
              restore: {},
              saveAsImage: {}
            }
          },
          series: [
            {
              name: '业务指标',
              type: 'gauge',
              detail: {formatter: '{value}%'},
              data: [{value: 50, name: '完成率'}]
            }
          ]
        };
        chart_change_moulding_time.setOption(option_change_moulding_time, true);

        setInterval(function () {
          option_change_moulding_time.series[0].data[0].value = (Math.random() * 100).toFixed(2) - 0;
          chart_change_moulding_time.setOption(option_change_moulding_time, true);
        },2000);



      }

    },
    mounted() {

      this.drawchart();

    }
  }

  export function getServer() {
    return request({
      url: '/monitor/server',
      method: 'get'
    })
  }


</script>

<style scoped>

</style>
