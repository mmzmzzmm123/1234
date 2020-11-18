<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="选择幼儿" prop="childid">
        <el-select
          v-model="queryParams.childid"
          placeholder="请输入选择幼儿"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in childOptions"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="父亲姓名" prop="fathername">
        <el-input
          v-model="queryParams.fathername"
          placeholder="请输入父亲姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="母亲姓名" prop="mothername">
        <el-input
          v-model="queryParams.mothername"
          placeholder="请输入母亲姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          :disabled="true"
          v-hasPermi="['benyi:contactpeople:add']"
        >新增</el-button>
      </el-col> -->
      <!-- <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:contactpeople:edit']"
        >修改</el-button
      > -->
      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:contactpeople:remove']"
        >删除</el-button
      >
    </div>

    <el-table
    border
      v-loading="loading"
      :data="contactpeopleList"
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
      <el-table-column label="父亲姓名" align="center" prop="fathername" />
      <el-table-column label="父亲联系电话" width="120" align="center" prop="fphone" />
      <el-table-column label="父亲办公电话" width="120" align="center" prop="foffphone" />
      <el-table-column label="母亲姓名" align="center" prop="mothername" />
      <el-table-column label="母亲联系电话" width="120" align="center" prop="mphone" />
      <el-table-column label="母亲办公电话" width="120" align="center" prop="moffphone" />
      <el-table-column
        label="操作"
        align="center"
        width="120"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:contactpeople:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:contactpeople:remove']"
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

    <!-- 添加或修改幼儿紧急情况联系人对话框 -->
    <el-dialog :title="title" :visible.sync="open" class="big-dialog" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="选择幼儿" prop="childid">
              <el-select
                v-model="form.childid"
                placeholder="请输入选择幼儿"
                :disabled="true"
              >
                <el-option
                  v-for="dict in childOptions"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="父亲姓名" prop="fathername">
              <el-input
                v-model="form.fathername"
                placeholder="请输入父亲姓名"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="fphone">
              <el-input
                v-model="form.fphone"
                placeholder="请输入父亲联系电话"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="办公电话" prop="foffphone">
              <el-input
                v-model="form.foffphone"
                placeholder="请输入父亲办公电话"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="母亲姓名" prop="mothername">
              <el-input
                v-model="form.mothername"
                placeholder="请输入母亲姓名"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="mphone">
              <el-input
                v-model="form.mphone"
                placeholder="请输入母亲联系电话"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="办公电话" prop="moffphone">
              <el-input
                v-model="form.moffphone"
                placeholder="请输入母亲办公电话"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="其他联系人姓名" prop="grandfathername">
              <el-input
                v-model="form.grandfathername"
                placeholder="请输入其他联系人姓名"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="与幼儿关系" prop="gfgx">
              <el-select v-model="form.gfgx" placeholder="请选择与幼儿关系">
                <el-option
                  v-for="dict in jtgxOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="gfphone">
              <el-input v-model="form.gfphone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="办公电话" prop="gfoffphone">
              <el-input
                v-model="form.gfoffphone"
                placeholder="请输入办公电话"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="住址" prop="gfaddress">
              <el-input v-model="form.gfaddress" placeholder="请输入住址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="12">
            <el-form-item label="其他联系人姓名" prop="grandmothername">
              <el-input
                v-model="form.grandmothername"
                placeholder="请输入其他联系人姓名"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="与幼儿关系" prop="gmgx">
              <el-select v-model="form.gmgx" placeholder="请选择与幼儿关系">
                <el-option
                  v-for="dict in jtgxOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="gmphone">
              <el-input v-model="form.gmphone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="办公电话" prop="gmoffphone">
              <el-input
                v-model="form.gmoffphone"
                placeholder="请输入办公电话"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="住址" prop="gmaddress">
              <el-input v-model="form.gmaddress" placeholder="请输入住址" />
            </el-form-item>
          </el-col>
        </el-row>
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
  listContactpeople,
  getContactpeople,
  delContactpeople,
  addContactpeople,
  updateContactpeople
} from "@/api/benyi/contactpeople";

import { listChild } from "@/api/benyi/child";

export default {
  name: "Contactpeople",
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
      // 幼儿紧急情况联系人表格数据
      contactpeopleList: [],
      //幼儿列表
      childOptions: [],
      //家庭关系
      jtgxOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        childid: undefined,
        fathername: undefined,
        fphone: undefined,
        foffphone: undefined,
        mothername: undefined,
        mphone: undefined,
        moffphone: undefined,
        grandfathername: undefined,
        gfgx: undefined,
        gfphone: undefined,
        gfoffphone: undefined,
        gfaddress: undefined,
        grandmothername: undefined,
        gmgx: undefined,
        gmphone: undefined,
        gmaddress: undefined,
        gmoffphone: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        childid: [{ required: true, message: "请选择幼儿", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
    this.getChildList();
    this.getDicts("sys_dm_jtgx").then(response => {
      this.jtgxOptions = response.data;
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
    /** 查询幼儿信息列表 */
    getChildList() {
      listChild(null).then(response => {
        this.childOptions = response.rows;
      });
    },
    /** 查询幼儿紧急情况联系人列表 */
    getList() {
      this.loading = true;
      listContactpeople(this.queryParams).then(response => {
        this.contactpeopleList = response.rows;
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
        fathername: undefined,
        fphone: undefined,
        foffphone: undefined,
        mothername: undefined,
        mphone: undefined,
        moffphone: undefined,
        grandfathername: undefined,
        gfgx: undefined,
        gfphone: undefined,
        gfoffphone: undefined,
        gfaddress: undefined,
        grandmothername: undefined,
        gmgx: undefined,
        gmphone: undefined,
        gmaddress: undefined,
        gmoffphone: undefined,
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
    // handleAdd() {
    //   this.reset();
    //   this.open = true;
    //   this.title = "添加幼儿紧急情况联系人";
    // },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getContactpeople(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改幼儿紧急情况联系人";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateContactpeople(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addContactpeople(this.form).then(response => {
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
      this.$confirm("是否确认删除幼儿紧急情况联系人数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delContactpeople(ids);
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
