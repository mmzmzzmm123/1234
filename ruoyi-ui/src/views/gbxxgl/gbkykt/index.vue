<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="干部姓名" prop="gbid">
        <el-select
          v-model="queryParams.gbid"
          filterable
          placeholder="请选择或输入姓名"
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
      <el-form-item label="课题名称" prop="lxktmc">
        <el-input
          v-model="queryParams.lxktmc"
          placeholder="请输入立项课题名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="立项日期" prop="lxrq">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.lxrq"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择立项日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结题日期" prop="jtrq">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.jtrq"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择结题日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="研究领域" prop="yjly">
        <el-select v-model="queryParams.yjly" placeholder="请选择研究领域">
          <el-option
            v-for="dict in yjlyOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课题级别" prop="lxktjb">
        <el-select v-model="queryParams.lxktjb" placeholder="请选择课题级别">
          <el-option
            v-for="dict in lxktjbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="课题类别" prop="lxktlb">
        <el-select v-model="queryParams.lxktlb" placeholder="请选择课题类别">
          <el-option
            v-for="dict in lxktlbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="鉴定结果" prop="jdjg">
        <el-select v-model="queryParams.jdjg" placeholder="请选择鉴定结果">
          <el-option
            v-for="dict in jdjgOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="承担角色" prop="cdjs">
        <el-select v-model="queryParams.cdjs" placeholder="请选择承担角色">
          <el-option
            v-for="dict in cdjsOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
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
          v-hasPermi="['gbxxgl:gbkykt:add']"
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
          v-hasPermi="['gbxxgl:gbkykt:edit']"
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
          v-hasPermi="['gbxxgl:gbkykt:remove']"
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
      :data="gbkyktList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="干部姓名" align="center" prop="tsbzGbjbqk.name" />
      <el-table-column
        label="研究领域"
        align="center"
        prop="yjly"
        :formatter="yjlyFormat"
      />
      <el-table-column label="课题名称" align="center" prop="lxktmc" />
      <el-table-column
        label="课题级别"
        align="center"
        prop="lxktjb"
        :formatter="lxktjbFormat"
      />
      <el-table-column
        label="课题类别"
        align="center"
        prop="lxktlb"
        :formatter="lxktlbFormat"
      />
      <el-table-column label="立项日期" align="center" prop="lxrq">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.lxrq, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结题日期" align="center" prop="jtrq">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.jtrq, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="鉴定结果"
        align="center"
        prop="jdjg"
        :formatter="jdjgFormat"
      />
      <el-table-column
        label="承担角色"
        align="center"
        prop="cdjs"
        :formatter="cdjsFormat"
      />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width edit-btns"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:gbkykt:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:gbkykt:remove']"
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

    <!-- 添加或修改科研课题(干部管理)对话框 -->
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
        <el-form-item label="研究领域" prop="yjly">
          <el-select v-model="form.yjly" placeholder="请选择研究领域">
            <el-option
              v-for="dict in yjlyOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课题名称" prop="lxktmc">
          <el-input v-model="form.lxktmc" placeholder="请输入立项课题名称" />
        </el-form-item>
        <el-form-item label="课题级别" prop="lxktjb">
          <el-select v-model="form.lxktjb" placeholder="请选择课题级别">
            <el-option
              v-for="dict in lxktjbOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课题类别" prop="lxktlb">
          <el-select v-model="form.lxktlb" placeholder="请选择课题类别">
            <el-option
              v-for="dict in lxktlbOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="立项日期" prop="lxrq">
          <el-date-picker
            clearable
            size="small"
            v-model="form.lxrq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择立项日期"
            class="my-date-picker"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结题日期" prop="jtrq">
          <el-date-picker
            clearable
            size="small"
            v-model="form.jtrq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结题日期"
            class="my-date-picker"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="鉴定结果" prop="jdjg">
          <el-select v-model="form.jdjg" placeholder="请选择鉴定结果">
            <el-option
              v-for="dict in jdjgOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="承担角色" prop="cdjs">
          <el-select v-model="form.cdjs" placeholder="请选择承担角色">
            <el-option
              v-for="dict in cdjsOptions"
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
  listGbkykt,
  getGbkykt,
  delGbkykt,
  addGbkykt,
  updateGbkykt,
} from "@/api/gbxxgl/gbkykt";
import { listGbjbqk, getGbjbqk } from "@/api/gbxxgl/gbjbqk";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Gbkykt",
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
      // 科研课题(干部管理)表格数据
      gbkyktList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 部门选项
      deptOptions: [],
      // 干部选项
      gbOptions: [],
      // 干部名称
      gbmcOptions: [],
      // 研究领域
      yjlyOptions: [],
      // 立项课题级别
      lxktjbOptions: [],
      // 立项课题类别
      lxktlbOptions: [],
      // 鉴定结果
      jdjgOptions: [],
      // 承担角色
      cdjsOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gbid: null,
        yjly: null,
        lxktmc: null,
        lxktjb: null,
        lxktlb: null,
        lxrq: null,
        jtrq: null,
        jdjg: null,
        cdjs: null,
        isdel: null,
        createUserid: null,
        deptId: null,
      },
      // 查询参数
      queryParams_gb: {
        deptId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deptId: [{ required: true, message: "部门不能为空", trigger: "blur" }],
        gbid: [
          { required: true, message: "干部姓名不能为空", trigger: "blur" },
        ],
        yjly: [
          { required: true, message: "研究领域不能为空", trigger: "blur" },
        ],
        lxktmc: [
          { required: true, message: "立项课题名称不能为空", trigger: "blur" },
        ],
        lxktjb: [
          { required: true, message: "立项课题级别不能为空", trigger: "blur" },
        ],
        lxktlb: [
          { required: true, message: "立项课题类别不能为空", trigger: "blur" },
        ],
        lxrq: [
          { required: true, message: "立项日期不能为空", trigger: "blur" },
        ],
        jtrq: [
          { required: true, message: "结题日期不能为空", trigger: "blur" },
        ],
        jdjg: [
          { required: true, message: "鉴定结果不能为空", trigger: "blur" },
        ],
        cdjs: [
          { required: true, message: "承担角色不能为空", trigger: "blur" },
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
    this.getDicts("sys_gbyjly").then((response) => {
      this.yjlyOptions = response.data;
    });
    this.getDicts("sys_gblxktjb").then((response) => {
      this.lxktjbOptions = response.data;
    });
    this.getDicts("sys_gblxktlb").then((response) => {
      this.lxktlbOptions = response.data;
    });
    this.getDicts("sys_gbjdjg").then((response) => {
      this.jdjgOptions = response.data;
    });
    this.getDicts("sys_gbcdjs").then((response) => {
      this.cdjsOptions = response.data;
    });
  },
  methods: {
    // 字典翻译
    yjlyFormat(row, column) {
      return this.selectDictLabel(this.yjlyOptions, row.yjly);
    },
    // 字典翻译
    lxktjbFormat(row, column) {
      return this.selectDictLabel(this.lxktjbOptions, row.lxktjb);
    },
    // 字典翻译
    lxktlbFormat(row, column) {
      return this.selectDictLabel(this.lxktlbOptions, row.lxktlb);
    },
    // 字典翻译
    jdjgFormat(row, column) {
      return this.selectDictLabel(this.jdjgOptions, row.jdjg);
    },
    // 字典翻译
    cdjsFormat(row, column) {
      return this.selectDictLabel(this.cdjsOptions, row.cdjs);
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
    /** 查询科研课题(干部管理)列表 */
    getList() {
      this.loading = true;
      listGbkykt(this.queryParams).then((response) => {
        this.gbkyktList = response.rows;
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
        yjly: null,
        lxktmc: null,
        lxktjb: null,
        lxktlb: null,
        lxrq: null,
        jtrq: null,
        jdjg: null,
        cdjs: null,
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
      this.title = "添加科研课题(干部管理)";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getGbkykt(id).then((response) => {
        this.form = response.data;
        this.form.deptId = response.data.tsbzGbjbqk.deptId;
        this.open = true;
        this.flag = true;
        this.title = "修改科研课题(干部管理)";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateGbkykt(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addGbkykt(this.form).then((response) => {
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
        '是否确认删除科研课题(干部管理)编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delGbkykt(ids);
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
.edit-btns {
  .el-button {
    display: block;
    margin: 0 auto;
  }
}
.no-margin ::v-deep.el-form-item__content {
  margin: 0 !important;
}
</style>