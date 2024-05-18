<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="题目类型" prop="quType">
        <el-select v-model="queryParams.quType" placeholder="请选择题目类型" clearable>
          <el-option
            v-for="dict in dict.type.exammanage_qu_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="level">
        <el-select v-model="queryParams.level" placeholder="请选择难度等级" clearable>
          <el-option
            v-for="dict in dict.type.exammanage_qu_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门ID" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入部门ID"
          clearable
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['exammanage:qu:add']"
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
          v-hasPermi="['exammanage:qu:edit']"
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
          v-hasPermi="['exammanage:qu:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['exammanage:qu:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="quList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="题目ID" align="center" prop="id" />
      <el-table-column label="题目类型" align="center" prop="quType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.exammanage_qu_type" :value="scope.row.quType"/>
        </template>
      </el-table-column>
      <el-table-column label="难度等级" align="center" prop="level">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.exammanage_qu_level" :value="scope.row.level"/>
        </template>
      </el-table-column>
      <el-table-column label="题目图片" align="center" prop="image" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.image" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="题目内容" align="center" prop="content" />
      <el-table-column label="题目备注" align="center" prop="remark" />
      <el-table-column label="整题解析" align="center" prop="analysis" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="部门ID" align="center" prop="deptId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['exammanage:qu:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['exammanage:qu:remove']"
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

    <!-- 添加或修改试题管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="题目类型" prop="quType">
          <el-select v-model="form.quType" placeholder="请选择题目类型">
            <el-option
              v-for="dict in dict.type.exammanage_qu_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="难度等级" prop="level">
          <el-select v-model="form.level" placeholder="请选择难度等级">
            <el-option
              v-for="dict in dict.type.exammanage_qu_level"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="题目图片" prop="image">
          <image-upload v-model="form.image"/>
        </el-form-item>
        <el-form-item label="题目内容">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="题目备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入题目备注" />
        </el-form-item>
        <el-form-item label="整题解析" prop="analysis">
          <el-input v-model="form.analysis" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="部门ID" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入部门ID" />
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
import { listQu, getQu, delQu, addQu, updateQu } from "@/api/exammanage/qu";

export default {
  name: "Qu",
  dicts: ['exammanage_qu_type', 'exammanage_qu_level'],
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
      // 试题管理表格数据
      quList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        quType: null,
        level: null,
        image: null,
        content: null,
        analysis: null,
        userId: null,
        deptId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        quType: [
          { required: true, message: "题目类型不能为空", trigger: "change" }
        ],
        level: [
          { required: true, message: "难度等级", trigger: "change" }
        ],
        image: [
          { required: true, message: "题目图片不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "题目内容不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
        remark: [
          { required: true, message: "题目备注不能为空", trigger: "blur" }
        ],
        analysis: [
          { required: true, message: "整题解析不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询试题管理列表 */
    getList() {
      this.loading = true;
      listQu(this.queryParams).then(response => {
        this.quList = response.rows;
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
        id: null,
        quType: null,
        level: null,
        image: null,
        content: null,
        createTime: null,
        updateTime: null,
        remark: null,
        analysis: null,
        userId: null,
        deptId: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      /*this.open = true;
      this.title = "添加试题管理";*/
      this.$router.push("/exammanage/qu-add/addQu");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const quId = row.id || this.ids
     /* getQu(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改试题管理";
      });*/
      this.$router.push(`/exammanage/qu-update/updateQu/${quId}`);
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateQu(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addQu(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除试题管理编号为"' + ids + '"的数据项？').then(function() {
        return delQu(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('exammanage/qu/export', {
        ...this.queryParams
      }, `qu_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
