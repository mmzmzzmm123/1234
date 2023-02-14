<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="竞赛ID" prop="contestId">
        <el-input
          v-model="queryParams.contestId"
          placeholder="请输入竞赛ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="竞赛名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入竞赛名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年度" prop="year">
        <el-date-picker clearable
          v-model="queryParams.year"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择年度">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="等级" prop="rank">
        <el-input
          v-model="queryParams.rank"
          placeholder="请输入等级"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="delFlag">
        <el-select v-model="queryParams.delFlag" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
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
          v-hasPermi="['contest:subContest:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['contest:subContest:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['contest:subContest:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contest:subContest:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="subContestList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="子竞赛ID" align="center" prop="subContestId" />
      <el-table-column label="竞赛ID" align="center" prop="contestId" />
      <el-table-column label="竞赛名称" align="center" prop="name" />
      <el-table-column label="竞赛通知网址" align="center" prop="url" />
      <el-table-column label="附件" align="center" prop="attachmentsUrl" />
      <el-table-column label="年度" align="center" prop="year" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.year, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="等级" align="center" prop="rank">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sub_contest_rank" :value="scope.row.rank"/>
        </template>
      </el-table-column>
      <el-table-column label="竞赛日期" align="center" prop="competitionDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.competitionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报名费用" align="center" prop="fee" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="状态" align="center" prop="delFlag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.delFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contest:subContest:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contest:subContest:remove']"
          >删除</el-button>
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

    <!-- 添加或修改子竞赛信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="竞赛ID" prop="contestId">
          <el-input v-model="form.contestId" placeholder="请输入竞赛ID" />
        </el-form-item>
        <el-form-item label="竞赛名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入竞赛名称" />
        </el-form-item>
        <el-form-item label="竞赛通知网址" prop="url">
          <el-input v-model="form.url" placeholder="请输入竞赛通知网址" />
        </el-form-item>
        <el-form-item label="附件" prop="attachmentsUrl">
          <el-input v-model="form.attachmentsUrl" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="年度" prop="year">
          <el-date-picker clearable
            v-model="form.year"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择年度">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="等级" prop="rank">
          <el-input v-model="form.rank" placeholder="请输入等级" />
        </el-form-item>
        <el-form-item label="竞赛日期" prop="competitionDate">
          <el-date-picker clearable
            v-model="form.competitionDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择竞赛日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="报名费用" prop="fee">
          <el-input v-model="form.fee" placeholder="请输入报名费用" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSubContest, getSubContest, delSubContest, addSubContest, updateSubContest } from "@/api/contest/subContest";

export default {
  name: "SubContest",
  dicts: ['sys_normal_disable'],
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
      // 子竞赛信息表格数据
      subContestList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contestId: null,
        name: null,
        year: null,
        rank: null,
        delFlag: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询子竞赛信息列表 */
    getList() {
      this.loading = true;
      listSubContest(this.queryParams).then(response => {
        this.subContestList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        subContestId: null,
        contestId: null,
        name: null,
        url: null,
        attachmentsUrl: null,
        year: null,
        rank: null,
        competitionDate: null,
        fee: null,
        remark: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
      };
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
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.subContestId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加子竞赛信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const subContestId = row.subContestId || this.ids
      getSubContest(subContestId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改子竞赛信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.subContestId != null) {
            updateSubContest(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSubContest(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const subContestIds = row.subContestId || this.ids;
      this.$modal.confirm('是否确认删除子竞赛信息编号为"' + subContestIds + '"的数据项？').then(function() {
        return delSubContest(subContestIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contest/subContest/export', {
        ...this.queryParams
      }, `subContest_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
