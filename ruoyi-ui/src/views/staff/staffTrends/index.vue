<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户标识" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户标识"
          clearable
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
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['staff:staffTrends:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['staff:staffTrends:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="staffTrendsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="文本" align="center" prop="content" :show-overflow-tooltip="true"/>
      <el-table-column label="图片集合" align="center" prop="imgList" :show-overflow-tooltip="true"/>
      <el-table-column label="视频地址" align="center" prop="videoUrl" :show-overflow-tooltip="true"/>
      <el-table-column label="语音地址" align="center" prop="voiceUrl" :show-overflow-tooltip="true"/>
      <el-table-column label="语言时长" align="center" prop="voiceTime" :show-overflow-tooltip="true"/>
      <el-table-column label="点赞数" align="center" prop="likeNum" :show-overflow-tooltip="true"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['staff:staffTrends:remove']"
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

    <!-- 添加或修改员工动态对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户标识" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户标识" />
        </el-form-item>
        <el-form-item label="文本">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
        <el-form-item label="图片集合" prop="imgList">
          <el-input v-model="form.imgList" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="视频地址" prop="videoUrl">
          <el-input v-model="form.videoUrl" placeholder="请输入视频地址" />
        </el-form-item>
        <el-form-item label="语音地址" prop="voiceUrl">
          <el-input v-model="form.voiceUrl" placeholder="请输入语音地址" />
        </el-form-item>
        <el-form-item label="语言时长" prop="voiceTime">
          <el-input v-model="form.voiceTime" placeholder="请输入语言时长" />
        </el-form-item>
        <el-form-item label="点赞数" prop="likeNum">
          <el-input v-model="form.likeNum" placeholder="请输入点赞数" />
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
import { listStaffTrends, getStaffTrends, delStaffTrends, addStaffTrends, updateStaffTrends } from "@/api/staff/staffTrends";

export default {
  name: "StaffTrends",
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
      // 员工动态表格数据
      staffTrendsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        content: null,
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
    /** 查询员工动态列表 */
    getList() {
      this.loading = true;
      listStaffTrends(this.queryParams).then(response => {
        this.staffTrendsList = response.rows;
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
        userId: null,
        content: null,
        imgList: null,
        videoUrl: null,
        voiceUrl: null,
        voiceTime: null,
        likeNum: null,
        createTime: null,
        updateTime: null
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
      this.title = "添加员工动态";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStaffTrends(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工动态";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStaffTrends(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStaffTrends(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除员工动态编号为"' + ids + '"的数据项？').then(function() {
        return delStaffTrends(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('staff/staffTrends/export', {
        ...this.queryParams
      }, `staffTrends_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
