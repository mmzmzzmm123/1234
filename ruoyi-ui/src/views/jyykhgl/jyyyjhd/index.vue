<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="所属任务" prop="rwid">
        <el-select
          v-model="queryParams.rwid"
          size="small"
          clearable
          placeholder="请选择所属任务"
        >
          <el-option
            v-for="dict in jyykhrwList"
            :key="dict.id"
            :label="dict.rwmc"
            :value="dict.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任务内容" prop="rwnrlx">
        <el-select
          v-model="queryParams.rwnrlx"
          size="small"
          clearable
          placeholder="请选择任务内容"
        >
          <el-option
            v-for="dict in rwnrOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="活动名称" prop="hdmc">
        <el-input
          v-model="queryParams.hdmc"
          placeholder="请输入活动名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动主题" prop="hdzt">
        <el-input
          v-model="queryParams.hdzt"
          placeholder="请输入活动主题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动时间" prop="hdsj">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.hdsj"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择活动时间"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="活动地点" prop="hddd">
        <el-input
          v-model="queryParams.hddd"
          placeholder="请输入活动地点"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="cyan"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['jyykhgl:jyyyjhd:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jyykhgl:jyyyjhd:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jyykhgl:jyyyjhd:remove']"
          >删除</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="jyyyjhdList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="所属任务" align="center" prop="tsbzJyykhrw.rwmc" />
      <el-table-column
        label="任务内容"
        align="center"
        prop="rwnrlx"
        :formatter="rwnrFormat"
      />
      <el-table-column label="活动名称" align="center" prop="hdmc" />
      <el-table-column label="活动主题" align="center" prop="hdzt" />
      <el-table-column label="活动时间" align="center" prop="hdsj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.hdsj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动地点" align="center" prop="hddd" />
      <!-- <el-table-column label="附件" align="center" prop="filepath" /> -->
      <el-table-column label="附件名称" align="center" prop="filename" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jyykhgl:jyyyjhd:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jyykhgl:jyyyjhd:remove']"
            >删除</el-button
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

    <!-- 添加或修改研究活动（教研员）对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属任务" prop="rwid">
          <el-select v-model="form.rwid" placeholder="请选择所属任务">
            <el-option
              v-for="dict in jyykhrwList"
              :key="dict.id"
              :label="dict.rwmc"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任务内容" prop="rwnrlx">
          <el-select v-model="form.rwnrlx" placeholder="请选择任务内容">
            <el-option
              v-for="dict in rwnrOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="活动名称" prop="hdmc">
          <el-input v-model="form.hdmc" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="活动主题" prop="hdzt">
          <el-input v-model="form.hdzt" placeholder="请输入活动主题" />
        </el-form-item>
        <el-form-item label="活动时间" prop="hdsj">
          <el-date-picker
            clearable
            class="my-date-picker"
            v-model="form.hdsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择活动时间"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="活动地点" prop="hddd">
          <el-input v-model="form.hddd" placeholder="请输入活动地点" />
        </el-form-item>
        <el-form-item label="附件" prop="filepath">
          <el-input v-model="form.filepath" v-if="false" />
          <el-upload
            class="upload-demo"
            :action="uploadFileUrl"
            :headers="headers"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            :limit="1"
            :on-exceed="handleExceed"
            :file-list="fileList"
            :on-success="handleAvatarSuccess"
          >
            <el-button size="small" type="primary">选择文件</el-button>
          </el-upload>
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
  listJyyyjhd,
  getJyyyjhd,
  delJyyyjhd,
  addJyyyjhd,
  updateJyyyjhd,
} from "@/api/jyykhgl/jyyyjhd";
import { listJyykhrw } from "@/api/jyykhgl/jyykhrw";
import { getToken } from "@/utils/auth";

export default {
  name: "Jyyyjhd",
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
      // 研究活动（教研员）表格数据
      jyyyjhdList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //考核任务
      jyykhrwList: [],
      //任务内容
      rwnrOptions: [],
      //文件
      fileList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rwid: null,
        rwnrlx: null,
        hdmc: null,
        hdzt: null,
        hdsj: null,
        hddd: null,
        filepath: null,
        filename: null,
        deptId: null,
        createUserid: null,
      },
      // 查询参数
      queryParams_khrw: {
        // rwlx: "02",
         parentId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rwid: [
          { required: true, message: "考核任务不能为空", trigger: "blur" },
        ],
        rwnrlx: [
          { required: true, message: "任务内容不能为空", trigger: "blur" },
        ],
      },
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    };
  },
  created() {
    this.getList();
    this.getKhrwList();
    // this.getDicts("sys_dm_jyhdnwnr").then((response) => {
    //   this.rwnrOptions = response.data;
    // });
    this.getDictsLikeDeptids("sys_dm_jyykhrwnr").then((response) => {
        //console.log(response.data);
        var item = [];
        response.data.forEach((res) => {
          //console.log(res.parentId);
          if (res.parentId == "sys_dm_jyykhrwlx02") {
            item.push(res);
          }
        });
        this.rwnrOptions = item;
      });
  },
  methods: {
    handleAvatarSuccess(res, file) {
      console.log(res, file);
      if (res.code == "200") {
        this.form.filepath = res.fileName;
        this.form.filename = file.name;
      } else {
        this.msgError(res.msg);
      }
    },
    handleRemove(file, fileList) {
      //console.log(file, fileList);
      if (file.response.code == "200") {
        this.form.fielname = "";
      }
    },
    handlePreview(file) {
      //console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    // 任务类型字典翻译
    rwnrFormat(row, column) {
      return this.selectDictLabel(this.rwnrOptions, row.rwnrlx);
    },
    /** 查询教研员考核任务列表 */
    getKhrwList() {
      listJyykhrw(this.queryParams_khrw).then((response) => {
        this.jyykhrwList = response.rows;
      });
    },
    /** 查询研究活动（教研员）列表 */
    getList() {
      this.loading = true;
      listJyyyjhd(this.queryParams).then((response) => {
        this.jyyyjhdList = response.rows;
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
        rwid: null,
        rwnrlx: null,
        hdmc: null,
        hdzt: null,
        hdsj: null,
        hddd: null,
        filepath: null,
        filename: null,
        deptId: null,
        createUserid: null,
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加研究活动（教研员）";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJyyyjhd(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改研究活动（教研员）";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJyyyjhd(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJyyyjhd(this.form).then((response) => {
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
      this.$confirm(
        '是否确认删除研究活动（教研员）编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delJyyyjhd(ids);
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
<style lang="scss" scoped>
.el-select {
  width: 100%;
}
.my-date-picker {
  width: 100%;
}
</style>