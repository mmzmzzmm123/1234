<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="102px">
      <el-form-item label="姓名" prop="gbid">
        <el-input
          v-model="queryParams.gbid"
          placeholder="姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任职年月" prop="rzny">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.rzny"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择任职年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="行政职务" prop="xzzw">
        <el-select v-model="queryParams.xzzw" placeholder="请选择行政职务" clearable size="small">
          <el-option
            v-for="dict in xzzwOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="行政职务级别" prop="xxzwjb">
        <el-select v-model="queryParams.xxzwjb" placeholder="请选择行政职务级别" clearable size="small">
          <el-option
            v-for="dict in xxzwjbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="党内职务" prop="dnzw">
        <el-select v-model="queryParams.dnzw" placeholder="请选择党内职务" clearable size="small">
          <el-option
            v-for="dict in dnzwOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="党内职务属性" prop="dnzwsx">
        <el-select v-model="queryParams.dnzwsx" placeholder="请选择党内职务属性" clearable size="small">
          <el-option
            v-for="dict in dnzwsxOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="群团职务" prop="qtzw">
        <el-select v-model="queryParams.qtzw" placeholder="请选择群团职务" clearable size="small">
          <el-option
            v-for="dict in qtzwOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
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
          v-hasPermi="['gbxxgl:gbxrzw:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['gbxxgl:gbxrzw:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['gbxxgl:gbxrzw:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['gbxxgl:gbxrzw:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gbxrzwList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="姓名" align="center" prop="gbid" />
      <el-table-column label="任职年月" align="center" prop="rzny" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.rzny, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="行政职务" align="center" prop="xzzw" :formatter="xzzwFormat" />
      <el-table-column label="行政职务级别" align="center" prop="xxzwjb" :formatter="xxzwjbFormat" />
      <el-table-column label="党内职务" align="center" prop="dnzw" :formatter="dnzwFormat" />
      <el-table-column label="党内职务属性" align="center" prop="dnzwsx" :formatter="dnzwsxFormat" />
      <el-table-column label="群团职务" align="center" prop="qtzw" :formatter="qtzwFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gbxxgl:gbxrzw:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gbxxgl:gbxrzw:remove']"
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

    <!-- 添加或修改干部任职情况-现任职务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="姓名" prop="gbid">
          <el-input v-model="form.gbid" placeholder="请输入干部姓名" />
        </el-form-item>
        <el-form-item label="任职年月" prop="rzny">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.rzny"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择任职年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="行政职务" prop="xzzw">
          <el-select v-model="form.xzzw" placeholder="请选择行政职务">
            <el-option
              v-for="dict in xzzwOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="行政职务级别" prop="xxzwjb">
          <el-select v-model="form.xxzwjb" placeholder="请选择行政职务级别">
            <el-option
              v-for="dict in xxzwjbOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="党内职务" prop="dnzw">
          <el-select v-model="form.dnzw" placeholder="请选择党内职务">
            <el-option
              v-for="dict in dnzwOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="党内职务属性" prop="dnzwsx">
          <el-select v-model="form.dnzwsx" placeholder="请选择党内职务属性">
            <el-option
              v-for="dict in dnzwsxOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="群团职务" prop="qtzw">
          <el-select v-model="form.qtzw" placeholder="请选择群团职务">
            <el-option
              v-for="dict in qtzwOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
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
import { listGbxrzw, getGbxrzw, delGbxrzw, addGbxrzw, updateGbxrzw, exportGbxrzw } from "@/api/gbxxgl/gbxrzw";

export default {
  name: "Gbxrzw",
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
      // 干部任职情况-现任职务表格数据
      gbxrzwList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 行政职务字典
      xzzwOptions: [],
      // 行政职务级别字典
      xxzwjbOptions: [],
      // 党内职务字典
      dnzwOptions: [],
      // 党内职务属性字典
      dnzwsxOptions: [],
      // 群团职务字典
      qtzwOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gbid: null,
        rzny: null,
        xzzw: null,
        xxzwjb: null,
        dnzw: null,
        dnzwsx: null,
        qtzw: null,
        createuser: null,
        createtime: null
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
    this.getDicts("sys_dm_xrxzzw").then(response => {
      this.xzzwOptions = response.data;
    });
    this.getDicts("sys_dm_xzzwjb").then(response => {
      this.xxzwjbOptions = response.data;
    });
    this.getDicts("sys_dm_xrdnzw").then(response => {
      this.dnzwOptions = response.data;
    });
    this.getDicts("sys_dnzwsx").then(response => {
      this.dnzwsxOptions = response.data;
    });
    this.getDicts("sys_dm_xrqtzw").then(response => {
      this.qtzwOptions = response.data;
    });
  },
  methods: {
    /** 查询干部任职情况-现任职务列表 */
    getList() {
      this.loading = true;
      listGbxrzw(this.queryParams).then(response => {
        this.gbxrzwList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 行政职务字典翻译
    xzzwFormat(row, column) {
      return this.selectDictLabel(this.xzzwOptions, row.xzzw);
    },
    // 行政职务级别字典翻译
    xxzwjbFormat(row, column) {
      return this.selectDictLabel(this.xxzwjbOptions, row.xxzwjb);
    },
    // 党内职务字典翻译
    dnzwFormat(row, column) {
      return this.selectDictLabel(this.dnzwOptions, row.dnzw);
    },
    // 党内职务属性字典翻译
    dnzwsxFormat(row, column) {
      return this.selectDictLabel(this.dnzwsxOptions, row.dnzwsx);
    },
    // 群团职务字典翻译
    qtzwFormat(row, column) {
      return this.selectDictLabel(this.qtzwOptions, row.qtzw);
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
        gbid: null,
        rzny: null,
        xzzw: null,
        xxzwjb: null,
        dnzw: null,
        dnzwsx: null,
        qtzw: null,
        createuser: null,
        createtime: null
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
      this.open = true;
      this.title = "添加干部任职情况-现任职务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGbxrzw(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改干部任职情况-现任职务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGbxrzw(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addGbxrzw(this.form).then(response => {
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
      this.$confirm('是否确认删除干部任职情况-现任职务编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delGbxrzw(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有干部任职情况-现任职务数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportGbxrzw(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
