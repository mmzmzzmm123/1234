<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="全景名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入全景图名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="描述信息" prop="descri">
        <el-input
          v-model="queryParams.descri"
          placeholder="请输入全景图描述信息"
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:panorama:add']"
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
          v-hasPermi="['system:panorama:edit']"
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
          v-hasPermi="['system:panorama:remove']"
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
          v-hasPermi="['system:panorama:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="panoramaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="全景图ID" align="center" prop="id" />
      <el-table-column label="全景图名称" align="center" prop="name" />
      <el-table-column label="全景图描述信息" align="center" prop="descri" />
      <el-table-column label="创建者" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新者" align="center" prop="updateBy" />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="配置json" align="center" prop="detail" />-->
<!--      <el-table-column label="组件json" align="center" prop="component" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:panorama:edit']"
          ></el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:panorama:remove']"
          ></el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-scissors"
            @click="handleUpdateDetail(scope.row)"
          ></el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleShowDetail(scope.row)"
          ></el-button>
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

    <!-- 添加或修改全景管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="全景名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入全景图名称" />
        </el-form-item>
        <el-form-item label="描述信息" prop="descri">
          <el-input v-model="form.descri" placeholder="请输入全景图描述信息" />
        </el-form-item>
<!--        <el-form-item label="配置json" prop="detail">-->
<!--          <el-input v-model="form.detail" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="组件json" prop="component">-->
<!--          <el-input v-model="form.component" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPanorama, getPanorama, delPanorama, addPanorama, updatePanorama, exportPanorama } from "@/api/system/panorama";

export default {
  name: "Panorama",
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
      // 全景管理表格数据
      panoramaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        descri: null,
        detail: null,
        component: null
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
    /** 查询全景管理列表 */
    getList() {
      this.loading = true;
      listPanorama(this.queryParams).then(response => {
        this.panoramaList = response.rows;
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
        name: null,
        descri: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        detail: null,
        component: null
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
      this.title = "添加全景管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPanorama(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改全景管理";
      });
    },
    handleUpdateDetail(row) {
      const id = row.id || this.ids;
      // let routeUrl = this.$router.resolve({
      //   // path: '/build/' + item.id
      //   path:'/editpanorama/'+id
      // })
      //
      //   window.location.replace(routeUrl.href);
      // this.$router.replace({path:'/editpanorama/'+id})
      let routeUrl = this.$router.resolve({
        path:'/editpanorama/'+id
      })
      window.open(routeUrl.href, '_blank');
    },
    handleShowDetail(row){
      const id = row.id || this.ids;
      // let routeUrl = this.$router.resolve({
      //   // path: '/build/' + item.id
      //   path:'/showpanorama/'+id
      // })
      // window.location.replace(routeUrl.href);
      // this.$router.replace({path:'/showpanorama/'+id})
      let routeUrl = this.$router.resolve({
        path:'/showpanorama/'+id
      })
      window.open(routeUrl.href, '_blank');
    },
    /** 提交按钮 */
    ran(m) {
        m = m > 13 ? 13 : m;
        var num = new Date().getTime();
        return num.toString().substring(13 - m);
      },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePanorama(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.id=this.ran(13);
            console.log(this.form.id);
            addPanorama(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除全景管理编号为"' + ids + '"的数据项？').then(function() {
        return delPanorama(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal.confirm('是否确认导出所有全景管理数据项？').then(() => {
        this.exportLoading = true;
        return exportPanorama(queryParams);
      }).then(response => {
        this.$download.name(response.msg);
        this.exportLoading = false;
      }).catch(() => {});
    }
  }
};
</script>
