<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="参赛人员类型：0教师、1学生" prop="personType">
        <el-select v-model="queryParams.personType" placeholder="请选择参赛人员类型：0教师、1学生" clearable>
          <el-option
            v-for="dict in dict.type.person_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="排序" prop="orderNum">
        <el-input
          v-model="queryParams.orderNum"
          placeholder="请输入排序"
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
          v-hasPermi="['contest:awardPerson:add']"
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
          v-hasPermi="['contest:awardPerson:edit']"
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
          v-hasPermi="['contest:awardPerson:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contest:awardPerson:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="awardPersonList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="参赛人员信息ID" align="center" prop="awardPersonId" />
      <el-table-column label="参赛人员ID" align="center" prop="personId" />
      <el-table-column label="参赛人员类型：0教师、1学生" align="center" prop="personType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.person_type" :value="scope.row.personType"/>
        </template>
      </el-table-column>
      <el-table-column label="排序" align="center" prop="orderNum" />
      <el-table-column label="贡献度" align="center" prop="conDegree" />
      <el-table-column label="工作内容" align="center" prop="workContent" />
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
            v-hasPermi="['contest:awardPerson:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contest:awardPerson:remove']"
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

    <!-- 添加或修改参赛人员信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="参赛人员类型：0教师、1学生" prop="personType">
          <el-select v-model="form.personType" placeholder="请选择参赛人员类型：0教师、1学生">
            <el-option
              v-for="dict in dict.type.person_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="orderNum">
          <el-input v-model="form.orderNum" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="贡献度" prop="conDegree">
          <el-input v-model="form.conDegree" placeholder="请输入贡献度" />
        </el-form-item>
        <el-form-item label="工作内容">
          <editor v-model="form.workContent" :min-height="192"/>
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
import { listAwardPerson, getAwardPerson, delAwardPerson, addAwardPerson, updateAwardPerson } from "@/api/contest/awardPerson";

export default {
  name: "AwardPerson",
  dicts: ['person_type', 'sys_normal_disable'],
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
      // 参赛人员信息表格数据
      awardPersonList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        personType: null,
        orderNum: null,
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
    /** 查询参赛人员信息列表 */
    getList() {
      this.loading = true;
      listAwardPerson(this.queryParams).then(response => {
        this.awardPersonList = response.rows;
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
        awardPersonId: null,
        awardId: null,
        personId: null,
        personType: null,
        orderNum: null,
        conDegree: null,
        workContent: null,
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
      this.ids = selection.map(item => item.awardPersonId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加参赛人员信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const awardPersonId = row.awardPersonId || this.ids
      getAwardPerson(awardPersonId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改参赛人员信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.awardPersonId != null) {
            updateAwardPerson(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAwardPerson(this.form).then(response => {
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
      const awardPersonIds = row.awardPersonId || this.ids;
      this.$modal.confirm('是否确认删除参赛人员信息编号为"' + awardPersonIds + '"的数据项？').then(function() {
        return delAwardPerson(awardPersonIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contest/awardPerson/export', {
        ...this.queryParams
      }, `awardPerson_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
