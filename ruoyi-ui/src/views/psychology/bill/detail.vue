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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['psychology:consult:export']"
        >导出</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" size="mini" style="width: 100%" :data="bills">
      <el-table-column label="账单日期" width="100" align="center" prop="billTime" />
      <el-table-column label="核销时间" width="160" align="center" prop="useTime" />
      <el-table-column label="所属咨询师" width="100" align="center" prop="consultName" />
      <el-table-column label="签约类型" width="120" align="center" prop="type" />
      <el-table-column label="结算比例" width="100" align="center" prop="ratio">
        <template slot-scope="scope">
          <span>{{ scope.row.ratio.toFixed('2') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单号" width="160" align="center" prop="orderNo" />
      <el-table-column label="下单时间" width="160" align="center" prop="orderTime" />
      <el-table-column label="订单状态" width="80" align="center" prop="status" />
      <el-table-column label="订单结算状态" width="100" align="center" prop="billStatus"/>
      <el-table-column label="服务名称" width="120" align="center" prop="serveName" />
      <el-table-column label="服务类型" width="100" align="center" prop="serveType" />
      <el-table-column label="咨询次数" align="center" prop="buyNumStr"/>
      <el-table-column label="服务总次数" width="100" align="center" prop="orderNum" />
      <el-table-column label="剩余次数" width="80" align="center" prop="num" />
      <el-table-column label="用户昵称" align="center" prop="nickName" />
      <el-table-column label="分期结算款项(元）" align="center" prop="price" >
        <template slot-scope="scope">
          <span>{{ scope.row.price && scope.row.price.toFixed('2') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="分期结算佣金(元）" align="center" prop="brokerage" >
        <template slot-scope="scope">
          <span>{{ scope.row.brokerage && scope.row.brokerage.toFixed('2') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单实付金额(元）" align="center" prop="orderTotal" >
        <template slot-scope="scope">
          <span>{{ scope.row.orderTotal && scope.row.orderTotal.toFixed('2') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="已结算金额(元）" align="center" prop="total" >
        <template slot-scope="scope">
          <span>{{ scope.row.total && scope.row.total.toFixed('2') }}</span>
        </template>
      </el-table-column>
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
import { getItemListForDetail } from "@/api/psychology/bill";

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
      billId: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        billId: null,
        consultId: null,
        orderNo: null,
      },
    };
  },
  async created() {
    await this.getConsults();
    if (this.$route.query && this.$route.query.id) {
      this.billId = this.$route.query.id
      this.getList();
    }
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
      this.queryParams.billId = this.billId
      if (this.consultId) {
        this.queryParams.consultId = this.consultId
      }

      getItemListForDetail(this.queryParams).then(response => {
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

      const row = this.bills[0]
      const title = row ? `心理咨询${row.billTime}账单_${new Date().getTime()}.xlsx` : `心理咨询账单_${new Date().getTime()}.xlsx`

      this.download('psychology/bill/exportItemsForDetail', {
        ...this.queryParams
      }, title)
    }
  }
}
</script>

<style scoped>

</style>
