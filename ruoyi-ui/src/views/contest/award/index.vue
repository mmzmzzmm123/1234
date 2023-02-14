<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="获奖日期" prop="awardDate">
        <el-date-picker clearable
          v-model="queryParams.awardDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择获奖日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="获奖等级" prop="awardGrade">
        <el-select v-model="queryParams.awardGrade" placeholder="请选择获奖等级" clearable>
          <el-option
            v-for="dict in dict.type.award_grade"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['contest:award:add']"
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
          v-hasPermi="['contest:award:edit']"
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
          v-hasPermi="['contest:award:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contest:award:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="awardList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="获奖ID" align="center" prop="awardId" />
      <el-table-column label="子竞赛ID" align="center" prop="subContestId" />
      <el-table-column label="获奖日期" align="center" prop="awardDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.awardDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="获奖等级" align="center" prop="awardGrade">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.award_grade" :value="scope.row.awardGrade"/>
        </template>
      </el-table-column>
      <el-table-column label="支撑材料" align="center" prop="attachmentUrl" />
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
            v-hasPermi="['contest:award:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contest:award:remove']"
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

    <!-- 添加或修改获奖登记对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="获奖日期" prop="awardDate">
          <el-date-picker clearable
            v-model="form.awardDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择获奖日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="获奖等级" prop="awardGrade">
          <el-select v-model="form.awardGrade" placeholder="请选择获奖等级">
            <el-option
              v-for="dict in dict.type.award_grade"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="支撑材料" prop="attachmentUrl">
          <file-upload v-model="form.attachmentUrl"/>
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
import { listAward, getAward, delAward, addAward, updateAward } from "@/api/contest/award";

export default {
  name: "Award",
  dicts: ['award_grade', 'sys_normal_disable'],
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
      // 获奖登记表格数据
      awardList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        contestId: null,
        subContestId: null,
        awardDate: null,
        awardGrade: null,
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
    /** 查询获奖登记列表 */
    getList() {
      this.loading = true;
      listAward(this.queryParams).then(response => {
        this.awardList = response.rows;
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
        awardId: null,
        contestId: null,
        subContestId: null,
        awardDate: null,
        awardGrade: null,
        attachmentUrl: null,
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
      this.ids = selection.map(item => item.awardId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加获奖登记";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const awardId = row.awardId || this.ids
      getAward(awardId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改获奖登记";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.awardId != null) {
            updateAward(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAward(this.form).then(response => {
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
      const awardIds = row.awardId || this.ids;
      this.$modal.confirm('是否确认删除获奖登记编号为"' + awardIds + '"的数据项？').then(function() {
        return delAward(awardIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contest/award/export', {
        ...this.queryParams
      }, `award_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
