<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="游戏数学名称" prop="mathid">
        <el-select v-model="queryParams.mathid" size="small">
          <el-option
            v-for="item in mathOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="游戏名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入游戏名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="排序" prop="sort">
        <el-input
          v-model="queryParams.sort"
          placeholder="请输入排序"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="游戏形式" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择游戏形式" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="游戏目标" prop="target">
        <el-input
          v-model="queryParams.target"
          placeholder="请输入游戏目标"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="游戏准备" prop="prepare">
        <el-input
          v-model="queryParams.prepare"
          placeholder="请输入游戏准备"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="游戏过程" prop="process">
        <el-input
          v-model="queryParams.process"
          placeholder="请输入游戏过程"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="本周其它目标" prop="otherTarget">
        <el-input
          v-model="queryParams.otherTarget"
          placeholder="请输入本周其它目标"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="建议" prop="suggest">
        <el-input
          v-model="queryParams.suggest"
          placeholder="请输入建议"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createtime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.createtime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建时间"
        ></el-date-picker>
      </el-form-item> -->
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
          v-hasPermi="['benyi:plan:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:plan:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:plan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['benyi:plan:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="游戏数学方案编号" align="center" prop="id" />
      <el-table-column label="游戏名称" align="center" prop="name" />
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="游戏形式" align="center" :formatter="typeFormat" prop="type" />
      <el-table-column label="游戏目标" align="center" prop="target" />
      <el-table-column label="游戏准备" align="center" prop="prepare" />
      <el-table-column label="游戏过程" align="center" prop="process" />
      <!-- <el-table-column label="本周其它目标" align="center" prop="otherTarget" />
      <el-table-column label="建议" align="center" prop="suggest" />
      <el-table-column label="创建时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:plan:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:plan:remove']"
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

    <!-- 添加或修改游戏数学方案对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1024px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属游戏数学" prop="mathid">
          <el-select v-model="form.mathid" size="small" :disabled="true">
            <el-option
              v-for="item in mathOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="游戏名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入游戏名称" />
        </el-form-item>
        <el-form-item label="序号" prop="sort">
          <el-input-number v-model="form.sort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="游戏形式" prop="type">
          <el-select v-model="form.type" multiple placeholder="请选择游戏形式" clearable size="small">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="游戏目标" prop="target">
          <Editor v-model="form.target" placeholder="请输入游戏目标" />
        </el-form-item>
        <el-form-item label="游戏准备" prop="prepare">
          <Editor v-model="form.prepare" placeholder="请输入游戏准备" />
        </el-form-item>
        <el-form-item label="游戏过程" prop="process">
          <Editor v-model="form.process" placeholder="请输入游戏过程" />
        </el-form-item>
        <el-form-item label="本周其它目标" prop="otherTarget">
          <Editor v-model="form.otherTarget" placeholder="请输入本周其它目标" />
        </el-form-item>
        <el-form-item label="建议" prop="suggest">
          <Editor v-model="form.suggest" placeholder="请输入建议" />
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
  listPlan,
  getPlan,
  delPlan,
  addPlan,
  updatePlan,
  exportPlan
} from "@/api/benyi/plan";
import { listMath, getMath } from "@/api/benyi/math";
import Editor from "@/components/Editor";

export default {
  name: "Plan",
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
      // 游戏数学名称
      mathOptions: [],
      // 默认游戏数学名称
      defaultMath: "",
      // 游戏数学方案表格数据
      planList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 游戏形式字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mathid: undefined,
        name: undefined,
        sort: undefined,
        type: undefined,
        target: undefined,
        prepare: undefined,
        process: undefined,
        otherTarget: undefined,
        suggest: undefined,
        createtime: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    const mathId = this.$route.params && this.$route.params.id;
    this.getMath(mathId);
    this.getMaths();
    this.getDicts("sys_math_type").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    /** 查询游戏数学方案列表 */
    getList() {
      this.loading = true;
      listPlan(this.queryParams).then(response => {
        this.planList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 游戏形式字典翻译
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
    /** 查询游戏数学名称详细 */
    getMath(mathId) {
      getMath(mathId).then(response => {
        this.queryParams.mathid = response.data.id;
        this.defaultMath = response.data.id;
        this.getList();
      });
    },
    //获取游戏数学名称列表
    getMaths() {
      listMath().then(response => {
        this.mathOptions = response.rows;
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
        mathid: undefined,
        name: undefined,
        sort: undefined,
        type: undefined,
        target: undefined,
        prepare: undefined,
        process: undefined,
        otherTarget: undefined,
        suggest: undefined,
        createtime: undefined
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
      this.queryParams.mathid = this.defaultMath;
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
      this.title = "添加游戏数学方案";
      this.form.mathid = this.queryParams.mathid;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getPlan(id).then(response => {
        this.form = response.data;
        // console.log(this.form);
        this.typeOptions = response.types;
        // console.log(this.typeOptions);
        this.form.type = response.typeIds;
        
        this.open = true;
        this.title = "修改游戏数学方案";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          var arrtype = this.form.type;
          this.form.type = arrtype.join(";");
          
          if (this.form.id != undefined) {
            updatePlan(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addPlan(this.form).then(response => {
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
        '是否确认删除游戏数学方案编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delPlan(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有游戏数学方案数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportPlan(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    }
  }
};
</script>