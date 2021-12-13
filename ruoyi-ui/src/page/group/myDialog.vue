<template>
  <div>
    <el-dialog title="测点信息"
               :visible.sync="dialogTableVisible"
               :modal="false">
      <el-image style="width:267px; height: 220px"
                :src="url"
                fit="fill">
      </el-image>
      <el-table :data="gridData"
                style="display: inline-block;width: calc(100% - 267px);">
        <el-table-column property="time"
                         label=时间></el-table-column>
        <el-table-column property="x"
                         label="X角度变化"></el-table-column>
        <el-table-column property="y"
                         label="Y角度变化"></el-table-column>
      </el-table>

      <!--    <div id="myChart" style="width:900px; height:500px" ></div>-->
      <div ref="chart"
           style="width:670px; height:372px"></div>
    </el-dialog>
  </div>
</template>

<script>
import { ref } from 'vue'
import editPanorama from "@/page/group/editPanorama";
import { globalBus } from "@/page/group/globalBus";
import { getMonitorData } from "@/api/monitor";
import { listVisualImage } from "@/api/system/visualImage";
export default {
  name: "myDialog",
  data () {
    return {
      dialogTableVisible: false,
      url: require("../../../public/icon/test.jpg"),
      gridData: [],
      //   [{
      //   time: '2021-11-14 11:00:00',
      //   x: '0.07',
      //   y: '0.69'
      // }, {
      //   time: '2021-11-14 13:00:00',
      //   x: '0.08',
      //   y: '0.70'
      // }, {
      //   time: '2021-11-14 15:00:00',
      //   x: '0.09',
      //   y: '0.69'
      // }, {
      //   time: '2021-11-14 17:00:00',
      //   x: '0.08',
      //   y: '0.68'
      // }],
      option: {
        // title: {
        //   text: 'Test'
        // },
        tooltip: {
          trigger: 'axis'
        },
        legend: {},
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: []
          // data: ['2021-11-14\n11:00:00', '2021-11-14\n13:00:00', '2021-11-14\n15:00:00', '2021-11-14\n17:00:00', '2021-11-14\n19:00:00', '2021-11-14\n21:00:00', '2021-11-14\n23:00:00']
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value}'
          }
        },
        series: [
          {
            name: 'Y角度变化',
            type: 'line',
            data: []
            // data: [10, 11, 13, 11, 12, 12, 9],
          },
          {
            name: 'X角度变化',
            type: 'line',
            data: []
            // data: [1, 3, 2, 5, 3, 2, 0],
          }
        ]
      },
      monitorRecord: []
      // [{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"16.2","root.ovmr.fehy.znna.temperature":"662.0","Time":"1638750600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"15.5","root.ovmr.fehy.znna.temperature":"655.0","Time":"1638748800000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23715.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.3","root.ovmr.fehy.znna.temperature":"653.0","Time":"1638747000000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26184.0","root.ovmr.fehy.znna.temperature_calc":"17.7","root.ovmr.fehy.znna.temperature":"677.0","Time":"1638705600000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23721.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"18.3","root.ovmr.fehy.znna.temperature":"683.0","Time":"1638703800000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23720.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"18.3","root.ovmr.fehy.znna.temperature":"683.0","Time":"1638702000000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23720.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26187.0","root.ovmr.fehy.znna.temperature_calc":"18.5","root.ovmr.fehy.znna.temperature":"685.0","Time":"1638700200000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23722.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26191.0","root.ovmr.fehy.znna.temperature_calc":"18.6","root.ovmr.fehy.znna.temperature":"686.0","Time":"1638698400000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23721.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"18.8","root.ovmr.fehy.znna.temperature":"688.0","Time":"1638696600000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23722.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"19.0","root.ovmr.fehy.znna.temperature":"690.0","Time":"1638694800000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23722.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"19.3","root.ovmr.fehy.znna.temperature":"693.0","Time":"1638691200000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23723.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"19.5","root.ovmr.fehy.znna.temperature":"695.0","Time":"1638689400000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23723.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26192.0","root.ovmr.fehy.znna.temperature_calc":"19.9","root.ovmr.fehy.znna.temperature":"699.0","Time":"1638687600000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23723.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26192.0","root.ovmr.fehy.znna.temperature_calc":"20.1","root.ovmr.fehy.znna.temperature":"701.0","Time":"1638685800000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23723.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"20.3","root.ovmr.fehy.znna.temperature":"703.0","Time":"1638684000000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23723.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"20.8","root.ovmr.fehy.znna.temperature":"708.0","Time":"1638682200000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23725.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"20.2","root.ovmr.fehy.znna.temperature":"702.0","Time":"1638680400000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23723.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"19.4","root.ovmr.fehy.znna.temperature":"694.0","Time":"1638678600000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23723.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"19.0","root.ovmr.fehy.znna.temperature":"690.0","Time":"1638676800000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23722.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26191.0","root.ovmr.fehy.znna.temperature_calc":"18.1","root.ovmr.fehy.znna.temperature":"681.0","Time":"1638675000000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23722.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26187.0","root.ovmr.fehy.znna.temperature_calc":"17.1","root.ovmr.fehy.znna.temperature":"671.0","Time":"1638673200000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23720.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"16.8","root.ovmr.fehy.znna.temperature":"668.0","Time":"1638671400000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23720.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.8","root.ovmr.fehy.znna.temperature":"658.0","Time":"1638669600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23719.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.9","root.ovmr.fehy.znna.temperature":"659.0","Time":"1638667800000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"15.0","root.ovmr.fehy.znna.temperature":"650.0","Time":"1638666000000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23717.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.2","root.ovmr.fehy.znna.temperature":"652.0","Time":"1638664200000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23715.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26186.0","root.ovmr.fehy.znna.temperature_calc":"14.7","root.ovmr.fehy.znna.temperature":"647.0","Time":"1638662400000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23717.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"14.5","root.ovmr.fehy.znna.temperature":"645.0","Time":"1638660600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23714.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"14.5","root.ovmr.fehy.znna.temperature":"645.0","Time":"1638658800000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23715.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"13.9","root.ovmr.fehy.znna.temperature":"639.0","Time":"1638657000000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23715.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"14.1","root.ovmr.fehy.znna.temperature":"641.0","Time":"1638655200000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26194.0","root.ovmr.fehy.znna.temperature_calc":"14.1","root.ovmr.fehy.znna.temperature":"641.0","Time":"1638653400000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23713.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"14.3","root.ovmr.fehy.znna.temperature":"643.0","Time":"1638651600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23714.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"14.5","root.ovmr.fehy.znna.temperature":"645.0","Time":"1638649800000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23713.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"14.4","root.ovmr.fehy.znna.temperature":"644.0","Time":"1638648000000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23714.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"14.4","root.ovmr.fehy.znna.temperature":"644.0","Time":"1638646200000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23712.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"14.9","root.ovmr.fehy.znna.temperature":"649.0","Time":"1638644400000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.2","root.ovmr.fehy.znna.temperature":"652.0","Time":"1638642600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23714.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26187.0","root.ovmr.fehy.znna.temperature_calc":"15.0","root.ovmr.fehy.znna.temperature":"650.0","Time":"1638640800000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23717.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.2","root.ovmr.fehy.znna.temperature":"652.0","Time":"1638639000000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23715.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26193.0","root.ovmr.fehy.znna.temperature_calc":"15.3","root.ovmr.fehy.znna.temperature":"653.0","Time":"1638637200000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23718.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.2","root.ovmr.fehy.znna.temperature":"652.0","Time":"1638635400000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"14.9","root.ovmr.fehy.znna.temperature":"649.0","Time":"1638633600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23715.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26191.0","root.ovmr.fehy.znna.temperature_calc":"14.8","root.ovmr.fehy.znna.temperature":"648.0","Time":"1638631800000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23714.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.4","root.ovmr.fehy.znna.temperature":"654.0","Time":"1638630000000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.6","root.ovmr.fehy.znna.temperature":"656.0","Time":"1638628200000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23715.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.8","root.ovmr.fehy.znna.temperature":"658.0","Time":"1638626400000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.9","root.ovmr.fehy.znna.temperature":"659.0","Time":"1638624600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.9","root.ovmr.fehy.znna.temperature":"659.0","Time":"1638622800000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23719.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.9","root.ovmr.fehy.znna.temperature":"659.0","Time":"1638621000000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26184.0","root.ovmr.fehy.znna.temperature_calc":"16.2","root.ovmr.fehy.znna.temperature":"662.0","Time":"1638619200000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23717.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"16.4","root.ovmr.fehy.znna.temperature":"664.0","Time":"1638617400000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23717.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"16.4","root.ovmr.fehy.znna.temperature":"664.0","Time":"1638615600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"16.5","root.ovmr.fehy.znna.temperature":"665.0","Time":"1638613800000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23720.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"16.6","root.ovmr.fehy.znna.temperature":"666.0","Time":"1638612000000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"16.7","root.ovmr.fehy.znna.temperature":"667.0","Time":"1638610200000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23718.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"17.1","root.ovmr.fehy.znna.temperature":"671.0","Time":"1638608400000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23720.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"17.3","root.ovmr.fehy.znna.temperature":"673.0","Time":"1638606600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23718.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26191.0","root.ovmr.fehy.znna.temperature_calc":"17.6","root.ovmr.fehy.znna.temperature":"676.0","Time":"1638604800000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23721.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26191.0","root.ovmr.fehy.znna.temperature_calc":"18.1","root.ovmr.fehy.znna.temperature":"681.0","Time":"1638603000000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23718.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"18.3","root.ovmr.fehy.znna.temperature":"683.0","Time":"1638601200000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23722.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"18.7","root.ovmr.fehy.znna.temperature":"687.0","Time":"1638599400000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23722.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26192.0","root.ovmr.fehy.znna.temperature_calc":"18.9","root.ovmr.fehy.znna.temperature":"689.0","Time":"1638597600000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23722.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"18.9","root.ovmr.fehy.znna.temperature":"689.0","Time":"1638595800000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23722.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"19.1","root.ovmr.fehy.znna.temperature":"691.0","Time":"1638594000000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23722.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26186.0","root.ovmr.fehy.znna.temperature_calc":"18.6","root.ovmr.fehy.znna.temperature":"686.0","Time":"1638592200000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23721.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"18.1","root.ovmr.fehy.znna.temperature":"681.0","Time":"1638590400000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23722.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26192.0","root.ovmr.fehy.znna.temperature_calc":"17.8","root.ovmr.fehy.znna.temperature":"678.0","Time":"1638588600000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23721.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"17.2","root.ovmr.fehy.znna.temperature":"672.0","Time":"1638586800000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23720.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"16.8","root.ovmr.fehy.znna.temperature":"668.0","Time":"1638585000000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23719.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"16.4","root.ovmr.fehy.znna.temperature":"664.0","Time":"1638583200000","root.ovmr.fehy.znna.Xangle_calc":"0.26","root.ovmr.fehy.znna.Xangle":"23721.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.2","root.ovmr.fehy.znna.temperature":"652.0","Time":"1638581400000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"14.1","root.ovmr.fehy.znna.temperature":"641.0","Time":"1638579600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23718.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26191.0","root.ovmr.fehy.znna.temperature_calc":"13.6","root.ovmr.fehy.znna.temperature":"636.0","Time":"1638577800000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23714.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26193.0","root.ovmr.fehy.znna.temperature_calc":"13.2","root.ovmr.fehy.znna.temperature":"632.0","Time":"1638576000000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23712.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"12.2","root.ovmr.fehy.znna.temperature":"622.0","Time":"1638572400000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23710.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26191.0","root.ovmr.fehy.znna.temperature_calc":"12.3","root.ovmr.fehy.znna.temperature":"623.0","Time":"1638570600000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23710.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26187.0","root.ovmr.fehy.znna.temperature_calc":"12.5","root.ovmr.fehy.znna.temperature":"625.0","Time":"1638568800000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23710.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26187.0","root.ovmr.fehy.znna.temperature_calc":"12.8","root.ovmr.fehy.znna.temperature":"628.0","Time":"1638567000000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23711.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26187.0","root.ovmr.fehy.znna.temperature_calc":"13.0","root.ovmr.fehy.znna.temperature":"630.0","Time":"1638565200000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23710.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"13.1","root.ovmr.fehy.znna.temperature":"631.0","Time":"1638563400000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23712.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26190.0","root.ovmr.fehy.znna.temperature_calc":"12.9","root.ovmr.fehy.znna.temperature":"629.0","Time":"1638561600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23713.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"12.9","root.ovmr.fehy.znna.temperature":"629.0","Time":"1638559800000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23711.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26191.0","root.ovmr.fehy.znna.temperature_calc":"13.0","root.ovmr.fehy.znna.temperature":"630.0","Time":"1638558000000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23712.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"13.2","root.ovmr.fehy.znna.temperature":"632.0","Time":"1638556200000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23712.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"13.2","root.ovmr.fehy.znna.temperature":"632.0","Time":"1638554400000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23711.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"13.5","root.ovmr.fehy.znna.temperature":"635.0","Time":"1638552600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23714.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"13.5","root.ovmr.fehy.znna.temperature":"635.0","Time":"1638550800000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23712.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"14.1","root.ovmr.fehy.znna.temperature":"641.0","Time":"1638549000000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23712.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26184.0","root.ovmr.fehy.znna.temperature_calc":"14.1","root.ovmr.fehy.znna.temperature":"641.0","Time":"1638547200000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23712.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"14.4","root.ovmr.fehy.znna.temperature":"644.0","Time":"1638545400000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23712.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"14.8","root.ovmr.fehy.znna.temperature":"648.0","Time":"1638543600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23713.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.2","root.ovmr.fehy.znna.temperature":"652.0","Time":"1638541800000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23715.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26193.0","root.ovmr.fehy.znna.temperature_calc":"15.4","root.ovmr.fehy.znna.temperature":"654.0","Time":"1638540000000","root.ovmr.fehy.znna.Xangle_calc":"0.24","root.ovmr.fehy.znna.Xangle":"23712.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26188.0","root.ovmr.fehy.znna.temperature_calc":"14.9","root.ovmr.fehy.znna.temperature":"649.0","Time":"1638538200000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23713.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26187.0","root.ovmr.fehy.znna.temperature_calc":"15.4","root.ovmr.fehy.znna.temperature":"654.0","Time":"1638536400000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23718.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.9","root.ovmr.fehy.znna.temperature":"659.0","Time":"1638534600000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23715.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.2","root.ovmr.fehy.znna.temperature":"652.0","Time":"1638532800000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23715.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.17","root.ovmr.fehy.znna.Yangle":"26189.0","root.ovmr.fehy.znna.temperature_calc":"15.9","root.ovmr.fehy.znna.temperature":"659.0","Time":"1638531000000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23717.0"},{"root.ovmr.fehy.znna.Yangle_calc":"0.16","root.ovmr.fehy.znna.Yangle":"26191.0","root.ovmr.fehy.znna.temperature_calc":"16.6","root.ovmr.fehy.znna.temperature":"666.0","Time":"1638529200000","root.ovmr.fehy.znna.Xangle_calc":"0.25","root.ovmr.fehy.znna.Xangle":"23716.0"}]
      ,
    };
  },
  // setup(){
  //   const showDialog = () =>{
  //     this.dialogTableVisible=true;
  //   }
  //
  //   return{
  //     showDialog,
  //   }
  // },
  mounted () {
    const _this = this;
    globalBus.$on("show", (url) => {
      getMonitorData(url).then(response => {
        console.log(response);
        this.monitorRecord = response.result.records;
        console.log(this.monitorRecord);
        // console.log(response.rows[0]);
        // console.log(this.pending)
        //赋值前先把所有都清空
        this.gridData = [];
        this.option.xAxis.data = [];
        this.option.series[1].data = [];
        this.option.series[0].data = [];
        for (let i = 0; i < 4; i++) {
          this.gridData[i] = {};
          // console.log(this.monitorRecord[i]);
          // const array=Object.values(this.monitorRecord[i]);
          // Date.prototype.toLocaleString = function() {
          //   return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate()  +" "+ this.getHours() + ":" + this.getMinutes();
          // };
          this.gridData[i]["time"] = new Date(parseInt(this.monitorRecord[i].Time)).toLocaleString().replace(/:\d{1,2}$/, ' ');
          // this.gridData[i]["time"]=this.monitorRecord[i].Time;
          for (let item in this.monitorRecord[i]) {
            // console.log(item);
            if (item.indexOf("Xangle_calc") != -1) { this.gridData[i]["x"] = this.monitorRecord[i][item] }
            if (item.indexOf("Yangle_calc") != -1) { this.gridData[i]["y"] = this.monitorRecord[i][item] }
          }

          // this.gridData[i]["x"]=this.monitorRecord[i]["root.ovmr.fehy.znna.Xangle_calc"];
          // this.gridData[i]["y"]=this.monitorRecord[i]["root.ovmr.fehy.znna.Yangle_calc"];
        }
        for (let i = 0; i < 10; i++) {
          // Date.prototype.toLocaleString = function() {
          //   return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate()  +" "+ this.getHours() + ":" + this.getMinutes();
          // };
          this.option.xAxis.data[i] = new Date(parseInt(this.monitorRecord[i].Time)).toLocaleString().replace(/:\d{1,2}$/, ' ');
          // this.gridData[i]["time"]=this.monitorRecord[i].Time;
          for (let item in this.monitorRecord[i]) {
            // console.log(item);
            if (item.indexOf("Xangle_calc") != -1) { this.option.series[1].data[i] = this.monitorRecord[i][item] }
            if (item.indexOf("Yangle_calc") != -1) { this.option.series[0].data[i] = this.monitorRecord[i][item] }
          }

          // this.gridData[i]["x"]=this.monitorRecord[i]["root.ovmr.fehy.znna.Xangle_calc"];
          // this.gridData[i]["y"]=this.monitorRecord[i]["root.ovmr.fehy.znna.Yangle_calc"];
        }
        // console.log("------------------");
        // console.log(this.gridData);
        this.dialogTableVisible = true;
        //nexttick在dom更新完之后再调用其函数
        this.$nextTick(() => {
          const chart = this.$refs.chart;
          console.log("chart");
          console.log(chart);
          if (chart) {
            const myChart = this.$echarts.init(chart)
            myChart.setOption(this.option)
            window.addEventListener("resize", function () {
              myChart.resize()
            })
          }
          this.$on('hook:destroyed', () => {
            window.removeEventListener("resize", function () {
              myChart.resize();
            });
          })
        }
        )
      })
    });

  },
  method: {
    getLocalTime (nS) {
      return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/, ' ');
    },
    closeDialog () {
      this.dialogTableVisible = false;
    },
    getEchartData () {
      // console.log(document.getElementById('myChart'));
      let myChart = this.$echarts.init(document.getElementById('myChart'));
      myChart.setOption(
        {
          // title: {
          //   text: 'Test'
          // },
          tooltip: {
            trigger: 'axis'
          },
          legend: {},
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: ['2021-11-14\n11:00:00', '2021-11-14\n13:00:00', '2021-11-14\n15:00:00', '2021-11-14\n17:00:00', '2021-11-14\n19:00:00', '2021-11-14\n21:00:00', '2021-11-14\n23:00:00']
          },
          yAxis: {
            type: 'value',
            axisLabel: {
              formatter: '{value}'
            }
          },
          series: [
            {
              name: 'Y角度变化',
              type: 'line',
              data: [10, 11, 13, 11, 12, 12, 9],
            },
            {
              name: 'X角度变化',
              type: 'line',
              data: [1, 3, 2, 5, 3, 2, 0],
            }
          ]
        });
    },
    showDialog () {
      this.dialogTableVisible = true;
      console.log(this.$parent);
    },
    test () {
      alert(123);
    }
  }
}
</script>

<style scoped>
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
