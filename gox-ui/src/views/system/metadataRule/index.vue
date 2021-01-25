<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号" prop="num">
        <el-input
          v-model="queryParams.num"
          placeholder="请输入编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="中文名称" prop="nameCn">
        <el-input
          v-model="queryParams.nameCn"
          placeholder="请输入中文名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="英文名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入英文名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="元素类型" prop="elementType">
        <el-select v-model="queryParams.elementType" placeholder="请选择元素类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="数据类型" prop="dataType">
        <el-select v-model="queryParams.dataType" placeholder="请选择数据类型" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
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
          v-hasPermi="['system:metadataRule:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:metadataRule:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:metadataRule:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:metadataRule:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="metadataRuleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="id" align="center" prop="id" />-->
<!--      <el-table-column label="编号" align="center" prop="num" />-->
      <el-table-column label="中文名称" align="center" prop="nameCn" />
<!--      <el-table-column label="英文名称" align="center" prop="name" />-->
      <el-table-column label="定义" align="center" prop="definition" />
      <el-table-column label="目的" align="center" prop="purpose" />
      <el-table-column label="约束性" align="center" prop="constraintion" />
<!--      <el-table-column label="条件" align="center" prop="conditions" />-->
<!--      <el-table-column label="相关正则" align="center" prop="regex" />-->
      <el-table-column label="可重复性" align="center" prop="repeatability" />
<!--      <el-table-column label="元素类型" align="center" prop="elementType" />-->
      <el-table-column label="数据类型" align="center" prop="dataType" />
<!--      <el-table-column label="编码修饰体系" align="center" prop="encode" />-->
      <el-table-column label="值域" align="center" prop="valueRange" />
      <el-table-column label="缺省值" align="center" prop="defaultValue" />
<!--      <el-table-column label="子元素" align="center" prop="subelement" />-->
      <el-table-column label="信息来源" align="center" prop="sourcesOfInformation" />
<!--      <el-table-column label="相关元素" align="center" prop="coherentElement" />-->
      <el-table-column label="注释" align="center" prop="comments" />
<!--      <el-table-column label="类型" align="center" prop="type" />-->
      <el-table-column label="长度" align="center" prop="len" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:metadataRule:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:metadataRule:remove']"
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

    <!-- 添加或修改元数据设置对话框 -->
    <el-dialog :title="title"  :visible.sync="open" width="1500px"  append-to-body>
      <el-form ref="form" :inline="true" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="编号" prop="num">
          <el-input v-model="form.num" placeholder="请输入编号" />
        </el-form-item>
        <el-form-item label="中文名称" prop="nameCn">
          <el-input v-model="form.nameCn" placeholder="请输入中文名称" />
        </el-form-item>
        <el-form-item label="英文名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入英文名称" />
        </el-form-item>
        <br>
        <el-form-item label="定义" prop="definition">
          <el-input v-model="form.definition" autosize style="width:200%"  type="textarea" placeholder="请输入内容" maxlength="200" show-word-limit/>
        </el-form-item>
        <br>
        <el-form-item label="目的" prop="purpose">
          <el-input v-model="form.purpose" autosize style="width:200%"   type="textarea" placeholder="请输入内容"  maxlength="200" show-word-limit/>
        </el-form-item>
        <br>
        <el-form-item label="约束性" prop="constraintion">
          <el-select v-model="form.constraintion" placeholder="请输入约束性">
            <el-option label="可选" value="可选" />
            <el-option label="条件选" value="条件选" />
            <el-option label="必选" value="必选" />
          </el-select>
        </el-form-item>
        <el-form-item label="可重复性" prop="repeatability">
          <el-select v-model="form.repeatability" placeholder="可重复性">
            <el-option label="可重复" value="可重复" />
            <el-option label="不可重复" value="不可重复" />
            <el-option label="----" value="----" />
          </el-select>
        </el-form-item>
        <el-form-item label="元素类型" prop="elementType">
          <el-select v-model="form.elementType" placeholder="请选择元素类型">
            <el-option label="简单型" value="简单型" />
            <el-option label="复合型" value="复合型" />
            <el-option label="容器型" value="容器型" />
          </el-select>
        </el-form-item>
        <el-form-item label="数据类型" prop="dataType">
          <el-select v-model="form.dataType" placeholder="请选择数据类型">
            <el-option label="字符型" value="字符型" />
            <el-option label="数值型" value="数值型" />
            <el-option label="----" value="----"/>
          </el-select>
        </el-form-item>
        <el-form-item label="编码修饰体系" prop="encode">
          <el-input v-model="form.encode" placeholder="请输入编码修饰体系" />
        </el-form-item>
        <el-form-item label="值域" prop="valueRange">
          <el-input v-model="form.valueRange" placeholder="请输入值域 用,隔开" />
        </el-form-item>
        <el-form-item label="缺省值" prop="defaultValue">
          <el-input v-model="form.defaultValue" placeholder="请输入缺省值" />
        </el-form-item>
<!--        <el-form-item label="子元素" prop="subelement">-->
<!--          <el-input v-model="form.subelement" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
        <br>
        <el-form-item label="信息来源" prop="sourcesOfInformation">
          <el-input v-model="form.sourcesOfInformation" autosize style="width:200%" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <br>
<!--        <el-form-item label="相关元素" prop="coherentElement">-->
<!--          <el-input v-model="form.coherentElement" placeholder="请输入相关元素" />-->
<!--        </el-form-item>-->
        <el-form-item label="注释" prop="comments">
          <el-input v-model="form.comments" autosize type="textarea" style="width:200%" placeholder="请输入内容" />
        </el-form-item>
        <br>
<!--        <el-form-item label="类型" prop="type">-->
<!--          <el-select v-model="form.type" placeholder="请选择类型">-->
<!--            <el-option label="请选择字典生成" value="" />-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <el-form-item label="长度" prop="len">
          <el-input v-model="form.len" maxlength="5" placeholder="请输入长度" />
        </el-form-item>
        <el-form-item label="条件" prop="conditions">
          <el-input v-model="form.conditions" placeholder="请输入条件" />
        </el-form-item>
        <el-form-item label="相关正则" prop="regex">
          <el-input v-model="form.regex" placeholder="请输入相关正则" />
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
import { listMetadataRule, getMetadataRule, delMetadataRule, addMetadataRule, updateMetadataRule, exportMetadataRule } from "@/api/system/metadataRule";

export default {
  name: "MetadataRule",
  components: {
  },
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
      showSearch: false,
      // 总条数
      total: 0,
      // 元数据设置表格数据
      metadataRuleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        num: null,
        nameCn: null,
        name: null,
        definition: null,
        purpose: null,
        elementType: null,
        dataType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        nameCn: [
          { required: true, message: "中文名称不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "英文名称不能为空", trigger: "blur" }
        ],
        definition: [
          { required: true, message: "定义不能为空", trigger: "blur" }
        ],
        purpose: [
          { required: true, message: "目的不能为空", trigger: "blur" }
        ],
        constraintion: [
          { required: true, message: "约束性不能为空", trigger: "blur" }
        ],
        len:[
          {type: 'number', message: '年龄必须为数字值'}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询元数据设置列表 */
    getList() {
      this.loading = true;
      listMetadataRule(this.queryParams).then(response => {
        this.metadataRuleList = response.rows;
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
        num: null,
        nameCn: null,
        name: null,
        definition: null,
        purpose: null,
        constraintion: null,
        conditions: null,
        regex: null,
        repeatability: null,
        elementType: null,
        dataType: null,
        encode: null,
        valueRange: null,
        defaultValue: null,
        subelement: null,
        sourcesOfInformation: null,
        coherentElement: null,
        comments: null,
        type: null,
        len: null
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
      this.title = "添加元数据设置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMetadataRule(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改元数据设置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMetadataRule(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMetadataRule(this.form).then(response => {
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
      this.$confirm('是否确认删除元数据设置编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delMetadataRule(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有元数据设置数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportMetadataRule(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
