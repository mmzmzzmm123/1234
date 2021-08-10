<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="ta的名字" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入ta的名字"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否阳历生日" prop="solarFlag">
        <el-select v-model="queryParams.solarFlag" placeholder="请选择是否阳历生日" clearable size="small">
          <el-option
            v-for="dict in solarFlagOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
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
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:birthdaypu:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:birthdaypu:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:birthdaypu:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
		  :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:birthdaypu:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="birthdaypuList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="圈子ID" align="center" prop="id" />
      <el-table-column label="ta的名字" align="center" prop="name" />
      <el-table-column label="用户的id" align="center" prop="userId" />
      <el-table-column label="阳历生日" align="center" prop="solarBirthday" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.solarBirthday, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="阴历生日" align="center" prop="birthday" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否阳历生日" align="center" prop="solarFlag" :formatter="solarFlagFormat" />
      <el-table-column label="是否提醒" align="center" prop="notifyFlag" :formatter="notifyFlagFormat" />
      <el-table-column label="星座" align="center" prop="constellation" />
      <el-table-column label="生肖" align="center" prop="zodiac" />
      <el-table-column label="年重复" align="center" prop="yearRepeat" :formatter="yearRepeatFormat" />
      <el-table-column label="提前通知" align="center" prop="dayRepeat" :formatter="dayRepeatFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:birthdaypu:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:birthdaypu:remove']"
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

    <!-- 添加或修改生日溥对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="ta的名字" prop="name">
          <el-input v-model="form.name" placeholder="请输入ta的名字" />
        </el-form-item>
       <!-- <el-form-item label="用户的id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户的id" />
        </el-form-item> -->
        <el-form-item label="阳历生日" prop="solarBirthday">
          <el-date-picker clearable size="small"
            v-model="form.solarBirthday"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择阳历生日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="阴历生日" prop="birthday">
          <el-date-picker clearable size="small"
            v-model="form.birthday"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择阴历生日">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否阳历生日">
          <el-radio-group v-model="form.solarFlag">
            <el-radio
              v-for="dict in solarFlagOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否提醒">
          <el-radio-group v-model="form.notifyFlag">
            <el-radio
              v-for="dict in notifyFlagOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="星座" prop="constellation">
          <el-input v-model="form.constellation" placeholder="请输入星座" />
        </el-form-item>
        <el-form-item label="生肖" prop="zodiac">
          <el-input v-model="form.zodiac" placeholder="请输入生肖" />
        </el-form-item>
        <el-form-item label="年重复提醒" prop="yearRepeat">
          <el-select v-model="form.yearRepeat" placeholder="请选择年重复提醒">
            <el-option
              v-for="dict in yearRepeatOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="提前几天通知" prop="dayRepeat">
          <el-select v-model="form.dayRepeat" placeholder="请选择提前几天通知">
            <el-option
              v-for="dict in dayRepeatOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生日溥状态">
          <el-radio-group v-model="form.status">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
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
import { listBirthdaypu, getBirthdaypu, delBirthdaypu, addBirthdaypu, updateBirthdaypu, exportBirthdaypu } from "@/api/system/birthdaypu";

export default {
  name: "Birthdaypu",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
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
      // 生日溥表格数据
      birthdaypuList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否阳历生日字典
      solarFlagOptions: [],
      // 是否提醒字典
      notifyFlagOptions: [],
      // 年重复提醒字典
      yearRepeatOptions: [],
      // 提前几天通知，复选框数据字典
      dayRepeatOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        userId: null,
        solarBirthday: null,
        birthday: null,
        solarFlag: null,
        notifyFlag: null,
        constellation: null,
        zodiac: null,
        yearRepeat: null,
        dayRepeat: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "ta的名字不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "用户的id不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("flag_yes_no").then(response => {
      this.solarFlagOptions = response.data;
    });
    this.getDicts("flag_yes_no").then(response => {
      this.notifyFlagOptions = response.data;
    });
    this.getDicts("birthday_notify_year").then(response => {
      this.yearRepeatOptions = response.data;
    });
    this.getDicts("birthday_notify_day").then(response => {
      this.dayRepeatOptions = response.data;
    });
  },
  methods: {
    /** 查询生日溥列表 */
    getList() {
      this.loading = true;
      listBirthdaypu(this.queryParams).then(response => {
        this.birthdaypuList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 是否阳历生日字典翻译
    solarFlagFormat(row, column) {
      return this.selectDictLabel(this.solarFlagOptions, row.solarFlag);
    },
    // 是否提醒字典翻译
    notifyFlagFormat(row, column) {
      return this.selectDictLabel(this.notifyFlagOptions, row.notifyFlag);
    },
    // 年重复提醒字典翻译
    yearRepeatFormat(row, column) {
      return this.selectDictLabel(this.yearRepeatOptions, row.yearRepeat);
    },
    // 提前几天通知，复选框数据字典翻译
    dayRepeatFormat(row, column) {
      return this.selectDictLabel(this.dayRepeatOptions, row.dayRepeat);
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
        name: null,
        userId: null,
        solarBirthday: null,
        birthday: null,
        solarFlag: "0",
        notifyFlag: "0",
        constellation: null,
        zodiac: null,
        yearRepeat: null,
        dayRepeat: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        status: "0",
        remark: null,
        ext1: null,
        ext2: null,
        ext3: null
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
      this.title = "添加生日溥";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBirthdaypu(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改生日溥";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBirthdaypu(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBirthdaypu(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除生日溥编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delBirthdaypu(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有生日溥数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.exportLoading = true;
          return exportBirthdaypu(queryParams);
        }).then(response => {
          this.download(response.msg);
          this.exportLoading = false;
        }).catch(() => {});
    }
  }
};
</script>
