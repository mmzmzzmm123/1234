<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
       <el-form-item label="所属单位" prop="deptId">
        <treeselect
          v-model="queryParams.deptId"
          :options="deptOptions"
          :disable-branch-nodes="true"
          :show-count="true"
          placeholder="请选择所属单位"
          style="width: 200px"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="gbid">
        <el-select
          v-model="queryParams.gbid"
          filterable
          placeholder="请选择或输入姓名"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in gbOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="考核学年" prop="xn">
        <el-select
            v-model="queryParams.xn"
            placeholder="请选择考核学年"
          >
            <el-option
              v-for="dict in xnOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="考核结果" prop="khjg">
        <el-select
            v-model="queryParams.khjg"
            placeholder="请选择考核学年"
          >
            <el-option
              v-for="dict in khjgOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="年度" prop="nd">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.nd"
          type="year"
          value-format="yyyy-MM-dd"
          placeholder="选择年度"
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
          v-hasPermi="['gbxxgl:gbndkh:add']"
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
          v-hasPermi="['gbxxgl:gbndkh:edit']"
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
          v-hasPermi="['gbxxgl:gbndkh:remove']"
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
      :data="gbndkhList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="姓名" align="center" prop="tsbzGbjbqk.name" />
      <el-table-column
        label="考核学年"
        align="center"
        prop="xn"
        :formatter="xnFormat"
      />
      <el-table-column label="考核结果" align="center" prop="khjg" :formatter="khjgFormat" />
      <el-table-column label="考核年度" align="center" prop="nd">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nd, "{y}") }}</span>
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
            v-hasPermi="['gbxxgl:gbndkh:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gbxxgl:gbndkh:remove']"
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

    <!-- 添加或修改年度考核(干部管理-评价与考核)对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
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
        <el-form-item label="姓名" prop="gbid">
          <el-select
            v-model="form.gbid"
            placeholder="请选择姓名"
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
        <el-form-item label="考核学年" prop="xn">
          <el-select
            v-model="form.xn"
            placeholder="请选择考核学年"
          >
            <el-option
              v-for="dict in xnOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考核结果" prop="khjg">
           <el-select
            v-model="form.khjg"
            placeholder="请选择考核学年"
          >
            <el-option
              v-for="dict in khjgOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考核年度" prop="nd">
          <el-date-picker
            clearable
            size="small"
            class="my-date-picker"
            v-model="form.nd"
            type="year"
            value-format="yyyy-MM-dd"
            placeholder="选择考核年度"
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
  listGbndkh,
  getGbndkh,
  delGbndkh,
  addGbndkh,
  updateGbndkh,
} from "@/api/gbxxgl/gbndkh";
import { listGbjbqk, getGbjbqk } from "@/api/gbxxgl/gbjbqk";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Gbndkh",
  components: { Treeselect },
  data() {
    return {
      flag: true,
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
      // 年度考核(干部管理-评价与考核)表格数据
      gbndkhList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 部门选项
      deptOptions: [],
      // 干部选项
      gbOptions: [],
      // 学年字典
      xnOptions: [],
       // 考核结果字典
      khjgOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gbid: null,
        xn: null,
        khjg: null,
        nd: null,
        isdel: null,
        createUserid: null,
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
        xn: [{ required: true, message: "考核学年不能为空", trigger: "blur" }],
        khjg: [
          { required: true, message: "考核结果不能为空", trigger: "blur" },
        ],
        nd: [{ required: true, message: "考核年度不能为空", trigger: "blur" }],
      },
    };
  },
  watch: {
    // 监听deptId
    "form.deptId": "handleBucketClick",
    "queryParams.deptId": "handleBucketClick",
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getDicts("sys_gbxn").then((response) => {
      this.xnOptions = response.data;
    });
    this.getDicts("sys_khjg").then((response) => {
      this.khjgOptions = response.data;
    });
  },
  methods: {
    // 字典翻译
    khjgFormat(row, column) {
      return this.selectDictLabel(this.khjgOptions, row.khjg);
    },
    // 字典翻译
    xnFormat(row, column) {
      return this.selectDictLabel(this.xnOptions, row.xn);
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
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then((response) => {
        this.deptOptions = response.data;
      });
    },
    /** 查询年度考核(干部管理-评价与考核)列表 */
    getList() {
      this.loading = true;
      listGbndkh(this.queryParams).then((response) => {
        this.gbndkhList = response.rows;
        this.total = response.total;
        this.loading = false;
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
        gbid: null,
        xn: null,
        khjg: null,
        nd: null,
        isdel: null,
        createUserid: null,
        createTime: null,
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
      this.open = true;
      this.flag = false;
      this.title = "添加年度考核(干部管理-评价与考核)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getGbndkh(id).then((response) => {
        this.form = response.data;
        this.form.deptId = response.data.tsbzGbjbqk.deptId;
        this.open = true;
        this.flag = true;
        this.title = "修改年度考核(干部管理-评价与考核)";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateGbndkh(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addGbndkh(this.form).then((response) => {
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
        '是否确认删除年度考核(干部管理-评价与考核)编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delGbndkh(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
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