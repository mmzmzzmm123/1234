<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-upload
          ref="upload"
          accept=".jpg,.png"
          :headers="upload.headers"
          :action="upload.url"
          :on-success="handleFileSuccess"
          auto-upload
          multiple
          :file-list="fileList">
          <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              v-hasPermi="['system:indexImg:add']"
            >新增</el-button>
        </el-upload>
        
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:indexImg:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="indexImgList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" width="50"/>
      <el-table-column label="图片" align="center" prop="imgPath">
        <template slot-scope="scope">
          <image-preview :src=" scope.row.imgPath" :width="300" :height="100"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:indexImg:remove']"
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

  </div>
</template>

<script>
import { listIndexImg, getIndexImg, delIndexImg, addIndexImg, updateIndexImg } from "@/api/system/indexImg";
import { getToken } from "@/utils/auth";

export default {
  name: "IndexImg",
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
      // 首页图片配置表格数据
      indexImgList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        imgName: null,
        imgPath: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/indexImg"
      },
      fileList:[]
    };
  },
  created() {
    console.log(this.upload.url)
    this.getList();
  },
  methods: {
    /** 查询首页图片配置列表 */
    getList() {
      this.loading = true;
      listIndexImg(this.queryParams).then(response => {
        this.indexImgList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
     /** 删除按钮操作 */
    handleDelete(row) {
      const dataId = row.id || this.ids;
      this.$modal.confirm('是否确认删除用户编号为"' + dataId + '"的数据项？').then(function() {
        return delIndexImg(dataId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.getList();
    },
  }
};
</script>
