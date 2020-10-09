<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="幼儿" prop="childid">
        <el-select v-model="queryParams.childid" placeholder="请选择">
          <el-option
            v-for="dict in childOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学年学期" prop="xnxq">
        <el-select v-model="queryParams.xnxq" placeholder="请选择">
          <el-option
            v-for="dict in xnxqOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
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
          v-hasPermi="['benyi:learndevelopmentteacher:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:learndevelopmentteacher:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:learndevelopmentteacher:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="teacherList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column label="幼儿" align="center" prop="childid" :formatter="childFormat" />
      <el-table-column label="学年学期" align="center" prop="xnxq" :formatter="xnxqFormat" />
      <!-- <el-table-column label="观察记录" align="center" prop="gcjl" />
      <el-table-column label="观察记录备注" align="center" prop="gcjlremarks" />
      <el-table-column label="问题与方案" align="center" prop="wtyfa" />
      <el-table-column label="问题与方案备注" align="center" prop="wtyfaremarks" />
      <el-table-column label="评估结果" align="center" prop="pgjg" />
      <el-table-column label="评估结果备注" align="center" prop="pgjgremarks" />
      <el-table-column label="教育计划" align="center" prop="jyjh" />
      <el-table-column label="教育计划备注" align="center" prop="jyjhremarks" />
      <el-table-column label="创建人" align="center" prop="createuserid" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:learndevelopmentteacher:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:learndevelopmentteacher:remove']"
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

    <!-- 添加或修改儿童学习与发展档案（教师）对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="90px">
          <el-col :span="12">
            <el-form-item label="幼儿" prop="childid">
              <el-select v-model="form.childid" placeholder="请选择">
                <el-option
                  v-for="dict in childOptions"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学年学期" prop="xnxq">
              <el-select v-model="form.xnxq" placeholder="请选择">
                <el-option
                  v-for="dict in xnxqOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="观察记录" prop="gcjl">
              <Editor v-model="form.gcjl" placeholder="请输入观察记录" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="gcjlremarks">
              <el-input v-model="form.gcjlremarks" type="textarea" placeholder="请输入观察记录备注" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="问题与方案" prop="wtyfa">
              <Editor v-model="form.wtyfa" placeholder="请输入问题与方案" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="wtyfaremarks">
              <el-input v-model="form.wtyfaremarks" type="textarea" placeholder="请输入问题与方案备注" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="评估结果" prop="pgjg">
              <Editor v-model="form.pgjg" placeholder="请输入评估结果" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="pgjgremarks">
              <el-input v-model="form.pgjgremarks" type="textarea" placeholder="请输入评估结果备注" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="教育计划" prop="jyjh">
              <Editor v-model="form.jyjh" placeholder="请输入教育计划" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="jyjhremarks">
              <el-input v-model="form.jyjhremarks" type="textarea" placeholder="请输入教育计划备注" />
            </el-form-item>
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listTeacher,
  getTeacher,
  delTeacher,
  addTeacher,
  updateTeacher,
} from "@/api/benyi/learndevelopmentteacher";

import { listChild } from "@/api/benyi/child";

import Editor from "@/components/Editor";

export default {
  name: "Teacher",
  components: {
    Editor,
  },
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
      // 儿童学习与发展档案（教师）表格数据
      teacherList: [],
      //幼儿
      childOptions: [],
      //学年学期
      xnxqOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        childid: undefined,
        xnxq: undefined,
        gcjl: undefined,
        gcjlremarks: undefined,
        wtyfa: undefined,
        wtyfaremarks: undefined,
        pgjg: undefined,
        pgjgremarks: undefined,
        jyjh: undefined,
        jyjhremarks: undefined,
        createuserid: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        childid: [{ required: true, message: "幼儿不能为空", trigger: "blur" }],
        xnxq: [
          { required: true, message: "学年学期不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getChildList();
    this.getDicts("sys_xnxq").then((response) => {
      this.xnxqOptions = response.data;
    });
  },
  methods: {
    // 字典翻译
    childFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.childOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].id == "" + row.childid) {
          actions.push(datas[key].name);
          return false;
        }
      });
      return actions.join("");
    },
    // 字典翻译
    xnxqFormat(row, column) {
      return this.selectDictLabel(this.xnxqOptions, row.xnxq);
    },
    //获取幼儿列表
    getChildList() {
      listChild(null).then((response) => {
        this.childOptions = response.rows;
      });
    },
    /** 查询儿童学习与发展档案（教师）列表 */
    getList() {
      this.loading = true;
      listTeacher(this.queryParams).then((response) => {
        this.teacherList = response.rows;
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
        childid: undefined,
        xnxq: undefined,
        gcjl: undefined,
        gcjlremarks: undefined,
        wtyfa: undefined,
        wtyfaremarks: undefined,
        pgjg: undefined,
        pgjgremarks: undefined,
        jyjh: undefined,
        jyjhremarks: undefined,
        createuserid: undefined,
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
      this.title = "添加儿童学习与发展档案（教师）";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getTeacher(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改儿童学习与发展档案（教师）";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            updateTeacher(this.form).then((response) => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addTeacher(this.form).then((response) => {
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
        '是否确认删除儿童学习与发展档案（教师）编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delTeacher(ids);
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