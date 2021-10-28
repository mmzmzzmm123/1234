<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label-width=100px label="原始文件名" prop="originName">
        <el-input v-model="queryParams.originName" placeholder="请输入原始文件名" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label-width=100px label="新文件名" prop="newName">
        <el-input v-model="queryParams.newName" placeholder="请输入新文件名" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="切割宽度" prop="width">
        <el-input v-model="queryParams.width" placeholder="请输入切割宽度" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="高度切割" prop="height">
        <el-input v-model="queryParams.height" placeholder="请输入切割高度" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:visualImage:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['system:visualImage:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:visualImage:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" :loading="exportLoading"
          @click="handleExport" v-hasPermi="['system:visualImage:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="visualImageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="原始文件名" align="center" prop="originName" />
      <el-table-column label="新文件名" align="center" prop="newName" />
      <el-table-column label="切割宽高" align="center">
        <template slot-scope="scope">
          <span >{{ scope.row.width + " × " +  scope.row.height}}</span>
        </template>
      </el-table-column>


      <!-- <el-table-column label="切割高度" align="center" prop="height" /> -->
      <el-table-column label="描述信息" align="center" prop="descri" />
      <el-table-column label="创建者" align="center" prop="createBy" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新者" align="center" prop="updateBy" />
      <el-table-column label="更新者" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:visualImage:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:visualImage:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改图片管理。管理上传的图片对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="原始文件名" prop="originName">
          <el-input v-model="form.originName" placeholder="请输入原始文件名" />
        </el-form-item>
        <el-form-item label="新生成的文件名" prop="newName">
          <el-input v-model="form.newName" placeholder="请输入新生成的文件名" />
        </el-form-item> -->
        <el-upload class="avatar-uploader" action="http://localhost:8080/system/visualImage/upload"
          :before-upload="beforeUpload" :show-file-list="false" :on-success="handleAvatarSuccess">
          <img v-if="imageUrl" :src="imageUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
        <el-form-item label="宽度" prop="width">
          <el-input v-model="form.width" placeholder="请输入切割宽度" />
        </el-form-item>
        <el-form-item label="高度" prop="height">
          <el-input v-model="form.height" placeholder="请输入切割高度" />
        </el-form-item>
        <el-form-item label="描述信息" prop="descri">
          <el-input v-model="form.descri" placeholder="请输入描述信息" />
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
    listVisualImage,
    getVisualImage,
    delVisualImage,
    addVisualImage,
    updateVisualImage,
    exportVisualImage
  } from "@/api/system/visualImage";
  // import { getToken } from '@/utils/auth'

  export default {
    name: "VisualImage",
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
        // 图片管理。管理上传的图片表格数据
        visualImageList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          originName: null,
          newName: null,
          width: null,
          height: null,
        },
        // 表单参数
        form: {},
        imageUrl:null,
        uploadHeader: {},
        // 表单校验
        rules: {}
      };
    },
    created() {
      this.getList();
    },
    mounted() {
      // this.uploadHeader['Content-Type'] = 'multipart/form-data'
      // this.uploadHeader['Authorization'] = 'Bearer ' + getToken()
      // console.log(this.uploadHeader);
    },
    methods: {
      handleAvatarSuccess(res, file) {
        console.log(res, file);
        this.form.originName = file.name
        this.form.newName = res.data.name
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeUpload(file) {
        //
        this.form.newName = null
        return true;
      },
      /** 查询图片管理。管理上传的图片列表 */
      getList() {
        this.loading = true;
        listVisualImage(this.queryParams).then(response => {
          this.visualImageList = response.rows;
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
          originName: null,
          newName: null,
          width: null,
          height: null,
          descri: null,
          delFlag: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,

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
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.imageUrl = null
        this.open = true;
        this.title = "添加图片";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        this.imageUrl = null
        const id = row.id || this.ids
        getVisualImage(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改图片";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateVisualImage(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              if (this.form.newName == null) {
                this.$modal.msgSuccess("请等待图片上传完成");
                return;
              }
              addVisualImage(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除图片管理。管理上传的图片编号为"' + ids + '"的数据项？').then(function() {
          return delVisualImage(ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$modal.confirm('是否确认导出所有图片管理。管理上传的图片数据项？').then(() => {
          this.exportLoading = true;
          return exportVisualImage(queryParams);
        }).then(response => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
      }
    }
  };
</script>

<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #000000;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #ff0000;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
