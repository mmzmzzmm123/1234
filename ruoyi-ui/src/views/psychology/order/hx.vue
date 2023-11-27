<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="90px">
      <el-form-item label="订单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属咨询师" prop="consultId">
        <el-select v-model="queryParams.consultId" clearable :disabled="consultList.length === 1">
          <el-option
            v-for="item in consultList"
            :key="item.id"
            :label="item.nickName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="核销时间" prop="times">
        <el-date-picker
          v-model="queryParams.times"
          style="width: 280px;"
          size="small"
          value-format="yyyy-MM-dd"
          format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['psychology:order:hxExport']"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <span style="color: #C5291B;margin: 0 10px">*每小时更新核销单</span>
      </el-col>
    </el-row>

    <el-table v-loading="loading" size="mini" style="width: 100%" :data="bills">
      <el-table-column label="核销时间" width="160" align="center" prop="useTime" />
      <el-table-column label="所属咨询师" width="100" align="center" prop="consultName" />
      <el-table-column label="订单号" width="260" align="center" prop="orderNo" />
      <el-table-column label="下单时间" width="160" align="center" prop="orderTime" />
      <el-table-column label="订单状态" width="100" align="center" prop="status" />
      <el-table-column label="订单结算状态" width="100" align="center" prop="billStatus"/>
      <el-table-column label="服务名称" width="180" align="center" prop="serveName" />
      <el-table-column label="服务类型" width="100" align="center" prop="serveType" />
      <el-table-column label="咨询次数" align="center" prop="buyNumStr"/>
      <el-table-column label="服务总次数" align="center" prop="orderNum" />
      <el-table-column label="剩余次数" align="center" prop="num" />
      <el-table-column label="用户昵称" align="center" prop="nickName" />
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { getConsultAll } from "@/api/psychology/consult";
import { getItemList } from "@/api/psychology/bill";

export default {
  name: "OrderHx",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 咨询师合同协议表格数据
      bills: [],
      // 咨询师列表
      timeVal: [],
      consultList: [],
      consultId: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        consultId: null,
        orderNo: null,
        startTime: null,
        endTime: null,
        times: []
      },
    };
  },
  async created() {
    await this.getConsults();
    this.getList();
  },
  methods: {
    async getConsults() {
      const res = await getConsultAll()
      this.consultList = res.data
      if (this.consultList.length === 1) {
        this.consultId = this.consultList[0].id
        this.queryParams.consultId = this.consultId
      }
    },
    /** 查询咨询师合同协议列表 */
    getList() {
      this.loading = true;
      if (this.queryParams.times.length > 0) {
        this.queryParams.startTime = this.queryParams.times[0] + ' 00:00:00'
        this.queryParams.endTime = this.queryParams.times[1] + ' 23:59:59'
      } else {
        this.queryParams.startTime = null
        this.queryParams.endTime = null
      }

      if (this.consultId) {
        this.queryParams.consultId = this.consultId
      }

      getItemList(this.queryParams).then(response => {
        this.bills = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.timeVal = []
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 导出按钮操作 */
    handleExport() {
      if (this.consultId) {
        this.queryParams.consultId = this.consultId
      }

      this.download('psychology/bill/exportItems', {
        ...this.queryParams
      }, `核销记录_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>

<style scoped>

</style>
