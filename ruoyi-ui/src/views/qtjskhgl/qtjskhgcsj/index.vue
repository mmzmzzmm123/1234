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
            v-for="item in qtjskhfaOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
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
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['qtjskhgl:qtjskhgcsj:remove']"
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
          v-hasPermi="['qtjskhgl:qtjskhgcsj:edit']"
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
      :data="qtjskhgcsjList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column
        label="所属方案"
        align="center"
        prop="tsbzQtjskhzbx.faid"
        :formatter="faFormat"
      />
      <el-table-column
        label="指标模块"
        align="center"
        prop="tsbzQtjskhzbx.khmk"
        :formatter="khmkFormat"
      />
      <el-table-column
        label="指标项"
        align="center"
        prop="tsbzQtjskhzbx.khnr"
      />
      <el-table-column
        label="提交文件数"
        align="center"
        prop="tsbzQtjskhzbx.tjsl"
      />
      <el-table-column label="内容" align="center" prop="content" />
      <el-table-column label="附件" align="center" prop="filename">
        <template slot-scope="scope">
          <el-link type="primary" :href="uploadFileUrl1 + scope.row.filepath" target="_blank">{{ scope.row.filename }}</el-link>
        </template>
      </el-table-column>
      <el-table-column
        label="当前状态"
        align="center"
        prop="substatus"
        :formatter="substatusFormat"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            >详情</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhgcsj:edit']"
            v-show="disable"
            >填报</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhgcsj:remove']"
            v-show="disable"
            >清空</el-button
          >
        </template></el-table-column
      ></el-table
    >

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改群体教师考核过程数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属方案" prop="faid">
          <el-select v-model="form.faid" size="small" :disabled="true">
            <el-option
              v-for="item in qtjskhfaOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="指标项" prop="zbid">
          <el-input v-model="form.khnr" :disabled="true" />
          <el-input v-model="form.zbid" v-if="false" />
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input
            v-model="form.content"
            type="textarea"
            :disabled="!btndisable"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="选择文件" prop="filepath">
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
        <el-button type="primary" v-show="btndisable" @click="submitForm"
          >确 定</el-button
        >
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listQtjskhgcsj,
  getQtjskhgcsj,
  delQtjskhgcsj,
  addQtjskhgcsj,
  updateQtjskhgcsj,
} from "@/api/qtjskhgl/qtjskhgcsj";

import {
  listQtjskhshByuser,
  getQtjskhsh,
  checkQtjskhsh,
} from "@/api/qtjskhgl/qtjskhsh";
import { listQtjskhfa, getQtjskhfa } from "@/api/qtjskhgl/qtjskhfa";
import { getToken } from "@/utils/auth";

export default {
  name: "Qtjskhgcsj",
  data() {
    return {
      //是否已提交
      disable: true,
      btndisable: true,
      //默认方案id
      defaultFaid: "",
      filecount: 1,
      khnr: "",
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
      // 群体教师考核过程数据表格数据
      qtjskhgcsjList: [],
      qtjskhfaOptions: [],
      // 提交状态
      substatusOptions: [],
      //指标模块
      khmkOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      uploadFileUrl1: process.env.VUE_APP_BASE_API, // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      //文件
      fileList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        zbid: null,
        content: null,
        createuserid: null,
        substatus: null,
      },
      // 查询参数
      queryParams_fa: {
        status: "1",
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
          { required: true, message: "考核内容不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getKhfa();
    this.getDicts("sys_dm_khmk").then((response) => {
      this.khmkOptions = response.data;
    });
    this.getDicts("sys_dm_qtjsshgctjzt").then((response) => {
      this.substatusOptions = response.data;
    });
  },
  methods: {
    //判断该方案是否已提交
    getIsCheck() {
      this.queryParams_khsh.faid = this.defaultFaid;
      listQtjskhshByuser(this.queryParams_khsh).then((response) => {
        //console.log(response);
        if (response.rows.length > 0) {
          this.disable = false;
        }
      });
    },
    handleAvatarSuccess(res, file) {
      //console.log(res, file);
      if (res.code == "200") {
        this.form.filepath = this.form.filepath + res.fileName + ";";
        this.form.filename = this.form.filename + file.name + ";";
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
    // 考核模块字典项字典翻译
    khmkFormat(row, column) {
      return this.selectDictLabel(this.khmkOptions, row.tsbzQtjskhzbx.khmk);
    },
    // 提交状态字典项字典翻译
    substatusFormat(row, column) {
      return this.selectDictLabel(this.substatusOptions, row.substatus);
    },
    // 字典翻译
    faFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.qtjskhfaOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.faid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    //考核方案
    async getKhfa() {
      await listQtjskhfa(this.queryParams_fa).then((response) => {
        this.qtjskhfaOptions = response.rows;
        this.defaultFaid = response.rows[0].id;
        this.queryParams.faid = this.defaultFaid;

        this.getList();
      });
    },
    /** 查询群体教师考核过程数据列表 */
    getList() {
      this.loading = true;
      listQtjskhgcsj(this.queryParams).then((response) => {
        this.qtjskhgcsjList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      this.getIsCheck();
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
        content: null,
        khnr: null,
        createuserid: null,
        createTime: null,
        substatus: "0",
        filepath: "",
        filename: "",
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
      this.reset();
      this.btndisable = true;
      const id = row.id || this.ids;

      if (id != "") {
        getQtjskhgcsj(id).then((response) => {
          this.form = response.data;
          // console.log(response);
          this.open = true;
          this.title = "填报群体教师考核过程数据";
          this.form.khnr = row.tsbzQtjskhzbx.khnr;
          // console.log(response.file);
          var array = [];
          var path = "";
          var name = "";
          response.file.forEach(function (value, key, arr) {
            //console.log(value); // 结果依次为1，2，3
            array.push({ name: value.filename, url: value.filepath });
            path = path + value.filepath + ";";
            name = name + value.filename + ";";
          });
          this.filecount = response.file.length;
          this.form.filepath = path;
          this.form.filename = name;
          this.fileList = array;
        });
      } else {
        this.reset();
        this.open = true;
        this.title = "添加群体教师考核过程数据";
        this.form.faid = row.tsbzQtjskhzbx.faid;
        this.form.zbid = row.tsbzQtjskhzbx.id;
        this.form.khnr = row.tsbzQtjskhzbx.khnr;
      }
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.reset();
      const id = row.id || this.ids;
      this.title = "群体教师考核过程数据详情";
      this.btndisable = false;
      if (id != "") {
        getQtjskhgcsj(id).then((response) => {
          this.form = response.data;
          // console.log(response);
          this.open = true;
          this.form.khnr = row.tsbzQtjskhzbx.khnr;
          // console.log(response.file);
          var array = [];
          var path = "";
          var name = "";
          response.file.forEach(function (value, key, arr) {
            //console.log(value); // 结果依次为1，2，3
            array.push({ name: value.filename, url: value.filepath });
            path = path + value.filepath + ";";
            name = name + value.filename + ";";
          });
          this.filecount = response.file.length;
          this.form.filepath = path;
          this.form.filename = name;
          this.fileList = array;
        });
      } else {
        this.reset();
        this.open = true;
        this.form.faid = row.tsbzQtjskhzbx.faid;
        this.form.zbid = row.tsbzQtjskhzbx.id;
        this.form.khnr = row.tsbzQtjskhzbx.khnr;
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateQtjskhgcsj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addQtjskhgcsj(this.form).then((response) => {
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
      this.$confirm("是否确认清空群体教师考核过程数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delQtjskhgcsj(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("清空成功");
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
        "是否确认提交当前方案的考核过程数据项?提交后数据不能维护！",
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return checkQtjskhsh(faid);
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
