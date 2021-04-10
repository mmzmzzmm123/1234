<template>
  <el-dialog
    :visible.sync="visible"
    :title="title"
    append-to-body
    @closed="onClosed"
    width="1000px"
  >
    <div id="container">
        <LineCharts v-if="visible" :chartOption="punchLogChartOption"></LineCharts>
    </div>  
  </el-dialog>
</template>
<script>
import {
  getAllPunchLogByCustomerId
} from "@/api/custom/wxUserLog";
import LineCharts from "@/components/Highcharts/LineCharts";

export default {
  name: "PunchLogChart",
  components: {
      LineCharts
  },
  data() {
    return {
      visible: false,
      title: "",
      data: null,
      punchLogChartOption:{
        imageFileName: "体重趋势图",
        yAxisTitle: "体重(斤)",
        seriesName: "体重趋势(斤)",
        xAxisData: [],
        seriesData: []
      },
      punchLogList:[],
      queryParams:{
         customerId: null
      }
    };
  },
  methods: {
    showDialog(data) {
      this.data = data;
      this.title = `「${data.name}」体重趋势图`;
      this.queryParams.customerId = data.id;
      this.punchLogChartOption.imageFileName = data.name + "体重趋势图"
      this.getAllPunchLogByCustomerId();
     
    },
    getAllPunchLogByCustomerId() {
        getAllPunchLogByCustomerId(this.queryParams).then((res) => {
          if (res.code == 200) {
            this.punchLogList = res.data;
            this.dealPunchLogList(res.data);
            this.visible = true;
          }
        });
    },
    dealPunchLogList(data){
        let xAxisData = [];
        let seriesData = [];
        if(data && data.length > 0){
            data.forEach((element,index) => {
              xAxisData.push(element.logTime);
              seriesData.push(element.weight);
              if(index == data.length-1){
                 this.punchLogChartOption.xAxisData = xAxisData;
                 this.punchLogChartOption.seriesData = seriesData;
              }
            });
        }else{
            this.punchLogChartOption.xAxisData = xAxisData;
            this.punchLogChartOption.seriesData = seriesData;
        }
    },
    onClosed() {
      this.data = null;
      this.punchLog = null;
      this.punchLogList = [];
    }
  },
};
</script>

<style lang="scss" scoped>

</style>
