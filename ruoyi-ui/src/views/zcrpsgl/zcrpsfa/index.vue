<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="方案名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入方案名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="方案类型" prop="jdtype">
        <el-select v-model="queryParams.jdtype" placeholder="请选择方案类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
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
          v-hasPermi="['zcrpsgl:zcrpsfa:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['zcrpsgl:zcrpsfa:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['zcrpsgl:zcrpsfa:remove']"
        >删除</el-button>
      </el-col>

	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="zcrpsfaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="方案名称" align="center" prop="name" />
      <el-table-column label="基地类型" align="center" prop="jdtype" />
      <el-table-column label="当前状态" align="center" prop="status" :formatter="statusFormat"/>
      <el-table-column label="报名时间" align="center" prop="starttime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.starttime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleOpenStatus(scope.row)"
            v-hasPermi="['zcrpsgl:zcrpsfa:openstatus']"
          >启用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleStopStatus(scope.row)"
            v-hasPermi="['zcrpsgl:zcrpsfa:stopstatus']"
          >停止</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['zcrpsgl:zcrpsfa:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['zcrpsgl:zcrpsfa:remove']"
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

    <!-- 添加或修改评审方案(主持人评审管理-评审方案)对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="方案名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入方案名称" />
        </el-form-item>
        <el-form-item label="当前状态">
          <el-select v-model="form.status" placeholder="请选择当前状态">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="基地类型" prop="jdtype">
          <el-select v-model="form.jdtype" placeholder="请选择基地类型">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="报名时间" prop="starttime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.starttime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择报名时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endtime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.endtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间">
          </el-date-picker>
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
import { listZcrpsfa, getZcrpsfa, delZcrpsfa, addZcrpsfa, updateZcrpsfa, exportZcrpsfa,openStatusZcrpsfa,stopStatusZcrpsfa } from "@/api/zcrpsgl/zcrpsfa";

export default {
  name: "Zcrpsfa",
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
      // 评审方案(主持人评审管理-评审方案)表格数据
      zcrpsfaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        status: null,
        jdtype: null,
        starttime: null,
        endtime: null,
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
    this.getDicts("sys_dm_zcrfadqzt").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询评审方案(主持人评审管理-评审方案)列表 */
    getList() {
      this.loading = true;
      listZcrpsfa(this.queryParams).then(response => {
        this.zcrpsfaList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
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
        name: null,
        status: "1",
        jdtype: null,
        starttime: null,
        endtime: null,
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
      this.title = "添加评审方案(主持人评审管理-评审方案)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getZcrpsfa(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改评审方案(主持人评审管理-评审方案)";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateZcrpsfa(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addZcrpsfa(this.form).then(response => {
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
      this.$confirm('是否确认删除评审方案(主持人评审管理-评审方案)编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delZcrpsfa(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },

    handleOpenStatus(row) {
      const id = row.id;
      openStatusZcrpsfa(id).then(response => {
        if (response.code === 200) {
          this.msgSuccess("启用成功");
          this.open = false;
          this.getList();
        }
      });
    },

    handleStopStatus(row) {
      const id = row.id;
      stopStatusZcrpsfa(id).then(response => {
        if (response.code === 200) {
          this.msgSuccess("停止成功");
          this.open = false;
          this.getList();
        }
      });
    }

  }
};
</script>
