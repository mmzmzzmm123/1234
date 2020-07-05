<template>
  <div>
    <h1>test001</h1>
    <div id="chart_change_moulding_time" style="width: 80%;height: 204px;left: 10%;"></div>



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
      this.getList();
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
