<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="102px"
    >
      <el-form-item label="干部姓名" prop="gbid">
        <el-select
          v-model="queryParams.gbid"
          filterable
          placeholder="请选择或输入干部姓名"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in gbmcOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="职务级别" prop="jibie">
        <el-select
          v-model="queryParams.jibie"
          placeholder="请选择职务级别"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in jibieOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="职务名称" prop="zwmc">
        <el-input
          v-model="queryParams.zwmc"
          placeholder="请输入职务名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任职起始年月" prop="rzqsny">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.rzqsny"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择任职起始年月"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="任职终止年月" prop="rzzzny">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.rzzzny"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择任职终止年月"
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
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['gbxxgl:gbshzw:add']"
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
          v-hasPermi="['gbxxgl:gbshzw:edit']"
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
          v-hasPermi="['gbxxgl:gbshzw:remove']"
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
      :data="gbshzwList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="干部姓名" align="center" prop="tsbzGbjbqk.name" />
      <el-table-column label="任职起始年月" align="center" prop="rzqsny">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.rzqsny, "{y}-{m}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任职终止年月" align="center" prop="rzzzny">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.rzzzny, "{y}-{m}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="届别"
        align="center"
        prop="jb"
        :formatter="jbFormat"
      />
      <el-table-column
        label="职务级别"
        align="center"
        prop="jibie"
        :formatter="jibieFormat"
      />
      <el-table-column label="职务名称" align="center" prop="zwmc" />
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gbxxgl:gbshzw:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gbxxgl:gbshzw:remove']"
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

    <!-- 添加或修改干部任职情况-社会职务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="归属部门" prop="deptId">
          <treeselect
            v-model="form.deptId"
            :options="deptOptions"
            :disable-branch-nodes="true"
            :show-count="true"
            placeholder="请选择归属部门"
            :disabled="flag"
          />
        </el-form-item>
        <el-form-item label="干部姓名" prop="gbid">
          <el-select
            v-model="form.gbid"
            placeholder="干部姓名"
            :disabled="flag"
          >
            <el-option
              v-for="dict in gbOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="届别" prop="jb">
          <el-select v-model="form.jb" placeholder="请选择届别">
            <el-option
              v-for="dict in jbOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职务级别" prop="jibie">
          <el-select v-model="form.jibie" placeholder="请选择职务级别">
            <el-option
              v-for="dict in jibieOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职务名称" prop="zwmc">
          <el-input v-model="form.zwmc" placeholder="请输入职务名称" />
        </el-form-item>
        <el-form-item label="任职起始年月" prop="rzqsny">
          <el-date-picker
            clearable
            size="small"
            v-model="form.rzqsny"
            class="my-date-picker"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择任职起始年月"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="任职终止年月" prop="rzzzny">
          <el-date-picker
            clearable
            size="small"
            v-model="form.rzzzny"
            class="my-date-picker"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择任职终止年月"
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
  listGbshzw,
  getGbshzw,
  delGbshzw,
  addGbshzw,
  updateGbshzw,
  exportGbshzw,
} from "@/api/gbxxgl/gbshzw";
import { listGbjbqk, getGbjbqk } from "@/api/gbxxgl/gbjbqk";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Gbshzw",
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      flag: null,
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
      // 干部任职情况-社会职务表格数据
      gbshzwList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 届别字典
      jbOptions: [],
      // 职务级别字典
      jibieOptions: [],
      // 部门选项
      deptOptions: [],
      // 干部选项
      gbOptions: [],
      // 干部名称
      gbmcOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gbid: null,
        rzqsny: null,
        rzzzny: null,
        jb: null,
        jibie: null,
        zwmc: null,
        createuser: null,
        createtime: null,
        deptId: null,
        dqzt: "00",
      },
      // 查询参数
      queryParams_gb: {
        deptId: null,
        dqzt: "00",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deptId: [{ required: true, message: "部门不能为空", trigger: "blur" }],
        gbid: [
          { required: true, message: "干部姓名不能为空", trigger: "blur" },
        ],
        rzqsny: [
          { required: true, message: "任职起始年月不能为空", trigger: "blur" },
        ],
        rzzzny: [
          { required: true, message: "任职终止年月不能为空", trigger: "blur" },
        ],
      },
    };
  },
  watch: {
    // 监听deptId
    "form.deptId": "handleBucketClick",
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getGbjbqkList();
    this.getDicts("sys_dm_jiebie").then((response) => {
      this.jbOptions = response.data;
    });
    this.getDicts("sys_dm_jibie").then((response) => {
      this.jibieOptions = response.data;
    });
  },
  methods: {
    /** 查询干部任职情况-社会职务列表 */
    getList() {
      this.loading = true;
      listGbshzw(this.queryParams).then((response) => {
        this.gbshzwList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询干部列表 */
    getGbjbqkList() {
      this.loading = true;
      listGbjbqk(null).then((response) => {
        this.gbmcOptions = response.rows;
      });
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 部门监听
    handleBucketClick(value) {
      // console.log(value);
      this.queryParams_gb.deptId = value;
      if (this.queryParams_gb.deptId != null) {
        listGbjbqk(this.queryParams_gb).then((response) => {
          // console.log(response.rows);
          this.gbOptions = response.rows;
        });
      }
    },
    // 届别字典翻译
    jbFormat(row, column) {
      return this.selectDictLabel(this.jbOptions, row.jb);
    },
    // 职务级别字典翻译
    jibieFormat(row, column) {
      return this.selectDictLabel(this.jibieOptions, row.jibie);
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
        gbid: null,
        rzqsny: null,
        rzzzny: null,
        jb: null,
        jibie: null,
        zwmc: null,
        createuser: null,
        createtime: null,
        deptId: null,
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      this.flag = false;
      this.open = true;
      this.title = "添加干部任职情况-社会职务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();

      this.getTreeselect();
      const id = row.id || this.ids;
      getGbshzw(id).then((response) => {
        this.form = response.data;
        this.form.deptId = response.data.tsbzGbjbqk.deptId;
        this.flag = true;
        this.open = true;
        this.title = "修改干部任职情况-社会职务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateGbshzw(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addGbshzw(this.form).then((response) => {
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
        '是否确认删除干部任职情况-社会职务编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delGbshzw(ids);
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
      this.$confirm("是否确认导出所有干部任职情况-社会职务数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportGbshzw(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
  },
};
</script>
<style lang="scss" scoped>
.el-select {
  width: 100%;
}
.my-date-picker {
  width: 100%;
}
</style>