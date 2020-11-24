<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" label-width="70px">
      <el-row :gutter="10">
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="班级名称" prop="classid">
            <el-select v-model="queryParams.classid" placeholder="请选择班级">
              <el-option
                v-for="dict in classOptions"
                :key="dict.bjbh"
                :label="dict.bjmc"
                :value="dict.bjbh"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="幼儿姓名" prop="childname">
            <el-input
              v-model="queryParams.childname"
              placeholder="请输入幼儿姓名"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="出勤类型" prop="type">
            <el-select
              v-model="queryParams.type"
              placeholder="请选择出勤类型"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in checkinOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="5">
          <el-form-item label="考勤时间" prop="createTime">
            <el-date-picker
              class="my-date-picker"
              clearable
              size="small"
              v-model="queryParams.createTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择考勤时间"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :ms="12" :md="4">
          <el-form-item class="no-margin">
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
        </el-col>
      </el-row>
    </el-form>

    <div class="mb8 btn-list">
      <el-button
        type="primary"
        icon="el-icon-plus"
        size="mini"
        @click="handleAdd"
        v-hasPermi="['benyi:checkindetail:add']"
        >新增</el-button
      >
      <el-button
        type="success"
        icon="el-icon-edit"
        size="mini"
        :disabled="single"
        @click="handleUpdate"
        v-hasPermi="['benyi:checkindetail:edit']"
        >修改</el-button
      >
      <el-button
        type="danger"
        icon="el-icon-delete"
        size="mini"
        :disabled="multiple"
        @click="handleDelete"
        v-hasPermi="['benyi:checkindetail:remove']"
        >删除</el-button
      >
      <el-button
        type="warning"
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['benyi:checkindetail:export']"
        >导出</el-button
      >
    </div>

    <el-table
      border
      v-loading="loading"
      :data="detailList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="55"
        align="center"
        :selectable="checkSelectable"
      />
      <!-- <el-table-column label="编号" align="center" prop="id" /> -->
      <!-- <el-table-column label="学校编码" align="center" prop="schoolid" /> -->
      <!-- <el-table-column label="幼儿编码" align="center" prop="childid" /> -->
      <el-table-column fixed label="幼儿姓名" align="center" prop="childname" />
      <el-table-column
        label="班级名称"
        align="center"
        prop="classid"
        :formatter="classFormat"
      />
      <el-table-column
        label="出勤类型"
        align="center"
        prop="type"
        :formatter="typeFormat"
      />
      <el-table-column
        label="考勤时间"
        align="center"
        prop="createTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="创建人" align="center" prop="createuserid" /> -->
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        width="60"
        class-name="small-padding fixed-width edit-btns"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['benyi:checkindetail:edit']"
            :disabled="!checkSelectable(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['benyi:checkindetail:remove']"
            :disabled="!checkSelectable(scope.row)"
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

    <!-- 添加或修改幼儿考勤对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      class="v-dialog"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="选择幼儿" prop="childname">
          <el-checkbox
            :indeterminate="isIndeterminate"
            v-model="checkAll"
            @change="handleCheckAllChange"
            :disabled="isable"
            >全选</el-checkbox
          >
          <div style="margin: 15px 0"></div>
          <el-checkbox-group
            v-model="checkedChilds"
            @change="handlecheckedChildsChange"
          >
            <el-checkbox
              v-for="child in childs"
              :label="child.id"
              :key="child.id"
              :disabled="isable"
              >{{ child.name }}</el-checkbox
            >
          </el-checkbox-group>
          <el-input v-model="form.childname" v-if="false" />
        </el-form-item>
        <el-form-item label="出勤类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择出勤类型">
            <el-option
              v-for="dict in checkinOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
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
  listDetail,
  getDetail,
  delDetail,
  addDetail,
  updateDetail,
  exportDetail,
} from "@/api/benyi/checkindetail";

import { listByCheck, listChild } from "@/api/benyi/child";

import { listClass } from "@/api/system/class";

export default {
  name: "Detail",
  data() {
    return {
      isable: false,
      checkAll: false,
      checkedChilds: [],
      childs: [],
      isIndeterminate: false,
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
      // 幼儿考勤表格数据
      detailList: [],
      //字典
      checkinOptions: [],
      //班级
      classOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        schoolid: undefined,
        classid: undefined,
        childid: undefined,
        childname: undefined,
        type: undefined,
        createuserid: undefined,
        createTime: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        childname: [
          {
            required: true,
            message: "请至少选择一个幼儿",
            trigger: "blur",
          },
        ],
        type: [
          { required: true, message: "出勤类型不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getClassList();
    this.getDicts("sys_dm_cqzt").then((response) => {
      this.checkinOptions = response.data;
    });
  },
  methods: {
    //控制按钮可用
    checkSelectable(row) {
      var date = new Date();
      //console.log(date.toLocaleDateString());
      return this.CompareDate(row.createTime, date.toLocaleDateString());
    },
    //比较日期大小
    CompareDate(d1, d2) {
      return new Date(d1.replace(/-/g, "/")) > new Date(d2.replace(/-/g, "/"));
    },
    //班级列表
    getClassList() {
      listClass(null).then((response) => {
        this.classOptions = response.rows;
      });
    },
    // 字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.checkinOptions, row.type);
    },
    // 字典翻译
    classFormat(row, column) {
      // return this.selectDictLabel(this.classOptions, row.classid);
      var actions = [];
      var datas = this.classOptions;
      Object.keys(datas).map((key) => {
        if (datas[key].bjbh == "" + row.classid) {
          actions.push(datas[key].bjmc);
          return false;
        }
      });
      return actions.join("");
    },
    /** 查询幼儿考勤列表 */
    getList() {
      this.loading = true;
      listDetail(this.queryParams).then((response) => {
        this.detailList = response.rows;
        // console.log(response.rows);
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
        schoolid: undefined,
        classid: undefined,
        childid: undefined,
        childname: undefined,
        type: undefined,
        createuserid: undefined,
        createTime: undefined,
      };
      this.resetForm("form");

      this.checkedChilds = [];
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
      this.isable = false;
      this.open = true;
      this.title = "幼儿考勤";
      listByCheck(null).then((response) => {
        this.childs = response.rows;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.isable = true;
      const id = row.id || this.ids;
      getDetail(id).then((response) => {
        this.form = response.data;
        listChild(null).then((response) => {
          this.childs = response.rows;
        });
        this.checkedChilds.push(response.data.childid);
        this.open = true;
        this.title = "修改幼儿考勤";
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.childname == "") {
            this.msgError("请至少选择一个幼儿");
            return;
          }
          if (valid) {
            if (this.form.id != undefined) {
              updateDetail(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("修改考勤成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              //提交
              addDetail(this.form).then((response) => {
                if (response.code === 200) {
                  this.msgSuccess("添加考勤成功");
                  this.open = false;
                  this.getList();
                }
              });
            }
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除幼儿考勤的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return delDetail(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function () {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有幼儿考勤数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return exportDetail(queryParams);
        })
        .then((response) => {
          this.download(response.msg);
        })
        .catch(function () {});
    },
    handleCheckAllChange(val) {
      // this.checkedChilds = val ? this.childs : [];
      // this.isIndeterminate = false;
      this.checkedChilds = [];
      this.childs.forEach((item) => {
        //当全选被选中的时候，循环遍历源数据，把数据的每一项加入到默认选中的数组去
        this.checkedChilds.push(item.id);
      });
      this.checkedChilds = val ? this.checkedChilds : []; //三元表达式，如果val的值为true，那么就把当前默认选中的值赋值给自身，这样页面页面上所有的元素就都选中了。如果为false，就是取消全选
      this.isIndeterminate = false; //官网说这是个样式控制，是来控制，什么时候半选的，要不要都无所谓，看你需求

      //console.log(this.checkedChilds);
      var cids = "";
      this.checkedChilds.forEach((item) => {
        //当全选被选中的时候，循环遍历源数据，把数据的每一项加入到默认选中的数组去
        cids = cids + item + ",";
      });
      // console.log(cids);
      this.form.childname = cids;
    },
    handlecheckedChildsChange(value) {
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.childs.length;
      this.isIndeterminate =
        checkedCount > 0 && checkedCount < this.childs.length;

      //console.log(this.checkedChilds);
      var cids = "";
      this.checkedChilds.forEach((item) => {
        //当全选被选中的时候，循环遍历源数据，把数据的每一项加入到默认选中的数组去
        cids = cids + item + ",";
      });
      // console.log(cids);
      this.form.childname = cids;
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