<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <!--<el-form-item label="分类名称" prop="cateName">
        <el-input
          v-model="queryParams.cateName"
          placeholder="请输入分类名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型标识，0全部可看 1客户可看 2额外付费观看" prop="payFlag">
        <el-input
          v-model="queryParams.payFlag"
          placeholder="请输入类型标识，0全部可看 1客户可看 2额外付费观看"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>-->
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['custom:videoClassify:add']"
        >新增</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['custom:videoClassify:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['custom:videoClassify:remove']"
        >删除</el-button>
      </el-col>-->
      <!--<el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['custom:videoClassify:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="videoClassifyList"
      row-key="id"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="分类名称"  prop="cateName" :show-overflow-tooltip="true"/>
      <el-table-column label="显示排序"  prop="orderNum" align="center"/>
      <el-table-column label="创建时间"  prop="createTime" align="center">
        <template slot-scope="scope">
             <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
          icon="el-icon-plus"
          type="text"
          size="mini"
          v-if="threeLevelClassify.indexOf(scope.row.id) == -1"
          @click="handleAdd(scope.row)"
          v-hasPermi="['custom:videoClassify:add']"
        >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['custom:videoClassify:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['custom:videoClassify:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--<pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />-->

    <!-- 添加或修改视频分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上级分类">
              <treeselect
                v-model="form.parentId"
                :options="classifyOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="选择上级分类"
              />
        </el-form-item>
        <el-form-item label="分类名称" prop="cateName">
          <el-input v-model.trim="form.cateName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
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
  import { listVideoClassify, getVideoClassify, delVideoClassify, addVideoClassify, updateVideoClassify, exportVideoClassify,getAllClassify } from "@/api/custom/videoClassify";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import IconSelect from "@/components/IconSelect";
  export default {
    name: "VideoClassify",
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
        // 视频分类表格数据
        videoClassifyList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          cateName: null,
          payFlag: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
           cateName: [
            { required: true, message: "分类名称不能为空", trigger: "blur" },
          ],
        },
        classifyOptions:[],
        //三级目录ID
        threeLevelClassify:[]
      };
    },
    components: { Treeselect, IconSelect },
    created() {
      this.getList();
      this.getTreeselect();
    },
    methods: {
      /** 查询视频分类列表 */
      getList() {
        this.loading = true;
        listVideoClassify(this.queryParams).then(response => {
          this.videoClassifyList = this.handleTree(response.rows, "id");
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
          cateName: null,
          parentId: null,
          orderNum: 0,
          payFlag: null,
          createTime: null,
          createBy: null,
          updateTime: null,
          updateBy: null,
          delFlag: null
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
      handleAdd(row) {
        this.reset();
        if (row != null && row.id) {
          this.form.parentId = row.id;
        } else {
          this.form.parentId = 0;
        }
        this.open = true;
        this.title = "添加视频分类";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        getVideoClassify(id).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改视频分类";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            //三级分类不能作为父级
            if(this.threeLevelClassify.indexOf(this.form.parentId) != -1){
                this.$message({
                    message: "最多只支持三级分类，三级类别不能作为上级",
                    type: "warning",
                });
                return;
            }
            if (this.form.id != null) {
              updateVideoClassify(this.form).then(response => {
                if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
                this.getTreeselect();
              }
            });
            } else {
              addVideoClassify(this.form).then(response => {
                if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
                this.getTreeselect();
              }
            });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids;
        this.$confirm('是否确认删除视频分类编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delVideoClassify(ids);
        }).then(() => {
          this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有视频分类数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportVideoClassify(queryParams);
        }).then(response => {
          this.download(response.msg);
      }).catch(function() {});
      },
      /** 转换菜单数据结构 */
      normalizer(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.id,
          label: node.cateName,
          children: node.children
        };
      },
       /** 查询目录下拉树结构 */
      getTreeselect() {
        getAllClassify().then(response => {
          this.classifyOptions = [];
          const classify = { id: 0, cateName: '主分类', children: [] };
          classify.children = this.handleTree(response.data, "id");
          classify.children.forEach((item,i) => {
              if(item.children && item.children.length > 0){
                 item.children.forEach((tem,j) => {
                      if(tem.children && tem.children.length > 0){
                         tem.children.forEach(element => {
                              this.threeLevelClassify.push(element.id);
                         });
                      }
                 });
              }
          });
          console.log(this.threeLevelClassify.length);
          this.classifyOptions.push(classify);
        });
      },
    }
  };
</script>
