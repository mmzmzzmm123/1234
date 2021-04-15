<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="70px"
    >
      <el-form-item label="任务类型" prop="rwlx">
        <el-select
          v-model="queryParams.rwlx"
          size="small"
          placeholder="请选择任务类型"
        >
          <el-option
            v-for="dict in rwlxOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="任务内容" prop="rwnr">
        <el-select
          v-model="queryParams.rwnr"
          size="small"
          placeholder="请选择任务内容"
        >
          <el-option
            v-for="dict in rwnrOptions"
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
          v-hasPermi="['jyykhgl:jyykhrw:add']"
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
          v-hasPermi="['jyykhgl:jyykhrw:edit']"
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
          v-hasPermi="['jyykhgl:jyykhrw:remove']"
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
      :data="jyykhrwList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column
        label="任务类型"
        align="center"
        prop="rwlx"
        :formatter="xrlxFormat"
      />
      <el-table-column
        label="任务内容"
        align="center"
        prop="rwnr"
        :formatter="rwnrFormat"
      />
      <el-table-column
        label="考核周期"
        align="center"
        prop="khzqkssj"
        width="180"
      >
        <template slot-scope="scope">
          <span
            >{{ parseTime(scope.row.khzqkssj, "{y}/{m}/{d}") }} -
            {{ parseTime(scope.row.khzqjssj, "{y}/{m}/{d}") }}</span
          >
        </template>
      </el-table-column>
      <el-table-column label="数量要求" align="center" prop="slyq" />
      <el-table-column
        label="是否必选"
        align="center"
        prop="fsbx"
        :formatter="typeFormat"
      />
      <!-- <el-table-column label="是否删除" align="center" prop="isdel" /> -->
      <el-table-column label="任务说明" align="center" prop="rwsm" />
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
            v-hasPermi="['jyykhgl:jyykhrw:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jyykhgl:jyykhrw:remove']"
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

    <!-- 添加或修改教研员考核任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务类型" prop="rwlx">
          <el-select v-model="form.rwlx" placeholder="请选择任务类型">
            <el-option
              v-for="dict in rwlxOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="任务内容" prop="rwnr">
          <el-select v-model="form.rwnr" placeholder="请选择任务内容">
            <el-option
              v-for="dict in rwnrOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="考核周期" prop="khzqkssj">
          <!-- <el-date-picker
            v-model="form.khzqkssj"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          >
          </el-date-picker> -->
          <el-date-picker
            clearable
            size="small"
            class="my-date-picker"
            v-model="form.khzqkssj"
            type="daterange"
            value-format="yyyy-MM-dd"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="数量要求" prop="slyq">
          <el-input v-model="form.slyq" placeholder="请输入数量要求" />
        </el-form-item>
        <el-form-item label="是否必选" prop="fsbx">
          <el-radio-group v-model="form.fsbx">
            <el-radio
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
              >{{ dict.dictLabel }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <!-- <el-form-item label="是否删除" prop="isdel">
          <el-input v-model="form.isdel" placeholder="请输入是否删除" />
        </el-form-item> -->
        <el-form-item label="任务说明" prop="rwsm">
          <el-input
            v-model="form.rwsm"
            type="textarea"
            placeholder="请输入内容"
          />
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
  listJyykhrw,
  getJyykhrw,
  delJyykhrw,
  addJyykhrw,
  updateJyykhrw,
  exportJyykhrw,
} from "@/api/jyykhgl/jyykhrw";

export default {
  name: "Jyykhrw",
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
      // 教研员考核任务表格数据
      jyykhrwList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //任务类型
      rwlxOptions: [],
      //任务内容
      rwnrOptions: [],
      //是否必选
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rwmc: null,
        rwlx: null,
        rwnr: null,
        khbm: null,
        khzq: null,
        slyq: null,
        fsbx: null,
        isdel: null,
        rwsm: null,
        khzqkssj: null,
        khzqjssj: null,
        parentId: null,
        xn: null,
        xq: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rwlx: [
          { required: true, message: "任务类型不能为空", trigger: "blur" },
        ],
        rwnr: [
          { required: true, message: "任务内容不能为空", trigger: "blur" },
        ],
        khzqkssj: [
          { required: true, message: "考核周期不能为空", trigger: "blur" },
        ],
        slyq: [
          { required: true, message: "数量要求不能为空", trigger: "blur" },
        ],
        fsbx: [
          { required: true, message: "是否必选不能为空", trigger: "blur" },
        ],
        rwsm: [
          { required: true, message: "任务内容不能为空", trigger: "blur" },
        ],
      },
    };
  },
  watch: {
    // 监听
    "form.rwlx": "handleBucketClick",
    "queryParams.rwlx": "handleBucketClick",
  },
  created() {
    this.queryParams.parentId = this.$route.params && this.$route.params.id;
    this.getList();
    this.getDicts("sys_dm_jyykhrwlx").then((response) => {
      this.rwlxOptions = response.data;
    });
    this.getDicts("sys_dm_jyykhrwnr").then((response) => {
      this.rwnrOptions = response.data;
    });
    this.getDicts("sys_yes_no").then((response) => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    // 监听
    handleBucketClick(value) {
      //console.log(value);
      this.getDictsLikeDeptids("sys_dm_jyykhrwnr").then((response) => {
        //console.log(response.data);
        var item = [];
        response.data.forEach((res) => {
          //console.log(res.parentId);
          if (res.parentId == "sys_dm_jyykhrwlx" + value) {
            item.push(res);
          }
        });
        this.rwnrOptions = item;
      });
    },
    // 任务类型字典翻译
    xrlxFormat(row, column) {
      return this.selectDictLabel(this.rwlxOptions, row.rwlx);
    },
    // 任务内容字典翻译
    rwnrFormat(row, column) {
      return this.selectDictLabel(this.rwnrOptions, row.rwnr);
    },
    // 是否字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.fsbx);
    },
    /** 查询教研员考核任务列表 */
    getList() {
      this.loading = true;
      listJyykhrw(this.queryParams).then((response) => {
        this.jyykhrwList = response.rows;
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
        rwmc: null,
        rwlx: null,
        rwnr: null,
        khbm: null,
        khzq: null,
        slyq: null,
        fsbx: null,
        isdel: null,
        createTime: null,
        rwsm: null,
        khzqkssj: null,
        khzqjssj: null,
        parentId: null,
        xn: null,
        xq: null,
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
      this.form.parentId = this.queryParams.parentId;
      this.title = "添加教研员考核任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      var myArray = new Array(2);
      getJyykhrw(id).then((response) => {
        this.form = response.data;
        myArray[0] = response.data.khzqkssj;
        myArray[1] = response.data.khzqjssj;
        //console.log(myArray);
        this.form.khzqkssj = myArray;
        this.open = true;
        this.title = "修改教研员考核任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          var v1 = this.form.khzqkssj[0];
          var v2 = this.form.khzqkssj[1];
          this.form.khzqkssj = v1;
          this.form.khzqjssj = v2;
          if (this.form.id != null) {
            updateJyykhrw(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJyykhrw(this.form).then((response) => {
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
        '是否确认删除教研员考核任务编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delJyykhrw(ids);
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
</style>
