<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="gbid">
        <el-select v-model="queryParams.gbid" filterable  placeholder="请选择或输入姓名" clearable size="small">
          <el-option
            v-for="dict in gbmcOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="培训级别" prop="pxjb">
        <el-select v-model="queryParams.pxjb" placeholder="请选择培训级别" clearable size="small">
          <el-option
            v-for="dict in pxjbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="职务" prop="zw">
        <el-select v-model="queryParams.zw" placeholder="请选择职务" clearable size="small">
          <el-option
            v-for="dict in zwOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="起始年月" prop="qsny">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.qsny"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择起始年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="终止年月" prop="zzny">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.zzny"
          type="month"
          value-format="yyyy-MM"
          placeholder="选择终止年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['gbxxgl:gbpxjl:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['gbxxgl:gbpxjl:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['gbxxgl:gbpxjl:remove']"
        >删除</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="gbpxjlList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="姓名" align="center" prop="gbid" :formatter="gbmcFormat"/>
      <el-table-column label="培训班名称" align="center" prop="pxbmc" />
      <el-table-column label="培训地点" align="center" prop="pxdd" />
      <el-table-column label="培训级别" align="center" prop="pxjb" :formatter="pxjbFormat"/>
      <el-table-column label="职务" align="center" prop="zw" :formatter="zwFormat"/>
      <el-table-column label="起始年月" align="center" prop="qsny">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.qsny, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="终止年月" align="center" prop="zzny" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.zzny, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gbxxgl:gbpxjl:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gbxxgl:gbpxjl:remove']"
          >删除</el-button>
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

    <!-- 添加或修改干部培训经历对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="归属部门" prop="deptId" >
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
          <el-select v-model="form.gbid" placeholder="请选择姓名" :disabled="flag">
            <el-option
              v-for="dict in gbOptions"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职务" prop="zw">
          <el-select v-model="form.zw" placeholder="请选择职务">
            <el-option
              v-for="dict in zwOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="培训级别" prop="pxjb">
          <el-select v-model="form.pxjb" placeholder="请选择培训级别">
            <el-option
              v-for="dict in pxjbOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="起始年月" prop="qsny">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.qsny"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择起始年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="终止年月" prop="zzny">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.zzny"
            type="month"
            value-format="yyyy-MM"
            placeholder="选择终止年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="培训地点" prop="pxdd">
          <el-input v-model="form.pxdd" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="培训班名称" prop="pxbmc">
          <el-input v-model="form.pxbmc" type="textarea" placeholder="请输入内容" />
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
import { listGbpxjl, getGbpxjl, delGbpxjl, addGbpxjl, updateGbpxjl, exportGbpxjl } from "@/api/gbxxgl/gbpxjl";
import { listGbjbqk, getGbjbqk } from "@/api/gbxxgl/gbjbqk";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Gbpxjl",
  components: { Treeselect },
  data() {
    return {
      flag: null,
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
      // 干部培训经历表格数据
      gbpxjlList: [],
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
      // 职务字典
      zwOptions: [],
      // 培训级别字典
      pxjbOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        gbid: null,
        pxbmc: null,
        pxdd: null,
        pxjb: null,
        zw: null,
        qsny: null,
        zzny: null,
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
        deptId: [
          { required: true, message: "部门不能为空", trigger: "blur" }
        ],
        gbid: [
          { required: true, message: "干部姓名不能为空", trigger: "blur" },
        ],
        qsny: [
          { required: true, message: "任职起始年月不能为空", trigger: "blur" },
        ],
        zzny: [
          { required: true, message: "任职终止年月不能为空", trigger: "blur" },
        ],
      }
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
    this.getDicts("sys_dm_xrxzzw").then(response => {
      this.zwOptions = response.data;
    });
    this.getDicts("sys_dm_pxjb").then(response => {
      this.pxjbOptions = response.data;
    });
  },
  methods: {
    /** 查询干部培训经历列表 */
    getList() {
      this.loading = true;
      listGbpxjl(this.queryParams).then(response => {
        this.gbpxjlList = response.rows;
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

    // 干部字典翻译
    gbmcFormat(row, column) {
      var actions = [];
      var datas = this.gbmcOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.gbid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
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
    // 职务字典翻译
    zwFormat(row, column) {
      return this.selectDictLabel(this.zwOptions, row.zw);
    },
    // 职务字典翻译
    pxjbFormat(row, column) {
      return this.selectDictLabel(this.pxjbOptions, row.pxjb);
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
        pxbmc: null,
        pxdd: null,
        pxjb: null,
        zw: null,
        qsny: null,
        zzny: null,
        createUserid: null,
        createTime: null,
        deptId: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getTreeselect();
      this.flag = false;
      this.open = true;
      this.title = "添加干部培训经历";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getGbpxjl(id).then(response => {
        this.form = response.data;
        this.form.deptId = response.data.deptId;
        this.flag = true;
        this.open = true;
        this.title = "修改干部培训经历";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateGbpxjl(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addGbpxjl(this.form).then(response => {
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
      this.$confirm('是否确认删除干部培训经历编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delGbpxjl(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有干部培训经历数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportGbpxjl(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
