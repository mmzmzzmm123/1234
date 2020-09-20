<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="考核方案" prop="faid">
        <el-select v-model="queryParams.faid" size="small">
          <el-option v-for="dict in faOptions" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="教师" prop="jsid">
        <el-select v-model="form.jsid" placeholder="请选择教师" >
          <el-option 
          v-for="dict in JssOptions" 
          :key="dict.id" 
          :label="dict.name" 
          :value="dict.id"></el-option>
        </el-select>
      </el-form-item>-->
      <el-form-item label="当前状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="校级意见" prop="xjshyj">
        <el-select v-model="queryParams.xjshyj" placeholder="请选择校级审核意见" clearable size="small">
          <el-option
            v-for="dict in xjshyjOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
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
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['qtjskhgl:qtjskhsh:edit']"
        >审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['qtjskhgl:qtjskhsh:remove']"
        >退回</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="qtjskhshList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :selectable="isShow" />
      <el-table-column label="考核方案" align="center" prop="faid" :formatter="faFormat" />
      <el-table-column label="教师" align="center" prop="tsbzJsjbxx.name">
        <template slot-scope="scope">
          <router-link :to="'/qtjskhgl/qtjsxxsh/data/' + scope.row.id" class="link-type">
            <span>{{ scope.row.tsbzJsjbxx.name }}</span>
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
            v-hasPermi="['qtjskhgl:qtjskhsh:edit']"
            v-show="isShow(scope.row)"
          >审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qtjskhgl:qtjskhsh:remove']"
            v-show="isShow(scope.row)"
          >退回</el-button>
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

    <!-- 添加或修改考核审核过程对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="考核方案" prop="faid">
          <el-select v-model="form.faid" placeholder="请选择方案" :disabled="true">
            <el-option v-for="dict in faOptions" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="教师" prop="jsid">
          <el-input v-model="jsxm" :disabled="true" />
          <el-input v-model="form.jsid" v-if="false" />
        </el-form-item>
        <el-form-item label="校级审核意见" prop="xjshyj">
          <el-select v-model="form.xjshyj" placeholder="请选择校级审核意见">
            <el-option
              v-for="dict in xjshyjOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="校级审核建议" prop="xjshjy">
          <el-input v-model="form.xjshjy" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 教师详情页 -->
    <el-dialog :title="title" :visible.sync="open1" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="教师" prop="jsid">
          <el-input v-model="jsxm" :disabled="true" />
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
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import 
{ listQtjskhsh,
  getQtjskhsh, 
  delQtjskhsh, 
  addQtjskhsh, 
  updateQtjskhsh, 
  exportQtjskhsh } from "@/api/qtjskhgl/qtjskhsh";
import { listQtjskhfa } from "@/api/qtjskhgl/qtjskhfa";

export default {
  name: "Qtjskhsh",
  data() {
    return {
      //教师姓名
      jsxm: "",
      // 教师聘任校
      jsprx: "",
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
      qtjskhshList: [],
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
        createuseird: null,
      },
      // 查询参数
      queryParams_fa: {
        status: "1",
      },
      // 查询参数
      queryParams_Jss: {
        jsid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        xjshyj: [
          { required: true, message: "方案编号不能为空", trigger: "blur" },
        ],
        xjshjy: [
          { required: true, message: "方案编号不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getFaList();
    this.getList();
    this.getDicts("sys_dm_khshgczt").then((response) => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then((response) => {
      this.xjshyjOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then((response) => {
      this.qjshyjOptions = response.data;
    });
  },
  methods: {
    /** 查询考核审核过程列表 */
    getList() {
      this.loading = true;
      listQtjskhsh(this.queryParams).then((response) => {
        this.qtjskhshList = response.rows;
        console.log(response.rows);
        this.total = response.total;
        this.loading = false;
      });
    },
    // 是否显示按钮
    isShow(row) {
      if (row.status == "0" || row.status == "9" || row.status == "2") {
        return false;
      } else {
        return true;
      }
    },
    // 方案字典翻译
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
    // 获取方案信息
    getFaList() {
      listQtjskhfa(this.queryParams_fa).then((response) => {
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
      if (id == null || id == "") {
        return this.msgError("当前教师未提交数据，不允许审核！");
      }
      getQtjskhsh(id).then((response) => {
        this.jsxm = response.data.tsbzJsjbxx.name;
        this.form = response.data;
        this.open = true;
        this.title = "修改考核审核过程";
      });
    },
    // /** 详情按钮操作 */
    // handleDetail(row) {
    //   this.reset();
    //   const id = row.id || this.ids;
    //   if (id == null || id == "") {
    //     return this.msgError("当前教师未提交数据，无法查看详情！");
    //   }
    //   getJzxzkhsh(id).then((response) => {
    //     this.jsxm = response.data.tsbzJxjsjbxx.name;
    //     this.jsprx = response.data.tsbzJxjsjbxx.prdwmc;
    //     this.form = response.data;
    //     this.open1 = true;
    //     this.title = "教师详情";
    //   });
    // },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateQtjskhsh(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addQtjskhsh(this.form).then((response) => {
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
        type: "warning",
      })
        .then(function () {
          return delQtjskhsh(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("回退成功");
        })
        .catch(function () {});
    },
  },
};
</script>
