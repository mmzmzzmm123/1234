<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="基地校" prop="jdxid">
        <el-select
          v-model="queryParams.jdxid"
          filterable
          placeholder="请选择基地校"
        >
          <el-option
            v-for="item in jdxOptions"
            :key="item.id"
            :label="item.jdxmc"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="见习教师" prop="jsid">
        <el-select
          v-model="queryParams.jsid"
          filterable
          placeholder="请选择教师"
        >
          <el-option
            v-for="item in jsOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="培训表现" prop="pxbx">
        <el-select
          v-model="queryParams.pxbx"
          placeholder="请选择培训表现"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in pxbxOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="培训年份" prop="pxnf">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.pxnf"
          type="year"
          value-format="yyyy-MM-dd"
          placeholder="选择培训年份"
        >
        </el-date-picker>
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
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxjs:jxzxpxpj:edit']"
          >评价</el-button
        >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxjs:jxzxpxpj:remove']"
        >删除</el-button>
      </el-col> -->
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="jxzxpxpjList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="基地校" align="center" prop="jdxmc" />
      <el-table-column
        label="见习教师"
        align="center"
        prop="tsbzJxjsjbxx.name"
      />
      <el-table-column
        label="培训表现"
        align="center"
        prop="pxbx"
        :formatter="pxbxFormat"
      />
      <el-table-column label="培训年份" align="center" prop="pxnf" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.pxnf, "{y}") }}</span>
        </template>
      </el-table-column>
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
            v-hasPermi="['jxjs:jxzxpxpj:edit']"
            >评价</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jxzxpxpj:remove']"
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

    <!-- 添加或修改见习之星教师培训评价对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="见习教师" prop="jsid">
          <el-input v-model="jsxm" :disabled="true" />
          <el-input v-if="false" v-model="form.jsid" />
        </el-form-item>
        <el-form-item label="培训表现" prop="pxbx">
          <el-select v-model="form.pxbx" placeholder="请选择培训表现">
            <el-option
              v-for="dict in pxbxOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="培训年份" prop="pxnf">
          <el-date-picker
            v-model="form.pxnf"
            type="year"
            value-format="yyyy-MM-dd"
            placeholder="选择培训年份"
          >
          </el-date-picker>
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
  listJxzxpxpj,
  getJxzxpxpj,
  delJxzxpxpj,
  addJxzxpxpj,
  updateJxzxpxpj,
  exportJxzxpxpj,
} from "@/api/jxjs/jxzxpxpj";
import { listJdx } from "@/api/jxjs/jdx";
import { listJxjsjbxx, getJxjsjbxx } from "@/api/jxjs/jxjsjbxx";

export default {
  name: "Jxzxpxpj",
  data() {
    return {
      jsxm: "",
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
      // 见习之星教师培训评价表格数据
      jxzxpxpjList: [],
      //基地校
      jdxOptions: [],
      //教师
      jsOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 培训表现字典
      pxbxOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jdxid: null,
        jsid: null,
        pxbx: null,
        pxnf: null,
        pxBeiyong: null,
        createUserid: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        pxbx: [
          { required: true, message: "培训表现不能为空", trigger: "blur" },
        ],
        pxnf: [
          { required: true, message: "培训年份不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getJdxList();
    this.getJsList();
    this.getList();
    this.getDicts("sys_dm_pxpj").then((response) => {
      this.pxbxOptions = response.data;
    });
  },
  methods: {
    /** 查询见习之星教师培训评价列表 */
    getList() {
      this.loading = true;
      listJxzxpxpj(this.queryParams).then((response) => {
        this.jxzxpxpjList = response.rows;
        console.log(this.jxzxpxpjList);
        this.total = response.total;
        this.loading = false;
      });
    },
    getJdxList() {
      listJdx(null).then((response) => {
        this.jdxOptions = response.rows;
      });
    },
    // 获取教师列表
    getJsList() {
      listJxjsjbxx(null).then((response) => {
        this.jsOptions = response.rows;
      });
    },
    // 培训表现字典翻译
    pxbxFormat(row, column) {
      return this.selectDictLabel(this.pxbxOptions, row.pxbx);
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
        jdxid: null,
        jsid: null,
        pxbx: null,
        pxnf: null,
        pxBeiyong: null,
        createUserid: null,
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
      this.jsxm = row.tsbzJxjsjbxx.name;
      const id = row.id || this.ids;
      this.reset();
      if (id == "" || id == null) {
        this.form.jsid = row.jsid;
        this.open = true;
        this.title = "添加见习之星教师培训评价";
      } else {
        getJxzxpxpj(id).then((response) => {
          this.form = response.data;
          this.open = true;
          this.title = "修改见习之星教师培训评价";
        });
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateJxzxpxpj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("评价成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJxzxpxpj(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("评价成功");
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
      const ids = row.id;
      if (ids == null || ids == "") {
        this.msgError("未评价，不可清空");
        return;
      }
      this.$confirm("是否确认清空见习之星教师培训评价的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delJxzxpxpj(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("清空成功");
        })
        .catch(function () {});
    },
  },
};
</script>
