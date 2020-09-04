<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="主题名称" prop="themeid">
        <el-select v-model="queryParams.themeid" size="small">
          <el-option
            v-for="item in themeOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入活动名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动形式" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择活动形式" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动领域" prop="field">
        <el-select v-model="queryParams.field" placeholder="请选择活动领域" clearable size="small">
          <el-option
            v-for="dict in fieldOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['benyi:theme:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:theme:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:theme:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="activityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="活动名称" align="center" prop="name" />
      <el-table-column label="活动形式" align="center" :formatter="typeFormat" prop="type" />
      <el-table-column label="活动领域" align="center" :formatter="fieldFormat" prop="field" />
      <el-table-column label="序号" align="center" prop="sort" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:theme:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:theme:remove']"
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

    <!-- 添加或修改主题整合活动对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1024px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属主题" prop="themeid">
          <el-select v-model="form.themeid" size="small" :disabled="true">
            <el-option
              v-for="item in themeOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="活动名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="活动形式" prop="type">
          <el-select v-model="form.type" multiple placeholder="请选择活动形式" clearable size="small">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="活动领域" prop="field">
          <el-select v-model="form.field" multiple placeholder="请选择活动领域" clearable size="small">
            <el-option
              v-for="dict in fieldOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="活动目标" prop="target">
          <Editor v-model="form.target" />
        </el-form-item>
        <el-form-item label="活动材料" prop="data">
          <Editor v-model="form.data" />
        </el-form-item>
        <el-form-item label="活动过程" prop="process">
          <Editor v-model="form.process" />
        </el-form-item>
        <el-form-item label="活动建议" prop="proposal">
          <Editor v-model="form.proposal" />
        </el-form-item>
        <el-form-item label="活动反思" prop="reflect">
          <Editor v-model="form.reflect" />
        </el-form-item>
        <el-form-item label="活动附录" prop="appendix">
          <Editor v-model="form.appendix" />
        </el-form-item>
        <el-form-item label="序号" prop="sort">
          <el-input-number v-model="form.sort" controls-position="right" :min="0" />
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
import {
  listActivity,
  getActivity,
  delActivity,
  addActivity,
  updateActivity
} from "@/api/benyi/activity";
import { listTheme, getTheme } from "@/api/benyi/theme";
import Editor from "@/components/Editor";

export default {
  name: "Activity",
  components: {
    Editor
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
      // 总条数
      total: 0,
      // 主题整合活动表格数据
      activityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //主题
      themeOptions: [],
      defaultTheme: "",
      //活动形式
      typeOptions: [],
      //活动领域
      fieldOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        themeid: undefined,
        name: undefined,
        type: undefined,
        field: undefined,
        target: undefined,
        data: undefined,
        process: undefined,
        proposal: undefined,
        reflect: undefined,
        appendix: undefined,
        sort: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "名称不能为空", trigger: "blur" }],
        type: [
          { required: true, message: "活动形式不能为空", trigger: "blur" }
        ],
        field: [
          { required: true, message: "活动领域不能为空", trigger: "blur" }
        ],
        target: [
          { required: true, message: "活动目标不能为空", trigger: "blur" }
        ],
        data: [
          { required: true, message: "活动材料不能为空", trigger: "blur" }
        ],
        process: [
          { required: true, message: "活动过程不能为空", trigger: "blur" }
        ],
        proposal: [
          { required: true, message: "活动建议不能为空", trigger: "blur" }
        ],
        reflect: [
          { required: true, message: "活动反思不能为空", trigger: "blur" }
        ],
        sort: [{ required: true, message: "排序序号", trigger: "blur" }]
      }
    };
  },
  created() {
    const themeId = this.$route.params && this.$route.params.id;
    this.getTheme(themeId);
    this.getThemes();
    this.getDicts("sys_theme_type").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("sys_theme_field").then(response => {
      this.fieldOptions = response.data;
    });
  },
  methods: {
    // 活动领域类型--字典状态字典翻译
    fieldFormat(row, column) {
      //alert(row.scope.split(';').length);
      var ilength = row.field.split(";").length;
      var names = "";
      for (var i = 0; i < ilength; i++) {
        names =
          names +
          this.selectDictLabel(this.fieldOptions, row.field.split(";")[i]) +
          ";";
      }
      //this.selectDictLabel(this.scopeOptions, row.xnxq);
      return names;
    },
    // 活动形式类型--字典状态字典翻译
    typeFormat(row, column) {
      //alert(row.scope.split(';').length);
      var ilength = row.type.split(";").length;
      var names = "";
      for (var i = 0; i < ilength; i++) {
        names =
          names +
          this.selectDictLabel(this.typeOptions, row.type.split(";")[i]) +
          ";";
      }
      //this.selectDictLabel(this.scopeOptions, row.xnxq);
      return names;
    },
    /** 查询主题详细 */
    getTheme(themeId) {
      getTheme(themeId).then(response => {
        this.queryParams.themeid = response.data.id;
        this.defaultTheme = response.data.id;
        this.getList();
      });
    },
    //获取主题列表
    getThemes() {
      listTheme().then(response => {
        this.themeOptions = response.rows;
      });
    },
    /** 查询主题整合活动列表 */
    getList() {
      this.loading = true;
      listActivity(this.queryParams).then(response => {
        this.activityList = response.rows;
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
        id: undefined,
        themeid: undefined,
        name: undefined,
        type: undefined,
        field: undefined,
        target: undefined,
        data: undefined,
        process: undefined,
        proposal: undefined,
        reflect: undefined,
        appendix: undefined,
        sort: 0,
        createTime: undefined
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
      this.queryParams.themeid = this.defaultTheme;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加主题整合活动";
      this.form.themeid = this.queryParams.themeid;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getActivity(id).then(response => {
        this.form = response.data;
        console.log(this.form);

        this.typeOptions = response.types;
        this.form.type = response.typeIds;

        this.fieldOptions = response.fields;
        this.form.field = response.fieldIds;

        this.open = true;
        this.title = "修改主题整合活动";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          var arrtype = this.form.type;
          this.form.type = arrtype.join(";");

          var arrfield = this.form.field;
          this.form.field = arrfield.join(";");

          if (this.form.id != undefined) {
            updateActivity(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addActivity(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除主题整合活动编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delActivity(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function() {});
    }
  }
};
</script>