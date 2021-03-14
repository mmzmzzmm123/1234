<template>
  <div class="app-container">
    <!-- 查看方案内容详情页 -->
    <el-form
      ref="form"
      :model="form"
      label-width="100px"
      :inline="true"
      v-show="showSearch"
    >
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
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          :limit="1"
          :on-exceed="handleExceed"
          :file-list="fileList"
          :on-success="handleAvatarSuccess"
          :disabled="true"
        >
          <el-button size="small" type="primary">选择文件</el-button>
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
    <div slot="footer" class="dialog-footer">
      <el-button @click="cancel">取 消</el-button>
    </div>
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
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    };
  },
  created() {
    const faid = this.$route.params && this.$route.params.id;
    //console.log(themeplanid);
    this.getPxfaList(faid);
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
    // 方案详情
    getPxfaList(faid) {
      this.loading = true;
      getJxzxpxfa(faid).then((response) => {
        this.form = response.data;
        const time = [];
        time.push(response.data.fayxkssj);
        time.push(response.data.fayxjssj);
        this.form.fayxkssj = time;
        this.fileList.push({
          name: response.data.wjmc,
          url: response.data.fawj,
        });
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 详情按钮操作 */
    handleView(row) {
      this.reset();
      const id = row.id;
      getJxzxpxfa(id).then((response) => {
        this.form = response.data;
        console.log(response.data);
        this.open_view = true;
        this.title = "评选方案详情";

        const file = response.data.fawj;
        const reader = new FileReader();
        reader.readAsText(file.raw);
        reader.onload = function (event) {
          event.target.result;
        };

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
