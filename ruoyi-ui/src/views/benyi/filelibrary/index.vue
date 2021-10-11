<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="文件名称" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入文件名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="所属类型" prop="type">
            <el-select
              v-model="queryParams.type"
              placeholder="请选择文件所属类型"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in typeOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索</el-button
            >
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置</el-button
            >
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-table v-loading="loading" border :data="filesList">
      <el-table-column label="文件名称" fixed align="center" prop="name" />
      <el-table-column
        label="文件所属类型"
        align="center"
        prop="type"
        :formatter="typeFormat"
      />
      <el-table-column
        label="操作"
        align="center"
        fixed="right"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            >预览</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-download"
            @click="handleDown(scope.row)"
            >下载</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listFilesByRole, getFiles } from "@/api/benyi/files";

import { getToken } from "@/utils/auth";

export default {
  name: "FileLibrary",
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
      // 文件管理表格数据
      filesList: [],
      fileList: [],
      apiurl: process.env.VUE_APP_BASE_API,
      //文件类型
      typeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        filetype: undefined,
        type: undefined,
        fileurl: undefined,
        createuserid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_filecenter").then((response) => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    // 类型--字典状态字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    /** 查询文件管理列表 */
    getList() {
      this.loading = true;
      listFilesByRole(this.queryParams).then((response) => {
        this.filesList = response.rows;
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
        id: undefined,
        name: undefined,
        filetype: undefined,
        type: undefined,
        fileurl: undefined,
        createuserid: undefined,
        createTime: undefined,
      };
      this.resetForm("form");
      this.fileList = [];
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
    //下载
    handleDown(row) {
      var url = row.fileurl;
      //window.open(this.apiurl + url);
      this.download(row.name,row.fileurl);
    },
    //预览
    handleView(row) {
      var url = row.fileurl;
      window.open(
        "https://view.officeapps.live.com/op/view.aspx?src=http://system.benyiedu.com" +
          this.apiurl +
          url,
        "_blank"
      );
    },
  },
};
</script>
<style lang="scss" scoped>
.el-select {
  width: 100%;
}
.no-margin ::v-deep.el-form-item__content {
  margin: 0 !important;
}
</style>