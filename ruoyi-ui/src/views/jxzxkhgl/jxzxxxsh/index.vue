<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="102px"
    >
      <el-form-item label="考核方案" prop="faid">
        <el-select v-model="form.faid" placeholder="请选择方案">
          <el-option 
          v-for="dict in faOptions" 
          :key="dict.id" 
          :label="dict.name" :value="dict.id"></el-option>
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
      </el-form-item> -->
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="校级审核意见" prop="xjshyj">
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
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxzxkhgl:jxzxkhsh:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jzxzkhshList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="聘任校" align="center" prop="tsbzJxjsjbxx.prdwmc" />
      <el-table-column label="考核方案" align="center" prop="faid" :formatter="faFormat" />
      <el-table-column label="教师" align="center" prop="tsbzJxjsjbxx.name" />
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
          <el-select v-model="form.faid" placeholder="请选择方案">
            <el-option v-for="dict in faOptions" :key="dict.id" :label="dict.name" :value="dict.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="教师" prop="jsid" :formatter="jsFormat">
          <el-select v-model="form.jsid" placeholder="请选择教师" :disabled="true">
            <el-option
              v-for="dict in JssOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>-->
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
  </div>
</template>

<script>
import {
  listJzxzkhsh,
  getJzxzkhsh,
  delJzxzkhsh,
  addJzxzkhsh,
  updateJzxzkhsh,
  exportJzxzkhsh
} from "@/api/jxzxkhgl/jxzxkhsh";
import { listJxzxkhfa } from "@/api/jxzxkhgl/jxzxkhfa";
import { listJxjsjbxx } from "@/api/jxjs/jxjsjbxx";

export default {
  name: "Jzxzkhsh",
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
      // 考核审核过程表格数据
      jzxzkhshList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      // 校级审核意见字典
      xjshyjOptions: [],
      // 区级审核意见字典
      qjshyjOptions: [],
      //方案
      faOptions: [],
      // 教师列表
      JssOptions: [],
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
        status: null
      },
      // 查询参数
      queryParams_Jss: {
        jsid: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        faid: [
          { required: true, message: "方案编号不能为空", trigger: "blur" },
        ],
        xjshyj: [
          { required: true, message: "方案编号不能为空", trigger: "blur" },
        ],
        xjshjy: [
          { required: true, message: "方案编号不能为空", trigger: "blur" },
        ],
      }
    };
  },
  created() {
    this.getJssList();
    this.getFaList();
    this.getList();
    this.getDicts("sys_dm_khshgczt").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then(response => {
      this.xjshyjOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then(response => {
      this.qjshyjOptions = response.data;
    });
  },
  methods: {
    /** 查询考核审核过程列表 */
    getList() {
      this.loading = true;
      listJzxzkhsh(this.queryParams).then(response => {
        this.jzxzkhshList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 是否显示按钮
    isShow(row) {
      //console.log(row.status);
      if (row.xjshyj == "0" || row.xjshyj == "1") {
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
      this.queryParams_fa.status = "1";
      listJxzxkhfa(this.queryParams_fa).then(response => {
        this.faOptions = response.rows;
      });
    },
    // 获取教师信息
    getJssList() {
      this.queryParams_Jss.jsid = this.queryParams.jsid;
      listJxjsjbxx(this.queryParams_Jss).then(response => {
        this.JssOptions = response.rows;
        //console.log(this.JssOptions);
      });
    },

    jsFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.JssOptions;
      Object.keys(datas).map(key => {
        if (datas[key].id == "" + row.jsid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.isable = false;
      this.open = true;
      this.title = "添加考核审核过程";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getJzxzkhsh(id).then(response => {
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
      this.$confirm("是否确认该教师考核数据回退到聘任校?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delJzxzkhsh(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    }
    /** 导出按钮操作 */
    // handleExport() {
    //   const queryParams = this.queryParams;
    //   this.$confirm('是否确认导出所有考核审核过程数据项?', "警告", {
    //       confirmButtonText: "确定",
    //       cancelButtonText: "取消",
    //       type: "warning"
    //     }).then(function() {
    //       return exportJzxzkhsh(queryParams);
    //     }).then(response => {
    //       this.download(response.msg);
    //     }).catch(function() {});
    // }
  }
};
</script>
