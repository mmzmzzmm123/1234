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
      <el-row>
        <el-form-item label="方案名称" prop="name">
          <el-input
            v-model="form.name"
            placeholder="请输入方案名称"
            :disabled="true"
          />
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="方案内容" prop="fanr">
          <Editor v-model="form.fanr" placeholder="请输入方案内容" />
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="方案文件" prop="fawj">
          <el-input v-model="form.fawj" v-if="false" />
          <el-upload
            class="upload-demo"
            :action="uploadFileUrl"
            :headers="headers"
            :on-preview="handlePreview"
            :limit="1"
            :file-list="fileList"
            :disabled="true"
          >
          </el-upload>
        </el-form-item>
      </el-row>
      <el-row>
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
      </el-row>
      <el-row>
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
      </el-row>
      <el-row>
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
      </el-row>
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
  </div>
</template>

<script>
import { getJxzxpxfa } from "@/api/jxjs/jxzxpxfa";

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
      // 表单参数
      form: {},
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      apiurl: process.env.VUE_APP_BASE_API,
      headers: {
        Authorization: "Bearer " + getToken(),
      },
    };
  },
  created() {
    const faid = this.$route.params && this.$route.params.id;
    //console.log(themeplanid);
    this.getPxfaList(faid);
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
    handlePreview(file) {
      //console.log(file);
      window.open(this.apiurl + file.url);
    },
    // 评选学段字典翻译
    pxxdFormat(row, column) {
      return this.selectDictLabel(this.pxxdOptions, row.pxxd);
    },
    // 评选学科字典翻译
    pxxkFormat(row, column) {
      return this.selectDictLabel(this.pxxkOptions, row.pxxk);
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
  },
};
</script>
