<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="119px">
      <el-form-item label="学校名称" prop="xxmc">
        <el-input
          v-model="queryParams.xxmc"
          placeholder="请输入学校名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教育局学校代码" prop="jyjxxdm">
        <el-input
          v-model="queryParams.jyjxxdm"
          placeholder="请输入教育局学校代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学校类别码" prop="xxlbm">
        <el-select v-model="queryParams.xxlbm" placeholder="请选择学校类别码">
          <el-option
            v-for="dict in xxlbmOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学校办别" prop="xxbbm">
        <el-select v-model="queryParams.xxbbm" placeholder="请选择学校办别">
          <el-option
            v-for="dict in xxbbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="所在地区类别码" prop="szdqlbm">
        <el-select v-model="queryParams.szdqlbm" placeholder="请选择所在地区类别码">
          <el-option
            v-for="dict in szdqlbmOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="组织机构代码" prop="zzjgdm">
        <el-input
          v-model="queryParams.zzjgdm"
          placeholder="请输入组织机构代码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['qtjs:xxjbxx:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['qtjs:xxjbxx:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['qtjs:xxjbxx:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['qtjs:xxjbxx:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="xxjbxxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="学校名称" align="center" prop="xxmc" />
      <el-table-column label="区县" align="center" prop="countyid" />
      <el-table-column label="教育局学校代码" align="center" prop="jyjxxdm" />
      <el-table-column label="学校办别码" align="center" prop="xxbbm"  :formatter="xxbbFormat"/>
      <el-table-column label="学校类别码" align="center" prop="xxlbm" :formatter="xxlbmFormat" />
      <el-table-column label="组织机构代码" align="center" prop="zzjgdm" />
      <el-table-column label="当前学年" align="center" prop="dqxn" />
      <el-table-column label="当前学期" align="center" prop="dqxq" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['qtjs:xxjbxx:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['qtjs:xxjbxx:remove']"
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

    <!-- 添加或修改学校信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="教育局学校代码" prop="jyjxxdm">
          <el-input v-model="form.jyjxxdm" placeholder="请输入教育局学校代码" maxlength="20" />
        </el-form-item>
        <el-form-item label="学校名称" prop="xxmc">
          <el-input v-model="form.xxmc" placeholder="请输入学校名称" />
        </el-form-item>
        <el-form-item label="区县" prop="countyid">
          <el-input v-model="form.countyid" placeholder="请输入区县" maxlength="6" />
        </el-form-item>
        <el-form-item label="乡镇(街道)" prop="townid">
          <el-input v-model="form.townid" placeholder="请输入乡镇(街道)"  maxlength="9"/>
        </el-form-item>
        <el-form-item label="村委(居委)" prop="villageid">
          <el-input v-model="form.villageid" placeholder="请输入村委(居委)" maxlength="11" />
        </el-form-item>
        <el-form-item label="学校地址" prop="xxdz">
          <el-input v-model="form.xxdz" placeholder="请输入学校地址" />
        </el-form-item>
        <el-form-item label="学校英文名称" prop="xxywmc">
          <el-input v-model="form.xxywmc" placeholder="请输入学校英文名称" />
        </el-form-item>
        <el-form-item label="学校校长" prop="xxxz">
          <el-input v-model="form.xxxz" placeholder="请输入学校校长" />
        </el-form-item>
        <el-form-item label="党组织负责人" prop="dzzfzr">
          <el-input v-model="form.dzzfzr" placeholder="请输入党组织负责人" />
        </el-form-item>
        <el-form-item label="建校年月" prop="jxny">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.jxny"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择建校年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="学校办别码" prop="xxbbm">
          <el-select v-model="form.xxbbm" placeholder="请选择学校办别">
            <el-option
              v-for="dict in xxbbOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学校类别码" prop="xxlbm">
          <el-select v-model="form.xxlbm" placeholder="请选择学校类别">
            <el-option
              v-for="dict in xxlbmOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在地区类别码" prop="szdqlbm">
          <el-select v-model="form.szdqlbm" placeholder="请选择所在地区类别码">
            <el-option
              v-for="dict in szdqlbmOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在地经济属性码" prop="szdjjsxm">
          <el-select v-model="form.szdjjsxm" placeholder="请选择所在地经济属性码">
            <el-option
              v-for="dict in szdjjsxmOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在地民族属性码" prop="szdmzsxm">
          <el-input v-model="form.szdmzsxm" placeholder="请输入所在地民族属性码" />
        </el-form-item>
        <el-form-item label="入学年龄" prop="rxnl">
          <el-input v-model="form.rxnl" placeholder="请输入入学年龄" />
        </el-form-item>
        <el-form-item label="主教学语言码" prop="zjxyym">
          <el-input v-model="form.zjxyym" placeholder="请输入主教学语言码" />
        </el-form-item>
        <el-form-item label="辅教学语言码" prop="fjxyym">
          <el-input v-model="form.fjxyym" placeholder="请输入辅教学语言码" />
        </el-form-item>
        <el-form-item label="招生区域" prop="zsqy">
          <el-input v-model="form.zsqy" placeholder="请输入招生区域" />
        </el-form-item>
        <el-form-item label="邮政编码" prop="yzbm">
          <el-input v-model="form.yzbm" placeholder="请输入邮政编码" />
        </el-form-item>
        <el-form-item label="联系电话" prop="lxdh">
          <el-input v-model="form.lxdh" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="传真电话" prop="czdh">
          <el-input v-model="form.czdh" placeholder="请输入传真电话" />
        </el-form-item>
        <el-form-item label="电子信箱" prop="dzxx">
          <el-input v-model="form.dzxx" placeholder="请输入电子信箱" />
        </el-form-item>
        <el-form-item label="主页地址" prop="zydz">
          <el-input v-model="form.zydz" placeholder="请输入主页地址" />
        </el-form-item>
        <el-form-item label="组织机构代码" prop="zzjgdm">
          <el-input v-model="form.zzjgdm" placeholder="请输入组织机构代码" />
        </el-form-item>
        <el-form-item label="历史沿革" prop="lsyg">
          <el-input v-model="form.lsyg" placeholder="请输入历史沿革" />
        </el-form-item>
        <el-form-item label="当前学年" prop="dqxn">
          <el-input v-model="form.dqxn" placeholder="请输入当前学年" />
        </el-form-item>
        <el-form-item label="当前学期" prop="dqxq">
          <el-input v-model="form.dqxq" placeholder="请输入当前学期" />
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
import { listXxjbxx, getXxjbxx, delXxjbxx, addXxjbxx, updateXxjbxx, exportXxjbxx } from "@/api/qtjs/xxjbxx";

export default {
  name: "Xxjbxx",
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
      // 学校信息表格数据
      xxjbxxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 学校类别选项
      xxlbmOptions: [],
      // 学校办别选项
      xxbbOptions: [],
      // 所在地区类别码
      szdqlbmOptions: [],
      // 所在地经济属性
      szdjjsxmOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        xxdm: null,
        countyid: null,
        townid: null,
        villageid: null,
        jyjxxdm: null,
        xxmc: null,
        xxywmc: null,
        xxdz: null,
        xxxz: null,
        dzzfzr: null,
        jxny: null,
        xxbbm: null,
        xxlbm: null,
        szdqlbm: null,
        szdjjsxm: null,
        szdmzsxm: null,
        rxnl: null,
        zjxyym: null,
        fjxyym: null,
        zsqy: null,
        yzbm: null,
        lxdh: null,
        czdh: null,
        dzxx: null,
        zydz: null,
        zzjgdm: null,
        lsyg: null,
        dqxn: null,
        dqxq: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jyjxxdm: [
          { required: true, message: "教育局学校代码不能为空", trigger: "blur" },
        ],
        xxmc: [
          { required: true, message: "学校名称不能为空", trigger: "blur" },
        ],
        xxlbm: [
          { required: true, message: "学校类别不能为空", trigger: "blur" },
        ],
        xxbbm: [
          { required: true, message: "学校办别不能为空", trigger: "blur" },
        ],
        szdqlbm: [
          { required: true, message: "所在地区类别不能为空", trigger: "blur" },
        ],
        countyid: [
          { required: true, message: "学校区县不能为空", trigger: "blur" },
        ],
        townid: [
          { required: true, message: "学校乡镇不能为空", trigger: "blur" },
        ],
        xxdz: [
          { required: true, message: "学校地址不能为空", trigger: "blur" },
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_bxlx").then((response) => {
      this.xxlbmOptions = response.data;
    });
    this.getDicts("sys_dm_xxbb").then((response) => {
      this.xxbbOptions = response.data;
    });
    this.getDicts("sys_dm_szdqlb").then((response) => {
      this.szdqlbmOptions = response.data;
    });
    this.getDicts("sys_dm_szdjjsxm").then((response) => {
      this.szdjjsxmOptions = response.data;
    });
  },
  methods: {
    
    /** 查询学校信息列表 */
    getList() {
      this.loading = true;
      listXxjbxx(this.queryParams).then(response => {
        this.xxjbxxList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 字典翻译
    xxlbmFormat(row, column) {
      return this.selectDictLabel(this.xxlbmOptions, row.xxlbm);
    },
    // 字典翻译
    xxbbFormat(row, column) {
      return this.selectDictLabel(this.xxbbOptions, row.xxbbm);
    },
    // 字典翻译
    szdqlbmFormat(row, column) {
      return this.selectDictLabel(this.szdqlbmOptions, row.szdqlbm);
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
        xxdm: null,
        countyid: null,
        townid: null,
        villageid: null,
        jyjxxdm: null,
        xxmc: null,
        xxywmc: null,
        xxdz: null,
        xxxz: null,
        dzzfzr: null,
        jxny: null,
        xxbbm: null,
        xxlbm: null,
        szdqlbm: null,
        szdjjsxm: null,
        szdmzsxm: null,
        rxnl: null,
        zjxyym: null,
        fjxyym: null,
        zsqy: null,
        yzbm: null,
        lxdh: null,
        czdh: null,
        dzxx: null,
        zydz: null,
        zzjgdm: null,
        lsyg: null,
        dqxn: null,
        dqxq: null,
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加学校信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getXxjbxx(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学校信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateXxjbxx(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addXxjbxx(this.form).then(response => {
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
      this.$confirm('是否确认删除学校信息编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delXxjbxx(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有学校信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportXxjbxx(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
