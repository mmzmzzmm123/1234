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
      <el-form-item label="月份" prop="month">
        <el-date-picker
          v-model="month"
          @change="monthRangeChange"
          type="month"
          placeholder="选择月">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['commision:detail:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="commisionList"
              @selection-change="handleSelectionChange">
      <el-table-column label="业务员" align="center" prop="userName"/>
      <el-table-column label="岗位" align="center" prop="postName"/>
      <el-table-column label="总金额" align="center" prop="amount">
        <template scope="scope">
          {{toThousands(scope.row.amount)}}
        </template>
      </el-table-column>
      <el-table-column label="比例" align="center" prop="rate">
        <template scope="scope">
          {{scope.row.rate + '%'}}
        </template>
      </el-table-column>
      <el-table-column label="提成" align="center" prop="commision">
        <template scope="scope">
          {{scope.row.commision.toFixed(2)}}
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
  import {
    detailCommision,
    exportCommision,
  } from "@/api/custom/commision";

  import {getOptions} from "@/api/custom/order";

  import dayjs from 'dayjs';

  export default {
    name: "CommisionDetail",
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
        showSearch: false,
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
          pageSize: 10,
          userId: null,
          postId: null,
        },
      };
    },
    created() {
      this.getList();
      getOptions().then(response => {
        this.options = response.data.reduce((opts, cur) => {
          if (cur.postCode.startsWith('planner')) {
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
        this.searchUserIdOptions = this.totalUserIdOptions.slice();
      });

    },
    methods: {
      /** 查询业务提成比例列表 */
      getList() {
        this.loading = true;
        const dateRange = [dayjs(this.month).startOf('month').format('YYYY-MM-DD'), dayjs(this.month).endOf('month').format('YYYY-MM-DD')];
        console.log(dateRange)
        detailCommision(this.addDateRange(this.queryParams, dateRange)).then(response => {
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
          return exportCommision(queryParams);
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
      }
    }
  };
</script>
