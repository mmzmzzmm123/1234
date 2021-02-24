<template>
  <div>
    <el-drawer
      :title="title"
      :close-on-press-escape="false"
      :visible.sync="visible"
      @closed="handleOnClosed"
      size="40%"
    >
      <div class="app-container">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button icon="el-icon-share" size="small" title="点击复制链接" class="copyBtn" type="primary" :data-clipboard-text="copyValue" @click="handleCopy()">外食计算器</el-button>
          </el-col>
        </el-row>
        <el-table :data="foodHeatStatisticsList" >
          <el-table-column label="日期" align="center" prop="edibleDate" width="120">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.edibleDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
         <!-- <el-table-column label="食材" align="center" prop="ingredient" />
          <el-table-column label="通俗计量" align="center" prop="unitName">
            <template slot-scope="scope">
              {{ scope.row.number ? (scope.row.number + "" + (scope.row.unitName != null ? scope.row.unitName : "")) : "" }}
            </template>
          </el-table-column>
          <el-table-column label="质量(克)" align="center" prop="quantity" />-->

          <el-table-column label="可摄入量" align="center" prop="maxHeatValue" />
          <el-table-column label="食材热量" align="center" prop="heatValue" />
          <el-table-column label="热量缺口" align="center" prop="heatGap" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                @click="handleDetail(scope.row)"
                v-hasPermi="['custom:foodHeatStatistics:query']"
              >详情</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleCalculate(scope.row)"
              >计算</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleOnDeleteClick(scope.row)"
                v-hasPermi="['custom:foodHeatStatistics:remove']"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="fetchHeatList"
        />

        <heatStatisticsCalculate ref="heatStatisticsCalculateRef"></heatStatisticsCalculate>
        <heatStatisticsDetail ref="heatStatisticsDetailRef"></heatStatisticsDetail>

      </div>
    </el-drawer>
  </div>
</template>
<script>
  import { listFoodHeatStatistics, getFoodHeatStatistics, delFoodHeatStatistics, addFoodHeatStatistics, updateFoodHeatStatistics, exportFoodHeatStatistics } from "@/api/custom/foodHeatStatistics";
  import Clipboard from 'clipboard';
  import HeatStatisticsCalculate from "@/components/HeatStatisticsCalculate";
  import HeatStatisticsDetail from "@/components/HeatStatisticsDetail";
export default {
  name: "HeatStatisticsDrawer",
  components: {
    'heatStatisticsCalculate':HeatStatisticsCalculate,
    'heatStatisticsDetail': HeatStatisticsDetail
  },
  data() {
    return {
      visible: false,
      title: "",
      data: undefined,
      foodHeatStatisticsList: [],
      total: 0,
      // 查询参数
      queryParams: {
        customerId: null,
        pageNum: 1,
        pageSize: 10,
      },
      copyValue: ""
    };
  },
  methods: {
    showDrawer(data) {
      // console.log(data);
      this.data = data;
      if (!this.data) {
        return;
      }
      this.title = `「${this.data.name}」热量统计列表`;
      this.queryParams.customerId = data.id;
      this.fetchHeatList();
    },
    fetchHeatList() {
      listFoodHeatStatistics(this.queryParams).then(response => {
        this.foodHeatStatisticsList = response.rows;
        this.total = response.total;
        this.visible = true;
      });
    },
    handleAdd() {

    },
    handleOnClosed() {
      this.data = undefined;
      this.copyValue = "";
    },
    handleOnDeleteClick(data) {
      const ids = data.id || this.ids;
      this.$confirm(
        '是否确认删除该数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delFoodHeatStatistics(ids);
        })
        .then(() => {
          this.fetchHeatList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    handleCopy() {
      this.copyValue = window.location.origin.replace('manage', 'sign') + "/foodHeatCalculator/"+this.data.encId;
      const btnCopy = new Clipboard('.copyBtn');
      this.$message({
        message: '拷贝成功',
        type: 'success'
      });
    },
    handleCalculate(data){
      this.$refs.heatStatisticsCalculateRef.showDialog(data,() => {
        this.fetchHeatList();
      });
    },
    handleDetail(data){
        this.$refs.heatStatisticsDetailRef.showDialog(data, this.data.name);
    }
  },
};
</script>
<style  lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}
</style>
