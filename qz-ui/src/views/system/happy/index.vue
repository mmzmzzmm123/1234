<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="发布时间" prop="releaseTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.releaseTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择发布时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="计划时间" prop="planReleaseTime">
        <el-date-picker clearable size="small"
          v-model="queryParams.planReleaseTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择计划发布时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="是否投稿" prop="distributeFlag">
        <el-select v-model="queryParams.distributeFlag" placeholder="请选择是否投稿" clearable size="small">
          <el-option
            v-for="dict in distributeFlagOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:happy:add']"
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
          v-hasPermi="['system:happy:edit']"
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
          v-hasPermi="['system:happy:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:happy:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="happyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
     <!-- <el-table-column label="圈子ID" align="center" prop="happyId" /> -->
      <el-table-column label="快乐+1" align="center" prop="happyContent" width="360"/>
      <el-table-column label="发布时间" align="center" prop="releaseTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.releaseTime, '{y}-{m}-{d} {h}:{m}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划发布时间" align="center" prop="planReleaseTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.planReleaseTime, '{y}-{m}-{d} {h}:{m}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否投稿" align="center" prop="distributeFlag" :formatter="distributeFlagFormat" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="审批人" align="center" prop="auditBy" />
      <el-table-column label="审批时间" align="center" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
	  <el-table-column label="署名" align="center" prop="signature"  width="180"/>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改快乐+1对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="快乐+1">
          <editor v-model="form.happyContent" :min-height="192"/>
        </el-form-item>
       <!-- <el-form-item label="发布时间" prop="releaseTime">
          <el-date-picker clearable size="small"
            v-model="form.releaseTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择发布时间">
          </el-date-picker>
        </el-form-item> -->
        <el-form-item label="定时推送" prop="planReleaseTime">
          <el-date-picker clearable size="small"
            v-model="form.planReleaseTime"
            type="datetime"
            value-format="yyyy-MM-dd hh:MM:ss"
            placeholder="选择计划发布时间">
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="是否投稿" prop="distributeFlag">
          <el-select v-model="form.distributeFlag" placeholder="请选择是否投稿">
            <el-option
              v-for="dict in distributeFlagOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item> -->
       <!-- <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item> -->
       <el-form-item label="署名" prop="signature">
          <el-input v-model="form.signature" placeholder="请输入署名" />
        </el-form-item>
       <!--  <el-form-item label="审批时间" prop="auditTime">
          <el-date-picker clearable size="small"
            v-model="form.auditTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择审批时间">
          </el-date-picker>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listHappy, getHappy, delHappy, addHappy, updateHappy, exportHappy } from "@/api/system/happy";

export default {
  name: "Happy",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
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
      // 快乐+1表格数据
      happyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否投稿字典
      distributeFlagOptions: [],
      // 状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        releaseTime: null,
        planReleaseTime: null,
        distributeFlag: null,
        status: null,
        auditBy: null,
        auditTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        happyContent: [
          { required: true, message: "快乐+1不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("flag_yes_no").then(response => {
      this.distributeFlagOptions = response.data;
    });
    this.getDicts("happy_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询快乐+1列表 */
    getList() {
      this.loading = true;
      listHappy(this.queryParams).then(response => {
        this.happyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 是否投稿字典翻译
    distributeFlagFormat(row, column) {
      return this.selectDictLabel(this.distributeFlagOptions, row.distributeFlag);
    },
    // 状态字典翻译
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
        happyId: null,
        happyContent: null,
        releaseTime: null,
        planReleaseTime: null,
        distributeFlag: null,
        status: null,
        auditBy: null,
        auditTime: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        signature: null,
        remark: null,
        ext1: null,
        ext2: null,
        ext3: null
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
      this.ids = selection.map(item => item.happyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加快乐+1";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const happyId = row.happyId || this.ids
      getHappy(happyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改快乐+1";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.happyId != null) {
            updateHappy(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
			  this.form.releaseTime=this.form.planReleaseTime;
			  //表单状态为待推送
			  this.form.status='4';
			  this.form.distributeFlag='1';
            addHappy(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const happyIds = row.happyId || this.ids;
      this.$confirm('是否确认删除快乐+1编号为"' + happyIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delHappy(happyIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有快乐+1数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportHappy(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
