<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> 
      <el-form-item label="性别" prop="xb">
        <el-input
          v-model="queryParams.xb"
          placeholder="请输入性别"
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
          v-hasPermi="['benyi:child:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['benyi:child:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['benyi:child:remove']"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="childList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="学校id" align="center" prop="schoolid" />
      <el-table-column label="班级id" align="center" prop="classid" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="英文名" align="center" prop="enName" />
      <el-table-column label="小名" align="center" prop="infantName" />
      <el-table-column label="家长手机号码" align="center" prop="phone" />
      <el-table-column label="性别" align="center" prop="xb" />
      <el-table-column label="民族" align="center" prop="mz" />
      <el-table-column label="证件号码" align="center" prop="zjhm" />
      <el-table-column label="出生日期" align="center" prop="csrq" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.csrq, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出生所在省" align="center" prop="birthProvince" />
      <el-table-column label="出生所在地市" align="center" prop="birthCity" />
      <el-table-column label="出生地区" align="center" prop="birthArea" />
      <el-table-column label="户口所在地" align="center" prop="registeredProvince" />
      <el-table-column label="户口所在地市" align="center" prop="registeredCity" />
      <el-table-column label="户口所在区" align="center" prop="registeredArea" />
      <el-table-column label="住址省" align="center" prop="addrProvince" />
      <el-table-column label="住址市" align="center" prop="addrCity" />
      <el-table-column label="住址区" align="center" prop="addrArea" />
      <el-table-column label="详细地址" align="center" prop="addrDetail" />
      <el-table-column label="曾经就读幼儿园" align="center" prop="everSchool" />
      <el-table-column label="是否学习英语" align="center" prop="learnEnglish" />
      <el-table-column label="入园渠道" align="center" prop="source" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="入园时间" align="center" prop="enterDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.enterDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="离园时间" align="center" prop="outDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.outDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="第一语言" align="center" prop="firstLanguage" />
      <el-table-column label="第二语言" align="center" prop="seconderLanguage" />
      <el-table-column label="其他语言" align="center" prop="otherLanguage" />
      <el-table-column label="创建人" align="center" prop="createuserid" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:child:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:child:remove']"
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

    <!-- 添加或修改幼儿信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学校id" prop="schoolid">
          <el-input v-model="form.schoolid" placeholder="请输入学校id" />
        </el-form-item>
        <el-form-item label="班级id" prop="classid">
          <el-input v-model="form.classid" placeholder="请输入班级id" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="英文名" prop="enName">
          <el-input v-model="form.enName" placeholder="请输入英文名" />
        </el-form-item>
        <el-form-item label="小名" prop="infantName">
          <el-input v-model="form.infantName" placeholder="请输入小名" />
        </el-form-item>
        <el-form-item label="家长手机号码" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入家长手机号码" />
        </el-form-item>
        <el-form-item label="性别" prop="xb">
          <el-input v-model="form.xb" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="民族" prop="mz">
          <el-input v-model="form.mz" placeholder="请输入民族" />
        </el-form-item>
        <el-form-item label="证件号码" prop="zjhm">
          <el-input v-model="form.zjhm" placeholder="请输入证件号码" />
        </el-form-item>
        <el-form-item label="出生日期" prop="csrq">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.csrq"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择出生日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="出生所在省" prop="birthProvince">
          <el-input v-model="form.birthProvince" placeholder="请输入出生所在省" />
        </el-form-item>
        <el-form-item label="出生所在地市" prop="birthCity">
          <el-input v-model="form.birthCity" placeholder="请输入出生所在地市" />
        </el-form-item>
        <el-form-item label="出生地区" prop="birthArea">
          <el-input v-model="form.birthArea" placeholder="请输入出生地区" />
        </el-form-item>
        <el-form-item label="户口所在地" prop="registeredProvince">
          <el-input v-model="form.registeredProvince" placeholder="请输入户口所在地" />
        </el-form-item>
        <el-form-item label="户口所在地市" prop="registeredCity">
          <el-input v-model="form.registeredCity" placeholder="请输入户口所在地市" />
        </el-form-item>
        <el-form-item label="户口所在区" prop="registeredArea">
          <el-input v-model="form.registeredArea" placeholder="请输入户口所在区" />
        </el-form-item>
        <el-form-item label="住址省" prop="addrProvince">
          <el-input v-model="form.addrProvince" placeholder="请输入住址省" />
        </el-form-item>
        <el-form-item label="住址市" prop="addrCity">
          <el-input v-model="form.addrCity" placeholder="请输入住址市" />
        </el-form-item>
        <el-form-item label="住址区" prop="addrArea">
          <el-input v-model="form.addrArea" placeholder="请输入住址区" />
        </el-form-item>
        <el-form-item label="详细地址" prop="addrDetail">
          <el-input v-model="form.addrDetail" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="曾经就读幼儿园" prop="everSchool">
          <el-input v-model="form.everSchool" placeholder="请输入曾经就读幼儿园" />
        </el-form-item>
        <el-form-item label="是否学习英语" prop="learnEnglish">
          <el-input v-model="form.learnEnglish" placeholder="请输入是否学习英语" />
        </el-form-item>
        <el-form-item label="入园渠道" prop="source">
          <el-input v-model="form.source" placeholder="请输入入园渠道" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="入园时间" prop="enterDate">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.enterDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择入园时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="离园时间" prop="outDate">
          <el-date-picker
            clearable
            size="small"
            style="width: 200px"
            v-model="form.outDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择离园时间"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="第一语言" prop="firstLanguage">
          <el-input v-model="form.firstLanguage" placeholder="请输入第一语言" />
        </el-form-item>
        <el-form-item label="第二语言" prop="seconderLanguage">
          <el-input v-model="form.seconderLanguage" placeholder="请输入第二语言" />
        </el-form-item>
        <el-form-item label="其他语言" prop="otherLanguage">
          <el-input v-model="form.otherLanguage" placeholder="请输入其他语言" />
        </el-form-item>
        <el-form-item label="创建人" prop="createuserid">
          <el-input v-model="form.createuserid" placeholder="请输入创建人" />
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
  listChild,
  getChild,
  delChild,
  addChild,
  updateChild
} from "@/api/benyi/child";

export default {
  name: "Child",
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
      // 幼儿信息表格数据
      childList: [],
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
        name: undefined,
        enName: undefined,
        infantName: undefined,
        phone: undefined,
        xb: undefined,
        mz: undefined,
        zjhm: undefined,
        csrq: undefined,
        birthProvince: undefined,
        birthCity: undefined,
        birthArea: undefined,
        registeredProvince: undefined,
        registeredCity: undefined,
        registeredArea: undefined,
        addrProvince: undefined,
        addrCity: undefined,
        addrArea: undefined,
        addrDetail: undefined,
        everSchool: undefined,
        learnEnglish: undefined,
        source: undefined,
        status: undefined,
        enterDate: undefined,
        outDate: undefined,
        firstLanguage: undefined,
        seconderLanguage: undefined,
        otherLanguage: undefined,
        createuserid: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询幼儿信息列表 */
    getList() {
      this.loading = true;
      listChild(this.queryParams).then(response => {
        this.childList = response.rows;
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
        name: undefined,
        enName: undefined,
        infantName: undefined,
        phone: undefined,
        xb: undefined,
        mz: undefined,
        zjhm: undefined,
        csrq: undefined,
        birthProvince: undefined,
        birthCity: undefined,
        birthArea: undefined,
        registeredProvince: undefined,
        registeredCity: undefined,
        registeredArea: undefined,
        addrProvince: undefined,
        addrCity: undefined,
        addrArea: undefined,
        addrDetail: undefined,
        everSchool: undefined,
        learnEnglish: undefined,
        source: undefined,
        status: "0",
        enterDate: undefined,
        outDate: undefined,
        firstLanguage: undefined,
        seconderLanguage: undefined,
        otherLanguage: undefined,
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
      this.title = "添加幼儿信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getChild(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改幼儿信息";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateChild(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addChild(this.form).then(response => {
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
        '是否确认删除幼儿信息编号为"' + ids + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function() {
          return delChild(ids);
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