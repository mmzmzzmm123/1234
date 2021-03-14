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
      <!-- <el-form-item label="评选学段" prop="pxxd">
        <el-select
          v-model="queryParams.pxxd"
          placeholder="请选择评选学段"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in pxxdOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="评选学科" prop="pxxk">
        <el-select
          v-model="queryParams.pxxk"
          placeholder="请选择评选学科"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in pxxkOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['jxjs:jxzxpxfa:add']"
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
          v-hasPermi="['jxjs:jxzxpxfa:edit']"
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
          v-hasPermi="['jxjs:jxzxpxfa:remove']"
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
      :data="jxzxpxfaList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="方案名称" align="center" prop="name" />
      <el-table-column label="方案状态" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.fazt"
            active-value="1"
            inactive-value="0"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="方案有效开始时间"
        align="center"
        prop="fayxkssj"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.fayxkssj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="方案有效结束时间"
        align="center"
        prop="fayxjssj"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.fayxjssj, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所属年份" align="center" prop="nf" />
      <!-- <el-table-column
        label="评选学段"
        align="center"
        prop="pxxd"
        :formatter="pxxdFormat"
      />
      <el-table-column
        label="评选学科"
        align="center"
        prop="pxxk"
        :formatter="pxxkFormat"
      /> -->
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="180px"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['jxjs:jxzxpxfa:query']"
            >详情</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document-copy"
            @click="handleCopy(scope.row)"
            v-hasPermi="['jxjs:jxzxpxfa:add']"
            >复制</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxjs:jxzxpxfa:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jxzxpxfa:remove']"
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

    <!-- 添加或修改见习之星评选方案对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1024px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
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
        <el-form-item label="方案状态" prop="fazt">
          <el-radio-group v-model="form.fazt">
            <el-radio
              v-for="dict in faztOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
              >{{ dict.dictLabel }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item label="方案有效时间" prop="fayxkssj">
          <el-date-picker
            v-model="form.fayxkssj"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
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
        <!-- <el-form-item label="评选学段" prop="pxxd">
          <el-select v-model="form.pxxd" placeholder="请选择评选学段">
            <el-option
              v-for="dict in pxxdOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="评选学科" prop="pxxk">
          <el-select v-model="form.pxxk" placeholder="请选择评选学科">
            <el-option
              v-for="dict in pxxkOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 查看方案内容详情页 -->
    <el-dialog
      :title="title"
      :visible.sync="open_view"
      width="1024px"
      append-to-body
    >
      <el-form ref="form" :model="form" label-width="110px">
        <el-form ref="form" :model="form" label-width="110px">
          <el-form-item label="方案名称" prop="name">
            <el-input
              v-model="form.name"
              placeholder="请输入方案名称"
              :disabled="true"
            />
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
              :limit="1"
              :on-exceed="handleExceed"
              :file-list="fileList"
              :on-success="handleAvatarSuccess"
              :disabled="true"
            >
            </el-upload>
          </el-form-item>
          <el-form-item label="方案状态" prop="fazt">
            <el-radio-group v-model="form.fazt" :disabled="true">
              <el-radio
                v-for="dict in faztOptions"
                :key="dict.dictValue"
                :label="dict.dictValue"
                >{{ dict.dictLabel }}</el-radio
              >
            </el-radio-group>
          </el-form-item>
          <el-form-item label="方案有效时间" prop="fayxkssj">
            <el-date-picker
              v-model="form.fayxkssj"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :disabled="true"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="所属年份" prop="nf">
            <el-select
              v-model="form.nf"
              placeholder="请选择所属年份"
              :disabled="true"
            >
              <el-option
                v-for="dict in nfOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="评选学段" prop="pxxd">
            <el-select
              v-model="form.pxxd"
              placeholder="请选择评选学段"
              :disabled="true"
            >
              <el-option
                v-for="dict in pxxdOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="评选学科" prop="pxxk">
            <el-select
              v-model="form.pxxk"
              placeholder="请选择评选学科"
              :disabled="true"
            >
              <el-option
                v-for="dict in pxxkOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item> -->
        </el-form>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listJxzxpxfa,
  getJxzxpxfa,
  delJxzxpxfa,
  addJxzxpxfa,
  updateJxzxpxfa,
  changeStatus,
  copyJxzxpxfa,
} from "@/api/jxjs/jxzxpxfa";

import { getToken } from "@/utils/auth";
import Editor from "@/components/Editor";

export default {
  name: "Jxzxpxfa",
  components: {
    Editor,
  },
  data() {
    return {
      inviteCode: "",
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
      // 见习之星评选方案表格数据
      jxzxpxfaList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open_view: false,
      //字典
      faztOptions: [],
      nfOptions: [],
      pxxkOptions: [],
      pxxdOptions: [],
      fileList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        fawj: null,
        fazt: null,
        fayxkssj: null,
        fayxjssj: null,
        nf: null,
        pxxd: null,
        pxxk: null,
        createuserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "方案名称不能为空", trigger: "blur" },
        ],
        fanr: [
          { required: true, message: "方案内容不能为空", trigger: "blur" },
        ],
        fawj: [
          { required: true, message: "方案文件不能为空", trigger: "blur" },
        ],
        fazt: [
          { required: true, message: "方案状态不能为空", trigger: "blur" },
        ],
        fayxkssj: [
          { required: true, message: "方案有效时间不能为空", trigger: "blur" },
        ],
        nf: [{ required: true, message: "方案年份不能为空", trigger: "blur" }],
      },
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      apiurl: process.env.VUE_APP_BASE_API,
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_rxnf").then((response) => {
      this.nfOptions = response.data;
    });
    this.getDicts("sys_dm_fazt").then((response) => {
      this.faztOptions = response.data;
    });
    this.getDicts("sys_dm_rjxk").then((response) => {
      this.pxxkOptions = response.data;
    });
    this.getDicts("sys_dm_rjxd").then((response) => {
      this.pxxdOptions = response.data;
    });
  },
  methods: {
    /** 详情按钮操作 */
    handleView(row) {
      this.reset();
      const id = row.id;
      getJxzxpxfa(id).then((response) => {
        this.form = response.data;
        //console.log(response.data);
        this.open_view = true;
        this.title = "评选方案详情";
        const time = [];
        time.push(response.data.fayxkssj);
        time.push(response.data.fayxjssj);
        this.form.fayxkssj = time;
        this.fileList.push({
          name: response.data.wjmc,
          url: response.data.fawj,
        });
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
      }
    },
    handlePreview(file) {
      //console.log(file.url);
      window.open(this.apiurl + file.url);
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
    // 评选学段字典翻译
    pxxdFormat(row, column) {
      return this.selectDictLabel(this.pxxdOptions, row.pxxd);
    },
    // 评选学科字典翻译
    pxxkFormat(row, column) {
      return this.selectDictLabel(this.pxxkOptions, row.pxxk);
    },
    /** 查询见习之星评选方案列表 */
    getList() {
      this.loading = true;
      listJxzxpxfa(this.queryParams).then((response) => {
        this.jxzxpxfaList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 复制按钮操作 */
    handleCopy(row) {
      const id = row.id;
      this.$confirm(
        '是否复制见习之星评选方案编号为"' + id + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return copyJxzxpxfa(id);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("复制成功");
        })
        .catch(function () {});
    },

    // // 一键
    // getUser() {
    //   getUserProfile().then((response) => {
    //     this.user = response.data;
    //     console.log(this.user);
    //     // this.inviteCode =
    //     //   response.data.dept.deptName +
    //     //   "评选方案 " +
    //     //   "http://" +
    //     //   domain +
    //     //   "/experience/apply/" +
    //     //   response.data.dept.deptId;
    //   });
    // },
    // // 一键复制
    // copy(e, text) {
    //   const clipboard = new Clipboard(e.target, { text: () => text });
    //   clipboard.on("success", (e) => {
    //     this.msgSuccess("复制成功");
    //     // 释放内存
    //     clipboard.off("error");
    //     clipboard.off("success");
    //     clipboard.destroy();
    //   });
    //   clipboard.on("error", (e) => {
    //     // 不支持复制
    //     this.msgError("手机权限不支持复制功能");
    //     // 释放内存
    //     clipboard.off("error");
    //     clipboard.off("success");
    //     clipboard.destroy();
    //   });
    //   clipboard.onClick(e);
    // },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open_view = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        fawj: null,
        fazt: "0",
        fayxkssj: null,
        fayxjssj: null,
        nf: null,
        pxxd: null,
        pxxk: null,
        createuserid: null,
        createTime: null,
        wjmc: null,
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
      this.title = "添加见习之星评选方案";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJxzxpxfa(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改见习之星评选方案";
        const time = [];
        time.push(response.data.fayxkssj);
        time.push(response.data.fayxjssj);
        this.form.fayxkssj = time;
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
          const time = this.form.fayxkssj;
          this.form.fayxkssj = time[0];
          this.form.fayxjssj = time[1];
          if (this.form.id != null) {
            updateJxzxpxfa(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJxzxpxfa(this.form).then((response) => {
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
        '是否确认删除见习之星评选方案编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delJxzxpxfa(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
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
