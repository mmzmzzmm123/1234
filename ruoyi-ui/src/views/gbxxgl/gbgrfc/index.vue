<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="干部姓名" prop="gbid">
        <el-select
          v-model="queryParams.gbid"
          filterable
          placeholder="请选择或输入姓名"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in gbmcOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
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
      <el-table-column label="干部ID" align="center" prop="tsbzGbjbqk.name"/>
      <el-table-column label="证件照名称" align="center" prop="zjzmc" />
      <el-table-column label="生活照名称" align="center" prop="shzmc" />
      <el-table-column label="工作照名称" align="center" prop="zgzmc" />
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
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="归属部门" prop="deptId">
          <treeselect
            v-model="form.deptId"
            :options="deptOptions"
            :disable-branch-nodes="true"
            :show-count="true"
            placeholder="请选择归属部门"
            :disabled="flag"
          />
        </el-form-item>
        <el-form-item label="干部姓名" prop="gbid">
          <el-select
            v-model="form.gbid"
            placeholder="干部姓名"
            :disabled="flag"
          >
            <el-option
              v-for="dict in gbOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="证件照" prop="zjzpath">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            accept=".jpg, .png"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="证件照名称" prop="zjzmc">
          <el-input v-model="form.zjzmc" placeholder="请输入证件照名称" />
        </el-form-item>
        <el-form-item label="生活照" prop="shzpath">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccessSh"
            :before-upload="beforeAvatarUpload"
            accept=".jpg, .png"
          >
            <img v-if="imageUrlSh" :src="imageUrlSh" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="生活照名称" prop="shzmc">
          <el-input v-model="form.shzmc" placeholder="请输入生活照名称" />
        </el-form-item>
        <el-form-item label="工作照" prop="gzzpath">
          <el-upload
            class="avatar-uploader"
            :action="uploadImgUrl"
            :headers="headers"
            :show-file-list="false"
            :on-success="handleAvatarSuccessGz"
            :before-upload="beforeAvatarUpload"
            accept=".jpg, .png"
          >
            <img v-if="imageUrlGz" :src="imageUrlGz" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="工作照名称" prop="zgzmc">
          <el-input v-model="form.zgzmc" placeholder="请输入工作照名称" />
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

import { listGbjbqk, getGbjbqk } from "@/api/gbxxgl/gbjbqk";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getToken } from "@/utils/auth";

export default {
  name: "Gbgrfc",
  components: { Treeselect },
  data() {
    return {
      //显示上传的图片，清空
      imageUrl: "",
      //显示上传的图片，清空
      imageUrlSh: "",
      //显示上传的图片，清空
      imageUrlGz: "",
      flag: true,
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
      // 部门选项
      deptOptions: [],
      // 干部选项
      gbOptions: [],
      // 干部名称
      gbmcOptions: [],
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
        deptId: null,
      },
      // 查询参数
      queryParams_gb: {
        deptId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deptId: [{ required: true, message: "部门不能为空", trigger: "blur" }],
        gbid: [
          { required: true, message: "干部姓名不能为空", trigger: "blur" },
        ],
      },
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    };
  },
  watch: {
    // 监听deptId
    "form.deptId": "handleBucketClick",
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getGbjbqkList();
  },
  methods: {
    //图片上传
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      //console.log(res);
      if (res.code == "200") {
        this.form.zjzpath = res.fileName;
      } else {
        this.msgError(res.msg);
      }
    },
    handleAvatarSuccessSh(res, file) {
      this.imageUrlSh = URL.createObjectURL(file.raw);
      //console.log(res);
      if (res.code == "200") {
        this.form.shzpath = res.fileName;
      } else {
        this.msgError(res.msg);
      }
    },
    handleAvatarSuccessGz(res, file) {
      this.imageUrlGz = URL.createObjectURL(file.raw);
      //console.log(res);
      if (res.code == "200") {
        this.form.gzzpath = res.fileName;
      } else {
        this.msgError(res.msg);
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg" || file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传首页图片只能是 JPG或PNG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传首页图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    /** 查询个人风采(干部管理-个人状况)列表 */
    getList() {
      this.loading = true;
      listGbgrfc(this.queryParams).then(response => {
        this.gbgrfcList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 部门监听
    handleBucketClick(value) {
      // console.log(value);
      this.queryParams_gb.deptId = value;
      if (this.queryParams_gb.deptId != null) {
        listGbjbqk(this.queryParams_gb).then((response) => {
          // console.log(response.rows);
          this.gbOptions = response.rows;
        });
      }
    },
    /** 查询干部列表 */
    getGbjbqkList() {
      this.loading = true;
      listGbjbqk(null).then((response) => {
        this.gbmcOptions = response.rows;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then((response) => {
        this.deptOptions = response.data;
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
        createTime: null,
        deptId: null,
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
      this.imageUrl = ""; //清空图片
      this.imageUrlGz = ""; //清空图片
      this.imageUrlSh = ""; //清空图片
      this.reset();
      this.open = true;
      this.flag = false;
      this.title = "添加个人风采(干部管理-个人状况)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.imageUrl = ""; //清空图片
      this.imageUrlGz = ""; //清空图片
      this.imageUrlSh = ""; //清空图片
      this.reset();
      const id = row.id || this.ids
      getGbgrfc(id).then(response => {
        this.form = response.data;
        if (response.data.zjzpath) {
          this.imageUrl = process.env.VUE_APP_BASE_API + response.data.zjzpath;
        }
        if (response.data.gzzpath) {
          this.imageUrlGz = process.env.VUE_APP_BASE_API + response.data.gzzpath;
        }
        if (response.data.shzpath) {
          this.imageUrlSh = process.env.VUE_APP_BASE_API + response.data.shzpath;
        }
        this.form.deptId = response.data.tsbzGbjbqk.deptId;
        this.open = true;
        this.flag = true;
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
<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
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
</style>
<style lang="scss" scoped>
.el-select {
  width: 100%;
}
.my-date-picker {
  width: 100%;
}
</style>