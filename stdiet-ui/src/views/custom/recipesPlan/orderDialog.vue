<template>
  <el-dialog title="订单详情" :visible.sync="visible" width="1000px" append-to-body @closed="onClosed">
    <el-table :show-header="false" :data="orderDetailList" border :cell-style="columnStyle" style="width: 100%;">
      <el-table-column width="120" prop="attr_name_one">
      </el-table-column>
      <el-table-column prop="value_one">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.attr_name_one === '订单状态'"
                  :type="scope.row.value_two === '2' ? 'success' : scope.row.value_one === '0'? '': 'danger'"
                  disable-transitions>
            {{scope.row.value_two === '2' ? '已完成': scope.row.value_one ==='0'? '进行中': '已暂停'}}
          </el-tag>
          <auto-hide-message v-else :data="scope.row.value_one == null ? '' : (scope.row.value_one+'')"
                             :maxLength="20"/>
        </template>
      </el-table-column>
      <el-table-column width="120" prop="attr_name_two"></el-table-column>
      <el-table-column prop="value_two">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.attr_name_two === '审核状态'"
                  :type="scope.row.value_two === 'yes' ? 'success' : 'danger'">
            {{scope.row.value_two === 'yes' ? '已审核' : '未审核'}}
          </el-tag>
          <auto-hide-message v-else :data="scope.row.value_two == null ? '' : (scope.row.value_two+'')"
                             :maxLength="20"/>
        </template>
      </el-table-column>
      <el-table-column width="120" prop="attr_name_three"></el-table-column>
      <el-table-column prop="value_three">
        <template slot-scope="scope">
          <auto-hide-message :data="scope.row.value_three == null ? '' : (scope.row.value_three+'')" :maxLength="20"/>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>
<script>
  import AutoHideMessage from "@/components/AutoHideMessage";
  import {getInfoDetail} from "@/api/custom/order";

  export default {
    name: 'OrderDialog',
    components: {
      "auto-hide-message": AutoHideMessage
    },
    props: {},
    data() {
      return {
        orderDetailList: [],
        visible: false,
        //订单详情的标题，按竖显示
        orderTitleData: [["订单状态", "审核状态", "成交时间"],
          ["姓名", "金额", "体重"],
          ["手机号", "服务时长", "赠送时长"],
          ["调理项目", "收款方式", "售前"],
          ["售后", "营养师", "助理营养师"],
          ["账号", "策划", "策划助理"],
          ["运营", "运营助理", "进粉时间"],
          ["开始时间", "结束时间", "备注"]],
        //订单详情的属性名称，与标题对应，按竖显示
        orderValueData: [["status", "reviewStatus", "orderTime"],
          ["customer", "amount", "weight"],
          ["phone", "serveTime", "giveServeDay"],
          ["conditioningProject", "payType", "preSale"],
          ["afterSale", "nutritionist", "nutriAssis"],
          ["account", "planner", "plannerAssis"],
          ["operator", "operatorAssis", "becomeFanTime"],
          ["startTime", "serverEndTime", "serverEndTime"]],
      }
    },
    created() {
    },
    methods: {
      // 自定义列背景色
      columnStyle({row, column, rowIndex, columnIndex}) {
        if (columnIndex == 0 || columnIndex == 2 || columnIndex == 4 || columnIndex == 6) {
          //第三第四列的背景色就改变了2和3都是列数的下标
          return "background:#f3f6fc;font-weight:bold";
        } else {
          return "background:#ffffff;";
        }
      },
      showDialog(orderId) {
        getInfoDetail({orderId}).then(response => {
          response.data.weight = response.data.weight != null ? (response.data.weight + "斤") : "";
          response.data.giveServeDay = response.data.giveServeDay != null ? (response.data.giveServeDay + "天") : "";
          for (let i = 0; i < this.orderTitleData.length; i++) {
            this.orderDetailList.push({
              "attr_name_one": this.orderTitleData[i][0],
              "value_one": response.data[this.orderValueData[i][0]],
              "attr_name_two": this.orderTitleData[i][1],
              "value_two": response.data[this.orderValueData[i][1]],
              "attr_name_three": this.orderTitleData[i][2],
              "value_three": response.data[this.orderValueData[i][2]]
            });
          }
          this.visible = true;
        });
      },
      onClosed() {
        this.orderDetailList = [];
      }
    },
    watch: {
      // visible: function (val, oldVal) {
      //   console.log({val, oldVal})
      // }
    }
  }
</script>
