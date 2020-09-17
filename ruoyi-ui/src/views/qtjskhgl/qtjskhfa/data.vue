<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="102px">
      <el-form-item label="所属考核方案" prop="faid">
        <el-input
          v-model="queryParams.faid"
          placeholder="请输入所属考核方案"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核模块" prop="khmk">
        <el-select v-model="queryParams.khmk" placeholder="请选择考核模块" clearable size="small">
          <el-option
            v-for="dict in khmkOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考核内容" prop="khnr">
        <el-input
          v-model="queryParams.khnr"
          placeholder="请输入考核内容"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核类型" prop="khlx">
        <el-select v-model="queryParams.khlx" placeholder="请选择考核类型" clearable size="small">
          <el-option
            v-for="dict in khlxOptions"
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
          v-hasPermi="['qtjskhgl:qtjskhzbx:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['qtjskhgl:qtjskhzbx:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['qtjskhgl:qtjskhzbx:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['qtjskhgl:qtjskhzbx:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="qtjskhzbxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="所属考核方案" align="center" prop="faid" />
      <el-table-column label="考核模块" align="center" prop="khmk" :formatter="khmkFormat" />
      <el-table-column label="考核内容" align="center" prop="khnr" />
      <el-table-column label="提交数量" align="center" prop="tjsl" />
      <el-table-column label="关键字段" align="center" prop="gjzd" />
      <el-table-column label="同步来源" align="center" prop="tbly" :formatter="tblyFormat" />
      <el-table-column label="接口标记预留" align="center" prop="jkbj" />
      <el-table-column label="考核类型" align="center" prop="khlx" :formatter="khlxFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhzbx:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhzbx:remove']"
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

    <!-- 添加或修改群体教师考核指标项对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="所属考核方案" prop="faid">
          <el-input v-model="form.faid" placeholder="请输入所属考核方案" />
        </el-form-item>
        <el-form-item label="考核模块
            字典项" prop="khmk">
          <el-select v-model="form.khmk" placeholder="请选择考核模块
            字典项">
            <el-option
              v-for="dict in khmkOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考核内容" prop="khnr">
          <el-input v-model="form.khnr" placeholder="请输入考核内容" />
        </el-form-item>
        <el-form-item label="提交数量" prop="tjsl">
          <el-input v-model="form.tjsl" placeholder="请输入提交数量" />
        </el-form-item>
        <el-form-item label="关键字段" prop="gjzd">
          <el-input v-model="form.gjzd" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="同步来源" prop="tbly">
          <el-select v-model="form.tbly" placeholder="请选择同步来源">
            <el-option
              v-for="dict in tblyOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接口标记预留" prop="jkbj">
          <el-input v-model="form.jkbj" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="执行开始时间" prop="starttime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.starttime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择执行开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="执行截止时间" prop="endtime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.endtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择执行截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="执行任务预留" prop="zxrw">
          <el-input v-model="form.zxrw" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="考核类型" prop="khlx">
          <el-select v-model="form.khlx" placeholder="请选择考核类型：必做;二选一;选择性项目">
            <el-option
              v-for="dict in khlxOptions"
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
import { listQtjskhzbx, getQtjskhzbx, delQtjskhzbx, addQtjskhzbx, updateQtjskhzbx, exportQtjskhzbx } from "@/api/qtjskhgl/qtjskhzbx";

export default {
  name: "Qtjskhzbx",
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
      // 群体教师考核指标项表格数据
      qtjskhzbxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 考核模块
      khmkOptions: [],
      // 同步来源
      tblyOptions: [],
      // 考核类型：必做;二选一;选择性项目字典
      khlxOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        khmk: null,
        khnr: null,
        tjsl: null,
        gjzd: null,
        tbly: null,
        jkbj: null,
        starttime: null,
        endtime: null,
        zxrw: null,
        createuserid: null,
        yly: null,
        yle: null,
        khlx: null
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
    this.getDicts("sys_dm_khmk").then(response => {
      this.khmkOptions = response.data;
    });
    this.getDicts("sys_dm_tbly").then(response => {
      this.tblyOptions = response.data;
    });
    this.getDicts("sys_dm_ggjskhzblx").then(response => {
      this.khlxOptions = response.data;
    });
  },
  methods: {
    /** 查询群体教师考核指标项列表 */
    getList() {
      this.loading = true;
      listQtjskhzbx(this.queryParams).then(response => {
        this.qtjskhzbxList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 考核模块
    khmkFormat(row, column) {
      return this.selectDictLabel(this.khmkOptions, row.khmk);
    },
    // 同步来源
    tblyFormat(row, column) {
      return this.selectDictLabel(this.tblyOptions, row.tbly);
    },
    // 考核类型：必做;二选一;选择性项目字典翻译
    khlxFormat(row, column) {
      return this.selectDictLabel(this.khlxOptions, row.khlx);
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
        faid: null,
        khmk: null,
        khnr: null,
        tjsl: null,
        gjzd: null,
        tbly: null,
        jkbj: null,
        starttime: null,
        endtime: null,
        zxrw: null,
        createuserid: null,
        createTime: null,
        yly: null,
        yle: null,
        khlx: null
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
      this.title = "添加群体教师考核指标项";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getQtjskhzbx(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改群体教师考核指标项";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateQtjskhzbx(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addQtjskhzbx(this.form).then(response => {
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
      this.$confirm('是否确认删除群体教师考核指标项编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delQtjskhzbx(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有群体教师考核指标项数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportQtjskhzbx(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
