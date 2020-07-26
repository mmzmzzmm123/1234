<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="家长姓名" prop="jzxm">
        <el-input
          v-model="queryParams.jzxm"
          placeholder="请输入家长姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="幼儿姓名" prop="yexm">
        <el-input
          v-model="queryParams.yexm"
          placeholder="请输入幼儿姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系方式" prop="lxfs">
        <el-input
          v-model="queryParams.lxfs"
          placeholder="请输入联系方式"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
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
          v-hasPermi="['benyi:experience:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:experience:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:experience:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="experienceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="家长姓名" align="center" prop="jzxm" />
      <el-table-column label="幼儿姓名" align="center" prop="yexm" />
      <el-table-column label="幼儿出生日期" align="center" prop="csrq" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.csrq, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="联系方式" align="center" prop="lxfs" />
      <el-table-column label="拟入园时间" align="center" prop="nrysj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.nrysj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请体验时间" align="center" prop="sqtysj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.sqtysj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上午或下午" align="center" prop="swxw" />
      <el-table-column label="体验学校" align="center" prop="schoolid" />
      <el-table-column label="是否回复" align="center" prop="sfhf" />
      <el-table-column label="回复内容" align="center" prop="hfrn" />
      <el-table-column label="回复人" align="center" prop="hfuserid" />
      <el-table-column label="回复时间" align="center" prop="fhsj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.fhsj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="园长指示" align="center" prop="yzzs" />
      <el-table-column label="体验结果
            0不入园
            1入园" align="center" prop="tyjg" />
      <el-table-column label="入园时间" align="center" prop="rysj" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.rysj, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:experience:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:experience:remove']"
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

    <!-- 添加或修改入班体验申请对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="家长姓名" prop="jzxm">
          <el-input v-model="form.jzxm" placeholder="请输入家长姓名" />
        </el-form-item>
        <el-form-item label="幼儿姓名" prop="yexm">
          <el-input v-model="form.yexm" placeholder="请输入幼儿姓名" />
        </el-form-item>
        <el-form-item label="幼儿出生日期" prop="csrq">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.csrq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择幼儿出生日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="联系方式" prop="lxfs">
          <el-input v-model="form.lxfs" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="拟入园时间" prop="nrysj">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.nrysj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择拟入园时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="申请体验时间" prop="sqtysj">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.sqtysj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择申请体验时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="上午或下午" prop="swxw">
          <el-input v-model="form.swxw" placeholder="请输入上午或下午" />
        </el-form-item>
        <el-form-item label="体验学校" prop="schoolid">
          <el-input v-model="form.schoolid" placeholder="请输入体验学校" />
        </el-form-item>
        <el-form-item label="是否回复" prop="sfhf">
          <el-input v-model="form.sfhf" placeholder="请输入是否回复" />
        </el-form-item>
        <el-form-item label="回复内容" prop="hfrn">
          <el-input v-model="form.hfrn" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="回复人" prop="hfuserid">
          <el-input v-model="form.hfuserid" placeholder="请输入回复人" />
        </el-form-item>
        <el-form-item label="回复时间" prop="fhsj">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.fhsj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择回复时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="园长指示" prop="yzzs">
          <el-input v-model="form.yzzs" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="体验结果
            0不入园
            1入园" prop="tyjg">
          <el-input v-model="form.tyjg" placeholder="请输入体验结果
            0不入园
            1入园" />
        </el-form-item>
        <el-form-item label="入园时间" prop="rysj">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.rysj"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择入园时间"
          ></el-date-picker>
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
  listExperience,
  getExperience,
  delExperience,
  addExperience,
  updateExperience,
  exportExperience,
} from "@/api/benyi/experience";

export default {
  name: "Experience",
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
      // 总条数
      total: 0,
      // 入班体验申请表格数据
      experienceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jzxm: undefined,
        yexm: undefined,
        csrq: undefined,
        lxfs: undefined,
        nrysj: undefined,
        sqtysj: undefined,
        swxw: undefined,
        schoolid: undefined,
        sfhf: undefined,
        hfrn: undefined,
        hfuserid: undefined,
        fhsj: undefined,
        yzzs: undefined,
        tyjg: undefined,
        rysj: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询入班体验申请列表 */
    getList() {
      this.loading = true;
      listExperience(this.queryParams).then((response) => {
        this.experienceList = response.rows;
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
        id: undefined,
        jzxm: undefined,
        yexm: undefined,
        csrq: undefined,
        lxfs: undefined,
        nrysj: undefined,
        sqtysj: undefined,
        swxw: undefined,
        schoolid: undefined,
        sfhf: undefined,
        hfrn: undefined,
        hfuserid: undefined,
        fhsj: undefined,
        yzzs: undefined,
        tyjg: undefined,
        rysj: undefined,
        createTime: undefined,
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
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加入班体验申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getExperience(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改入班体验申请";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateExperience(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addExperience(this.form).then((response) => {
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
        '是否确认删除入班体验申请编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delExperience(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    }
  },
};
</script>