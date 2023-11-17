<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="创建时间" prop="dateLimit">
        <el-date-picker
          v-model="timeVal"
          style="width: 280px;"
          size="small"
          value-format="yyyy-MM-dd"
          format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @change="onchangeTime"
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
      <el-form-item label="审批状态" prop="status">
        <el-select v-model="queryParams.status" clearable>
          <el-option
            v-for="dict in statusList"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['contract:contract:add']"
        >发起合同</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" size="mini" style="width: 100%" :data="contractList" @selection-change="handleSelectionChange">
      <el-table-column label="合同编号" width="180" align="center" prop="id" />
      <el-table-column label="协议名称" width="180" align="center" prop="name" />
      <el-table-column label="签署人" width="100" align="center" prop="consultName" />
      <el-table-column label="入驻类型" width="150" align="center" prop="type" >
        <template slot-scope="scope">
          <span>{{ types.find(item => item.value == scope.row.type).label }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入驻费用" width="100" align="center" prop="money">
        <template slot-scope="scope">
          <span>{{ scope.row.money.toFixed('2') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结算比例" width="100" align="center" prop="ratio">
        <template slot-scope="scope">
          <span>{{ scope.row.ratio.toFixed('2') }}%</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100" align="center" prop="status">
        <template slot-scope="scope">
          <span>{{ statusList.find(item => item.value === scope.row.status).label }}</span>
          <el-tooltip v-if="['2','7'].includes(scope.row.status)" class="item" effect="dark" content="与咨询师协商一致，更新合作方式 " placement="top-start" style="margin-left: 2px">
            <i class="el-icon-question"></i>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="有效期" align="center" prop="startTime" width="200">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }} ~ {{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="签署时间" align="center" prop="startTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="view(scope.row)"
          >查看</el-button>
          <template v-if="scope.row.status === '1'">
            <el-button
              size="mini"
              type="text"
              @click="unDo(scope.row)"
              v-hasPermi="['contract:contract:edit']"
            >撤销</el-button>
          </template>
          <template v-else-if="['4','5'].includes(scope.row.status)">
            <el-button
              size="mini"
              type="text"
              @click="doStop(scope.row)"
              v-hasPermi="['contract:contract:edit']"
            > 终止合同</el-button>
          </template>
          <template v-else>
            <el-button
              size="mini"
              type="text"
              @click="tryAgain(scope.row)"
              v-hasPermi="['contract:contract:edit']"
            >重新发起</el-button>
          </template>
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

    <!-- 添加或修改咨询师合同协议对话框 -->
    <add-form ref="addForm" :consultList="consultList" @handleOk="getList"/>

    <do-form ref="doForm" @handleOk="getList"/>

  </div>
</template>

<script>
import doForm from "./doForm";
import addForm from "./addForm";
import { getConsultAll } from "@/api/psychology/consult";
import { listContract, getContract, delContract } from "@/api/psychology/contract";

export default {
  name: "Contract",
  components: {
    doForm,
    addForm,
  },
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
      // 咨询师合同协议表格数据
      contractList: [],
      // 咨询师列表
      timeVal: [],
      consultList: [],
      types: this.$constants.partnerTypes,
      statusList: this.$constants.contractStatus,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        consultId: null,
        startTime: null,
        endTime: null,
        status: null,
        time: [],
      },
    };
  },
  async created() {
    await this.getConsults();
    this.getList();
  },
  methods: {
    onchangeTime (e) {
      this.timeVal = e;
      this.queryParams.dateLimit = e ? this.timeVal.join(',') : ''
      this.queryParams.pageNum = 1
      this.getList();
    },
    async getConsults() {
      const res = await getConsultAll()
      this.consultList = res.data
      if (this.consultList.length === 1) {
        this.queryParams.consultId = this.consultList[0].id
      }
    },
    /** 查询咨询师合同协议列表 */
    getList() {
      this.loading = true;
      listContract(this.queryParams).then(response => {
        this.contractList = response.rows;
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$refs.addForm.init()
    },
    /** 查看项目 */
    view(row) {
      const id = row.id
      getContract(id).then(response => {
        // this.form = response.data;
        // this.open = true;
        // this.title = "查看合同";
        console.log(response)
      });
    },
    /** 撤销 */
    unDo(row) {
      const data = {
        id: row.id,
        status: '2',
        title: '确认撤销与该咨询师的合同吗？',
        placeholder: '请输入撤销原因'
      }
      this.$refs.doForm.init(data)
    },
    /** 终止合同 */
    doStop(row) {
      const data = {
        id: row.id,
        status: '7',
        title: '确认终止与该咨询师的合同吗？',
        placeholder: '请输入终止原因'
      }
      this.$refs.doForm.init(data)
    },
    /** 重新发起 */
    tryAgain(row) {
      const that = this
      that.$modal.confirm('确认发起合同吗？').then(function() {

      }).then(() => {
      }).catch(() => {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除咨询师合同协议编号为"' + ids + '"的数据项？').then(function() {
        return delContract(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contract/contract/export', {
        ...this.queryParams
      }, `contract_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
