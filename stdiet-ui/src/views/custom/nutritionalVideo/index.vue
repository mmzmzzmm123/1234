<template>
  <div class="app-container">
    <!--<el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关键词" prop="title">
        <el-input
          v-model="queryParams.key"
          placeholder="请输入关键词"
          clearable
          size="small"
        />
      </el-form-item>
      <el-form-item label="状态" prop="showFlag">
        <el-select
          v-model="queryParams.showFlag"
          placeholder="请选示状态"
          clearable
          size="small"
        >
          <el-option key="0" label="屏蔽" value="0"/>
          <el-option key="1" label="正常" value="1"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>-->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="clickUploadVideo"
          v-hasPermi="['custom:nutritionalVideo:add']"
        >视频上传</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:nutritionalVideo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:nutritionalVideo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:nutritionalVideo:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="nutritionalVideoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--<el-table-column label="视频分类ID" align="center" prop="cateId" />-->
      <el-table-column label="封面" align="center" prop="coverUrl" width="300">
        <template slot-scope="scope">
          <el-image
          style="width: 300px; height: 200px"
          :src="scope.row.coverUrl"
          :preview-src-list="coverImageList">
          </el-image>
        </template>
      </el-table-column>
      <el-table-column label="标题" align="center" prop="title" width="200"/>
      <el-table-column label="描述" align="center" prop="description" />
      <el-table-column label="标签" align="center" prop="tags" width="100"/>
       <el-table-column label="分类" align="center" prop="cateName" width="100"/>
       <el-table-column label="权限等级" align="center" prop="payLevelName" width="100"/>
      <el-table-column label="显示状态" align="center" prop="showFlag" width="100">
        <template slot-scope="scope">
          {{scope.row.showFlag == 1 ? '正常' : '不显示'}}
        </template>
      </el-table-column>
      <!--<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:nutritionalVideo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:nutritionalVideo:remove']"
          >删除</el-button>
        </template>
      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      :pageSizes="[5,10,15,20]"
      @pagination="getList"
    />

    <!-- 添加或修改营养视频对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="视频分类ID" prop="cateId">
          <el-input v-model="form.cateId" placeholder="请输入视频分类ID" />
        </el-form-item>
        <el-form-item label="阿里云视频ID" prop="videoId">
          <el-input v-model="form.videoId" placeholder="请输入阿里云视频ID" />
        </el-form-item>
        <el-form-item label="视频封面URL" prop="coverUrl">
          <el-input v-model="form.coverUrl" placeholder="请输入视频封面URL" />
        </el-form-item>
        <el-form-item label="视频标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入视频标题" />
        </el-form-item>
        <el-form-item label="视频描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入视频描述" />
        </el-form-item>
        <el-form-item label="视频大小，单位：字节" prop="videoSize">
          <el-input v-model="form.videoSize" placeholder="请输入视频大小，单位：字节" />
        </el-form-item>
        <el-form-item label="标签，使用 , 隔开" prop="tags">
          <el-input v-model="form.tags" placeholder="请输入标签，使用 , 隔开" />
        </el-form-item>
        <el-form-item label="是否显示，0不显示 1显示，默认0" prop="showFlag">
          <el-input v-model="form.showFlag" placeholder="请输入是否显示，0不显示 1显示，默认0" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <UploadVideo ref="uploadVideoRef"></UploadVideo>
  </div>
</template>

<script>
  import { listNutritionalVideo, getNutritionalVideo, delNutritionalVideo, addNutritionalVideo, updateNutritionalVideo, exportNutritionalVideo,getUploadVideoAuth } from "@/api/custom/nutritionalVideo";

  import UploadVideo from "@/components/UploadVideo";
  export default {
    name: "NutritionalVideo",
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
        // 营养视频表格数据
        nutritionalVideoList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 5,
          key: null,
          showFlag: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
        },
        coverImageList:[]
      };
    },
    created() {
      this.getList();
    },
    components: {
      UploadVideo
    },
    methods: {
      /** 查询营养视频列表 */
      getList() {
        this.loading = true;
        listNutritionalVideo(this.queryParams).then(response => {
          this.nutritionalVideoList = response.rows;
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
          cateId: null,
          videoId: null,
          coverUrl: null,
          title: null,
          description: null,
          videoSize: null,
          tags: null,
          showFlag: null,
          createTime: null,
          createBy: null,
          updateTime: null,
          updateBy: null,
          delFlag: null
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
      clickUploadVideo(){
        this.$refs.uploadVideoRef.showDialog(()=>{
         this.getList();
        });
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加营养视频";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getNutritionalVideo(id).then(response => {
          this.form = response.data;
        this.open = true;
        this.title = "修改营养视频";
      });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateNutritionalVideo(this.form).then(response => {
                if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
            } else {
              addNutritionalVideo(this.form).then(response => {
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
        this.$confirm('是否确认删除营养视频编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delNutritionalVideo(ids);
        }).then(() => {
          this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有营养视频数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportNutritionalVideo(queryParams);
        }).then(response => {
          this.download(response.msg);
      }).catch(function() {});
      }
    }
  };
</script>
