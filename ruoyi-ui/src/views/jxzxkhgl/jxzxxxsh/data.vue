<template>
  <div class="app-container">
    <el-form :model="form" ref="form" :inline="true" v-show="showSearch" label-width="102px">
      <el-form-item label="教师" prop="jsid">
        <el-input v-model="jsxm" :disabled="true" />
      </el-form-item>
      <el-form-item label="性别" prop="jsxb" :formatter="xbFormat">
        <el-select v-model="jsxb" placeholder="请选择性别" :disabled="true">
          <el-option
            v-for="dict in xbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="考核方案" prop="faid">
        <el-select v-model="form.faid" placeholder="请选择方案" :disabled="true">
          <el-option v-for="dict in faOptions" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="聘任校" prop="jsprx">
        <el-input v-model="jsprx" :disabled="true" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="form.status" placeholder="请选择状态" :disabled="true">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <!-- <el-table v-loading="loading" :data="jzxzkhshList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :selectable="isShow" />
      <el-table-column label="考核方案" align="center" prop="faid" :formatter="faFormat" />
      <el-table-column label="教师" align="center" prop="tsbzJxjsjbxx.name">
        <template slot-scope="scope">
          <router-link :to="'/jxzxkhgl/jxzxxxsh/data/' + scope.row.id" class="link-type">
            <span>{{ scope.row.tsbzJxjsjbxx.name }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="校级审核意见" align="center" prop="xjshyj" :formatter="xjshyjFormat" />
      <el-table-column label="校级审核建议" align="center" prop="xjshjy" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxzxkhgl:jxzxkhsh:edit']"
            v-show="isShow(scope.row)"
          >审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxzxkhgl:jxzxkhsh:remove']"
            v-show="isShow(scope.row)"
          >退回</el-button>
        </template>
      </el-table-column>
    </el-table> -->
  </div>
</template>

<script>
import {
  listJzxzkhsh,
  getJzxzkhsh,
  delJzxzkhsh,
  addJzxzkhsh,
  updateJzxzkhsh
} from "@/api/jxzxkhgl/jxzxkhsh";
import { listJxzxkhfa } from "@/api/jxzxkhgl/jxzxkhfa";

export default {
  name: "Jzxzkhsh",
  data() {
    return {
      //教师姓名
      jsxm: "",
      // 教师性别
      jsxb: "",
      // 教师聘任校
      jsprx: "",
      // 教师文件
      jsfilename: "",
      // 教师文件路径
      jsfilepath: "",
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
      // 考核审核过程表格数据
      jzxzkhshList: [],
      // 性别字典
      xbOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 教师详情弹出层
      open1: false,
      // 状态字典
      statusOptions: [],
      // 校级审核意见字典
      xjshyjOptions: [],
      // 区级审核意见字典
      qjshyjOptions: [],
      //方案
      faOptions: [],

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        jsid: null,
        status: null,
        xjshr: null,
        xjshyj: null,
        xjshjy: null,
        qjshr: null,
        qjshyj: null,
        qjshjy: null,
        createuseird: null
      },
      // 查询参数
      queryParams_fa: {
        status: "1"
      },
      // 查询参数
      queryParams_Jss: {
        jsid: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        xjshyj: [
          { required: true, message: "方案编号不能为空", trigger: "blur" }
        ],
        xjshjy: [
          { required: true, message: "方案编号不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    const dqshid = this.$route.params && this.$route.params.id;
    console.log(dqshid);
    if (dqshid != null || dqshid != "") {
      this.getDetail(dqshid);
      this.getFaList();
      // this.getList();
      this.getDicts("sys_dm_khshgczt").then(response => {
        this.statusOptions = response.data;
      });
      this.getDicts("sys_dm_shyj").then(response => {
        this.xjshyjOptions = response.data;
      });
      this.getDicts("sys_dm_shyj").then(response => {
        this.qjshyjOptions = response.data;
      });
      this.getDicts("sys_user_sex").then(response => {
        this.xbOptions = response.data;
      });
    } else {
      alert("当前教师未提交数据，无法查看！");
    }
  },
  methods: {
    // 查详情
    getDetail(val) {
      getJzxzkhsh(val).then(response => {
        this.jsxm = response.data.tsbzJxjsjbxx.name;
        this.jsxb = response.data.tsbzJxjsjbxx.xb;
        this.jsprx = response.data.tsbzJxjsjbxx.prdwmc;
        this.jsfilename = response.data.tsbzJxzxkhgcwjsj.filename;
        this.jsfilepath = response.data.tsbzJxzxkhgcwjsj.filepath;
        this.form = response.data;
        console.log(this.form);
      });
    },
    // /** 查询考核审核过程列表 */
    // getList() {
    //   this.loading = true;
    //   listJzxzkhsh(this.queryParams.id).then((response) => {
    //     this.jzxzkhshList = response.rows;
    //     this.total = response.total;
    //     this.loading = false;
    //   });
    // },
    // 是否显示按钮
    isShow(row) {
      if (row.status == "0" || row.status == "9" || row.status == "2") {
        return false;
      } else {
        return true;
      }
    },
    // 性别字典翻译
    xbFormat(row, column) {
      return this.selectDictLabel(this.xbOptions, row.xb);
    },
    // 方案字典翻译
    faFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.faOptions;
      Object.keys(datas).map(key => {
        if (datas[key].id == "" + row.faid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 获取方案信息
    getFaList() {
      listJxzxkhfa(this.queryParams_fa).then(response => {
        this.faOptions = response.rows;
        this.defaultFaid = response.rows[0].id;
        this.queryParams.faid = this.defaultFaid;
      });
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 校级审核意见字典翻译
    xjshyjFormat(row, column) {
      return this.selectDictLabel(this.xjshyjOptions, row.xjshyj);
    },
    // 区级审核意见字典翻译
    qjshyjFormat(row, column) {
      return this.selectDictLabel(this.qjshyjOptions, row.qjshyj);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open1 = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        faid: null,
        jsid: null,
        status: null,
        xjshr: null,
        xjshyj: null,
        xjshjy: null,
        qjshr: null,
        qjshyj: null,
        qjshjy: null,
        createuseird: null,
        createTime: null
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
      this.queryParams.faid = this.defaultFaid;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      if (id == null || id == "") {
        return this.msgError("当前教师未提交数据，不允许审核！");
      }
      getJzxzkhsh(id).then(response => {
        this.jsxm = response.data.tsbzJxjsjbxx.name;
        this.form = response.data;
        this.open = true;
        this.title = "修改考核审核过程";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateJzxzkhsh(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJzxzkhsh(this.form).then(response => {
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
      this.$confirm("是否确认该教师考核数据回退到教师?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delJzxzkhsh(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("回退成功");
        })
        .catch(function() {});
    }
  }
};
</script>
