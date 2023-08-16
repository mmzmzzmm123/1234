<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['psychology:class:add']"
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
          v-hasPermi="['psychology:class:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['psychology:class:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="PsyClassList" @selection-change="handleSelectionChange">
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="跳转url" align="center" prop="linkUrl" />
      <el-table-column label="图标路径" align="center" prop="url">
        <template slot-scope="scope">
          <image-preview :src="scope.row.url" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '0' ? 'success' : 'info'" effect="plain">
            {{ scope.row.status === "0" ? "启用" : "停用" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['psychology:class:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['psychology:class:remove']"
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

    <!-- 添加或修改咨询类型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number :min="1" :max="999" v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="图标路径" prop="url">
          <image-upload v-model="form.url" :extraData="{}"/>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-radio-group v-model="form.type">
            <el-radio label="0">跳转URL</el-radio>
            <el-radio label="1">快捷筛选</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="跳转url" prop="linkUrl" v-if="form.type === '0'">
          <el-input v-model="form.linkUrl" type="textarea" placeholder="跳转url" />
        </el-form-item>
        <template v-if="form.type === '1'">
          <el-form-item label="满足条件" prop="nand">
            <el-radio-group v-model="form.nand">
              <el-radio label="0">必须满足所有被选中的条件</el-radio>
              <el-radio label="1">满足任意一个被选中的条件</el-radio>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="服务条件" prop="serve">
            <el-checkbox true-label="0" false-label="1" v-model="form.serve">指定服务</el-checkbox>
            <el-select style="width: 240px;margin-left: 10px" v-model="form.serveId" placeholder="请选择服务" clearable>
              <el-option
                v-for="item in serves"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="价格条件" prop="price">
            <el-checkbox true-label="0" false-label="1" v-model="form.price">服务价格区间</el-checkbox>
            <el-input-number style="margin-left: 10px" size="mini" v-model="form.lowPrice" :min="0"/>
            <span style="margin: 0 10px">-</span>
            <el-input-number size="mini" v-model="form.highPrice" :min="0"/>
          </el-form-item>

          <el-form-item label="咨询师条件" prop="buy">
            <el-checkbox true-label="0" false-label="1" v-model="form.buy">当日可约</el-checkbox>
          </el-form-item>
        </template>

        <el-form-item label="启用" prop="status">
          <el-switch v-model="form.status" active-value="0" inactive-value="1"/>
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
import { listPsyClass, getPsyClass, delPsyClass, addPsyClass, updatePsyClass } from "@/api/psychology/psyClass";
import { getList } from "@/api/psychology/serveConfig";

export default {
  name: "PsyClass",
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
      // 咨询类型表格数据
      serves: [],
      PsyClassList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        sort: null,
        url: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" }
        ],
        url: [
          { required: true, message: "图标不能为空", trigger: "change" }
        ],
        type: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],
        linkUrl: [
          { required: true, message: "URL不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "下班时间不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getServes();
    this.getList();
  },
  methods: {
    getServes() {
      getList({}).then(response => {
        if (response && response.code === 200) {
          this.serves = response.data
        }
      })
    },
    /** 查询咨询类型列表 */
    getList() {
      this.loading = true;
      listPsyClass(this.queryParams).then(response => {
        this.PsyClassList = response.rows;
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
        id: null,
        name: null,
        sort: 1,
        url: null,
        linkUrl: null,
        delFlag: null,
        status: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        type: "0",
        nand: "1",
        serveId: null,
        lowPrice: null,
        highPrice: null,
        buy: "1",
        price: "1",
        serve: "1"
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
      this.title = "添加首页tab";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getPsyClass(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改首页tab";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePsyClass(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPsyClass(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
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
      this.$modal.confirm('是否确认删除名称为"' + row.name + '"的数据项？').then(function() {
        return delPsyClass(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('psychology/psyClass/export', {
        ...this.queryParams
      }, `PsyClass_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
