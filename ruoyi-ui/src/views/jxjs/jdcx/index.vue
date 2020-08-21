<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="方案编号" prop="faid">
        <el-input
          v-model="queryParams.faid"
          placeholder="请输入方案编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教师编号" prop="jsid">
        <el-input
          v-model="queryParams.jsid"
          placeholder="请输入教师编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="当前状态" prop="dqzt">
        <el-select v-model="queryParams.dqzt" placeholder="请选择当前状态" clearable size="small">
          <el-option
            v-for="dict in dqztOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="createuserid">
        <el-input
          v-model="queryParams.createuserid"
          placeholder="请输入创建人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="基地校审核人" prop="jdxshr">
        <el-input
          v-model="queryParams.jdxshr"
          placeholder="请输入基地校审核人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="基地校审核状态" prop="jdxshzt">
        <el-select v-model="queryParams.jdxshzt" placeholder="请选择基地校审核状态" clearable size="small">
          <el-option
            v-for="dict in jdxshztOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上报理由" prop="sbly">
        <el-input
          v-model="queryParams.sbly"
          placeholder="请输入上报理由"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="区级审核人" prop="qjshr">
        <el-input
          v-model="queryParams.qjshr"
          placeholder="请输入区级审核人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="区级审核状态" prop="qjshzt">
        <el-select v-model="queryParams.qjshzt" placeholder="请选择区级审核状态" clearable size="small">
          <el-option
            v-for="dict in qjshztOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="区级审核意见" prop="qjshyj">
        <el-select v-model="queryParams.qjshyj" placeholder="请选择区级审核意见" clearable size="small">
          <el-option
            v-for="dict in qjshyjOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="基地排序" prop="jdpx">
        <el-input
          v-model="queryParams.jdpx"
          placeholder="请输入基地排序"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="案例分析得分" prop="alfxdf">
        <el-input
          v-model="queryParams.alfxdf"
          placeholder="请输入案例分析得分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="教案设计得分" prop="jasjdf">
        <el-input
          v-model="queryParams.jasjdf"
          placeholder="请输入教案设计得分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="钢笔字得分" prop="gbzdf">
        <el-input
          v-model="queryParams.gbzdf"
          placeholder="请输入钢笔字得分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="综合得分" prop="zhdf">
        <el-input
          v-model="queryParams.zhdf"
          placeholder="请输入综合得分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="成绩导入创建时间" prop="cjdrcreateTime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.cjdrcreateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择成绩导入创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="面试确认" prop="msqr">
        <el-input
          v-model="queryParams.msqr"
          placeholder="请输入面试确认"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="面试确认时间" prop="msqrcreateTime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.msqrcreateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择面试确认时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="面试结果模拟课堂教学" prop="msjgmnktjxdf">
        <el-input
          v-model="queryParams.msjgmnktjxdf"
          placeholder="请输入面试结果模拟课堂教学"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="演讲得分" prop="yjdf">
        <el-input
          v-model="queryParams.yjdf"
          placeholder="请输入演讲得分"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="综合得分2" prop="zhdf2">
        <el-input
          v-model="queryParams.zhdf2"
          placeholder="请输入综合得分2"
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
          v-hasPermi="['jxjs:jdcx:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['jxjs:jdcx:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['jxjs:jdcx:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['jxjs:jdcx:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jdcxList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="方案编号" align="center" prop="faid" />
      <el-table-column label="教师编号" align="center" prop="jsid" />
      <el-table-column label="当前状态" align="center" prop="dqzt" :formatter="dqztFormat" />
      <el-table-column label="创建人" align="center" prop="createuserid" />
      <el-table-column label="基地校审核人" align="center" prop="jdxshr" />
      <el-table-column label="基地校审核状态" align="center" prop="jdxshzt" :formatter="jdxshztFormat" />
      <el-table-column label="上报理由" align="center" prop="sbly" />
      <el-table-column label="区级审核人" align="center" prop="qjshr" />
      <el-table-column label="区级审核状态" align="center" prop="qjshzt" :formatter="qjshztFormat" />
      <el-table-column label="区级审核意见" align="center" prop="qjshyj" :formatter="qjshyjFormat" />
      <el-table-column label="基地排序" align="center" prop="jdpx" />
      <el-table-column label="案例分析得分" align="center" prop="alfxdf" />
      <el-table-column label="教案设计得分" align="center" prop="jasjdf" />
      <el-table-column label="钢笔字得分" align="center" prop="gbzdf" />
      <el-table-column label="综合得分" align="center" prop="zhdf" />
      <el-table-column label="成绩导入创建时间" align="center" prop="cjdrcreateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.cjdrcreateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="面试确认" align="center" prop="msqr" />
      <el-table-column label="面试确认时间" align="center" prop="msqrcreateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.msqrcreateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="面试结果模拟课堂教学" align="center" prop="msjgmnktjxdf" />
      <el-table-column label="演讲得分" align="center" prop="yjdf" />
      <el-table-column label="综合得分2" align="center" prop="zhdf2" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['jxjs:jdcx:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['jxjs:jdcx:remove']"
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

    <!-- 添加或修改基地区级审核对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="方案编号" prop="faid">
          <el-input v-model="form.faid" placeholder="请输入方案编号" />
        </el-form-item>
        <el-form-item label="教师编号" prop="jsid">
          <el-input v-model="form.jsid" placeholder="请输入教师编号" />
        </el-form-item>
        <el-form-item label="当前状态" prop="dqzt">
          <el-select v-model="form.dqzt" placeholder="请选择当前状态">
            <el-option
              v-for="dict in dqztOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建人" prop="createuserid">
          <el-input v-model="form.createuserid" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="基地校审核人" prop="jdxshr">
          <el-input v-model="form.jdxshr" placeholder="请输入基地校审核人" />
        </el-form-item>
        <el-form-item label="基地校审核状态" prop="jdxshzt">
          <el-select v-model="form.jdxshzt" placeholder="请选择基地校审核状态">
            <el-option
              v-for="dict in jdxshztOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上报理由" prop="sbly">
          <el-input v-model="form.sbly" placeholder="请输入上报理由" />
        </el-form-item>
        <el-form-item label="区级审核人" prop="qjshr">
          <el-input v-model="form.qjshr" placeholder="请输入区级审核人" />
        </el-form-item>
        <el-form-item label="区级审核状态" prop="qjshzt">
          <el-select v-model="form.qjshzt" placeholder="请选择区级审核状态">
            <el-option
              v-for="dict in qjshztOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="区级审核意见" prop="qjshyj">
          <el-select v-model="form.qjshyj" placeholder="请选择区级审核意见">
            <el-option
              v-for="dict in qjshyjOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="基地排序" prop="jdpx">
          <el-input v-model="form.jdpx" placeholder="请输入基地排序" />
        </el-form-item>
        <el-form-item label="案例分析得分" prop="alfxdf">
          <el-input v-model="form.alfxdf" placeholder="请输入案例分析得分" />
        </el-form-item>
        <el-form-item label="教案设计得分" prop="jasjdf">
          <el-input v-model="form.jasjdf" placeholder="请输入教案设计得分" />
        </el-form-item>
        <el-form-item label="钢笔字得分" prop="gbzdf">
          <el-input v-model="form.gbzdf" placeholder="请输入钢笔字得分" />
        </el-form-item>
        <el-form-item label="综合得分" prop="zhdf">
          <el-input v-model="form.zhdf" placeholder="请输入综合得分" />
        </el-form-item>
        <el-form-item label="成绩导入创建时间" prop="cjdrcreateTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.cjdrcreateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择成绩导入创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="面试确认" prop="msqr">
          <el-input v-model="form.msqr" placeholder="请输入面试确认" />
        </el-form-item>
        <el-form-item label="面试确认时间" prop="msqrcreateTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.msqrcreateTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择面试确认时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="面试结果模拟课堂教学" prop="msjgmnktjxdf">
          <el-input v-model="form.msjgmnktjxdf" placeholder="请输入面试结果模拟课堂教学" />
        </el-form-item>
        <el-form-item label="演讲得分" prop="yjdf">
          <el-input v-model="form.yjdf" placeholder="请输入演讲得分" />
        </el-form-item>
        <el-form-item label="综合得分2" prop="zhdf2">
          <el-input v-model="form.zhdf2" placeholder="请输入综合得分2" />
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
import { listJdcx, getJdcx, delJdcx, addJdcx, updateJdcx, exportJdcx } from "@/api/jxjs/jdcx";

export default {
  name: "Jdcx",
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
      // 基地区级审核表格数据
      jdcxList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 当前状态字典
      dqztOptions: [],
      // 基地校审核状态字典
      jdxshztOptions: [],
      // 区级审核状态字典
      qjshztOptions: [],
      // 区级审核意见字典
      qjshyjOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        faid: null,
        jsid: null,
        dqzt: null,
        createuserid: null,
        jdxshr: null,
        jdxshzt: null,
        sbly: null,
        qjshr: null,
        qjshzt: null,
        qjshyj: null,
        jdpx: null,
        alfxdf: null,
        jasjdf: null,
        gbzdf: null,
        zhdf: null,
        cjdrcreateTime: null,
        msqr: null,
        msqrcreateTime: null,
        msjgmnktjxdf: null,
        yjdf: null,
        zhdf2: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_dm_shzt").then(response => {
      this.dqztOptions = response.data;
    });
    this.getDicts("sys_dm_shzt").then(response => {
      this.jdxshztOptions = response.data;
    });
    this.getDicts("sys_dm_shzt").then(response => {
      this.qjshztOptions = response.data;
    });
    this.getDicts("sys_dm_shyj").then(response => {
      this.qjshyjOptions = response.data;
    });
  },
  methods: {
    /** 查询基地区级审核列表 */
    getList() {
      this.loading = true;
      listJdcx(this.queryParams).then(response => {
        this.jdcxList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 当前状态字典翻译
    dqztFormat(row, column) {
      return this.selectDictLabel(this.dqztOptions, row.dqzt);
    },
    // 基地校审核状态字典翻译
    jdxshztFormat(row, column) {
      return this.selectDictLabel(this.jdxshztOptions, row.jdxshzt);
    },
    // 区级审核状态字典翻译
    qjshztFormat(row, column) {
      return this.selectDictLabel(this.qjshztOptions, row.qjshzt);
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
        dqzt: null,
        createuserid: null,
        createTime: null,
        jdxshr: null,
        jdxshzt: null,
        sbly: null,
        qjshr: null,
        qjshzt: null,
        qjshyj: null,
        jdpx: null,
        alfxdf: null,
        jasjdf: null,
        gbzdf: null,
        zhdf: null,
        cjdrcreateTime: null,
        msqr: null,
        msqrcreateTime: null,
        msjgmnktjxdf: null,
        yjdf: null,
        zhdf2: null
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
      this.title = "添加基地区级审核";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getJdcx(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改基地区级审核";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateJdcx(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addJdcx(this.form).then(response => {
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
      this.$confirm('是否确认删除基地区级审核编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delJdcx(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有基地区级审核数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportJdcx(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
