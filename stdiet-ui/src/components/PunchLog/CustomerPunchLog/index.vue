<template>
  <el-drawer
    :title="title"
    :close-on-press-escape="false"
    :visible.sync="visible"
    @closed="handleOnClosed"
    size="45%"
  >
    <div class="app-container punchLog_drawer_wrapper">
      <div class="header">
        <section>
          <el-button icon="el-icon-view" size="mini" @click="showPunchLogChart()"
            >体重趋势图
          </el-button>
        </section>
        <section>
          <el-button
            icon="el-icon-refresh"
            size="mini"
            @click="getList"
            circle
          />
        </section>
      </div>

      <el-table :data="punchLogList" v-loading="planLoading" height="80%">
        <el-table-column label="打卡日期" align="center"  prop="logTime"/>
        <el-table-column label="体重(斤)" align="center"  prop="weight"/>
        <el-table-column label="饮水量(ml)" align="center"  prop="water"/>
        <el-table-column label="营养师" align="center" prop="nutritionist" />
        <el-table-column label="售后" align="center" prop="afterNutritionist" />
        <el-table-column label="操作" align="center" width="160">
          <template slot-scope="scope">
            <el-button
            size="mini"
            type="text"
            @click="showPunchLogDetail(scope.row)"
            v-hasPermi="['custom:wxUserLog:query']"
            >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:wxUserLog:edit']"
            >修改
          </el-button>
            <el-button
              type="text"
              icon="el-icon-delete"
              v-hasPermi="['custom:wxUserLog:remove']"
              @click="handleOnDelete(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
    </div>
    <!-- 详情 -->
    <PunchLogDetail ref="punchLogDetailRef"></PunchLogDetail>
    <!-- 编辑 -->
    <PunchLogEdit ref="punchLogEditRef"></PunchLogEdit>
    <!-- 体重趋势图 -->
    <PunchLogChart ref="punchLogChartRef"></PunchLogChart>
  </el-drawer>
</template>
<script>
import {
  addWxUserLog,
  delWxUserLog,
  exportWxUserLog,
  getWxUserLog,
  listWxUserLog,
  updateWxUserLog,
} from "@/api/custom/wxUserLog";
import PunchLogDetail from "@/components/PunchLog/PunchLogDetail";
import PunchLogEdit from "@/components/PunchLog/PunchLogEdit";
import PunchLogChart from "@/components/PunchLog/PunchLogChart";
import dayjs from "dayjs";
export default {
  name: "CustomerPunchLog",
  components: {
    PunchLogDetail,PunchLogEdit,PunchLogChart
  },
  data() {
    return {
      open: false,
      visible: false,
      title: "",
      planLoading: false,
      data: null,
      punchLogList: [],
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customerId: null
      },
    };
  },
  methods: {
    showDrawer(data) {
      // console.log(data);
      this.data = data;
      if (this.data == undefined || this.data == null) {
        return;     
      }
      this.punchLogList = [];
      this.total = 0;
      this.visible = true;
      this.title = `「${this.data.name}」打卡记录`;
      this.getList();
    },
    getList() {
      this.planLoading = true;
      this.queryParams.customerId = this.data.id;
      listWxUserLog(this.queryParams).then((response) => {
        if(response.code == 200){
          this.punchLogList = response.rows;
          this.total = response.total;
        }
        this.planLoading = false;
      });
    },
    reset() {
      
    },
    handleOnClosed(){
      this.data = null
    },
    showPunchLogDetail(data){
        this.$refs.punchLogDetailRef.showDialog(data);
    },
    showPunchLogChart(){
        this.$refs.punchLogChartRef.showDialog(this.data);
    },
    handleUpdate(data){
        this.$refs.punchLogEditRef.showDialog(data, () => {
          this.getList();
        });
    },
    /** 删除按钮操作 */
    handleOnDelete(row) {
      const ids = row.id;
      this.$confirm(
        '是否确定删除该用户' + row.logTime + '的打卡记录?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delWxUserLog(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
  },
};
</script>
<style lang="scss" scoped>
/deep/ :focus {
  outline: 0;
}

.punchLog_drawer_wrapper {
  height: calc(100vh - 77px);

  .header {
    margin-bottom: 8px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
}
</style>
