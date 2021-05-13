<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="基地编号" prop="jdid">
        <el-input
          v-model="queryParams.jdid"
          placeholder="请输入基地编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item label="活动类型" prop="hdtype">
        <el-select v-model="queryParams.hdtype" placeholder="请选择活动类型" clearable size="small">
          <el-option
            v-for="dict in hdtypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动时间" prop="hdtime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.hdtime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择活动时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="活动地点" prop="hddd">
        <el-input
          v-model="queryParams.hddd"
          placeholder="请输入活动地点"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上传路径" prop="scpath">
        <el-input
          v-model="queryParams.scpath"
          placeholder="请输入上传路径"
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
          v-hasPermi="['jdgl:jdglhdgl:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jdgl:jdglhdgl:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jdgl:jdglhdgl:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jdgl:jdglhdgl:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jdglhdglList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="基地编号" align="center" prop="jdid" />
      <el-table-column label="活动名称" align="center" prop="name" />
      <el-table-column label="活动类型" align="center" prop="hdtype" :formatter="hdtypeFormat" />
      <el-table-column label="活动时间" align="center" prop="hdtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.hdtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动地点" align="center" prop="hddd" :formatter="hdddFormat" />
      <el-table-column label="上传路径" align="center" prop="scpath" />
      <el-table-column label="创建人" align="center" prop="createUserid" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jdgl:jdglhdgl:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jdgl:jdglhdgl:remove']"
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

    <!-- 添加或修改基地管理活动管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="基地编号" prop="jdid">
          <el-input v-model="form.jdid" placeholder="请输入基地编号" />
        </el-form-item>
        <el-form-item label="活动名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="活动类型" prop="hdtype">
          <el-select v-model="form.hdtype" placeholder="请选择活动类型">
            <el-option
              v-for="dict in hdtypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="活动时间" prop="hdtime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.hdtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择活动时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="活动地点" prop="hddd">
          <el-input v-model="form.hddd" placeholder="请输入活动地点" />
        </el-form-item>
        <el-form-item label="上传路径" prop="scpath">
          <el-input v-model="form.scpath" placeholder="请输入上传路径" />
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
import { listJdglhdgl, getJdglhdgl, delJdglhdgl, addJdglhdgl, updateJdglhdgl, exportJdglhdgl } from "@/api/jdgl/jdglhdgl";

export default {
  name: "Jdglhdgl",
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
      // 基地管理活动管理表格数据
      jdglhdglList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 活动类型字典
      hdtypeOptions: [],
      // 活动地点字典
      hdddOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jdid: null,
        name: null,
        hdtype: null,
        hdtime: null,
        hddd: null,
        scpath: null,
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
    this.getDicts("sys_dm_hdglhdlx").then(response => {
      this.hdtypeOptions = response.data;
    });
    this.getDicts("sys_dm_hdglhddd").then(response => {
      this.hdddOptions = response.data;
    });
  },
  methods: {
    /** 查询基地管理活动管理列表 */
    getList() {
      this.loading = true;
      listJdglhdgl(this.queryParams).then(response => {
        this.jdglhdglList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 活动类型字典翻译
    hdtypeFormat(row, column) {
      return this.selectDictLabel(this.hdtypeOptions, row.hdtype);
    },
    // 活动地点字典翻译
    hdddFormat(row, column) {
      return this.selectDictLabel(this.hdddOptions, row.hddd);
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
        jdid: null,
        name: null,
        hdtype: null,
        hdtime: null,
        hddd: null,
        scpath: null,
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
      this.title = "添加基地管理活动管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getJdglhdgl(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基地管理活动管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateJdglhdgl(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJdglhdgl(this.form).then(response => {
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
      this.$confirm('是否确认删除基地管理活动管理编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delJdglhdgl(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有基地管理活动管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportJdglhdgl(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
