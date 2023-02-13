<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="在教育部认可的57项赛事内" prop="inMinistry">
        <el-input
          v-model="queryParams.inMinistry"
          placeholder="请输入在教育部认可的57项赛事内"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="在教育部认可的57项赛事中的编号" prop="ministryNo">
        <el-input
          v-model="queryParams.ministryNo"
          placeholder="请输入在教育部认可的57项赛事中的编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="竞赛等级：A~E" prop="grade">
        <el-input
          v-model="queryParams.grade"
          placeholder="请输入竞赛等级：A~E"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="竞赛频次：0每年一届、1每两年一届" prop="freq">
        <el-input
          v-model="queryParams.freq"
          placeholder="请输入竞赛频次：0每年一届、1每两年一届"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="省赛/区域赛费用" prop="fee1">
        <el-input
          v-model="queryParams.fee1"
          placeholder="请输入省赛/区域赛费用"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="国赛/决赛费用" prop="fee2">
        <el-input
          v-model="queryParams.fee2"
          placeholder="请输入国赛/决赛费用"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['contest:contestInfo:add']"
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
          v-hasPermi="['contest:contestInfo:edit']"
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
          v-hasPermi="['contest:contestInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['contest:contestInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="contestInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="竞赛ID" align="center" prop="contestId" />
      <el-table-column label="竞赛名称" align="center" prop="name" />
      <el-table-column label="竞赛官网" align="center" prop="url" />
      <el-table-column label="竞赛简介" align="center" prop="brief" />
      <el-table-column label="在教育部认可的57项赛事内" align="center" prop="inMinistry" />
      <el-table-column label="在教育部认可的57项赛事中的编号" align="center" prop="ministryNo" />
      <el-table-column label="竞赛类别：0个人、1团体、2个人与团体" align="center" prop="type" />
      <el-table-column label="竞赛等级：A~E" align="center" prop="grade" />
      <el-table-column label="竞赛频次：0每年一届、1每两年一届" align="center" prop="freq" />
      <el-table-column label="省赛/区域赛费用" align="center" prop="fee1" />
      <el-table-column label="国赛/决赛费用" align="center" prop="fee2" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['contest:contestInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['contest:contestInfo:remove']"
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

    <!-- 添加或修改竞赛基础信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="竞赛名称" prop="name">
          <el-input v-model="form.name" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="竞赛官网" prop="url">
          <el-input v-model="form.url" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="竞赛简介" prop="brief">
          <el-input v-model="form.brief" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="在教育部认可的57项赛事内" prop="inMinistry">
          <el-input v-model="form.inMinistry" placeholder="请输入在教育部认可的57项赛事内" />
        </el-form-item>
        <el-form-item label="在教育部认可的57项赛事中的编号" prop="ministryNo">
          <el-input v-model="form.ministryNo" placeholder="请输入在教育部认可的57项赛事中的编号" />
        </el-form-item>
        <el-form-item label="竞赛等级：A~E" prop="grade">
          <el-input v-model="form.grade" placeholder="请输入竞赛等级：A~E" />
        </el-form-item>
        <el-form-item label="竞赛频次：0每年一届、1每两年一届" prop="freq">
          <el-input v-model="form.freq" placeholder="请输入竞赛频次：0每年一届、1每两年一届" />
        </el-form-item>
        <el-form-item label="省赛/区域赛费用" prop="fee1">
          <el-input v-model="form.fee1" placeholder="请输入省赛/区域赛费用" />
        </el-form-item>
        <el-form-item label="国赛/决赛费用" prop="fee2">
          <el-input v-model="form.fee2" placeholder="请输入国赛/决赛费用" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
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
import { listContestInfo, getContestInfo, delContestInfo, addContestInfo, updateContestInfo } from "@/api/contest/contestInfo";

export default {
  name: "ContestInfo",
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
      // 竞赛基础信息表格数据
      contestInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        url: null,
        brief: null,
        inMinistry: null,
        ministryNo: null,
        type: null,
        grade: null,
        freq: null,
        fee1: null,
        fee2: null,
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
    /** 查询竞赛基础信息列表 */
    getList() {
      this.loading = true;
      listContestInfo(this.queryParams).then(response => {
        this.contestInfoList = response.rows;
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
        contestId: null,
        name: null,
        url: null,
        brief: null,
        inMinistry: null,
        ministryNo: null,
        type: null,
        grade: null,
        freq: null,
        fee1: null,
        fee2: null,
        remark: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
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
      this.ids = selection.map(item => item.contestId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加竞赛基础信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const contestId = row.contestId || this.ids
      getContestInfo(contestId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改竞赛基础信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.contestId != null) {
            updateContestInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addContestInfo(this.form).then(response => {
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
      const contestIds = row.contestId || this.ids;
      this.$modal.confirm('是否确认删除竞赛基础信息编号为"' + contestIds + '"的数据项？').then(function() {
        return delContestInfo(contestIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('contest/contestInfo/export', {
        ...this.queryParams
      }, `contestInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
