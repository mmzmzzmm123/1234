<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="所属方案" prop="faid">
        <el-select v-model="queryParams.faid" size="small">
          <el-option
            v-for="item in faOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="指标编号" prop="zbid">
        <el-input
          v-model="queryParams.zbid"
          placeholder="请输入指标编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
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
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['qtjs:qtjspxfazbxsj:add']"
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
          v-hasPermi="['qtjs:qtjspxfazbxsj:edit']"
          >修改</el-button
        >
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['qtjs:qtjspxfazbxsj:remove']"
          v-show="disable"
          >清空</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-check"
          size="mini"
          @click="handleCheck"
          v-hasPermi="['qtjs:qtjspxfazbxsj:edit']"
          v-show="disable"
          >提交</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="qtjspxfazbxsjList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column
        label="所属方案"
        align="center"
        prop="faid"
        :formatter="faFormat"
      />
      <el-table-column label="指标" align="center" prop="tsbzQtjspxfazbx.zbb" />
      <!-- <el-table-column label="教师编号" align="center" prop="jsid" /> -->
      <el-table-column label="佐证内容" align="center" prop="content" />
      <!-- <el-table-column label="附件" align="center" prop="filepath" /> -->
      <el-table-column label="附件名称" align="center" prop="filename" />
      <!-- <el-table-column label="创建人" align="center" prop="createuserid" /> -->
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
            v-hasPermi="['qtjs:qtjspxfazbxsj:edit']"
            v-show="disable"
            >填报</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qtjs:qtjspxfazbxsj:remove']"
            v-show="disable"
            >清空</el-button
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

    <!-- 添加或修改群体教师评选指标数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="方案编号" prop="faid">
          <el-select v-model="form.faid" size="small" :disabled="true">
            <el-option
              v-for="item in faOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="指标" prop="zbid">
          <el-input v-model="form.zbb" :disabled="true" />
          <el-input v-model="form.zbid" v-if="false" />
        </el-form-item>
        <el-form-item label="佐证内容" prop="content">
          <el-input
            v-model="form.content"
            type="textarea"
            placeholder="请输入内容"
          />
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
            :limit="filecount"
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
  listQtjspxfazbxsj,
  getQtjspxfazbxsj,
  delQtjspxfazbxsj,
  addQtjspxfazbxsj,
  updateQtjspxfazbxsj,
} from "@/api/qtjs/qtjspxfazbxsj";
import { listQtjspxfa, getQtjspxfa } from "@/api/qtjs/qtjspxfa";
import { checkQtjspxsh, getQtjspxshByFaid } from "@/api/qtjs/qtjspxsh";
import { getToken } from "@/utils/auth";

export default {
  name: "Qtjspxfazbxsj",
  data() {
    return {
      //是否已提交
      disable: true,
      //默认方案id
      defaultFaid: "",
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
      // 群体教师评选指标数据表格数据
      qtjspxfazbxsjList: [],
      // 方案选项
      faOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      //文件
      fileList: [],
      filecount: 1,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        zbid: null,
        jsid: null,
        content: null,
        filepath: "",
        filename: "",
        createuserid: null,
      },
      // 查询参数
      queryParams_fa: {
        fazt: "1",
      },
      // 查询参数
      queryParams_khsh: {
        faid: "1",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        content: [
          { required: true, message: "佐证内容不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getPxFaList();
  },
  methods: {
    handleAvatarSuccess(res, file) {
      //console.log(res, file);
      if (res.code == "200") {
        this.form.filepath = res.fileName + ";";
        this.form.filename = file.name + ";";
      } else {
        this.msgError(res.msg);
      }
      //console.log(this.form.filepath, this.form.filename);
    },
    handleRemove(file, fileList) {
      //console.log(file, fileList);
      if (file.response.code == "200") {
        this.form.filepath = this.form.filepath.replace(
          file.response.fileName + ";",
          ""
        );
        this.form.filename = this.form.filename.replace(file.name + ";", "");
      }
    },
    handlePreview(file) {
      //console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 ` +
          this.filecount +
          ` 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
            files.length + fileList.length
          } 个文件`
      );
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    // 字典翻译
    faFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.faOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.faid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    //判断该方案是否已提交
    getIsCheck() {
      this.queryParams_khsh.faid = this.defaultFaid;
      getQtjspxshByFaid(this.queryParams_khsh).then((response) => {
        if (response.rows.length > 0) {
          this.disable = false;
        }
      });
    },
    //获取评选方案
    async getPxFaList() {
      await listQtjspxfa(this.queryParams_fa).then((response) => {
        this.faOptions = response.rows;
        this.defaultFaid = response.rows[0].id;
        this.queryParams.faid = this.defaultFaid;
      });
      this.getList();
    },
    /** 查询群体教师评选指标数据列表 */
    getList() {
      this.loading = true;
      listQtjspxfazbxsj(this.queryParams).then((response) => {
        this.qtjspxfazbxsjList = response.rows;
        this.total = response.total;
        this.loading = false;

        this.getIsCheck();
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
        faid: null,
        zbid: null,
        jsid: null,
        content: null,
        filepath: null,
        filename: null,
        zbb: null,
        createuserid: null,
        createTime: null,
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
      this.queryParams.faid = this.defaultFaid;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      // console.log("row:" + row.tsbzQtjspxfazbx.id);
      this.reset();
      const id = row.id || this.ids;
      // console.log("rowid:" + id);
      if (id != "") {
        getQtjspxfazbxsj(id).then((response) => {
          this.form = response.data;
          this.form.zbb = row.tsbzQtjspxfazbx.zbb;
          this.open = true;
          this.title = "填报群体教师评选指标数据";

          // console.log(response.file);
          var array = [];
          var path = "";
          var name = "";
          // console.log("filename:" + response.data.filename);
          if (response.data.filename != "" && response.data.filename != null) {
            //console.log(value); // 结果依次为1，2，3
            array.push({
              name: response.data.filename,
              url: response.data.filepath,
            });
            path = response.data.filepath + ";";
            name = response.data.filename + ";";
            this.filecount = 1;
            this.form.filepath = path;
            this.form.filename = name;
            this.fileList = array;
          } else {
            this.filecount = 0;
          }
        });
      } else {
        this.open = true;
        this.title = "填报群体教师评选指标数据";
        this.form.faid = row.tsbzQtjspxfazbx.faid;
        this.form.zbid = row.tsbzQtjspxfazbx.id;
        this.form.zbb = row.tsbzQtjspxfazbx.zbb;
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateQtjspxfazbxsj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addQtjspxfazbxsj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("填报成功");
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
      this.$confirm("是否确认清空群体教师评选指标数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delQtjspxfazbxsj(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 提交按钮操作 */
    handleCheck() {
      const faid = this.queryParams.faid;
      if (faid == null || faid == "") {
        this.msgError("请选择要提交的方案");
        return;
      }
      this.$confirm(
        "是否确认提交当前方案的评选过程数据项?提交后数据不能维护！",
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return checkQtjspxsh(faid);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("提交成功");
        })
        .catch(function () {});
    },
  },
};
</script>
