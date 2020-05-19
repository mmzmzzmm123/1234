<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="流程名称" prop="detailName">
        <el-select v-model="queryParams.detailName" size="small">
          <el-option
            v-for="item in detailOptions"
            :key="item.id"
            :label="item.name"
            :value="item.name"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="任务名称" prop="taskLable">
        <el-input
          v-model="queryParams.taskLable"
          placeholder="请输入任务名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['benyi:dayflowtask:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:dayflowtask:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:dayflowtask:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['benyi:dayflowtask:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dayflowtaskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务名称" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope" >
          <router-link :to="'/dayflow/dayflowmanger/dayflowtask/standard/' + scope.row.code" class="link-dayflow">
            <span>{{ scope.row.taskLable }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="所属流程" align="center" prop="detailName" />
      <el-table-column label="任务排序" align="center" prop="taskSort" />
      <el-table-column label="创建时间" align="center" prop="createtime" />
      <el-table-column label="更新时间" align="center" prop="updatetime" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:dayflowtask:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:dayflowtask:remove']"
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

    <!-- 添加或修改一日流程任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="流程名称" prop="detailName" >
          <el-input v-model="form.detailName" :disabled="true" />
        </el-form-item>
        <el-form-item label="任务名称" prop="taskLable">
          <el-input v-model="form.taskLable" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="任务目的" prop="taskTarget">
          <el-input v-model="form.taskTarget" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="任务解读" prop="taskContent">
          <el-input v-model="form.taskContent" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="任务排序" prop="taskSort">
          <el-input-number v-model="form.taskSort" controls-position="right" :min="0" />
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
import { listDayflowtask, getDayflowtask, delDayflowtask, addDayflowtask, updateDayflowtask, exportDayflowtask } from "@/api/benyi/dayflow/dayflowtask";
import {listDetail, getDetail} from "@/api/benyi/dayflow/dayflowmanger";

export default {
  name: "Dayflowtask",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      codes: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 一日流程任务表格数据
      dayflowtaskList: [],
      // 弹出层标题
      title: "",
      // 默认流程名称
      defaultDetailName: "",
      // 流程名称字典
      detailOptions: [],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskLable: undefined,
        taskValue: undefined,
        detailName: undefined,
        taskTarget: undefined,
        taskContent: undefined,
        taskSort: undefined,
        cssClass: undefined,
        listClass: undefined,
        createuser: undefined,
        updateuser: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        taskLable: [
          { required: true, message: "任务名称(标签)不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    const detailId = this.$route.params && this.$route.params.id;
    //this.getList();
    this.getDetail(detailId);
    this.getDetailList();
  },
  methods: {
    /**查询流程名称详细 */
    getDetail(detailId) {
      getDetail(detailId).then(response => {
        this.queryParams.detailName = response.data.name;
        this.defaultDetailName = response.data.name;
        this.getList();
      })
    },
    /**查询流程名称列表 */
    getDetailList() {
      listDetail().then(response => {
        this.detailOptions = response.rows;
      })
    },
    /** 查询一日流程任务列表 */
    getList() {
      this.loading = true;
      listDayflowtask(this.queryParams).then(response => {
        this.dayflowtaskList = response.rows;
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
        code: undefined,
        taskLable: undefined,
        taskValue: undefined,
        detailName: undefined,
        taskTarget: undefined,
        taskContent: undefined,
        taskSort: undefined,
        cssClass: undefined,
        listClass: undefined,
        createuser: undefined,
        updateuser: undefined,
        createtime: undefined,
        updatetime: undefined,
        remark: undefined
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
      this.queryParams.detailName = this.defaultDetailName;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.codes = selection.map(item => item.code)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加一日流程任务";
      //有可能是name
      this.form.detailName = this.queryParams.detailName;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const code = row.code || this.codes
      getDayflowtask(code).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改一日流程任务";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.code != undefined) {
            updateDayflowtask(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addDayflowtask(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const codes = row.code || this.codes;
      this.$confirm('是否确认删除一日流程任务编号为"' + codes + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDayflowtask(codes);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有一日流程任务数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDayflowtask(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>