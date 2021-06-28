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
            v-for="item in jxzxkhfaOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxzxkhgl:jxzxkhgcsj:edit']"
          v-show="disable"
        >填报</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxzxkhgl:jxzxkhgcsj:remove']"
          v-show="disable"
        >清空</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-check"
          size="mini"
          @click="handleCheck"
          v-hasPermi="['jxzxkhgl:jxzxkhgcsj:edit']"
          v-show="disable"
        >提交</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jxzxkhgcsjList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="所属方案" align="center" prop="tsbzJxzxkhzbx.faid" :formatter="faFormat" />
      <el-table-column
        label="指标模块"
        align="center"
        prop="tsbzJxzxkhzbx.khmk"
        :formatter="khmkFormat"
      />
      <el-table-column label="指标项" align="center" prop="tsbzJxzxkhzbx.khnr" />
      <el-table-column label="提交文件数" align="center" prop="tsbzJxzxkhzbx.tjsl" />
      <el-table-column label="内容" align="center" prop="content" />
      <el-table-column label="证明时间" align="center" prop="zmtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.zmtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="地点" align="center" prop="dd" />
      <el-table-column label="落款单位" align="center" prop="lkdw" />
      <!-- <el-table-column label="创建人" align="center" prop="createuserid" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxzxkhgl:jxzxkhgcsj:edit']"
            v-show="disable"
          >填报</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxzxkhgl:jxzxkhgcsj:remove']"
            v-show="disable"
          >清空</el-button>
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

    <!-- 添加或修改考核过程数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属方案" prop="faid">
          <el-select v-model="form.faid" size="small" :disabled="true">
            <el-option
              v-for="item in jxzxkhfaOptions"
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
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容" />
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
        <el-form-item label="证明时间" prop="zmtime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.zmtime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择证明时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="地点" prop="dd">
          <el-input v-model="form.dd"  placeholder="请输入地点" />
        </el-form-item>
        <el-form-item label="落款单位" prop="lkdw">
          <el-input v-model="form.lkdw" placeholder="请输入落款单位" />
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
  listJxzxkhgcsj,
  getJxzxkhgcsj,
  delJxzxkhgcsj,
  addJxzxkhgcsj,
  updateJxzxkhgcsj,
} from "@/api/jxzxkhgl/jxzxkhgcsj";

import { listJxzxkhfa, getJxzxkhfa } from "@/api/jxzxkhgl/jxzxkhfa";
import {
  listJzxzkhshByfaid,
  getJzxzkhsh,
  checkJzxzkhsh,
} from "@/api/jxzxkhgl/jxzxkhsh";
import { getToken } from "@/utils/auth";

export default {
  name: "Jxzxkhgcsj",
  data() {
    return {
      //是否已提交
      disable: true,
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
      // 考核过程数据表格数据
      jxzxkhgcsjList: [],
      //考核方案
      jxzxkhfaOptions: [],
      //指标模块
      khmkOptions: [],
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        zbid: null,
        content: null,
        createuserid: null,
        zmtime: null,
        dd: null,
        lkdw: null,

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
  },
  methods: {
    // 考核模块字典项字典翻译
    khmkFormat(row, column) {
      return this.selectDictLabel(this.khmkOptions, row.tsbzJxzxkhzbx.khmk);
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
    // 字典翻译
    faFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.jxzxkhfaOptions;
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
      listJzxzkhshByfaid(this.queryParams_khsh).then((response) => {
        if (response.rows.length > 0) {
          this.disable = false;
        }
      });
    },
    //考核方案
    async getKhfa() {
      await listJxzxkhfa(this.queryParams_fa).then((response) => {
        this.jxzxkhfaOptions = response.rows;
        this.defaultFaid = response.rows[0].id;
        this.queryParams.faid = this.defaultFaid;

        this.getList();
      });
    },
    /** 查询考核过程数据列表 */
    getList() {
      this.loading = true;
      listJxzxkhgcsj(this.queryParams).then((response) => {
        console.log(response.rows);
        this.jxzxkhgcsjList = response.rows;
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
        content: null,
        createuserid: null,
        createTime: null,
        khnr: null,
        filepath: "",
        filename: "",
        zmtime: null,
        dd: null,
        lkdw: null,

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
      const id = row.id || this.ids;
      // console.log("tjsl:" + row.tsbzJxzxkhzbx.tjsl);
      this.filecount = row.tsbzJxzxkhzbx.tjsl;

      if (id != "") {
        getJxzxkhgcsj(id).then((response) => {
          this.form = response.data;
          console.log(response.data);
          this.open = true;
          this.title = "修改考核过程数据";
          this.form.khnr = row.tsbzJxzxkhzbx.khnr;
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
        this.title = "添加考核过程数据";
        this.form.faid = row.tsbzJxzxkhzbx.faid;
        this.form.zbid = row.tsbzJxzxkhzbx.id;
        this.form.khnr = row.tsbzJxzxkhzbx.khnr;
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJxzxkhgcsj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJxzxkhgcsj(this.form).then((response) => {
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
      console.log(ids);
      if (ids.length <= 0) {
        this.msgError("数据已清空");
        return;
      }
      this.$confirm("是否确认清空考核过程数据的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delJxzxkhgcsj(ids);
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
          return checkJzxzkhsh(faid);
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
