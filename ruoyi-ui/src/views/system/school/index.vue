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
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
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
        <el-select v-model="queryParams.status" placeholder="状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="截止时间">
                  <el-date-picker
                    v-model="dateRange"
                    size="small"
                    class="my-date-picker"
                    value-format="yyyy-MM-dd"
                    type="daterange"
                    range-separator="-"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
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

    <el-table v-loading="loading" :data="schoolList" :default-sort = "{prop: 'days', order: 'descending'}" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="幼儿园名称" align="center" prop="schoolName" />
      <el-table-column label="幼儿园类型" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="所在省" align="center" prop="provincename" />
      <el-table-column label="所在城市" align="center" prop="regionname" />
      <el-table-column label="详细地址" align="center" prop="address" />
      <el-table-column label="联系人" align="center" prop="mastername" />
      <el-table-column label="电话" align="center" prop="tel" />
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
      <!--<el-table-column label="创建人" align="center" prop="createUser" />-->
      <el-table-column label="创建时间" align="center" prop="createTime" />
      <el-table-column label="开通截至时间" align="center" prop="openDeadline" />
       <el-table-column label="剩余天数" align="center" prop="days" sortable />
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
    <el-dialog :title="title" :visible.sync="open" width="800px">
      <el-row :gutter="15">
        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
          <el-col :span="12">
            <el-form-item label="幼儿园名称" prop="schoolName">
              <el-input v-model="form.schoolName" placeholder="请输入幼儿园名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="幼儿园简称" prop="nameShort">
              <el-input v-model="form.nameShort" placeholder="请输入幼儿园简称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="幼儿园类型" props="type">
              <el-radio-group v-model="form.type" @change="changeHandle">
                <el-radio
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="选择集团园" prop="parentId" v-show="flag1">
              <!-- <el-input v-model="form.parentId" placeholder="请选择集团园" /> -->
              <el-select v-model="form.parentId" filterable placeholder="请选择集团园">
                <el-option
                  v-for="item in schoolOptions"
                  :key="item.id"
                  :label="item.schoolName"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="所在省" prop="provincename">
              <v-distpicker
                v-model="form.provincename"
                :placeholders="placeholders"
                :province="diglogForm.province"
                :city="diglogForm.city"
                :area="diglogForm.area"
                @selected="onSelected"
              ></v-distpicker>
              <el-input v-model="form.province" v-if="false" />
              <el-input v-model="form.regionid" v-if="false" />
              <el-input v-model="form.area" v-if="false" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="详细地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入详细地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="mastername">
              <el-input v-model="form.mastername" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="tel">
              <el-input v-model="form.tel" placeholder="请输入电话" maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="紧急联系人" prop="emMan">
              <el-input v-model="form.emMan" placeholder="请输入紧急联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="紧急联系电话" prop="emTel">
              <el-input v-model="form.emTel" placeholder="请输入紧急联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" props="status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="幼儿园规模" prop="scale">
              <el-select v-model="form.scale">
                <el-option
                  v-for="dict in scaleOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="创建人ID" prop="createUser" v-show="false">
              <el-input v-model="form.createUser" placeholder="请输入创建人ID" />
            </el-form-item>
            <el-form-item label="最后审核人ID" prop="approvalUser" v-show="false">
              <el-input v-model="form.approvalUser" placeholder="请输入最后审核人ID" />
            </el-form-item>
            <el-form-item label="创建时间" prop="createTime" v-show="false">
              <el-input v-model="form.createTime"></el-input>
            </el-form-item>
            <el-form-item label="园所会员性质" prop="isDemonstr">
              <el-select v-model="form.isDemonstr" placeholder="请选择">
                <el-option
                  v-for="dict in isDemonstrOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="家长手册发布状态" prop="openBook" v-show="false">
              <el-input v-model="form.openBook" placeholder="请输入家长手册发布状态" />
            </el-form-item>
            <el-form-item label="幼儿园缴费状态" prop="feeStatus">
              <el-radio-group v-model="form.feeStatus">
                <el-radio
                  v-for="dict in feeStatusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
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
//导入省市区三级联动库
import VDistpicker from "v-distpicker";
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
      placeholders: {
        province: "请选择省",
        city: "请选择市",
        area: "请选择区"
      },
      diglogForm: {
        province: null,
        city: null,
        area: null
      },
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
      //幼儿园类型开关
      typeOptions: [],
      //幼儿园状态开关数组
      statusOptions: [],
      //幼儿园缴费状态
      feeStatusOptions: [],
      //幼儿园会员性质
      isDemonstrOptions: [],
      //幼儿园规模选项
      scaleOptions: [],
      //幼儿园列表
      schoolOptions: [],
            // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        xxdm: undefined,
        schoolName: undefined,
        nameShort: undefined,
        type: undefined,
        parentId: undefined,
        province: undefined,
        provincename: undefined,
        // regionid: undefined,
        regionname: undefined,
        // area: undefined,
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
        // location: undefined,
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
          {
            required: true,
            message: "机构名称不能为空",
            trigger: "blur"
          }
        ],
        tel: [
          {
            required: true,
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }
        ],
        provincename: [
          {
            required: true,
            message: "省市区不能为空",
            trigger: "blur"
          }
        ],
        address: [
          {
            required: true,
            message: "详细地址不能为空",
            trigger: "blur"
          }
        ],
        mastername: [
          {
            required: true,
            message: "联系人不能为空",
            trigger: "blur"
          }
        ],
        emMan: [
          {
            required: true,
            message: "紧急联系人不能为空",
            trigger: "blur"
          }
        ],
        emTel: [
          {
            required: true,
            message: "紧急联系人电话不能为空",
            trigger: "blur"
          }
        ],
        openDeadline: [
          {
            required: true,
            message: "开通截止日期不能为空",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.getList();
    //幼儿园类型选择开关
    this.getDicts("sys_yeylx").then(response => {
      this.typeOptions = response.data;
    });
    //幼儿园状态开关
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    //幼儿园规模选项
    this.getDicts("sys_yeygm").then(response => {
      this.scaleOptions = response.data;
    });
    //幼儿园园所会员性质选项
    this.getDicts("sys_yeyysxz").then(response => {
      this.isDemonstrOptions = response.data;
    });
    //幼儿园缴费状态
    this.getDicts("sys_yeyjfzt").then(response => {
      this.feeStatusOptions = response.data;
    });
    listSchool(null).then(response => {
      this.schoolOptions = response.rows;
    });
  },
  components: {
    //省市区三级联动全局组件
    VDistpicker
  },
  methods: {
    /** 查询幼儿园机构列表 */
    getList() {
      this.loading = true;
      listSchool(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.schoolList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 幼儿园类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
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
        xxdm: undefined,
        schoolName: undefined,
        nameShort: undefined,
        type: "1",
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
        scale: "1",
        createTime: undefined,
        createUser: undefined,
        approvalUser: undefined,
        approvalTime: undefined,
        remark: undefined,
        // location: undefined,
        isDemonstr: "1",
        businesslicenseimg: undefined,
        openBook: undefined,
        feeStatus: "1",
        openDeadline: undefined,
        dqxn: undefined,
        dqxq: undefined
      };

      this.diglogForm.province = "";
      this.diglogForm.city = "";
      this.diglogForm.area = "";
      this.flag1 = false;

      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
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
        this.diglogForm.province = response.data.provincename;
        this.diglogForm.city = response.data.regionname;
        this.diglogForm.area = response.data.areaname;
        //判断是否下属园
        if (response.data.type == "3") {
          this.flag1 = true;
        }
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
    //幼儿园类型中集团下属显示和隐藏切换方法
    changeHandle(val) {
      if (val == 3) {
        this.flag1 = true;
      } else {
        this.flag1 = false;
      }
    },
    //所在省市区触发联动方法
    onSelected(data) {
      //console.log(data);
      if (
        data.province.code == undefined ||
        data.city.code == undefined ||
        data.area.code == undefined
      ) {
        this.form.provincename = undefined;
      } else {
        this.form.provincename = data.province.value;
        this.form.province = data.province.code;
        this.form.regionname = data.city.value;
        this.form.regionid = data.city.code;
        this.form.areaname = data.area.value;
        this.form.area = data.area.code;
      }
    }
  }
};
</script>
