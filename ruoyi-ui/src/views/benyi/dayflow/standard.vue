<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="任务名称" prop="taskLable">
        <el-select v-model="queryParams.taskLable" size="small">
          <el-option
            v-for="item in taskOptions"
            :key="item.code"
            :label="item.taskLable"
            :value="item.taskLable"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="标准名称" prop="sname">
        <el-input
          v-model="queryParams.sname"
          placeholder="请输入标准名称"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['benyi:standard:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:standard:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:standard:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="standardList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标准名称" align="center" prop="sname" />
      <el-table-column label="所属任务" align="center" prop="taskLable" />
      <el-table-column label="标准排序" align="center" prop="standardSort" />
      <el-table-column label="创建时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updatetime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updatetime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:standard:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:standard:remove']"
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

    <!-- 添加或修改一日流程标准对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务名称" prop="taskLable">
          <el-input v-model="form.taskLable" :disabled="true" />
        </el-form-item>
        <el-form-item label="标准名称" prop="sname">
          <el-input v-model="form.sname" placeholder="请输入标准的名称" />
        </el-form-item>
        <el-form-item label="标准内容" prop="standardContent">
          <el-input v-model="form.standardContent" type="textarea" placeholder="请输入标准的内容" />
        </el-form-item>
        <el-form-item label="标准解读" prop="standardJiedu">
          <el-input v-model="form.standardJiedu" type="textarea" placeholder="请输入标准的解读" />
        </el-form-item>
        <el-form-item label="标准排序" prop="standardSort">
          <el-input-number v-model="form.standardSort" controls-position="right" :min="0" />
          <el-input v-model="form.picture" v-if="false" />
        </el-form-item>
        <el-form-item label="照片" prop="picture">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            accept=".jpg, .png"
          >
            <img width="100%" v-if="imageUrl" :src="imageUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="视频" prop="video">
          <el-input v-model="form.video" type="textarea" placeholder="请输入内容" />
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
  listStandard,
  getStandard,
  delStandard,
  addStandard,
  updateStandard
} from "@/api/benyi/dayflow/biaozhun/standard";
import {
  listDayflowtask,
  getDayflowtask
} from "@/api/benyi/dayflow/dayflowtask";
import { getToken } from "@/utils/auth";

export default {
  name: "Standard",
  data() {
    return {
      //显示上传的图片，清空
      imageUrl: "",
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
      // 一日流程标准表格数据
      standardList: [],
      // 任务名称集合
      taskOptions: [],
      // 默认任务名称
      defaultTaskName: "",
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskLable: undefined,
        sname: undefined,
        standardContent: undefined,
        standardJiedu: undefined,
        standardSort: undefined,
        picture: undefined,
        video: undefined,
        createuser: undefined,
        createtime: undefined,
        updateuser: undefined,
        updatetime: undefined,
        beiyong: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sname: [
          { required: true, message: "标准名称不能为空", trigger: "blur" }
        ]
      },
      // 上传的图片服务器地址
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      headers: {
        Authorization: "Bearer " + getToken()
      }
    };
  },
  created() {
    //this.getList();
    const taskId = this.$route.params && this.$route.params.code;
    this.getDayflowtask(taskId);
    this.getTaskList();
    
  },
  methods: {
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      console.log(res);
      if (res.code == "200") {
        this.form.picture = res.fileName;
      } else {
        this.msgError(res.msg);
      }
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error("上传图片大小不能超过 2MB!");
      }
      return isLt2M;
    },
    /**查询任务名称详细 */
    getDayflowtask(taskId) {
      getDayflowtask(taskId).then(response => {
        this.queryParams.taskLable = response.data.taskLable;
        this.defaultTaskName = response.data.taskLable;
        this.getList();
      });
    },
    /** 查询一日流程标准列表 */
    getList() {
      this.loading = true;
      listStandard(this.queryParams).then(response => {
        this.standardList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /**查询任务名称列表 */
    getTaskList() {
      listDayflowtask().then(response => {
        this.taskOptions = response.rows;
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
        sid: undefined,
        taskLable: undefined,
        sname: undefined,
        standardContent: undefined,
        standardJiedu: undefined,
        standardSort: undefined,
        picture: undefined,
        video: undefined,
        createuser: undefined,
        createtime: undefined,
        updateuser: undefined,
        updatetime: undefined,
        beiyong: undefined
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
      this.queryParams.taskLable = this.defaultTaskName;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.sid);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.imageUrl = ""; //清空图片
      this.reset();
      this.open = true;
      this.title = "添加标准";
      this.form.taskLable = this.queryParams.taskLable;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.imageUrl = ""; //清空图片
      this.reset();
      const sid = row.sid || this.ids;
      getStandard(sid).then(response => {
        this.form = response.data;
        if (response.data.picture) {
          this.imageUrl = process.env.VUE_APP_BASE_API + response.data.picture;
        }
        this.open = true;
        this.title = "修改标准";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          console.log(this.form.imageUrl);
          if (this.form.sid != undefined) {
            updateStandard(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addStandard(this.form).then(response => {
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
      const sids = row.sid || this.ids;
      this.$confirm(
        '是否确认删除一日流程标准编号为"' + sids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delStandard(sids);
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