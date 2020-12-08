<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="干部编号" prop="gbid">
        <el-input
          v-model="queryParams.gbid"
          placeholder="请输入干部编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作单位编号" prop="gzdwid">
        <el-input
          v-model="queryParams.gzdwid"
          placeholder="请输入工作单位编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作单位名称" prop="gzdwmc">
        <el-input
          v-model="queryParams.gzdwmc"
          placeholder="请输入工作单位名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="起始年月" prop="qsny">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.qsny"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择起始年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="终止年月" prop="zzny">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.zzny"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择终止年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="执行职务" prop="zzzw">
        <el-input
          v-model="queryParams.zzzw"
          placeholder="请输入执行职务"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="党内职务" prop="dnzw">
        <el-input
          v-model="queryParams.dnzw"
          placeholder="请输入党内职务"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="群团职务" prop="qtzw">
        <el-input
          v-model="queryParams.qtzw"
          placeholder="请输入群团职务"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任教学科" prop="rjxk">
        <el-input
          v-model="queryParams.rjxk"
          placeholder="请输入任教学科"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作岗位" prop="gzgw">
        <el-input
          v-model="queryParams.gzgw"
          placeholder="请输入工作岗位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建人" prop="createUserid">
        <el-input
          v-model="queryParams.createUserid"
          placeholder="请输入创建人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['gbxxgl:gbgzjl:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['gbxxgl:gbgzjl:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['gbxxgl:gbgzjl:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['gbxxgl:gbgzjl:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gbgzjlList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="干部编号" align="center" prop="gbid" />
      <el-table-column label="工作单位编号" align="center" prop="gzdwid" />
      <el-table-column label="工作单位名称" align="center" prop="gzdwmc" />
      <el-table-column label="起始年月" align="center" prop="qsny" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.qsny, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="终止年月" align="center" prop="zzny" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.zzny, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="执行职务" align="center" prop="zzzw" />
      <el-table-column label="党内职务" align="center" prop="dnzw" />
      <el-table-column label="群团职务" align="center" prop="qtzw" />
      <el-table-column label="任教学科" align="center" prop="rjxk" />
      <el-table-column label="工作岗位" align="center" prop="gzgw" />
      <el-table-column label="创建人" align="center" prop="createUserid" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gbxxgl:gbgzjl:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gbxxgl:gbgzjl:remove']"
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

    <!-- 添加或修改干部工作经历对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="干部编号" prop="gbid">
          <el-input v-model="form.gbid" placeholder="请输入干部编号" />
        </el-form-item>
        <el-form-item label="工作单位编号" prop="gzdwid">
          <el-input v-model="form.gzdwid" placeholder="请输入工作单位编号" />
        </el-form-item>
        <el-form-item label="工作单位名称" prop="gzdwmc">
          <el-input v-model="form.gzdwmc" placeholder="请输入工作单位名称" />
        </el-form-item>
        <el-form-item label="起始年月" prop="qsny">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.qsny"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择起始年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="终止年月" prop="zzny">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.zzny"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择终止年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="执行职务" prop="zzzw">
          <el-input v-model="form.zzzw" placeholder="请输入执行职务" />
        </el-form-item>
        <el-form-item label="党内职务" prop="dnzw">
          <el-input v-model="form.dnzw" placeholder="请输入党内职务" />
        </el-form-item>
        <el-form-item label="群团职务" prop="qtzw">
          <el-input v-model="form.qtzw" placeholder="请输入群团职务" />
        </el-form-item>
        <el-form-item label="任教学科" prop="rjxk">
          <el-input v-model="form.rjxk" placeholder="请输入任教学科" />
        </el-form-item>
        <el-form-item label="工作岗位" prop="gzgw">
          <el-input v-model="form.gzgw" placeholder="请输入工作岗位" />
        </el-form-item>
        <el-form-item label="创建人" prop="createUserid">
          <el-input v-model="form.createUserid" placeholder="请输入创建人" />
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
import { listGbgzjl, getGbgzjl, delGbgzjl, addGbgzjl, updateGbgzjl, exportGbgzjl } from "@/api/gbxxgl/gbgzjl";

export default {
  name: "Gbgzjl",
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
      // 干部工作经历表格数据
      gbgzjlList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gbid: null,
        gzdwid: null,
        gzdwmc: null,
        qsny: null,
        zzny: null,
        zzzw: null,
        dnzw: null,
        qtzw: null,
        rjxk: null,
        gzgw: null,
        createUserid: null,
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
    /** 查询干部工作经历列表 */
    getList() {
      this.loading = true;
      listGbgzjl(this.queryParams).then(response => {
        this.gbgzjlList = response.rows;
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
        gbid: null,
        gzdwid: null,
        gzdwmc: null,
        qsny: null,
        zzny: null,
        zzzw: null,
        dnzw: null,
        qtzw: null,
        rjxk: null,
        gzgw: null,
        createUserid: null,
        createTime: null
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
      this.title = "添加干部工作经历";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGbgzjl(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改干部工作经历";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGbgzjl(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addGbgzjl(this.form).then(response => {
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
      this.$confirm('是否确认删除干部工作经历编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delGbgzjl(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有干部工作经历数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportGbgzjl(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
