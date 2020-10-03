<template>
  <div class="app-container" style="overflow-y: scroll;height:750px">
<!--    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">-->
<!--      <el-form-item label="标签名字" prop="name">-->
<!--        <el-input-->
<!--          v-model="queryParams.name"-->
<!--          placeholder="请输入标签名字"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="引用数量" prop="icount">-->
<!--        <el-input-->
<!--          v-model="queryParams.icount"-->
<!--          placeholder="请输入引用数量"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="谁增加的该标签" prop="userId">-->
<!--        <el-input-->
<!--          v-model="queryParams.userId"-->
<!--          placeholder="请输入谁增加的该标签"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="标签的字体颜色" prop="isFontColor">-->
<!--        <el-input-->
<!--          v-model="queryParams.isFontColor"-->
<!--          placeholder="请输入标签的字体颜色"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="标签的背景颜色" prop="isBgColor">-->
<!--        <el-input-->
<!--          v-model="queryParams.isBgColor"-->
<!--          placeholder="请输入标签的背景颜色"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="S系统标签,P用户标签" prop="tagType">-->
<!--        <el-select v-model="queryParams.tagType" placeholder="请选择S系统标签,P用户标签" clearable size="small">-->
<!--          <el-option label="请选择字典生成" value="" />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="状态 0显示默认" prop="istatus">-->
<!--        <el-select v-model="queryParams.istatus" placeholder="请选择状态 0显示默认" clearable size="small">-->
<!--          <el-option label="请选择字典生成" value="" />-->
<!--        </el-select>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="排序" prop="tagSort">-->
<!--        <el-input-->
<!--          v-model="queryParams.tagSort"-->
<!--          placeholder="请输入排序"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
<!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:tag:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:tag:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:tag:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:tag:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="tagList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="标签名字"  width="155" >
        <template slot-scope="scope">
              <el-tag type="success" size="medium">{{ scope.row.name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="引用数量" align="center" prop="icount" />
      <el-table-column label="标签的字体颜色" align="center" prop="isFontColor" />
      <el-table-column label="标签的背景颜色" align="center" prop="isBgColor" />
      <el-table-column label="排序" align="center" prop="tagSort" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:tag:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:tag:remove']"
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

    <!-- 添加或修改书签_标签对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标签名字" prop="name">
          <el-input v-model="form.name" placeholder="请输入标签名字" />
        </el-form-item>
        <el-form-item label="引用数量" prop="icount">
          <el-input v-model="form.icount" placeholder="请输入引用数量" />
        </el-form-item>
        <el-form-item label="谁增加的该标签" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入谁增加的该标签" />
        </el-form-item>
        <el-form-item label="标签的字体颜色" prop="isFontColor">
          <el-input v-model="form.isFontColor" placeholder="请输入标签的字体颜色" />
        </el-form-item>
        <el-form-item label="标签的背景颜色" prop="isBgColor">
          <el-input v-model="form.isBgColor" placeholder="请输入标签的背景颜色" />
        </el-form-item>
        <el-form-item label="S系统标签,P用户标签">
          <el-select v-model="form.tagType" placeholder="请选择S系统标签,P用户标签">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态 0显示默认">
          <el-radio-group v-model="form.istatus">
            <el-radio label="1">请选择字典生成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="排序" prop="tagSort">
          <el-input v-model="form.tagSort" placeholder="请输入排序" />
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
  import { listTag, getTag, delTag, addTag, updateTag, exportTag } from "@/api/bookmark/tag";

  export default {
    name: "Tag",
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
        // 书签_标签表格数据
        tagList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          name: undefined,
          icount: undefined,
          userId: undefined,
          isFontColor: undefined,
          isBgColor: undefined,
          tagType: undefined,
          istatus: undefined,
          tagSort: undefined,
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
    },
    methods: {
      /** 查询书签_标签列表 */
      getList() {
        this.loading = true;
        listTag(this.queryParams).then(response => {
          this.tagList = response.rows;
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
          name: undefined,
          icount: undefined,
          userId: undefined,
          isFontColor: undefined,
          isBgColor: undefined,
          tagType: undefined,
          istatus: "0",
          tagSort: undefined,
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
        this.ids = selection.map(item => item.id)
        this.single = selection.length!=1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加书签_标签";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getTag(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改书签_标签";
        });
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != undefined) {
              updateTag(this.form).then(response => {
                if (response.code === 200) {
                  this.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                }
              });
            } else {
              addTag(this.form).then(response => {
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
        this.$confirm('是否确认删除书签_标签编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delTag(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有书签_标签数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportTag(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
      }
    }
  };
</script>
<style scoped>
  .app-container{
    padding: 0px;
  }

</style>
