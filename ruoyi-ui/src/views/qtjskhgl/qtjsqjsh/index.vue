<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="考核方案" prop="faid">
        <el-select v-model="queryParams.faid" size="small">
          <el-option
            v-for="item in qtjskhfaOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="选择教师" prop="jsid">
        <el-input
          v-model="queryParams.jsid"
          placeholder="请输入教师"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="区级意见" prop="qjshyj">
        <el-select v-model="queryParams.qjshyj" placeholder="请选择区级审核意见" clearable size="small">
          <el-option
            v-for="dict in qjshyjOptions"
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
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['qtjskhgl:qtjskhsh:edit']"
        >审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['qtjskhgl:qtjskhsh:remove']"
        >退回</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="qtjskhshList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="考核方案" align="center" prop="faid" />
      <el-table-column label="教师" align="center" prop="jsid" />
      <el-table-column label="状态" align="center" prop="status" />
      <!-- <el-table-column label="校级审核人" align="center" prop="xjshr" /> -->
      <el-table-column label="校级审核意见" align="center" prop="xjshyj" />
      <el-table-column label="校级审核建议" align="center" prop="xjshjy" />
      <!-- <el-table-column label="区级审核人" align="center" prop="qjshr" /> -->
      <el-table-column label="区级审核意见" align="center" prop="qjshyj" />
      <el-table-column label="区级审核建议" align="center" prop="qjshjy" />
      <!-- <el-table-column label="创建人" align="center" prop="createuseird" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhsh:edit']"
          >审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhsh:remove']"
          >退回</el-button>
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

    <!-- 添加或修改群体教师考核审核过程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考核方案" prop="faid">
          <el-select v-model="form.faid" size="small" :disabled="true">
            <el-option
              v-for="item in qtjskhfaOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="教师" prop="jsid">
          <el-input v-model="jsxm" :disabled="true" />
          <el-input v-model="form.jsid" v-if="false" />
        </el-form-item>
        <el-form-item label="区级审核意见" prop="qjshyj">
          <el-select v-model="form.qjshyj" placeholder="请选择区级审核意见">
            <el-option
              v-for="dict in qjshyjOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区级审核建议" prop="qjshjy">
          <el-input v-model="form.qjshjy" type="textarea" placeholder="请输入内容" />
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
import {
  listQtjskhsh,
  getQtjskhsh,
  delQtjskhsh,
  addQtjskhsh,
  updateQtjskhsh,
} from "@/api/qtjskhgl/qtjskhsh";
import { listQtjskhfa, getQtjskhfa } from "@/api/qtjskhgl/qtjskhfa";

export default {
  name: "Qtjskhsh",
  data() {
    return {
      //教师姓名
      jsxm: "",
      //默认方案id
      defaultFaid: "",
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
      // 群体教师考核审核过程表格数据
      qtjskhshList: [],
      qtjskhfaOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      // 校级审核意见字典
      xjshyjOptions: [],
      // 区级审核意见字典
      qjshyjOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        jsid: null,
        status: "2",
        xjshr: null,
        xjshyj: null,
        xjshjy: null,
        qjshr: null,
        qjshyj: null,
        qjshjy: null,
        createuseird: null,
      },
      // 查询参数
      queryParams_fa: {
        status: "1",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        qjshyj: [
          { required: true, message: "区级审核意见不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getKhfa();
    this.getDicts("sys_dm_khshgczt").then((response) => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then((response) => {
      this.xjshyjOptions = response.data;
      this.qjshyjOptions = response.data;
    });
  },
  methods: {
    //考核方案
    async getKhfa() {
      await listQtjskhfa(this.queryParams_fa).then((response) => {
        this.qtjskhfaOptions = response.rows;
        this.defaultFaid = response.rows[0].id;
        this.queryParams.faid = this.defaultFaid;

        this.getList();
      });
    },
    /** 查询群体教师考核审核过程列表 */
    getList() {
      this.loading = true;
      listQtjskhsh(this.queryParams).then((response) => {
        this.qtjskhshList = response.rows;
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
        faid: null,
        jsid: null,
        status: "0",
        xjshr: null,
        xjshyj: null,
        xjshjy: null,
        qjshr: null,
        qjshyj: null,
        qjshjy: null,
        createuseird: null,
        createTime: null,
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
      this.queryParams.faid = this.defaultFaid;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getQtjskhsh(id).then((response) => {
        this.jsxm = response.data.tsbzJsjbxx.jsxm;
        this.form = response.data;
        this.open = true;
        this.title = "群体教师考核审核";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateQtjskhsh(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("审核成功");
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
      this.$confirm(
        '是否确认删除群体教师考核审核过程编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delQtjskhsh(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
  },
};
</script>
