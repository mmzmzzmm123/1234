<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="quanName">
        <el-input
          v-model="queryParams.quanName"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="简介" prop="quanDesc">
        <el-input
          v-model="queryParams.quanDesc"
          placeholder="请输入简介"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="quanType">
        <el-select v-model="queryParams.quanType" placeholder="请选择类型" clearable size="small">
          <el-option
            v-for="dict in quanTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
     
      <el-form-item label="圈子状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择圈子状态" clearable size="small">
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
          v-hasPermi="['system:orbit:add']"
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
          v-hasPermi="['system:orbit:edit']"
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
          v-hasPermi="['system:orbit:remove']"
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
          v-hasPermi="['system:orbit:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orbitList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="圈子ID" align="center" prop="quanId" />
      <el-table-column label="名称" align="center" prop="quanName" />
      <el-table-column label="简介" align="center" prop="quanDesc" />
      <el-table-column label="类型" align="center" prop="quanType" :formatter="quanTypeFormat" />
      <el-table-column label="封面" align="center" prop="picUrl" />
      <el-table-column label="最大成员数" align="center" prop="membershipNum" />
      <el-table-column label="允许成员加入" align="center" prop="memJoinFlag" :formatter="memJoinFlagFormat" />
      <el-table-column label="可分享" align="center" prop="shareFlag" :formatter="shareFlagFormat" />
      <el-table-column label="可浏览" align="center" prop="showViewsFlag" :formatter="showViewsFlagFormat" />
      <el-table-column label="成员加入通知" align="center" prop="memJoinNotify" :formatter="memJoinNotifyFormat" />
      <el-table-column label="圈子变更发送通知" align="center" prop="descChangNotify" :formatter="descChangNotifyFormat" />
      <el-table-column label="新碎片通知" align="center" prop="fragmentNotify" :formatter="fragmentNotifyFormat" />
      <el-table-column label="碎片置顶通知" align="center" prop="fragmentTopNotify" :formatter="fragmentTopNotifyFormat" />
      <el-table-column label="圈子状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="扩展字段1" align="center" prop="ext1" />
      <el-table-column label="扩展字段2" align="center" prop="ext2" />
      <el-table-column label="扩展字段3" align="center" prop="ext3" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:orbit:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:orbit:remove']"
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

    <!-- 添加或修改圈子对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名称" prop="quanName">
          <el-input v-model="form.quanName" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="简介" prop="quanDesc">
          <el-input v-model="form.quanDesc" placeholder="请输入简介" />
        </el-form-item>
        <el-form-item label="类型" prop="quanType">
          <el-select v-model="form.quanType" placeholder="请选择类型">
            <el-option
              v-for="dict in quanTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="封面" prop="picUrl">
          <el-input v-model="form.picUrl" placeholder="请输入封面" />
        </el-form-item>
        <el-form-item label="最大成员数" prop="membershipNum">
          <el-input v-model="form.membershipNum" placeholder="请输入最大成员数" />
        </el-form-item>
        <el-form-item label="允许成员加入">
          <el-radio-group v-model="form.memJoinFlag">
            <el-radio
              v-for="dict in memJoinFlagOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="可分享">
          <el-radio-group v-model="form.shareFlag">
            <el-radio
              v-for="dict in shareFlagOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="可浏览">
          <el-radio-group v-model="form.showViewsFlag">
            <el-radio
              v-for="dict in showViewsFlagOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="成员加入通知">
          <el-radio-group v-model="form.memJoinNotify">
            <el-radio
              v-for="dict in memJoinNotifyOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="圈子变更发送通知">
          <el-radio-group v-model="form.descChangNotify">
            <el-radio
              v-for="dict in descChangNotifyOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="新碎片通知">
          <el-radio-group v-model="form.fragmentNotify">
            <el-radio
              v-for="dict in fragmentNotifyOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="碎片置顶通知">
          <el-radio-group v-model="form.fragmentTopNotify">
            <el-radio
              v-for="dict in fragmentTopNotifyOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="圈子状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="扩展字段1" prop="ext1">
          <el-input v-model="form.ext1" placeholder="请输入扩展字段1" />
        </el-form-item>
        <el-form-item label="扩展字段2" prop="ext2">
          <el-input v-model="form.ext2" placeholder="请输入扩展字段2" />
        </el-form-item>
        <el-form-item label="扩展字段3" prop="ext3">
          <el-input v-model="form.ext3" placeholder="请输入扩展字段3" />
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
import { listOrbit, getOrbit, delOrbit, addOrbit, updateOrbit, exportOrbit } from "@/api/system/orbit";

export default {
  name: "Orbit",
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
      // 圈子表格数据
      orbitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 类型字典
      quanTypeOptions: [],
      // 允许成员加入字典
      memJoinFlagOptions: [],
      // 可分享字典
      shareFlagOptions: [],
      // 可浏览字典
      showViewsFlagOptions: [],
      // 成员加入通知字典
      memJoinNotifyOptions: [],
      // 圈子变更发送通知字典
      descChangNotifyOptions: [],
      // 新碎片通知字典
      fragmentNotifyOptions: [],
      // 碎片置顶通知字典
      fragmentTopNotifyOptions: [],
      // 圈子状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        quanName: null,
        quanDesc: null,
        quanType: null,
        picUrl: null,
        membershipNum: null,
        memJoinFlag: null,
        shareFlag: null,
        showViewsFlag: null,
        memJoinNotify: null,
        descChangNotify: null,
        fragmentNotify: null,
        fragmentTopNotify: null,
        status: null,
        ext1: null,
        ext2: null,
        ext3: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        quanName: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        quanDesc: [
          { required: true, message: "简介不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("quan_type").then(response => {
      this.quanTypeOptions = response.data;
    });
    this.getDicts("flag_yes_no").then(response => {
      this.memJoinFlagOptions = response.data;
    });
    this.getDicts("flag_yes_no").then(response => {
      this.shareFlagOptions = response.data;
    });
    this.getDicts("flag_yes_no").then(response => {
      this.showViewsFlagOptions = response.data;
    });
    this.getDicts("flag_yes_no").then(response => {
      this.memJoinNotifyOptions = response.data;
    });
    this.getDicts("flag_yes_no").then(response => {
      this.descChangNotifyOptions = response.data;
    });
    this.getDicts("flag_yes_no").then(response => {
      this.fragmentNotifyOptions = response.data;
    });
    this.getDicts("flag_yes_no").then(response => {
      this.fragmentTopNotifyOptions = response.data;
    });
    this.getDicts("sys_job_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询圈子列表 */
    getList() {
      this.loading = true;
      listOrbit(this.queryParams).then(response => {
        this.orbitList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 类型字典翻译
    quanTypeFormat(row, column) {
      return this.selectDictLabel(this.quanTypeOptions, row.quanType);
    },
    // 允许成员加入字典翻译
    memJoinFlagFormat(row, column) {
      return this.selectDictLabel(this.memJoinFlagOptions, row.memJoinFlag);
    },
    // 可分享字典翻译
    shareFlagFormat(row, column) {
      return this.selectDictLabel(this.shareFlagOptions, row.shareFlag);
    },
    // 可浏览字典翻译
    showViewsFlagFormat(row, column) {
      return this.selectDictLabel(this.showViewsFlagOptions, row.showViewsFlag);
    },
    // 成员加入通知字典翻译
    memJoinNotifyFormat(row, column) {
      return this.selectDictLabel(this.memJoinNotifyOptions, row.memJoinNotify);
    },
    // 圈子变更发送通知字典翻译
    descChangNotifyFormat(row, column) {
      return this.selectDictLabel(this.descChangNotifyOptions, row.descChangNotify);
    },
    // 新碎片通知字典翻译
    fragmentNotifyFormat(row, column) {
      return this.selectDictLabel(this.fragmentNotifyOptions, row.fragmentNotify);
    },
    // 碎片置顶通知字典翻译
    fragmentTopNotifyFormat(row, column) {
      return this.selectDictLabel(this.fragmentTopNotifyOptions, row.fragmentTopNotify);
    },
    // 圈子状态字典翻译
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
        quanId: null,
        quanName: null,
        quanDesc: null,
        quanType: null,
        picUrl: null,
        membershipNum: null,
        memJoinFlag: "0",
        shareFlag: "0",
        showViewsFlag: "0",
        memJoinNotify: "0",
        descChangNotify: "0",
        fragmentNotify: "0",
        fragmentTopNotify: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        status: "0",
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
      this.ids = selection.map(item => item.quanId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加圈子";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const quanId = row.quanId || this.ids
      getOrbit(quanId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改圈子";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.quanId != null) {
            updateOrbit(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrbit(this.form).then(response => {
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
      const quanIds = row.quanId || this.ids;
      this.$confirm('是否确认删除圈子编号为"' + quanIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delOrbit(quanIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有圈子数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportOrbit(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
