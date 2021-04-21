<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
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
        <el-select v-model="queryParams.xb" placeholder="请选择性别" clearable size="small">
          <el-option
            v-for="dict in xbOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input
          v-model="queryParams.age"
          placeholder="请输入年龄"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学历" prop="xl">
        <el-select v-model="queryParams.xl" placeholder="请选择学历" clearable size="small">
          <el-option
            v-for="dict in xlOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="职称" prop="zc">
        <el-select v-model="queryParams.zc" placeholder="请选择职称" clearable size="small">
          <el-option
            v-for="dict in zcOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="专长" prop="zhch">
        <el-select v-model="queryParams.zhch" placeholder="请选择专长" clearable size="small">
          <el-option
            v-for="dict in zhchOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="单位" prop="dw">
        <el-input
          v-model="queryParams.dw"
          placeholder="请输入单位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分工" prop="fg">
        <el-select v-model="queryParams.fg" placeholder="请选择分工" clearable size="small">
          <el-option
            v-for="dict in fgOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建人" prop="createUserid">
        <el-input
          v-model="queryParams.createUserid"
          placeholder="请输入创建人"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
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
          v-hasPermi="['zcrpsgl:zcrzjzmd:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['zcrpsgl:zcrzjzmd:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['zcrpsgl:zcrzjzmd:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['zcrpsgl:zcrzjzmd:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="zcrzjzmdList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="性别" align="center" prop="xb" :formatter="xbFormat" />
      <el-table-column label="年龄" align="center" prop="age" />
      <el-table-column label="学历" align="center" prop="xl" :formatter="xlFormat" />
      <el-table-column label="职称" align="center" prop="zc" :formatter="zcFormat" />
      <el-table-column label="专长" align="center" prop="zhch" :formatter="zhchFormat" />
      <el-table-column label="单位" align="center" prop="dw" />
      <el-table-column label="分工" align="center" prop="fg" :formatter="fgFormat" />
      <el-table-column label="创建人" align="center" prop="createUserid" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['zcrpsgl:zcrzjzmd:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['zcrpsgl:zcrzjzmd:remove']"
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

    <!-- 添加或修改主持人报名专家组名单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="xb">
          <el-select v-model="form.xb" placeholder="请选择性别">
            <el-option
              v-for="dict in xbOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="学历" prop="xl">
          <el-select v-model="form.xl" placeholder="请选择学历">
            <el-option
              v-for="dict in xlOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职称" prop="zc">
          <el-select v-model="form.zc" placeholder="请选择职称">
            <el-option
              v-for="dict in zcOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="专长" prop="zhch">
          <el-select v-model="form.zhch" placeholder="请选择专长">
            <el-option
              v-for="dict in zhchOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单位" prop="dw">
          <el-input v-model="form.dw" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="分工" prop="fg">
          <el-select v-model="form.fg" placeholder="请选择分工">
            <el-option
              v-for="dict in fgOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建人" prop="createUserid">
          <el-input v-model="form.createUserid" placeholder="请输入创建人" />
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
import { listZcrzjzmd, getZcrzjzmd, delZcrzjzmd, addZcrzjzmd, updateZcrzjzmd, exportZcrzjzmd } from "@/api/zcrpsgl/zcrzjzmd";

export default {
  name: "Zcrzjzmd",
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 主持人报名专家组名单表格数据
      zcrzjzmdList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 性别字典
      xbOptions: [],
      // 学历字典
      xlOptions: [],
      // 职称字典
      zcOptions: [],
      // 专长字典
      zhchOptions: [],
      // 分工字典
      fgOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        xb: null,
        age: null,
        xl: null,
        zc: null,
        zhch: null,
        dw: null,
        fg: null,
        createUserid: null
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
    this.getDicts("sys_user_sex").then(response => {
      this.xbOptions = response.data;
    });
    this.getDicts("sys_dm_xl").then(response => {
      this.xlOptions = response.data;
    });
    this.getDicts("sys_dm_zcrzjzzc").then(response => {
      this.zcOptions = response.data;
    });
    this.getDicts("sys_dm_zcrbmzjzzc").then(response => {
      this.zhchOptions = response.data;
    });
    this.getDicts("sys_dm_zcrzjzfg").then(response => {
      this.fgOptions = response.data;
    });
  },
  methods: {
    /** 查询主持人报名专家组名单列表 */
    getList() {
      this.loading = true;
      listZcrzjzmd(this.queryParams).then(response => {
        this.zcrzjzmdList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 性别字典翻译
    xbFormat(row, column) {
      return this.selectDictLabel(this.xbOptions, row.xb);
    },
    // 学历字典翻译
    xlFormat(row, column) {
      return this.selectDictLabel(this.xlOptions, row.xl);
    },
    // 职称字典翻译
    zcFormat(row, column) {
      return this.selectDictLabel(this.zcOptions, row.zc);
    },
    // 专长字典翻译
    zhchFormat(row, column) {
      return this.selectDictLabel(this.zhchOptions, row.zhch);
    },
    // 分工字典翻译
    fgFormat(row, column) {
      return this.selectDictLabel(this.fgOptions, row.fg);
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
        xb: null,
        age: null,
        xl: null,
        zc: null,
        zhch: null,
        dw: null,
        fg: null,
        createTime: null,
        createUserid: null
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
      this.title = "添加主持人报名专家组名单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getZcrzjzmd(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改主持人报名专家组名单";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateZcrzjzmd(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addZcrzjzmd(this.form).then(response => {
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
      this.$confirm('是否确认删除主持人报名专家组名单编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delZcrzjzmd(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有主持人报名专家组名单数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportZcrzjzmd(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
