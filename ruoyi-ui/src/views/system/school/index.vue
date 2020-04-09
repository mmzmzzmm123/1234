<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="88px">
      <el-form-item label="幼儿园名称" prop="schoolName">
        <el-input
          v-model="queryParams.schoolName"
          placeholder="请输入幼儿园名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="幼儿园类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择幼儿园类型" clearable size="small">
          <el-option label="请选择字典生成" value />
        </el-select>
      </el-form-item>
      <el-form-item label="所在省" prop="provincename">
        <el-input
          v-model="queryParams.provincename"
          placeholder="请输入所在省"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系人" prop="mastername">
        <el-input
          v-model="queryParams.mastername"
          placeholder="请输入联系人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电话" prop="tel">
        <el-input
          v-model="queryParams.tel"
          placeholder="请输入电话"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option label="请选择字典生成" value />
        </el-select>
      </el-form-item>
      <!--
      <el-form-item label="幼儿园规模" prop="scale">
        <el-input
          v-model="queryParams.scale"
          placeholder="请输入幼儿园规模"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      -->
      <el-form-item label="创建时间" prop="approvalTime">
        <el-date-picker
          clearable
          size="small"
          style="width: 200px"
          v-model="queryParams.approvalTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择创建时间"
        ></el-date-picker>
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
          v-hasPermi="['system:school:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:school:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:school:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:school:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="schoolList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="幼儿园ID" align="center" prop="id" />
      <el-table-column label="幼儿园名称" align="center" prop="schoolName" />
      <el-table-column label="幼儿园类型" align="center" prop="type" />
      <el-table-column label="所在省" align="center" prop="provincename" />
      <el-table-column label="所在城市" align="center" prop="regionname" />
      <el-table-column label="详细地址" align="center" prop="address" />
      <el-table-column label="联系人" align="center" prop="mastername" />
      <el-table-column label="电话" align="center" prop="tel" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="创建人" align="center" prop="createUser" />
      <!--<el-table-column label="幼儿园规模" align="center" prop="scale" />-->
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <!--
      <el-table-column label="最后审核人ID" align="center" prop="approvalUser" />
      <el-table-column label="审核时间" align="center" prop="approvalTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.approvalTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核备注" align="center" prop="remark" />
      <el-table-column label="幼儿园位置" align="center" prop="location" />
      <el-table-column label="园所会员性质" align="center" prop="isDemonstr" />
      <el-table-column label="营业执照图片" align="center" prop="businesslicenseimg" />
      <el-table-column label="家长手册发布状态" align="center" prop="openBook" />
      <el-table-column label="幼儿园缴费状态" align="center" prop="feeStatus" />
      <el-table-column label="开通截至日期" align="center" prop="openDeadline" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.openDeadline) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="当前学年" align="center" prop="dqxn" />
      <el-table-column label="当前学期" align="center" prop="dqxq" />
      -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:school:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:school:remove']"
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

    <!-- 添加或修改幼儿园机构对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="幼儿园名称" prop="schoolName">
          <el-input v-model="form.schoolName" placeholder="请输入幼儿园名称" />
        </el-form-item>
        <el-form-item label="幼儿园简称" prop="nameShort">
          <el-input v-model="form.nameShort" placeholder="请输入幼儿园简称" />
        </el-form-item>
        <el-form-item label="幼儿园类型">
          <el-radio-group v-model="form.type" placeholder="请选择类型" @change="changeHandle">
            <el-radio
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="集团下属ID" prop="parentId" v-show="flag1">
          <el-input v-model="form.parentId" placeholder="请输入集团下属id" />
        </el-form-item>
        <el-form-item label="所在省" prop="province">
          <el-input v-model="form.province" placeholder="请输入所在省" />
        </el-form-item>
        <el-form-item label="所在省" prop="provincename">
          <el-input v-model="form.provincename" placeholder="请输入所在省" />
        </el-form-item>
        <el-form-item label="所在城市" prop="regionid">
          <el-input v-model="form.regionid" placeholder="请输入所在城市" />
        </el-form-item>
        <el-form-item label="所在城市" prop="regionname">
          <el-input v-model="form.regionname" placeholder="请输入所在城市" />
        </el-form-item>
        <el-form-item label="所在区" prop="area">
          <el-input v-model="form.area" placeholder="请输入所在区" />
        </el-form-item>
        <el-form-item label="所在区" prop="areaname">
          <el-input v-model="form.areaname" placeholder="请输入所在区" />
        </el-form-item>
        <el-form-item label="详细地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="联系人" prop="mastername">
          <el-input v-model="form.mastername" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入电话" />
        </el-form-item>
        <el-form-item label="紧急联系人" prop="emMan">
          <el-input v-model="form.emMan" placeholder="请输入紧急联系人" />
        </el-form-item>
        <el-form-item label="紧急联系电话" prop="emTel">
          <el-input v-model="form.emTel" placeholder="请输入紧急联系电话" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="幼儿园规模" prop="scale">
          <el-input v-model="form.scale" placeholder="请输入幼儿园规模" />
        </el-form-item>
        <!--<el-form-item label="创建人ID" prop="createUser">-->
        <el-input v-model="form.createUser" placeholder="请输入创建人ID" type="hidden" />
        <!--</el-form-item>-->
        <el-form-item label="最后审核人ID" prop="approvalUser">
          <el-input v-model="form.approvalUser" placeholder="请输入最后审核人ID" />
        </el-form-item>
        <el-form-item label="审核时间" prop="approvalTime">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.approvalTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择审核时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="幼儿园位置" prop="location">
          <el-input v-model="form.location" placeholder="请输入幼儿园位置" />
        </el-form-item>
        <el-form-item label="园所会员性质" prop="isDemonstr">
          <el-input v-model="form.isDemonstr" placeholder="请输入园所会员性质" />
        </el-form-item>
        <el-form-item label="营业执照图片" prop="businesslicenseimg">
          <el-input v-model="form.businesslicenseimg" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="家长手册发布状态" prop="openBook">
          <el-input v-model="form.openBook" placeholder="请输入家长手册发布状态" />
        </el-form-item>
        <el-form-item label="幼儿园缴费状态">
          <el-radio-group v-model="form.feeStatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="开通截至日期" prop="openDeadline">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.openDeadline"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开通截至日期"
          ></el-date-picker>
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
import {
  listSchool,
  getSchool,
  delSchool,
  addSchool,
  updateSchool,
  exportSchool
} from "@/api/system/school";

export default {
  name: "School",
  data() {
    return {
      //显示集团属性的标志
      flag1: false,
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
      // 幼儿园机构表格数据
      schoolList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        schoolName: undefined,
        nameShort: undefined,
        type: undefined,
        typeOptions: [],
        parentId: undefined,
        province: undefined,
        provincename: undefined,
        regionid: undefined,
        regionname: undefined,
        area: undefined,
        areaname: undefined,
        address: undefined,
        mastername: undefined,
        tel: undefined,
        emMan: undefined,
        emTel: undefined,
        status: undefined,
        scale: undefined,
        createUser: undefined,
        createTime: undefined,
        approvalUser: undefined,
        approvalTime: undefined,
        location: undefined,
        isDemonstr: undefined,
        businesslicenseimg: undefined,
        openBook: undefined,
        feeStatus: undefined,
        openDeadline: undefined,
        dqxn: undefined,
        dqxq: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        schoolName: [
          { required: true, message: "机构名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_yeylx").then(response => {
      this.typeOptions = response.data;
    });
  },
  watch: {},
  methods: {
    /** 查询幼儿园机构列表 */
    getList() {
      this.loading = true;
      listSchool(this.queryParams).then(response => {
        this.schoolList = response.rows;
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
        schoolName: undefined,
        nameShort: undefined,
        type: undefined,
        parentId: undefined,
        province: undefined,
        provincename: undefined,
        regionid: undefined,
        regionname: undefined,
        area: undefined,
        areaname: undefined,
        address: undefined,
        mastername: undefined,
        tel: undefined,
        emMan: undefined,
        emTel: undefined,
        status: "0",
        scale: undefined,
        createTime: undefined,
        createUser: undefined,
        approvalUser: undefined,
        approvalTime: undefined,
        remark: undefined,
        location: undefined,
        isDemonstr: undefined,
        businesslicenseimg: undefined,
        openBook: undefined,
        feeStatus: "0",
        openDeadline: undefined,
        dqxn: undefined,
        dqxq: undefined
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
      this.title = "添加幼儿园机构";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getSchool(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改幼儿园机构";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateSchool(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              } else {
                this.msgError(response.msg);
              }
            });
          } else {
            addSchool(this.form).then(response => {
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
      const ids = row.id || this.ids;
      this.$confirm(
        '是否确认删除幼儿园机构编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delSchool(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
        .catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("是否确认导出所有幼儿园机构数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(function() {
          return exportSchool(queryParams);
        })
        .then(response => {
          this.download(response.msg);
        })
        .catch(function() {});
    },
    changeHandle(val) {
      if (val == 3) {
        this.flag1 = true;
      } else {
        this.flag1 = false;
      }
    }
  }
};
</script>