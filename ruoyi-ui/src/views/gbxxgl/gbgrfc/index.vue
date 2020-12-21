<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="干部ID" prop="gbid">
        <el-input
          v-model="queryParams.gbid"
          placeholder="请输入干部ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件照名称" prop="zjzmc">
        <el-input
          v-model="queryParams.zjzmc"
          placeholder="请输入证件照名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生活照名称" prop="shzmc">
        <el-input
          v-model="queryParams.shzmc"
          placeholder="请输入生活照名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作照名称" prop="zgzmc">
        <el-input
          v-model="queryParams.zgzmc"
          placeholder="请输入工作照名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否删除" prop="isdel">
        <el-input
          v-model="queryParams.isdel"
          placeholder="请输入是否删除"
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
          v-hasPermi="['gbxxgl:gbgrfc:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['gbxxgl:gbgrfc:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['gbxxgl:gbgrfc:remove']"
        >删除</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gbgrfcList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="干部ID" align="center" prop="gbid" />
      <el-table-column label="证件照" align="center" prop="zjzpath" />
      <el-table-column label="证件照名称" align="center" prop="zjzmc" />
      <el-table-column label="生活照" align="center" prop="shzpath" />
      <el-table-column label="生活照名称" align="center" prop="shzmc" />
      <el-table-column label="工作照" align="center" prop="gzzpath" />
      <el-table-column label="工作照名称" align="center" prop="zgzmc" />
      <el-table-column label="是否删除" align="center" prop="isdel" />
      <el-table-column label="创建人" align="center" prop="createUserid" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gbxxgl:gbgrfc:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gbxxgl:gbgrfc:remove']"
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

    <!-- 添加或修改个人风采(干部管理-个人状况)对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="干部ID" prop="gbid">
          <el-input v-model="form.gbid" placeholder="请输入干部ID" />
        </el-form-item>
        <el-form-item label="证件照" prop="zjzpath">
          <el-input v-model="form.zjzpath" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="证件照名称" prop="zjzmc">
          <el-input v-model="form.zjzmc" placeholder="请输入证件照名称" />
        </el-form-item>
        <el-form-item label="生活照" prop="shzpath">
          <el-input v-model="form.shzpath" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="生活照名称" prop="shzmc">
          <el-input v-model="form.shzmc" placeholder="请输入生活照名称" />
        </el-form-item>
        <el-form-item label="工作照" prop="gzzpath">
          <el-input v-model="form.gzzpath" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="工作照名称" prop="zgzmc">
          <el-input v-model="form.zgzmc" placeholder="请输入工作照名称" />
        </el-form-item>
        <el-form-item label="是否删除" prop="isdel">
          <el-input v-model="form.isdel" placeholder="请输入是否删除" />
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
import { listGbgrfc, getGbgrfc, delGbgrfc, addGbgrfc, updateGbgrfc } from "@/api/gbxxgl/gbgrfc";

export default {
  name: "Gbgrfc",
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
      // 个人风采(干部管理-个人状况)表格数据
      gbgrfcList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gbid: null,
        zjzpath: null,
        zjzmc: null,
        shzpath: null,
        shzmc: null,
        gzzpath: null,
        zgzmc: null,
        isdel: null,
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
    /** 查询个人风采(干部管理-个人状况)列表 */
    getList() {
      this.loading = true;
      listGbgrfc(this.queryParams).then(response => {
        this.gbgrfcList = response.rows;
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
        zjzpath: null,
        zjzmc: null,
        shzpath: null,
        shzmc: null,
        gzzpath: null,
        zgzmc: null,
        isdel: null,
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
      this.title = "添加个人风采(干部管理-个人状况)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGbgrfc(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改个人风采(干部管理-个人状况)";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGbgrfc(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addGbgrfc(this.form).then(response => {
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
      this.$confirm('是否确认删除个人风采(干部管理-个人状况)编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delGbgrfc(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
  }
};
</script>
