<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="岗位" prop="postId">
        <el-select v-model="queryParams.postId" placeholder="请选择岗位" clearable size="small" @change="searchPostChange">
          <el-option
            v-for="dict in postIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="业务员" prop="userId">
        <el-select v-model="queryParams.userId" placeholder="请选择业务员" clearable size="small">
          <el-option
            v-for="dict in searchUserIdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <!--<el-form-item label="月份" prop="month">
        <el-date-picker
          v-model="month"
          @change="monthRangeChange"
          type="month"
          placeholder="选择月">
        </el-date-picker>
      </el-form-item>-->
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <div><span style="color:#E6A23C;font-family:PingFang SC">
          注意事项：
          <br/>1、2021年1月开始的订单才开始按天计算提成
          <br/>2、发工资日期定为每月15号，若当前日期大于15号，则认定上个月的工资已发放
    </span></div>
    <el-row :gutter="10" class="mb8" style="margin-top:10px;">
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['commisionDay:detail:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commisionList"
              @selection-change="handleSelectionChange" stripe :row-class-name="tableRowClassName">
      <el-table-column label="业务员" align="center" prop="nickName">
        <template slot-scope="scope">
          <span>{{ scope.row.nickName }}</span>
        </template>

      </el-table-column>

      <el-table-column label="岗位" align="center" prop="postName"/>
      <el-table-column label="总提成" align="center" prop="totalCommissionAmount">
        <template scope="scope">
          {{scope.row.totalCommissionAmount.toFixed(3)}}
        </template>
      </el-table-column>
      <el-table-column label="已发放提成" align="center" prop="totalHasSentCommissionAmount">
        <template scope="scope">
          {{scope.row.totalHasSentCommissionAmount.toFixed(3)}}
        </template>
      </el-table-column>
      <el-table-column label="未发放提成" align="center" prop="totalNotSentCommissionAmount">
        <template scope="scope">
          {{scope.row.totalNotSentCommissionAmount.toFixed(3)}}
        </template>

      </el-table-column>
      <el-table-column label="下月应发提成" align="center" prop="nextMonthCommission">
        <template scope="scope">
          {{scope.row.nextMonthCommission.toFixed(3)}}
        </template>

      </el-table-column>
      <el-table-column label="操作" align="center">
        <template scope="scope">
          <el-button type="text" @click="openFormDialog('查看发放计划', scope.row)">查看发放计划</el-button>
        </template>
      </el-table-column>

      <!--<el-table-column label="提成" align="center" prop="commissionTotalAmount"></el-table-column>-->

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />


    <el-dialog title="提成发放计划" :visible.sync="sendCommissionPlanTable" width="40%" align="center">
      <el-table :data="sendCommissionPlan.list" show-summary  :summary-method="getSummaries">
        <el-table-column property="nickName" label="姓名" width="150"></el-table-column>
        <el-table-column property="yearMonth" label="提成月份" width="200">
          <template scope="scope">
            {{scope.row.yearMonth.substring(0, 4)}}年{{scope.row.yearMonth.substring(4)}}月
          </template>
        </el-table-column>
        <el-table-column property="yearMonthCommission" label="提成金额" width="100">
          <template scope="scope">
            {{scope.row.yearMonthCommission.toFixed(3)}}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
     /*background: #1890FF;*/
    color: #1890FF
  }
</style>

<script>
  import {
    detailDayCommision, exportDayCommision} from "@/api/custom/commision";

  import {getOptions} from "@/api/custom/order";

  import dayjs from 'dayjs';

  export default {
    name: "CommisionDayDetail",
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        //
        month: dayjs(),
        // 业务提成比例表格数据
        commisionList: [],
        // 是否显示弹出层
        open: false,
        // 业务员字典
        userIdOptions: [],
        //
        postIdOptions: [],
        //
        totalUserIdOptions: [],
        //
        searchUserIdOptions: [],
        //
        options: {},
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 20,
          userId: null,
          postId: null,
        },
        sendCommissionPlanTable: false,
        sendCommissionPlan:{}
      };
    },
    created() {
      this.getList();
      getOptions().then(response => {
        this.options = response.data.reduce((opts, cur) => {
            if (cur.postCode != ('after_sale') && cur.postCode != 'nutri') {
              return opts;
            }
          if (!opts[cur.postId]) {
            opts[cur.postId] = [];
          }
          opts[cur.postId].push({dictValue: cur.userId, dictLabel: cur.userName});

          if (!this.postIdOptions.some(opt => opt.dictValue === cur.postId)) {
            this.postIdOptions.push({dictValue: cur.postId, dictLabel: cur.postName});
          }
          if (!this.totalUserIdOptions.some(opt => opt.dictValue === cur.userId)) {
            this.totalUserIdOptions.push({dictValue: cur.userId, dictLabel: cur.userName});
          }
          return opts;
        }, {});
        this.postIdOptions = this.postIdOptions.sort((a, b) => a.dictValue - b.dictValue);
        this.searchUserIdOptions = this.totalUserIdOptions.slice();
      })

    },
    methods: {
      /** 查询业务提成比例列表 */
      getList() {
        this.loading = true;
        const dateRange = [dayjs(this.month).startOf('month').format('YYYY-MM-DD'), dayjs(this.month).endOf('month').format('YYYY-MM-DD')];
        console.log(dateRange)
        detailDayCommision(this.addDateRange(this.queryParams, dateRange)).then(response => {
          this.commisionList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 业务员字典翻译
      userIdFormat(row, column) {
        return this.selectDictLabel(this.userIdOptions, row.userId);
      },
      // 表单重置
      reset() {
        this.userIdOptions = [];
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
        this.searchUserIdOptions = this.totalUserIdOptions.slice();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.ruleId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有业务提成比例数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportDayCommision(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function () {
        });
      },
      monthRangeChange(time) {
        this.month = time;
      },
      postChange(postId) {
        this.userIdOptions = this.options[postId];
      },
      searchPostChange(postId) {
        if (!postId) {
          this.searchUserIdOptions = this.totalUserIdOptions.slice();
          return;
        }
        this.searchUserIdOptions = this.options[postId];
        this.queryParams.userId = null;
      },
      openFormDialog(title, row){
         this.sendCommissionPlan = {};
         var total = 0;
         if(row.sendDetailList != null){
           row.sendDetailList.forEach(function (e) {
             e.nickName = row.nickName;
             total += e.yearMonthCommission;
           });
         }
         this.sendCommissionPlan.list = row.sendDetailList;
         this.sendCommissionPlan.total = row.totalNotSentCommissionAmount;
         this.sendCommissionPlanTable = true;
      },
      getSummaries(param) {
        //param 是固定的对象，里面包含 columns与 data参数的对象 {columns: Array[4], data: Array[5]},包含了表格的所有的列与数据信息
        const { columns, data } = param;
        const sums = [];
        columns.forEach((column, index) => {
          if (index === 0) {
            sums[index] = '合计';
            return;
          }
          if(index == 1){
            sums[index] = data.length +"个月";
            return
          }
          if(index == 2){
            sums[index] = this.sendCommissionPlan.total;
          }
        });
        return sums;
      },
      tableRowClassName({row, rowIndex}) {
        if(this.commisionList.length == rowIndex+1){
           return "success-row";
        }
        return '';
      }
    }
  };
</script>
