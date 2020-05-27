<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="讲师姓名" prop="lecturer">
        <el-select v-model="queryParams.lecturer" filterable placeholder="请选择讲师">
          <el-option
            v-for="item in lecturerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="所属类别" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择所属类别" clearable size="small">
          <el-option label="请选择字典生成" value />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createtime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.createtime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建时间"
        ></el-date-picker>
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['benyi:video:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:video:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:video:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="videoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="培训视频标题" align="center" prop="title" />
      <el-table-column label="视频简介" align="center" prop="information" />
      <el-table-column label="培训讲师" align="center" prop="lecturername" />
      <el-table-column label="所属类别" align="center" prop="type" />
      <el-table-column label="创建时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:video:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:video:remove']"
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

    <!-- 添加或修改培训对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="视频标题" prop="title">
          <el-input v-model="form.title" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="视频简介" prop="information">
          <el-input v-model="form.information" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="培训讲师" prop="lecturer">
          <el-select v-model="form.lecturer" placeholder="请选择讲师">
            <el-input v-model="form.videourl" v-if="false" />
            <el-option
              v-for="item in lecturerOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="选择视频" prop="videourl">
          <div id="app">
            <el-upload
              class="avatar-uploader"
              :data="dataObj"
              action="http://upload.qiniup.com"
              list-type="picture-card"
              :show-file-list="false"
              :on-error="handleError"
              :on-success="handleSuccessVideo"
              :on-progress="uploadProcess"
              :before-upload="videoBeforeUpload"
              :on-remove="handleRemove"
            >
              <video
                v-if="imageUrl.length > 1 && imgFlag == false"
                controls="controls"
                :src="[qiniuUrl + '/' + imageUrl]"
                class="avatar"
              ></video>
              <i
                v-else-if="imageUrl.length < 1 && imgFlag == false"
                class="el-icon-plus avatar-uploader-icon"
              ></i>
              <el-progress
                v-if="imgFlag == true"
                type="circle"
                :percentage="percent"
                style="margin-top: 20px"
              ></el-progress>
            </el-upload>
          </div>
        </el-form-item>
        <el-form-item label="所属类别">
          <el-select v-model="form.type" placeholder="请选择所属类别">
            <el-option label="请选择字典生成" value />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script src="//unpkg.com/vue/dist/vue.js"></script>
<script src="//unpkg.com/element-ui@2.9.1/lib/index.js"></script>
<script>
import {
  listVideo,
  getVideo,
  delVideo,
  addVideo,
  updateVideo,
  getQiNiuToken
} from "@/api/benyi_train/video";
import { listAllLecturer } from "@/api/benyi_train/lecturer";

export default {
  name: "Video",
  data() {
    return {
      qiniuUrl: "https://files.benyiedu.com/", // 个人七牛访问前缀
      imgFlag: false,
      imageUrl: [],
      percent: 0,
      dialogImageUrl: "",
      dialogVisible: false,
      listObj: {},
      dataObj: {
        token: ""
      }, // 此处七牛token写成常量方便调试，正式环境需要获取token
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
      // 培训表格数据
      videoList: [],
      // 弹出层标题
      title: "",
      //讲师列表
      lecturerOptions: [],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: undefined,
        information: undefined,
        lecturer: undefined,
        videourl: undefined,
        type: undefined,
        classtype: undefined,
        createuserid: undefined,
        createtime: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
    listAllLecturer().then(response => {
      console.log(response.lecturer);
      this.lecturerOptions = response.lecturer;
    });
    getQiNiuToken().then(res => {
      console.log(res.token);
      this.dataObj.token = res.token;
    });
  },
  methods: {
    handleRemove(file, fileList) {
      this.imageUrl = "";
    },
    handleSuccessVideo(response) {
      console.log(123, response);
      this.imgFlag = false;
      this.percent = 0;
      if (response.hash) {
        this.imageUrl = response.hash;
      } else {
        this.msgError("视频上传失败，请重新上传！");
      }
    },
    handleError(err, file, fileList) {
      // 上传失败异常处理
      const error = JSON.parse(JSON.stringify(err));
      console.log(err);
      console.log(error);
      this.msgError(error.status.toString());
      this.imgFlag = false;
      this.percent = 0;
    },
    videoBeforeUpload(file) {
      console.log(file);
      const _self = this;
      const isVideo =
        file.type === "video/mp4" ||
        file.type === "video/ogg" ||
        file.type === "video/flv" ||
        file.type === "video/avi" ||
        file.type === "video/wmv" ||
        file.type === "video/rmvb";
      const isLt500M = file.size / 1024 / 1024 < 500;
      if (!isVideo) {
        this.msgError("请上传正确格式的视频！");
        return false;
      } else {
        if (!isLt500M) {
          this.msgError("上传视频文件大小不能超过 500MB!");
          return false;
        }
      }
    },
    //上传进度
    uploadProcess(event, file, fileList) {
      this.imgFlag = true;
      console.log(event.percent);
      this.percent = Math.floor(event.percent);
    },
    /** 查询培训列表 */
    getList() {
      this.loading = true;
      listVideo(this.queryParams).then(response => {
        this.videoList = response.rows;
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
        title: undefined,
        information: undefined,
        lecturer: undefined,
        videourl: undefined,
        type: undefined,
        classtype: undefined,
        createuserid: undefined,
        createtime: undefined
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.imageUrl="";
      this.reset();
      this.open = true;
      this.title = "添加培训";
      //获取讲师列表
      listAllLecturer().then(response => {
        //console.log(response.lecturer);
        this.lecturerOptions = response.lecturer;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.imageUrl="";
      this.reset();
      const id = row.id || this.ids;
      getVideo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改培训";
        this.imageUrl = response.data.videourl;
        console.log(this.imageUrl);
        //获取讲师列表
        listAllLecturer().then(response => {
          //console.log(response.lecturer);
          this.lecturerOptions = response.lecturer;
        });
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.videourl = this.imageUrl;
          if (this.form.id != undefined) {
            updateVideo(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addVideo(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除培训编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delVideo(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function() {});
    }
  }
};
</script>

<style scoped>
@import url("//unpkg.com/element-ui@2.9.1/lib/theme-chalk/index.css");
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 178px;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
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

.image-preview {
  width: 178px;
  height: 178px;
  position: relative;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  float: left;
}

.image-preview .image-preview-wrapper {
  position: relative;
  width: 100%;
  height: 100%;
}

.image-preview .image-preview-wrapper img {
  width: 100%;
  height: 100%;
}

.image-preview .image-preview-action {
  position: absolute;
  width: 100%;
  height: 100%;
  left: 0;
  top: 0;
  cursor: default;
  text-align: center;
  color: #fff;
  opacity: 0;
  font-size: 20px;
  background-color: rgba(0, 0, 0, 0.5);
  transition: opacity 0.3s;
  cursor: pointer;
  text-align: center;
  line-height: 200px;
}

.image-preview .image-preview-action .el-icon-delete {
  font-size: 32px;
}

.image-preview:hover .image-preview-action {
  opacity: 1;
}
</style>