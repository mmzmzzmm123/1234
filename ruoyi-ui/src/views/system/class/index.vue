<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="班级类型" prop="bjtype">
        <el-select v-model="queryParams.bjtype" placeholder="请选择班级类型" clearable size="small">
          <el-option
            v-for="dict in bjtypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="班级名称" prop="bjmc">
        <el-input
          v-model="queryParams.bjmc"
          placeholder="请输入班级名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主班教师" prop="zbjs">
        <el-select v-model="queryParams.zbjs" filterable placeholder="请选择主班教师">
          <el-option
            v-for="item in zbjsOptions"
            :key="item.userId"
            :label="item.nickName"
            :value="item.userId"
            :disabled="item.status == 1"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="配班教师" prop="pbjs">
         <el-select v-model="queryParams.pbjs" filterable placeholder="请选择配班教师">
          <el-option
            v-for="item in pbjsOptions"
            :key="item.userId"
            :label="item.nickName"
            :value="item.userId"
            :disabled="item.status == 1"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="助理教师" prop="zljs">
         <el-select v-model="queryParams.zljs" filterable placeholder="请选择助理教师">
          <el-option
            v-for="item in zljsOptions"
            :key="item.userId"
            :label="item.nickName"
            :value="item.userId"
            :disabled="item.status == 1"
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
          v-hasPermi="['system:class:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:class:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:class:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:class:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="classList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="班级编号" align="center" prop="bjbh" v-if="false" />>
      <el-table-column label="班级类型" align="center" :formatter="bjtypeFormat" prop="bjtype" />
      <el-table-column label="班级名称" align="center" prop="bjmc" />
      <el-table-column label="主班教师" align="center" prop="zbjsxm" />
      <el-table-column label="配班教师" align="center" prop="pbjsxm" />
      <el-table-column label="助理教师" align="center" prop="zljsxm" />
      <el-table-column label="创建时间" align="center" prop="createtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createtime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:class:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:class:remove']"
          >删除班级</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleEditJs(scope.row)"
            v-hasPermi="['system:class:edit']"
          >清空教师</el-button>
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

    <!-- 添加或修改班级信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="班级类型" prop="bjtype">
          <el-select v-model="form.bjtype" placeholder="请选择班级类型">
            <el-option
              v-for="dict in bjtypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="班级名称" prop="bjmc">
          <el-input v-model="form.bjmc" placeholder="请输入班级名称" />
        </el-form-item>
        <el-form-item label="主班教师" prop="zbjs">
          <el-select v-model="form.zbjs" placeholder="请选择主班教师">
            <el-option
              v-for="item in zbjsOptions"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
              :disabled="item.status == 1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="配班教师" prop="pbjs">
          <el-select v-model="form.pbjs" placeholder="请选择配班教师">
            <el-option
              v-for="item in pbjsOptions"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
              :disabled="item.status == 1"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="助理教师" prop="zljs">
          <el-select v-model="form.zljs" placeholder="请选择助理教师">
            <el-option
              v-for="item in zljsOptions"
              :key="item.userId"
              :label="item.nickName"
              :value="item.userId"
              :disabled="item.status == 1"
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
  listClass,
  getClass,
  delClass,
  addClass,
  updateClass,
  exportClass,
  delJsClass
} from "@/api/system/class";
import { getUsersByRoleId } from "@/api/system/user";

export default {
  name: "Class",
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
      // 班级信息表格数据
      classList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //班级类型 字典
      bjtypeOptions: [],
      //主班教师角色用户
      zbjsOptions: [],
      //配班教师角色用户
      pbjsOptions: [],
      //助理教师角色用户
      zljsOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        bjtype: undefined,
        bjmc: undefined,
        zbjs: undefined,
        pbjs: undefined,
        zljs: undefined,
        zbjsxm: undefined,
        pbjsxm: undefined,
        zljsxm: undefined,
        createtime: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        bjtype: [
          { required: true, message: "班级类型不能为空", trigger: "blur" }
        ],
        bjmc: [{ required: true, message: "班级名称不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_yebjlx").then(response => {
      this.bjtypeOptions = response.data;
    });
    //获取主班教师角色用户列表
    getUsersByRoleId().then(response => {
      this.zbjsOptions = response.zbjs;
      this.pbjsOptions = response.pbjs;
      this.zljsOptions = response.zljs;
    });
  },
  methods: {
    /** 查询班级信息列表 */
    getList() {
      this.loading = true;
      listClass(this.queryParams).then(response => {
        this.classList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 班级类型--字典状态字典翻译
    bjtypeFormat(row, column) {
      return this.selectDictLabel(this.bjtypeOptions, row.bjtype);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        bjbh: undefined,
        deptId: undefined,
        bjtype: undefined,
        bjmc: undefined,
        zbjs: undefined,
        pbjs: undefined,
        zljs: undefined,
        createtime: undefined
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
      this.ids = selection.map(item => item.bjbh);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加班级信息";
      //获取主班教师角色用户列表
      getUsersByRoleId().then(response => {
        this.zbjsOptions = response.zbjs;
        this.pbjsOptions = response.pbjs;
        this.zljsOptions = response.zljs;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const bjbh = row.bjbh || this.ids;
      getClass(bjbh).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改班级信息";

        //获取主班教师角色用户列表
        getUsersByRoleId().then(response => {
          this.zbjsOptions = response.zbjs;
          this.pbjsOptions = response.pbjs;
          this.zljsOptions = response.zljs;
        });
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.bjbh != undefined) {
            updateClass(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addClass(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const bjbhs = row.bjbh || this.ids;
      this.$confirm("是否确认删除选中的班级信息?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delClass(bjbhs);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function() {});
    },
    handleEditJs(row) {
      const bjbhs = row.bjbh || this.ids;
      this.$confirm("是否确认清空选中的班级教师信息?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return delJsClass(bjbhs);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("清空成功");
        })
        .catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有班级信息数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportClass(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    }
  }
};
</script>