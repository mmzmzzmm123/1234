<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="Note便签ID" prop="noteId">
        <el-input
          v-model="queryParams.noteId"
          placeholder="请输入Note便签ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="便签标题(自动截取)" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入便签标题(自动截取)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="摘要(前50字) 循环展示" prop="description">
        <el-input
          v-model="queryParams.description"
          placeholder="请输入摘要(前50字) 循环展示"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属分组Id" prop="menuId">
        <el-input
          v-model="queryParams.menuId"
          placeholder="请输入所属分组Id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="背景颜色 '白','红','黄','蓝','紫'" prop="background">
        <el-select v-model="queryParams.background" placeholder="请选择背景颜色 '白','红','黄','蓝','紫'" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="字数" prop="noteCount">
        <el-input
          v-model="queryParams.noteCount"
          placeholder="请输入字数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="排序(小到大)" prop="noteSort">
        <el-input
          v-model="queryParams.noteSort"
          placeholder="请输入排序(小到大)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="便签状态" prop="isState">
        <el-input
          v-model="queryParams.isState"
          placeholder="请输入便签状态"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="阅读进度" prop="readProgress">
        <el-input
          v-model="queryParams.readProgress"
          placeholder="请输入阅读进度"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否星标" prop="isStar">
        <el-select v-model="queryParams.isStar" placeholder="请选择是否星标" clearable size="small">
          <el-option
            v-for="dict in isStarOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否删除" prop="isDelete">
        <el-select v-model="queryParams.isDelete" placeholder="请选择是否删除" clearable size="small">
          <el-option
            v-for="dict in isDeleteOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否置顶" prop="topFlag">
        <el-select v-model="queryParams.topFlag" placeholder="请选择是否置顶" clearable size="small">
          <el-option
            v-for="dict in topFlagOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否分享(判断是否可以随意访问)" prop="isShare">
        <el-select v-model="queryParams.isShare" placeholder="请选择是否分享(判断是否可以随意访问)" clearable size="small">
          <el-option
            v-for="dict in isShareOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否加密" prop="isEncryption">
        <el-select v-model="queryParams.isEncryption" placeholder="请选择是否加密" clearable size="small">
          <el-option
            v-for="dict in isEncryptionOptions"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['note:note:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['note:note:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['note:note:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['note:note:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="noteList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Note便签ID" align="center" prop="noteId" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="便签标题(自动截取)" align="center" prop="title" />
      <el-table-column label="摘要(前50字) 循环展示" align="center" prop="description" />
      <el-table-column label="所属分组Id" align="center" prop="menuId" />
      <el-table-column label="背景颜色 '白','红','黄','蓝','紫'" align="center" prop="background" />
      <el-table-column label="字数" align="center" prop="noteCount" />
      <el-table-column label="排序(小到大)" align="center" prop="noteSort" />
      <el-table-column label="便签状态" align="center" prop="isState" />
      <el-table-column label="阅读进度" align="center" prop="readProgress" />
      <el-table-column label="是否星标" align="center" prop="isStar" :formatter="isStarFormat" />
      <el-table-column label="是否删除" align="center" prop="isDelete" :formatter="isDeleteFormat" />
      <el-table-column label="是否置顶" align="center" prop="topFlag" :formatter="topFlagFormat" />
      <el-table-column label="是否分享(判断是否可以随意访问)" align="center" prop="isShare" :formatter="isShareFormat" />
      <el-table-column label="是否加密" align="center" prop="isEncryption" :formatter="isEncryptionFormat" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['note:note:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['note:note:remove']"
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

    <!-- 添加或修改便签管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="便签标题(自动截取)" prop="title">
          <el-input v-model="form.title" placeholder="请输入便签标题(自动截取)" />
        </el-form-item>
        <el-form-item label="摘要(前50字) 循环展示" prop="description">
          <el-input v-model="form.description" placeholder="请输入摘要(前50字) 循环展示" />
        </el-form-item>
        <el-form-item label="所属分组Id" prop="menuId">
          <el-input v-model="form.menuId" placeholder="请输入所属分组Id" />
        </el-form-item>
        <el-form-item label="背景颜色 '白','红','黄','蓝','紫'">
          <el-select v-model="form.background" placeholder="请选择背景颜色 '白','红','黄','蓝','紫'">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="字数" prop="noteCount">
          <el-input v-model="form.noteCount" placeholder="请输入字数" />
        </el-form-item>
        <el-form-item label="排序(小到大)" prop="noteSort">
          <el-input v-model="form.noteSort" placeholder="请输入排序(小到大)" />
        </el-form-item>
        <el-form-item label="便签状态" prop="isState">
          <el-input v-model="form.isState" placeholder="请输入便签状态" />
        </el-form-item>
        <el-form-item label="阅读进度" prop="readProgress">
          <el-input v-model="form.readProgress" placeholder="请输入阅读进度" />
        </el-form-item>
        <el-form-item label="是否星标">
          <el-radio-group v-model="form.isStar">
            <el-radio
              v-for="dict in isStarOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否删除">
          <el-radio-group v-model="form.isDelete">
            <el-radio
              v-for="dict in isDeleteOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否置顶">
          <el-radio-group v-model="form.topFlag">
            <el-radio
              v-for="dict in topFlagOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否分享(判断是否可以随意访问)">
          <el-radio-group v-model="form.isShare">
            <el-radio
              v-for="dict in isShareOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否加密">
          <el-radio-group v-model="form.isEncryption">
            <el-radio
              v-for="dict in isEncryptionOptions"
              :key="dict.dictValue"
              :label="parseInt(dict.dictValue)"
            >{{dict.dictLabel}}</el-radio>
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
import { listNote, getNote, delNote, addNote, updateNote, exportNote } from "@/api/note/note";

export default {
  name: "Note",
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
      // 便签管理表格数据
      noteList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否星标字典
      isStarOptions: [],
      // 是否删除字典
      isDeleteOptions: [],
      // 是否置顶字典
      topFlagOptions: [],
      // 是否分享(判断是否可以随意访问)字典
      isShareOptions: [],
      // 是否加密字典
      isEncryptionOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noteId: undefined,
        userId: undefined,
        title: undefined,
        description: undefined,
        menuId: undefined,
        background: undefined,
        noteCount: undefined,
        noteSort: undefined,
        isState: undefined,
        readProgress: undefined,
        isStar: undefined,
        isDelete: undefined,
        topFlag: undefined,
        isShare: undefined,
        isEncryption: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_yes_no").then(response => {
      this.isStarOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.isDeleteOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.topFlagOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.isShareOptions = response.data;
    });
    this.getDicts("sys_yes_no").then(response => {
      this.isEncryptionOptions = response.data;
    });
  },
  methods: {
    /** 查询便签管理列表 */
    getList() {
      this.loading = true;
      listNote(this.queryParams).then(response => {
        this.noteList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 是否星标字典翻译
    isStarFormat(row, column) {
      return this.selectDictLabel(this.isStarOptions, row.isStar);
    },
    // 是否删除字典翻译
    isDeleteFormat(row, column) {
      return this.selectDictLabel(this.isDeleteOptions, row.isDelete);
    },
    // 是否置顶字典翻译
    topFlagFormat(row, column) {
      return this.selectDictLabel(this.topFlagOptions, row.topFlag);
    },
    // 是否分享(判断是否可以随意访问)字典翻译
    isShareFormat(row, column) {
      return this.selectDictLabel(this.isShareOptions, row.isShare);
    },
    // 是否加密字典翻译
    isEncryptionFormat(row, column) {
      return this.selectDictLabel(this.isEncryptionOptions, row.isEncryption);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        noteId: undefined,
        userId: undefined,
        title: undefined,
        description: undefined,
        menuId: undefined,
        background: undefined,
        noteCount: undefined,
        noteSort: undefined,
        isState: undefined,
        readProgress: undefined,
        isStar: "0",
        isDelete: "0",
        topFlag: "0",
        isShare: "0",
        isEncryption: "0",
        createTime: undefined,
        updateTime: undefined
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
      this.ids = selection.map(item => item.noteId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加便签管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const noteId = row.noteId || this.ids
      getNote(noteId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改便签管理";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.noteId != undefined) {
            updateNote(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addNote(this.form).then(response => {
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
      const noteIds = row.noteId || this.ids;
      this.$confirm('是否确认删除便签管理编号为"' + noteIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delNote(noteIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有便签管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportNote(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>