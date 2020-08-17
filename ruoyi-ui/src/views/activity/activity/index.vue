<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="活动名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入活动名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
      <el-form-item label="活动开始时间" prop="gmtBegin">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.gmtBegin"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择活动开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="活动结束时间" prop="gmtEnd">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.gmtEnd"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择活动结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="比赛时间" prop="gmtGame">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.gmtGame"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择比赛时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="备注" prop="content">
        <el-input
          v-model="queryParams.content"
          placeholder="请输入备注"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合同编号" prop="contractId">
        <el-input
          v-model="queryParams.contractId"
          placeholder="请输入合同编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="人脸库编号" prop="facesetId">
        <el-input
          v-model="queryParams.facesetId"
          placeholder="请输入人脸库编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动封面图" prop="avatar">
        <el-input
          v-model="queryParams.avatar"
          placeholder="请输入活动封面图"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动类型" prop="activityType">
        <el-select v-model="queryParams.activityType" placeholder="请选择活动类型" clearable size="small">
          <el-option
            v-for="dict in activityTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="转码模版id" prop="transcodeTemplateGropId">
        <el-input
          v-model="queryParams.transcodeTemplateGropId"
          placeholder="请输入转码模版id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="引擎id" prop="engineId">
        <el-input
          v-model="queryParams.engineId"
          placeholder="请输入引擎id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="引擎id" prop="genre">
        <el-input
          v-model="queryParams.genre"
          placeholder="请输入引擎id"
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
          v-hasPermi="['activity:activity:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['activity:activity:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['activity:activity:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['activity:activity:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="activityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="引擎id" align="center" prop="id" />
      <el-table-column label="活动名称" align="center" prop="name" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="活动开始时间" align="center" prop="gmtBegin" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.gmtBegin, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动结束时间" align="center" prop="gmtEnd" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.gmtEnd, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="比赛时间" align="center" prop="gmtGame" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.gmtGame, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动类型" align="center" prop="activityType" :formatter="activityTypeFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['activity:activity:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['activity:activity:remove']"
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

    <!-- 添加或修改活动列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="活动id" prop="activityId">
          <el-input v-model="form.activityId" placeholder="请输入活动id" />
        </el-form-item>
        <el-form-item label="活动名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="活动开始时间" prop="gmtBegin">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.gmtBegin"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择活动开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="活动结束时间" prop="gmtEnd">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.gmtEnd"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择活动结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="比赛时间" prop="gmtGame">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.gmtGame"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择比赛时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="content">
          <el-input v-model="form.content" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="合同编号" prop="contractId">
          <el-input v-model="form.contractId" placeholder="请输入合同编号" />
        </el-form-item>
        <el-form-item label="人脸库编号" prop="facesetId">
          <el-input v-model="form.facesetId" placeholder="请输入人脸库编号" />
        </el-form-item>
        <el-form-item label="活动封面图" prop="avatar">
          <el-input v-model="form.avatar" placeholder="请输入活动封面图" />
        </el-form-item>
        <el-form-item label="活动类型">
          <el-select v-model="form.activityType" placeholder="请选择活动类型">
            <el-option
              v-for="dict in activityTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="转码模版id" prop="transcodeTemplateGropId">
          <el-input v-model="form.transcodeTemplateGropId" placeholder="请输入转码模版id" />
        </el-form-item>
        <el-form-item label="引擎id" prop="engineId">
          <el-input v-model="form.engineId" placeholder="请输入引擎id" />
        </el-form-item>
        <el-form-item label="引擎id" prop="genre">
          <el-input v-model="form.genre" placeholder="请输入引擎id" />
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
import { listActivity, getActivity, delActivity, addActivity, updateActivity, exportActivity } from "@/api/activity/activity";

export default {
  name: "Activity",
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
      // 活动列表表格数据
      activityList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      // 活动类型字典
      activityTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        status: undefined,
        gmtBegin: undefined,
        gmtEnd: undefined,
        gmtGame: undefined,
        content: undefined,
        contractId: undefined,
        facesetId: undefined,
        avatar: undefined,
        activityType: undefined,
        transcodeTemplateGropId: undefined,
        engineId: undefined,
        genre: undefined
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
    this.getDicts("org_activity_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("org_activity_type").then(response => {
      this.activityTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询活动列表列表 */
    getList() {
      this.loading = true;
      listActivity(this.queryParams).then(response => {
        this.activityList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 活动类型字典翻译
    activityTypeFormat(row, column) {
      return this.selectDictLabel(this.activityTypeOptions, row.activityType);
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
        activityId: undefined,
        name: undefined,
        status: "0",
        gmtBegin: undefined,
        gmtEnd: undefined,
        gmtGame: undefined,
        content: undefined,
        contractId: undefined,
        facesetId: undefined,
        isWatermark: "0",
        watermarkId: undefined,
        avatar: undefined,
        orgId: undefined,
        gmtModified: undefined,
        creater: undefined,
        modifier: undefined,
        gmtCreate: undefined,
        gmtDelete: undefined,
        deleter: undefined,
        isDel: undefined,
        activityType: undefined,
        transcodeTemplateGropId: undefined,
        engineId: undefined,
        genre: undefined
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
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加活动列表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getActivity(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改活动列表";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
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
      this.$confirm('是否确认删除活动列表编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delActivity(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有活动列表数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportActivity(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
