<template>
  <el-dialog
    title="合同详情"
    :visible.sync="visible"
    width="1000px"
    append-to-body
    @closed="onClosed"
  >
    <el-table
      :show-header="false"
      :data="contractDetailList"
      border
      :cell-style="columnStyle"
      style="width: 100%"
    >
      <el-table-column width="120" prop="attr_name_one"> </el-table-column>
      <el-table-column prop="value_one">
        <template slot-scope="scope">
          <auto-hide-message :data="scope.row.value_one == null ? '' : scope.row.value_one + ''" :maxLength="20"/>
        </template>
      </el-table-column>
      <el-table-column width="120" prop="attr_name_two"></el-table-column>
      <el-table-column prop="value_two">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.attr_name_two === '合同状态'" :type="scope.row.value_two === 'yes' ? 'success' : 'danger'">
            {{ scope.row.value_two === "yes" ? "已签订" : "未签订" }}
          </el-tag>
          <el-button v-else-if="scope.row.attr_name_two === '合同地址'" type="text" icon="el-icon-copy-document" @click="handleCopy(scope.row.value_two)" class="copyBtn"
                     :data-clipboard-text="copyValue">复制
          </el-button>
          <auto-hide-message v-else :data="scope.row.value_two == null ? '' : scope.row.value_two + ''" :maxLength="20"/>
        </template>
      </el-table-column>
      <el-table-column width="120" prop="attr_name_three"></el-table-column>
      <el-table-column prop="value_three">
        <template slot-scope="scope">

          <auto-hide-message :data="scope.row.value_three == null ? '' : scope.row.value_three + ''" :maxLength="20"/>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>
<script>
import AutoHideMessage from "@/components/AutoHideMessage";
import { getContract } from "@/api/custom/contract";
import Clipboard from 'clipboard';

export default {
  name: "ContractDialog",
  components: {
    "auto-hide-message": AutoHideMessage,
  },
  props: {},
  data() {
    return {
      contractDetailList: [],
      visible: false,
      copyValue: "",
      //合同详情的标题，按竖显示
      contractTitleData: [
        ["合同编号", "合同状态", "客户姓名"],
        ["证件号", "电话", "服务时间"],
        ["服务承诺", "金额", "调理项目"],
        ["签订时间", "合同地址", "备注"]
      ],
      //合同详情的属性名称，与标题对应，按竖显示
      contractValueData: [
        ["id", "status", "name"],
        ["cusId", "phone", "serveTime"],
        ["servePromise", "amount", "projectName"],
        ["updateTime", "path", "remark"]
      ],
    };
  },
  created() {},
  methods: {
    // 自定义列背景色
    columnStyle({ row, column, rowIndex, columnIndex }) {
      if (
        columnIndex == 0 ||
        columnIndex == 2 ||
        columnIndex == 4 ||
        columnIndex == 6
      ) {
        //第三第四列的背景色就改变了2和3都是列数的下标
        return "background:#f3f6fc;font-weight:bold";
      } else {
        return "background:#ffffff;";
      }
    },
    showDialog(id) {
      getContract(id).then((response) => {
         let contractDetail = response.data;
         contractDetail.serveTime = contractDetail.serveTime/30 + "个月";
         contractDetail.servePromise += contractDetail.servePromise ? "斤" : "";
         for (let i = 0; i < this.contractTitleData.length; i++) {
            this.contractDetailList.push({
            attr_name_one: this.contractTitleData[i][0],
            value_one: response.data[this.contractValueData[i][0]],
            attr_name_two: this.contractTitleData[i][1],
            value_two: response.data[this.contractValueData[i][1]],
            attr_name_three: this.contractTitleData[i][2],
            value_three: response.data[this.contractValueData[i][2]]
          });
         }
         this.visible = true;
      });
    },
    onClosed() {
      this.contractDetailList = [];
    },
    handleCopy(path) {
      this.copyValue = window.location.origin.replace('manage', 'sign') + path;
      const btnCopy = new Clipboard('.copyBtn');
      this.$message({
        message: '拷贝成功',
        type: 'success'
      });
      // btnCopy.destroy();
    }
  },
  watch: {
    // visible: function (val, oldVal) {
    //   console.log({val, oldVal})
    // }
  },
};
</script>
