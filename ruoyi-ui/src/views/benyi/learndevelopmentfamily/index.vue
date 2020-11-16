<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="68px"
    >
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
        <el-button
          type="primary"
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

    <div class="mb8 btn-list">
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['benyi:learndevelopmentfamily:add']"
        >新增</el-button
      >
      <!-- <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:learndevelopmentfamily:edit']"
        >修改</el-button
      > -->
      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:learndevelopmentfamily:remove']"
        >删除</el-button
      >
    </div>

    <el-table
      v-loading="loading"
      :data="familyList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <el-table-column
        label="幼儿"
        align="center"
        prop="childid"
        :formatter="childFormat"
      />
      <el-table-column
        label="学年学期"
        align="center"
        prop="xnxq"
        :formatter="xnxqFormat"
      />
      <!-- <el-table-column label="作品照片" align="center" prop="zpimgs" />
      <el-table-column label="作品照片备注" align="center" prop="zpimgremarks" />
      <el-table-column label="生活照片" align="center" prop="shimgs" />
      <el-table-column label="生活照片备注" align="center" prop="shimgsremarks" />
      <el-table-column label="有趣事件" align="center" prop="yqsj" />
      <el-table-column label="有趣事件备注" align="center" prop="yqsjremarks" />
      <el-table-column label="教师评语" align="center" prop="jspy" />
      <el-table-column label="教师评语备注" align="center" prop="jspyremarks" />
      <el-table-column label="创建人" align="center" prop="createuserid" />-->
      <el-table-column
        label="操作"
        align="center"
        width="100"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:learndevelopmentfamily:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:learndevelopmentfamily:remove']"
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

    <!-- 添加或修改儿童学习与发展档案（家长）对话框 -->
    <el-dialog :title="title" :visible.sync="open" class="big-dialog" append-to-body>
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-col :span="12">
            <el-form-item label="幼儿id" prop="childid">
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
            <el-form-item label="作品照片" prop="zpimgs">
              <Editor v-model="form.zpimgs" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="zpimgremarks">
              <el-input
                v-model="form.zpimgremarks"
                type="textarea"
                placeholder="请输入作品照片备注"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="生活照片" prop="shimgs">
              <Editor v-model="form.shimgs" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="shimgsremarks">
              <el-input
                v-model="form.shimgsremarks"
                type="textarea"
                placeholder="请输入生活照片备注"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="有趣事件" prop="yqsj">
              <Editor v-model="form.yqsj" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="yqsjremarks">
              <el-input
                v-model="form.yqsjremarks"
                type="textarea"
                placeholder="请输入有趣事件备注"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="教师评语" prop="jspy">
              <Editor v-model="form.jspy" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="jspyremarks">
              <el-input
                v-model="form.jspyremarks"
                type="textarea"
                placeholder="请输入教师评语备注"
              />
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
  listFamily,
  getFamily,
  delFamily,
  addFamily,
  updateFamily
} from "@/api/benyi/learndevelopmentfamily";

import { listChild } from "@/api/benyi/child";

import Editor from "@/components/Editor";

export default {
  name: "Family",
  components: {
    Editor
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
      // 儿童学习与发展档案（家长）表格数据
      familyList: [],
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
        zpimgs: undefined,
        zpimgremarks: undefined,
        shimgs: undefined,
        shimgsremarks: undefined,
        yqsj: undefined,
        yqsjremarks: undefined,
        jspy: undefined,
        jspyremarks: undefined,
        createuserid: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        childid: [{ required: true, message: "幼儿不能为空", trigger: "blur" }],
        xnxq: [{ required: true, message: "学年学期不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
    this.getChildList();
    this.getDicts("sys_xnxq").then(response => {
      this.xnxqOptions = response.data;
    });
  },
  methods: {
    // 字典翻译
    childFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.childOptions;
      Object.keys(datas).map(key => {
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
      listChild(null).then(response => {
        this.childOptions = response.rows;
      });
    },
    /** 查询儿童学习与发展档案（家长）列表 */
    getList() {
      this.loading = true;
      listFamily(this.queryParams).then(response => {
        this.familyList = response.rows;
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
        zpimgs: undefined,
        zpimgremarks: undefined,
        shimgs: undefined,
        shimgsremarks: undefined,
        yqsj: undefined,
        yqsjremarks: undefined,
        jspy: undefined,
        jspyremarks: undefined,
        createuserid: undefined,
        createTime: undefined
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加儿童学习与发展档案（家长）";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getFamily(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改儿童学习与发展档案（家长）";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateFamily(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addFamily(this.form).then(response => {
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
        '是否确认删除儿童学习与发展档案（家长）编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delFamily(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function() {});
    }
  }
};
</script>
