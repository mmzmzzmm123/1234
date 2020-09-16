<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="方案名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入方案名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="方案状态" prop="fazt">
        <el-select v-model="queryParams.fazt" placeholder="请选择方案状态">
          <el-option
            v-for="dict in faztOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所属年份" prop="nf">
        <el-select v-model="queryParams.nf" placeholder="请选择所属年份">
          <el-option
            v-for="dict in nfOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="评选类别" prop="pxlb">
        <el-select v-model="queryParams.pxlb" placeholder="请选择评选类别">
          <el-option
            v-for="dict in pxlbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
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
          v-hasPermi="['qtjs:qtjspxfa:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['qtjs:qtjspxfa:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['qtjs:qtjspxfa:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['qtjs:qtjspxfa:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="qtjspxfaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="方案名称" align="center" prop="name" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <router-link :to="'/qtjs/qtjspxfa/data/' + scope.row.id" class="link-type">
            <span>{{ scope.row.name }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="方案状态" align="center" prop="fazt" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.fazt"
            active-value="1"
            inactive-value="0"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>  
      <el-table-column label="方案有效开始时间" align="center" prop="fayxkssj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.fayxkssj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="方案有效结束时间" align="center" prop="fayxjssj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.fayxjssj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属年份" align="center" prop="nf" />
      <el-table-column label="评选类别" align="center" prop="pxlb" :formatter="pxlbFormat"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document-copy"
            @click="handleCopy(scope.row)"
            v-hasPermi="['qtjs:qtjspxfa:add']"
          >复制</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['qtjs:qtjspxfa:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qtjs:qtjspxfa:remove']"
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

    <!-- 添加或修改群体教师评选方案对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1024px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="128px">
        <el-form-item label="方案名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入方案名称" />
        </el-form-item>
        <el-form-item label="方案内容" prop="fanr">
          <Editor v-model="form.fanr" placeholder="请输入方案内容" />
        </el-form-item>
        <el-form-item label="方案文件" prop="fawj">
          <el-input v-model="form.fawj" v-if="false" />
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
        <el-form-item label="文件名称" prop="wjmc">
          <el-input v-model="form.wjmc" placeholder="请输入文件名称" />
        </el-form-item>
        <el-form-item label="方案状态" prop="fazt">
          <el-radio-group v-model="form.fazt">
            <el-radio
              v-for="dict in faztOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="方案有效开始时间" prop="fayxkssj">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.fayxkssj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择方案有效开始时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="方案有效结束时间" prop="fayxjssj">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.fayxjssj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择方案有效结束时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="所属年份" prop="nf">
          <el-select v-model="form.nf" placeholder="请选择所属年份">
            <el-option
              v-for="dict in nfOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="评选类别" prop="pxlb">
          <el-select v-model="form.pxlb" placeholder="请选择评选类别">
            <el-option
              v-for="dict in pxlbOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
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

<script>
import {
  listQtjspxfa,
  getQtjspxfa,
  delQtjspxfa,
  addQtjspxfa,
  updateQtjspxfa,
  exportQtjspxfa,
  changeStatus,
  copyQtjspxfa,
} from "@/api/qtjs/qtjspxfa";
import { getToken } from "@/utils/auth";
import Editor from "@/components/Editor";

export default {
  name: "Qtjspxfa",
  components: {
    Editor
  },
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
      // 群体教师评选方案表格数据
      qtjspxfaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 方案状态选项
      faztOptions: [],
      // 所属年份选项
      nfOptions: [],
      // 评选类别选项
      pxlbOptions: [],
      // 文件选项
      fileList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        fanr: null,
        fawj: null,
        wjmc: null,
        fazt: null,
        fayxkssj: null,
        fayxjssj: null,
        nf: null,
        pxlb: null,
        createuserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    };
  },
  created() {
    this.getList();
    // 字典翻译
    this.getDicts("sys_dm_fazt").then((response) => {
      this.faztOptions = response.data;
    });
    this.getDicts("sys_dm_rxnf").then((response) => {
      this.nfOptions = response.data;
    });
    this.getDicts("sys_dm_pxlb").then((response) => {
      this.pxlbOptions = response.data;
    });
  },
  methods: {
    /** 查询群体教师评选方案列表 */
    getList() {
      this.loading = true;
      listQtjspxfa(this.queryParams).then((response) => {
        this.qtjspxfaList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handleAvatarSuccess(res, file) {
      console.log(res, file);
      if (res.code == "200") {
        this.form.fawj = res.fileName;
        this.form.wjmc = file.name;
      } else {
        this.msgError(res.msg);
      }
    },
    handleRemove(file, fileList) {
      //console.log(file, fileList);
      if (file.response.code == "200") {
        this.form.fawj = "";
        this.form.wjmc = "";
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
    // 评选类别字典翻译
    pxlbFormat(row, column) {
      return this.selectDictLabel(this.pxlbOptions, row.pxlb);
    },
    // 评选类别字典翻译
    pxlbFormat(row, column) {
      return this.selectDictLabel(this.pxlbOptions, row.pxlb);
    },
    /** 复制按钮操作 */
    handleCopy(row) {
      const id = row.id;
      this.$confirm(
        '是否复制评选方案编号为"' + id + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return copyQtjspxfa(id);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("复制成功");
        })
        .catch(function () {});
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
        name: null,
        fanr: null,
        fawj: null,
        wjmc: null,
        fazt: null,
        fayxkssj: null,
        fayxjssj: null,
        nf: null,
        pxlb: null,
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
      this.title = "添加群体教师评选方案";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getQtjspxfa(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改群体教师评选方案";
        this.fileList.push({
          name: response.data.wjmc,
          url: response.data.fawj,
        });
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateQtjspxfa(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addQtjspxfa(this.form).then((response) => {
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
        '是否确认删除群体教师评选方案编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delQtjspxfa(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有群体教师评选方案数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportQtjspxfa(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    // 状态修改
    handleStatusChange(row) {
      let text = row.fazt === "1" ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.name + '"方案吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return changeStatus(row.id, row.fazt);
        })
        .then(() => {
          this.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.fazt = row.fazt === "1" ? "0" : "1";
        });
    },
  },
};
</script>
